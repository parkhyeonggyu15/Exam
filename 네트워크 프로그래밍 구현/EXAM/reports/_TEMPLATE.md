# 📝 개인 작업 보고서 — [본인 이름]

> 사용법: 본 파일을 `<본인이름>.md` 로 복사한 뒤 작성하세요.
> 작성된 보고서는 평가 PPT 의 본인 파트 자료로 그대로 활용됩니다.

---

## 📌 기본 정보

| 항목 | 내용 |
|---|---|
| 이름 | __________ |
| 역할 | □ 조장  □ 조원 |
| 담당 TASK | □ TASK-00  □ TASK-A  □ TASK-B  □ TASK-C  □ TASK-D |
| GitHub 계정 | @__________ |
| 작업 기간 | YYYY-MM-DD ~ YYYY-MM-DD |

---

## 1. 담당 작업 요약

> 어떤 파일/기능을 구현했는지 1~3문장으로.

예시:
> ChatServer.java 의 ServerSocket bind / accept 루프와 broadcast 메서드, ChatHandler.java 의 Thread 기반 클라이언트 처리 + 자원 해제를 구현하였다.

---

## 2. 채점기준 충족 매트릭스

본인이 충족시킨 채점기준에 ✅ 표시:

| # | 채점기준 | 충족 |
|---|---|:---:|
| 01 | JDK / IDE 빌드 환경 | □ |
| 02 | JDBC Driver 빌드패스 + Driver 로드 | □ |
| 03 | MySQL BookDB + dbconn 계정 | □ |
| 04 | GitHub clone-commit-push | □ |
| 05 | .gitignore + properties 분리 | □ |
| 06 | ServerSocket bind / accept | □ |
| 07 | Socket connect / I/O Stream | □ |
| 08 | Thread 다중 접속 / 브로드캐스트 | □ |
| 09 | Swing 이벤트 ↔ Socket 연동 | □ |
| 10 | try-catch-finally / close | □ |
| 11 | DAO/VO 캡슐화 | □ |
| 12 | JDBC 4단계 | □ |
| 13 | CRUD 4종 동작 | □ |
| 14 | PreparedStatement (SQL Injection 방어) | □ |
| 15 | UI ↔ DB 결과 반영 | □ |

---

## 3. 핵심 코드 스니펫 (본인 작성)

> 본인이 작성한 코드 중 핵심 부분 1~3개 발췌 (PPT 에 캡처용).

```java
// 예: ChatServer accept 루프
public static void main(String[] args) {
    ServerSocket server = null;
    try {
        server = new ServerSocket(PORT);
        // ...
    }
}
```

---

## 4. 시연 캡처 위치

PPT 에 들어갈 캡처 파일 경로 (예: `screenshots/01_chat_server_start.png`).
캡처 안 했으면 "□ TODO" 로 남겨두기.

| # | 캡처 내용 | 파일명 |
|---|---|---|
| 1 | (예) 서버 시작 콘솔 로그 | __________ |
| 2 | 클라이언트 2개 동시 접속 채팅 | __________ |
| 3 | UI 종료 → 서버 콘솔 로그 | __________ |

---

## 5. 어려웠던 점 + 해결 방법

> 작업 중 막혔던 부분과 어떻게 풀었는지. 평가자가 "이해하고 짠 것인지" 보는 자료.

예시:
> Thread 가 클라이언트 2개부터 접속 안 받아져서 한참 헤맸음. 알고 보니 accept 후 handler.run() 을 직접 호출해서 메인 스레드가 블록되어 있었음. `new Thread(handler).start()` 로 변경하고 해결.

---

## 6. 본인 PR / 커밋 링크

| 항목 | 링크 |
|---|---|
| PR | https://github.com/.../pull/X |
| 주요 커밋 1 | https://github.com/.../commit/abc123 |
| 주요 커밋 2 | https://github.com/.../commit/def456 |

---

## 7. 팀에 기여한 추가 활동 (선택)

> 다른 조원 도와준 것, 코드 리뷰, 문서 작업 등.

---

## 8. 자가 평가 (선택)

| 항목 | 자가 점수 (1~5) | 한 줄 회고 |
|---|---|---|
| 코드 품질 | __ | |
| 협업 (PR/커뮤니케이션) | __ | |
| 문제 해결 능력 | __ | |
