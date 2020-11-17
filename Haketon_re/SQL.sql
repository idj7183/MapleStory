USE mysql;

CREATE user IF NOT EXISTS loading;
CREATE user IF NOT EXISTS search;
CREATE user IF NOT EXISTS sign_in;
CREATE user IF NOT EXISTS store;
CREATE DATABASE IF NOT EXISTS maplestory;

USE maplestory;
CREATE TABLE 'user'(
	'no' INT AUTO_INCREMENT PRIMARY KEY,
	'id' VARCHAR(20) UNIQUE KEY,
	'password' VARCHAR(20) NOT NULL
);
CREATE TABLE 'status'(
	'no' INT NOT NULL PRIMARY KEY,
	'level' INT DEFAULT 1, 
	'hp' INT,
	'ex' INT,
	'x' INT,
	'job' VARCHAR(20) NOT NULL,
	'bx' INT DEFAULT 0,
	'weapon_level' INT,
	FOREIGN KEY ('no') REFERENCES 'user' ('no')
	);

USE mysql;
GRANT SELECT ON maplestory.'status' TO loading@localhost IDENTIFIED BY loading;
GRANT SELECT ON maplestory.'user' TO search@localhost IDENTIFIED BY search;
GRANT INSERT ON maplestory.'user' TO sign_in@localhost IDENTIFIED BY sign_in;
GRANT SELECT,INSERT,UPDATE ON maplestory.'status' TO store@localhost IDENTIFIED BY store;

