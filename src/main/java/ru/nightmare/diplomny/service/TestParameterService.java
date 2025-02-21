package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.TestParameter;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.TestParameterRepository;

@Service
public class TestParameterService {
    @Autowired
    private TestParameterRepository testParameterRepository;
    // Create
    public TestParameter createTestParameter(String name, int required, int prevRequired, int testId) {
        TestParameter testParameter = new TestParameter();
        testParameter.setName(name);
        testParameter.setRequired(required);
        testParameter.setPreviousRequired(prevRequired);
        testParameter.setTestID(testId);
        return testParameterRepository.save(testParameter);
    }

    // Read
    public Iterable<TestParameter> getTestParameters(int testId) {
        return testParameterRepository.findAllByTestID(testId);
    }

    public TestParameter getTestParameter(int id) {
        return testParameterRepository.findById(id).orElseThrow();
    }

    // Update
    public TestParameter updateTestParameter(int id, String name, int required, int prevRequired, int testId) {
        TestParameter testParameter = testParameterRepository.findById(id).orElseThrow();
        testParameter.setName(name);
        testParameter.setRequired(required);
        testParameter.setPreviousRequired(prevRequired);
        testParameter.setTestID(testId);
        return testParameterRepository.save(testParameter);
    }

    // Delete
    public void deleteTestParameter(int id) {
        testParameterRepository.deleteById(id);
    }
}
