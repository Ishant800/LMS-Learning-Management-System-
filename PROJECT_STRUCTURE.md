# LMS Project Structure

## Modular Architecture

```
src/main/java/com/example/product_service/
│
├── ProductServiceApplication.java (Main Application)
│
├── modules/
│   ├── auth/           (Authentication & User Management)
│   │   ├── controller/
│   │   │   └── UserController.java
│   │   ├── service/
│   │   │   └── UserService.java
│   │   ├── repository/
│   │   │   └── UserRepository.java
│   │   ├── entity/
│   │   │   └── User.java
│   │   └── dto/
│   │       ├── UserDto.java
│   │       ├── UserLoginDto.java
│   │       ├── UserResponseDto.java
│   │       ├── UserCreateRequest.java
│   │       └── UserRegisterDto.java
│   │
│   ├── tenant/         (Tenant Management)
│   │   ├── controller/
│   │   │   └── TenantController.java
│   │   ├── service/
│   │   │   └── TenantService.java
│   │   ├── repository/
│   │   │   └── TenantRepository.java
│   │   ├── entity/
│   │   │   └── Tenant.java
│   │   └── dto/
│   │       ├── TenantDto.java
│   │       └── TenantLoginDto.java
│   │
│   ├── course/         (Course & Subject Management)
│   │   ├── controller/
│   │   │   └── CourseController.java
│   │   ├── service/
│   │   │   └── CourseService.java
│   │   ├── repository/
│   │   │   ├── CourseRepo.java
│   │   │   └── SubjectRepo.java
│   │   ├── entity/
│   │   │   ├── Course.java
│   │   │   └── Subject.java
│   │   └── dto/
│   │       ├── CourseDto.java
│   │       └── SubjectRequest.java
│   │
│   ├── student/        (Student & Enrollment Management)
│   │   ├── controller/
│   │   │   └── StudentController.java
│   │   ├── service/
│   │   │   └── StudentService.java
│   │   ├── repository/
│   │   │   └── StudentRepo.java
│   │   ├── entity/
│   │   │   ├── Student.java
│   │   │   └── Enrollment.java
│   │   └── dto/
│   │       ├── StudentDto.java
│   │       └── StudentResponseDto.java
│   │
│   ├── teacher/        (Teacher Management)
│   │   ├── controller/
│   │   │   └── TeacherController.java
│   │   ├── service/
│   │   │   └── TeacherService.java
│   │   ├── repository/
│   │   │   └── TeacherRepository.java
│   │   ├── entity/
│   │   │   └── Teacher.java
│   │   └── dto/
│   │       └── TeacherDto.java
│   │
│   ├── batch/          (Batch Management)
│   │   ├── controller/
│   │   │   └── BatchController.java
│   │   ├── service/
│   │   │   └── BatchService.java
│   │   ├── repository/
│   │   │   └── BatchRepo.java
│   │   ├── entity/
│   │   │   └── Batch.java
│   │   └── dto/
│   │       ├── BatchDto.java
│   │       └── BatchResponse.java
│   │
│   ├── attendance/     (Attendance Tracking)
│   │   ├── entity/
│   │   │   └── Attendance.java
│   │   └── dto/
│   │       └── AttendenceDto.java
│   │
│   └── product/        (Demo/Product Module)
│       ├── controller/
│       │   └── ProductController.java
│       ├── service/
│       │   └── ProductService.java
│       ├── repository/
│       │   └── ProductRepo.java
│       └── entity/
│           └── Product.java
│
└── common/             (Shared Components)
    ├── config/
    │   ├── RedisConfig.java
    │   └── SecurityConfig.java
    ├── messaging/
    │   ├── RabbitMQConfig.java
    │   ├── MessageProducer.java
    │   └── MessageConsumer.java
    ├── email/
    │   └── EmailSender.java
    ├── exception/
    │   ├── GlobalExceptionHandler.java
    │   ├── ErrorResponse.java
    │   ├── ResourceNotFoundException.java
    │   └── UserNotFoundException.java
    ├── utility/
    │   ├── AuthContext.java
    │   └── MapToResponse.java
    └── enums/
        ├── Role.java
        ├── Gender.java
        └── AttendanceStatus.java
```

## Package Structure Convention

Each module follows this structure:
```
module_name/
├── controller/    - REST endpoints
├── service/       - Business logic
├── repository/    - Data access
├── entity/        - JPA entities
└── dto/           - Data Transfer Objects
```

## Import Path Pattern

```java
// Auth Module
import com.example.product_service.modules.auth.controller.*;
import com.example.product_service.modules.auth.service.*;
import com.example.product_service.modules.auth.repository.*;
import com.example.product_service.modules.auth.entity.*;
import com.example.product_service.modules.auth.dto.*;

// Common
import com.example.product_service.common.config.*;
import com.example.product_service.common.exception.*;
import com.example.product_service.common.enums.*;
import com.example.product_service.common.utility.*;
import com.example.product_service.common.messaging.*;
import com.example.product_service.common.email.*;
```

## Module Dependencies

- All modules can depend on `common` package
- Modules should NOT directly depend on other modules
- Cross-module communication should go through services/APIs

## Benefits

✅ **Modular** - Each feature is self-contained
✅ **Scalable** - Easy to add new modules
✅ **Maintainable** - Clear separation of concerns
✅ **Testable** - Modules can be tested independently
✅ **Clean** - No scattered files across project
