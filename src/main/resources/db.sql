USE ykyl;

TRUNCATE TABLE zx_question;
TRUNCATE TABLE zx_answer;
TRUNCATE TABLE zx_user;


SELECT * FROM zx_question;
SELECT * FROM zx_answer;
SELECT * FROM zx_option;
SELECT * FROM zx_knowledge_point;
SELECT * FROM zx_subject;
SELECT * FROM zx_label;
SELECT * FROM zx_user_question_hist;
SELECT * FROM zx_user;
SELECT op_content FROM zx_option WHERE op_question_id=1;
SELECT kp_name FROM zx_knowledge_point WHERE kp_subject_id=1;
SELECT question_content FROM zx_question WHERE qu_knowledge_point_id=1;
SELECT kp_name FROM zx_knowledge_point,zx_question WHERE zx_knowledge_point.id=zx_question.qu_knowledge_point_id AND zx_question.id=1;
SELECT lb_name FROM zx_label,zx_question WHERE zx_label.id=zx_question.qu_label_id AND zx_question.id=2;
-- 查询古文练习的题目数量
SELECT COUNT(*) FROM zx_question WHERE zx_question.qu_knowledge_point_id=(SELECT zx_knowledge_point.id FROM zx_knowledge_point WHERE kp_name='古文练习');
SELECT kp_name FROM zx_knowledge_point WHERE id=2;
SELECT COUNT(*) FROM zx_question,zx_knowledge_point WHERE zx_question.qu_knowledge_point_id= zx_knowledge_point.id AND zx_knowledge_point.kp_name='古文练习'
SELECT COUNT(*) FROM zx_question,zx_knowledge_point WHERE zx_question.qu_knowledge_point_id= zx_knowledge_point.id AND zx_knowledge_point.id=2;
SELECT qh_user_question_hist FROM zx_user_question_hist WHERE id=2;
-- 根据知识点名查找对应的题目数量
SELECT zx_knowledge_point.id FROM zx_knowledge_point WHERE kp_name='古文练习';
SELECT COUNT(zx_question.std_answer) FROM zx_question,zx_knowledge_point WHERE zx_question.qu_knowledge_point_id=(SELECT zx_knowledge_point.id FROM zx_knowledge_point WHERE kp_name='古文练习');
SELECT COUNT(*) FROM zx_question WHERE zx_question.qu_knowledge_point_id=(SELECT zx_knowledge_point.id FROM zx_knowledge_point WHERE kp_name='古文练习');
SELECT sb_name,sb_shortcut,sb_title FROM zx_subject WHERE id=3;
SELECT zx_question.std_answer FROM zx_question WHERE zx_question.id=2;
SELECT kp_name FROM zx_knowledge_point,zx_question WHERE zx_knowledge_point.id=zx_question.qu_knowledge_point_id AND zx_question.id=1
SELECT * FROM zx_knowledge_point WHERE kp_subject_id=1;
SELECT qu_knowledge_point_id FROM zx_question WHERE id=2;

INSERT INTO zx_question(id,question_content,std_answer) VALUES(1,'爱好','A');
INSERT INTO zx_question(id,question_content,std_answer) VALUES(2,'性别','B');

INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(1,'A','足球',1);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(2,'B','篮球',1);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(3,'C','排球',1);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(4,'D','羽毛球',1);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(5,'A','男',2);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(6,'D','女',2);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(7,'C','1条',3);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(8,'D','2条',3);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(9,'A','3条',3);
INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(10,'D','4条',3);

UPDATE zx_option SET op_question_id=1 WHERE id=2;
UPDATE zx_option SET op_code='B' WHERE id=6;
UPDATE zx_option SET op_content='正确' WHERE id=5;
UPDATE zx_option SET op_content='错误' WHERE id=6;

