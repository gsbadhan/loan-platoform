create database loan_platform;

create table loan_workflow(
id BIGINT(10) AUTO_INCREMENT PRIMARY KEY,
front_office VARCHAR(30),
carloan_office VARCHAR(30),
risk_office VARCHAR(30),
disbursal_office VARCHAR(30),
update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

insert into loan_workflow values(null,'PENDING',null,null,null);