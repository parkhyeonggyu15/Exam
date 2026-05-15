# 📁 reports/ — 개인 작업 보고서

이 폴더에는 **각 팀원의 개인 작업 보고서** 가 들어갑니다.
PPT 만들 때 이 보고서들을 합치면 됩니다.

---

## 📝 사용법

1. [_TEMPLATE.md](_TEMPLATE.md) 를 본인 이름으로 복사:
   ```bash
   # PowerShell
   Copy-Item _TEMPLATE.md 홍길동.md
   ```
2. `홍길동.md` 를 열어 각 항목 작성
3. 캡처 이미지는 같은 폴더에 `screenshots/` 만들어 저장 (선택)
4. git commit + push

---

## ⚠️ 주의

- **본인 보고서만 본인이 작성** (조장이 대신 작성하지 말 것 — 평가자가 누가 했는지 못 알아봄)
- `_TEMPLATE.md` 자체는 수정하지 말 것 (다음 사람이 또 복사해야 하므로)
- 비밀번호 / 개인정보 절대 적지 말 것

---

## 📋 예상 보고서 구성 (4명 팀 기준)

```
reports/
├── README.md                  ← 이 파일
├── _TEMPLATE.md               ← 템플릿 (수정 X)
├── 홍길동.md                   ← 조장 보고서
├── 김철수.md                   ← TASK-A 담당
├── 이영희.md                   ← TASK-C 담당
└── 박민수.md                   ← TASK-D 담당
```
