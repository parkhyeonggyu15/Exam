# lib/ 폴더 안내

## MySQL JDBC Driver 다운로드

이 폴더에는 **MySQL Connector/J** 가 들어가야 합니다.

1. 다운로드 페이지 접속
   <https://dev.mysql.com/downloads/connector/j/>

2. **Platform Independent (Architecture Independent), ZIP Archive** 선택

3. 압축 해제 후 `mysql-connector-j-9.x.x.jar` (또는 `mysql-connector-java-8.x.x.jar`) 파일을
   **이 폴더(`Ex/lib/`)** 에 복사

4. 결과 예시:
   ```
   Ex/lib/mysql-connector-j-9.1.0.jar
   ```

> ⚠️ jar 파일은 `.gitignore` 로 커밋에서 제외됩니다.
> 평가 제출 시에는 README에 다운로드 링크와 사용 버전을 기록하세요.
