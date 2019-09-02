CREATE TABLE if not exists users (
    id INTEGER not NULL, name VARCHAR(255), PRIMARY KEY (id )
 );
INSERT INTO users (id,name) VALUES ( 1, 'john' );
INSERT INTO users (id,name) VALUES ( 2, 'mike' );