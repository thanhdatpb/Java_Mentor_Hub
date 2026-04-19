-- =============================================
-- Java Mentor Hub - Initial Database Schema
-- Version: V1
-- Database: PostgreSQL 16
-- =============================================

-- Roles table
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    avatar_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User-Role many-to-many join table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Topics (Java Core, Spring, OOP, etc.)
CREATE TABLE topics (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    icon VARCHAR(10),
    display_order INT DEFAULT 0
);

-- Question bank for interview practice
CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT REFERENCES topics(id) ON DELETE SET NULL,
    title VARCHAR(500) NOT NULL,
    content TEXT NOT NULL,
    difficulty VARCHAR(20) NOT NULL,
    type VARCHAR(30) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Answers for questions
CREATE TABLE answers (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL REFERENCES questions(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    explanation TEXT
);

-- Assignments / Exercises
CREATE TABLE assignments (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT REFERENCES topics(id) ON DELETE SET NULL,
    title VARCHAR(500) NOT NULL,
    description TEXT,
    difficulty VARCHAR(20) NOT NULL,
    starter_code TEXT,
    solution TEXT,
    hints TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Recommended books
CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT REFERENCES topics(id) ON DELETE SET NULL,
    name VARCHAR(500) NOT NULL,
    author VARCHAR(200),
    description TEXT,
    cover_url VARCHAR(500),
    download_link VARCHAR(500),
    level VARCHAR(20) NOT NULL,
    display_order INT DEFAULT 0
);

-- Practice session history
CREATE TABLE practice_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    question_id BIGINT REFERENCES questions(id) ON DELETE SET NULL,
    user_answer TEXT,
    is_correct BOOLEAN,
    score INT DEFAULT 0 CHECK (score >= 0 AND score <= 100),
    practiced_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Assignment progress tracking
CREATE TABLE assignment_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    assignment_id BIGINT NOT NULL REFERENCES assignments(id) ON DELETE CASCADE,
    status VARCHAR(20) DEFAULT 'NOT_STARTED',
    submission TEXT,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    UNIQUE (user_id, assignment_id)
);

-- Overall progress per topic per user
CREATE TABLE progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    questions_answered INT DEFAULT 0,
    questions_correct INT DEFAULT 0,
    assignments_completed INT DEFAULT 0,
    completion_percentage FLOAT DEFAULT 0.0,
    last_activity TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, topic_id)
);

-- =============================================
-- Create indexes for performance
-- =============================================
CREATE INDEX idx_questions_topic ON questions(topic_id);
CREATE INDEX idx_questions_difficulty ON questions(difficulty);
CREATE INDEX idx_questions_type ON questions(type);
CREATE INDEX idx_answers_question ON answers(question_id);
CREATE INDEX idx_assignments_topic ON assignments(topic_id);
CREATE INDEX idx_books_topic ON books(topic_id);
CREATE INDEX idx_practice_user ON practice_sessions(user_id);
CREATE INDEX idx_practice_question ON practice_sessions(question_id);
CREATE INDEX idx_assignment_progress_user ON assignment_progress(user_id);
CREATE INDEX idx_progress_user ON progress(user_id);
CREATE INDEX idx_progress_topic ON progress(topic_id);

-- =============================================
-- Seed initial data
-- =============================================

-- Roles
INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

-- Topics (from Notion structure)
INSERT INTO topics (name, description, icon, display_order) VALUES
    ('Java Core', 'Kiến thức nền tảng Java: Syntax, Data Types, Collections, Streams, Lambda...', '☕', 1),
    ('OOP', 'Lập trình hướng đối tượng: Encapsulation, Inheritance, Polymorphism, Abstraction', '🧱', 2),
    ('Spring Framework', 'Spring Boot, Spring Security, Spring Data JPA, Spring MVC', '🍃', 3),
    ('Design Patterns', 'Gang of Four patterns + Enterprise patterns', '🏗️', 4),
    ('System Design', 'Thiết kế hệ thống: Scalability, Microservices, Caching, Load Balancing...', '📐', 5),
    ('Database', 'SQL, PostgreSQL, Indexing, Transaction, Normalization, Query Optimization', '🗄️', 6),
    ('Concurrency', 'Multi-threading, Synchronization, ExecutorService, CompletableFuture', '⚡', 7),
    ('Interview Q&A', 'Câu hỏi phỏng vấn thường gặp và best practices', '🎯', 8);

-- Books (from ThanhDat's Notion)
INSERT INTO books (name, author, description, level, topic_id, display_order) VALUES
    ('Head First Java A Brain Friendly Guide', 'Kathy Sierra & Bert Bates',
     'Sách cho người mới học hoặc cần củng cố lại kiến thức Java cơ bản. Rất nên đọc!',
     'BEGINNER', (SELECT id FROM topics WHERE name = 'Java Core'), 1),

    ('JAVA OCA Oracle Certified Associate Java SE 8 Programmer I Study Guide Exam 1Z0-808', 'Jeanne Boyarsky & Scott Selikoff',
     'Thi chứng chỉ JAVA thì nên đọc. Giúp nắm vững Java Core một cách có hệ thống.',
     'INTERMEDIATE', (SELECT id FROM topics WHERE name = 'Java Core'), 2),

    ('Head First Design Patterns 2nd Edition', 'Eric Freeman & Elisabeth Robson',
     'Xem để code xịn hơn. Học Design Pattern qua hình ảnh và ví dụ thực tế.',
     'INTERMEDIATE', (SELECT id FROM topics WHERE name = 'Design Patterns'), 3),

    ('Dive into Design Pattern', 'Alexander Shvets (Guru Refactoring)',
     'Sách của guru.refactor — nên đọc! Giải thích pattern rất rõ ràng với code examples.',
     'INTERMEDIATE', (SELECT id FROM topics WHERE name = 'Design Patterns'), 4),

    ('System Design Interview An Insiders Guide', 'Alex Xu',
     'Sách về System Design — bắt buộc đọc! Chuẩn bị cho interview system design.',
     'ADVANCED', (SELECT id FROM topics WHERE name = 'System Design'), 5),

    ('System Design ByteByteGo', 'Alex Xu (LinkedIn)',
     'Xem để biết system design có những gì. Tài liệu từ ByteByteGo newsletter.',
     'ADVANCED', (SELECT id FROM topics WHERE name = 'System Design'), 6);
