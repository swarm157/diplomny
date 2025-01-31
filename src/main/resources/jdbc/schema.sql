CREATE TABLE _User (
    userID int,
    name varchar(255),
    lastName varchar(255),
    registration date,
    admin bit,
    email varchar(255),
    password varchar(255),
    description text(5000),
    avatar blob
);

INSERT INTO _User(userID, name, lastName, registration, admin, email, password, description, avatar) VALUES
(0, 'admin', 'primary', null, true, 'yrakurbatov4@gmail.com', '123123', 'Главный архитектор', null);

CREATE TABLE Test (
    testID int,
    name varchar(255),
    description text(5000),
    previousID int
);

CREATE TABLE TestQuestion (
    testQuestionID int,
    numberInOrder int,
    testID int,
    question varchar(255),
    showAnswersPerInstance int,
    timeForAnsweringInSec int,
    taken date,
    answered date
);

CREATE TABLE TestAnswer (
    testAnswer int,
    testID int,
    answer varchar(255)
);

CREATE TABLE TestAnswerReward (
    testAnswerRewardID int,
    testAnswerID int,
    parameterID int,
    reward int
);

CREATE TABLE TestParameter (
    testParameterID int,
    testID int,
    name varchar(255),
    required int,
    previousRequired int
);

CREATE TABLE TestUser (
    testUserID int,
    userID int,
    testID int,
    answers varchar(255),
    passed bit
);

CREATE TABLE TestInstanceRedirection (
    testInstanceRedirectionID int,
    userID int,
    testID int,
    testAnswerID int,
    redirectedToNumber int
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













