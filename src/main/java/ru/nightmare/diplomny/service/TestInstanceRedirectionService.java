package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.TestInstanceRedirection;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.TestInstanceRedirectionRepository;

@Service
public class TestInstanceRedirectionService {
    @Autowired
    private TestInstanceRedirectionRepository testInstanceRedirectionRepository;
    // Create
    public TestInstanceRedirection createTestInstanceRedirection(int testQuestionId, int testAnswerId, int userId, int redirectedTo) {
        TestInstanceRedirection redirection = new TestInstanceRedirection();
        redirection.setTestAnswerID(testAnswerId);
        redirection.setTestUserID(userId);
        redirection.setTestQuestionID(testQuestionId);
        redirection.setRedirectedToNumber(redirectedTo);
        return testInstanceRedirectionRepository.save(redirection);
    }

    // Read
    public Iterable<TestInstanceRedirection> getAllTestInstanceRedirection(int testUserId, int questionId) {
        return testInstanceRedirectionRepository.findAllByTestUserIDAndTestQuestionIDOrderByRedirectedToNumberAsc(testUserId, questionId);
    }



    public TestInstanceRedirection getTestInstanceRedirection(int testAnswerId) {
        return testInstanceRedirectionRepository.findById(testAnswerId).orElseThrow();
    }

    // Update
    public TestInstanceRedirection updateTestInstanceRedirection(int id, int testQuestionId, int testAnswerId, int userId, int redirectedTo) {
        TestInstanceRedirection redirection = testInstanceRedirectionRepository.findById(id).orElseThrow();
        redirection.setTestAnswerID(testAnswerId);
        redirection.setTestUserID(userId);
        redirection.setTestQuestionID(testQuestionId);
        redirection.setRedirectedToNumber(redirectedTo);
        return testInstanceRedirectionRepository.save(redirection);
    }

    // Delete
    public void deleteTestInstanceRedirection(int id) {
        testInstanceRedirectionRepository.deleteById(id);
    }
}
