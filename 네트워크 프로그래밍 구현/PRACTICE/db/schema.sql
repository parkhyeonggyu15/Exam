-- ============================================================
-- 메모 공유 시스템 (MemoDB) DDL — 사전 PRACTICE
-- NCS 2001020508_19v3 네트워크 프로그래밍 구현 (실습 워밍업)
-- MySQL 8.x 기준
-- ============================================================
--
-- 시나리오: 팀원이 메모를 공유하고, 메신저로 알림을 주고받는 단순 시스템.
--           Ex 평가(도서관리) 도전 전 같은 패턴(Socket + JDBC) 을
--           단순한 도메인(메모 1개 테이블) 으로 먼저 익힌다.
--
-- ============================================================

DROP DATABASE IF EXISTS MemoDB;
CREATE DATABASE MemoDB
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;
USE MemoDB;

DROP USER IF EXISTS 'memouser'@'%';
CREATE USER 'memouser'@'%' IDENTIFIED BY 'memo1234!';
GRANT ALL PRIVILEGES ON MemoDB.* TO 'memouser'@'%';
FLUSH PRIVILEGES;

CREATE TABLE Memo_Tbl (
    memo_id     INT          NOT NULL AUTO_INCREMENT,
    title       VARCHAR(100) NOT NULL,
    content     VARCHAR(500),
    created_at  DATETIME     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (memo_id)
);

INSERT INTO Memo_Tbl (title, content) VALUES
    ('회의 안건', '내일 오전 10시 회의 — 프로젝트 진행 현황 공유'),
    ('점심 메뉴', '오늘은 한식 어떨까요?'),
    ('PRACTICE TODO', 'src/ 폴더 안의 TODO 6개 모두 채우기'),
    ('JDBC 학습', 'Driver Load → Connection → PreparedStatement → ResultSet'),
    ('Socket 학습', 'ServerSocket bind → accept → Thread 분기 → broadcast');

SELECT '--- Memo_Tbl ---' AS '';
SELECT * FROM Memo_Tbl;
