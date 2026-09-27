# LMS API Endpoints Documentation

Complete API documentation with sample JSON data for testing all endpoints.

---

## 1. Tenant APIs

### Base URL: `/tenant`

#### 1.1 Create Tenant
- **Method:** POST
- **Endpoint:** `/tenant/create`
- **Request Body:**
```json
{
  "orgName": "ABC Learning Institute",
  "tenantName": "John Doe",
  "adminEmail": "admin@abcinstitute.com",
  "adminPassword": "Admin@123",
  "address": "123 Main Street, New York, NY 10001",
  "contactEmail": "contact@abcinstitute.com",
  "contactPhone": "+1-234-567-8900"
}
```
- **Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "orgName": "ABC Learning Institute",
  "tenantName": "John Doe",
  "adminEmail": "admin@abcinstitute.com",
  "address": "123 Main Street, New York, NY 10001",
  "contactEmail": "contact@abcinstitute.com",
  "contactPhone": "+1-234-567-8900"
}
```

#### 1.2 Get All Tenants
- **Method:** GET
- **Endpoint:** `/tenant`
- **Response:** Array of tenant objects

#### 1.3 Get Tenant by ID
- **Method:** GET
- **Endpoint:** `/tenant/{id}`
- **Example:** `/tenant/550e8400-e29b-41d4-a716-446655440000`

#### 1.4 Delete Tenant
- **Method:** DELETE
- **Endpoint:** `/tenant?id={tenantId}`
- **Example:** `/tenant?id=550e8400-e29b-41d4-a716-446655440000`

---

## 2. User/Auth APIs

### Base URL: `/auth`

#### 2.1 Login
- **Method:** POST
- **Endpoint:** `/auth/login`
- **Request Body:**
```json
{
  "email": "admin@abcinstitute.com",
  "password": "Admin@123"
}
```
- **Response:** JWT Token (string)

#### 2.2 User Login
- **Method:** POST
- **Endpoint:** `/auth/userLogin`
- **Request Body:**
```json
{
  "email": "teacher@abcinstitute.com",
  "password": "Teacher@123"
}
```
- **Response:** Success message or JWT token

#### 2.3 Create Staff/User
- **Method:** POST
- **Endpoint:** `/auth/createStaff`
- **Request Body:**
```json
{
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "fullName": "Robert Smith",
  "email": "robert.smith@abcinstitute.com",
  "password": "Teacher@123",
  "phone": "+1-234-567-8901",
  "username": "robert.smith",
  "role": "TEACHER"
}
```
- **Response:**
```json
{
  "username": "robert.smith",
  "fullName": "Robert Smith",
  "email": "robert.smith@abcinstitute.com",
  "phone": "+1-234-567-8901",
  "role": "TEACHER",
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "profilePictureUrl": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3LALal1UCNSOdzUpct6GKzQVj87E8M3UXNbWQ7183DwgETpkMzHkbI44&s"
}
```

#### 2.4 Get User by Email
- **Method:** GET
- **Endpoint:** `/auth/getuser`
- **Request Body:**
```json
"teacher@abcinstitute.com"
```

#### 2.5 Get All Users
- **Method:** GET
- **Endpoint:** `/auth/users`

#### 2.6 Get User by ID
- **Method:** GET
- **Endpoint:** `/auth/users/{id}`
- **Example:** `/auth/users/1`

---

## 3. Course APIs

### Base URL: `/course`

#### 3.1 Create Course
- **Method:** POST
- **Endpoint:** `/course/addCourse`
- **Request Body:**
```json
{
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "courseName": "Bachelor of Computer Science",
  "courseCode": "BCS-2024",
  "description": "4-year undergraduate program in Computer Science",
  "durationsMonths": 48,
  "subjects": [
    {
      "subjectName": "Data Structures",
      "teacherId": 1
    },
    {
      "subjectName": "Database Management",
      "teacherId": 1
    },
    {
      "subjectName": "Web Development",
      "teacherId": 1
    }
  ]
}
```

#### 3.2 Get All Courses
- **Method:** GET
- **Endpoint:** `/course`

#### 3.3 Get Course by ID
- **Method:** GET
- **Endpoint:** `/course/{id}`
- **Example:** `/course/1`

---

## 4. Batch APIs

### Base URL: `/batch`

#### 4.1 Create Batch
- **Method:** POST
- **Endpoint:** `/batch/createBatch`
- **Request Body:**
```json
{
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "batchName": "BCS 2024 Morning",
  "batchCode": "BCS-2024-M",
  "courseId": 1,
  "startDate": "2024-01-15",
  "endDate": "2028-05-30",
  "capacity": 60
}
```

#### 4.2 Get All Batches
- **Method:** GET
- **Endpoint:** `/batch/getBatches`

#### 4.3 Get Batch by ID
- **Method:** GET
- **Endpoint:** `/batch/{batchId}`
- **Example:** `/batch/1`

---

## 5. Teacher APIs

### Base URL: `/teacher`

#### 5.1 Create Teacher
- **Method:** POST
- **Endpoint:** `/teacher/createTeacher`
- **Request Body:**
```json
{
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "userId": 2,
  "employeeId": "EMP-2024-001",
  "qualification": "PhD in Computer Science",
  "specialization": "Data Science and Machine Learning",
  "joiningDate": "2024-01-10",
  "department": "Computer Science"
}
```

#### 5.2 Get All Teachers
- **Method:** GET
- **Endpoint:** `/teacher/allTeachers`

#### 5.3 Get Teacher by ID
- **Method:** GET
- **Endpoint:** `/teacher/{id}`
- **Example:** `/teacher/1`

---

## 6. Student APIs

### Base URL: `/student`

#### 6.1 Create Student
- **Method:** POST
- **Endpoint:** `/student/createStudent`
- **Request Body:**
```json
{
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "userId": 3,
  "rollNo": "BCS2024001",
  "dob": "2005-08-15",
  "gender": "MALE",
  "address": "456 Oak Avenue, Brooklyn, NY 11201",
  "guardianName": "Michael Johnson",
  "guardianPhone": "+1-234-567-8902"
}
```

#### 6.2 Get All Students
- **Method:** GET
- **Endpoint:** `/student/getStudents`

#### 6.3 Get Student by ID
- **Method:** GET
- **Endpoint:** `/student/{id}`
- **Example:** `/student/1`

---

## 7. Attendance APIs

### Base URL: `/api/attendance`

#### 7.1 Mark Attendance
- **Method:** POST
- **Endpoint:** `/api/attendance`
- **Access:** ADMIN, TEACHER
- **Request Body:**
```json
{
  "studentId": 1,
  "subjectId": 1,
  "date": "2024-09-27",
  "status": "PRESENT"
}
```
- **Response:**
```json
{
  "id": 1,
  "tenantId": "550e8400-e29b-41d4-a716-446655440000",
  "studentId": 1,
  "studentName": "Sarah Johnson",
  "subjectId": 1,
  "subjectName": "Data Structures",
  "date": "2024-09-27",
  "status": "PRESENT"
}
```

#### 7.2 Update Attendance
- **Method:** PUT
- **Endpoint:** `/api/attendance/{attendanceId}`
- **Access:** ADMIN, TEACHER
- **Example:** `/api/attendance/1`
- **Request Body:**
```json
{
  "studentId": 1,
  "subjectId": 1,
  "date": "2024-09-27",
  "status": "ABSENT"
}
```

#### 7.3 Get Attendance by ID
- **Method:** GET
- **Endpoint:** `/api/attendance/{attendanceId}`
- **Access:** ADMIN, TEACHER, STUDENT
- **Example:** `/api/attendance/1`

#### 7.4 Get All Attendance
- **Method:** GET
- **Endpoint:** `/api/attendance`
- **Access:** ADMIN, TEACHER

#### 7.5 Get Attendance by Student
- **Method:** GET
- **Endpoint:** `/api/attendance/student/{studentId}`
- **Access:** ADMIN, TEACHER, STUDENT
- **Example:** `/api/attendance/student/1`

#### 7.6 Get Attendance by Subject and Date
- **Method:** GET
- **Endpoint:** `/api/attendance/subject/{subjectId}/date/{date}`
- **Access:** ADMIN, TEACHER
- **Example:** `/api/attendance/subject/1/date/2024-09-27`

#### 7.7 Get Attendance by Date
- **Method:** GET
- **Endpoint:** `/api/attendance/date/{date}`
- **Access:** ADMIN, TEACHER
- **Example:** `/api/attendance/date/2024-09-27`

#### 7.8 Get Attendance by Date Range
- **Method:** GET
- **Endpoint:** `/api/attendance/student/{studentId}/range?startDate={start}&endDate={end}`
- **Access:** ADMIN, TEACHER, STUDENT
- **Example:** `/api/attendance/student/1/range?startDate=2024-09-01&endDate=2024-09-30`

#### 7.9 Delete Attendance
- **Method:** DELETE
- **Endpoint:** `/api/attendance/{attendanceId}`
- **Access:** ADMIN only
- **Example:** `/api/attendance/1`

---

## 8. Product APIs (Demo)

### Base URL: `/api/products`

#### 8.1 Create Product
- **Method:** POST
- **Endpoint:** `/api/products`
- **Access:** ADMIN only
- **Request Body:**
```json
{
  "productName": "Learning Management System License",
  "quantity": 100,
  "price": 499.99
}
```
- **Response:**
```json
{
  "id": 1,
  "productName": "Learning Management System License",
  "quantity": 100,
  "price": 499.99
}
```

#### 8.2 Update Product
- **Method:** PUT
- **Endpoint:** `/api/products/{id}`
- **Access:** ADMIN only
- **Example:** `/api/products/1`
- **Request Body:**
```json
{
  "productName": "LMS Pro License",
  "quantity": 150,
  "price": 599.99
}
```

#### 8.3 Get Product by ID
- **Method:** GET
- **Endpoint:** `/api/products/{id}`
- **Access:** ADMIN, TEACHER, STUDENT
- **Example:** `/api/products/1`

#### 8.4 Get All Products
- **Method:** GET
- **Endpoint:** `/api/products`
- **Access:** ADMIN, TEACHER, STUDENT

#### 8.5 Delete Product
- **Method:** DELETE
- **Endpoint:** `/api/products/{id}`
- **Access:** ADMIN only
- **Example:** `/api/products/1`

---

## Enums Reference

### AttendanceStatus
```
PRESENT
ABSENT
LATE
EXCUSED
```

### Gender
```
MALE
FEMALE
OTHER
```

### Role
```
ADMIN
TEACHER
STUDENT
TENANT_ADMIN
```

---

## Testing Workflow

### 1. Setup Tenant
```bash
POST /tenant/create
# Create organization and admin
```

### 2. Login as Tenant Admin
```bash
POST /auth/login
# Use admin credentials
```

### 3. Create Users (Teachers/Students)
```bash
POST /auth/createStaff
# Create teacher accounts
```

### 4. Create Courses
```bash
POST /course/addCourse
# Add courses with subjects
```

### 5. Create Teachers
```bash
POST /teacher/createTeacher
# Map users to teacher profiles
```

### 6. Create Batches
```bash
POST /batch/createBatch
# Create batches for courses
```

### 7. Create Students
```bash
POST /student/createStudent
# Register students
```

### 8. Mark Attendance
```bash
POST /api/attendance
# Record daily attendance
```

---

## Authentication Headers

For protected endpoints, include JWT token:
```
Authorization: Bearer {your-jwt-token}
```

---

## Status Codes

- **200 OK** - Success
- **201 Created** - Resource created
- **204 No Content** - Successful deletion
- **400 Bad Request** - Invalid input
- **401 Unauthorized** - Missing or invalid token
- **403 Forbidden** - Insufficient permissions
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server error

---

## Sample Complete Test Data

### Complete Flow Example:

```json
// 1. Create Tenant
POST /tenant/create
{
  "orgName": "TechEd Academy",
  "tenantName": "Admin User",
  "adminEmail": "admin@teched.com",
  "adminPassword": "Admin@2024",
  "address": "789 Education Blvd, Boston, MA 02101",
  "contactEmail": "info@teched.com",
  "contactPhone": "+1-555-123-4567"
}

