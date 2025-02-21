package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.TestAnswerReward;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.TestAnswerRewardRepository;

@Service
public class TestAnswerRewardService {
    @Autowired
    private TestAnswerRewardRepository testAnswerRewardRepository;
    // Create
    public TestAnswerReward createTestAnswer(int testAnswerId, int reward, int parameterId) {
        TestAnswerReward testAnswerReward = new TestAnswerReward();
        testAnswerReward.setTestAnswerID(testAnswerId);
        testAnswerReward.setParameterID(parameterId);
        testAnswerReward.setValue(reward);
        return testAnswerRewardRepository.save(testAnswerReward);
    }

    // Read
    public Iterable<TestAnswerReward> getAllTestAnswerRewards(int testAnswerId) {
        return testAnswerRewardRepository.findAllByTestAnswerID(testAnswerId);
    }

    public TestAnswerReward getTestAnswerReward(int id) {
        return testAnswerRewardRepository.findById(id).orElseThrow();
    }

    // Update
    public TestAnswerReward updateTestAnswerReward(int id, int testAnswerId, int reward, int parameterId) {
        TestAnswerReward testAnswerReward = testAnswerRewardRepository.findById(id).orElseThrow();
        testAnswerReward.setTestAnswerID(testAnswerId);
        testAnswerReward.setParameterID(parameterId);
        testAnswerReward.setValue(reward);
        return testAnswerRewardRepository.save(testAnswerReward);
    }

    // Delete
    public void deleteTestAnswerReward(int id) {
        testAnswerRewardRepository.deleteById(id);
    }
}
