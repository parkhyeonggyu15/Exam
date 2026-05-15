package book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * 도서관리 Swing UI (★ 제공 — 완성본)
 *
 * <p>학생은 이 파일을 수정할 필요가 없습니다.
 * BookDAO 의 insert / selectAll / update / delete 가 정상 구현되면
 * 모든 버튼이 동작합니다.</p>
 *
 * 화면 구성:
 *   ┌─────────────────────────────────────────────────────┐
 *   │ 입력 폼 (코드/분류ID/저자/제목/출판사/예약여부)        │
 *   │ [추가] [수정] [삭제] [새로고침] [폼 초기화]            │
 *   ├─────────────────────────────────────────────────────┤
 *   │  도서 목록 테이블 (행 클릭 → 폼에 자동 채움)           │
 *   └─────────────────────────────────────────────────────┘
 */
public class BookManagementUI extends JFrame {

    private final JTextField codeField      = new JTextField(8);
    private final JTextField classIdField   = new JTextField(4);
    private final JTextField authorField    = new JTextField(10);
    private final JTextField nameField      = new JTextField(15);
    private final JTextField publisherField = new JTextField(10);
    private final JComboBox<String> reserveCombo = new JComboBox<>(new String[]{"N", "Y"});

    private final JButton addBtn     = new JButton("추가");
    private final JButton updateBtn  = new JButton("수정");
    private final JButton deleteBtn  = new JButton("삭제");
    private final JButton refreshBtn = new JButton("새로고침");
    private final JButton clearBtn   = new JButton("폼 초기화");

    private final String[] columns = {"Book_code", "Classification_Id", "author", "name", "publisher", "isreserve"};
    private final DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable table = new JTable(tableModel);

    private final BookDAO dao = new BookDAO();

    public BookManagementUI() {
        super("Book Management — JDBC CRUD");

        initLayout();
        initEvents();

        setSize(820, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 시작 시 자동 조회
        SwingUtilities.invokeLater(this::doRefresh);
    }

    /** Swing 컴포넌트 배치 — 상단 입력 폼 + 버튼, 중앙 도서 목록 테이블. */
    private void initLayout() {
        // === 입력 폼 ===
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("도서 정보 입력"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addRow(form, g, row++, "도서코드*",    codeField,      "분류ID",   classIdField);
        addRow(form, g, row++, "저자",          authorField,    "출판사",   publisherField);

        g.gridx = 0; g.gridy = row; form.add(new JLabel("제목*"), g);
        g.gridx = 1; g.gridy = row; g.gridwidth = 3; form.add(nameField, g); g.gridwidth = 1;

        row++;
        g.gridx = 0; g.gridy = row; form.add(new JLabel("예약여부"), g);
        g.gridx = 1; g.gridy = row; form.add(reserveCombo, g);

        // === 버튼 패널 ===
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);
        buttons.add(refreshBtn);
        buttons.add(clearBtn);

        JPanel north = new JPanel(new BorderLayout());
        north.add(form,    BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);

        // === 테이블 ===
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("도서 목록"));

        setLayout(new BorderLayout(4, 4));
        add(north,  BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    /** GridBagLayout 에 한 줄(라벨+필드 2쌍)을 같은 행에 배치하는 헬퍼. */
    private void addRow(JPanel form, GridBagConstraints g, int row,
                        String l1, JComponent c1, String l2, JComponent c2) {
        g.gridx = 0; g.gridy = row; form.add(new JLabel(l1), g);
        g.gridx = 1; g.gridy = row; form.add(c1, g);
        g.gridx = 2; g.gridy = row; form.add(new JLabel(l2), g);
        g.gridx = 3; g.gridy = row; form.add(c2, g);
    }

    /** 버튼 ActionListener 등록 + 테이블 행 선택 시 폼 자동 채우기. */
    private void initEvents() {
        addBtn.addActionListener(e -> doInsert());
        updateBtn.addActionListener(e -> doUpdate());
        deleteBtn.addActionListener(e -> doDelete());
        refreshBtn.addActionListener(e -> doRefresh());
        clearBtn.addActionListener(e -> clearForm());

        // 행 클릭 → 폼 채우기
        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int idx = table.getSelectedRow();
            if (idx < 0) return;
            codeField.setText     (str(tableModel.getValueAt(idx, 0)));
            classIdField.setText  (str(tableModel.getValueAt(idx, 1)));
            authorField.setText   (str(tableModel.getValueAt(idx, 2)));
            nameField.setText     (str(tableModel.getValueAt(idx, 3)));
            publisherField.setText(str(tableModel.getValueAt(idx, 4)));
            String r = str(tableModel.getValueAt(idx, 5));
            reserveCombo.setSelectedItem("Y".equalsIgnoreCase(r) ? "Y" : "N");
        });
    }

