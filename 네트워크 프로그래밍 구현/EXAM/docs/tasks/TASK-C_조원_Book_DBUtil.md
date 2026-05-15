# 🟡 TASK-C — 도서 VO + DB 연결 (Book + DBUtil)

> **담당자**: ___________________
> **추정 시간**: ~1시간
> **선행 조건**: 조장의 [TASK-00](TASK-00_조장_환경통합.md) Day 1 완료 (DB / JDBC jar 준비)
> **의존성**: 독립 (TASK-D 의 선행 조건이 됨 — TASK-D 담당자가 본인을 기다림)

---

## 🎯 미션

도서 1행을 표현하는 **VO (Book)** 와, MySQL Connection 을 만들어주는 **유틸 (DBUtil)** 을 구현한다.
이 두 클래스는 TASK-D 의 BookDAO 가 사용하므로 **빨리 끝낼수록 팀 전체가 빨라진다**.

---

## 📂 담당 파일

| 파일 | 역할 |
|---|---|
| [src/book/Book.java](../../src/book/Book.java) | Book_tbl 한 행을 담는 VO (필드 + getter/setter) |
| [src/book/DBUtil.java](../../src/book/DBUtil.java) | properties 로드 + Driver 로드 + Connection 획득 |

> ⚠️ `provided/book/BookManagementUI.java` 와 `src/book/BookDAO.java` (TASK-D) 는 절대 수정 금지!

---

## ✅ 채점 매핑 — [CHECKLIST.md](../../CHECKLIST.md)

| 체크리스트 | 어디서 충족? | 배점 |
|---|---|---|
| **1-3** JDBC Driver 빌드패스 인식 | `lib/` jar + `.classpath` | 4점 (조장과 공유) |
| **1-5** properties 분리 | `DBUtil.getConnection()` 의 `Properties.load(...)` | 5점 (조장과 공유) |
| **3-1** Book VO 캡슐화 | `Book.java` 6개 private 필드 + getter/setter | 6점 |
| **3-2** Driver 로드 + Connection 획득 | `DBUtil.getConnection()` 의 `Class.forName(driver)` + `DriverManager.getConnection(...)` | 6점 |

> 본 TASK 가 능력단위요소 1 의 일부 + 능력단위요소 3 의 12점 직접 담당.

---

## 🧭 작업 흐름

### Step 1 — 본인 브랜치 생성
```bash
git checkout main
git pull origin main
git checkout -b feature/task-c
```

### Step 2 — 두 파일 헤더에 본인 이름 적기

### Step 3 — `Book.java` 구현

