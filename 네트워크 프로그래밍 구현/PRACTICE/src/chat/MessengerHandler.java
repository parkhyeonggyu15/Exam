package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * [PRACTICE] 클라이언트 1명을 처리하는 Thread — ☆ 학생 구현
 *
 * <pre>
 * 능력단위요소 2 / 수행준거 2.3 + 2.4
 * 체크리스트 2-3 (Thread), 2-4 (broadcast 송신), 2-5 (자원 해제)
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 * <ol>
 *   <li>생성자: Socket 의 InputStream/OutputStream 을 BufferedReader/PrintWriter 로 감싸기 (UTF-8 명시).</li>
 *   <li>run(): readLine() 루프 — 첫 줄 닉네임 받고, 이후 받는 줄마다 broadcast.</li>
 *   <li>send(): out.println(message) 한 줄.</li>
 *   <li>finally: MessengerServer.remove(this) + in/out/socket close.</li>
 * </ol>
 *
 * <p>필요 import 추가: <code>java.io.InputStreamReader</code>, <code>java.io.OutputStreamWriter</code></p>
 * <p>정답은 PRACTICE_답 참고.</p>
 */
public class MessengerHandler implements Runnable {

    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String nickname = "팀원";

    public MessengerHandler(Socket socket) throws IOException {
        this.socket = socket;
        // TODO: in / out 스트림 초기화 (UTF-8 명시!)
        InputStream tmpIn = socket.getInputStream();
        InputStreamReader in_reader = new InputStreamReader(tmpIn);
        in = new BufferedReader(in_reader);
        out = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            // ====== 학생 구현 시작 ======================================
            // TODO 1) (선택) 첫 줄을 닉네임으로 받아 nickname 필드에 저장 + 입장 broadcast
        	String nick = in.readLine();
        	if(nick!=null && !nick.isBlank()) {
        		this.nickname = nick;
        	}
        	
            // TODO 2) readLine() 반복 → null 이 아니면 MessengerServer.broadcast(닉네임 + " : " + line)
        	String line = null;	//recv용
        	while((line = in.readLine())!=null ) 
        		MessengerServer.broadcast(nickname + " : " + line);
        	
        	
        	
            // 학생이 위 TODO 를 모두 구현하면 아래 한 줄 삭제
            
            // ====== 학생 구현 끝 ========================================
        } catch (IOException e) {
            System.err.println("[MessengerHandler] " + nickname + " 접속 종료: " + e.getMessage());
        } finally {
            // TODO 3) MessengerServer.remove(this) 로 컬렉션에서 제거
        	MessengerServer.remove(this);
            // TODO 4) in / out / socket 자원 해제 (각각 null 체크 + try-catch)
            try {in.close();} catch (IOException e) {e.printStackTrace();}
            try {out.close();} catch (Exception e) {e.printStackTrace();}
            try {socket.close();} catch (IOException e) {e.printStackTrace();}
            
        	// TODO 5) 퇴장 broadcast
            MessengerServer.broadcast("[퇴장]" + nickname);
        }
    }

    /**
     * 이 핸들러가 담당하는 클라이언트에게 한 줄 전송.
     *
     * <p>학생 TODO: out 이 null 이 아니면 println(message) 호출.</p>
     */
    public void send(String message) {
        // TODO: out 으로 한 줄 송신
    	if(out!=null) {
    		out.println(message);
    		out.flush();
    	}
    }

    public String getNickname() {
        return nickname;
    }
}
