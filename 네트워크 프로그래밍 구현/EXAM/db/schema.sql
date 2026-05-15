-- ============================================================
-- 도서관리 시스템 (BookDB) DDL
-- NCS 2001020508_19v3 네트워크 프로그래밍 구현 평가용
-- MySQL 8.x 기준
-- ============================================================

DROP DATABASE IF EXISTS BookDB;
CREATE DATABASE BookDB
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;
USE BookDB;

-- ------------------------------------------------------------
-- 원격 접속 계정 (dbconn)
-- ------------------------------------------------------------
DROP USER IF EXISTS 'dbconn'@'%';
CREATE USER 'dbconn'@'%' IDENTIFIED BY 'dbconn1234!';
GRANT ALL PRIVILEGES ON BookDB.* TO 'dbconn'@'%';
FLUSH PRIVILEGES;

-- ============================================================
-- 1) 회원
-- ============================================================
CREATE TABLE Member_Tbl (
    Member_id   VARCHAR(20)  NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    identity    VARCHAR(20),
    grade       VARCHAR(20),
    addr        VARCHAR(200),
    phone       VARCHAR(20),
    PRIMARY KEY (Member_id)
);

-- ============================================================
-- 2) 도서 분류
-- ============================================================
CREATE TABLE Classification_Tbl (
    Classification_Id   INT          NOT NULL AUTO_INCREMENT,
    name                VARCHAR(100) NOT NULL,
    PRIMARY KEY (Classification_Id)
);

-- ============================================================
-- 3) 도서
-- ============================================================
CREATE TABLE Book_tbl (
    Book_code           VARCHAR(20)  NOT NULL,
    Classification_Id   INT,
    author              VARCHAR(100),
    name                VARCHAR(200) NOT NULL,
    publisher           VARCHAR(100),
    isreserve           CHAR(1) DEFAULT 'N',
    PRIMARY KEY (Book_code),
    FOREIGN KEY (Classification_Id) REFERENCES Classification_Tbl(Classification_Id)
);

-- ============================================================
-- 4) 대여
-- ============================================================
CREATE TABLE Rental_Tbl (
    Rental_id   INT         NOT NULL AUTO_INCREMENT,
    Book_code   VARCHAR(20),
    Member_id   VARCHAR(20),
    rent_date   DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (Rental_id),
    FOREIGN KEY (Book_code) REFERENCES Book_tbl(Book_code),
    FOREIGN KEY (Member_id) REFERENCES Member_Tbl(Member_id)
);

-- ============================================================
-- 5) 예약
-- ============================================================
CREATE TABLE Reserve_Tbl (
    Rental_Id       INT          NOT NULL,
    Member_id       VARCHAR(20)  NOT NULL,
    Reserve_order   INT,
    PRIMARY KEY (Rental_Id, Member_id),
    FOREIGN KEY (Rental_Id) REFERENCES Rental_Tbl(Rental_id),
    FOREIGN KEY (Member_id) REFERENCES Member_Tbl(Member_id)
);

-- ============================================================
-- 6) 부록
-- ============================================================
CREATE TABLE Appendix_Tbl (
    Appendix_id     INT          NOT NULL AUTO_INCREMENT,
    Book_code       VARCHAR(20),
    name            VARCHAR(200),
    PRIMARY KEY (Appendix_id),
    FOREIGN KEY (Book_code) REFERENCES Book_tbl(Book_code)
);

-- ============================================================
-- 샘플 데이터 (CRUD 테스트용)
-- ============================================================
INSERT INTO Classification_Tbl (name) VALUES
    ('컴퓨터/IT'),
    ('소설'),
    ('자기계발'),
    ('역사');

INSERT INTO Member_Tbl (Member_id, name, identity, grade, addr, phone) VALUES
    ('M001', '김철수', '950101', 'GENERAL', '서울시 강남구',  '010-1111-2222'),
    ('M002', '이영희', '961215', 'VIP',     '서울시 마포구',  '010-3333-4444'),
    ('M003', '박민수', '930320', 'GENERAL', '경기도 성남시',  '010-5555-6666');

INSERT INTO Book_tbl (Book_code, Classification_Id, author, name, publisher, isreserve) VALUES
    ('B001', 1, 'Joshua Bloch',  'Effective Java',         'Pearson',     'N'),
    ('B002', 1, '윤성우',         '열혈 자바 프로그래밍',     'Orange Media', 'N'),
    ('B003', 2, '한강',           '소년이 온다',             '창비',         'N'),
    ('B004', 3, '데일 카네기',     '인간관계론',              '현대지성',     'N');

-- 확인
SELECT '--- Member_Tbl ---' AS '';
SELECT * FROM Member_Tbl;
SELECT '--- Classification_Tbl ---' AS '';
SELECT * FROM Classification_Tbl;
SELECT '--- Book_tbl ---' AS '';
SELECT * FROM Book_tbl;
