# ☕ Java Mentor Hub

> A personal platform for Java knowledge sharing, interview practice, and learning progress tracking.

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)
[![React](https://img.shields.io/badge/React-19-61DAFB?logo=react)](https://react.dev/)
[![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker)](https://www.docker.com/)

---

## 🎯 What is Java Mentor Hub?

Java Mentor Hub is a personal platform built to:

- 📚 **Share Java knowledge** — curated books, documents, and study materials
- 🎯 **Practice interview questions** — question bank with difficulty levels (Intern → Senior)
- 📊 **Track learning progress** — completion percentage per topic
- 💻 **Solve assignments** — coding exercises from easy to hard
- 👤 **About me** — introducing myself as a Java Software Engineer

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Java 17, Spring Boot 4.0.5, Spring Security (JWT), Spring Data JPA, Flyway |
| **Database** | PostgreSQL 16 (Docker local / Supabase production) |
| **Frontend** | React 19, Vite, React Router, Axios |
| **DevOps** | Docker, GitHub Actions, Render (backend), Vercel (frontend) |

---

## 🚀 Getting Started (Local Development)

### Prerequisites
- Java 17+
- Docker Desktop
- Node.js 18+

### 1. Clone the repository
```bash
git clone https://github.com/thanhdatpb/Java_Mentor_Hub.git
cd Java_Mentor_Hub
```

### 2. Start PostgreSQL with Docker
```bash
docker-compose up -d
```

### 3. Run Backend
```bash
cd backend
./gradlew bootRun
```
API will be available at: `http://localhost:8080`
Swagger UI: `http://localhost:8080/swagger-ui.html`

### 4. Run Frontend
```bash
cd frontend
npm install
npm run dev
```
App will be available at: `http://localhost:5173`

---

## 📁 Project Structure

```
Java_Mentor_Hub/
├── backend/          # Spring Boot REST API
├── frontend/         # React + Vite
├── docker-compose.yml
└── README.md
```


## 👤 Author

**Trần Thành Đạt** 

- 🎓 Khoa Công nghệ thông tin, Trường Đại học Khoa học, Đại học Huế
- 🏃 Runner · Photo Freelance · Learner · Software Engineer
- 💼 [GitHub](https://github.com/thanhdatpb)
- 🔗 [LinkedIn](https://linkedin.com/in/thanhdatpb)
- 📧 Tthanhdat.pb@gmail.com

---

> *"Build to learn, learn to build."*
