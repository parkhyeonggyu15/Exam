package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 채팅 서버 (☆ 학생 구현)
 *
 * <pre>
 * 담당자(@assignee): ___________________
 * 작업단위(@task)  : TASK-A
 * 가이드 카드      : docs/tasks/TASK-A_조원_ChatServer_Handler.md
 *
 * 능력단위요소 2 / 수행준거 2.1 + 2.3 + 2.4
 * 체크리스트 2-1 (ServerSocket bind/accept), 2-3 (Thread 다중접속), 2-5 (try-catch-finally)
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 * <ol>
 *   <li>main() 에서 ServerSocket(PORT) 를 바인딩한다.</li>
 *   <li>무한 루프에서 accept() 로 클라이언트 접속을 수락한다.</li>
 *   <li>접속한 Socket 을 ChatHandler 로 감싸 새 Thread 로 시작한다.</li>
 *   <li>접속자 목록(clients) 에 추가하여 broadcast 가 가능하게 한다.</li>
 *   <li>예외 처리(try-catch-finally) 와 ServerSocket close() 를 잊지 말 것.</li>
 * </ol>
 *
 * <p>정답 코드 확인이 필요하면 강사용 SOLVED 프로젝트 또는 평가 후 공개 자료 참고.</p>
 *
 * <p>참고:
 *  - 접속자 컬렉션은 여러 스레드가 동시에 접근 → Collections.synchronizedSet 사용 권장.
 *  - broadcast 메서드는 static 으로 두면 ChatHandler 에서 호출하기 쉽다.
 * </p>
 */
public class ChatServer {

    public static final int PORT = 9999;

    /** 접속한 모든 클라이언트 핸들러 (스레드 안전 컬렉션) */
    public static final Set<ChatHandler> clients =
            Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // ====== 학생 구현 시작 ======================================
            // TODO 1) ServerSocket(PORT) 생성 + 시작 로그 출력
            // TODO 2) 무한 루프 안에서 accept() 로 Socket 받기
            // TODO 3) 받은 Socket 으로 ChatHandler 생성 → clients 에 add → 새 Thread 로 start

            // 학생이 위 TODO 를 모두 구현하면 아래 한 줄 삭제
            throw new IOException("ChatServer.main() 가 아직 구현되지 않았습니다. (TODO)");
            // ====== 학생 구현 끝 ========================================
        } catch (IOException e) {
            System.err.println("[ChatServer] 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // TODO 4) ServerSocket 자원 해제 (null 체크 + try-catch)
            if (server != null) {
                try { server.close(); } catch (IOException ignored) {}
            }
        }
    }

    /**
     * 모든 접속 클라이언트에게 메시지를 브로드캐스트.
     *
     * <p>학생 TODO:
     *   clients 컬렉션을 (스레드 안전하게) 순회하며 각 핸들러의 send(message) 호출.
     * </p>
     */
    public static void broadcast(String message) {
        // TODO: 동기화 블록 안에서 clients 순회 → 각 handler.send(message) 호출
        System.out.println("[broadcast TODO] " + message);
    }

    /** 클라이언트 접속 종료 시 컬렉션에서 제거 */
    public static void remove(ChatHandler handler) {
        clients.remove(handler);
    }
}
