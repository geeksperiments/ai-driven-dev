# Backend Refactoring - Owner Entity Extraction

## Database Schema Changes

- Step 1: In brownfield-backend, create a new Entity class named Owner in owner package. It should have 3 attributes (id, name, address). Define its schema in data.sql. Verify by running `mvn compile` ✅

- Step 2: Refactor Pet entity to replace ownerName field with @ManyToOne Owner relationship. Update Pet schema and test data in data.sql to use foreign key references. Verify by running `mvn compile` ✅

- Step 3: Update Visit entity to add @ManyToOne Owner relationship. Modify Visit schema and test data accordingly. Verify by running `mvn compile` ✅

## Repository Layer Updates

- Step 4: Create OwnerRepository interface extending JpaRepository with findByName method. Update PetRepository to add findByOwner method. Verify by running `mvn compile` ✅

- Step 5: Update VisitRepository to add findByOwner method. Verify by running `mvn compile` ✅

# Time to test the database and repository changes

- Step 6: Create OwnerRepositoryTest with JUnit tests for save, findById, findByName operations. Run `mvn test` to ensure all tests pass and database schema works correctly

## Service Layer Refactoring

- Step 7: Refactor PetService to work with Owner entities instead of ownerName strings. Update business rules for pet uniqueness per owner. Verify by running `mvn compile`

- Step 8: Update VisitService to work with Owner entities. Ensure proper relationship handling. Verify by running `mvn compile`

# Time to test service layer changes

- Step 9: Update existing PetServiceTest and VisitServiceTest to work with Owner entities. Run `mvn test` to ensure all service tests pass

## API Layer Updates

- Step 10: Update PetController to include Owner information in Pet API responses. Modify Pet DTOs if needed. Verify by running `mvn compile`

- Step 11: Update VisitController to include Owner information in Visit API responses. Modify Visit DTOs if needed. Verify by running `mvn compile`

# Final Integration Testing

- Step 12: Run `mvn test` to ensure all existing tests pass with new Owner entity relationships

- Step 13: Start application with `mvn spring-boot:run` and test API endpoints manually or with integration tests to verify Owner data is properly exposed through Pet and Visit APIs