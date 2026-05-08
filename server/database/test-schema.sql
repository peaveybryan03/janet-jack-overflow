DROP database IF EXISTS janet_test;
CREATE database janet_test;
use janet_test;

CREATE TABLE users (
	id int PRIMARY KEY auto_increment, 
	`name` varchar(256) NOT NULL,
	email varchar(256) NOT NULL,
	password varchar(256) NOT NULL,
	CONSTRAINT uq_user_email
		UNIQUE (email),
	constraint uq_user_name
		unique (`name`)
);

CREATE TABLE question (
	id int PRIMARY KEY auto_increment,
	`text` text NOT NULL,
	is_edited bit NOT NULL,
	user_id int NOT NULL,
	CONSTRAINT fk_question_user_id
		FOREIGN KEY (user_id)
		REFERENCES users(id)
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
		REFERENCES users(id)
);

delimiter //
create procedure set_known_good_state()
begin
	delete from answer;
	alter table answer auto_increment = 1;
	delete from question;
	alter table question auto_increment = 1;
	delete from users;
	alter table users auto_increment = 1;
	
	insert into users (`name`, email, password) values
		("bryanpeavey", "peaveybryan03@gmail.com", "780429354"),
		("ryl33jaxun", "rdmeadows@gmail.com", "1363295421");
end //
delimiter ;