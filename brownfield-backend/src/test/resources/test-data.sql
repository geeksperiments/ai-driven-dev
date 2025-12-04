-- Test data for HSQLDB
-- Disable foreign key checks for HSQLDB
SET DATABASE REFERENTIAL INTEGRITY FALSE;

-- Drop existing tables to ensure clean schema
DROP TABLE IF EXISTS visit;
DROP TABLE IF EXISTS vet;
DROP TABLE IF EXISTS pet;
DROP TABLE IF EXISTS owners;

-- Schema for Owner table
CREATE TABLE owners (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Test data for Owner
INSERT INTO owners (name, address) VALUES ('John Smith', '123 Main St, Springfield');
INSERT INTO owners (name, address) VALUES ('Sarah Johnson', '456 Oak Ave, Riverside');
INSERT INTO owners (name, address) VALUES ('Emily Davis', '789 Pine Rd, Lakewood');
INSERT INTO owners (name, address) VALUES ('Michael Brown', '321 Elm St, Hillside');

-- Schema for Pet table
CREATE TABLE pet (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES owners(id)
);

-- Test data for Pet
INSERT INTO pet (name, owner_id) VALUES ('Max', 1);
INSERT INTO pet (name, owner_id) VALUES ('Bella', 2);
INSERT INTO pet (name, owner_id) VALUES ('Charlie', 1);
INSERT INTO pet (name, owner_id) VALUES ('Luna', 3);

-- Schema for Vet table
CREATE TABLE vet (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(255) NOT NULL
);

-- Test data for Vet
INSERT INTO vet (name, specialty) VALUES ('Dr. Sarah Martinez', 'Surgery');
INSERT INTO vet (name, specialty) VALUES ('Dr. James Chen', 'Dentistry');
INSERT INTO vet (name, specialty) VALUES ('Dr. Emily Rodriguez', 'General Practice');
INSERT INTO vet (name, specialty) VALUES ('Dr. Michael Thompson', 'Cardiology');

-- Schema for Visit table
CREATE TABLE visit (
    id BIGINT IDENTITY PRIMARY KEY,
    date_time TIMESTAMP NOT NULL,
    clinic VARCHAR(255) NOT NULL,
    summary VARCHAR NOT NULL,
    pet_id BIGINT NOT NULL,
    vet_id BIGINT,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (pet_id) REFERENCES pet(id),
    FOREIGN KEY (vet_id) REFERENCES vet(id),
    FOREIGN KEY (owner_id) REFERENCES owners(id)
);

-- Test data for Visit
INSERT INTO visit (date_time, clinic, summary, pet_id, vet_id, owner_id) VALUES ('2025-11-15 09:30:00', 'Downtown Clinic', 'Pre-operative surgical consultation for neutering procedure. Patient examined and deemed healthy for anesthesia. Bloodwork results reviewed and within normal limits. Surgery scheduled for next week. Pre-operative fasting instructions provided to owner.', 1, 1, 1);
INSERT INTO visit (date_time, clinic, summary, pet_id, vet_id, owner_id) VALUES ('2025-11-18 14:00:00', 'North Branch', 'Routine wellness examination. All vital signs normal. Dental cleaning performed. Owner educated about dental care at home. No concerns noted.', 1, 3, 1);
INSERT INTO visit (date_time, clinic, summary, pet_id, vet_id, owner_id) VALUES ('2025-11-10 10:15:00', 'East Side Clinic', 'Dental examination revealed minor tartar buildup. Professional cleaning completed. Two teeth required extraction due to decay. Post-operative care instructions provided. Pain medication prescribed.', 2, 2, 2);
INSERT INTO visit (date_time, clinic, summary, pet_id, vet_id, owner_id) VALUES ('2025-11-20 11:45:00', 'Downtown Clinic', 'Surgical consultation for mass removal. Pre-operative bloodwork ordered. Surgery scheduled for next week. Owner counseled on procedure risks and recovery expectations.', 2, 1, 2);
INSERT INTO visit (date_time, clinic, summary, pet_id, vet_id, owner_id) VALUES ('2025-11-12 16:30:00', 'North Branch', 'Presented with lethargy and reduced appetite. Physical examination unremarkable. Bloodwork shows mild dehydration. Subcutaneous fluids administered. Dietary recommendations provided. Recheck in 3 days if symptoms persist.', 3, 3, 1);
INSERT INTO visit (date_time, clinic, summary, pet_id, vet_id, owner_id) VALUES ('2025-11-22 08:00:00', 'East Side Clinic', 'Routine dental prophylaxis. Moderate periodontal disease noted. Full mouth radiographs taken. One molar extracted. Antibiotic therapy initiated. Home care demonstration completed with owner.', 3, 2, 3);
INSERT INTO visit (date_time, clinic, summary, pet_id, vet_id, owner_id) VALUES ('2025-11-17 13:20:00', 'Downtown Clinic', 'Post-operative follow-up after spay surgery. Incision healing well with no signs of infection. Sutures intact. Activity restriction to continue for one more week. Recheck scheduled for suture removal.', 3, 1, 3);
INSERT INTO visit (date_time, clinic, summary, pet_id, vet_id, owner_id) VALUES ('2025-11-21 15:00:00', 'North Branch', 'Cardiac evaluation for heart murmur detected during routine exam. Echocardiogram performed showing mild mitral valve insufficiency. No clinical signs of heart failure at this time. Monitoring recommended every 6 months. Owner educated on warning signs.', 3, 4, 3);

-- Re-enable foreign key checks
SET DATABASE REFERENTIAL INTEGRITY TRUE;