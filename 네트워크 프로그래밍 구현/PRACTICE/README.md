# 📋 [PRACTICE] 메모 공유 + 팀 메신저 — 사전 실습 프로젝트

> **NCS 능력단위** `2001020508_19v3` 네트워크 프로그래밍 구현 (2수준)
> 본 프로젝트는 **Ex 평가 도전 전 워밍업** 입니다.
> 같은 패턴(Socket + JDBC) 을 더 단순한 도메인(메모 1개 테이블) 으로 먼저 익히세요.

---

## 🎯 학습 흐름

```
PRACTICE (본 프로젝트)  →  PRACTICE_답 (정답 + 친절 설명)  →  Ex (실 평가)
   ☆ 직접 풀어보기            📖 막히면 정답 코드 비교         🎯 평가 도전
```

---

## 📊 Ex 와 PRACTICE 차이점

| 항목 | PRACTICE (본 프로젝트) | Ex (실 평가) |
|---|---|---|
| 도메인 | 메모 공유 (단순) | 도서관리 (복잡) |
| 테이블 | 1개 (`Memo_Tbl`)         | 6개 (`Book_tbl` 등) |
| 필드 수 | 4개 (id/title/content/created_at) | 6개 + FK + null 처리 |
| 채팅 | 팀 메신저 (단순) | 직원 채팅 (다대다) |
| 답안 힌트 | ❌ 가이드만 (정답 코드 ✗) → PRACTICE_답 별도 참조 | ❌ 가이드만 |
| 평가 / 점수 | 자가 학습 (체크리스트 100점 동일) | 실 평가 (100점) |

> 💡 본 PRACTICE 에서 **정답이 보고 싶으면 `../PRACTICE_답/`** 의 같은 위치 파일을 참고하세요.
> PRACTICE_답 에는 동일한 코드에 **친절한 한국어 설명 주석** 이 풍부하게 들어 있습니다.

---

## 🗂️ 프로젝트 구조

```
PRACTICE/
├── README.md                ← 이 파일
├── CHECKLIST.md             ← ✅ Ex 와 동일한 100점/15항목 체크리스트
├── .project / .classpath    ← Eclipse 메타 (Eclipse 프로젝트명: MemoApp_Practice)
├── build.bat / run-*.bat    ← Windows 빌드/실행
├── config/db.properties.sample  ← DB 접속정보 템플릿
├── db/schema.sql            ← MemoDB + Memo_Tbl
├── lib/README.md            ← MySQL JDBC jar 안내
├── docs/가이드.md           ← 통합 단계별 가이드
│
├── 🟦 src/                  ★ 학생 작업 영역 (정답 코드 ✗ — 가이드만)
│   ├── chat/
│   │   ├── MessengerServer.java
│   │   ├── MessengerHandler.java
│   │   └── MessengerClient.java
│   └── memo/
│       ├── Memo.java
│       ├── DBManager.java
│       └── MemoDAO.java
│
└── 🔒 provided/             수정 금지 — 완성 UI + 런처
    ├── chat/MessengerClientUI.java
    ├── memo/MemoManagementUI.java
    └── launcher/Main.java
```

---

## 🚀 빠른 시작

### 0. 사전 준비
- JDK 17+
- MySQL 8.x
- MySQL JDBC Driver → `lib/` 에 복사

### 1. Eclipse import
1. `File` → `Import...` → `Existing Projects into Workspace` → 본 `PRACTICE` 폴더
2. Package Explorer 에 `MemoApp_Practice` 확인
3. `lib/` jar 자동 인식 확인

### 2. DB 셋업
```cmd
mysql -uroot -p < db\schema.sql
copy config\db.properties.sample config\db.properties
```

### 3. 실행
```cmd
build.bat
run-server.bat        # 팀 메신저 서버 (포트 8888)
run-chat.bat          # 메신저 클라이언트 (여러 개)
run-memo.bat          # 메모 관리 (CRUD)
run-menu.bat          # 통합 런처
```

---

## 📝 학생 구현 순서 (권장)

| 순서 | 파일 | 시간 | 능력단위요소 |
|---|---|---|---|
| 1 | `src/memo/Memo.java`       | ~15분 | 3 (VO 캡슐화) |
| 2 | `src/memo/DBManager.java`   | ~20분 | 1 + 3 (Driver 로드 + Connection) |
| 3 | `src/memo/MemoDAO.java`    | ~30분 | 3 (CRUD + PreparedStatement) |
| 4 | `src/chat/MessengerServer.java` + `MessengerHandler.java` | ~30분 | 2 (Server + Thread) |
| 5 | `src/chat/MessengerClient.java` | ~20분 | 2 (Socket + I/O) |

> 💡 각 파일에는 **정답 코드 없이 가이드 주석만** 있습니다.
> 막히면 `../PRACTICE_답/` 의 같은 파일을 참조하세요.

자세한 단계별 절차 → [docs/가이드.md](docs/가이드.md)

---

## ✅ 자가 평가

작성 후 [CHECKLIST.md](CHECKLIST.md) 의 15항목으로 본인 점수 매기기.
- **60점 이상** → Ex 평가 진입 OK 🎉
- **60점 미만** → 어느 항목이 미충족인지 보고 다시 작성

---

## ⚠️ 주의

- 같은 워크스페이스에 Ex / SOLVED / PRACTICE / PRACTICE_답 다 import 가능 (이름 충돌 없음)
- 다만 메신저 서버는 **한 번에 하나만** 실행 (포트 충돌 — Ex 9999 / PRACTICE 8888)
- `db.properties` 와 `lib/*.jar` 는 절대 커밋 금지
