CREATE TABLE _User (
    user_id int,
    name varchar(255),
    last_name varchar(255),
    registration date,
    admin bit,
    email varchar(255),
    password varchar(255),
    description text(5000),
    avatar blob
);

INSERT INTO _User(user_id, name, last_name, registration, admin, email, password, description, avatar) VALUES
(0, 'admin', 'primary', null, true, 'yrakurbatov4@gmail.com', '123123', 'Главный архитектор', null);

CREATE TABLE Test (
    test_id int,
    name varchar(255),
    description text(5000),
    previousID int
);

CREATE TABLE TestQuestion (
    test_question_id int,
    number_in_order int,
    test_id int,
    question varchar(255),
    show_answers_per_instance int,
    time_for_answering_in_sec int,
    taken date,
    answered date,
);

CREATE TABLE TestAnswer (
    test_answer_id int,
    test_id int,
    answer varchar(255)
);

CREATE TABLE TestAnswerReward (
    test_answer_reward_id int,
    test_answer_id int,
    parameter_id int,
    reward int
);

CREATE TABLE TestParameter (
    test_parameter_id int,
    test_id int,
    name varchar(255),
    required int,
    previous_required int
);

CREATE TABLE TestUser (
    test_user_id int,
    user_id int,
    test_id int,
    passed bit
);

CREATE TABLE TestResult (
    test_result_id int,
    test_parameter_id int,
    summary int
);

CREATE TABLE TestUserAnswer(
    test_user_answer_id int,
    test_user_answer_id int,
    answer int
);

CREATE TABLE TestInstanceRedirection (
    test_instance_redirection_id int,
    user_id int,
    test_id int,
    test_answer_id int,
    redirected_to_number int
);









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













