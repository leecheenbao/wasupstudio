# wasupstudio

這個專案是基於 Java8 和 Spring Boot + Spring Security + JWT 技術開發，
使用 MySQL 資料庫以及 Mybatis ORM。

***
## 系統需求
在開始使用這個專案之前，請確保您的系統符合以下需求：

* Java 8 或以上版本
* Maven 用於編譯和打包專案
* 具備 Spring Boot 和 Spring Security 知識
* 具備 JWT 相關知識
* MySql資料庫

***
## 安裝與使用
1. 下載專案的壓縮檔或使用 Git Clone 指令複製專案到本地端。
2. 使用docker-compose啟動專案
````````````
docker-compose up -d 
````````````
3. 開啟網頁瀏覽器，並輸入以下網址開啟Swagger：
````````````
localhost:8080/wasupstudio
````````````


---
## 功能項目
* 註冊 / 登入(含Google三方)
* 使用者管理(一般使用者、管理員)
* 任務管理
* 劇本管理
* 教材管理
  * 啟動碼管理
  * 使用者統計檢視
* 金流串接