UPDATE zx_question SET question_content='爱好是什么？' WHERE id=1;
UPDATE zx_question SET question_content='太阳不会下山吗？' WHERE id=2;
UPDATE zx_question SET question_content=CONCAT('正方形有几条边?qid=',4) WHERE id=4;
UPDATE zx_question SET qu_knowledge_point_id=1 WHERE id=1;
UPDATE zx_question SET qu_knowledge_point_id=1 WHERE id=2;
UPDATE zx_question SET qu_label_id=2 WHERE id=1;
UPDATE zx_question SET qu_label_id=1 WHERE id=2;
UPDATE zx_question SET question_analyse='分析：该题答案不唯一，开放题。' WHERE id=1;
UPDATE zx_question SET question_analyse='分析：该题过于简单，不给予分析。' WHERE id=2;
UPDATE zx_question SET question_type=0 WHERE id=1;
UPDATE zx_question SET question_type=2 WHERE id=2;
UPDATE zx_subject SET sb_shortcut='yw' WHERE id=1;
UPDATE zx_subject SET sb_shortcut='sx' WHERE id=2;
UPDATE zx_subject SET sb_shortcut='yy' WHERE id=3;
UPDATE zx_subject SET sb_title='上海市考纲-语文知识点练习' WHERE id=1;
UPDATE zx_subject SET sb_title='上海市考纲-数学知识点练习' WHERE id=2;
UPDATE zx_subject SET sb_title='上海市考纲-英语知识点练习' WHERE id=3;
UPDATE zx_knowledge_point SET kp_shortcut='gwlx' WHERE id=1;
UPDATE zx_knowledge_point SET kp_shortcut='xdwydlj' WHERE id=2;
UPDATE zx_knowledge_point SET kp_shortcut='sgsx' WHERE id=3;
UPDATE zx_knowledge_point SET kp_shortcut='dssqj' WHERE id=4;
UPDATE zx_knowledge_point SET kp_shortcut='ltjh' WHERE id=5;
UPDATE zx_knowledge_point SET kp_shortcut='kjxl' WHERE id=6;
UPDATE zx_knowledge_point SET kp_shortcut='wxtk' WHERE id=7;
UPDATE zx_knowledge_point SET kp_shortcut='ktzw' WHERE id=8;
UPDATE zx_user_question_hist SET qh_user_question_hist='kasd' WHERE id=1;
UPDATE zx_user_question_hist SET qh_user_id=3 WHERE id=3;

INSERT INTO zx_answer(id,answer,an_question_id,an_user_id) VALUES(1,'C',1,1);
INSERT INTO zx_answer(id,answer,an_question_id,an_user_id) VALUES(2,'A',2,1);

INSERT INTO zx_subject(id,sb_name) VALUES(1,'语文');
INSERT INTO zx_subject(id,sb_name) VALUES(2,'数学');
INSERT INTO zx_subject(id,sb_name) VALUES(3,'英语');

INSERT INTO zx_knowledge_point(id,kp_name,kp_subject_id)	VALUES(1,'古文练习',1);
INSERT INTO zx_knowledge_point(id,kp_name,kp_subject_id)	VALUES(2,'现代文阅读理解',1);
INSERT INTO zx_knowledge_point(id,kp_name,kp_subject_id)	VALUES(3,'诗歌赏析',1);
INSERT INTO zx_knowledge_point(id,kp_name,kp_subject_id)	VALUES(4,'代数式求解',2);
INSERT INTO zx_knowledge_point(id,kp_name,kp_subject_id)	VALUES(5,'立体几何',2);
INSERT INTO zx_knowledge_point(id,kp_name,kp_subject_id)	VALUES(6,'空间向量',2);
INSERT INTO zx_knowledge_point(id,kp_name,kp_subject_id)	VALUES(7,'完形填空',3);
INSERT INTO zx_knowledge_point(id,kp_name,kp_subject_id)	VALUES(8,'看图作文',3);

INSERT INTO zx_label(id,lb_name) VALUES(1,'常识题');
INSERT INTO zx_label(id,lb_name) VALUES(2,'智力题');

INSERT INTO zx_user_question_hist(id,qh_user_question_hist,qh_user_id) VALUES(1,'xxx',1);

INSERT INTO zx_question(id,question_content,std_answer,qu_knowledge_point_id,qu_label_id,question_analyse,question_type)
VALUES(3,'三角形有几条边','C',5,1,'分析：三条边',0);

DESC zx_question;

DELETE FROM zx_question WHERE id=21;
DELETE FROM zx_option WHERE op_question_id=21;

ALTER TABLE zx_question AUTOINCREMENT = 22
-- 存储过程

DELIMITER $
CREATE PROCEDURE pro_testWhile(IN num INT)
BEGIN
    -- 定义一个局部变量
    DECLARE i INT DEFAULT 0;
    WHILE i<=num DO
    INSERT INTO zx_question(id,question_content,std_answer,qu_knowledge_point_id,qu_label_id,question_analyse,question_type)
    VALUES(23+i,CONCAT('立方体有几个面?qid=',23+i),'C',5,2,'分析：六个面',0);
    
    INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(87+i*4,'A','1个',23+i);
	INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(88+i*4,'B','2个',23+i);
	INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(89+i*4,'C','6个',23+i);
	INSERT INTO zx_option(id,op_code,op_content,op_question_id) VALUES(90+i*4,'D','10个',23+i);
	SET i=i+1;
    END WHILE;
END $

CALL pro_testWhile(80);

DROP PROCEDURE pro_testWhile;
ROLLBACK;


