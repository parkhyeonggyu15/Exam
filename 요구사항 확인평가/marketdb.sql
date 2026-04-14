-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema marketdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema marketdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `marketdb` DEFAULT CHARACTER SET utf8 ;
USE `marketdb` ;

-- -----------------------------------------------------
-- Table `marketdb`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketdb`.`member` (
  `member_id` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `marketdb`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketdb`.`address` (
  `address_id` INT NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  `address` VARCHAR(200) NULL,
  PRIMARY KEY (`address_id`),
  INDEX `fk_address_member_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_address_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `marketdb`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `marketdb`.`seller`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketdb`.`seller` (
  `seller_id` INT NOT NULL,
  `seller_name` VARCHAR(45) NULL,
  `seller_phone` VARCHAR(45) NULL,
  `company_name` VARCHAR(45) NULL,
  `company_address` VARCHAR(45) NULL,
  PRIMARY KEY (`seller_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `marketdb`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketdb`.`category` (
  `category_id` INT NOT NULL,
  `category_name` VARCHAR(45) NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `marketdb`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketdb`.`product` (
  `product_id` INT NOT NULL,
  `seller_id` INT NOT NULL,
  `category_category_id` INT NOT NULL,
  `product_count` INT NULL,
  `product_img` BLOB NULL,
  `product_info` VARCHAR(200) NULL,
  `address` VARCHAR(200) NULL,
  `price` VARCHAR(45) NULL,
  `product_name` VARCHAR(45) NULL,
  PRIMARY KEY (`product_id`),
  INDEX `fk_product_seller1_idx` (`seller_id` ASC) VISIBLE,
  INDEX `fk_product_category1_idx` (`category_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_seller1`
    FOREIGN KEY (`seller_id`)
    REFERENCES `marketdb`.`seller` (`seller_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_category_id`)
    REFERENCES `marketdb`.`category` (`category_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `marketdb`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketdb`.`order` (
  `order_id` INT NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  `product_id` INT NOT NULL,
  `order_count` INT NULL,
  `address` VARCHAR(45) NULL,
  `buy_datetime` DATETIME NULL,
  `process` VARCHAR(45) NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_order_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_order_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `marketdb`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `marketdb`.`product` (`product_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `marketdb`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketdb`.`review` (
  `review` INT NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  `product_id` INT NOT NULL,
  `starpoint` INT NULL,
  `comment` VARCHAR(200) NULL,
  `write_datetime` DATETIME NULL,
  `seller_comment` VARCHAR(45) NULL,
  `seller_comment_datetime` DATETIME NULL,
  INDEX `fk_member_has_product_product1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_member_has_product_member1_idx` (`member_id` ASC) VISIBLE,
  PRIMARY KEY (`review`),
  CONSTRAINT `fk_member_has_product_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `marketdb`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_member_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `marketdb`.`product` (`product_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `marketdb`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketdb`.`cart` (
  `cart_id` INT NOT NULL,
  `member_id` VARCHAR(45) NOT NULL,
  `product_id` INT NOT NULL,
  `product_name` VARCHAR(45) NULL,
  `product_count` INT NULL,
  `price` VARCHAR(45) NULL,
  PRIMARY KEY (`cart_id`, `member_id`),
  INDEX `fk_cart_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_cart_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `marketdb`.`member` (`member_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `marketdb`.`product` (`product_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
