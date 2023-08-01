# User mangement API

## Use cases

1. Create new user
2. Update user info
3. Delete user
4. List available users
5. Validate user credentials

## Environment

Java 17

Database: MySQL 8.0

Build: Apache Maven

## Improvements backlog

- Generate UserController Impl through swagger interface
- Generate LoginController Impl through swagger interface
- Reduce technical debt (improve UT)
- Segregate user data and login data
- Use of Enum to store error details with message, response code, cause, description, and severity
- Add support for version handling
- Improve logging
- Add security features
- Add documentation
- Cache user login data, for instance using Redis
