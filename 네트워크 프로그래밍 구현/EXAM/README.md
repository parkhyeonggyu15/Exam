# 📚 네트워크 프로그래밍 구현 - 평가 실습 프로젝트

> **NCS 능력단위** `2001020508_19v3` 네트워크 프로그래밍 구현 (2수준)
> **교과목** 공공데이터 기반 객체지향 프로그래밍

학생은 **`src/` 폴더 안의 파일들만 구현하면 됩니다.**
Swing UI 와 런처는 **`provided/` 폴더에 완성된 형태로 제공**되며 **수정 금지** 입니다.

> 🔑 **한 줄 요약** :
> - ✅ 학생 작업 → **`src/`** 만 건드림
> - ❌ 절대 수정 금지 → **`provided/`** 폴더 전체

---

## 🤝 협업 모드 — 조장 1명 + 조원 N명

본 프로젝트는 **조 단위 협업** 으로 진행합니다. 시작 전 다음 두 문서를 반드시 읽으세요:

| 문서 | 내용 |
|---|---|
| 📘 [docs/ROLES.md](docs/ROLES.md) | 역할 분담표 + 팀 규모별 매트릭스 (2~5명) |
| 📗 [docs/WORKFLOW.md](docs/WORKFLOW.md) | Git 브랜치 / 커밋 / PR 흐름 + 충돌 방지 규칙 |

### 작업 분할 (5개 TASK)

| TASK | 카드 | 담당 영역 |
|---|---|---|
| 👑 **TASK-00** | [환경통합](docs/tasks/TASK-00_조장_환경통합.md) | 조장: DB / GitHub / 통합 / 시연 |
| 🟢 **TASK-A** | [ChatServer + Handler](docs/tasks/TASK-A_조원_ChatServer_Handler.md) | 채팅 서버측 |
| 🟢 **TASK-B** | [ChatClient](docs/tasks/TASK-B_조원_ChatClient.md) | 채팅 클라이언트측 |
| 🟡 **TASK-C** | [Book + DBUtil](docs/tasks/TASK-C_조원_Book_DBUtil.md) | 도서 VO + DB 연결 |
| 🟡 **TASK-D** | [BookDAO](docs/tasks/TASK-D_조원_BookDAO.md) | 도서 CRUD (TASK-C 후 시작) |

> 각 카드에는 **Definition of Done**, **자가 점검 체크리스트**, **자주 하는 실수**, **막혔을 때 대응** 이 들어 있습니다.

### 본인 보고서 작성

각 팀원은 [reports/_TEMPLATE.md](reports/_TEMPLATE.md) 를 자기 이름으로 복사하여 작성합니다.
PPT 만들 때 이 보고서들을 합치면 끝.

### 시작 안내

- **조장이세요?** → [docs/tasks/TASK-00_조장_환경통합.md](docs/tasks/TASK-00_조장_환경통합.md) 부터
- **조원이세요?** → 조장이 배정한 TASK 카드를 정독 + 본인 feature 브랜치 생성

---

## 🗂️ 프로젝트 구조

