
-- 建立會員資料表
CREATE TABLE wa_member (
                           id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主鍵欄位，用來唯一識別每一筆資料',
                           email VARCHAR(255) NOT NULL COMMENT '存儲信箱的欄位，最大長度為 255 個字元，且不能為空',
                           pwd VARCHAR(255) NOT NULL COMMENT '密碼',
                           name VARCHAR(255) NOT NULL COMMENT '存儲姓名的欄位，最大長度為 255 個字元，且不能為空',
                           phone VARCHAR(20) COMMENT '存儲電話號碼的欄位，最大長度為 20 個字元，且不能為空',
                           birthday DATE COMMENT '存儲生日的欄位，使用 DATE 型別，且不能為空',
                           organization VARCHAR(255) COMMENT '存儲機構的欄位，最大長度為 255 個字元，且不能為空',
                           grade INT COMMENT '存儲年級的欄位，使用整數型別，且不能為空',
                           role VARCHAR(20) NOT NULL COMMENT '使用者角色',
                           lastIp VARCHAR(50) COMMENT '登入IP',
                           lastLogin TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最後登入時間',
                           status INT NOT NULL COMMENT '帳號狀態 0:未啟用 1:啟用 2:停用',
                           gender INT COMMENT '性別 （0:女 1:男 2:其他）',
                           category VARCHAR(50) COMMENT '機構種類',
                           registration_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '存儲註冊時間的欄位，使用 TIMESTAMP 型別，預設為當前時間',
                           verificationCode VARCHAR(100) COMMENT '帳號啟用驗證碼'
) COMMENT='會員資料表';

-- 建立啟動碼資料表
CREATE TABLE wa_license (
                            id int NOT NULL AUTO_INCREMENT COMMENT '序號',
                            license_key varchar(255) NOT NULL COMMENT '序號',
                            activated tinyint(1) DEFAULT '0' COMMENT '啟用狀態 0:未啟用 1:已啟用 2:已失效',
                            activation_date timestamp NULL DEFAULT NULL COMMENT '啟用日期',
                            expiration_date timestamp NOT NULL COMMENT '到期日期',
                            create_date timestamp NOT NULL COMMENT '生成日期',
                            customer_name varchar(255) COMMENT '客戶姓名',
                            customer_email varchar(255) COMMENT '客戶信箱',
                            generate varchar(255) NOT NULL COMMENT '生成方式',
                            PRIMARY KEY (id),
                            UNIQUE KEY license_key (license_key)
) COMMENT='序號資料表';


-- 創建「訂單」表
CREATE TABLE wa_orders (
                           order_id INT NOT NULL COMMENT '訂單編號',
                           user_id INT NOT NULL COMMENT '用戶編號',
                           recipient VARCHAR(255) NOT NULL COMMENT '收件人姓名',
                           phone VARCHAR(255) NOT NULL COMMENT '收件人電話',
                           address VARCHAR(255) NOT NULL COMMENT '收件地址',
                           total_price DECIMAL(10,2) NOT NULL COMMENT '總價格',
                           status VARCHAR(255) NOT NULL COMMENT '訂單狀態 0:完成 1:未完成 2:失敗',
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
                           goal JSON NULL COMMENT '教學目標',
                           tips JSON NULL COMMENT '給老師的提醒',
                           preamble JSON NULL COMMENT '前導說明',
                           image VARCHAR(255) COMMENT '劇本封面路徑',
                           update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '劇本更新時間',
                           status INTEGER NOT NULL DEFAULT 0 COMMENT '劇本狀態 0:正常 1:關閉',
                           PRIMARY KEY (script_id)
) COMMENT='劇本表';

-- 建立每日劇本詳情表
CREATE TABLE wa_script_detail (
                                  script_detail_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '劇本詳情ID',
                                  script_id INT COMMENT '劇本ID',
                                  period INT COMMENT '實際建議天數',
                                  advisory_time INT COMMENT '建議時間(分)',
                                  description VARCHAR(255) COMMENT '方式說明',
                                  today_script TEXT COMMENT '本日劇情',
                                  additional_info JSON COMMENT '額外信息',
                                  teaching_url JSON COMMENT '教材文件（可儲存多個路徑）',
                                  stu_content VARCHAR(500) COMMENT '學生討論內容',
                                  par_content VARCHAR(500) COMMENT '家長討論內容',
                                  FOREIGN KEY (script_id) REFERENCES wa_script(script_id)
) COMMENT '每日劇本詳情表';

-- 建立學生討論內容標準表
CREATE TABLE wa_script_student_config (
                                          id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
                                          script_detail_id INT COMMENT '每日劇本ID',
                                          stu_description VARCHAR(255) COMMENT '選項描述',
                                          stu_orderly INT COMMENT '秩序 -1:扣分 0:不計分 1:計分',
                                          stu_relation INT COMMENT '關係 -1:扣分 0:不計分 1:計分',
                                          FOREIGN KEY (script_detail_id) REFERENCES wa_script_detail(script_detail_id)
) COMMENT '學生討論內容標準表';

