package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.TestUserAnswer;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.TestUserAnswerRepository;

import java.sql.Date;

@Service
public class TestUserAnswerService {
    @Autowired
    private TestUserAnswerRepository testUserAnswerRepository;
    // Create
    public TestUserAnswer createTestUserAnswer(int testUserId) {
        TestUserAnswer answer = new TestUserAnswer();
        answer.setAnswer(-1);
        answer.setTestUserID(testUserId);
        return testUserAnswerRepository.save(answer);
    }
    // Read
    public Iterable<TestUserAnswer> getAllTestUserAnswers(int testUser) {
        return testUserAnswerRepository.findByTestUserID(testUser);
    }

    public TestUserAnswer getLastTestUserAnswer(int testUser) {
        return testUserAnswerRepository.findByTestUserIDOrderByAnsweredDesc(testUser);
    }
    public TestUserAnswer getTestUserAnswer(int id) {
        return testUserAnswerRepository.findById(id).orElseThrow();
    }

    public TestUserAnswer getTestUserAnswerByQuestionID(int tu_id, int id) {
        return testUserAnswerRepository.findByTestQuestionIDAndTestUserID(id, tu_id);
    }

    // Update
    public TestUserAnswer updateTestUserAnswer(int id, int value, Date date) {
        TestUserAnswer answer = testUserAnswerRepository.findById(id).orElseThrow();
        answer.setAnswer(value);
        answer.setAnswered(date);
        return testUserAnswerRepository.save(answer);
    }
    // Update
    public TestUserAnswer updateTestUserAnswer(int id, Date date) {
        TestUserAnswer answer = testUserAnswerRepository.findById(id).orElseThrow();
        answer.setTaken(date);
        return testUserAnswerRepository.save(answer);
    }

    // Delete
    public void deleteTestUserAnswer(int id) {
        testUserAnswerRepository.deleteById(id);
    }
}
