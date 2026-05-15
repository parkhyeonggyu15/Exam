package memo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * [PRACTICE] JDBC Connection 관리자 — ☆ 학생 구현
 *
 * <pre>
 * 능력단위요소 1 / 수행준거 1.2 + 1.3
 * 능력단위요소 3 / 수행준거 3.1
 * 체크리스트 1-5 (properties 분리), 3-2 (Driver 로드 + Connection 획득)
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 *
 * <ol>
 *   <li><code>getConnection()</code>:
 *     <ul>
 *       <li>① <code>Properties</code> 로 <code>config/db.properties</code> 로드</li>
 *       <li>② <code>Class.forName(driver)</code> — JDBC 4단계 ① Driver Load</li>
 *       <li>③ <code>DriverManager.getConnection(url, user, password)</code> — JDBC 4단계 ② Connection</li>
 *     </ul>
 *   </li>
 *   <li><code>close(AutoCloseable...)</code>:
 *     for-each 로 순회 + null 체크 + try-catch 로 close()</li>
 * </ol>
 *
 * <p>필요 import 5개 추가:
 *   <code>java.io.FileInputStream</code>,
 *   <code>java.io.IOException</code>,
 *   <code>java.io.InputStream</code>,
 *   <code>java.sql.DriverManager</code>,
 *   <code>java.util.Properties</code>
 * </p>
 *
 * <p>⚠️ 비밀번호 하드코딩 금지 — 반드시 properties 파일 사용 (체크리스트 1-5)</p>
 * <p>정답은 PRACTICE_답 참고.</p>
 */
public class DBManager {

    private DBManager() {}

    public static Connection getConnection() throws SQLException {
        // TODO 1) Properties 로 config/db.properties 로드
        Properties prop = new Properties();
        
        try(InputStream fin = new FileInputStream("config/db.properties")) {
        	prop.load(fin);
        }catch(IOException e) {
//        	e.printStackTrace();
        	throw new RuntimeException("DBManager's getConnection() db.propertis..."+e.getCause());
        }
        String driver 	= prop.getProperty("db.driver");
        String url 		= prop.getProperty("db.url");
        String user 	= prop.getProperty("db.user");
        String password = prop.getProperty("db.password");
    	
        
        // TODO 2) Class.forName(driver)
        
        try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			throw new RuntimeException("DBManager's getConnection() class.forName..."+e.getCause());
		}
        
        // TODO 3) return DriverManager.getConnection(url, user, password);

        return DriverManager.getConnection(url, user, password);
        
        
    	
    	
    	
//        throw new UnsupportedOperationException(
//                "DBManager.getConnection() — 아직 구현되지 않았습니다.\n" +
//                "  → src/memo/DBManager.java 의 TODO 를 채우세요.");
    }

    /** AutoCloseable 자원 여러 개 안전 close */
    public static void close(AutoCloseable... closeables) {
        // TODO: for-each + null 체크 + try { c.close(); } catch (Exception ignored) {}
    	for(AutoCloseable c : closeables) {
    		if(c!=null) {
    			try {
					c.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    	
    }
}
