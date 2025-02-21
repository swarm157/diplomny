package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.TestRepository;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    // Create
    public Test createTest(String name, String description, int prevId) {
        Test test = new Test();
        test.setName(name);
        test.setDescription(description);
        test.setPreviousId(prevId);
        return testRepository.save(test);
    }

    // Read
    public Iterable<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test getTest(int testId) {
        return testRepository.findById(testId).orElseThrow();
    }

    // Update
    public Test updateTest(int id, String name, String description, int prevId) {
        Test test = testRepository.findById(id).orElseThrow();
        test.setName(name);
        test.setDescription(description);
        test.setPreviousId(prevId);
        return testRepository.save(test);
    }

    // Delete
    public void deleteTest(int id) {
        testRepository.deleteById(id);
    }
}
