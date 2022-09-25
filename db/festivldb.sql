-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema festivaldb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema festivaldb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `festivaldb` ;
USE `festivaldb` ;

-- -----------------------------------------------------
-- Table `festivaldb`.`consumption`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`consumption` (
  `id_transaction` INT NOT NULL,
  `ended` TINYINT(1) NULL DEFAULT NULL,
  `id_dispenser` INT NULL DEFAULT NULL,
  `tap_insant_closed` VARCHAR(255) COLLATE 'utf8mb3_unicode_ci' NULL DEFAULT NULL,
  `tap_insant_opened` VARCHAR(255) COLLATE 'utf8mb3_unicode_ci' NULL DEFAULT NULL,
  PRIMARY KEY (`id_transaction`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `festivaldb`.`dispenser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`dispenser` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `current_opened` TINYINT(1) NULL DEFAULT NULL,
  `flow_volume` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9;


-- -----------------------------------------------------
-- Table `festivaldb`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