```
Ex/
├── README.md                      ← 이 파일
├── CHECKLIST.md                   ← ✅ 평가 체크리스트 (100점 만점, 15항목)
├── .gitignore                     ← Git 제외 목록 (lib jar, db.properties)
├── .project / .classpath          ← Eclipse 메타파일 (그대로 두기)
├── build.bat / run-*.bat          ← Windows 빌드/실행 스크립트
├── config/
│   ├── db.properties.sample       ← DB 접속정보 템플릿 (★ 복사해서 db.properties로 작성)
│   └── db.properties              ← (Git 제외) 본인 DB 비밀번호 입력
├── db/
│   └── schema.sql                 ← MySQL DDL (테이블 6개)
├── lib/
│   └── README.md                  ← mysql-connector-j-x.x.x.jar 다운로드 안내
├── docs/
│   ├── ROLES.md                   ← 🤝 역할 분담표 (조장/조원)
│   ├── WORKFLOW.md                ← 🔁 Git 협업 흐름
│   ├── 문제1_환경구축.md
│   ├── 문제2_소켓채팅.md
│   ├── 문제3_JDBC_CRUD.md
│   └── tasks/                     ← 🃏 5개 작업 카드 (TASK-00 ~ TASK-D)
├── reports/                       ← 📝 개인 작업 보고서 (각자 작성)
│   ├── _TEMPLATE.md
│   └── README.md
├── .github/
│   └── PULL_REQUEST_TEMPLATE.md   ← PR 작성 시 자동으로 채점기준 체크리스트 표시
│
├── 🟦 src/                        ┌──────────────────────────────────────┐
│   │                              │  ★ 학생 작업 영역 — 여기만 수정!      │
│   ├── chat/                      │                                      │
│   │   ├── ChatServer.java        │ TODO: ServerSocket bind / accept 루프 │
│   │   ├── ChatHandler.java       │ TODO: Thread + 브로드캐스트            │
│   │   └── ChatClient.java        │ TODO: Socket connect / I/O Stream    │
│   └── book/                      │                                      │
│       ├── Book.java              │ TODO: 6개 필드 + getter/setter 본문   │
│       ├── DBUtil.java            │ TODO: properties 로드 + getConnection│
│       └── BookDAO.java           │ TODO: JDBC CRUD 4종 (PreparedStatement)│
│                                  └──────────────────────────────────────┘
└── 🔒 provided/                   ┌──────────────────────────────────────┐
    │                              │  수정 금지 — 채점/시연용 고정 코드     │
    ├── chat/                      │                                      │
    │   └── ChatClientUI.java      │ 채팅 Swing UI 완성본                  │
    ├── book/                      │                                      │
    │   └── BookManagementUI.java  │ 도서관리 Swing UI 완성본              │
    └── launcher/                  │                                      │
        └── Main.java              │ 통합 런처 진입점                       │
                                   └──────────────────────────────────────┘
```

---

## 🚀 빠른 시작 (Windows)

> 본 프로젝트는 **순수 Java 프로젝트**입니다 (Spring Boot / JSP 아님, Maven / Gradle 아님).
> Eclipse 프로젝트 메타파일(`.project`, `.classpath`, `.settings/`)이 포함되어 바로 import 가능합니다.

### 0. 사전 준비
- **JDK 17+** 설치 후 `java -version` 확인
- **MySQL 8.x** 설치 후 root 로그인 가능 확인
- **MySQL JDBC Driver** 다운로드: <https://dev.mysql.com/downloads/connector/j/>
  → `mysql-connector-j-9.x.x.jar` 파일을 `Ex/lib/` 폴더에 넣기

### 1-A. Eclipse 로 import 하는 경우 (권장)
1. Eclipse 실행 → `File` → `Import...` → `General` → `Existing Projects into Workspace`
2. `Select root directory` → 본 `Ex` 폴더 선택 → `Finish`
3. 좌측 Package Explorer 에 `NetworkProgramming_Ex` 표시 확인
4. Source Folder 가 **두 개** (`src`, `provided`) 등록되어 있는지 확인
   - Package Explorer 에서 두 폴더가 모두 src 폴더 아이콘(📂) 으로 표시되면 정상
5. **lib 폴더의 jar 파일 추가** :
   - `Ex/lib/mysql-connector-j-9.1.0.jar` 가 정확한 이름이면 `.classpath` 가 자동 인식
   - 다른 버전이면 → 프로젝트 우클릭 → `Build Path` → `Configure Build Path...` → `Libraries` 탭 → `Classpath` 선택 → `Add JARs...` → `lib` 안의 jar 선택
6. `provided/launcher/Main.java` 우클릭 → `Run As` → `Java Application`

### 1-B. 명령줄로 빌드/실행하는 경우
아래 「3. 빌드」/「4. 실행」 단계 참고.

### 2. DB 준비
```bash
mysql -uroot -p < db/schema.sql
```
또는 MySQL Workbench로 `db/schema.sql` 실행.

### 3. DB 접속 정보 작성
```bash
copy config\db.properties.sample config\db.properties
notepad config\db.properties
```
본인 비밀번호 입력 후 저장 (**git에 커밋 금지** — `.gitignore`로 제외됨).

