package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.TestUser;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.TestUserRepository;

import java.util.Collections;

@Service
public class TestUserService {
    @Autowired
    private TestUserRepository testUserRepository;
    // Create
    public TestUser createTestUser(String name, int testId, int userId) {
        TestUser testUser = new TestUser();
        testUser.setPassed(false);
        testUser.setTestID(testId);
        testUser.setUserID(userId);
        return testUserRepository.save(testUser);
    }

    // Read
    public Iterable<TestUser> getAllTestUsers(int id) {
        return testUserRepository.findAllByTestUserID(id);
    }

    public TestUser getTestUser(int id) {
        return testUserRepository.findById(id).orElseThrow();
    }
    public TestUser getTestUserByUserAndTest(int userId, int testId) {
        return testUserRepository.findByUserIDAndTestID(userId, testId);
    }

    // Update
    public TestUser updateTestUser(int id, boolean passed) {
        TestUser testUser = testUserRepository.findById(id).orElseThrow();
        testUser.setPassed(passed);
        return testUserRepository.save(testUser);
    }

    // Delete
    public void deleteTestUser(int id) {
        testUserRepository.deleteById(id);
    }
}
