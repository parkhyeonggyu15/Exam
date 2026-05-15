package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * [PRACTICE] 팀 메신저 클라이언트 — ☆ 학생 구현
 *
 * <pre>
 * 능력단위요소 2 / 수행준거 2.2 + 2.4
 * 체크리스트 2-2 (Socket connect + I/O Stream), 2-5 (try-catch-finally)
 * </pre>
 *
 * <p>MessengerClientUI 와 연결되는 비-UI 통신 클래스.</p>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 * <ol>
 *   <li>connect(): Socket 생성 → in/out 스트림 → 닉네임 송신 → Daemon Thread 시작</li>
 *   <li>send(): out 이 null 이 아니면 println(message)</li>
 *   <li>disconnect(): running = false → in/out/socket close</li>
 *   <li>readLoop() 는 이미 구현되어 있음. connect 안에서 호출하는 Thread 만 시작.</li>
 * </ol>
 *
 * <p>필요 import 추가: <code>java.io.InputStreamReader</code>, <code>java.io.OutputStreamWriter</code></p>
 * <p>정답은 PRACTICE_답 참고.</p>
 */
public class MessengerClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Thread readerThread;
    private final Consumer<String> messageListener;
    private volatile boolean running = false;

    public MessengerClient(Consumer<String> messageListener) {
        this.messageListener = messageListener;
    }

    /** 서버 접속 */
    public void connect(String host, int port, String nickname) throws IOException {
        // TODO 1) socket = new Socket(host, port);
    	socket = new Socket(host,port);
        
    	// TODO 2) in / out 스트림 초기화 (UTF-8)
    	 InputStream tmpIn = socket.getInputStream();
         InputStreamReader in_reader = new InputStreamReader(tmpIn);
         in = new BufferedReader(in_reader);
         out = new PrintWriter(socket.getOutputStream());
        
        
        // TODO 3) out 으로 첫 줄에 닉네임 송신
         out.println(nickname);
         
        // TODO 4) running = true; → new Thread(this::readLoop).setDaemon(true) → start()
         running = true;
         readerThread = new Thread() {

			@Override
			public void run() {
				readLoop();
			}
         };
         readerThread.setDaemon(true);
         readerThread.start();
    }

    /** 메시지 송신 */
    public void send(String message) {
        // TODO: out 이 null 이 아니면 println(message)
    	if(out!=null) {
    		out.println(message);
    		out.flush();
    	}
    }

    /**
     * 수신 루프 — 별도 Thread 에서 실행됨 (제공)
     *
     * <p>이 메서드는 이미 구현되어 있습니다.
     *    학생은 connect() 안에서 이 메서드를 호출하는 Daemon Thread 를 시작하기만 하면 됩니다.</p>
     */
    private void readLoop() {
        try {
            String line;
            while (in != null && (line = in.readLine()) != null) {
                messageListener.accept(line);
            }
        } catch (IOException e) {
            if (running) {
                messageListener.accept("[시스템] 서버와의 연결이 종료되었습니다: " + e.getMessage());
            }
        } finally {
            disconnect();
        }
    }

    /** 접속 종료 */
    public void disconnect() {
        running = false;
        // TODO: socket → in → out 순서로 close (각각 null 체크 + try-catch 로 IOException 무시)
        //       ★ socket 을 먼저 닫아야 reader 스레드의 blocking read 가 깨어나 BufferedReader lock 이 풀림
        //       (Java 21 부터 BufferedReader 는 ReentrantLock 으로 보호 → 순서 잘못되면 EDT deadlock)
        
        try {if(socket != null && !socket.isClosed()) socket.close();}catch(IOException e) {e.printStackTrace();}
        try {if(in != null) in.close();}catch(IOException e) {e.printStackTrace();}
        if(out != null) out.close();
        
    }

    public boolean isConnected() {
        return socket != null && !socket.isClosed();
    }
}
