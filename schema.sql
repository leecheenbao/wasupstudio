-- 建立學習單問卷表
CREATE TABLE wa_learning_assessment (
                                        learning_id INT NOT NULL AUTO_INCREMENT COMMENT '學習單問卷唯一 ID',
                                        title VARCHAR(255) NOT NULL COMMENT '學習單問卷標題',
                                        description TEXT COMMENT '學習單問卷描述',
                                        create_time TIMESTAMP NOT NULL COMMENT '學習單建立時間',
                                        update_time TIMESTAMP NOT NULL COMMENT '學習單更新時間',
                                        PRIMARY KEY (learning_id)
) COMMENT='學習單問卷表';

-- 建立問題表
CREATE TABLE wa_question (
                             question_id INT NOT NULL AUTO_INCREMENT COMMENT '問題唯一 ID',
                             content TEXT NOT NULL COMMENT '問題內容',
                             learning_assessment_id INT NOT NULL COMMENT '學習單問卷 ID',
                             PRIMARY KEY (question_id),
                             FOREIGN KEY (learning_assessment_id) REFERENCES wa_learning_assessment (learning_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='問題表';

-- 建立選項表
CREATE TABLE wa_question_option (
                                    option_id INT NOT NULL AUTO_INCREMENT COMMENT '選項唯一 ID',
                                    content TEXT NOT NULL COMMENT '選項內容',
                                    question_id INT NOT NULL COMMENT '問題 ID',
                                    score_ratio DECIMAL(4,2) NOT NULL COMMENT '選項配分比例',
                                    PRIMARY KEY (option_id),
                                    FOREIGN KEY (question_id) REFERENCES wa_question (question_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='選項表';

-- 建立會員資料表
CREATE TABLE wa_member (
                           id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主鍵欄位，用來唯一識別每一筆資料',
                           email VARCHAR(255) NOT NULL COMMENT '存儲信箱的欄位，最大長度為 255 個字元，且不能為空',
                           pwd VARCHAR(255) NOT NULL COMMENT '密碼',
                           name VARCHAR(255) NOT NULL COMMENT '存儲姓名的欄位，最大長度為 255 個字元，且不能為空',
                           phone VARCHAR(20) NOT NULL COMMENT '存儲電話號碼的欄位，最大長度為 20 個字元，且不能為空',
                           birthday DATE NOT NULL COMMENT '存儲生日的欄位，使用 DATE 型別，且不能為空',
                           organization VARCHAR(255) NOT NULL COMMENT '存儲機構的欄位，最大長度為 255 個字元，且不能為空',
                           grade INT NOT NULL COMMENT '存儲年級的欄位，使用整數型別，且不能為空',
                           role VARCHAR(20) NOT NULL COMMENT '使用者角色',
                           lastIp VARCHAR(50) COMMENT '登入IP',
                           status INT NOT NULL COMMENT '帳號狀態',
                           registration_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '存儲註冊時間的欄位，使用 TIMESTAMP 型別，預設為當前時間'
);

-- 建立啟動碼資料表
CREATE TABLE wa_license (
                            id int NOT NULL AUTO_INCREMENT COMMENT '序號',
                            license_key varchar(255) NOT NULL COMMENT '序號',
                            activated tinyint(1) DEFAULT '0' COMMENT '啟用狀態',
                            activation_date timestamp NULL DEFAULT NULL COMMENT '啟用日期',
                            expiration_date timestamp NOT NULL COMMENT '到期日期',
                            customer_name varchar(255) NOT NULL COMMENT '客戶姓名',
                            customer_email varchar(255) NOT NULL COMMENT '客戶信箱',
                            PRIMARY KEY (id),
                            UNIQUE KEY license_key (license_key)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='序號資料表';


-- 創建「訂單」表
CREATE TABLE wa_orders (
                           order_id INT NOT NULL AUTO_INCREMENT,
                           user_id INT NOT NULL,
                           recipient VARCHAR(255) NOT NULL,
                           phone VARCHAR(255) NOT NULL,
                           address VARCHAR(255) NOT NULL,
                           total_price DECIMAL(10,2) NOT NULL,
                           status VARCHAR(255) NOT NULL,
                           create_time TIMESTAMP NOT NULL,
                           update_time TIMESTAMP NOT NULL,
                           PRIMARY KEY (order_id)
);


-- 創建「訂單明細」表
CREATE TABLE wa_order_items (
                                order_item_id INT NOT NULL AUTO_INCREMENT,
                                order_id INT NOT NULL,
                                product_id INT NOT NULL,
                                price DECIMAL(10,2) NOT NULL,
                                quantity INT NOT NULL,
                                PRIMARY KEY (order_item_id)
);

-- 建立劇本表
CREATE TABLE wa_script (
                           script_id INT NOT NULL AUTO_INCREMENT COMMENT '劇本唯一 ID',
                           title VARCHAR(255) NOT NULL COMMENT '劇本標題',
                           author VARCHAR(255) NOT NULL COMMENT '劇本作者',
                           description TEXT COMMENT '劇本描述',
                           create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '劇本創建時間',
                           update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '劇本更新時間',
                           status INTEGER NOT NULL DEFAULT 0 COMMENT '劇本狀態 0:正常 1:關閉',
                           PRIMARY KEY (script_id)
) COMMENT='劇本表';

-- 任務表，存儲任務的基本信息
CREATE TABLE wa_task (
                         task_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '任務ID',
                         task_name VARCHAR(255) NOT NULL COMMENT '任務名稱',
                         description TEXT COMMENT '任務描述',
                         priority INTEGER DEFAULT 0 COMMENT '任務優先級',
                         status INTEGER DEFAULT 0 COMMENT '任務狀態 0:開啟任務 1:進行中 2:完成',
                         create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
                         update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
                         author VARCHAR(255) NOT NULL COMMENT '最後編輯者'
);

-- 任務分配表，存儲任務分配的信息
CREATE TABLE task_assignment (
                                 id INT PRIMARY KEY AUTO_INCREMENT COMMENT '分配ID',
                                 task_id INT NOT NULL COMMENT '任務ID',
                                 assignee_id INT NOT NULL COMMENT '受分配人ID',
                                 assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '分配時間',
                                 FOREIGN KEY (task_id) REFERENCES wa_task(task_id),
                                 FOREIGN KEY (assignee_id) REFERENCES wa_member(id)
);

