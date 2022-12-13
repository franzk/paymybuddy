# paymybuddy

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
