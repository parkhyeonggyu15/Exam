# 🟢 TASK-A — 채팅 서버측 (ChatServer + ChatHandler)

> **담당자**: ___________________
> **추정 시간**: ~1.5시간
> **선행 조건**: 조장의 [TASK-00](TASK-00_조장_환경통합.md) Day 1 완료
> **의존성**: 독립 (TASK-B 와 함께 테스트 가능, 단독으로도 가능)

---

## 🎯 미션

`ServerSocket(9999)` 로 클라이언트 접속을 받고, 들어온 클라이언트마다 `Thread` 를 만들어 메시지를 받으면 **모든 클라이언트에게 브로드캐스트**하는 서버를 구현한다.

---

## 📂 담당 파일

| 파일 | 역할 |
|---|---|
| [src/chat/ChatServer.java](../../src/chat/ChatServer.java) | 서버 진입점 (`ServerSocket` bind / accept 루프) |
| [src/chat/ChatHandler.java](../../src/chat/ChatHandler.java) | 클라이언트 1명을 처리하는 Thread |

> ⚠️ `provided/` 와 `src/chat/ChatClient.java` (TASK-B) 는 절대 수정 금지!

---

## ✅ 채점 매핑 — [CHECKLIST.md](../../CHECKLIST.md) 능력단위요소 2

| 체크리스트 | 어디서 충족? | 배점 |
|---|---|---|
| **2-1** ServerSocket bind / accept | `ChatServer.main()` | 6점 |
| **2-3** Thread 다중 접속 | `ChatHandler` Thread 실행 + `ChatServer.clients` Set | 6점 |
| **2-4** broadcast 송신 | `ChatServer.broadcast()` + `ChatHandler.send()` | 6점 |
| **2-5** try-catch-finally + close | 두 파일 모두 | 6점 |

> 본 TASK 단독으로 능력단위요소 2 의 30점 중 **24점** 기여 가능 (나머지 6점은 TASK-B 의 클라이언트측).

---

## 🧭 작업 흐름

### Step 1 — 본인 브랜치 생성
```bash
git checkout main
git pull origin main
git checkout -b feature/task-a
```

### Step 2 — 파일 헤더에 본인 이름 적기
`ChatServer.java`, `ChatHandler.java` 두 파일의 javadoc 헤더에 있는
`담당자(@assignee)` 자리에 본인 이름을 적습니다.

### Step 3 — 코드 구현
[문제2_소켓채팅.md](../문제2_소켓채팅.md) 의 Step 1, 2, 3 그대로 따라 작성.

### Step 4 — 단위 테스트 (혼자)
1. `build.bat` → 컴파일 OK 확인
2. `run-server.bat` 실행 → 콘솔에 "[ChatServer] 서버 시작 - 포트: 9999"
3. **TASK-B 가 아직 안 끝났다면**: PowerShell 또는 `telnet localhost 9999` 로 접속 테스트
   ```powershell
   # PowerShell 으로 간단 테스트
   $tcp = New-Object System.Net.Sockets.TcpClient("localhost", 9999)
   $tcp.Connected   # True 면 OK
   $tcp.Close()
   ```
4. **TASK-B 가 끝났다면**: `run-chat.bat` 두 번 실행 → 두 창에서 메시지 주고받기

### Step 5 — 커밋 + 푸시
```bash
git add src/chat/ChatServer.java src/chat/ChatHandler.java
git commit -m "feat: ChatServer accept 루프 + ChatHandler Thread 구현"
git push origin feature/task-a
```

### Step 6 — PR 생성
GitHub 웹에서 `Compare & pull request`:
- 제목: `[TASK-A] ChatServer + ChatHandler 구현`
- PR 템플릿 본문 채우기 (채점기준 06, 08, 10 체크)
- Reviewer: 조장 지정

### Step 7 — 보고서 작성
[reports/_TEMPLATE.md](../../reports/_TEMPLATE.md) 를 본인 이름으로 복사 후 작성.

---

## 🧾 Definition of Done

- [ ] 두 파일 헤더의 `@assignee` 에 본인 이름 적힘
- [ ] `build.bat` 컴파일 OK
- [ ] `run-server.bat` → "서버 시작 - 포트: 9999" 출력
- [ ] 클라이언트 2개 동시 접속 시 한쪽 메시지가 양쪽에 표시 (TASK-B 와 함께 테스트)
- [ ] 클라이언트 종료 시 서버 콘솔에 "[퇴장] xxx" 또는 정상 종료 로그
- [ ] `try-catch-finally` 와 `close()` 가 모든 자원에 적용됨
- [ ] PR 생성 + 조장 review 완료
- [ ] 본인 reports/<이름>.md 작성

---

## 📌 자주 하는 실수

- ❌ `Socket socket = server.accept();` 다음에 `Thread.sleep` 없이 그냥 `handler.run()` 호출
   → 두 번째 클라이언트 접속 불가 (메인 스레드가 블록됨)
   → 반드시 `new Thread(handler).start();`
- ❌ `clients` 컬렉션을 순회하면서 동시에 `add/remove`
   → `ConcurrentModificationException`
   → `synchronized (clients) { ... }` 블록 안에서 순회
- ❌ `BufferedReader(new InputStreamReader(...))` 인코딩 미지정
   → 한글 깨짐
   → `new InputStreamReader(socket.getInputStream(), "UTF-8")`
- ❌ `PrintWriter(out)` 만 쓰고 `autoFlush = true` 누락
   → 메시지가 안 가는 것처럼 보임 (버퍼에 머물러 있음)

---

## 🆘 막혔을 때

| 증상 | 해결 |
|---|---|
| `Address already in use` | 이전에 띄운 서버가 안 죽음 → `taskkill /F /IM java.exe` |
| 클라가 접속은 되는데 메시지 안 옴 | broadcast 가 호출 안 되는지 콘솔 로그 확인 |
| 한글 깨짐 | UTF-8 명시 누락 |
| 클라 종료하면 서버 멈춤 | `run()` 의 try 안에서 NullPointerException 안 잡고 있는지 확인 |

---

## 🔗 관련 문서

- [문제2_소켓채팅.md](../문제2_소켓채팅.md) — 정답 코드 스니펫
- [WORKFLOW.md](../WORKFLOW.md) — git 흐름
- [TASK-B](TASK-B_조원_ChatClient.md) — 짝꿍 작업
