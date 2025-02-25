CREATE TABLE _user (
    user_id int not null,
    name varchar(255) not null,
    last_name varchar(255) not null,
    registration date,
    admin bit,
    email varchar(255),
    password varchar(255) not null,
    description text(5000),
    avatar blob,
    CONSTRAINT _user_pk PRIMARY KEY (user_id)
);

INSERT INTO _user(user_id, name, last_name, registration, admin, email, password, description, avatar) VALUES
(0, 'admin', 'primary', null, true, 'yrakurbatov4@gmail.com', '123123', 'Главный архитектор', null);

CREATE TABLE test (
    test_id int not null,
    name varchar(255) NOT NULL,
    description text(5000) NOT NULL,
    previous_id int,
    CONSTRAINT test_pk PRIMARY KEY (test_id),
    CONSTRAINT test_previous_fk FOREIGN KEY (previous_id) REFERENCES test(previous_id)
);

CREATE TABLE test_question (
    test_question_id int not null,
    number_in_order int not null,
    test_id int not null,
    question varchar(255) not null,
    show_answers_per_instance int not null,
    time_for_answering_in_sec int not null,
    CONSTRAINT test_question_pk PRIMARY KEY (test_question_id),
    CONSTRAINT test_question_test_fk FOREIGN KEY (test_id) REFERENCES test(test_id) ON DELETE CASCADE
);

CREATE TABLE test_answer (
    test_answer_id int not null,
    test_question_id int not null,
    answer varchar(255) not null,
    CONSTRAINT test_answer_pk PRIMARY KEY (test_answer_id),
    CONSTRAINT test_answer_test_question_fk FOREIGN KEY (test_question_id) REFERENCES test_question(test_question_id) ON DELETE CASCADE
);

CREATE TABLE test_answer_reward (
    test_answer_reward_id int not null,
    test_answer_id int not null,
    parameter_id int not null,
    reward int not null,
    CONSTRAINT test_answer_reward_pk PRIMARY KEY (test_answer_reward_id),
    CONSTRAINT test_answer_reward_test_answer_fk FOREIGN KEY (test_answer_id) REFERENCES test_answer(test_answer_id) ON DELETE CASCADE,
    CONSTRAINT test_answer_reward_parameter_fk FOREIGN KEY (parameter_id) REFERENCES test_parameter(test_parameter_id) ON DELETE CASCADE
);

CREATE TABLE test_parameter (
    test_parameter_id int not null,
    test_id int not null,
    name varchar(255) not null,
    required int not null,
    previous_required int,
    CONSTRAINT test_parameter_pk PRIMARY KEY (test_parameter_id),
    CONSTRAINT test_parameter_test_fk FOREIGN KEY (test_id) REFERENCES test(test_id) ON DELETE CASCADE
);

CREATE TABLE test_user (
    test_user_id int not null,
    user_id int not null,
    test_id int not null,
    passed bit,
    CONSTRAINT test_user_pk PRIMARY KEY (test_user_id),
    CONSTRAINT test_user_user_fk FOREIGN KEY (user_id) REFERENCES _user(user_id) ON DELETE CASCADE,
    CONSTRAINT test_user_test_fk FOREIGN KEY (test_id) REFERENCES test(test_id) ON DELETE CASCADE
);

CREATE TABLE test_result (
    test_result_id int not null,
    test_parameter_id int not null,
    test_user_id int not null,
    summary int not null,
    CONSTRAINT test_result_pk PRIMARY KEY (test_result_id),
    CONSTRAINT test_result_parameter_fk FOREIGN KEY (test_parameter_id) REFERENCES test_parameter(test_parameter_id) ON DELETE CASCADE,
    CONSTRAINT test_result_test_user_fk FOREIGN KEY (test_user_id) REFERENCES test_user(test_user_id) ON DELETE CASCADE
);

CREATE TABLE test_user_answer(
    test_user_answer_id int not null,
    test_user_id int not null,
    taken date,
    answered date,
    answer int not null,
    CONSTRAINT test_user_answer_pk PRIMARY KEY (test_user_answer_id),
    CONSTRAINT test_user_answer_test_user_fk FOREIGN KEY (test_user_id) REFERENCES test_user(test_user_id) ON DELETE CASCADE
);

CREATE TABLE test_instance_redirection (
    test_instance_redirection_id int NOT NULL,
    test_question_id int NOT NULL,
    test_user_id int NOT NULL,
    test_answer_id int NOT NULL,
    redirected_to_number int NOT NULL,
    CONSTRAINT test_instance_redirection_pk PRIMARY KEY (test_instance_redirection_id),
    CONSTRAINT test_instance_redirection_test_question_fk FOREIGN KEY (test_question_id) REFERENCES test_question(test_question_id) ON DELETE CASCADE,
    CONSTRAINT test_instance_redirection_test_user_fk FOREIGN KEY (test_user_id) REFERENCES test_user(test_user_id) ON DELETE CASCADE,
    CONSTRAINT test_instance_redirection_test_answer_fk FOREIGN KEY (test_answer_id) REFERENCES test_answer(test_answer_id) ON DELETE CASCADE
);

CREATE table user_state (
    user_state_id int NOT NULL,
    state varchar(8) NOT NULL,
    CONSTRAINT user_state_pk PRIMARY KEY (user_state_id)
);

CREATE TABLE user_pointer (
    user_pointer_id int NOT NULL,
    user_state_id int NOT NULL,
    user_id int NOT NULL,
    pointer int NOT NULL,
    CONSTRAINT user_pointer_pk PRIMARY KEY (user_pointer_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES _user(user_id) ON DELETE CASCADE,
    CONSTRAINT user_state_id_fk FOREIGN KEY (user_state_id) REFERENCES user_state(user_state_id) ON DELETE CASCADE
);

INSERT INTO user_state(user_state_id, state) VALUES (1, 'visitor');
INSERT INTO user_state(user_state_id, state) VALUES (2, 'test');
INSERT INTO user_state(user_state_id, state) VALUES (3, 'result');
INSERT INTO user_state(user_state_id, state) VALUES (4, 'guest');
INSERT INTO user_state(user_state_id, state) VALUES (5, 'reading');
INSERT INTO user_state(user_state_id, state) VALUES (6, 'choosing');
INSERT INTO user_state(user_state_id, state) VALUES (7, 'aborting');



CREATE TABLE SPRING_SESSION (
                                PRIMARY_ID CHAR(36) NOT NULL,
                                SESSION_ID CHAR(36) NOT NULL,
                                CREATION_TIME BIGINT NOT NULL,
                                LAST_ACCESS_TIME BIGINT NOT NULL,
                                MAX_INACTIVE_INTERVAL INT NOT NULL,
                                EXPIRY_TIME BIGINT NOT NULL,
                                PRINCIPAL_NAME VARCHAR(100),
                                CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
                                           SESSION_PRIMARY_ID CHAR(36) NOT NULL,
                                           ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
                                           ATTRIBUTE_BYTES BYTEA NOT NULL,
                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);