-- 建立家長討論內容標準表
CREATE TABLE wa_script_parent_config (
                                         id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
                                         script_detail_id INT COMMENT '每日劇本ID',
                                         par_description VARCHAR(255) COMMENT '選項描述',
                                         par_orderly INT COMMENT '秩序 -1:扣分 0:不計分 1:計分',
                                         par_relation INT COMMENT '關係 -1:扣分 0:不計分 1:計分',
                                         FOREIGN KEY (script_detail_id) REFERENCES wa_script_detail(script_detail_id)
) COMMENT '家長討論內容標準表';

-- 建立劇本結局表
CREATE TABLE wa_script_ending (
                                  script_id INT PRIMARY KEY COMMENT '劇本ID',
                                  advisory_time INT COMMENT '建議時間',
                                  ending_description TEXT COMMENT '結局說明(分)',
                                  ending_one TEXT COMMENT '結局一描述',
                                  ending_two TEXT COMMENT '結局二描述',
                                  ending_three TEXT COMMENT '結局三描述',
                                  ending_four TEXT COMMENT '結局四描述',
                                  orderly_one INT COMMENT '結局一秩序配分設定 0負面 1正面',
                                  orderly_two INT COMMENT '結局二秩序配分設定 0負面 1正面',
                                  orderly_three INT COMMENT '結局三秩序配分設定 0負面 1正面',
                                  orderly_four INT COMMENT '結局四秩序配分設定 0負面 1正面',
                                  relation_one INT COMMENT '結局一關係配分設定 0負面 1正面',
                                  relation_two INT COMMENT '結局二關係配分設定 0負面 1正面',
                                  relation_three INT COMMENT '結局三關係配分設定 0負面 1正面',
                                  relation_four INT COMMENT '結局四關係配分設定 0負面 1正面',
                                  FOREIGN KEY (script_id) REFERENCES wa_script(script_id)
) COMMENT '劇本結局表';

-- 建立問題統計表
CREATE TABLE wa_script_question (
                                    question_id INT NOT NULL AUTO_INCREMENT COMMENT '選項唯一 ID',
                                    task_id INT NOT NULL COMMENT '任務 ID',
                                    script_id INT NOT NULl COMMENT '劇本 ID',
                                    period INT COMMENT '天數',
                                    par_ans VARCHAR(10) NOT NULL COMMENT '家長答案選項',
                                    stu_ans VARCHAR(10) NOT NULL COMMENT '學生答案選項',
                                    PRIMARY KEY (question_id)
) COMMENT='問題統計表';

-- 建立媒體資料表
CREATE TABLE wa_media (
                          script_id INT NOT NULL COMMENT '所屬劇本的 ID',
                          description VARCHAR(50) NOT NULL COMMENT '圖片所屬位置說明 cover:封面, day-1:劇本第一日',
                          media_type VARCHAR(20) NOT NULL COMMENT '媒體類型，可以是圖片或影片文件',
                          file_path VARCHAR(255) NOT NULL COMMENT '媒體檔案路徑',
                          filename VARCHAR(100) NOT NULL COMMENT '檔案名稱',
                          create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '媒體創建時間',
                          update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '媒體更新時間',
                          PRIMARY KEY (script_id, description)
) COMMENT='媒體資料表';


-- 任務表，存儲任務的基本信息
CREATE TABLE wa_task (
                         task_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '任務ID',
                         task_name VARCHAR(255) NOT NULL COMMENT '任務名稱',
                         member_Id INTEGER NOT NULL COMMENT '任務所屬會員',
                         script_id INT NOT NULL COMMENT '劇本ID',
                         description TEXT COMMENT '任務描述',
                         priority INTEGER DEFAULT 0 COMMENT '任務優先級',
                         estimated_participants INTEGER DEFAULT 0 COMMENT '預計參與人數',
                         status INTEGER DEFAULT 0 COMMENT '任務狀態 0:開啟任務 1:進行中 2:完成(包含結束)',
                         create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
                         end_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '結束時間',
                         learning INTEGER DEFAULT 0 COMMENT '0:小學 (低年級)
                             1:小學 (中年級)
                             2:小學 (高年級)
                             3:國中 (7年級)
                             4:國中 (8年級)
                             5:國中 (9年級)',
                         author VARCHAR(255) NOT NULL COMMENT '最後編輯者'
) COMMENT='任務資料表';


-- 任務分配表，存儲任務分配的信息
CREATE TABLE wa_task_assignment (
                                    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '分配ID',
                                    task_id INT NOT NULL COMMENT '任務ID',
                                    assignee_id INT NOT NULL COMMENT '受分配人ID',
                                    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '分配時間',
                                    FOREIGN KEY (task_id) REFERENCES wa_task(task_id),
                                    FOREIGN KEY (assignee_id) REFERENCES wa_member(id)
) COMMENT='任務分配表';

-- 登入紀錄表
CREATE TABLE wa_login_records (
                                  id INT AUTO_INCREMENT PRIMARY KEY COMMENT '登入ID',
                                  user_id INT COMMENT '用戶ID',
                                  login_time DATETIME COMMENT '登入時間',
                                  login_type INT COMMENT '登入方式 0:一般登入 1:google Auth登入'
) COMMENT='登入紀錄表';



