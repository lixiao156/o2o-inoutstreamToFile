USE o2o;
CREATE TABLE tb_area(
/* 区域不会超过一百个*/
area_id INT (2) NOT NULL AUTO_INCREMENT,
/* VARCHAR的长度是会动态变化的*/
area_name VARCHAR(200) NOT NULL,
/* 权限范围为100 默认的权限级别为0*/
prioprity INT(2) NOT NULL DEFAULT '0',
/* 基于时间搓的   dateTime 大于 timeStems*/
create_time DATETIME DEFAULT NULL,

last_edit_time DATETIME DEFAULT NULL,
/*指定主键*/
PRIMARY KEY(`area_id`),
/* 创建唯一键*/
UNIQUE KEY `UK_AREA`(`area_name`)
)
/*创建表的引擎 每次都加1  默认的字符集是utf8  还有一个MYISAM字符集引擎，innoDB可以并发基于行去锁的*/
ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;


USE o2o;
CREATE TABLE tb_person_info(
user_id INT (10) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(32) DEFAULT NULL,
/* 这里的用户头像使用的是微信的头像的URL地址 String使用的1024位够用*/
`profile_img` VARCHAR(1024) DEFAULT NULL,
`email`VARCHAR(1024) DEFAULT NULL,
`gender`VARCHAR(2) DEFAULT NULL,
`enable_stat`o2o`us`INT(2) NOT NULL DEFAULT '0' COMMENT '0：禁止使用本商场，1允许使用本商场' ,
 `user_type` INT (2) NOT NULL DEFAULT '1' COMMENT '1:顾客，2店家，3：超级管理员',
 `creat_time`DATETIME DEFAULT NULL,
 `last_edit_time` DATETIME DEFAULT NULL,
 /*主键*/
 PRIMARY KEY(`user_id`)
)ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8


USE o2o;
/* 微信id*/
CREATE TABLE `tb_wechat_auth`(
`wechat_auth_id`INT (10) NOT NULL AUTO_INCREMENT,
/* 数据库不支持对象 学习的学生不是很多 10位够了*/
`user_id` INT (10),
/*open_id是由字母和数字组成的*/
`open_id` VARCHAR(1024) NOT NULL,
`creat_time` DATETIME DEFAULT NULL,
PRIMARY KEY(`wechat_auth_id`),
/* 需要将`tb_person_info`表中的(`user_id`)关联  [constraint 外键名*/
CONSTRAINT`fk_wechat_auth_profile` FOREIGN KEY(`user_id`) REFERENCES `tb_person_info`(`user_id`)
)ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8

USE o2o;
/*本地id*/
CREATE TABLE `tb_local_auth`(
`local_auth_id` INT(10) NOT NULL AUTO_INCREMENT,
`user_id` INT(10) NOT NULL,
`username` VARCHAR(128) NOT NULL,
`password` VARCHAR(128) NOT NULL,
`create_time` DATETIME DEFAULT NULL,
`last_edit_time`DATETIME DEFAULT NULL,
PRIMARY KEY(`local_auth_id`),
/*唯一键另外取名字*/
UNIQUE KEY `uk_local_profile`(`username`),
/* 外检约束  外键取别名*/
CONSTRAINT `fk_localauth_profile` FOREIGN KEY(`user_id`) REFERENCES `tb_person_info`(`user_id`)
)ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8


/*需要将表转化成唯一的索引  设成唯一值 表已经建设好 如何在已经存在的表中追加条件呢*/

ALTER TABLE tb_wechat_auth ADD UNIQUE INDEX(open_id)

/*
头条数据库表
*/
CREATE TABLE `tb_head_line`(
`line_id` INT (100) NOT NULL AUTO_INCREMENT,
`line_name`VARCHAR(1000) DEFAULT NULL,
`line_link`VARCHAR(2000) NOT  NULL,
`line_img` VARCHAR(2000)NOT NULL,
`priority`INT(2) DEFAULT NULL,
`enable_status`INT(2) NOT NULL DEFAULT '0',
`create_time` DATETIME DEFAULT NULL,
`last_edit_time`DATETIME DEFAULT NULL,
PRIMARY KEY(`line_id`)
)ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8


