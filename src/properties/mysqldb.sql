
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
INSERT INTO role_user_info (name, passwd, cm, nickname, email, ctime, ltime,  gid, cid)VALUES ( 'q@q.com', '202cb962ac59075b964b07152d234b70', '', 'admin', '', 0, 1436600255,  1,0);

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
  `data` text COMMENT 'data',
   PRIMARY KEY (`id`)   
) ENGINE=InnoDB DEFAULT CHARSET=utf8;














DROP TABLE IF EXISTS `hor_apisecret`;
 CREATE TABLE hor_apisecret
(
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  username varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  passwd varchar(32) NOT NULL COMMENT 'login passwd only md5, if only one change code ,pls see here and use https to login',
  ctime int(11) NOT NULL DEFAULT '0' COMMENT 'log time',
  idesc varchar(200) NOT NULL DEFAULT '' COMMENT 'id desc',
  secret varchar(32) NOT NULL, -- secret
  uid int(11) NOT NULL  COMMENT 'user id ', -- user id
  isshare  int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share , 1 share
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hor_cache`;
CREATE TABLE hor_cache
(
  md5 varchar(32) NOT NULL,
  json text COMMENT 'json data',
  ctime timestamp DEFAULT now(),
  title varchar(200) NOT NULL DEFAULT '' COMMENT 'name', -- 名称
  uid int(11) NOT NULL  COMMENT 'user id ', -- user id
  isshare int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share' -- 0 no share , 1 share
) DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hor_classify`;
CREATE TABLE hor_classify
(
  id  int(11) NOT NULL AUTO_INCREMENT,
  pid int(11) NOT NULL DEFAULT '0' COMMENT 'pid',
  name varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  ctime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  displays  int(11) NOT NULL DEFAULT '0' COMMENT 'create time', -- 0 显示，1 隐藏
  uid int(11) NOT NULL  COMMENT 'user id ', -- user id
  isshare  int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share, 1 share
  idesc varchar(200) NOT NULL DEFAULT '' COMMENT 'id desc',
   PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hor_rlanguage`;
CREATE TABLE hor_rlanguage
(
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  cid  int(11) NOT NULL DEFAULT '0' COMMENT 'cid',
  day  int(11) NOT NULL DEFAULT '0' COMMENT 'day',
  hour  int(11) NOT NULL DEFAULT '0' COMMENT 'hour',
  minu  int(11) NOT NULL DEFAULT '0' COMMENT 'minu',
  uid  int(11) NOT NULL DEFAULT '0' COMMENT 'uid',
  isshare int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share',
  rdesc varchar(256) NOT NULL DEFAULT '' COMMENT 'id desc', -- desc
  rcode text COMMENT 'r  code', -- 代码
  ctime int(11) NOT NULL DEFAULT '0' COMMENT 'create time', -- create time
  etime int(11) NOT NULL DEFAULT '0' COMMENT 'create time', -- edit time
  ishow int(11) NOT NULL DEFAULT '0' COMMENT 'create time', -- 0 not show chart, 1 show in chart
  CONSTRAINT hor_rlanguage_pkey PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hor_rexcel`;
CREATE TABLE hor_rexcel
(
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  path text ,
  ctime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  etime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  uid int(11) NOT NULL DEFAULT '0' COMMENT 'uid',
  isshare  int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share',
  PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hor_classinfo`;
CREATE TABLE hor_classinfo
(
  id int(11) NOT NULL AUTO_INCREMENT,
  title  varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  idesc  text  COMMENT 'idesc',
  ctime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  view_name varchar(50) NOT NULL DEFAULT '' COMMENT 'name', -- 保存 view 的名称
  ctype  int(1) NOT NULL DEFAULT '0' COMMENT '-- 0 -- csv , 1 -- sql', -- 0 -- csv , 1 -- sql
  uid int(11) NOT NULL DEFAULT '0' COMMENT 'uid', -- user id
  isshare  int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share, 1 share
  PRIMARY KEY (id),
  UNIQUE (view_name)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hor_dbsource`;
CREATE TABLE hor_dbsource
(
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  dcname varchar(256) NOT NULL DEFAULT '' COMMENT 'name',
  url varchar(256) NOT NULL DEFAULT '' COMMENT 'name',
  username varchar(100) NOT NULL DEFAULT '' COMMENT 'name',
  password varchar(100) NOT NULL DEFAULT '' COMMENT 'name',
  ctime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  modify_time timestamp DEFAULT now(),
  uid int(11) NOT NULL DEFAULT '0' COMMENT 'uid', -- user id
  isshare int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share, 1 share
  PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hor_doc`;
CREATE TABLE hor_doc
(
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  ctime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  doc text,
  uid int(11) NOT NULL DEFAULT '0' COMMENT 'uid', -- user id
  isshare int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share, 1 share
   PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `hor_mongodbrule`;
CREATE TABLE hor_mongodbrule
(
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  collention varchar(256) NOT NULL DEFAULT '' COMMENT 'name',
  qaction int(11) NOT NULL DEFAULT '0' COMMENT 'uid',
  query text NOT NULL,
  field text NOT NULL,
  sort text NOT NULL,
  ctime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  stime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  etime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  istop int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share',
  snap int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 -- 可以在报表那儿显示菜单，1 需要二次运算开发
  cid int(11) NOT NULL DEFAULT '0' COMMENT 'cid', -- classify id
  uid int(11) NOT NULL DEFAULT '0' COMMENT 'uid', -- user id
  isshare int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share, 1 share
  PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hor_sqltmp`;
CREATE TABLE hor_sqltmp
(
  id int(11) NOT NULL AUTO_INCREMENT,
  sid  int(11) NOT NULL DEFAULT '0' COMMENT 'cid',
  name varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  sqltmp text ,
  units varchar(256) NOT NULL DEFAULT '' COMMENT 'unit',
  ctime timestamp  DEFAULT now(),
  etime int(11) NOT NULL DEFAULT '0' COMMENT 'create time',
  uid int(11) NOT NULL DEFAULT '0' COMMENT 'uid', -- user id
  isshare int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share, 1 share
 PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hor_usersql`;
CREATE TABLE hor_usersql
(
  id  int(11) NOT NULL AUTO_INCREMENT,
  usql text,
  name varchar(200) NOT NULL DEFAULT '' COMMENT 'name',
  modify_time timestamp  DEFAULT now(),
  dtype int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share',
  sql_type int(1) NOT NULL DEFAULT '0',
  sqltmp text NOT NULL ,
  uview varchar(256) NOT NULL DEFAULT '' COMMENT 'name',  
  input_data int(1) NOT NULL DEFAULT '0', -- 0 不导入，1 导入数据
  vtime int(11) NOT NULL DEFAULT '0', -- view 运行完成时间
  cid int(11) NOT NULL DEFAULT '0' COMMENT 'cid', -- classify id
  uid int(11) NOT NULL DEFAULT '0' COMMENT 'uid', -- 用户ID
  isshare int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share, 1 share
   PRIMARY KEY (id)
)DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hor_class`;
CREATE TABLE hor_class
(
  rule int(11) NOT NULL DEFAULT 0,
  act_v0 text,
  act_v1 text,
  act_v2 text,
  act_v3 text,
  act_v4 text,
  act_v5 text,
  act_v6 text,
  act_v7 text,
  act_v8 text,
  act_v9 text,
  act_va text,
  act_vb text,
  act_vc text,
  act_vd text,
  act_ve text,
  act_vf text,
  act_v10 text,
  act_v11 text,
  act_v12 text,
  act_v13 text,
  act_v14 text,
  act_v15 text,
  act_v16 text,
  act_v17 text,
  act_v18 text,
  act_v19 text,
  act_v1a text,
  act_v1b text,
  act_v1c text,
  act_v1d text,
  act_v1e text,
  act_v1f text,
  act_v20 text,
  act_v21 text,
  act_v22 text,
  act_v23 text,
  act_v24 text,
  act_v25 text,
  act_v26 text,
  act_v27 text,
  act_v28 text,
  act_v29 text,
  act_v2a text,
  act_v2b text,
  act_v2c text,
  act_v2d text,
  act_v2e text,
  act_v2f text,
  act_v30 text,
  act_v31 text,
  act_v32 text,
  act_v33 text,
  act_v34 text,
  act_v35 text,
  act_v36 text,
  act_v37 text,
  act_v38 text,
  act_v39 text,
  act_v3a text,
  act_v3b text,
  act_v3c text,
  act_v3d text,
  act_v3e text,
  act_v3f text,
  act_v40 text,  
  modify_time timestamp  DEFAULT now(),
  uid int(11) NOT NULL DEFAULT '0' COMMENT 'user id ', -- user id
  isshare int(1) NOT NULL DEFAULT '0' COMMENT '0 no share , 1 share', -- 0 no share , 1 share
  KEY `rule_index` (`rule`)
)DEFAULT CHARSET=utf8;




