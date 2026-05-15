package launcher;

import javax.swing.*;
import java.awt.*;

import chat.MessengerClientUI;
import chat.MessengerServer;
import memo.MemoManagementUI;

/**
 * 통합 런처 — PRACTICE 용 (제공)
 */
public class Main {

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(Main::showLauncher);
    }

    /** 런처 창 구성 — 4개 버튼(메신저 서버/클라/메모관리/종료)을 세로로 배치. */
    private static void showLauncher() {
        JFrame f = new JFrame("PRACTICE — 메모 공유 + 팀 메신저 런처");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridLayout(0, 1, 8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("실행할 항목을 선택하세요", SwingConstants.CENTER);
        title.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        p.add(title);

        JButton b1 = new JButton("① 팀 메신저 서버 실행 (포트 8888)");
        JButton b2 = new JButton("② 팀 메신저 클라이언트 실행");
        JButton b3 = new JButton("③ 메모 관리 (JDBC CRUD) 실행");
        JButton b4 = new JButton("④ 종료");

        b1.addActionListener(e -> {
            new Thread(() -> MessengerServer.main(new String[]{}), "MessengerServer-main").start();
            JOptionPane.showMessageDialog(f, "메신저 서버를 콘솔에서 시작했습니다. (포트 8888)");
        });
        b2.addActionListener(e -> new MessengerClientUI().setVisible(true));
        b3.addActionListener(e -> new MemoManagementUI().setVisible(true));
        b4.addActionListener(e -> System.exit(0));

        p.add(b1); p.add(b2); p.add(b3); p.add(b4);

        f.setContentPane(p);
        f.pack();
        f.setSize(420, 280);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
