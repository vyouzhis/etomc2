 
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


DROP TABLE IF EXISTS `web_user`;
CREATE TABLE IF NOT EXISTS `web_user` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `password` char(32) NOT NULL,
  `alias` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `ctime` int(11) NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `web_article`;
CREATE TABLE IF NOT EXISTS `web_article` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth` varchar(32) NOT NULL COMMENT 'article auth',
  `title` char(100) NOT NULL COMMENT 'article title',
  `cont` text NOT NULL COMMENT 'cont',
  `img` varchar(100) NOT NULL,  
  `ctime` int(11) NULL DEFAULT '0' COMMENT 'create time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 
-------

 
 CREATE TABLE hor_apisecret
(
  id serial NOT NULL,
  title character varying NOT NULL,
  username character varying NOT NULL,
  passwd character varying NOT NULL,
  ctime integer NOT NULL,
  idesc character varying NOT NULL DEFAULT ''::character varying,
  secret character varying(32) NOT NULL DEFAULT ''::character varying, -- secret
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare  integer NOT NULL DEFAULT 0, -- 0 no share , 1 share
    PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN hor_apisecret.secret IS 'secret';
COMMENT ON COLUMN hor_apisecret.uid IS 'user id';

CREATE TABLE hor_cache
(
  md5 character varying NOT NULL,
  json text NOT NULL,
  ctime timestamp without time zone DEFAULT now(),
  title character varying NOT NULL, -- 名称
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0 -- 0 no share , 1 share
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN hor_cache.title IS '名称';
COMMENT ON COLUMN hor_cache.uid IS 'user id';
COMMENT ON COLUMN hor_cache.isshare IS '0 no share , 1 share';


-- Index: md5_index

-- DROP INDEX md5_index;

CREATE INDEX md5_index
  ON hor_cache
  USING btree
  (md5 COLLATE pg_catalog."default");
COMMENT ON INDEX md5_index
  IS 'md5_index';

CREATE TABLE hor_city
(
  id serial NOT NULL,
  city character varying NOT NULL,
  min character varying NOT NULL,
  name character varying NOT NULL,
  title character varying(5) NOT NULL DEFAULT ''::character varying
)
WITH (
  OIDS=FALSE
);



CREATE TABLE hor_class
(
  rule integer NOT NULL DEFAULT 0,
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
  modify_time timestamp without time zone DEFAULT now(),
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0 -- 0 no share, 1 share
)
WITH (
  OIDS=FALSE
);

COMMENT ON TABLE hor_class
  IS '外部数据插入，比如CSV
rule 依赖于 classinfo 的 id 字段';
COMMENT ON COLUMN hor_class.uid IS 'user id';
COMMENT ON COLUMN hor_class.isshare IS '0 no share, 1 share';

CREATE TABLE hor_classify
(
  id serial NOT NULL,
  pid integer NOT NULL DEFAULT 0,
  name character varying NOT NULL,
  ctime integer NOT NULL,
  displays integer NOT NULL DEFAULT 0, -- 0 显示，1 隐藏
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0, -- 0 no share, 1 share
  idesc character varying(255) NOT NULL DEFAULT ''::character varying,
   PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN hor_classify.displays IS '0 显示，1 隐藏';
COMMENT ON COLUMN hor_classify.uid IS 'user id';
COMMENT ON COLUMN hor_classify.isshare IS '0 no share, 1 share';


CREATE TABLE hor_rlanguage
(
  id serial NOT NULL,
  title character varying NOT NULL,
  cid integer NOT NULL DEFAULT 0,
  day integer NOT NULL DEFAULT 0,
  hour integer NOT NULL DEFAULT 0,
  minu integer NOT NULL DEFAULT 0,
  uid integer NOT NULL DEFAULT 1,
  isshare integer NOT NULL DEFAULT 0,
  rdesc character varying(266) NOT NULL DEFAULT ''::character varying, -- desc
  rcode text NOT NULL DEFAULT ''::text, -- 代码
  ctime integer NOT NULL DEFAULT 0, -- create time
  etime integer NOT NULL DEFAULT 0, -- edit time
  ishow integer NOT NULL DEFAULT 0, -- 0 not show chart, 1 show in chart
  CONSTRAINT hor_rlanguage_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hor_rlanguage
  OWNER TO bi;
COMMENT ON COLUMN hor_rlanguage.rdesc IS 'desc';
COMMENT ON COLUMN hor_rlanguage.rcode IS '代码';
COMMENT ON COLUMN hor_rlanguage.ctime IS 'create time';
COMMENT ON COLUMN hor_rlanguage.etime IS 'edit time';
COMMENT ON COLUMN hor_rlanguage.ishow IS '0 not show chart, 1 show in chart';


CREATE TABLE hor_rexcel
(
  id serial NOT NULL,
  title character varying NOT NULL,
  path text NOT NULL DEFAULT ''::text,
  ctime integer NOT NULL DEFAULT 0,
  etime integer NOT NULL DEFAULT 0,
  uid integer NOT NULL DEFAULT 1,
  isshare integer NOT NULL DEFAULT 0,
  CONSTRAINT hor_rexcel_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hor_rexcel
  OWNER TO bi;

CREATE TABLE hor_classinfo
(
  id serial NOT NULL,
  title character varying NOT NULL,
  idesc character varying NOT NULL DEFAULT ''::character varying,
  ctime integer NOT NULL DEFAULT 1,
  view_name character varying(50), -- 保存 view 的名称
  view_sql text ,  
  ctype integer NOT NULL DEFAULT 0, -- 0 -- csv , 1 -- sql
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0, -- 0 no share, 1 share
  PRIMARY KEY (id),
   UNIQUE (view_name)
)
WITH (
  OIDS=FALSE
);

COMMENT ON TABLE hor_classinfo
  IS 'ctime 创建时间
etime 这次完成的时间  
view_name  view 的名称
ctype  -- 0 -- csv , 1 -- sql';
COMMENT ON COLUMN hor_classinfo.view_name IS '保存 view 的名称';
COMMENT ON COLUMN hor_classinfo.ctype IS '0 -- csv , 1 -- sql';
COMMENT ON COLUMN hor_classinfo.uid IS 'user id';
COMMENT ON COLUMN hor_classinfo.isshare IS '0 no share, 1 share';


CREATE TABLE hor_dbsource
(
  id serial NOT NULL,
  title character varying NOT NULL,
  dcname character varying NOT NULL,
  url character varying NOT NULL,
  username character varying NOT NULL,
  password character varying NOT NULL,
  ctime integer NOT NULL,
  modify_time timestamp without time zone DEFAULT now(),
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0 -- 0 no share, 1 share
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN hor_dbsource.uid IS 'user id';
COMMENT ON COLUMN hor_dbsource.isshare IS '0 no share, 1 share';


CREATE TABLE hor_doc
(
  id serial NOT NULL,
  title character varying NOT NULL,
  ctime integer NOT NULL,
  doc text,
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0, -- 0 no share, 1 share
   PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN hor_doc.uid IS 'user id';
COMMENT ON COLUMN hor_doc.isshare IS '0 no share, 1 share';


CREATE TABLE hor_mongodbrule
(
  id serial NOT NULL,
  name character varying NOT NULL,
  collention character varying NOT NULL,
  qaction smallint NOT NULL DEFAULT (0)::smallint,
  query text NOT NULL,
  field text NOT NULL,
  sort text NOT NULL,
  ctime integer NOT NULL,
  stime integer NOT NULL,
  etime integer NOT NULL DEFAULT 0,
  istop smallint NOT NULL DEFAULT 0,
  snap integer NOT NULL DEFAULT 0, -- 0 -- 可以在报表那儿显示菜单，1 需要二次运算开发
  cid integer NOT NULL DEFAULT 0, -- classify id
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0, -- 0 no share, 1 share
  PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN hor_mongodbrule.snap IS '0 -- 可以在报表那儿显示菜单，1 需要二次运算开发';
COMMENT ON COLUMN hor_mongodbrule.cid IS 'classify id';
COMMENT ON COLUMN hor_mongodbrule.uid IS 'user id';
COMMENT ON COLUMN hor_mongodbrule.isshare IS '0 no share, 1 share';

CREATE TABLE hor_sqltmp
(
  id serial NOT NULL,
  sid integer NOT NULL,
  name character varying NOT NULL,
  sqltmp text NOT NULL DEFAULT ''::character varying,
  units character varying(256) NOT NULL DEFAULT ''::character varying,
  ctime timestamp without time zone DEFAULT now(),
  etime integer NOT NULL DEFAULT 0,
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0, -- 0 no share, 1 share
 PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

COMMENT ON COLUMN hor_sqltmp.uid IS 'user id';
COMMENT ON COLUMN hor_sqltmp.isshare IS '0 no share, 1 share';



CREATE TABLE hor_usersql
(
  id serial NOT NULL,
  usql text NOT NULL,
  name character varying NOT NULL,
  modify_time timestamp without time zone DEFAULT now(),
  dtype integer NOT NULL DEFAULT 0,
  sql_type integer NOT NULL DEFAULT 0,
  sqltmp text NOT NULL DEFAULT ''::character varying,
  uview character varying(256) NOT NULL DEFAULT ''::character varying,
  
  input_data integer NOT NULL DEFAULT 0, -- 0 不导入，1 导入数据
  vtime integer NOT NULL DEFAULT 0, -- view 运行完成时间
  cid integer NOT NULL DEFAULT 0, -- classify id
  uid integer NOT NULL DEFAULT 1, -- 用户ID
  isshare integer NOT NULL DEFAULT 0 -- 0 no share, 1 share
)
WITH (
  OIDS=FALSE
);

COMMENT ON TABLE hor_usersql
  IS 'sql 语句
name 名称
modify_time 更新时间
dtype 数据类型
sql_type 0 代表一般的SQL 1 是模板
sqltmp 是记录模板对应的变量值
uview 记录视图名称
input_data 0 -- 0 不导入，1 导入数据';
COMMENT ON COLUMN hor_usersql.input_data IS '0 不导入，1 导入数据';
COMMENT ON COLUMN hor_usersql.vtime IS 'view 运行完成时间';
COMMENT ON COLUMN hor_usersql.cid IS 'classify id';
COMMENT ON COLUMN hor_usersql.uid IS '用户ID';
COMMENT ON COLUMN hor_usersql.isshare IS '0 no share, 1 share';



CREATE TABLE hor_webvisitcount
(
  rule integer NOT NULL DEFAULT (0)::smallint,
  volume integer NOT NULL,
  dial integer NOT NULL DEFAULT 0,
  modify_time timestamp without time zone DEFAULT now(),
  val character varying, -- 值
  uid integer NOT NULL DEFAULT 1, -- user id
  isshare integer NOT NULL DEFAULT 0 -- 0 no share, 1 share
)
WITH (
  OIDS=FALSE
);

COMMENT ON TABLE hor_webvisitcount
  IS 'rule 规则ID
volume 数量
dial 刻度盘';
COMMENT ON COLUMN hor_webvisitcount.val IS '值';
COMMENT ON COLUMN hor_webvisitcount.uid IS 'user id';
COMMENT ON COLUMN hor_webvisitcount.isshare IS '0 no share, 1 share';


-- Index: rul_wvcdial

-- DROP INDEX rul_wvcdial;

CREATE INDEX rul_wvcdial
  ON hor_webvisitcount
  USING btree
  (rule, dial);
  
  
  CREATE TABLE role_group
(
  id serial NOT NULL,
  gname character varying NOT NULL DEFAULT ''::character varying,
  gdesc character varying NOT NULL DEFAULT ''::character varying,
  "position" integer DEFAULT 1,
  mainrole text,
  subrole text,
  status integer NOT NULL DEFAULT 1,
  isdelete integer DEFAULT 0,
  uid integer NOT NULL DEFAULT 0,
  ctime integer NOT NULL,
  etime integer NOT NULL,
  PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);




CREATE TABLE role_log
(
  id serial NOT NULL,
  lid character varying NOT NULL DEFAULT ''::character varying,
  uid integer NOT NULL,
  action integer DEFAULT 0,
  ip character varying NOT NULL DEFAULT ''::character varying,
  ctime integer NOT NULL DEFAULT 0,
  data text NOT NULL DEFAULT ''::text,
   PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);




CREATE TABLE role_user_info
(
  uid serial NOT NULL,
  name character varying NOT NULL,
  passwd character varying NOT NULL,
  cm character varying NOT NULL,
  nickname character varying NOT NULL,
  email character varying NOT NULL,
  ctime integer NOT NULL DEFAULT 1,
  etime integer DEFAULT 0,
  ltime integer NOT NULL DEFAULT 1,
  phone character varying NOT NULL DEFAULT ''::character varying,
  status integer NOT NULL DEFAULT 1,
  isdelete integer DEFAULT 0,
  gid integer NOT NULL DEFAULT 0,
  cid integer NOT NULL DEFAULT 0,
  error integer NOT NULL DEFAULT 0,
   PRIMARY KEY (uid),
  UNIQUE (name)
)
WITH (
  OIDS=FALSE
);

INSERT INTO role_user_info (name, passwd, cm, nickname, email, ctime, ltime,  gid, cid)VALUES ( 'q@q.com', '202cb962ac59075b964b07152d234b70', '', 'admin', '', 0, 1436600255,  1,0);