[문제3_JDBC_CRUD.md → Step 0](../문제3_JDBC_CRUD.md#step-0--bookjava-vo-채점기준-11) 그대로:
1. **private 필드 6개** 선언 (반드시 private!)
2. **전체 인자 생성자** 본문 (`this.xxx = xxx`)
3. **getter / setter 12개** 본문
4. **toString()** 모든 필드 표시

> 💡 Eclipse 단축키 활용:
>   - `Alt + Shift + S` → Generate Constructor using Fields...
>   - `Alt + Shift + S` → Generate Getters and Setters...
>   - `Alt + Shift + S` → Generate toString()

### Step 4 — `DBUtil.java` 구현

[문제3_JDBC_CRUD.md → Step 1](../문제3_JDBC_CRUD.md#step-1--dbutiljava-connection-헬퍼-채점기준-12--) 그대로:

#### `getConnection()` 흐름
1. `Properties prop = new Properties();`
2. `try (InputStream in = new FileInputStream("config/db.properties")) { prop.load(in); }`
3. `Class.forName(prop.getProperty("db.driver"));`
4. `return DriverManager.getConnection(url, user, password);`

#### `close(AutoCloseable...)` 흐름
- for-each 로 순회하며 null 체크 + try { c.close(); } catch (Exception ignored) {}

> 필요한 import 5개:
> `java.io.FileInputStream`, `java.io.IOException`, `java.io.InputStream`,
> `java.sql.DriverManager`, `java.util.Properties`

### Step 5 — 단위 테스트

#### 테스트 A: Book 동작 확인
간단한 main 으로 직접 테스트 (테스트 파일은 commit 하지 마세요):
```java
public static void main(String[] args) {
    Book b = new Book("B999", 1, "테스트", "테스트도서", "출판사", "N");
    System.out.println(b);  // toString 정상 표시되는지
    System.out.println(b.getBookCode());  // "B999"
}
```

#### 테스트 B: DBUtil 동작 확인
```java
public static void main(String[] args) throws Exception {
    try (Connection conn = DBUtil.getConnection()) {
        System.out.println("연결 성공: " + conn);
        // SELECT 1 같은 간단한 쿼리도 가능
    }
}
```
- 정상: `연결 성공: com.mysql.cj.jdbc.ConnectionImpl@...`
- 실패: 에러 메시지 보고 해결 (db.properties 경로, jar, 비밀번호 점검)

### Step 6 — 커밋 + 푸시
```bash
git add src/book/Book.java src/book/DBUtil.java
git commit -m "feat: Book VO + DBUtil getConnection 구현"
git push origin feature/task-c
```

### Step 7 — PR 생성
- 제목: `[TASK-C] Book + DBUtil 구현`
- PR 템플릿 채우기 (채점기준 02, 05, 11, 12 체크)
- Reviewer: 조장
- **PR 본문에 "TASK-D 담당자에게 알림: 이제 BookDAO 작업 시작 가능" 멘션 추가**

### Step 8 — 보고서 작성

---

## 🧾 Definition of Done

- [ ] 두 파일 헤더 `@assignee` 본인 이름
- [ ] `build.bat` 컴파일 OK
- [ ] `Book.java` 의 모든 필드가 `private` 으로 선언됨
- [ ] `DBUtil.getConnection()` 가 properties 파일에서 비번을 읽음 (코드에 하드코딩 X)
- [ ] DBUtil 단위 테스트로 실제 MySQL 연결 성공 확인
- [ ] PR 생성 + 조장 review 완료 + TASK-D 담당자 통지
- [ ] 본인 reports/<이름>.md 작성

---

## 📌 자주 하는 실수

- ❌ `Book` 필드를 `public` 으로 선언 → **채점기준 11 감점** (캡슐화 위반)
- ❌ `DBUtil` 에 비밀번호 하드코딩 (`"dbconn1234!"` 직접 입력) → **채점기준 05 감점**
- ❌ `Class.forName` 누락 → 옛날 JDBC 1.0 시절은 자동이지만, 평가는 명시 요구
- ❌ properties 파일 경로를 절대경로로 (`C:\...`) 작성 → 다른 사람 PC 에서 안 됨
- ❌ Connection 을 닫지 않고 main 종료 → 메모리 누수 (try-with-resources 사용)

---

## 🆘 막혔을 때

| 증상 | 해결 |
|---|---|
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | lib/ 폴더에 jar 없음 → 조장에게 jar 받기 |
| `Communications link failure` | MySQL 안 떠 있음 → `services.msc` → MySQL80 시작 |
| `Access denied for user 'dbconn'` | 비밀번호 틀림 → 조장과 db.properties 비교 |
| `Unknown database 'BookDB'` | schema.sql 실행 안 됨 → 조장에게 DB 생성 요청 |
| `db.properties (지정된 파일을 찾을 수 없음)` | 실행 위치 문제 → IDE 의 working directory 가 `Ex/` 인지 확인 |

---

## 🔗 관련 문서

- [문제3_JDBC_CRUD.md](../문제3_JDBC_CRUD.md) — 정답 스니펫
- [TASK-D](TASK-D_조원_BookDAO.md) — 본인 작업 후 시작될 작업
- [WORKFLOW.md](../WORKFLOW.md)
