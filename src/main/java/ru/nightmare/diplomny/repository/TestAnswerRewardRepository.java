package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestAnswerReward;

public interface TestAnswerRewardRepository extends CrudRepository<TestAnswerReward, Integer> {
    Iterable<TestAnswerReward> findAllByTestAnswerID(int testAnswerID);
}
