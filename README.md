# **Notification Service with Kafka, Redis, and Spring Boot**

This project implements a notification service as part of a microservices architecture. It leverages **Kafka** for asynchronous messaging, **Redis** for caching templates, and **Spring Boot** for service orchestration. The service ensures efficient delivery of notifications, scalability, and reliability.

---

## **Features**
- **Template Management**: Cache notification templates in Redis to reduce database load.
- **Contact Management**: Consume contact data through Kafka and deliver notifications.
- **Rebalancer Service**: Ensures all contacts are processed, even in case of failures.
- **Asynchronous Messaging**: Utilizes Kafka for at-least-once delivery of messages.
- **Microservices Design**: Decoupled architecture with separate services for templates, contacts, and rebalancing.

---

## **Tech Stack**
- **Java 17**
- **Spring Boot 3.0**
- **Apache Kafka**
- **Redis**
- **PostgreSQL**
- **Docker & Docker Compose**

---

## **Prerequisites**
1. **Java 17+**
2. **Docker** and **Docker Compose**
3. **Kafka CLI Tools** (optional for manual operations)
4. **Postman** or **cURL** (for testing APIs)

---

## **Setup Instructions**

### 1. **Clone the Repository**
```bash
git clone https://github.com/your-repo/notification-service.git
cd notification-service
```

### 2. **Set Up Environment Variables**
Create a `.env` file in the root directory and configure the following:
```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/notificationdb
SPRING_DATASOURCE_USERNAME=your_db_username
SPRING_DATASOURCE_PASSWORD=your_db_password
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
REDIS_HOST=localhost
REDIS_PORT=6379
```

### 3. **Build and Run Services**
Use Docker Compose to start the services, including Kafka, Redis, and PostgreSQL:
```bash
docker-compose up --build
```

### 4. **Create Kafka Topics**
Run the following commands to create required topics:
```bash
docker exec -it kafka /bin/bash

# Inside Kafka container
kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic templates-topic
kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic contacts-topic
kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 --topic notifications-topic
```

### 5. **Start the Application**
Run the application locally (if needed):
```bash
./mvnw spring-boot:run
```

---

## **API Endpoints**

### **Template APIs**
1. **Cache Template**
   - `POST /templates`
   - **Request Body**:
     ```json
     {
       "id": 1,
       "name": "Welcome Template",
       "content": "Hello {{name}}, welcome to our service!"
     }
     ```

2. **Fetch Template by ID**
   - `GET /templates/{id}`

3. **Delete Template**
   - `DELETE /templates/{id}`

### **Contact APIs**
1. **Consume Contacts**
   - Contacts are consumed from the Kafka `contacts-topic`.

2. **Send Notifications**
   - Notifications are processed and delivered based on the template and contact data.

---

## **Rebalancer Service**
The rebalancer ensures reliable processing of contacts by:
- Identifying unprocessed contacts in Kafka or Redis.
- Retrying delivery or marking contacts as failed for manual intervention.

---

## **Project Structure**
```plaintext
src/main/java/com/azimsh3r/notificationservice/
├── config/          # Kafka and Redis configuration files
├── controller/      # REST controllers for APIs
├── dto/             # Data Transfer Objects (DTOs)
├── service/         # Business logic for templates, contacts, and notifications
├── repository/      # Data access layer
└── utils/           # Utility classes for common operations
```

---

## **How It Works**
1. **API Service**:
   - Publishes templates and contacts to Kafka topics (`templates-topic` and `contacts-topic`).
2. **Notification Service**:
   - Consumes messages from Kafka.
   - Caches templates in Redis for fast access.
   - Processes contacts and delivers notifications.
3. **Rebalancer Service**:
   - Verifies all contacts were processed and reattempts delivery when needed.

---

## **Scaling**
- **Kafka**: Add more partitions for topics to scale horizontally.
- **Redis**: Use Redis clusters for distributed caching.
- **Kubernetes**: Deploy services in Kubernetes for auto-scaling and fault tolerance.

---

## **Contributing**
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch (`feature/your-feature`).
3. Commit your changes.
4. Push to your branch and create a pull request.

---

## **License**
This project is licensed under the MIT License. See `LICENSE` for more details.

---

## **Contact**
For queries or issues, reach out to:
- **Author**: azimsh3r
- **Email**: azimjon.works@gmail.com
