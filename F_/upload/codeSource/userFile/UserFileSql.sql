DROP TABLE IF EXISTS `t_user_file`;
CREATE TABLE `t_user_file`
(
    `id`          varchar(45) NOT NULL,
    `create_date` DATETIME    DEFAULT NULL,
    `modify_date` DATETIME    DEFAULT NULL,
    `uName`       VARCHAR(45) DEFAULT NULL COMMENT 'name',
    PRIMARY KEY (`id`)
);