### 4. 빌드 (Eclipse 외부에서 — 명령줄 사용 시)
```bash
build.bat
```
> Eclipse 사용자는 자동 빌드되므로 이 단계 건너뛰기

### 5. 실행
```bash
run-server.bat        # 채팅 서버 (포트 9999)
run-chat.bat          # 채팅 클라이언트 (여러 개 띄워서 시연)
run-book.bat          # 도서관리 (JDBC CRUD)
run-menu.bat          # 통합 런처 (선택 메뉴)
```
> Eclipse 사용자는 각 main 클래스(`launcher.Main`, `chat.ChatServer`, `chat.ChatClientUI`, `book.BookManagementUI`)에서 우클릭 → Run As → Java Application

---

## 📝 구현해야 할 TODO 목록

### 🟦 능력단위요소 1 (20점) — 환경 구축 ([문제1 가이드](docs/문제1_환경구축.md))
- [ ] JDK / MySQL / JDBC Driver 설치 + GitHub repo + `.gitignore` + `db.properties` 분리
- [ ] (코드는 학생이 짜는 부분 없음 — 환경 결과물만 제출)

### 🟢 능력단위요소 2 (30점) — Socket 채팅 ([문제2 가이드](docs/문제2_소켓채팅.md))
- [ ] `src/chat/ChatServer.java` — `ServerSocket(9999)` 바인딩 + `accept()` 루프 + `broadcast()`
- [ ] `src/chat/ChatHandler.java` — 스트림 초기화 + `run()` 수신 루프 + `send()`
- [ ] `src/chat/ChatClient.java` — `connect()` / `send()` / `disconnect()`

### 🟡 능력단위요소 3 (30점) — JDBC Book CRUD ([문제3 가이드](docs/문제3_JDBC_CRUD.md))
- [ ] `src/book/Book.java` — VO (6개 필드 + 생성자 + getter/setter + `toString()`)
- [ ] `src/book/DBUtil.java` — `db.properties` 로드 + Driver 로드 + Connection 획득
- [ ] `src/book/BookDAO.java` — `insert / selectAll / update / delete` 4개 (PreparedStatement)

> 각 파일의 `// TODO:` 주석에 **구현 가이드(정답 코드 ✗)** 가 적혀 있습니다.
> `provided/` 폴더의 어떤 파일도 수정하지 마세요.
> 정답 코드는 강사용 **`SOLVED/`** 프로젝트에 별도 보관됩니다.

---

## 🧾 평가 체크리스트 (100점 만점)

본 프로젝트의 평가는 [**CHECKLIST.md**](CHECKLIST.md) 의 **15개 항목** 으로 이루어집니다.
각 항목은 능력단위요소의 **수행준거**에 근거하여 작성되었으며, 항목별 배점이 적용됩니다.

| 능력단위요소 | 항목 수 | 배점 |
|---|---:|---:|
| 1. 개발환경 분석하기 | 5  | **30점** |
| 2. 기능 구현하기     | 10 | **70점** |
| **합계** | **15** | **100점** |

> 📋 자세한 항목 / 배점 / 수행준거 매핑은 [CHECKLIST.md](CHECKLIST.md) 참조
> 🎯 합격 기준: 60점 이상 (100점 만점의 60%)

---

## 📤 최종 제출
1. GitHub Repository URL
2. `db/schema.sql` 파일
3. 본 프로젝트 전체 (`Ex/`) 를 PPT에 캡처와 함께 정리
4. 시연 동영상(채팅 다중접속 + CRUD 한 사이클)

---

## ⚠️ 주의사항
- `config/db.properties` 와 `lib/*.jar` 는 절대 커밋 금지 (`.gitignore` 확인)
- 비밀번호 하드코딩 금지 → `DBUtil` 에서 반드시 properties 파일 사용 (체크리스트 1-5)
- `BookDAO` 에서 SQL을 `Statement` + 문자열 연결로 작성하면 **체크리스트 3-4 0점**
- `provided/` 폴더의 어떤 파일도 수정하지 말 것 (수정 시 채점 0점)
