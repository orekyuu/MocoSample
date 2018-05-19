CREATE TABLE companies (
  id     INT AUTO_INCREMENT PRIMARY KEY,
  name   VARCHAR (128) NOT NULL,
  UNIQUE INDEX (name)
) ENGINE = InnoDB;

CREATE TABLE employees (
  id            INT AUTO_INCREMENT PRIMARY KEY,
  company_id    INT NOT NULL,
  age           INT NOT NULL,
  name          VARCHAR (128) NOT NULL,
  job_category  VARCHAR (128) NOT NULL,
  INDEX (job_category),
  INDEX (age),
  FOREIGN KEY (company_id) REFERENCES companies(id)
) ENGINE = InnoDB;
