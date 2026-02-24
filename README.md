# spring-boot-order-rest-api

A Spring Boot REST API for managing orders, ready for use by front-end applications.

## Running the Application

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`.

## Interactive API Documentation (Swagger UI)

Once the application is running, visit:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

The Swagger UI lets you browse, try out, and understand every endpoint directly from the browser — no additional tooling required.

---

## API Endpoints

All endpoints are prefixed with `/api/orders`.

### Orders

| Method   | Endpoint                    | Description                              | Request Body        | Query Params              |
|----------|-----------------------------|------------------------------------------|---------------------|---------------------------|
| `GET`    | `/api/orders`               | Get all orders                           | —                   | `status`, `customerEmail` |
| `GET`    | `/api/orders/{id}`          | Get a single order by ID                 | —                   | —                         |
| `POST`   | `/api/orders`               | Create a new order                       | Order JSON (below)  | —                         |
| `PUT`    | `/api/orders/{id}`          | Replace an existing order                | Order JSON (below)  | —                         |
| `PATCH`  | `/api/orders/{id}/status`   | Update only the status of an order       | —                   | `status`                  |
| `DELETE` | `/api/orders/{id}`          | Delete an order                          | —                   | —                         |

### Order Status Values

`PENDING` · `CONFIRMED` · `PROCESSING` · `SHIPPED` · `DELIVERED` · `CANCELLED`

---

## Request / Response Examples

### Create an Order — `POST /api/orders`

**Request body:**
```json
{
  "customerName": "Jane Doe",
  "customerEmail": "jane@example.com",
  "items": [
    {
      "productName": "Widget Pro",
      "quantity": 2,
      "unitPrice": 9.99
    }
  ]
}
```

**Response (`201 Created`):**
```json
{
  "id": 1,
  "customerName": "Jane Doe",
  "customerEmail": "jane@example.com",
  "totalAmount": 19.98,
  "status": "PENDING",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00",
  "items": [
    {
      "id": 1,
      "productName": "Widget Pro",
      "quantity": 2,
      "unitPrice": 9.99
    }
  ]
}
```

### Get All Orders — `GET /api/orders`

Optional query parameters:
- `?status=PENDING` — filter by status
- `?customerEmail=jane@example.com` — filter by customer email

**Response (`200 OK`):** Array of order objects.

### Update Order Status — `PATCH /api/orders/1/status?status=CONFIRMED`

**Response (`200 OK`):** Updated order object with `"status": "CONFIRMED"`.

### Delete an Order — `DELETE /api/orders/1`

**Response (`204 No Content`):** Empty body.

---

## Error Responses

| HTTP Status | When                                          |
|-------------|-----------------------------------------------|
| `400`       | Validation failure or invalid query parameter |
| `404`       | Order not found for the given ID              |

---

## Tech Stack

- Java 17
- Spring Boot 3.2
- Spring Data JPA + H2 (in-memory)
- SpringDoc OpenAPI 2 (Swagger UI)
