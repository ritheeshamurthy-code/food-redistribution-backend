# Smart Food Redistribution Platform — Spring Boot Backend

Full backend for the 10-table schema: Entities (JPA) + Repositories + Services + REST Controllers.

## Stack
- Java 17, Spring Boot 3.2.5
- Spring Data JPA + MySQL
- Lombok (getters/setters/constructors)

## Project structure
```
src/main/java/com/foodredistribution/
  model/        -> 10 entities + 8 enums (mirrors the SQL schema exactly)
  repository/   -> JpaRepository interfaces with custom finder methods
  service/      -> business logic + CRUD
  controller/   -> REST endpoints (/api/...)
  exception/    -> global error handling (404 / 400 / 500 as clean JSON)
```

## Setup
1. Install MySQL and create nothing manually — `spring.jpa.hibernate.ddl-auto=update`
   in `application.properties` will auto-create all 10 tables from the entities
   on first run (same structure as the SQL schema I gave you earlier).
2. Edit `src/main/resources/application.properties`:
   - `spring.datasource.username`
   - `spring.datasource.password`
3. Run:
   ```
   mvn spring-boot:run
   ```
   Server starts on `http://localhost:8080`.

## API endpoints (all follow the same CRUD pattern)

| Resource | Base path |
|---|---|
| Users | `/api/users` |
| Donors | `/api/donors` |
| NGOs | `/api/ngos` (supports `?status=PENDING/VERIFIED/REJECTED`, plus `PATCH /{id}/verify?status=...`) |
| Admins | `/api/admins` |
| Food Donations | `/api/donations` (supports `?donorId=` , `?status=`, plus `PATCH /{id}/status?status=...&changedByUserId=...`) |
| Eligibility Screening | `/api/screenings` (plus `GET /donation/{donationId}`) |
| Matching | `/api/matches` (supports `?donationId=` for ranked matches, `?ngoId=`) |
| Pickups | `/api/pickups` (supports `?ngoId=`, plus `PATCH /{id}/status?status=...`) |
| Distribution | `/api/distributions` (supports `?ngoId=`) |
| Donation Status Log | `/api/status-logs` (supports `?donationId=` for full history) |

Each resource supports standard `GET` (all), `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}`
unless noted otherwise above.

## Notable design choices
- Every child entity (e.g. `FoodDonation`, `Pickup`) owns its foreign key via `@ManyToOne`/`@OneToOne`
  + `@JoinColumn`. Parent entities don't hold back-reference collections — this avoids Jackson
  infinite-recursion issues in JSON responses and keeps the code simple for a student project.
- `FoodDonationService.changeStatus()` automatically writes a `DonationStatusLog` entry every time
  a donation's status changes — matches the audit-trail purpose of that table.
- `GlobalExceptionHandler` turns a missing record into a clean `404` JSON response instead of a stack trace.

## Not implemented (left as an exercise / future work)
- Authentication & JWT security (all endpoints are currently open)
- The actual matching-score calculation algorithm (the `Matching` table is ready to store scores,
  but you'll need to write the scoring logic — distance + urgency + compatibility — yourself,
  probably as a `@Scheduled` job or triggered on donation creation)
- Input validation error responses beyond the basics (`@Valid` is wired on a couple of fields; extend as needed)
