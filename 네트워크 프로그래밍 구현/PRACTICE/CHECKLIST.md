# ✅ PRACTICE 평가 체크리스트 — 총 100점

> **NCS 능력단위** `2001020508_19v3` 네트워크 프로그래밍 구현 (2수준)
> 본 체크리스트는 학습모듈의 **능력단위요소별 수행준거** 에 근거하여 작성되었습니다.
> Ex 평가 프로젝트와 **동일한 100점 / 15항목** 구조이며,
> 클래스명만 PRACTICE(메모 공유 + 메신저) 컨텍스트로 매핑됩니다.

---

## 📊 배점 요약

| 능력단위요소 | 항목 수 | 배점 |
|---|---:|---:|
| 1. 개발환경 분석하기 | 5  | **30점** |
| 2. 기능 구현하기     | 10 | **70점** |
| **합계** | **15** | **100점** |

---

## 🟦 능력단위요소 1. 개발환경 분석하기 (30점)

### 📜 수행준거
- **1.1** 개발방법 기준에 따라서 네트워크 프로그래밍 구현을 위한 H/W (PC, workstation, server 등) 및 S/W (Unix, windows, IOS 등) 개발환경을 구축할 수 있다.
- **1.2** 네트워크 개발환경의 프로그래밍 구현을 위하여 표준개발도구 (commands, .net framework control, eclipse 등) 을 사용할 수 있다.
- **1.3** 개발방법론에 따라서 네트워크 프로그래밍 구현을 위한 타겟시스템 형상(Configuration) 을 수정하여 보완할 수 있다.

### 🧾 체크리스트

```
1) □ JDK 설치 및 PATH 환경변수가 정상 구성되어 있는가 (5점)
2) □ MySQL MemoDB 및 memouser 원격 접속 계정이 정상 구축되어 있는가 (6점)
3) □ JDBC Driver 가 빌드패스에 정상 등록되어 있는가 (6점)
4) □ GitHub Repository 의 clone/commit/push 흐름이 정상 동작하는가 (6점)
5) □ DB 접속정보가 .gitignore 와 properties 분리로 보안 처리되어 있는가 (7점)
```

### 🔗 수행준거 매핑
| 항목 | 수행준거 | 평가 근거 |
|:-:|:-:|---|
| 1) | 1.1 | `java -version` 출력 |
| 2) | 1.1 | `SHOW DATABASES;` + `SHOW GRANTS FOR 'memouser'@'%';` |
| 3) | 1.2 | `lib/mysql-connector-j-x.x.x.jar` + `.classpath` |
| 4) | 1.3 | GitHub Repository URL + 커밋 히스토리 |
| 5) | 1.3 | `.gitignore` + `src/memo/DBManager.java` 의 `Properties.load(...)` |

**소계: 30점**

---

## 🟢 능력단위요소 2. 기능 구현하기 (70점)

### 📜 수행준거
- **2.1** 개발방법 기준에 따라서 네트워크 프로그래밍 응용프로그램을 구현할 수 있다.
- **2.2** 프로그래밍 방법론에 따라서 설계내용을 바탕으로 네트워크 프로토콜을 구현할 수 있다.
- **2.3** 구축 계획에 따라 자원관리를 위하여 데이터베이스를 구현할 수 있다.
- **2.4** 효율적인 자원관리를 위하여 에이전트 (Agent) 를 구현할 수 있다.
- **2.5** 효과적인 트래픽 분석을 위하여 네트워크 QoS 제공방안을 구현할 수 있다.

### 🧾 체크리스트

```
6)  □ Swing UI 의 모든 이벤트 리스너가 정상 연결되어 동작하는가 (7점)
7)  □ 통합 런처에서 메신저 서버/클라이언트/메모관리 모듈이 정상 실행되는가 (7점)
8)  □ MessengerServer 가 ServerSocket(8888) 으로 포트 바인딩 후 accept() 로 접속을 수락하는가 (7점)
9)  □ MessengerClient 가 Socket 으로 connect 후 BufferedReader/PrintWriter 로 I/O Stream 을 구성하는가 (7점)
10) □ Memo VO 와 MemoDAO 가 객체지향(캡슐화) 원칙에 맞게 설계되어 있는가 (7점)
11) □ JDBC 4단계(Driver→Connection→PreparedStatement→ResultSet) 로 Memo_Tbl CRUD 4종이 정상 동작하는가 (7점)
12) □ 모든 쿼리에 PreparedStatement + ? 파라미터 바인딩으로 SQL Injection 이 방어되는가 (7점)
13) □ MessengerHandler 가 Thread 로 동작하여 클라이언트 2명 이상 동시 접속이 가능한가 (7점)
14) □ MessengerServer.broadcast() 가 클라이언트 컬렉션을 관리하며 모든 접속자에게 메시지를 전달하는가 (7점)
15) □ 모든 소켓/스트림/DB 자원에 try-catch-finally + close() 가 적용되어 자원 누수가 없는가 (7점)
```

### 🔗 수행준거 매핑
| 항목 | 수행준거 | 구현 위치 |
|:-:|:-:|---|
| 6)  | 2.1 | provided UI ↔ src 연동 |
| 7)  | 2.1 | `provided/launcher/Main.java` |
| 8)  | 2.2 | `src/chat/MessengerServer.java` |
| 9)  | 2.2 | `src/chat/MessengerClient.java` |
| 10) | 2.3 | `src/memo/Memo.java` + `MemoDAO.java` |
| 11) | 2.3 | `MemoDAO` 4 메서드 + `DBManager` |
| 12) | 2.3 | `MemoDAO` 의 PreparedStatement |
| 13) | 2.4 | `src/chat/MessengerHandler.java` (Thread) |
| 14) | 2.4 | `MessengerServer.broadcast()` + `clients` 관리 |
| 15) | 2.5 | 모든 chat/memo 파일의 try-catch-finally |

**소계: 70점**

---

## 📋 채점 결과란

| 능력단위요소 | 획득 / 만점 |
|---|---|
| 1. 개발환경 분석하기 | ____ / 30 |
| 2. 기능 구현하기     | ____ / 70 |
| **총점**            | **____ / 100** |

**합격 기준 (PRACTICE)**: 60점 이상이면 Ex 평가 진입 권장

**확인자(강사) 서명**: ___________________   **일자**: __________

---

## 🔗 관련 자료
- 학생 작업 영역: [src/](src/)
- 통합 가이드: [docs/가이드.md](docs/가이드.md)
- 정답 프로젝트: `../PRACTICE_답/`
- 다음 단계 (실 평가): `../Ex/`
