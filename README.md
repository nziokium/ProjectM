# ProjectM – Onboarding Module

**ProjectM** is a modern Android application built using Jetpack Compose and Firebase. This module focuses on onboarding users via email/password authentication and collecting essential profile details using Firebase Authentication and Firestore.

---

## ✨ Features

- ✅ Sign Up with Email & Password (Firebase Authentication)
- 🔐 Secure storage of user details (Firestore)
- 🧑‍💼 Collect Name, Phone Number, and National ID
- 📦 Clean Architecture (Data, Domain, and Presentation layers)
- ⚙️ Hilt for Dependency Injection
- 📲 Jetpack Compose for UI
- 🚀 Coroutine-based async operations with proper loading state management

---

## 🧱 Tech Stack

| Layer         | Technology                                       |
|---------------|--------------------------------------------------|
| UI            | Jetpack Compose, ViewModel, StateFlow            |
| Logic         | Kotlin Coroutines, Clean Architecture (UseCases) |
| Backend       | Firebase Auth, Firebase Firestore                |
| DI            | Hilt                                             |
| Build System  | Gradle (KTS)                                     |

---

## 📁 Project Structure

features/
└── onboarding/
├── data/
│ └── repository/
│ └── OnboardingRepositoryImpl.kt
├── domain/
│ ├── model/
│ │ └── SignUpData.kt
│ ├── repository/
│ │ └── OnboardingRepository.kt
│ └── use_case/
│ ├── SignUpWithEmailAndPassword.kt
│ ├── UpdateUserDetails.kt
└── presentation/
└── sign_up/
├── SignUpScreen.kt
└── SignUpViewModel.kt

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Kotlin 1.9+
- Firebase Project (Auth + Firestore enabled)
- `google-services.json` file in your `/app` directory

### Installation Steps

1. **Clone the repository:**

2. **Open in Android Studio**

3. **Add your google-services.json from Firebase Console to:**
  app/google-services.json

4. **Sync Gradle and run the app on an emulator or device.**
