package memo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * [PRACTICE] Memo_Tbl DAO — ☆ 학생 구현
 *
 * <pre>
 * 능력단위요소 3 / 수행준거 3.1 + 3.2 + 3.3
 * 체크리스트 3-3 (JDBC 4단계), 3-4 (PreparedStatement), 3-5 (UI 연동)
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 *
 * <ul>
 *   <li>4개 메서드(<code>insert / selectAll / update / delete</code>) 모두 구현</li>
 *   <li>반드시 <b>PreparedStatement</b> + <b>?</b> 파라미터 바인딩 (SQL Injection 방어 — 체크리스트 3-4)</li>
 *   <li>Connection 은 <code>DBManager.getConnection()</code> 으로 획득, <b>try-with-resources</b> 권장</li>
 *   <li>SQLException 은 그대로 throws</li>
 * </ul>
 *
 * <p>JDBC 4단계 (체크리스트 3-3):
 *   ① Driver Load (DBManager 에서 처리됨) →
 *   ② Connection 획득 →
 *   ③ PreparedStatement 작성 →
 *   ④ executeUpdate() / executeQuery() + ResultSet 처리
 * </p>
 *
 * <p>필요 import 4개 추가:
 *   <code>java.sql.Connection</code>,
 *   <code>java.sql.PreparedStatement</code>,
 *   <code>java.sql.ResultSet</code>,
 *   <code>java.util.ArrayList</code>
 * </p>
 *
 * <p>📌 SQL 문은 이미 상수로 선언되어 있음 — 그대로 사용 (직접 작성 금지).</p>
 * <p>정답은 PRACTICE_답 참고.</p>
 */
public class MemoDAO {

    private static final String SQL_INSERT =
            "INSERT INTO Memo_Tbl (title, content) VALUES (?, ?)";

    private static final String SQL_SELECT_ALL =
            "SELECT memo_id, title, content, created_at " +
            "FROM Memo_Tbl ORDER BY memo_id DESC";

    private static final String SQL_UPDATE =
            "UPDATE Memo_Tbl SET title = ?, content = ? WHERE memo_id = ?";

    private static final String SQL_DELETE =
            "DELETE FROM Memo_Tbl WHERE memo_id = ?";

    /**
     * 메모 1건 등록.
     *
     * <p>학생 TODO: try-with-resources 로 Connection / PreparedStatement 획득 →
     *    setString 으로 2개 파라미터 바인딩 → executeUpdate() 반환.</p>
     */
    public int insert(Memo memo) throws SQLException {
//        // TODO: PreparedStatement 로 SQL_INSERT 실행
//        throw new UnsupportedOperationException("MemoDAO.insert() — TODO: 구현하세요.");
    try (Connection conn = DBManager.getConnection();
    		PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT);)
    {
    	pstmt.setString(1,memo.getTitle());
    	pstmt.setString(2,memo.getContent());
    	return pstmt.executeUpdate();
    }
    	
    }

    /**
     * 전체 메모 조회.
     *
     * <p>학생 TODO: try-with-resources 로 Connection / PreparedStatement / ResultSet 획득 →
     *    rs.next() 루프 → Memo 객체 채워서 List 에 추가.</p>
     */
    public List<Memo> selectAll() throws SQLException {
        // TODO: PreparedStatement 로 SQL_SELECT_ALL 실행 → ResultSet 순회 → Memo List 반환
    	try (Connection conn = DBManager.getConnection();
        		PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL);
    			ResultSet rs = pstmt.executeQuery();
    			)
    			
    	{
        	List<Memo> list = new ArrayList<>();
        	Memo dto = null;
        	while(rs.next()) {
        		Memo m = new Memo();
        		m.setMemoId(rs.getInt("memo_id"));
        		m.setTitle(rs.getString("title"));
        		m.setContent(rs.getString("content"));
        		m.setCreatedAt(rs.getTimestamp("created_at"));
        		list.add(m);
        	}
        	
        	return list;
        }
  
    }

    /**
     * 메모 1건 수정.
     *
     * <p>학생 TODO: SQL_UPDATE 실행. 파라미터 3개: title, content, memoId(WHERE).</p>
     */
    public int update(Memo memo) throws SQLException {
        // TODO: PreparedStatement 로 SQL_UPDATE 실행
    	try (Connection conn = DBManager.getConnection();PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE);)
        {
        	pstmt.setString(1,memo.getTitle());
        	pstmt.setString(2,memo.getContent());
        	pstmt.setInt(3,memo.getMemoId());
        	return pstmt.executeUpdate();
        }
    }

    /**
     * 메모 1건 삭제.
     *
     * <p>학생 TODO: SQL_DELETE 실행. setInt(1, memoId).</p>
     */
    public int delete(int memoId) throws SQLException {
        // TODO: PreparedStatement 로 SQL_DELETE 실행
    	try (Connection conn = DBManager.getConnection();PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE);)
        {
    		pstmt.setInt(1,memoId);
        	return pstmt.executeUpdate();
        }
    }
}
