# Booking Management API

## Overview

This project is a **Spring Boot REST API** designed to manage **Businesses** and their **Service Offerings**.
It follows **RESTful best practices**, clear separation of concerns, and proper update semantics using **PUT** and **PATCH**.

The API is structured for scalability, correctness, and ease of frontend integration.

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* PostgreSQL (or any JPA-compatible database)
* ModelMapper
* Jakarta Validation
* Lombok

---
## Project Structure

```
config/
controller/
dto/
entity/
exception/
repository/
service/
```

## Core Features Implemented

### 1. Business Management

CRUD operations for businesses with pagination, sorting, and search support.

#### Endpoints

| Method | Endpoint                             | Description                             |
|--------|--------------------------------------|-----------------------------------------|
| POST   | `/api/businesses`                    | Create a new business                   |
| GET    | `/api/businesses`                    | Get all businesses (paginated & sorted) |
| GET    | `/api/businesses/search?search=term` | Search businesses by name               |
| PATCH  | `/api/businesses/{businessId}`       | Partially update a business             |
| DELETE | `/api/businesses/{businessId}`       | Delete a business                       |
| PUT    | `api/businesses/{businessId}`        | Update a business                       |

#### Key Business Rules

* Business names must be **unique**
* Duplicate names are prevented on:

    * Creation
    * Update (excluding the current business)
* PATCH updates **only provided fields**
* Missing fields are **not overwritten**

---

### 2. Service Offering Management

Each business can manage its own set of service offerings.

#### Endpoints

| Method | Endpoint                                            | Description               |
| ------ | --------------------------------------------------- | ------------------------- |
| POST   | `/api/businesses/{businessId}/services`             | Create a service offering |
| PUT    | `/api/businesses/{businessId}/services/{serviceId}` | Full update (replace)     |
| PATCH  | `/api/businesses/{businessId}/services/{serviceId}` | Partial update            |
| DELETE | `/api/businesses/{businessId}/services/{serviceId}` | Delete service offering   |

---

## PUT vs PATCH Design

This project **intentionally separates PUT and PATCH semantics**.

### PUT — Full Replacement

* Requires a complete request body
* All fields are replaced
* Missing fields are treated as intentional
* Uses validated request DTOs

### PATCH — Partial Update

* Updates only provided fields
* Existing values remain unchanged if not supplied
* Safer for frontend incremental updates

This separation prevents accidental data loss and ensures predictable API behavior.

---

## DTO Strategy

### Business

* `BusinessRequest` – Used for creation and full replacement (PUT)
* `BusinessPatchRequest` – Used for partial updates (PATCH)
* `BusinessItemResponse` – Response payload
* `BusinessResponseDto` – Paginated response 

### Service Offering

* `ServiceOfferingRequest` – Create & PUT
* `ServiceOfferingPatchRequest` – PATCH
* `ServiceOfferingResponseItem` – Response payload
* `ServiceOfferingResponse` - Paginated response 

---

## Validation & Error Handling

* Bean Validation (`@NotBlank`, `@NotNull`, `@Positive`)
* Custom exceptions:

    * `ResourceNotFoundException`
    * `APIException`
* Clear error messages for:

    * Duplicate resources
    * Invalid updates
    * Missing entities

---

## Pagination & Sorting

All list endpoints support:

* `pageNumber`
* `pageSize`
* `sortBy`
* `sortOrder` (`asc` / `desc`)

Implemented using `PageRequest` and `Sort`.


### 3. Business Working Hours Management

Each business can define its **weekly working hours**, which are later used for **availability and booking logic**.

Working hours are managed **per business** and **per day of week**.

#### Endpoints

| Method | Endpoint                                                      | Description                          |
| ------ | ------------------------------------------------------------- | ------------------------------------ |
| POST   | `/api/businesses/{businessId}/working-hours`                  | Create working hours for a day       |
| GET    | `/api/businesses/{businessId}/working-hours`                  | Get all working hours for a business |
| PATCH  | `/api/businesses/{businessId}/working-hours/{workingHoursId}` | Partially update working hours       |
| DELETE | `/api/businesses/working-hours/{workingHoursId}`              | Delete working hours                 |

---

#### Key Business Rules

* A business **cannot have duplicate working hours for the same day**

    * Only one entry per `DayOfWeek` is allowed
* `openTime` **must be before** `closeTime`
* Working hours are always associated with a **valid business**
* PATCH updates **only provided fields**
* Existing values remain unchanged if not supplied

---

#### DTO Strategy — Business Working Hours

* `BusinessWorkingHoursRequest` – Create working hours
* `BusinessWorkingHoursPatchRequest` – Partial updates
* `BusinessWorkingHoursResponse` – Response payload

---

#### Validation & Error Handling

* Validation rules enforced:

    * Valid `DayOfWeek`
    * Valid time range (`openTime < closeTime`)
* Errors handled via:

    * `ResourceNotFoundException` – Business or working hours not found
    * `APIException` – Duplicate working hours or invalid time ranges

### Next Possible Enhancements (Updated)

* Booking & availability generation based on working hours
* Time slot generation logic
* Holiday / closed-day overrides
* Authentication & authorization (JWT / OAuth2)
* Role-based access (Business Owner vs User)
* Soft deletes
* Integration tests (Testcontainers)
* OpenAPI / Swagger documentation

## Author

**Nana Owusu Appiah**
Backend Software Engineer (Java / Spring Boot)
