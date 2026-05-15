package launcher;

import javax.swing.*;
import java.awt.*;

import book.BookManagementUI;
import chat.ChatClientUI;
import chat.ChatServer;

/**
 * 통합 런처 — 제공
 *
 * <p>실행하면 다음 4가지를 선택하는 작은 창이 뜬다:
 *   1) 채팅 서버 실행
 *   2) 채팅 클라이언트 실행
 *   3) 도서관리 (JDBC CRUD) 실행
 *   4) 종료
 * </p>
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(Main::showLauncher);
    }

    /** 런처 창 구성 — 4개 버튼(서버/클라/도서관리/종료)을 세로로 배치. */
    private static void showLauncher() {
        JFrame f = new JFrame("NCS 네트워크 프로그래밍 구현 — 평가 런처");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridLayout(0, 1, 8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("실행할 항목을 선택하세요", SwingConstants.CENTER);
        title.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        p.add(title);

        JButton b1 = new JButton("① 채팅 서버 실행 (포트 9999)");
        JButton b2 = new JButton("② 채팅 클라이언트 실행");
        JButton b3 = new JButton("③ 도서관리 (JDBC CRUD) 실행");
        JButton b4 = new JButton("④ 종료");

        b1.addActionListener(e -> {
            new Thread(() -> ChatServer.main(new String[]{}), "ChatServer-main").start();
            JOptionPane.showMessageDialog(f, "채팅 서버를 콘솔에서 시작했습니다. (포트 9999)");
        });
        b2.addActionListener(e -> new ChatClientUI().setVisible(true));
        b3.addActionListener(e -> new BookManagementUI().setVisible(true));
        b4.addActionListener(e -> System.exit(0));

        p.add(b1); p.add(b2); p.add(b3); p.add(b4);

        f.setContentPane(p);
        f.pack();
        f.setSize(400, 280);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
