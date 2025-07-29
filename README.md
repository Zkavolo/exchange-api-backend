# exchange-api-backend

`exchange-api-backend` is a backend service built with **Java Spring Boot** and **PostgreSQL**, providing exchange rate data and currency conversion using the [ExchangeRate-API](https://www.exchangerate-api.com/).

This project serves as the backend for the larger `exchange-api` project.

---

## Features

- Fetch live exchange rates from [ExchangeRate-API](https://www.exchangerate-api.com/).
- Store and update currency data in a PostgreSQL database.
- Automatic daily synchronization of exchange rates (customizable with cron).
- REST API endpoints to:
    - List all currencies (with pagination).
    - Fetch specific currency details.
    - Convert currency values between base and target currencies.

---

## Tech Stack

- **Java 21** (Spring Boot 3.x)
- **PostgreSQL**
- **Lombok**
- **Spring Data JPA**
- **ExchangeRate-API** (external API provider)

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/exchange-api-backend.git
cd exchange-api-backend
```

### 2. Open Project
You can use:

IntelliJ IDEA (recommended)

Eclipse or VSCode with Spring Boot plugin

### 3. Get an API Key
   Create a free account at [ExchangeRate-API](https://www.exchangerate-api.com/) to get your API key.

### 4. Configure Environment Variables
   Set the following environment variables (via .env, application.properties, or OS environment):

```properties
${PSQL_USER}=your_postgres_user
${PSQL_PASSWORD}=your_postgres_password
${PSQL_DB}=your_database_name
${EXCHANGE_RATE_API_KEY}=your_api_key_here
```

### 5. Run the Application
```bash
./mvnw spring-boot:run
```
The Application will start at:
   http://localhost:8080

---

## 📡 API Endpoints

### Get All Currencies (Paginated)
```http
GET http://localhost:8080/api/v1/currencies?page=0&size=10
```
### Get Specific Currency
```http
GET http://localhost:8080/api/v1/currencies/{currencyCode}
```
### Convert Currency
```http
GET http://localhost:8080/api/v1/currencies/convert?baseCurrency={base}&target={target}&value={amount}
```
#### Example:

```http
GET http://localhost:8080/api/v1/currencies/convert?baseCurrency=USD&target=EUR&value=100
```

---

## Configuration
You can adjust constants inside the project:

- `BASE_CURRENCIES`
Set which base currencies should be used for synchronization.

Example:
```java
 public static final List<String> BASE_CURRENCIES = Arrays.asList(
            "USD", // US Dollar
            "EUR", // Euro
            "GBP", // British Pound Sterling
            "JPY", // Japanese Yen
            "CHF", // Swiss Franc
            "CAD", // Canadian Dollar
            "AUD", // Australian Dollar
            "CNY",  // Chinese Yuan
            "IDR" // Indonesian Rupiah
    );
```

- `app.schedule.cron=${value}`

Define when the database should automatically sync (default: once every 24h).

Example:

```properties
app.schedule.cron=0 0 0 * * *
```
- `spring.jpa.hibernate.ddl-auto=${value}`

create → Drop and create schema at startup.

update → Update schema without dropping data.
Example:

```properties
spring.jpa.hibernate.ddl-auto=update;
```

## Testing
For quick local testing, you can change the scheduler to run every minute instead of once per day:

```properties
app.schedule.cron=0 * * * * *
# every minute
```