DROP TABLE IF EXISTS EMPLOYEES;

CREATE TABLE EMPLOYEES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  middle_i VARCHAR(5) DEFAULT '',
  date_of_birth DATE NOT NULL,
  date_of_employment DATE NOT NULL,
  active_status BOOLEAN DEFAULT true
);
