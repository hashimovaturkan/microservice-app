CREATE KEYSPACE springcloud
    WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

use springcloud;

CREATE TABLE IF NOT EXISTS accounts (
        id TEXT PRIMARY KEY,
        uname TEXT,
        name TEXT,
        surname TEXT,
        email TEXT,
        birth_date TIMESTAMP,
        pwd TEXT,
        created_at TIMESTAMP,
        is_active BOOLEAN
);
