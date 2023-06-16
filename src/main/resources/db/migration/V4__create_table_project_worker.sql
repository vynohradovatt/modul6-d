CREATE TABLE IF NOT EXISTS project_worker(
    project_id INTEGER,
    worker_id INTEGER,
    PRIMARY KEY(project_id, worker_id),
    FOREIGN KEY(project_id) REFERENCES project(id),
    FOREIGN KEY(worker_id) REFERENCES worker(id)
);