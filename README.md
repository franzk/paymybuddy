# paymybuddy

<img src="https://img.shields.io/badge/-JAVA%2011-00A7BB?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/-SPRING%20BOOT%202.7.5-6eb442?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/-SPRING%20SECURITY-1a5900?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/-SPRING%20WEB-397200?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/-SPRING%20DATA%20JPA-8db411?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/-MYSQL-006189?style=for-the-badge&logo=mysql&logoColor=white"> 
<br> <img src="https://img.shields.io/badge/-MAVEN-black?style=for-the-badge&logo=apachemaven&logoColor=white"> <img src="https://img.shields.io/badge/-JACOCO-810a00?style=for-the-badge">
<br><img src="https://img.shields.io/badge/-ANGULAR-c41829?style=for-the-badge&logo=angular&logoColor=white"> 

Pay My Buddy est une application web qui fait l'objet du Projet 6 de la formation développement d'applications JAVA d'Openclassrooms. Cette appli permettrait aux clients de transférer de l'argent pour gérer leurs finances ou payer leurs amis.  
Le back office est en Java Spring Boot, le front office en Angular.  
L'énoncé fournit la maquette de l'UI qui doit être intégrée telle quelle.


## Getting Started

### 1. Clone the application

```bash
$ git clone https://github.com/franzk/paymybuddy.git
```

### 2. Create a MySQL database

See intallation scripts : <https://github.com/franzk/paymybuddy/tree/main/back/paymybuddy/database_files>

## 3. Create properties files

Create 3 files in the folder /back/paymybuddy/external_config, like this :

### datasource.properties

spring.datasource.url=    

spring.datasource.username=  

spring.datasource.password=   

---

### datasource_tests.properties

spring.datasource.url=  

spring.datasource.username=  

spring.datasource.password=  

---

### mailsender.properties

spring.mail.host=  

spring.mail.port=  

spring.mail.username=  

spring.mail.password=  

spring.mail.properties.mail.smtp.auth=  

spring.mail.properties.mail.smtp.ssl.enable=  

com.pmb.paymybuddy.emailaddress=  


---

## Domain Model

![PayMyBuddy Domain Model](/doc/paymybuddy_domain_model.png)

## Database Diagram

![PayMyBuddy Database Diagram Model](/doc/paymybuddy_database_diagram.png)
