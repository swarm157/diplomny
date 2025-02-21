package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.repository.TestAnswerRepository;

import java.util.Optional;

@Service
public class TestAnswerService {
    @Autowired
    private TestAnswerRepository testAnswerRepository;
    // Create
    public TestAnswer createTestAnswer(String name, int testQuestionId) {
        TestAnswer answer = new TestAnswer();
        answer.setAnswer(name);
        answer.setTestQuestionID(testQuestionId);
        return testAnswerRepository.save(answer);
    }

    // Read
    public Iterable<TestAnswer> getAllTestAnswers(int testQuestionId) {
        return testAnswerRepository.findTestAnswerByTestQuestionID(testQuestionId);
    }

    public TestAnswer getTestAnswer(int testAnswerId) {
        return testAnswerRepository.findById(testAnswerId).orElseThrow();
    }

    // Update
    public TestAnswer updateTestAnswer(int id, String name, int testQuestionId) {
        TestAnswer answer = testAnswerRepository.findById(id).orElseThrow();
        answer.setAnswer(name);
        answer.setTestQuestionID(testQuestionId);
        return testAnswerRepository.save(answer);
    }

    // Delete
    public void deleteTestAnswer(int id) {
        testAnswerRepository.deleteById(id);
    }
}
