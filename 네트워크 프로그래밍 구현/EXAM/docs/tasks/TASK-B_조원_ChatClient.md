# 🟢 TASK-B — 채팅 클라이언트측 (ChatClient)

> **담당자**: ___________________
> **추정 시간**: ~1시간
> **선행 조건**: 조장의 [TASK-00](TASK-00_조장_환경통합.md) Day 1 완료
> **의존성**: 독립 (서버는 본인이 띄워서 테스트 가능, 또는 TASK-A 와 함께)

---

## 🎯 미션

`Socket` 으로 서버에 접속해 닉네임을 보내고, 입력 메시지를 송신하고,
서버에서 들어오는 메시지를 **별도 Thread 로 받아 UI 콜백으로 전달**하는 클라이언트를 구현한다.

---

## 📂 담당 파일

| 파일 | 역할 |
|---|---|
| [src/chat/ChatClient.java](../../src/chat/ChatClient.java) | 비-UI 통신 클래스 (`connect / send / disconnect`) |

> ⚠️ `provided/chat/ChatClientUI.java` 와 TASK-A 의 서버 파일들은 절대 수정 금지!

> 💡 `readLoop()` 메서드는 **이미 구현되어 제공**됩니다. 본인은 `connect()` 안에서 이 메서드를 호출하는 Thread 를 시작하기만 하면 됩니다.

---

## ✅ 채점 매핑 — [CHECKLIST.md](../../CHECKLIST.md) 능력단위요소 2

| 체크리스트 | 어디서 충족? | 배점 |
|---|---|---|
| **2-2** Socket connect + I/O Stream | `ChatClient.connect()` | 6점 |
| **2-5** try-catch-finally + close | `ChatClient.disconnect()` | 6점 (TASK-A 와 공유) |

> 본 TASK 단독으로 능력단위요소 2 의 30점 중 **6점** 직접 기여 + 2-5 공유.
> Swing UI 와의 연동은 provided 의 `ChatClientUI` 가 본인 `ChatClient` 를 호출하면서 자동 검증됩니다.

---

## 🧭 작업 흐름

### Step 1 — 본인 브랜치 생성
```bash
git checkout main
git pull origin main
git checkout -b feature/task-b
```

### Step 2 — 파일 헤더에 본인 이름 적기
[ChatClient.java](../../src/chat/ChatClient.java) 의 javadoc 헤더 `담당자(@assignee)` 자리에 본인 이름.

### Step 3 — 코드 구현
[문제2_소켓채팅.md → Step 4](../문제2_소켓채팅.md#step-4--chatclientconnect--send--readloop) 그대로 따라 작성.

### 구현해야 할 메서드 3개

#### `connect(host, port, nickname)` — `throws IOException`
1. `socket = new Socket(host, port);`
2. `in / out` 스트림 초기화 (UTF-8 명시!)
3. `out.println(nickname);` — 첫 줄로 닉네임 전송
4. `running = true;`
5. **`new Thread(this::readLoop, "ChatClient-Reader").start();`** — readLoop 은 이미 구현되어 있음

#### `send(String message)`
- `if (out != null) out.println(message);` 한 줄

#### `disconnect()`
- `running = false;`
- `in / out / socket` close (try-catch 로 NPE/IOException 무시)

### Step 4 — 단위 테스트
1. `build.bat` → 컴파일 OK
2. **TASK-A 가 끝났다면**: `run-server.bat` (조장이나 본인이) → `run-chat.bat` 두 번
3. **TASK-A 가 안 끝났다면**: 간단한 echo 서버 만들어 테스트
   ```java
   // 임시 테스트용 (커밋 X)
   ServerSocket s = new ServerSocket(9999);
   while (true) {
       Socket c = s.accept();
       new Thread(() -> {
           try (BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
                PrintWriter w = new PrintWriter(c.getOutputStream(), true)) {
               String line;
               while ((line = r.readLine()) != null) w.println("ECHO: " + line);
           } catch (IOException e) {}
       }).start();
   }
   ```

### Step 5 — 커밋 + 푸시
```bash
git add src/chat/ChatClient.java
git commit -m "feat: ChatClient connect/send/disconnect 구현"
git push origin feature/task-b
```

### Step 6 — PR 생성
- 제목: `[TASK-B] ChatClient 구현`
- PR 템플릿 채우기 (채점기준 07, 09, 10 체크)
- Reviewer: 조장

### Step 7 — 보고서 작성
[reports/_TEMPLATE.md](../../reports/_TEMPLATE.md) 복사 후 작성.

---

## 🧾 Definition of Done

- [ ] 파일 헤더 `@assignee` 본인 이름
- [ ] `build.bat` 컴파일 OK
- [ ] `run-chat.bat` 실행 → UI 뜸 → [접속] 클릭 → "[시스템] localhost:9999 에 접속" 표시
- [ ] [전송] 버튼 + 엔터키 모두 동작
- [ ] [종료] 버튼 클릭 → "[시스템] 접속을 종료했습니다" 표시
- [ ] UI 창을 X 로 닫아도 백그라운드 Thread 가 살아남지 않음 (Daemon Thread 또는 disconnect 호출)
- [ ] PR 생성 + 조장 review 완료
- [ ] 본인 reports/<이름>.md 작성

---

## 📌 자주 하는 실수

- ❌ readLoop Thread 를 daemon 으로 안 만듦
   → UI 닫혀도 JVM 안 죽음
   → `t.setDaemon(true);` 추가
- ❌ `out.println` 만 쓰고 `out.flush()` 누락 (autoFlush 가 false 인 경우)
   → 메시지 전송 안 됨
   → `new PrintWriter(..., true)` 의 두 번째 인자가 autoFlush
- ❌ `disconnect()` 에서 `running = false` 안 함
   → readLoop 이 끝났는데 disconnect 다시 호출되며 무한 루프 가능
- ❌ `connect()` 에서 첫 줄 닉네임 전송 누락
   → 서버 측 `ChatHandler.run()` 의 첫 `readLine()` 이 닉네임으로 인식 못 함

---

## 🆘 막혔을 때

| 증상 | 해결 |
|---|---|
| 접속은 되는데 메시지 송신 후 응답 없음 | TASK-A 의 broadcast 가 안 호출되는 것일 수 있음 → 조장에게 알려 TASK-A 담당과 함께 디버깅 |
| `Connection refused` | 서버가 안 떠 있음 — `run-server.bat` 먼저 실행 |
| 한글 메시지 깨짐 | UTF-8 명시 안 됨 — 양쪽(서버/클라) 모두 UTF-8 |
| UI 가 멈춤 | 메인 Thread (EDT) 에서 `socket.read()` 호출하고 있음 — 별도 Thread 로 빼야 함 |

---

## 🔗 관련 문서

- [문제2_소켓채팅.md](../문제2_소켓채팅.md) — 정답 스니펫
- [TASK-A](TASK-A_조원_ChatServer_Handler.md) — 짝꿍 작업
- [WORKFLOW.md](../WORKFLOW.md)
