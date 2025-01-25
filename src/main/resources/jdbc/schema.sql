CREATE TABLE User (
    userID int,
    name varchar(255),
    lastName varchar(255),
    registration date,
    admin bit,
    email varchar(255),
    password varchar(255),
    description text(5000),
    avatar blob,
);

CREATE TABLE Test (
    testID int,
    name varchar(255),
    description text(5000),
    previousID int,
);

CREATE TABLE TestQuestion (
    testQuestionID int,
    numberInOrder int,
    testID int,
    question varchar(255),
    showAnswersPerInstance int,
    timeForAnsweringInSec int,
    taken date,
    answered date,
);

CREATE TABLE TestAnswer (
    testAnswer int,
    testID int,
    answer varchar(255),
);

CREATE TABLE TestAnswerReward (
    testAnswerRewardID int,
    testAnswerID int,
    parameterID int,
    value int,
);

CREATE TABLE TestParameter (
    testParameterID int,
    testID int,
    name varchar(255),
    required int,
    previousRequired int,
);

CREATE TABLE TestUser (
    testUserID int,
    userID int,
    testID int,
    answers varchar(255),
    passed bit,
);

CREATE TABLE TestInstanceRedirection (
    testInstanceRedirectionID int,
    userID int,
    testID int,
    testAnswerID int,
    redirectedToNumber int,
);























