DROP TABLE IF exists employee;

CREATE TABLE employee (
  id              int IDENTITY not null,
  created_date    timestamp,
  updated_date    timestamp,
  fname           varchar(100),
  lname           varchar(100),
  email           varchar(100),
  title           varchar(100),
  salary          double,
  primary key (id)
);

SELECT * FROM EMPLOYEE;