# 🟡 문제 3. JDBC를 활용한 도서(Book) CRUD 구현 (가이드)

> 제공된 ERD/DDL 기반으로 `Book_tbl` 에 대한 **C / R / U / D** 4가지를 JDBC + Swing 으로 구현합니다.
> 학생이 구현해야 하는 파일은 모두 **`src/book/`** 폴더에 있습니다.
> **본 문서는 가이드만 제공합니다 — 정답 코드는 강사용 SOLVED 프로젝트 참고.**

---

## 📋 상세 요구사항

- 제공된 [db/schema.sql](../db/schema.sql) 기반 `Book_tbl` 에 대한 CRUD 를 JDBC 로 구현
- 제공된 Swing UI ([provided/book/BookManagementUI.java](../provided/book/BookManagementUI.java)) 이벤트 리스너에 연동 (UI 는 수정 불필요)
- `Book` (VO), `DBUtil` (Connection 헬퍼), `BookDAO` (CRUD) 3개 파일을 모두 구현
- **PreparedStatement** 로 SQL Injection 방어

---

## ✅ 채점 매핑 — [CHECKLIST.md](../CHECKLIST.md) 능력단위요소 3 (30점)

| 체크리스트 | 구현 위치 |
|---|---|
| **3-1** Book VO 캡슐화 (private 필드 + getter/setter) (6점) | [src/book/Book.java](../src/book/Book.java) |
| **3-2** Driver 로드 + Connection 획득 (6점) | [src/book/DBUtil.java](../src/book/DBUtil.java) |
| **3-3** JDBC 4단계 정확히 수행 (6점) | DBUtil (① + ②) + BookDAO (③ + ④) |
| **3-4** PreparedStatement (SQL Injection 방어) (6점) | BookDAO 모든 쿼리 |
| **3-5** UI ↔ JDBC 정상 연동 (6점) | provided UI 가 BookDAO 호출 |

---

## 🎯 구현 순서 권장 (단계별 흐름)

> 정답 코드는 **SOLVED 프로젝트** 참고. 본 가이드는 단계별 절차만 안내.

### Step 0 — `Book.java` (VO, 체크리스트 3-1)

`src/book/Book.java` 를 열고 채울 4가지:

| 항목 | 채울 내용 |
|---|---|
| (a) 필드 | `bookCode`(String, PK), `classificationId`(Integer, null 가능), `author`, `name`, `publisher`, `isReserve` 6개를 **모두 private** |
| (b) 생성자 | 전체 인자 생성자에서 `this.xxx = xxx` 로 6개 필드 초기화 |
| (c) getter/setter | 12개 본문을 채움 (시그니처는 변경 금지 — UI 가 호출) |
| (d) toString() | 모든 필드를 한 줄로 표시 |

> 💡 Eclipse 단축키: `Alt+Shift+S` → Generate Constructor / Generate Getters and Setters / Generate toString()

---

### Step 1 — `DBUtil.java` (Connection 헬퍼, 체크리스트 3-2)

`src/book/DBUtil.java` 의 두 메서드 구현 흐름:

#### `getConnection()` 흐름
1. **properties 파일 로딩** — `Properties` 객체 만들어 `config/db.properties` 를 `FileInputStream` 으로 읽기
2. `db.driver` / `db.url` / `db.user` / `db.password` 4개 키 꺼내기
3. **JDBC Driver 로드** — `Class.forName(driver)` (JDBC 4단계 - ①)
4. **Connection 획득** — `DriverManager.getConnection(url, user, password)` 반환 (JDBC 4단계 - ②)

#### `close(AutoCloseable...)` 흐름
- for-each 로 순회
- 각 자원이 null 이 아니면 `try { c.close(); } catch (Exception ignored) {}`

#### 필요한 import (5개 추가)
- `java.io.FileInputStream`
- `java.io.IOException`
- `java.io.InputStream`
- `java.sql.DriverManager`
- `java.util.Properties`

> ⚠️ **체크리스트 1-5** (보안): `db.properties` 는 `.gitignore` 로 제외, 코드에 비밀번호 하드코딩 금지.

---

### Step 2 — `BookDAO.java` (CRUD, 체크리스트 3-3 / 3-4 / 3-5)

`src/book/BookDAO.java` 의 4개 메서드 — 모두 **동일 패턴**:

#### 공통 패턴
1. `try (Connection conn = DBUtil.getConnection();` ← JDBC ②
2. `     PreparedStatement pstmt = conn.prepareStatement(SQL_XXX)) {` ← JDBC ③
3. `     pstmt.setXxx(idx, value);` (반복) ← 파라미터 바인딩
4. `     return pstmt.executeUpdate();` ← JDBC ④
5. `}` ← try-with-resources 가 close 자동 처리

