# Todo App with Spring Boot

Welcome to the Todo App repository developed by me as part of the Spring Boot course, showcasing the power of Spring Boot along with an H2 in-memory database. This project serves as a hands-on implementation, emphasizing efficient task management, showcasing the fundamentals of building a robust web application using the Spring Boot framework together with a persistent H2 database.

## Features:

- **Task Management:** Efficiently manage your tasks with basic CRUD (Create, Read, Update, Delete) operations.
- **RESTful API:** Utilize a clean and scalable RESTful API architecture for seamless integration with various platforms.
- **H2 Database Integration:** Leverage the power of Spring Data JPA and H2 in-memory database for lightweight, embedded storage during development.
- **Thymeleaf Templates:** Employ Thymeleaf templates for server-side rendering, ensuring a dynamic and interactive user interface.

## Getting Started:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/jakubmuczyn/Todo-App.git
   ```

2. **Navigate to the Project Directory:**
   ```bash
   cd Todo-App
   ```

3. **Build and Run the Application:**

   Unix:
   ```bash
   ./mvnw spring-boot:run
   ```
   or Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```
   
4. **Access the Application:**

   Open your web browser and visit http://localhost:8080 to use the Todo App.

## Prerequisites:
- Java Development Kit (JDK)
- Maven
- [Your favorite IDE with Spring Boot support (e.g., IntelliJ, Eclipse)]
  
## Database Configuration:
- The application is configured to use the H2 in-memory database by default.
- Access the H2 Console at http://localhost:8080/h2-console for database management.

## Contributing:
We welcome contributions! If you have ideas for improvements, bug fixes, or new features, feel free to open an issue or submit a pull request.

## License:
This Todo App is open-source and available under the MIT License.
