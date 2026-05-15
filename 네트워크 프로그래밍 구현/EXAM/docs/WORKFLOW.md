# 🔁 Git 협업 워크플로

> 4명 팀 기준으로 작성. 인원 다르면 적당히 줄이세요.
> **충돌을 가장 적게 만드는 흐름**을 선택했습니다.

---

## 🌳 브랜치 전략

```
main                   ← 항상 동작하는 안정 버전 (조장만 push 가능)
 ├── feature/task-a    ← 조원1: ChatServer + ChatHandler
 ├── feature/task-b    ← 조원1 또는 조원2: ChatClient
 ├── feature/task-c    ← 조원2 또는 조원3: Book + DBUtil
 └── feature/task-d    ← 조원3 또는 조원4: BookDAO
```

### 브랜치 명명 규칙
| 패턴 | 용도 | 예시 |
|---|---|---|
| `main`            | 안정 버전 | `main` |
| `feature/task-X`  | 조원의 신규 기능 | `feature/task-a` |
| `fix/<설명>`      | 버그 수정 | `fix/socket-close-leak` |
| `docs/<설명>`     | 문서만 변경 | `docs/readme-screenshots` |

---

## 📝 커밋 메시지 컨벤션

[Conventional Commits](https://www.conventionalcommits.org/) 단순 버전:

```
<type>: <짧은 한글 요약 (50자 이내)>

<선택: 상세 설명, 빈 줄로 구분>
```

### type 종류
| type | 의미 | 예시 |
|---|---|---|
| `feat` | 새 기능 | `feat: ChatServer accept 루프 구현` |
| `fix`  | 버그 수정 | `fix: 클라이언트 종료 시 NPE 방지` |
| `docs` | 문서 | `docs: README 에 시연 영상 링크 추가` |
| `chore`| 설정/빌드 | `chore: .gitignore 에 .vscode 추가` |
| `test` | 테스트 | `test: BookDAO insert 단위 테스트` |
| `refactor` | 리팩터 | `refactor: Book getter 자동 생성` |

### 좋은 예 vs 나쁜 예

✅ 좋음
```
feat: BookDAO.insert() 구현 (PreparedStatement 사용)

- SQL Injection 방어를 위해 ?로 파라미터 바인딩
- try-with-resources 로 Connection / PreparedStatement 자동 close
- 채점기준 13, 14 충족
```

❌ 나쁨
```
.
update
asdf
수정
```

---

## 🚫 충돌 방지 규칙 (가장 중요!)

| 파일/폴더 | 누가 건드리나 |
|---|---|
| `provided/`           | **아무도 수정 금지** (조장도 X) |
| `src/chat/Chat*.java` | TASK-A, TASK-B 담당자만 |
| `src/book/Book*.java` `DBUtil.java` | TASK-C, TASK-D 담당자만 |
| `db/schema.sql`       | 조장만 |
| `config/`             | 조장만 (db.properties 는 어차피 git 제외) |
| `README.md`           | 조장만 (조원은 본인 reports/ 에만 작성) |
| `docs/tasks/`         | 조장만 |
| `reports/_TEMPLATE.md`| 조장만 |
| `reports/<본인이름>.md` | 본인만 |

> 💡 같은 파일을 여러 명이 수정하면 merge 충돌 → 그래서 위처럼 파일 단위로 책임을 나눴습니다.

---

## 🔁 일일 흐름 (조원 기준)

### 1. 작업 시작 — 매번
```bash
git checkout main
git pull origin main          # 다른 사람 변경사항 받아오기
git checkout feature/task-a   # 본인 브랜치로 이동
git merge main                # main 의 최신 변경 본인 브랜치에 합치기
```

### 2. 작업 중 — 코드 수정
```bash
# Eclipse 에서 코딩...
# 작은 단위로 자주 커밋!
git add src/chat/ChatServer.java
git commit -m "feat: ChatServer accept() 루프 구현"
```

### 3. 푸시 — 작업 완료 OR 퇴근 전
```bash
git push origin feature/task-a
```

### 4. PR 생성 — 본인 TASK 완료 시
GitHub 웹사이트:
- `Compare & pull request` 버튼 클릭
- base: `main` ← compare: `feature/task-a`
- 제목: `[TASK-A] ChatServer + ChatHandler 구현 완료`
- 본문: PR 템플릿 자동 채워짐 → 채점기준 체크박스 표시
- Reviewer: **조장 지정**
- → 조장이 review 후 merge

---

## 👑 조장 흐름

### PR 리뷰 시 체크
- [ ] 컴파일 정상? (Eclipse 에서 빌드 에러 없음)
- [ ] `provided/` 파일 수정 안 했나?
- [ ] `@assignee` 태그에 작성자 이름 적혀 있나?
- [ ] 채점기준 매핑이 PR 본문에 적혀 있나?
- [ ] 커밋 메시지가 컨벤션 따랐나?

### Merge 후
```bash
git checkout main
git pull origin main          # merge 된 내용 로컬에 반영
build.bat                     # 통합 빌드 확인
```

---

## ⚠️ 자주 발생하는 충돌 + 해결

### 시나리오 1: 같은 파일을 두 명이 수정
**증상**: PR Merge 시 conflict 발생
**예방**: 위 「충돌 방지 규칙」 표를 따를 것
**해결**: VS Code 또는 Eclipse 의 merge tool 로 둘 다 살릴 부분 선택 후 commit

### 시나리오 2: pull 안 받고 작업하다가 push 거부됨
**증상**: `! [rejected] feature/task-a -> feature/task-a (non-fast-forward)`
**해결**:
```bash
git pull --rebase origin feature/task-a
# 충돌 있으면 해결 후
git rebase --continue
git push origin feature/task-a
```

### 시나리오 3: `db.properties` 가 실수로 commit 됨
**증상**: GitHub 에 비밀번호 노출
**즉시 해결**:
```bash
git rm --cached config/db.properties
git commit -m "chore: db.properties 추적 제외"
git push origin feature/task-X
```
- MySQL 비밀번호 즉시 변경 (`ALTER USER 'dbconn'@'%' IDENTIFIED BY '새비번';`)
- 새 비번을 조원에게 비공개 채널로 다시 공유

---

## 📌 일일 체크인 (선택, 권장)

매일 작업 시작 전 **5분 스탠드업**:
1. 어제 한 일
2. 오늘 할 일
3. 막힌 것 / 도움이 필요한 것

→ Slack/카톡 채널에 텍스트로 남기면 충분.
→ 조장은 막힌 사람 우선 도와주기.

---

## 🔗 다음 단계

- 본인 TASK 시작 → [tasks/](tasks/) 안의 본인 카드
- 보고서 작성 → [../reports/_TEMPLATE.md](../reports/_TEMPLATE.md) 복사
- 역할 다시 보기 → [ROLES.md](ROLES.md)
