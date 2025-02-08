# Wikimedia Project

This project is designed to help you explore the basics of Kafka through a practical example involving a producer and a consumer. The producer fetches real-time events from the Wikimedia stream and sends them to a Kafka topic. The consumer reads these events from the Kafka topic and saves them into a PostgreSQL database.

---

## Technologies Used

- **Spring Boot 3**: Backend framework for building the producer and consumer modules.
- **Kafka (KRaft mode)**: Used for real-time event streaming.
- **PostgreSQL**: Database for storing the consumed events.
- **Docker**: Containerization for easy setup and deployment.

---

## Key Features

- **Producer Module**:
    - Fetches real-time events from the Wikimedia stream: [https://stream.wikimedia.org/v2/stream/recentchange](https://stream.wikimedia.org/v2/stream/recentchange).
    - Sends the events to a Kafka topic (`wikimedia_recentchange`).

- **Consumer Module**:
    - Reads events from the Kafka topic (`wikimedia_recentchange`).
    - Saves the events into a PostgreSQL database.

- **Docker Integration**:
    - Docker Compose is used to orchestrate the entire setup, including Kafka, PostgreSQL, the producer, the consumer, and Kafka-UI.

---

## Project Structure

The project is structured as a multi-module Maven project:

```
wikimedia
├── kafka-producer-wikimedia
└── kafka-consumer-postgres-wikimedia
```

### Modules

1. **kafka-producer-wikimedia**:
    - Fetches events from the Wikimedia stream and sends them to the Kafka topic.

2. **kafka-consumer-postgres-wikimedia**:
    - Reads events from the Kafka topic and saves them into the PostgreSQL database.

---

## Prerequisites

To run this project, you need the following installed on your machine:

- **Docker**: For containerization and orchestration.

---

## Setup and Installation

### Building the Project

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd wikimedia
   ```

2. **Build and Run the Project Using Docker Compose**:
   ```bash
   docker-compose -f wikimedia-compose.yml up --build -d
   ```
   This command will:
    - Start Kafka in KRaft mode.
    - Start PostgreSQL.
    - Build and start the producer and consumer modules.
    - Start Kafka-UI for managing and monitoring Kafka.

---

## Docker Configuration

The project uses Docker Compose to orchestrate the following services:

- **kafka-wikimedia**: Kafka broker running in KRaft mode.
- **postgres-wikimedia**: PostgreSQL database for storing events.
- **producer**: Spring Boot application for producing events.
- **consumer**: Spring Boot application for consuming events.
- **kafka-ui**: Kafka Manager GUI for monitoring Kafka.

---

## Kafka Configuration

- **Topic**: `wikimedia_recentchange`
- **Mode**: Kafka is running in KRaft mode (without Zookeeper).

---

## PostgreSQL Configuration

The PostgreSQL database is configured with the following details:

- **URL**: `jdbc:postgresql://localhost:5432/wikimedia`
- **Username**: `admin`
- **Password**: `admin`

---

## Running the Project

Once the Docker Compose setup is complete, the following services will be running:

- **Kafka**: Accessible at `localhost:9092`.
- **PostgreSQL**: Accessible at `localhost:5432`.
- **Kafka-UI**: Accessible at `http://localhost:5080` for monitoring Kafka.

---

## Verifying the Setup

1. **Monitor Kafka Topic**:
    - Open Kafka-UI at [http://localhost:5080](http://localhost:5080) to monitor the Kafka topic.

2. **Verify PostgreSQL Database**:
    - Check the PostgreSQL database to verify that events are being saved.

---

## Docker Commands

- **Start the Project**:
  ```bash
  docker compose -f wikimedia-compose.yml up --build -d
  ```

- **Stop the Project**:
  ```bash
  docker compose -f wikimedia-compose.yml down
  ```

---

## Environment Variables

No additional environment variables or configuration files are required. All configurations are handled within the Docker Compose file and application properties.

---

## Initial Database Setup

No initial scripts or migrations are required. The database schema is managed by the consumer application.

---

## Contributing

If you'd like to contribute to this project, feel free to open an issue or submit a pull request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Acknowledgments

- Wikimedia for providing the real-time event stream.
- Apache Kafka for the event streaming platform.
- Spring Boot for the backend framework.
- Docker for containerization and orchestration.

---

## Enjoy!

Enjoy exploring Kafka with this project! If you have any questions or feedback, feel free to reach out.