    // --- 액션 핸들러 ---

    /** [추가] 버튼 — 폼 입력값을 BookDAO.insert() 로 DB 에 등록. */
    private void doInsert() {
        Book book = readForm();
        if (book == null) return;
        try {
            int n = dao.insert(book);
            info(n + "건 추가되었습니다.");
            doRefresh();
            clearForm();
        } catch (SQLException ex) {
            error("추가 실패: " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            error("BookDAO.insert() 가 아직 구현되지 않았습니다.\n" + ex.getMessage());
        }
    }

    /** [수정] 버튼 — 폼 입력값을 도서코드 기준으로 BookDAO.update() 호출. */
    private void doUpdate() {
        Book book = readForm();
        if (book == null) return;
        try {
            int n = dao.update(book);
            if (n == 0) info("일치하는 도서코드가 없어 수정되지 않았습니다.");
            else        info(n + "건 수정되었습니다.");
            doRefresh();
        } catch (SQLException ex) {
            error("수정 실패: " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            error("BookDAO.update() 가 아직 구현되지 않았습니다.\n" + ex.getMessage());
        }
    }

    /** [삭제] 버튼 — 확인 다이얼로그 후 도서코드로 BookDAO.delete() 호출. */
    private void doDelete() {
        String code = codeField.getText().trim();
        if (code.isEmpty()) { warn("도서코드를 입력하거나 행을 선택하세요."); return; }
        int ok = JOptionPane.showConfirmDialog(this,
                "도서코드 [" + code + "] 를 삭제하시겠습니까?",
                "삭제 확인", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            int n = dao.delete(code);
            if (n == 0) info("일치하는 도서코드가 없어 삭제되지 않았습니다.");
            else        info(n + "건 삭제되었습니다.");
            doRefresh();
            clearForm();
        } catch (SQLException ex) {
            error("삭제 실패: " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            error("BookDAO.delete() 가 아직 구현되지 않았습니다.\n" + ex.getMessage());
        }
    }

    /** [새로고침] 버튼 — BookDAO.selectAll() 결과로 테이블 다시 그림. */
    private void doRefresh() {
        try {
            List<Book> list = dao.selectAll();
            tableModel.setRowCount(0);
            for (Book b : list) {
                tableModel.addRow(new Object[]{
                        b.getBookCode(),
                        b.getClassificationId(),
                        b.getAuthor(),
                        b.getName(),
                        b.getPublisher(),
                        b.getIsReserve()
                });
            }
            setTitle("Book Management — JDBC CRUD  (총 " + list.size() + " 건)");
        } catch (SQLException ex) {
            error("조회 실패: " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            error("BookDAO.selectAll() 가 아직 구현되지 않았습니다.\n" + ex.getMessage());
        }
    }

    // --- 헬퍼 ---

    /** 폼 입력값을 Book VO 로 변환. 필수값 누락/타입 오류 시 null + 경고창. */
    private Book readForm() {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();
        if (code.isEmpty() || name.isEmpty()) {
            warn("도서코드와 제목은 필수입니다.");
            return null;
        }
        Integer cid = null;
        String cidText = classIdField.getText().trim();
        if (!cidText.isEmpty()) {
            try { cid = Integer.parseInt(cidText); }
            catch (NumberFormatException e) { warn("분류ID 는 숫자여야 합니다."); return null; }
        }
        return new Book(
                code,
                cid,
                authorField.getText().trim(),
                name,
                publisherField.getText().trim(),
                (String) reserveCombo.getSelectedItem()
        );
    }

    /** [폼 초기화] 버튼 — 모든 입력 필드 비우고 테이블 선택 해제. */
    private void clearForm() {
        codeField.setText("");
        classIdField.setText("");
        authorField.setText("");
        nameField.setText("");
        publisherField.setText("");
        reserveCombo.setSelectedIndex(0);
        table.clearSelection();
        codeField.requestFocus();
    }

    private static String str(Object o) { return o == null ? "" : o.toString(); }
    private void info(String m)  { JOptionPane.showMessageDialog(this, m, "안내",   JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String m)  { JOptionPane.showMessageDialog(this, m, "확인",   JOptionPane.WARNING_MESSAGE); }
    private void error(String m) { JOptionPane.showMessageDialog(this, m, "오류",   JOptionPane.ERROR_MESSAGE); }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new BookManagementUI().setVisible(true));
    }
}
