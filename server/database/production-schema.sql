DROP database IF EXISTS janet;
CREATE database janet;
use janet;

CREATE TABLE user (
	id int PRIMARY KEY auto_increment, 
	`name` varchar(256) NOT NULL,
	email varchar(256) NOT NULL,
	password varchar(256) NOT NULL,
	CONSTRAINT uq_user_email
		UNIQUE (email)
);

CREATE TABLE question (
	id int PRIMARY KEY auto_increment,
	`text` text NOT NULL,
	is_edited bit NOT NULL,
	user_id int NOT NULL,
	CONSTRAINT fk_question_user_id
		FOREIGN KEY (user_id)
		REFERENCES user(id)
);

CREATE TABLE answer (
	id int PRIMARY KEY auto_increment,
	`text` text NOT NULL,
	question_id int NOT NULL,
	user_id int NOT NULL,
	CONSTRAINT fk_answer_question_id
		FOREIGN KEY (question_id)
		REFERENCES question(id),
	CONSTRAINT fk_answer_user_id
		FOREIGN KEY (user_id)
		REFERENCES user(id)
);