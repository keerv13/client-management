-- Ensure the 'patient' table exists
CREATE TABLE IF NOT EXISTS player
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(255) UNIQUE NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    registered_date DATE NOT NULL,
    password VARCHAR(255) NOT NULL
    );

-- Insert well-known UUIDs for specific patients
INSERT INTO player (id, name, email, registered_date, password)
SELECT '123e4567-e89b-12d3-a456-426614174000',
       'John Doe',
       'john.doe@example.com',
       '2024-01-10',
       'password123'
    WHERE NOT EXISTS (SELECT 1
                  FROM player
                  WHERE id = '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO player (id, name, email, registered_date, password)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       'Jane Smith',
       'jane.smith@example.com',
       '2023-12-01',
       'securePass456'
    WHERE NOT EXISTS (SELECT 1
                  FROM player
                  WHERE id = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO player (id, name, email, registered_date, password)
SELECT '323e4567-e89b-12d3-a456-426614174002',
       'Charlie Brown',
       'charlie@example.com',
       '2023-03-20',
       'charliePass789'
    WHERE NOT EXISTS (SELECT 1
                  FROM player
                  WHERE id = '323e4567-e89b-12d3-a456-426614174002');