package book;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC Connection 유틸 (☆ 학생 구현)
 *
 * <pre>
 * 담당자(@assignee): ___________________
 * 작업단위(@task)  : TASK-C
 * 가이드 카드      : docs/tasks/TASK-C_조원_Book_DBUtil.md
 *
 * 능력단위요소 1 / 수행준거 1.2 + 1.3 (JDBC Driver / properties 분리)
 * 능력단위요소 3 / 수행준거 3.1 (Driver 로드 + Connection)
 * 체크리스트 1-5, 3-2
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 *
 * <ol>
 *   <li><code>getConnection()</code>:
 *     <ul>
 *       <li>① <code>Properties</code> 로 <code>config/db.properties</code> 로드</li>
 *       <li>② <code>Class.forName(driver)</code> — JDBC 4단계의 ① Driver Load</li>
 *       <li>③ <code>DriverManager.getConnection(url, user, password)</code> — JDBC 4단계의 ② Connection</li>
 *     </ul>
 *   </li>
 *   <li><code>close(AutoCloseable...)</code>:
 *     for-each 로 순회 + null 체크 + try-catch 로 close()</li>
 * </ol>
 *
 * <p>필요 import (추가):
 *   <code>java.io.FileInputStream</code>,
 *   <code>java.io.IOException</code>,
 *   <code>java.io.InputStream</code>,
 *   <code>java.sql.DriverManager</code>,
 *   <code>java.util.Properties</code>
 * </p>
 *
 * <p>⚠️ 비밀번호 하드코딩 금지 — 반드시 properties 파일 사용 (체크리스트 1-5)</p>
 */
public class DBUtil {

    /** 인스턴스화 방지 */
    private DBUtil() {}

    /**
     * MySQL Connection 을 획득해서 반환한다.
     * 호출 후 반드시 close() — try-with-resources 권장.
     */
    public static Connection getConnection() throws SQLException {
        // TODO 1) Properties 로 config/db.properties 로드
        // TODO 2) Class.forName(driver) — JDBC 4단계 ①
        // TODO 3) return DriverManager.getConnection(url, user, password); — JDBC 4단계 ②

        throw new UnsupportedOperationException(
                "DBUtil.getConnection() — 아직 구현되지 않았습니다.\n" +
                "  → src/book/DBUtil.java 의 TODO 를 채우세요.");
    }

    /**
     * AutoCloseable 자원 여러 개를 안전하게 닫는 헬퍼.
     *
     * <p>학생 TODO: 매개변수 배열을 순회하며 각 자원이 null 이 아니면 close().
     *    close() 도중 예외는 무시.</p>
     */
    public static void close(AutoCloseable... closeables) {
        // TODO: for-each + null 체크 + try { c.close(); } catch (Exception ignored) {}
    }
}
