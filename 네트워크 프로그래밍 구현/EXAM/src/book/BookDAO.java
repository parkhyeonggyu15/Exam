package book;

import java.sql.SQLException;
import java.util.List;

/**
 * Book_tbl DAO (☆ 학생 구현)
 *
 * <pre>
 * 담당자(@assignee): ___________________
 * 작업단위(@task)  : TASK-D
 * 가이드 카드      : docs/tasks/TASK-D_조원_BookDAO.md
 * 선행 조건        : TASK-C (Book + DBUtil) 완료 후 시작
 *
 * 능력단위요소 3 / 수행준거 3.1 + 3.2 + 3.3
 * 체크리스트 3-3 (JDBC 4단계), 3-4 (PreparedStatement), 3-5 (UI 연동)
 * </pre>
 *
 * <p><b>학생 구현 가이드 (정답 코드 ✗ — 가이드만)</b></p>
 *
 * <ul>
 *   <li>4개 메서드(<code>insert / selectAll / update / delete</code>) 모두 구현</li>
 *   <li>반드시 <b>PreparedStatement</b> + <b>?</b> 파라미터 바인딩 사용 (체크리스트 3-4 — SQL Injection 방어)</li>
 *   <li>Connection 은 <code>DBUtil.getConnection()</code> 으로 획득, <b>try-with-resources</b> 권장</li>
 *   <li>SQLException 은 그대로 throws 처리</li>
 * </ul>
 *
 * <p>JDBC 4단계 (체크리스트 3-3 — 절대 빠뜨리지 말 것):
 *   ① Driver Load (DBUtil 에서 처리됨) →
 *   ② Connection 획득 →
 *   ③ PreparedStatement 작성 →
 *   ④ executeUpdate() / executeQuery() + ResultSet 처리
 * </p>
 *
 * <p>필요 import (추가):
 *   <code>java.sql.Connection</code>,
 *   <code>java.sql.PreparedStatement</code>,
 *   <code>java.sql.ResultSet</code>,
 *   <code>java.util.ArrayList</code>
 * </p>
 *
 * <p>📌 SQL 문은 이미 상수로 선언되어 있음 — 그대로 사용 (직접 작성 금지).</p>
 */
public class BookDAO {

    private static final String SQL_INSERT =
            "INSERT INTO Book_tbl (Book_code, Classification_Id, author, name, publisher, isreserve) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL =
            "SELECT Book_code, Classification_Id, author, name, publisher, isreserve " +
            "FROM Book_tbl " +
            "ORDER BY Book_code";

    private static final String SQL_UPDATE =
            "UPDATE Book_tbl " +
            "   SET Classification_Id = ?, author = ?, name = ?, publisher = ?, isreserve = ? " +
            " WHERE Book_code = ?";

    private static final String SQL_DELETE =
            "DELETE FROM Book_tbl WHERE Book_code = ?";

    /**
     * 도서 1건 등록.
     *
     * <p>학생 TODO: try-with-resources 로 Connection / PreparedStatement 획득 →
     *    setString / setObject 로 6개 파라미터 바인딩 → executeUpdate() 반환.</p>
     */
    public int insert(Book book) throws SQLException {
        // TODO: PreparedStatement 로 SQL_INSERT 실행
        throw new UnsupportedOperationException("BookDAO.insert() — TODO: 구현하세요.");
    }

    /**
     * 전체 도서 조회.
     *
     * <p>학생 TODO: try-with-resources 로 Connection / PreparedStatement / ResultSet 획득 →
     *    rs.next() 루프 → Book 객체 채워서 List 에 추가.</p>
     *
     * <p>주의: <code>Classification_Id</code> 는 null 가능 컬럼.
     *    <code>rs.getInt(...)</code> 후 <code>rs.wasNull()</code> 로 null 체크 필요.</p>
     */
    public List<Book> selectAll() throws SQLException {
        // TODO: PreparedStatement 로 SQL_SELECT_ALL 실행 → ResultSet 순회 → Book List 반환
        throw new UnsupportedOperationException("BookDAO.selectAll() — TODO: 구현하세요.");
    }

    /**
     * 도서 1건 수정 (Book_code 기준).
     *
     * <p>학생 TODO: SQL_UPDATE 실행. 마지막 파라미터(?)는 WHERE 의 Book_code.</p>
     */
    public int update(Book book) throws SQLException {
        // TODO: PreparedStatement 로 SQL_UPDATE 실행
        throw new UnsupportedOperationException("BookDAO.update() — TODO: 구현하세요.");
    }

    /**
     * 도서 1건 삭제.
     *
     * <p>학생 TODO: SQL_DELETE 실행. ?에 bookCode 바인딩.</p>
     */
    public int delete(String bookCode) throws SQLException {
        // TODO: PreparedStatement 로 SQL_DELETE 실행
        throw new UnsupportedOperationException("BookDAO.delete() — TODO: 구현하세요.");
    }
}
