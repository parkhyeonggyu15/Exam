package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * [PRACTICE] 팀 메신저 서버 — ☆ 학생 구현
 *
 * <pre>
 * 능력단위요소 2 / 수행준거 2.1 + 2.3 + 2.4
 * 체크리스트 2-1 (ServerSocket bind/accept), 2-3 (Thread 다중접속), 2-5 (try-catch-finally)
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 * <ol>
 *   <li>main() 에서 ServerSocket(PORT) 를 바인딩한다.</li>
 *   <li>무한 루프에서 accept() 로 클라이언트 접속을 수락한다.</li>
 *   <li>접속한 Socket 을 MessengerHandler 로 감싸 새 Thread 로 시작한다.</li>
 *   <li>접속자 목록(clients) 에 추가하여 broadcast 가 가능하게 한다.</li>
 *   <li>예외 처리(try-catch-finally) 와 ServerSocket close() 를 잊지 말 것.</li>
 * </ol>
 *
 * <p>정답 코드는 <b>PRACTICE_답</b> 프로젝트의 같은 위치 참고.</p>
 */
public class MessengerServer {

    public static final int PORT = 8888;

    /** 접속한 모든 클라이언트 핸들러 (스레드 안전 컬렉션) */
    public static final Set<MessengerHandler> clients =
            Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // ====== 학생 구현 시작 ======================================
            // TODO 1) ServerSocket(PORT) 생성 + 시작 로그 출력
        	server = new ServerSocket(PORT);
        	System.out.println("[MessengerServer] 서버 시작  - PORT : " + PORT);
            
        	// TODO 2) 무한 루프 안에서 accept() 로 Socket 받기
        	while(true) {
        		
        		Socket client = server.accept();
        		
        		// TODO 3) 받은 Socket 으로 MessengerHandler 생성 → clients 에 add → 새 Thread 로 start
        		MessengerHandler handler = new MessengerHandler(client);
        		clients.add(handler);
        		new Thread(handler).start();
        		
        	}
            

            // 학생이 위 TODO 를 모두 구현하면 아래 한 줄 삭제
//            throw new IOException("MessengerServer.main() 가 아직 구현되지 않았습니다. (TODO)");
            // ====== 학생 구현 끝 ========================================
        } catch (IOException e) {
            System.err.println("[MessengerServer] 오류: " + e.getMessage());
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
     * <p>학생 TODO: clients 컬렉션을 (스레드 안전하게) 순회하며 각 핸들러의 send(message) 호출.</p>
     */
    public static void broadcast(String message) {
        // TODO: 동기화 블록 안에서 clients 순회 → 각 handler.send(message) 호출
//        System.out.println("[broadcast TODO] " + message);
    	
    	synchronized (clients) {
    	for(MessengerHandler h : clients) {
    		//h의 outputStream이용해서 내용 전달
    		h.send(message);
    		}
    	}
    	
    }

    public static void remove(MessengerHandler handler) {
        clients.remove(handler);
    }
}
