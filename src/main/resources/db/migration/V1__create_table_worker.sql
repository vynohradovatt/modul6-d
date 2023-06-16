CREATE TABLE worker (
    id IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL CHECK (LENGTH(name) > 2 AND LENGTH(name) <=100),
    birthday DATE NOT NULL CHECK (YEAR(birthday) > 1900),
    level VARCHAR(50) NOT NULL CHECK (level IN ('trainee', 'junior', 'middle', 'senior')),
    salary_for_a_month INT NOT NULL CHECK(salary_for_a_month > 100 AND salary_for_a_month < 100000)

);