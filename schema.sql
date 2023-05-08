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
                           lastLogin TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最後登入時間',
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
                           order_id INT NOT NULL AUTO_INCREMENT COMMENT '訂單編號',
                           user_id INT NOT NULL COMMENT '用戶編號',
                           recipient VARCHAR(255) NOT NULL COMMENT '收件人姓名',
                           phone VARCHAR(255) NOT NULL COMMENT '收件人電話',
                           address VARCHAR(255) NOT NULL COMMENT '收件地址',
                           total_price DECIMAL(10,2) NOT NULL COMMENT '總價格',
                           status VARCHAR(255) NOT NULL COMMENT '訂單狀態',
                           create_time TIMESTAMP NOT NULL COMMENT '訂單創建時間',
                           update_time TIMESTAMP NOT NULL COMMENT '訂單更新時間',
                           PRIMARY KEY (order_id)
) COMMENT='訂單表';

-- 創建「訂單明細」表
CREATE TABLE wa_order_items (
                                order_item_id INT NOT NULL AUTO_INCREMENT COMMENT '訂單項目編號',
                                order_id INT NOT NULL COMMENT '訂單編號',
                                product_id INT NOT NULL COMMENT '產品編號',
                                price DECIMAL(10,2) NOT NULL COMMENT '產品價格',
                                quantity INT NOT NULL COMMENT '產品數量',
                                PRIMARY KEY (order_item_id),
                                FOREIGN KEY (order_id) REFERENCES wa_orders(order_id)
) COMMENT='訂單明細表';

-- 建立商品管理表
CREATE TABLE wa_products (
                             product_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
                             name VARCHAR(255) NOT NULL COMMENT '商品名稱',
                             price DECIMAL(10, 2) NOT NULL COMMENT '商品價格',
                             quantity INT NOT NULL COMMENT '商品數量 -1為無限量'
) COMMENT '商品管理表';


-- 建立劇本表
CREATE TABLE wa_script (
                           script_id INT NOT NULL AUTO_INCREMENT COMMENT '劇本唯一 ID',
                           title VARCHAR(255) NOT NULL COMMENT '劇本標題',
                           author VARCHAR(255) NOT NULL COMMENT '劇本作者',
                           description TEXT COMMENT '劇本描述',
                           create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '劇本創建時間',
                           script_period INTEGER NOT NULL DEFAULT 0 COMMENT '劇本時長(天)',
                           goal VARCHAR(255) COMMENT '教學目標',
                           tips VARCHAR(255) COMMENT '給老師的提醒',
                           image VARCHAR(255) COMMENT '劇本封面路徑',
                           update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '劇本更新時間',
                           status INTEGER NOT NULL DEFAULT 0 COMMENT '劇本狀態 0:正常 1:關閉',
                           PRIMARY KEY (script_id)
) COMMENT='劇本表';

-- 建立劇本詳情表
CREATE TABLE wa_script_detail (
                                  script_detail_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '劇本詳情ID',
                                  script_id INT COMMENT '劇本ID',
                                  advisory_time DATETIME COMMENT '建議時間',
                                  method_description VARCHAR(255) COMMENT '方式說明',
                                  today_script TEXT COMMENT '本日劇情',
                                  additional_info TEXT COMMENT '額外信息',
                                  teaching_materials JSON COMMENT '教材文件（可儲存多個路徑）',
                                  FOREIGN KEY (script_id) REFERENCES wa_script(script_id)
) COMMENT '劇本詳情表';

-- 建立劇本結局表
CREATE TABLE wa_script_endding (
                                   script_endding_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '劇本結局ID',
                                   script_id INT COMMENT '劇本ID',
                                   advisory_time DATETIME COMMENT '建議時間',
                                   endding_description TEXT COMMENT '結局說明',
                                   orderly_credit BOOLEAN COMMENT '秩序配分設定 true正數 false負數',
                                   relation_credit BOOLEAN COMMENT '關係配分設定 true正數 false負數',
                                   file_path JSON COMMENT '教材影音檔（可儲存多個路徑）',
                                   FOREIGN KEY (script_id) REFERENCES wa_script(script_id)
) COMMENT '劇本結局表';

-- 學習單問卷表
CREATE TABLE wa_script_learning (
                                    learning_id INT NOT NULL AUTO_INCREMENT COMMENT '學習單問卷唯一 ID',
                                    script_id INT COMMENT '劇本ID',
                                    title VARCHAR(255) NOT NULL COMMENT '學習單問卷標題',
                                    description TEXT COMMENT '學習單問卷描述',
                                    create_time TIMESTAMP NOT NULL COMMENT '學習單建立時間',
                                    update_time TIMESTAMP NOT NULL COMMENT '學習單更新時間',
                                    PRIMARY KEY (learning_id),
                                    FOREIGN KEY (script_id) REFERENCES wa_script(script_id)
) COMMENT='學習單問卷表';

-- 建立問題表
CREATE TABLE wa_script_question (
                                    question_id INT NOT NULL AUTO_INCREMENT COMMENT '問題唯一 ID',
                                    content TEXT NOT NULL COMMENT '問題內容',
                                    learning_id INT NOT NULL COMMENT '學習單問卷 ID',
                                    PRIMARY KEY (question_id),
                                    FOREIGN KEY (learning_id) REFERENCES wa_script_learning (learning_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='問題表';

-- 建立選項表
CREATE TABLE wa_script_question_option (
                                           option_id INT NOT NULL AUTO_INCREMENT COMMENT '選項唯一 ID',
                                           content TEXT NOT NULL COMMENT '選項內容',
                                           question_id INT NOT NULL COMMENT '問題 ID',
                                           score_ratio DECIMAL(4,2) NOT NULL COMMENT '選項配分比例',
                                           PRIMARY KEY (option_id),
                                           FOREIGN KEY (question_id) REFERENCES wa_script_question (question_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='選項表';

-- 建立媒體資料表
CREATE TABLE wa_media (
                          media_id INT NOT NULL AUTO_INCREMENT COMMENT '媒體唯一 ID',
                          script_id INT NOT NULL COMMENT '所屬劇本的 ID',
                          media_type ENUM('image', 'video','docs') NOT NULL COMMENT '媒體類型，可以是圖片或影片文件',
                          file_path VARCHAR(255) NOT NULL COMMENT '媒體檔案路徑',
                          create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '媒體創建時間',
                          update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '媒體更新時間',
                          PRIMARY KEY (media_id)
) COMMENT='媒體資料表';


-- 任務表，存儲任務的基本信息
CREATE TABLE wa_task (
                         task_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '任務ID',
                         task_name VARCHAR(255) NOT NULL COMMENT '任務名稱',
                         member_Id INTEGER NOT NULL COMMENT '任務所屬會員',
                         description TEXT COMMENT '任務描述',
                         priority INTEGER DEFAULT 0 COMMENT '任務優先級',
                         estimated_participants INTEGER DEFAULT 0 COMMENT '預計參與人數',
                         status INTEGER DEFAULT 0 COMMENT '任務狀態 0:開啟任務 1:進行中 2:完成',
                         create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
                         update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新時間',
                         author VARCHAR(255) NOT NULL COMMENT '最後編輯者'
);

-- 任務分配表，存儲任務分配的信息
CREATE TABLE wa_task_assignment (
                                    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '分配ID',
                                    task_id INT NOT NULL COMMENT '任務ID',
                                    assignee_id INT NOT NULL COMMENT '受分配人ID',
                                    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '分配時間',
                                    FOREIGN KEY (task_id) REFERENCES wa_task(task_id),
                                    FOREIGN KEY (assignee_id) REFERENCES wa_member(id)
);
