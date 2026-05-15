package memo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * 메모 관리 Swing UI (★ 제공 — 완성본)
 *
 * <p>학생은 이 파일을 수정할 필요가 없습니다.
 * MemoDAO 의 insert / selectAll / update / delete 가 구현되면
 * 모든 버튼이 동작합니다.</p>
 *
 * 화면 구성:
 *   ┌─────────────────────────────────────────────┐
 *   │ 입력 폼 (제목 + 내용)                         │
 *   │ [추가] [수정] [삭제] [새로고침] [폼 초기화]    │
 *   ├─────────────────────────────────────────────┤
 *   │  메모 목록 테이블 (행 클릭 → 폼에 자동 채움)   │
 *   └─────────────────────────────────────────────┘
 */
public class MemoManagementUI extends JFrame {

    private final JTextField  titleField   = new JTextField(25);
    private final JTextArea   contentArea  = new JTextArea(4, 25);

    private final JButton addBtn      = new JButton("추가");
    private final JButton updateBtn   = new JButton("수정");
    private final JButton deleteBtn   = new JButton("삭제");
    private final JButton refreshBtn  = new JButton("새로고침");
    private final JButton clearBtn    = new JButton("폼 초기화");

    private final String[] columns = {"memo_id", "title", "content", "created_at"};
    private final DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    private final MemoDAO dao = new MemoDAO();
    private Integer selectedId = null;

    public MemoManagementUI() {
        super("Memo Management — JDBC CRUD (PRACTICE)");

        initLayout();
        initEvents();

        setSize(820, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(this::doRefresh);
    }

    /** Swing 컴포넌트 배치 — 상단 입력 폼 + 버튼, 중앙 메모 목록 테이블. */
    private void initLayout() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("메모 입력"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; form.add(new JLabel("제목*"), g);
        g.gridx = 1; g.gridy = 0; g.weightx = 1.0; form.add(titleField, g); g.weightx = 0;

        contentArea.setLineWrap(true);
        contentArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        JScrollPane contentScroll = new JScrollPane(contentArea);
        g.gridx = 0; g.gridy = 1; g.anchor = GridBagConstraints.NORTHWEST; form.add(new JLabel("내용"), g);
        g.gridx = 1; g.gridy = 1; g.fill = GridBagConstraints.BOTH; g.weightx = 1.0; g.weighty = 1.0;
        form.add(contentScroll, g);
        g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 0; g.weighty = 0;

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        buttons.add(addBtn); buttons.add(updateBtn); buttons.add(deleteBtn);
        buttons.add(refreshBtn); buttons.add(clearBtn);

        JPanel north = new JPanel(new BorderLayout());
        north.add(form,    BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("메모 목록"));

        setLayout(new BorderLayout(4, 4));
        add(north,  BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    /** 버튼 ActionListener 등록 + 테이블 행 선택 시 폼 자동 채우기 (선택 행의 ID 저장). */
    private void initEvents() {
        addBtn.addActionListener(e -> doInsert());
        updateBtn.addActionListener(e -> doUpdate());
        deleteBtn.addActionListener(e -> doDelete());
        refreshBtn.addActionListener(e -> doRefresh());
        clearBtn.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int idx = table.getSelectedRow();
            if (idx < 0) { selectedId = null; return; }
            Object id = tableModel.getValueAt(idx, 0);
            selectedId = (id instanceof Integer) ? (Integer) id
                       : Integer.parseInt(id.toString());
            titleField.setText  (str(tableModel.getValueAt(idx, 1)));
            contentArea.setText (str(tableModel.getValueAt(idx, 2)));
        });
    }

    // --- 액션 ---

    /** [추가] 버튼 — 폼 입력값으로 Memo VO 만들어 MemoDAO.insert() 호출. */
    private void doInsert() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) { warn("제목은 필수입니다."); return; }

        Memo m = new Memo();
        m.setTitle(title);
        m.setContent(contentArea.getText());

        try {
            int n = dao.insert(m);
            info(n + "건 추가되었습니다.");
            doRefresh();
            clearForm();
        } catch (SQLException ex) {
            error("추가 실패: " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            error("MemoDAO.insert() 가 아직 구현되지 않았습니다.\n" + ex.getMessage());
        }
    }

    /** [수정] 버튼 — 선택 행의 ID 기준으로 폼 값을 MemoDAO.update() 호출. */
    private void doUpdate() {
        if (selectedId == null) { warn("행을 선택하세요."); return; }
        String title = titleField.getText().trim();
        if (title.isEmpty()) { warn("제목은 필수입니다."); return; }

        Memo m = new Memo();
        m.setMemoId(selectedId);
        m.setTitle(title);
        m.setContent(contentArea.getText());

        try {
            int n = dao.update(m);
            if (n == 0) info("일치하는 메모가 없어 수정되지 않았습니다.");
            else        info(n + "건 수정되었습니다.");
            doRefresh();
        } catch (SQLException ex) {
            error("수정 실패: " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            error("MemoDAO.update() 가 아직 구현되지 않았습니다.\n" + ex.getMessage());
        }
    }

    /** [삭제] 버튼 — 확인 다이얼로그 후 선택 행 ID 로 MemoDAO.delete() 호출. */
    private void doDelete() {
        if (selectedId == null) { warn("행을 선택하세요."); return; }
        int ok = JOptionPane.showConfirmDialog(this,
                "ID [" + selectedId + "] 메모를 삭제하시겠습니까?",
                "삭제 확인", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            int n = dao.delete(selectedId);
            if (n == 0) info("일치하는 메모가 없어 삭제되지 않았습니다.");
            else        info(n + "건 삭제되었습니다.");
            doRefresh();
            clearForm();
        } catch (SQLException ex) {
            error("삭제 실패: " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            error("MemoDAO.delete() 가 아직 구현되지 않았습니다.\n" + ex.getMessage());
        }
    }

    /** [새로고침] 버튼 — MemoDAO.selectAll() 결과로 테이블 다시 그림. */
    private void doRefresh() {
        try {
            List<Memo> list = dao.selectAll();
            tableModel.setRowCount(0);
            for (Memo m : list) {
                tableModel.addRow(new Object[]{
                        m.getMemoId(),
                        m.getTitle(),
                        m.getContent(),
                        m.getCreatedAt()
                });
            }
            setTitle("Memo Management — JDBC CRUD (총 " + list.size() + " 건)");
        } catch (SQLException ex) {
            error("조회 실패: " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            error("MemoDAO.selectAll() 가 아직 구현되지 않았습니다.\n" + ex.getMessage());
        }
    }

    /** [폼 초기화] 버튼 — 입력 필드 비우고 테이블 선택 해제. */
    private void clearForm() {
        titleField.setText("");
        contentArea.setText("");
        table.clearSelection();
        selectedId = null;
        titleField.requestFocus();
    }

    private static String str(Object o) { return o == null ? "" : o.toString(); }
    private void info(String m)  { JOptionPane.showMessageDialog(this, m, "안내", JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String m)  { JOptionPane.showMessageDialog(this, m, "확인", JOptionPane.WARNING_MESSAGE); }
    private void error(String m) { JOptionPane.showMessageDialog(this, m, "오류", JOptionPane.ERROR_MESSAGE); }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new MemoManagementUI().setVisible(true));
    }
}
