# Nexus - Collaborative Study Room Platform (Full-Stack JWT & Google OAuth)

Nexus is an advanced, high-fidelity collaborative study room platform designed to help students and professionals focus, maintain accountability, and study productively in virtual group environments. 

This repository houses an enterprise-grade full-stack architecture tailored for SDE assessments, featuring a Java Spring Boot secure API backend and an interactive Vanilla JS frontend client.

---

## 🛠 Technology Stack

### 1. Backend Architecture (`/backend`)
*   **Core Framework**: Java 17, Spring Boot 3.x (Spring MVC REST Web Services)
*   **Security & Encryption**: Spring Security 6, BCrypt Password Encoder
*   **Session Management**: Stateless authentication powered by JSON Web Tokens (JJWT library)
*   **Data Persistence**: Spring Data JPA (Java Persistence API), Hibernate ORM
*   **Database**: In-Memory H2 SQL Database (zero-setup persistence)
*   **Build Tool**: Maven

### 2. Frontend Client (`/frontend`)
*   **Layout & Styling**: Semantic HTML5, Vanilla CSS3 (Custom properties, CSS Grid/Flexbox, Glassmorphism, 3D CSS Card Flips)
*   **Logic & Routing**: Vanilla ES6 JS Modules (Dynamic component routing via reactive State Proxy binding)
*   **Cloud Authentication**: Official Google Identity Services (GSI) SDK integration (OAuth 2.0 Implicit Token Client flow)
*   **Real-time Collaboration**: HTML5 `BroadcastChannel` API (Serverless multi-tab data synchronization)
*   **Focus Sound Synthesizer**: Web Audio API (Synthesizing oscillators, low-frequency oscillators (LFO), lowpass filtering, and stereo panning nodes entirely in the browser)
*   **Audio Visualizer**: Canvas 2D API feeding off a Web Audio `AnalyserNode` for live frequency spectrum wave rendering

---

## 🚀 Step-by-Step Execution Guide

Follow these steps to run the backend, serve the client, test user authentication, and inspect the database.

---

### Step 1: Configure the Environment Variables
Before running the application, make sure the `.env` files are configured:
1.  Open the environment file at: **`frontend/.env`** (or **`/.env`** in the root).
2.  Set the following key-value pairs:
    *   `GOOGLE_CLIENT_ID`: Paste your Google Cloud Console Web Application Client ID (e.g. `12345-abc.apps.googleusercontent.com`). If you do not have one, leave it as the default placeholder to utilize local emulation mode.
    *   `API_BASE_URL`: Defaults to `http://localhost:8080/api` (Spring Boot API).

---

### Step 2: Build & Start the Java Spring Boot Backend
Ensure you have **Java JDK 17** (or higher) and **Maven** installed.

1.  Open a terminal window and navigate to the backend directory:
    ```powershell
    cd C:\Users\devan\.gemini\antigravity\scratch\collaborative-study-room\backend
    ```
2.  Build the project and download Maven dependencies:
    ```powershell
    mvn clean package
    ```
3.  Run the Spring Boot application:
    ```powershell
    mvn spring-boot:run
    ```
4.  Verify startup: Look for the log line:
    `[INFO] Started StudyApplication in X.XXX seconds (process running on port 8080)`

---

### Step 3: Run & Launch the Frontend Client
Because the frontend utilizes modern ES6 JavaScript Modules, it must be run through a local web server (to satisfy browser CORS security policies on local imports).

1.  Open a second terminal window and navigate to the frontend directory:
    ```powershell
    cd C:\Users\devan\.gemini\antigravity\scratch\collaborative-study-room\frontend
    ```
2.  Start a local server:
    *   **If you have Node.js / NPM installed:**
        ```bash
        npx http-server -p 3000
        ```
    *   **If you have Python installed:**
        ```bash
        python -m http.server 3000
        ```
3.  Launch the app: Open **`http://localhost:3000`** in your web browser.

---

### Step 4: Validate Login & Signup flows

#### Option A: Running with Spring Boot Backend Integration
1.  On the frontend lobby screen, toggle the switch labeled **"⚡ Spring Boot Backend Connection (Port 8080)"** to **ON**. The status indicator will turn green (`Spring Boot Connected`).
2.  Click **"Sign Up"**, fill in username, email, password, select a focus avatar, and click **"Create Account"**. 
    *   *Backend Event:* The client sends a `POST` request to `/api/auth/signup`. Spring Boot encrypts the password with BCrypt and saves the user in the database.
3.  Click **"Log In"**, enter your email and password, and click **"Enter Workspace"**.
    *   *Backend Event:* The client sends a `POST` request to `/api/auth/login`. Spring Boot verifies the credentials, signs a stateless JWT token, and returns it. The client saves this token in `localStorage` and appends it as `Authorization: Bearer <JWT_TOKEN>` to all subsequent request headers.
4.  **Google Sign-In/Signup**: Click **"Sign in with Google"** (or **"Sign up with Google"**).
    *   If using your own Google Client ID: Completing the Google popup retrieves your name and email, registers them with the Spring Boot backend using a secure OAuth bypass password, and logs you in.
    *   If using the default placeholder: Click **Cancel** on the setup dialog to run mock Google Authentication. It will immediately authenticate you as **"Dr. Emma Google"** and sync the profile to your Spring Boot database.

#### Option B: Running in Offline LocalStorage Mode
1.  Keep the backend connection toggle switch **OFF** (`LocalStorage Mode (Offline)`).
2.  Register and log in. The application will emulate all Spring Boot controllers using local storage, allowing 100% of the visual features (whiteboard, flashcards, pomodoro timer, chatbot, soundscapes) to run with zero server setup.

---

### Step 5: Access & Query the H2 Database
You can verify that your registered accounts and rooms are saved in the database:

1.  While the Spring Boot application is running, open a browser tab to:
    🔗 **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
2.  Configure the login form with the exact parameters defined in our `application.properties`:
    *   **Saved Settings:** `Generic H2 (Embedded)`
    *   **Driver Class:** `org.h2.Driver`
    *   **JDBC URL:** `jdbc:h2:mem:nexusdb`  *(Ensure this matches exactly)*
    *   **User Name:** `sa`
    *   **Password:** `password`
3.  Click **"Connect"**.
4.  **Explore Data**:
    *   On the left sidebar, click the **`USERS`** table. The query input will prefill with:
        ```sql
        SELECT * FROM USERS;
        ```
    *   Click the green **"Run"** button in the top menu bar.
    *   Verify your registered users, password hashes, and XP levels in the query results!
    *   Run similar queries for `STUDY_ROOM`, `STUDY_TASK`, `CHAT_MESSAGE`, and `NOTE` tables.
