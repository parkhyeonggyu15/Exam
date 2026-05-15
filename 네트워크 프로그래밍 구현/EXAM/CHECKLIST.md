# ✅ 평가 체크리스트 — 총 100점

> **NCS 능력단위** `2001020508_19v3` 네트워크 프로그래밍 구현 (2수준)
> 본 체크리스트는 학습모듈의 **능력단위요소별 수행준거** 에 근거하여 작성되었습니다.

---

## 📊 배점 요약

| 능력단위요소 | 항목 수 | 배점 |
|---|---:|---:|
| 1. 개발환경 분석하기 | 5  | **30점** |
| 2. 기능 구현하기     | 10 | **70점** |
| **합계** | **15** | **100점** |

> 💡 각 항목 옆 **(N점)** 표기는 해당 항목의 배점입니다.
>     배점은 능력단위요소별 비중 + 항목별 난이도/검증 가능성 을 종합하여 산정되었습니다.

---

## 🟦 능력단위요소 1. 개발환경 분석하기 (30점)

### 📜 수행준거
- **1.1** 개발방법 기준에 따라서 네트워크 프로그래밍 구현을 위한 H/W (PC, workstation, server 등) 및 S/W (Unix, windows, IOS 등) 개발환경을 구축할 수 있다.
- **1.2** 네트워크 개발환경의 프로그래밍 구현을 위하여 표준개발도구 (commands, .net framework control, eclipse 등) 을 사용할 수 있다.
- **1.3** 개발방법론에 따라서 네트워크 프로그래밍 구현을 위한 타겟시스템 형상(Configuration) 을 수정하여 보완할 수 있다.

### 🧾 체크리스트

```
1) □ JDK 설치 및 PATH 환경변수가 정상 구성되어 있는가 (5점)
2) □ MySQL BookDB 및 dbconn 원격 접속 계정이 정상 구축되어 있는가 (6점)
3) □ JDBC Driver 가 빌드패스에 정상 등록되어 있는가 (6점)
4) □ GitHub Repository 의 clone/commit/push 흐름이 정상 동작하는가 (6점)
5) □ DB 접속정보가 .gitignore 와 properties 분리로 보안 처리되어 있는가 (7점)
```

### 🔗 수행준거 매핑
| 항목 | 수행준거 | 평가 근거 (Ex 에서 확인) |
|:-:|:-:|---|
| 1) | 1.1 | `java -version` / `javac -version` 출력 |
| 2) | 1.1 | `SHOW DATABASES;` + `SHOW GRANTS FOR 'dbconn'@'%';` |
| 3) | 1.2 | `Ex/lib/mysql-connector-j-x.x.x.jar` + `.classpath` |
| 4) | 1.3 | GitHub Repository URL + `git log --oneline` |
| 5) | 1.3 | `.gitignore` 내용 + `Ex/src/book/DBUtil.java` 의 `Properties.load(...)` |

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
7)  □ 통합 런처에서 채팅 서버/클라이언트/도서관리 모듈이 정상 실행되는가 (7점)
8)  □ ChatServer 가 ServerSocket(9999) 으로 포트 바인딩 후 accept() 로 접속을 수락하는가 (7점)
9)  □ ChatClient 가 Socket 으로 connect 후 BufferedReader/PrintWriter 로 I/O Stream 을 구성하는가 (7점)
10) □ Book VO 와 BookDAO 가 객체지향(캡슐화) 원칙에 맞게 설계되어 있는가 (7점)
11) □ JDBC 4단계(Driver→Connection→PreparedStatement→ResultSet) 로 Book_tbl CRUD 4종이 정상 동작하는가 (7점)
12) □ 모든 쿼리에 PreparedStatement + ? 파라미터 바인딩으로 SQL Injection 이 방어되는가 (7점)
13) □ ChatHandler 가 Thread 로 동작하여 클라이언트 2명 이상 동시 접속이 가능한가 (7점)
14) □ ChatServer.broadcast() 가 클라이언트 컬렉션을 관리하며 모든 접속자에게 메시지를 전달하는가 (7점)
15) □ 모든 소켓/스트림/DB 자원에 try-catch-finally + close() 가 적용되어 자원 누수가 없는가 (7점)
```

### 🔗 수행준거 매핑
| 항목 | 수행준거 | 구현 위치 |
|:-:|:-:|---|
| 6)  | 2.1 (응용프로그램 구현)        | provided UI ↔ src 연동 |
| 7)  | 2.1 (응용프로그램 구현)        | `Ex/provided/launcher/Main.java` 통합 동작 |
| 8)  | 2.2 (네트워크 프로토콜 구현)    | `Ex/src/chat/ChatServer.java` |
| 9)  | 2.2 (네트워크 프로토콜 구현)    | `Ex/src/chat/ChatClient.java` |
| 10) | 2.3 (데이터베이스 구현)        | `Ex/src/book/Book.java` + `BookDAO.java` |
| 11) | 2.3 (데이터베이스 구현)        | `BookDAO` 의 4 메서드 + `DBUtil` |
| 12) | 2.3 (데이터베이스 구현)        | `BookDAO` 의 PreparedStatement 사용 |
| 13) | 2.4 (에이전트 구현)           | `Ex/src/chat/ChatHandler.java` (Thread 기반) |
| 14) | 2.4 (에이전트 구현)           | `ChatServer.broadcast()` + `clients` 자원관리 |
| 15) | 2.5 (QoS — 자원관리 안정성)   | 모든 chat/book 파일의 try-catch-finally |

**소계: 70점**

---

## 📋 채점 결과란

| 능력단위요소 | 획득 / 만점 |
|---|---|
| 1. 개발환경 분석하기 | ____ / 30 |
| 2. 기능 구현하기     | ____ / 70 |
| **총점**            | **____ / 100** |

**합격 기준**: 60점 이상 (100점 만점의 60%)

**채점자 서명**: ___________________   **일자**: __________

---

## 🔗 관련 자료
- 학생 작업 영역: [src/](src/)
- 작업 카드: [docs/tasks/](docs/tasks/)
- 정답 프로젝트: `../SOLVED/` (강사용)
- 사전 PRACTICE: `../PRACTICE/`
