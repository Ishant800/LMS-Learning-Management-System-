# LMS API Documentation

Complete API endpoints documentation with request and response details.

---

## Table of Contents
1. [Tenant Management](#tenant-management)
2. [User Management](#user-management)
3. [Course Management](#course-management)
4. [Batch Management](#batch-management)
5. [Student Management](#student-management)
6. [Teacher Management](#teacher-management)
7. [Product Management](#product-management)

---

## Tenant Management

### Create Tenant
**Endpoint:** `POST /tenant/create`

**Request Body:**
```json
{
  "id": "string",
  "tenantName": "string",
  "orgName": "string",
  "adminEmail": "string",
  "adminPassword": "string",
  "address": "string",
  "contactEmail": "string",
  "contactPhone": "string",
  "active": true,
  "subscriptionPlan": "string",
  "subscriptionEndDate": "2024-01-01T00:00:00",
  "createdAt": "2024-01-01T00:00:00",
  "updatedAt": "2024-01-01T00:00:00"
}
```

**Input Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | String | Yes | Unique tenant identifier |
| tenantName | String | Yes | Unique name for the tenant |
| orgName | String | Yes | Organization name |
| adminEmail | String | Yes | Admin email address |
| adminPassword | String | Yes | Admin password |
| address | String | No | Organization address |
| contactEmail | String | No | Contact email |
| contactPhone | String | No | Contact phone number |
| active | Boolean | No | Tenant active status (default: true) |
| subscriptionPlan | String | No | Subscription plan type |
| subscriptionEndDate | LocalDateTime | No | Subscription end date |

**Response:**
Returns the created Tenant entity with all fields.

---

## User Management

### Create Staff/User
**Endpoint:** `POST /user/createStaff`

**Request Body:**
```json
{
  "tenantId": "string",
  "username": "string",
  "password": "string",
  "role": "ADMIN|TEACHER|STUDENT|STAFF",
  "fullName": "string",
  "email": "string",
  "phone": "string",
  "profilePictureUrl": "string"
}
```

**Input Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| tenantId | String | Yes | Tenant identifier |
| username | String | Yes | Unique username |
| password | String | Yes | User password |
| role | Enum | Yes | User role: ADMIN, TEACHER, STUDENT, STAFF |
| fullName | String | Yes | User's full name |
| email | String | Yes | User email address |
| phone | String | No | Phone number |
| profilePictureUrl | String | No | URL to profile picture |

**Response:**
Returns UserResponseDto with user details.

---

### User Login
**Endpoint:** `POST /user/userLogin`

**Request Body:**
```json
{
  "email": "string",
  "password": "string"
}
```

**Input Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| email | String | Yes | User email address |
| password | String | Yes | User password |

**Response:**
Returns authentication token/message as String.

---

## Course Management

### Create Course
**Endpoint:** `POST /course/addCourse`

**Request Body:**
```json
{
  "tenantId": "string",
  "courseCode": "string",
  "courseName": "string",
  "description": "string",
  "durationsMonths": 12,
  "subjects": [
    {
      "tenantId": "string",
      "subjectName": "string",
      "teacherId": 1,
      "subjectCode": "string",
      "attendanceId": "string"
    }
  ]
}
```

**Input Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| tenantId | String | Yes | Tenant identifier |
| courseCode | String | Yes | Unique course code |
| courseName | String | Yes | Course name |
| description | String | No | Course description |
| durationsMonths | Integer | No | Course duration in months |
| subjects | List<SubjectRequest> | No | List of subjects in the course |

**SubjectRequest Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| tenantId | String | Yes | Tenant identifier |
| subjectName | String | Yes | Subject name |
| teacherId | Long | No | Assigned teacher ID |
| subjectCode | String | Yes | Subject code |
| attendanceId | String | No | Attendance tracking ID |

**Response:**
Returns the created Course entity.

---

### Get All Courses
**Endpoint:** `GET /course`

**Request Parameters:** None

**Response:**
Returns a list of all Course entities.

---

## Batch Management

### Create Batch
**Endpoint:** `POST /batch/createBatch`

**Request Body:**
```json
{
  "tenantId": "string",
  "batchName": "string",
  "courseId": 1,
  "totalStudent": 30,
  "year": 2024,
  "section": "A"
}
```

**Input Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| tenantId | String | Yes | Tenant identifier |
| batchName | String | Yes | Batch name |
| courseId | Long | Yes | Associated course ID |
| totalStudent | Integer | No | Total students in batch |
| year | Integer | No | Batch year |
| section | String | No | Section identifier (e.g., A, B, C) |

**Response:**
Returns the created Batch entity.

---

### Get All Batches
**Endpoint:** `GET /batch/getBatches`

**Request Parameters:** None

**Response:**
Returns a list of all Batch entities.

---

### Get Batch by ID
**Endpoint:** `GET /batch/{batchId}`

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| batchId | Long | Yes | Batch identifier |

**Response:**
Returns the Batch entity with the specified ID.

---

## Student Management

### Create Student
**Endpoint:** `POST /student/createStudent`

**Request Body:**
```json
{
  "id": 1,
  "tenantId": "string",
  "userId": 1,
  "rollNo": "string",
  "dob": "2000-01-01",
  "gender": "MALE|FEMALE|OTHERS",
  "address": "string",
  "guardianName": "string",
  "guardianPhone": "string"
}
```

**Input Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | No | Student ID (auto-generated) |
| tenantId | String | Yes | Tenant identifier |
| userId | Long | Yes | Associated user ID |
| rollNo | String | Yes | Student roll number |
| dob | LocalDate | Yes | Date of birth (Format: YYYY-MM-DD) |
| gender | Enum | Yes | Gender: MALE, FEMALE, OTHERS |
| address | String | No | Student address |
| guardianName | String | No | Guardian's name |
| guardianPhone | String | No | Guardian's phone number |

**Response:**
Returns the created Student entity.

---

### Get All Students
**Endpoint:** `GET /student/getStudents`

**Request Parameters:** None

**Response:**
Returns a list of all Student entities.

---

## Teacher Management

### Create Teacher
**Endpoint:** `POST /teacher/createTeacher`

**Request Body:**
```json
{
  "tenantId": "string",
  "userId": 1,
  "subjectId": 1,
  "qualification": "string",
  "experienceYears": 5,
  "specialization": "string"
}
```

**Input Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| tenantId | String | Yes | Tenant identifier |
| userId | Long | Yes | Associated user ID |
| subjectId | Long | No | Primary subject ID |
| qualification | String | No | Teacher's qualification |
| experienceYears | Integer | No | Years of teaching experience |
| specialization | String | No | Area of specialization |

**Response:**
Returns the created Teacher entity.

---

### Get Teacher by ID
**Endpoint:** `GET /teacher/{id}`

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id | Long | Yes | Teacher identifier |

**Response:**
Returns the Teacher entity with the specified ID.

---

### Get All Teachers
**Endpoint:** `GET /teacher/allTeachers`

**Request Parameters:** None

**Response:**
Returns a list of all Teacher entities.

---

## Product Management

### Create Product
**Endpoint:** `POST /product/createProduct`

**Request Body:**
```json
{
  "id": 1,
  "productName": "string",
  "quantity": 100,
  "price": 99.99
}
```

**Input Fields:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | No | Product ID (auto-generated) |
| productName | String | Yes | Product name |
| quantity | int | Yes | Product quantity |
| price | double | Yes | Product price |

**Response:**
Returns the created Product entity.

---

### Get Product by ID
**Endpoint:** `GET /product/{id}`

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id | Long | Yes | Product identifier |

**Response:**
Returns the Product entity with the specified ID.

---

### Get All Products
**Endpoint:** `GET /product/products`

**Request Parameters:** None

**Response:**
Returns a list of all Product entities.

---

## Common Data Types

### Role Enum Values
- `ADMIN` - Administrator role
- `TEACHER` - Teacher role
- `STUDENT` - Student role
- `STAFF` - Staff role

### Gender Enum Values
- `MALE` - Male gender
- `FEMALE` - Female gender
- `OTHERS` - Other gender

---

## Notes

1. **CORS Configuration**: The Tenant endpoint has CORS enabled for `http://localhost:5173`
2. **Date Format**: All LocalDate fields use ISO-8601 format: `YYYY-MM-DD`
3. **DateTime Format**: All LocalDateTime fields use ISO-8601 format: `YYYY-MM-DDTHH:mm:ss`
4. **Authentication**: The `/user/userLogin` endpoint returns an authentication token that should be used for subsequent requests
5. **Tenant Isolation**: All entities are tenant-aware and require a valid `tenantId`
6. **Auto-generated Fields**: 
   - Entity IDs are typically auto-generated when not provided
   - Timestamps (`createdAt`, `updatedAt`) are automatically managed by the system

---

## Error Handling

All endpoints follow standard HTTP status codes:
- `200 OK` - Request successful
- `400 Bad Request` - Invalid input data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

---

**Last Updated:** 2024
**API Version:** 1.0
