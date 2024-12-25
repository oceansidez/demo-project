DROP TABLE IF EXISTS `t_user_file2`;
CREATE TABLE `t_user_file2`
(
    `id`          varchar(45) NOT NULL,
    `create_date` DATETIME    DEFAULT NULL,
    `modify_date` DATETIME    DEFAULT NULL,
    `name`        VARCHAR(45) DEFAULT NULL COMMENT 'am',
    PRIMARY KEY (`id`)
);