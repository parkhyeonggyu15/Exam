package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * 채팅 Swing UI (★ 제공 — 완성본)
 *
 * <p>학생은 이 파일을 수정할 필요가 없습니다.
 * 단, ChatClient.connect()/send() 가 정상 구현되어야 동작합니다.</p>
 *
 * 화면 구성:
 *   ┌─────────────────────────────────┐
 *   │ Host: [ localhost ] Port: [9999]│
 *   │ Nick: [ user1     ] [접속][종료] │
 *   ├─────────────────────────────────┤
 *   │  채팅 영역 (JTextArea, scroll)   │
 *   │                                 │
 *   ├─────────────────────────────────┤
 *   │ [입력창 .................][전송] │
 *   └─────────────────────────────────┘
 */
public class ChatClientUI extends JFrame {

    private final JTextField hostField     = new JTextField("localhost", 10);
    private final JTextField portField     = new JTextField("9999", 5);
    private final JTextField nickField     = new JTextField("user" + (int)(Math.random() * 100), 8);
    private final JButton    connectBtn    = new JButton("접속");
    private final JButton    disconnectBtn = new JButton("종료");

    private final JTextArea  chatArea      = new JTextArea();
    private final JTextField inputField    = new JTextField();
    private final JButton    sendBtn       = new JButton("전송");

    private final ChatClient client;

    public ChatClientUI() {
        super("Chat Client");

        // ChatClient 생성 — 수신 메시지가 오면 채팅창에 추가
        client = new ChatClient(this::appendMessage);

        initLayout();
        initEvents();

        setSize(560, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                client.disconnect();
            }
            @Override public void windowClosed(WindowEvent e) {
                // 단독 실행(run-chat.bat) 케이스에서는 이 창이 마지막 Frame.
                // 다른 표시 가능한 Frame(예: 런처)이 없으면 JVM 종료.
                for (Frame f : Frame.getFrames()) {
                    if (f != ChatClientUI.this && f.isDisplayable()) return;
                }
                System.exit(0);
            }
        });

        updateButtons(false);
    }

    /** Swing 컴포넌트 배치 — 상단 접속 패널, 중앙 채팅창, 하단 입력창. */
    private void initLayout() {
        // === 상단 접속 패널 ===
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        top.setBorder(BorderFactory.createTitledBorder("접속 정보"));
        top.add(new JLabel("Host:"));
        top.add(hostField);
        top.add(new JLabel("Port:"));
        top.add(portField);
        top.add(new JLabel("Nick:"));
        top.add(nickField);
        top.add(connectBtn);
        top.add(disconnectBtn);

        // === 중앙 채팅 영역 ===
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(chatArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // === 하단 입력 패널 ===
        JPanel bottom = new JPanel(new BorderLayout(4, 4));
        inputField.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendBtn,    BorderLayout.EAST);

        setLayout(new BorderLayout(4, 4));
        add(top,    BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    /** 버튼 ActionListener 등록. 전송은 버튼 + 엔터키 양쪽 지원. */
    private void initEvents() {
        // 접속
        connectBtn.addActionListener(e -> doConnect());

        // 종료
        disconnectBtn.addActionListener(e -> doDisconnect());

        // 전송 버튼 + 엔터키
        ActionListener sendAction = new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { doSend(); }
        };
        sendBtn.addActionListener(sendAction);
        inputField.addActionListener(sendAction);
    }

    /** [접속] 버튼 — 입력값 검증 후 ChatClient.connect() 호출. */
    private void doConnect() {
        String host = hostField.getText().trim();
        String nick = nickField.getText().trim();
        int port;
        try {
            port = Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "포트 번호가 올바르지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (host.isEmpty() || nick.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Host 와 Nick 을 입력하세요.", "오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            client.connect(host, port, nick);
            appendMessage("[시스템] " + host + ":" + port + " 에 접속했습니다. (nick=" + nick + ")");
            updateButtons(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "접속 실패: " + ex.getMessage(),
                    "접속 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** [종료] 버튼 — 소켓 disconnect 후 창 닫기. */
    private void doDisconnect() {
        client.disconnect();
        appendMessage("[시스템] 접속을 종료했습니다.");
        updateButtons(false);
        dispose();
    }

    /** [전송] 버튼 / 엔터키 — 입력창 내용을 서버로 송신하고 비움. */
    private void doSend() {
        String msg = inputField.getText().trim();
        if (msg.isEmpty()) return;
        if (!client.isConnected()) {
            JOptionPane.showMessageDialog(this,
                    "먼저 [접속] 버튼으로 서버에 접속하세요.",
                    "안내", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        client.send(msg);
        inputField.setText("");
        inputField.requestFocus();
    }

    /** 접속 상태에 따라 버튼/필드 활성 토글. */
    private void updateButtons(boolean connected) {
        connectBtn.setEnabled(!connected);
        disconnectBtn.setEnabled(connected);
        sendBtn.setEnabled(connected);
        inputField.setEnabled(connected);
        hostField.setEnabled(!connected);
        portField.setEnabled(!connected);
        nickField.setEnabled(!connected);
    }

    /** 다른 스레드에서도 안전하게 채팅창에 한 줄 추가 */
    private void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new ChatClientUI().setVisible(true));
    }
}
