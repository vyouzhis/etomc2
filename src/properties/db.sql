 
DROP TABLE IF EXISTS `role_user_info`;
CREATE TABLE IF NOT EXISTS `role_user_info` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'login name',
  `passwd` varchar(32) NOT NULL COMMENT 'login passwd only md5, if only one change code ,pls see here and use https to login',
  `cm` varchar(32) NOT NULL COMMENT 'login again change check md5',
  `nickname` varchar(255) NOT NULL COMMENT 'nick name',
  `email` varchar(255) NOT NULL COMMENT 'email',
  `ctime` int(11) NOT NULL DEFAULT '1' COMMENT 'create time',
  `etime` int(11)  DEFAULT '0' COMMENT 'edit time',  
  `ltime` int(11) NOT NULL DEFAULT '1' COMMENT 'last login time',  
  `phone` varchar(128) NOT NULL DEFAULT '' COMMENT 'user phone',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT 'defaule 1 enable 0 disable',  
  `isdelete` tinyint(1) DEFAULT '0' COMMENT 'defaule 0 normal 1 delete',
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT 'group id',
  `cid` int(11) NOT NULL DEFAULT '0' COMMENT 'creater uid',
  `error` int(1) NOT NULL DEFAULT '0' COMMENT 'passwd error count',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `role_group`;
