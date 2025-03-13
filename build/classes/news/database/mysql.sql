DROP TABLE IF EXISTS news_category;

CREATE TABLE news_category(
news_category_id VARCHAR(5) PRIMARY KEY, 
category_name 	VARCHAR(255) NOT NULL,
created_time TIMESTAMP	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO news_category( news_category_id, category_name, created_time ) VALUES 
('NC001', '重要公告', NOW()),
('NC002', '活動', NOW()),
('NC003', '空間', NOW());


DROP TABLE IF EXISTS news_status;

CREATE TABLE news_status(
news_status_id INT PRIMARY KEY,
category_name VARCHAR(255) NOT NULL,
created_time TIMESTAMP	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO news_status ( news_status_id, category_name, created_time ) VALUES 
(0, '過期'  ,NOW()),
(1, '上架中' ,NOW()),
(2, '即將發布' ,NOW());

DROP TABLE IF EXISTS news;

CREATE TABLE news (
news_id VARCHAR(5)	PRIMARY KEY comment "最新消息流水號",
news_title VARCHAR(20)	NOT NULL comment "消息標題",
news_content VARCHAR(500) NOT NULL comment "消息內容",
news_start_date	TIMESTAMP NOT NULL comment "消息起始日",
news_end_date TIMESTAMP	NOT NULL comment "消息結束日", 
news_img	VARCHAR(256) comment "消息圖片",		
created_time TIMESTAMP	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment "建檔時間",
news_category_id_fk VARCHAR(5) NOT NULL comment "消息種類",
admin_id_fk VARCHAR(5) NOT NULL comment "管理員ID",
news_status_id_fk INT NOT NULL comment "消息狀態",
CONSTRAINT news_category_id_fk FOREIGN KEY (news_category_id) REFERENCES news_category(news_category_id),
CONSTRAINT admin_id_fk FOREIGN KEY (admin_id) REFERENCES admin(admin_id),
CONSTRAINT news_status_id_fk FOREIGN KEY (news_status_id) REFERENCES news_status(news_status_id)
);

INSERT INTO news ( news_id, news_title, news_content, news_start_date, news_end_date, news_img, created_time, news_category_id_fk, admin_id_fk, news_status_id_fk ) VALUES 
('N001', '最新優惠'  ,'優惠優惠優惠優惠優惠',STR_TO_DATE('2025-02-18','%Y-%m-%d'),STR_TO_DATE('2025-03-18','%Y-%m-%d'),NULL, NOW(),'NC001','A001', 1),
('N002', '裝潢公告' ,'公告公告公告公告公告'  ,STR_TO_DATE('2025-02-19','%Y-%m-%d'),STR_TO_DATE('2025-03-18','%Y-%m-%d'),NULL, NOW(),'NC003','A001', 1),
('N003', '優惠券發放' ,'發放發放發放發放發放'  ,STR_TO_DATE('2025-02-01','%Y-%m-%d'),STR_TO_DATE('2025-02-05','%Y-%m-%d'),NULL, NOW(),'NC001','A001', 0),
('N004', '徵才消息' ,'消息消息消息消息消息'  ,STR_TO_DATE('2025-02-21','%Y-%m-%d'),STR_TO_DATE('2025-03-18','%Y-%m-%d'),NULL, NOW(),'NC001','A001', 2),
('N005', '春節優惠','春節春節春節春節春節' ,STR_TO_DATE('2025-02-03','%Y-%m-%d'),STR_TO_DATE('2025-02-05','%Y-%m-%d'),NULL, NOW(),'NC002','A001', 0);

