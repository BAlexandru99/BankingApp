# Green Bank Application

Welcome to the Green Bank Application repository.

## Overview

The Green Bank Application is a web-based banking system that allows users to manage their accounts, view transaction history, and perform various banking operations. The application is built using Spring Boot and Thymeleaf, and it includes security features such as user authentication and password encryption.

## Features

- User Authentication
- View Account Balance
- Transaction History
- New Payment
- Contact Support
- Manage Cards and Products

## Technologies Used

- Spring Boot
- Thymeleaf
- Spring Security
- H2 Database
- Lombok
- HTML/CSS


## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/green-bank-app.git
   cd green-bank-app

2. Build the application:
   ```bash
    mvn clean install

3. Run the application:
  ```bash
  mvn spring-boot:run
   ```

### Accessing the Application

Once the application is running, you can access it at:

http://localhost:8080


### H2 Database Console

The H2 database console can be accessed at:

http://localhost:8080/h2

Use the following settings to log in:
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** (leave empty)

### Contributions

We welcome contributions! Please fork the repository and create a pull request with your changes.

### License

This project is licensed under the MIT License.

### Status

This project is still in development. Expect frequent updates and changes.

Feel free to contact us if you have any questions or suggestions. Thank you for using Green Bank Application!
