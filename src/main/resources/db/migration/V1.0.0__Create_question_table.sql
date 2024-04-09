CREATE TABLE IF NOT EXISTS tb_question (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    tag VARCHAR(255),
    time_limit DOUBLE PRECISION,
    memory_limit DOUBLE PRECISION,
    level INTEGER,
    average_tries DOUBLE PRECISION,
    total_tries BIGINT,
    total_person BIGINT,
    success_rate DOUBLE PRECISION,
    total_success BIGINT
);