#### Book_tbl 컬럼 매핑

| DB 컬럼 | Java 필드 | setXxx |
|---|---|---|
| `Book_code` (PK)        | `String  bookCode`         | `setString` |
| `Classification_Id` (FK)| `Integer classificationId` | `setObject(.., Types.INTEGER)` (null 가능) |
| `author`                | `String  author`           | `setString` |
| `name`                  | `String  name`             | `setString` |
| `publisher`             | `String  publisher`        | `setString` |
| `isreserve`             | `String  isReserve`        | `setString` ('Y'/'N') |

#### 각 메서드 흐름

| 메서드 | 사용할 SQL 상수 | 파라미터 개수 | 추가 처리 |
|---|---|---|---|
| `insert(Book)`     | `SQL_INSERT`     | 6 | — |
| `selectAll()`      | `SQL_SELECT_ALL` | 0 | `executeQuery()` + `rs.next()` 루프 → Book List 반환 |
| `update(Book)`     | `SQL_UPDATE`     | 6 (마지막은 WHERE 의 Book_code) | — |
| `delete(String)`   | `SQL_DELETE`     | 1 | — |

> 📌 SQL 문은 이미 `BookDAO` 안에 상수로 선언되어 있음 — **그대로 사용**.

#### selectAll 의 `Classification_Id` null 처리
- DB 의 `Classification_Id` 는 null 가능 컬럼
- `int cid = rs.getInt("Classification_Id");` 후
- `b.setClassificationId(rs.wasNull() ? null : cid);`

#### 필요한 import (4개 추가)
- `java.sql.Connection`
- `java.sql.PreparedStatement`
- `java.sql.ResultSet`
- `java.util.ArrayList`

---

## 🎬 시연 시나리오 (PPT 캡처용)

1. `run-book.bat` 실행 → 시작 시 자동 조회로 샘플 4건 표시 캡처 → **체크리스트 3-3 + 3-5 충족**
2. **추가**: `B999 / 1 / 테스트저자 / 테스트도서 / 테스트출판사 / N` → [추가] → 안내창 + 목록 갱신
3. **수정**: 행 클릭 → 폼 자동 채움 → 출판사 변경 → [수정] → 목록 갱신
4. **삭제**: 행 클릭 → [삭제] → 확인 → 목록에서 제거
5. **에러 처리**: 같은 도서코드로 [추가] 한 번 더 → 에러 다이얼로그 표시 캡처

---

## 🔒 SQL Injection 방어 (체크리스트 3-4)

❌ **금지** — 절대 이렇게 작성하지 말 것
- 문자열 연결(`+`) 로 사용자 입력을 SQL 에 끼워 넣기
- `Statement` + `executeUpdate(sql)` 사용

✅ **정답 방향**
- SQL 안에는 `?` 만 두기
- `PreparedStatement.setXxx(idx, value)` 로 파라미터 바인딩
- (이렇게만 해도 SQL Injection 방어가 자동으로 적용됨)

---

## 📌 자주 하는 실수

- ❌ `Book` 의 필드를 `public` 으로 선언 → 캡슐화 위반, **체크리스트 3-1 감점**
- ❌ `DBUtil` 에서 비밀번호 하드코딩 → **체크리스트 1-5 감점**
- ❌ `Statement` 사용 → **체크리스트 3-4 0점**, 반드시 `PreparedStatement`
- ❌ Connection / ResultSet `close()` 누락 → 커넥션 누수
- ❌ `Classification_Id` 가 null 인 행을 `getInt` 로만 읽기 → `wasNull()` 체크 필요
- ❌ `provided/book/BookManagementUI.java` 를 수정 → 평가 0점 처리

---

## 🧪 빠른 테스트 체크리스트

- [ ] `Book` 의 모든 필드가 `private` → **체크리스트 3-1 충족**
- [ ] `DBUtil.getConnection()` 가 properties 를 읽고 Driver 를 로드 → **체크리스트 3-2 + 1-5 충족**
- [ ] 시작 시 샘플 도서 4건 자동 표시 → **체크리스트 3-3 + 3-5 충족**
- [ ] 추가/수정/삭제 한 사이클 정상 동작 → **체크리스트 3-3 + 3-4 + 3-5 충족**
- [ ] DB 가 꺼진 상태에서 실행 → 친절한 에러 메시지

---

## 🔗 관련 자료

- **체크리스트 (배점)**: [CHECKLIST.md](../CHECKLIST.md) — 능력단위요소 3
- **작업 카드**: [tasks/TASK-C](tasks/TASK-C_조원_Book_DBUtil.md) + [tasks/TASK-D](tasks/TASK-D_조원_BookDAO.md)
- **정답 코드**: 강사용 `SOLVED/src/book/` 참고
