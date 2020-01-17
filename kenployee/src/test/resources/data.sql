TRUNCATE TABLE EMPLOYEES;
COMMIT;
INSERT INTO
	EMPLOYEES (first_name, middle_i, last_name, date_of_birth, date_of_employment, active_status)
VALUES
  	('Lokesh','','Gupta', to_date('02/12/1975', 'mm/dd/yyyy'), to_date('06/04/2001', 'mm/dd/yyyy'), true),
  	('John', 'Q', 'Doe', to_date('12/12/2012', 'mm/dd/yyyy'), to_date('12/12/2019', 'mm/dd/yyyy'), false);
