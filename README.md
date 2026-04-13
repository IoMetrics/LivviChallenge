# Livvi Mobile Challenge

**Candidate:** Cristiano Barbosa  
**Seniority:** Senior  

<img width="920" height="338" alt="image" src="https://github.com/user-attachments/assets/7cfd4978-0f72-41db-8ac3-ee798d70b21a" />

---

## Overview

This project is a mobile application built for the Vingcard / ASSA ABLOY Mobile Developer Challenge.

The goal was to implement the core user flow described in the challenge: authentication, browsing available doors, and inspecting door events.

The implementation focuses on **code quality, architecture, and user experience**, respecting the one-day time constraint defined in the challenge.

---

## Features Implemented

### Mandatory Features

- User Sign Up
- User Sign In
- Bearer token persistence
- Automatic authentication in API requests
- Doors list with pagination
- Search doors by name

### Optional Feature

- Door events list for a selected door

---

## Additional Improvements

- Loading state handling
- Empty state handling
- Error state handling
- Retry actions for key flows
- Session persistence
- Logout support

---

## Features Not Implemented

To keep the scope realistic and aligned with the challenge time constraint, the following features were intentionally not implemented:

- Permissions management (CRUD)
- Raw BLE event parsing
- Encrypted requests (ECDH + AES-GCM)

These features are valuable but would significantly increase the scope and complexity. The focus was to deliver a **robust and polished core experience** instead.

---

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM architecture
- Repository pattern
- Retrofit
- Coroutines + Flow
- DataStore / SharedPreferences (for token persistence)
- JUnit
- MockK (or Mockito)
- Turbine (if applicable)

---

## Architecture

The project follows a layered architecture:

### Presentation Layer
- UI (Jetpack Compose)
- ViewModels
- UI State management

### Domain / State Layer
- Business logic coordination
- State transitions (loading, success, error, empty)

### Data Layer
- API services (Retrofit)
- Repositories
- DTO ↔ Domain mapping
- Token persistence

This structure was chosen to improve **testability, maintainability, and separation of concerns**.

---

## Main Flows

### Authentication
- User signs up or signs in
- Token is stored locally
- All authenticated endpoints use the bearer token automatically

### Doors
- Load paginated list of doors
- Search doors by name
- Open a door to view its events

---

## Error Handling

The application handles the following states across the main flows:

- Loading
- Success
- Empty
- Error

API errors are mapped into user-friendly messages when possible.

---

## Tests

This project includes targeted unit tests focusing on high-value areas:

- DTO to domain mapping
- Repository behavior
- Login state handling
- Doors list state handling
- Success / error / empty state transitions

The goal was to prioritize **focused and meaningful tests** rather than broad but shallow coverage.

---

## Trade-offs and Decisions

Given the one-day time constraint defined in the challenge, I focused on delivering a solid and well-structured core experience.

I prioritized:
- completing all mandatory features
- implementing one optional feature with good depth
- ensuring clean architecture and separation of concerns
- handling UI states properly (loading, success, error, empty)
- writing targeted unit tests for critical parts

I intentionally avoided expanding into more advanced features (such as permissions management, raw BLE parsing, and encrypted requests), as they would significantly increase scope and risk for the available time.

This approach allowed me to deliver a more stable, readable, and maintainable solution.

---

## How to Run

### Requirements

- Android Studio
- JDK 17
- Android SDK configured

### Steps

1. Clone this repository  
2. Open the project in Android Studio  
3. Sync Gradle  
4. Run the app on an emulator or physical device  

---

## Running Tests

Run unit tests using:

```bash
./gradlew test
