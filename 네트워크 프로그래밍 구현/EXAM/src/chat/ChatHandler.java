package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 클라이언트 1명을 처리하는 Thread (☆ 학생 구현)
 *
 * <pre>
 * 담당자(@assignee): ___________________
 * 작업단위(@task)  : TASK-A
 * 가이드 카드      : docs/tasks/TASK-A_조원_ChatServer_Handler.md
 *
 * 능력단위요소 2 / 수행준거 2.3 + 2.4
 * 체크리스트 2-3 (Thread), 2-4 (broadcast 송신), 2-5 (자원 해제)
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 * <ol>
 *   <li>생성자: Socket 의 InputStream / OutputStream 을 BufferedReader / PrintWriter 로 감싼다 (UTF-8 명시).</li>
 *   <li>run(): 첫 줄을 닉네임으로 받고, readLine() 반복하여 ChatServer.broadcast() 호출.</li>
 *   <li>send(): out.println(message) 한 줄.</li>
 *   <li>finally: ChatServer.remove(this) + in/out/socket close.</li>
 * </ol>
 *
 * <p>필요 import (추가): <code>java.io.InputStreamReader</code>, <code>java.io.OutputStreamWriter</code></p>
 */
public class ChatHandler implements Runnable {

    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String nickname = "guest";

    public ChatHandler(Socket socket) throws IOException {
        this.socket = socket;
        // TODO: in / out 스트림 초기화 (UTF-8 명시!)
        //   - in : BufferedReader + InputStreamReader(socket.getInputStream(), "UTF-8")
        //   - out: PrintWriter   + OutputStreamWriter(socket.getOutputStream(), "UTF-8"), autoFlush=true
    }

    @Override
    public void run() {
        try {
            // ====== 학생 구현 시작 ======================================
            // TODO 1) (선택) 첫 줄을 닉네임으로 받아 nickname 필드에 저장 + 입장 broadcast
            // TODO 2) readLine() 반복 → null 이 아니면 ChatServer.broadcast(닉네임 + " : " + line)

            // 학생이 위 TODO 를 모두 구현하면 아래 한 줄 삭제
            throw new IOException("ChatHandler.run() 이 아직 구현되지 않았습니다. (TODO)");
            // ====== 학생 구현 끝 ========================================
        } catch (IOException e) {
            System.err.println("[ChatHandler] " + nickname + " 접속 종료: " + e.getMessage());
        } finally {
            // TODO 3) ChatServer.remove(this) 로 컬렉션에서 제거
            // TODO 4) in / out / socket 자원 해제 (각각 null 체크 + try-catch)
            // TODO 5) 퇴장 broadcast
        }
    }

    /**
     * 이 핸들러가 담당하는 클라이언트에게 한 줄 전송.
     *
     * <p>학생 TODO: out 이 null 이 아니면 println(message) 호출.</p>
     */
    public void send(String message) {
        // TODO: out 으로 한 줄 송신
    }

    public String getNickname() {
        return nickname;
    }
}
