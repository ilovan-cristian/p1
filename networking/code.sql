PRAGMA foreign_keys = ON;

-- Table for the Employee class
CREATE TABLE Employee (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          name TEXT,
                          password TEXT
);

-- Table for the Client class
CREATE TABLE Client (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT
);

-- Table for the Flight class.
CREATE TABLE Flight (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        destination TEXT,
                        departure TEXT,  -- stored as an ISO8601 string
                        seats INTEGER,
                        location TEXT
);

-- Table for the Ticket class.
CREATE TABLE Ticket (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        flight_id INTEGER,
                        client_id INTEGER,
                        FOREIGN KEY (flight_id) REFERENCES Flight(id),
                        FOREIGN KEY (client_id) REFERENCES Client(id)
);