/*
创建店铺类别数据表
*/
CREATE TABLE `tb_shop_category` (
  `shop_category_id` INT(11) NOT NULL AUTO_INCREMENT,
  `shop_category_name` VARCHAR(100) NOT NULL DEFAULT '',
  `shop_category_desc` VARCHAR(1000) DEFAULT '',
  `shop_category_img` VARCHAR(2000) DEFAULT NULL,
  `priority` INT(2) NOT NULL DEFAULT '0',
  `create_time` DATETIME DEFAULT NULL,
  `last_edit_time` DATETIME DEFAULT NULL,
  `parent_id` INT(11) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  /**/
  KEY `fk_shop_category_self` (`parent_id`),
  /*  外键将自己连接起来？？？？ 重点*/
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=INNODB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*
店铺信息实体类表
*/
CREATE TABLE `tb_shop` (
  `shop_id` INT(10) NOT NULL AUTO_INCREMENT,
  `owner_id` INT(10) NOT NULL COMMENT '店铺创建人',
  `area_id` INT(5) DEFAULT NULL,
  `shop_category_id` INT(11) DEFAULT NULL,
  `parent_category_id` INT(11) DEFAULT NULL,
  `shop_name` VARCHAR(256) COLLATE utf8_unicode_ci NOT NULL,
  `shop_desc` VARCHAR(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_addr` VARCHAR(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` VARCHAR(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_img` VARCHAR(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude` DOUBLE(16,12) DEFAULT NULL,
  `latitude` DOUBLE(16,12) DEFAULT NULL,
  `priority` INT(3) DEFAULT '0',
  `create_time` DATETIME DEFAULT NULL,
  `last_edit_time` DATETIME DEFAULT NULL,
  `enable_status` INT(2) NOT NULL DEFAULT '0',
  `advice` VARCHAR(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  /* 需要创建三个外键*/
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_profile` (`owner_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_shopcate` (`shop_category_id`),
  KEY `fk_shop_parentcate` (`parent_category_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_parentcate` FOREIGN KEY (`parent_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`),
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=INNODB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


/* 商品分类*/

CREATE TABLE `tb_product_category` (
  `product_category_id` INT(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` VARCHAR(100) NOT NULL,
  `product_category_desc` VARCHAR(500) DEFAULT NULL,
  `priority` INT(2) DEFAULT '0',
  `create_time` DATETIME DEFAULT NULL,
  `last_edit_time` DATETIME DEFAULT NULL,
  `shop_id` INT(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_category_id`),
  CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=INNODB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;


/* 商品对应的图片介绍*/
CREATE TABLE `tb_product_img` (
  `product_img_id` INT(20) NOT NULL AUTO_INCREMENT,
  `img_addr` VARCHAR(2000) NOT NULL,
  `img_desc` VARCHAR(2000) DEFAULT NULL,
  `priority` INT(2) DEFAULT '0',
  `create_time` DATETIME DEFAULT NULL,
  `product_id` INT(20) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `fk_proimg_product` (`product_id`),
  CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
USE o2o;
SELECT area_id,area_name,prioprity,create_time,last_edit_time
FROM tb_area
/* 降序*/
ORDER BY prioprity DESC;

SELECT s.shop_id, s.shop_name, s.shop_desc, s.shop_addr, s.phone, s.shop_img, s.priority, s.create_time, s.last_edit_time, s.enable_status, s.advice, a.area_id,
a.area_name, sc.shop_category_id, sc.shop_category_name FROM tb_shop s, tb_area a, tb_shop_category sc WHERE s.owner_id=1 AND s.area_id=a.area_id AND
 s.shop_category_id=sc.shop_category_id ORDER BY s.priority DESC LIMIT 0,5




/*商品信息*/

CREATE TABLE `tb_product` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_desc` varchar(2000) DEFAULT NULL,
  `img_addr` varchar(2000) DEFAULT '',
  `normal_price` varchar(100) DEFAULT NULL,
  `promotion_price` varchar(100) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `point` int(10) DEFAULT NULL,
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `fk_product_shop` (`shop_id`),
  KEY `fk_product_procate` (`product_category_id`),
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`),
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;