CREATE TABLE IF NOT EXISTS `role_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(30) NOT NULL DEFAULT '' COMMENT 'group name',
  `gdesc` varchar(200) NOT NULL DEFAULT '' COMMENT 'group desc',
  `position` int(11) DEFAULT '1' COMMENT 'lib position page order by',    
  `mainrole` text COMMENT 'main role json',  
  `subrole` text COMMENT 'sub role module json,eg. passwd diable',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT 'defaule 1 enable 0 disable',
  `isdelete` tinyint(1) DEFAULT '0' COMMENT 'defaule 0 normal 1 delete',
  `uid` int(11) NOT NULL DEFAULT '0' COMMENT 'creater uid',
  `ctime` int(11) NOT NULL COMMENT 'create time',  
  `etime` int(11) NOT NULL COMMENT 'last edit time',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `role_log`;
CREATE TABLE IF NOT EXISTS  `role_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lid` varchar(30) NOT NULL DEFAULT '' COMMENT 'lib name lib ppl/lib ', 
  `uid` int(11) NOT NULL  COMMENT 'user id ', 
  `action` tinyint(1) DEFAULT '0' COMMENT 'action: read-0  create-1 edit-2 remove-3 search-4',
  `ip` varchar(16) NOT NULL DEFAULT '' COMMENT 'client source IP',   
  `ctime` int(11) NOT NULL DEFAULT '0' COMMENT 'log time',  
   PRIMARY KEY (`id`)   
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO role_user_info (name, passwd, cm, nickname, email, ctime, ltime,  gid, cid)VALUES ( 'q@q.com', '202cb962ac59075b964b07152d234b70', '', 'admin', '', 0, 1436600255,  1,0);




-- 股票用户基本信息
DROP TABLE IF EXISTS `stock_user_info`;
CREATE TABLE IF NOT EXISTS `stock_user_info` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,  
  `email` varchar(255) NOT NULL COMMENT 'login name',
  `passwd` varchar(32) NOT NULL COMMENT 'login passwd only md5, if only one change code ,pls see here and use https to login',  
  `nickname` varchar(255) NOT NULL COMMENT 'nick name',  
  `ctime` int(11) NOT NULL DEFAULT '1' COMMENT 'create time',
  `etime` int(11)  DEFAULT '0' COMMENT 'edit time',  
  `ltime` int(11) NOT NULL DEFAULT '1' COMMENT 'last login time',  
  `phone` varchar(11) NOT NULL DEFAULT '0' COMMENT 'user phone',
  `addr` varchar(254) NOT NULL DEFAULT '' COMMENT 'addr',
  `rdes` varchar(254) NOT NULL DEFAULT '' COMMENT 'desc',
  
  `slogo` varchar(254) NOT NULL DEFAULT '' COMMENT 'source logo',
  `logo` varchar(254) NOT NULL DEFAULT '' COMMENT 'logo',
  
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '0 man 1 woman',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'defaule 1 enable 0 disable',  
  `isdelete` tinyint(1) DEFAULT '0' COMMENT 'defaule 0 normal 1 delete',
  `error` int(1) NOT NULL DEFAULT '0' COMMENT 'passwd error count',
  `active` int(1) NOT NULL DEFAULT '0' COMMENT '0 no , 1 yes',
  `salt` varchar(33) NOT NULL DEFAULT '' COMMENT 'active salt',
  `atime` int(11) NOT NULL DEFAULT '1' COMMENT 'active time', 
  `ip` varchar(16) NOT NULL DEFAULT '' COMMENT '第一次注册的IP',  
  PRIMARY KEY (`uid`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--- 模板html
DROP TABLE IF EXISTS `theme_html`;
CREATE TABLE IF NOT EXISTS `theme_html` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `html` text NOT NULL COMMENT 'html body',
  `types` varchar(16) NOT NULL DEFAULT '' COMMENT '类型',
  `tdesc` varchar(16) NOT NULL DEFAULT '' COMMENT '说明', 
  PRIMARY KEY (`id`),
  key(`types`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--- 一些名言
DROP TABLE IF EXISTS `tips_info`;
CREATE TABLE IF NOT EXISTS `tips_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tips` text NOT NULL COMMENT 'tips',  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--- 股票用户的基本信息
DROP TABLE IF EXISTS `stock_user_base`;
CREATE TABLE IF NOT EXISTS `stock_user_base` ( 
  `uid` int(11) NOT NULL COMMENT 'login uid' ,
  `leverage` int(11) NOT NULL COMMENT '杠杆',
  `balance` double NOT NULL COMMENT '余额',
  `taxes` double NOT NULL     COMMENT '税',
  `equity` double NOT NULL DEFAULT '0' COMMENT '净值',
  `capital` double NOT NULL DEFAULT '0' COMMENT '资本',
  `ctime` int(11) NOT NULL DEFAULT '1' COMMENT 'create time',
  `etime` int(11)  DEFAULT '0' COMMENT 'edit time',  
  `ltime` int(11) NOT NULL DEFAULT '1' COMMENT 'last login time',      
  KEY (`uid`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 股票用户 对某一条策略留言
DROP TABLE IF EXISTS `stock_user_talk`;
CREATE TABLE IF NOT EXISTS `stock_user_talk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,   
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT 'pid 回复上一个留言的id　０　代表独立的',
  `sid` int(11) NOT NULL DEFAULT '0' COMMENT '策略 id 0 是股票讨论',  
  `code` varchar(16) NOT NULL DEFAULT '' COMMENT '股票代码 空是策略讨论', 
  `uid` int(11) NOT NULL ,
  `msg` text default '',
  `ctime` int(11) NOT NULL DEFAULT '1' COMMENT 'create time',
   `ip` varchar(16) NOT NULL DEFAULT '' COMMENT 'IP',
   `reply` tinyint(1) DEFAULT '0' COMMENT 'defaule 0 可以 1 不可以回复',
   `top` tinyint(1) DEFAULT '0' COMMENT 'defaule 0 一般  1 置顶',
  PRIMARY KEY (`id`),
  KEY `code` (`code`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--- 股票类策略, 策略时间为 两年
DROP TABLE IF EXISTS `strategy_stock`;
CREATE TABLE `strategy_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL COMMENT '策略 类型 id',
  `title` varchar(255) NOT NULL COMMENT '策略标题',
  `uid` int(11) NOT NULL COMMENT 'login uid',
  `sdesc` text COMMENT '详细说明',
  `integral` int(11) DEFAULT '0' COMMENT '0 免费 ',
  `iid` int(11) NOT NULL COMMENT '策略 运行信息 id',
  `path` text COMMENT '策略路经',
  `returns` tinyint(1) DEFAULT '0' COMMENT '返回类型 0 table , 1 chart,  2 aplah, 3 chart and table. 4... ',
  `follow` int(11) NOT NULL COMMENT '当有人使用的时候记录一次',
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--- 首页显示的内容信息
DROP TABLE IF EXISTS `index_show`;
CREATE TABLE `index_show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tables` varchar(255) NOT NULL COMMENT '表名',
  `tid` int(11) NOT NULL COMMENT '该表的 id',
  `seque` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--- 股票类策略类型
DROP TABLE IF EXISTS `strategy_stock_style`;
CREATE TABLE IF NOT EXISTS `strategy_stock_style` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'style name',
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 策略运行信息, 策略 在某一个地方运行的
DROP TABLE IF EXISTS `strategy_info`;
CREATE TABLE IF NOT EXISTS `strategy_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(16) NOT NULL DEFAULT '' COMMENT 'IP',  
  `name` varchar(255) NOT NULL COMMENT 'login name',
  `passwd` varchar(255) NOT NULL COMMENT '加密过的密码', 
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 策略 与 股票 的关系,  同一时间，同一个股票用户，只有三个股票在跑，单个股票只有三个策略在分析，
DROP TABLE IF EXISTS `stock_strategy`;
CREATE TABLE IF NOT EXISTS `stock_strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT 'login uid' ,
  `code` varchar(10) NOT NULL COMMENT '股票代码' ,
  `name` varchar(20) NOT NULL COMMENT '股票名称' ,
  `cid` int(11) NOT NULL COMMENT '策略 类型 id' ,    
  `price` double NOT NULL COMMENT '股票价格',
  `sid`  int(11) NOT NULL COMMENT '策略ID' ,
  `ctime` int(11) NOT NULL DEFAULT '1' COMMENT 'create time',
  `etime` int(11)  DEFAULT '0' COMMENT 'edit time',  
  `ltime` int(11) NOT NULL DEFAULT '1' COMMENT 'last  time',
  `isstop`  tinyint(1) DEFAULT '0' COMMENT 'defaule 0 normal 1 stop',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create index  `index_uid_isstop` on `stock_strategy` (`uid`,`isstop`);
--ALTER TABLE `stock_strategy` ADD UNIQUE `unique_index`(`uid`, `isstop`, `code`，`sid`);


---- 是单独给与 SQLErrorLog 类使用，专门记录出错的日志
DROP TABLE IF EXISTS `error_log_sql`;
CREATE TABLE IF NOT EXISTS `error_log_sql` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` text NOT NULL COMMENT '保存错误记录 JSON' ,
  `error` varchar(255) NOT NULL COMMENT '出错原因' ,
  `ctime` int(11) NOT NULL DEFAULT '1' COMMENT 'create time',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--------------------------
-- 策略开发者基本信息， 在第三期开发， 所以不用创建表这么快
DROP TABLE IF EXISTS `strategy_user_info`;
CREATE TABLE IF NOT EXISTS `strategy_user_info` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'login name',
  `passwd` varchar(32) NOT NULL COMMENT 'login passwd only md5, if only one change code ,pls see here and use https to login',  
  `nickname` varchar(255) NOT NULL COMMENT 'nick name',
  `email` varchar(255) NOT NULL COMMENT 'email',
  `ctime` int(11) NOT NULL DEFAULT '1' COMMENT 'create time',
  `etime` int(11)  DEFAULT '0' COMMENT 'edit time',  
  `ltime` int(11) NOT NULL DEFAULT '1' COMMENT 'last login time',  
  `phone` int(11) NOT NULL DEFAULT '0' COMMENT 'user phone',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT 'defaule 1 enable 0 disable',  
  `isdelete` tinyint(1) DEFAULT '0' COMMENT 'defaule 0 normal 1 delete',
  `gid` int(11) NOT NULL DEFAULT '0' COMMENT 'group id',
  `cid` int(11) NOT NULL DEFAULT '0' COMMENT 'creater uid',
  `error` int(1) NOT NULL DEFAULT '0' COMMENT 'passwd error count',
  `active` int(1) NOT NULL DEFAULT '0' COMMENT '0 no , 1 yes',
  `ip` varchar(16) NOT NULL DEFAULT '' COMMENT '第一次注册的IP',  
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

