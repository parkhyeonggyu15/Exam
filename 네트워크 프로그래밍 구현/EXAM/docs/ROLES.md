# 👥 팀 역할 분담 가이드

> NCS 평가는 개인 단위지만, 본 실습은 **조 단위 협업**으로 진행합니다.
> 각자가 채점기준을 모두 이해하면서도 코드 작성은 분담해서 효율적으로 끝낼 수 있도록 구성했습니다.

---

## 🎯 역할 정의

### 👑 조장 (1명)
**책임 범위**: 환경 / 통합 / 문서 / 시연

| 항목 | 작업 내용 |
|---|---|
| 환경 구축 | JDK / MySQL 설치 안내, JDBC jar 배포, schema.sql 실행 결과 공유 |
| 저장소 관리 | GitHub Repository 생성, branch protection, 조원 권한 부여 |
| 통합 | 조원 PR 리뷰 및 main 브랜치로 merge, 충돌 해결 |
| 문서 | README, schema.sql, 평가 제출용 PPT 최종 정리 |
| 시연 | 채팅 다중접속 + CRUD 한 사이클 시연 영상 녹화 |

> 💡 조장은 본인도 1개의 TASK 를 함께 진행하는 것을 권장합니다 (팀 규모에 따라).

### 🧑‍💻 조원 (1~4명)
**책임 범위**: 배정받은 TASK 카드 1~2개 완료

각 조원은 [docs/tasks/](tasks/) 안의 카드 1~2개를 배정받아 구현합니다.
완료 정의(Definition of Done)와 자가 점검 체크리스트가 카드에 포함되어 있습니다.

---

## 🧩 작업 분할표 (5개 TASK)

| TASK | 담당 영역 | 파일 | 추정 시간 | 의존성 |
|---|---|---|---|---|
| **[TASK-00](tasks/TASK-00_조장_환경통합.md)** | 환경 구축 + 통합 | DB 셋업, GitHub repo, README, PPT | ~3h (분산) | — |
| **[TASK-A](tasks/TASK-A_조원_ChatServer_Handler.md)** | 채팅 서버 | `ChatServer.java`, `ChatHandler.java` | ~1.5h | 독립 |
| **[TASK-B](tasks/TASK-B_조원_ChatClient.md)** | 채팅 클라이언트 | `ChatClient.java` | ~1h | 독립 (자기가 서버 띄워 테스트) |
| **[TASK-C](tasks/TASK-C_조원_Book_DBUtil.md)** | 도서 VO + DB 연결 | `Book.java`, `DBUtil.java` | ~1h | 독립 |
| **[TASK-D](tasks/TASK-D_조원_BookDAO.md)** | 도서 DAO (CRUD) | `BookDAO.java` | ~1.5h | TASK-C 완료 필요 |

---

## 👥 팀 규모별 추천 분담 매트릭스

### 2명 팀
| 사람 | TASK |
|---|---|
| 조장 | TASK-00 + TASK-A + TASK-B |
| 조원1 | TASK-C + TASK-D |

### 3명 팀
| 사람 | TASK |
|---|---|
| 조장 | TASK-00 |
| 조원1 | TASK-A + TASK-B |
| 조원2 | TASK-C + TASK-D |

### 4명 팀 (권장)
| 사람 | TASK |
|---|---|
| 조장 | TASK-00 + 발표 |
| 조원1 | TASK-A + TASK-B |
| 조원2 | TASK-C |
| 조원3 | TASK-D |

### 5명 팀
| 사람 | TASK |
|---|---|
| 조장 | TASK-00 + 발표 |
| 조원1 | TASK-A |
| 조원2 | TASK-B |
| 조원3 | TASK-C |
| 조원4 | TASK-D |

---

## 📋 팀 시작 체크리스트 (Day 0)

조 결성 후 30분 안에 끝낼 일들:

### 조장이 할 것
- [ ] GitHub Organization 또는 Repository 생성, 조원 모두 collaborator 로 초대
- [ ] [TASK-00](tasks/TASK-00_조장_환경통합.md) 의 환경 구축 단계 완료
- [ ] `db.properties` 의 비밀번호 결정 → 조원에게 비공개 채널로 전달 (Slack DM, 카톡 등)
- [ ] 위 분담표 보고 누가 어떤 TASK 받을지 확정 → 조원에게 공지

### 조원이 할 것
- [ ] 자기 TASK 카드 한 번 정독
- [ ] [reports/_TEMPLATE.md](../reports/_TEMPLATE.md) 를 본인 이름으로 복사 (예: `reports/홍길동.md`)
- [ ] 본인 feature 브랜치 생성 (`feature/task-a`, `feature/task-b` 등) — [WORKFLOW.md](WORKFLOW.md) 참조
- [ ] Eclipse 에 프로젝트 import + 컴파일 정상 확인

---

## 🏷️ 채점 시 본인 기여 표기

평가 시 평가자가 "이 코드는 누가 짰는지" 확인할 수 있도록:

1. **각 .java 파일 헤더의 `@assignee` 태그에 본인 이름 적기** (필수)
   ```java
   /**
    * ...
    * 담당자(@assignee): 홍길동
    * 작업단위(@task)  : TASK-A
    * ...
    */
   ```
2. **개인 보고서 작성** ([reports/](../reports/) 폴더)
3. **Git 커밋은 본인 계정으로** — `git config user.email` 본인 GitHub 이메일과 일치 확인
4. **PR 작성자도 본인** — 조장이 대신 PR 만들어주지 말 것

> 💡 평가자는 git log, PR history, @assignee 태그, 개인 보고서를 모두 종합하여 개인 점수 산정.

---

## 🔗 다음 단계

- 협업 흐름이 어떻게 돌아가는지 → [WORKFLOW.md](WORKFLOW.md)
- 본인 TASK 시작 → [tasks/](tasks/) 안의 본인 카드
- 보고서 작성 → [../reports/_TEMPLATE.md](../reports/_TEMPLATE.md) 복사