// 2. Login
POST /auth/login
{
  "email": "admin@teched.com",
  "password": "Admin@2024"
}

// 3. Create Teacher User
POST /auth/createStaff
{
  "tenantId": "tenant-uuid-from-step-1",
  "fullName": "Dr. Emily Chen",
  "email": "emily.chen@teched.com",
  "password": "Teacher@123",
  "phone": "+1-555-234-5678",
  "username": "emily.chen",
  "role": "TEACHER"
}

// 4. Create Student User
POST /auth/createStaff
{
  "tenantId": "tenant-uuid-from-step-1",
  "fullName": "Alex Martinez",
  "email": "alex.martinez@teched.com",
  "password": "Student@123",
  "phone": "+1-555-345-6789",
  "username": "alex.martinez",
  "role": "STUDENT"
}

// 5. Create Course
POST /course/addCourse
{
  "tenantId": "tenant-uuid-from-step-1",
  "courseName": "Full Stack Web Development",
  "courseCode": "FSWD-2024",
  "description": "Comprehensive full stack development course",
  "durationsMonths": 6,
  "subjects": [
    {
      "subjectName": "React.js",
      "teacherId": 1
    },
    {
      "subjectName": "Node.js",
      "teacherId": 1
    }
  ]
}

// 6. Create Teacher Profile
POST /teacher/createTeacher
{
  "tenantId": "tenant-uuid-from-step-1",
  "userId": 2,
  "employeeId": "TECH-001",
  "qualification": "MS in Computer Science",
  "specialization": "Web Technologies",
  "joiningDate": "2024-01-01",
  "department": "Computer Science"
}

// 7. Create Batch
POST /batch/createBatch
{
  "tenantId": "tenant-uuid-from-step-1",
  "batchName": "FSWD September 2024",
  "batchCode": "FSWD-SEP-2024",
  "courseId": 1,
  "startDate": "2024-09-01",
  "endDate": "2025-02-28",
  "capacity": 30
}

// 8. Create Student Profile
POST /student/createStudent
{
  "tenantId": "tenant-uuid-from-step-1",
  "userId": 3,
  "rollNo": "FSWD2024001",
  "dob": "2000-05-15",
  "gender": "OTHER",
  "address": "123 Student Lane, Boston, MA 02102",
  "guardianName": "Maria Martinez",
  "guardianPhone": "+1-555-456-7890"
}

// 9. Mark Attendance
POST /api/attendance
{
  "studentId": 1,
  "subjectId": 1,
  "date": "2024-09-27",
  "status": "PRESENT"
}
```
