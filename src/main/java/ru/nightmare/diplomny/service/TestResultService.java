package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.TestResult;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.TestResultRepository;

@Service
public class TestResultService {
    @Autowired
    private TestResultRepository testResultRepository;
    // Create
    public TestResult createTestResult(String name, int testUserId, int testParameterId) {
        TestResult result = new TestResult();
        result.setSummary(-1);
        result.setTestParameterID(testParameterId);
        result.setTestUserID(testUserId);
        return testResultRepository.save(result);
    }

    // Read
    public Iterable<TestResult> getAllTestResults(int testUserId) {
        return testResultRepository.findAllByTestUserID(testUserId);
    }

    public TestResult getTestResult(int id) {
        return testResultRepository.findById(id).orElseThrow();
    }

    // Update
    public TestResult updateTestResult(int id, int summary) {
        TestResult result = testResultRepository.findById(id).orElseThrow();
        result.setSummary(summary);
        return testResultRepository.save(result);
    }

    // Delete
    public void deleteTestResult(int id) {
        testResultRepository.deleteById(id);
    }
}
