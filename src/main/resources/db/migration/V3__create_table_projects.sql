CREATE TABLE IF NOT EXISTS project(
    id IDENTITY PRIMARY KEY,
    client_id INT,
    start_date DATE,
    finish_date DATE
);