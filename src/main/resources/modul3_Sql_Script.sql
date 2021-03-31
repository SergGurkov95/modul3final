CREATE SCHEMA `module3_db`;

use module3_db;

CREATE TABLE `module3_db`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(50) NOT NULL,
  `login` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `age` INT NOT NULL,
  `is_author` TINYINT NOT NULL DEFAULT 0,
  `is_moderator` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

  ALTER TABLE `module3_db`.`users`
ADD COLUMN `exist` TINYINT NOT NULL DEFAULT 1 AFTER `is_moderator`;

CREATE TABLE `module3_db`.`posts` (
  `post_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `content` VARCHAR(1000) NULL,
  `author_id` INT NOT NULL,
  `moderator_id` INT NULL,
  `rating` DECIMAL NULL,
  `post_status` ENUM('DRAFT', 'IN_PROGRESS', 'WORKSHEET', 'PUBLISHED') NULL DEFAULT 'DRAFT',
  PRIMARY KEY (`post_id`));
  
  ALTER TABLE `module3_db`.`posts` 
ADD INDEX `pk_author_id_idx` (`author_id` ASC) VISIBLE,
ADD INDEX `pk_moderator_id_idx` (`moderator_id` ASC) VISIBLE;

ALTER TABLE `module3_db`.`posts` 
ADD CONSTRAINT `pk_author_id`
  FOREIGN KEY (`author_id`)
  REFERENCES `module3_db`.`users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `pk_moderator_id`
  FOREIGN KEY (`moderator_id`)
  REFERENCES `module3_db`.`users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  CREATE TABLE `module3_db`.`following_connection` (
  `master_id` INT NOT NULL,
  `slave_id` INT NOT NULL,
  PRIMARY KEY (`master_id`, `slave_id`),
  INDEX `pk_slave_idx` (`slave_id` ASC) VISIBLE,
  CONSTRAINT `pk_master`
    FOREIGN KEY (`master_id`)
    REFERENCES `module3_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pk_slave`
    FOREIGN KEY (`slave_id`)
    REFERENCES `module3_db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
