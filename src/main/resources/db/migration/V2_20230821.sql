CREATE TABLE post (
                      id SERIAL PRIMARY KEY,
                      category VARCHAR(128),
                      title VARCHAR(128),
                      text VARCHAR(2048),
                      author VARCHAR(128) NOT NULL,
                      createdAt TIMESTAMP,
                      updatedAt TIMESTAMP
);