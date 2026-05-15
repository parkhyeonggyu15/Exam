# 👑 TASK-00 — 조장 (환경 구축 + 통합 + 시연)

> **담당자**: ___________________
> **추정 시간**: ~3시간 (분산 진행, 한 번에 다 하지 않아도 됨)
> **선행 조건**: 없음 (가장 먼저 시작)

---

## 🎯 미션

조원들이 코드를 짤 수 있도록 **환경을 깔끔하게 마련**하고,
완성된 후 **하나의 동작하는 결과물**로 통합해 시연한다.

---

## 📋 단계별 작업

### Day 0 — 시작 전 (~30분)
- [ ] [LM2001020508_네트워크+프로그래밍+구현 (1).pdf](../../../LM2001020508_네트워크+프로그래밍+구현%20(1).pdf) 또는 본 README 정독
- [ ] 팀원 인원 파악 → [ROLES.md](../ROLES.md) 의 분담 매트릭스에서 적절한 표 선택
- [ ] 누가 어느 TASK 받을지 결정 → 채팅으로 공지

### Day 1 — 환경 구축 (~1시간)
- [ ] **GitHub Repository 생성**
  - 빈 repo 생성 (Private 권장)
  - 모든 조원 collaborator 추가 (write 권한)
  - main 브랜치 protection 설정 (선택)
- [ ] **본 프로젝트 push**
  ```bash
  cd Ex
  git init
  git add .
  git commit -m "chore: NCS 평가 프로젝트 초기 골격"
  git branch -M main
  git remote add origin https://github.com/<id>/<repo>.git
  git push -u origin main
  ```
- [ ] **MySQL 셋업**
  - MySQL 8.x 설치 후 root 로그인 확인
  - `mysql -uroot -p < db/schema.sql` 실행 → BookDB 생성, dbconn 계정 생성
  - 정상 확인: `SHOW DATABASES;` `SHOW GRANTS FOR 'dbconn'@'%';`
- [ ] **JDBC Driver 배포**
  - <https://dev.mysql.com/downloads/connector/j/> 에서 Platform Independent ZIP 다운
  - 압축 해제 후 `mysql-connector-j-9.x.x.jar` 를 조원에게 공유 (Slack 첨부, 또는 폴더 공유)
  - 조원은 본인 PC 의 `Ex/lib/` 폴더에 넣음
- [ ] **db.properties 비밀번호 결정**
  - schema.sql 의 기본값(`dbconn1234!`) 그대로 사용해도 OK
  - 변경하고 싶으면 `ALTER USER` 후 새 비번을 비공개 채널로 조원에게 전달

### Day 1 — 분담 공지 (~10분)
- [ ] 채팅에 다음 형식으로 공지:
  ```
  📢 TASK 분담 (확정)
  - 조장 (홍길동): TASK-00 + 발표
  - 김철수: TASK-A
  - 이영희: TASK-B
  - 박민수: TASK-C
  - 최지우: TASK-D

  📌 시작 전:
  1. main 브랜치 pull
  2. feature/task-X 브랜치 생성
  3. reports/_TEMPLATE.md 를 본인이름.md 로 복사
  4. 본인 카드 정독 후 작업 시작
  ```

### Day 2~3 — 조원 작업 모니터링 (~수시)
- [ ] 조원이 PR 올리면 리뷰 → [WORKFLOW.md 의 PR 리뷰 체크리스트](../WORKFLOW.md#pr-리뷰-시-체크) 따라
- [ ] 막힌 조원 도와주기 (스탠드업 또는 1:1)
- [ ] 본인 TASK 도 병행 (팀 인원이 적으면)

### Day 4 — 통합 + 시연 (~1.5시간)
- [ ] 모든 PR merge 완료 → main 에서 통합 빌드
  ```bash
  git checkout main
  git pull origin main
  build.bat
  ```
- [ ] **시연 시나리오 실행**
  1. `run-server.bat` (콘솔 1)
  2. `run-chat.bat` 두 번 (창 2개) → localhost:9999 접속 → 메시지 주고받기 캡처
  3. `run-book.bat` → 추가/수정/삭제 한 사이클 캡처
- [ ] **시연 영상 녹화** (휴대폰으로 화면 촬영도 OK)

### Day 4 — PPT 정리 + 제출 (~1시간)
- [ ] [reports/](../../reports/) 안의 조원 보고서 모두 모으기
- [ ] PPT 한 파일로 정리:
  - 표지 (조 이름, 멤버, 역할)
  - 환경 구축 결과 (스크린샷)
  - ERD
  - 채팅 시연 캡처
  - CRUD 시연 캡처
  - 채점기준 매핑표
  - GitHub Repository URL
- [ ] 제출물 체크:
  - [ ] PPT 1 파일
  - [ ] `db/schema.sql`
  - [ ] GitHub URL
  - [ ] 시연 영상

---

## ✅ 채점기준 책임 (조장이 직접 보장해야 하는 항목)

| # | 채점기준 | 조장 작업으로 충족 |
|---|---|---|
| 01 | JDK 설치 + IDE 빌드 환경 | 본인 환경 + 조원 안내 |
| 03 | MySQL BookDB + dbconn 계정 | schema.sql 실행 |
| 04 | GitHub clone-commit-push 흐름 | repo 생성 + 통합 merge |
| 05 | .gitignore + properties 분리 | 본 프로젝트 골격 그대로 유지 + 조원 교육 |

> 02, 06~15 채점기준은 조원의 코드로 충족됩니다 — 조장은 **PR 리뷰 시 채점기준 누락 없는지** 확인.

---

## 🧾 Definition of Done (완료 정의)

- [ ] GitHub Repository URL 이 README 에 적혀 있음
- [ ] main 브랜치에서 `build.bat` → `run-server.bat` + `run-chat.bat` x2 + `run-book.bat` 모두 정상 동작
- [ ] 시연 영상 1개 제출 가능
- [ ] PPT 한 파일 완성
- [ ] 본인 보고서 [reports/조장이름.md](../../reports/_TEMPLATE.md) 작성

---

## 🆘 막혔을 때

| 증상 | 해결 |
|---|---|
| MySQL 8 root 비밀번호 모름 | MySQL 재설치 또는 `--skip-grant-tables` 모드 |
| 조원이 `provided/` 수정해서 PR 옴 | "원복해서 다시 PR 부탁" 코멘트 |
| 통합 후 컴파일 에러 | 어느 PR 부터 깨진지 `git bisect` 로 추적 |
| 시연 직전 DB 깨짐 | `db/schema.sql` 다시 실행 (재현 가능하도록 schema 만 의존) |
