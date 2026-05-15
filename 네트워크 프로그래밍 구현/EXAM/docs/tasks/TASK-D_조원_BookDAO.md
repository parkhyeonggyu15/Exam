# 🟡 TASK-D — 도서 DAO (BookDAO CRUD)

> **담당자**: ___________________
> **추정 시간**: ~1.5시간
> **선행 조건**: [TASK-C](TASK-C_조원_Book_DBUtil.md) **완료 후 시작 가능** (Book + DBUtil 필요)
> **의존성**: TASK-C → TASK-D

---

## 🎯 미션

`Book_tbl` 에 대한 **CRUD 4개 메서드** 를 PreparedStatement 로 구현한다.
provided UI 가 자동으로 본인 코드를 호출하므로, 완성되면 즉시 화면에서 추가/수정/삭제가 동작한다.

---

## 📂 담당 파일

| 파일 | 역할 |
|---|---|
| [src/book/BookDAO.java](../../src/book/BookDAO.java) | `insert / selectAll / update / delete` |

> ⚠️ Book.java, DBUtil.java (TASK-C), provided/book/BookManagementUI.java 는 절대 수정 금지!

---

## ✅ 채점 매핑 — [CHECKLIST.md](../../CHECKLIST.md) 능력단위요소 3

| 체크리스트 | 어디서 충족? | 배점 |
|---|---|---|
| **3-3** JDBC 4단계 (③ + ④) | PreparedStatement (③) + executeUpdate/executeQuery + ResultSet (④) | 6점 (TASK-C 와 공유) |
| **3-4** PreparedStatement (SQL Injection 방어) | 모든 쿼리에 `?` 파라미터 + setXxx | 6점 |
| **3-5** Swing UI ↔ JDBC 정상 연동 | provided UI 가 BookDAO 호출 → 화면 반영 | 6점 |

> 본 TASK 가 능력단위요소 3 의 30점 중 **12점 직접** + 3-3 공유 기여.

---

## 🧭 작업 흐름

### Step 1 — TASK-C 완료 대기 + 본인 브랜치 생성
TASK-C 가 main 에 merge 된 후 시작:
```bash
git checkout main
git pull origin main         # TASK-C 의 Book + DBUtil 받기
git checkout -b feature/task-d
```

### Step 2 — 파일 헤더에 본인 이름 적기

### Step 3 — 코드 구현

[문제3_JDBC_CRUD.md → Step 2](../문제3_JDBC_CRUD.md#step-2--bookdaojava-crud-채점기준-11121314) 그대로:

#### 4개 메서드 패턴
```java
public int insert(Book book) throws SQLException {
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT)) {
        pstmt.setString(1, book.getBookCode());
        pstmt.setObject(2, book.getClassificationId(), java.sql.Types.INTEGER);
        // ... 나머지 setXxx
        return pstmt.executeUpdate();
    }
}
```

> SQL 상수는 이미 BookDAO 안에 선언되어 있음 — 그대로 사용.

#### Classification_Id 주의사항
- DB 의 `Classification_Id` 는 **null 가능** 컬럼
- `pstmt.setObject(idx, value, java.sql.Types.INTEGER)` 사용 (null 도 OK)
- ResultSet 읽을 때는:
  ```java
  int cid = rs.getInt("Classification_Id");
  b.setClassificationId(rs.wasNull() ? null : cid);
  ```

### Step 4 — 단위 테스트 (UI 와 함께)

1. `build.bat`
2. `run-book.bat` 실행 → 도서관리 UI 뜸
3. 시작 시 자동 조회로 샘플 4건 표시되는지 확인 (selectAll 동작)
4. **추가**: `B999 / 1 / 테스트저자 / 테스트도서 / 테스트출판사 / N` → [추가] → 안내창 + 목록에 추가
5. **수정**: 행 클릭 → 폼 채워짐 → 출판사 변경 → [수정] → 목록 반영
6. **삭제**: 행 클릭 → [삭제] → 확인 → 목록에서 제거
7. **에러**: 같은 도서코드로 [추가] 한 번 더 → 에러 다이얼로그 (PK 중복)

### Step 5 — 커밋 + 푸시
```bash
git add src/book/BookDAO.java
git commit -m "feat: BookDAO CRUD 4종 PreparedStatement 구현"
git push origin feature/task-d
```

### Step 6 — PR 생성
- 제목: `[TASK-D] BookDAO CRUD 구현`
- PR 템플릿 채우기 (채점기준 11~15 체크)
- Reviewer: 조장
- 시연 캡처 4장 (조회/추가/수정/삭제) PR 본문에 첨부 권장

### Step 7 — 보고서 작성

---

## 🧾 Definition of Done

- [ ] 파일 헤더 `@assignee` 본인 이름
- [ ] `build.bat` 컴파일 OK
- [ ] 4개 메서드 모두 PreparedStatement + `?` 사용 (Statement 사용 X)
- [ ] `run-book.bat` 으로 추가/조회/수정/삭제 한 사이클 정상 동작
- [ ] PK 중복 시 SQLException 다이얼로그 정상 표시
- [ ] PR 생성 + 시연 캡처 4장 첨부 + 조장 review 완료
- [ ] 본인 reports/<이름>.md 작성

---

## 📌 자주 하는 실수

- ❌ `Statement stmt = conn.createStatement()` 사용 → **채점기준 14 0점**
- ❌ SQL 문자열에 `+` 로 사용자 입력 연결 → **채점기준 14 0점** (SQL Injection)
- ❌ `Classification_Id` 가 null 인 행을 `getInt` 로만 읽기 → 0 으로 표시됨, `wasNull()` 체크 필요
- ❌ Connection 을 클래스 필드로 보유 + 한 번만 열고 계속 사용 → 끊어지면 모든 쿼리 실패
- ❌ try-with-resources 안 쓰고 finally 에서 일일이 close → 코드 길어지고 누락 위험

---

## 🆘 막혔을 때

| 증상 | 해결 |
|---|---|
| `Duplicate entry 'B001' for key 'PRIMARY'` | PK 중복 — 다른 도서코드로 추가 |
| `Cannot add or update a child row: a foreign key constraint fails` | Classification_Id 가 Classification_Tbl 에 없는 값 (1~4 만 사용) |
| 추가는 되는데 목록에 안 나타남 | `doRefresh()` 호출 누락 가능 — UI 코드 보지 말고 본인 selectAll 가 정상 반환하는지 확인 |
| `BookDAO.getConnection()` UnsupportedOperationException | TASK-C 의 DBUtil 미완성 → TASK-C 담당자에게 알림 |

---

## 🔗 관련 문서

- [문제3_JDBC_CRUD.md](../문제3_JDBC_CRUD.md) — 정답 스니펫
- [TASK-C](TASK-C_조원_Book_DBUtil.md) — 선행 작업
- [WORKFLOW.md](../WORKFLOW.md)
