package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.TestQuestion;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.TestQuestionRepository;

@Service
public class TestQuestionService {
    @Autowired
    private TestQuestionRepository testQuestionRepository;
    // Create
    public TestQuestion createTestQuestion(String name, int testId, int inOrder, int perInstance) {
        TestQuestion question = new TestQuestion();
        question.setQuestion(name);
        question.setTestID(testId);
        question.setNumberInOrder(inOrder);
        question.setShowAnswersPerInstance(perInstance);
        return testQuestionRepository.save(question);
    }

    // Read
    public Iterable<TestQuestion> getAllTestQuestions(int testId) {
        return testQuestionRepository.findAllByTestIDOrderByNumberInOrderAsc(testId);
    }

    public TestQuestion getTestQuestion(int id) {
        return testQuestionRepository.findById(id).orElseThrow();
    }

    public TestQuestion getNextTestQuestion(int prevId) {
        TestQuestion prev = getTestQuestion(prevId);
        return testQuestionRepository.findByTestIDAndNumberInOrder(prev.getTestID(), prev.getNumberInOrder() + 1);
    }

    // Update
    public TestQuestion updateTestQuestion(int id, String name, int testId, int inOrder, int perInstance) {
        TestQuestion question = testQuestionRepository.findById(id).orElseThrow();
        question.setQuestion(name);
        question.setTestID(testId);
        question.setNumberInOrder(inOrder);
        question.setShowAnswersPerInstance(perInstance);
        return testQuestionRepository.save(question);
    }

    // Delete
    public void deleteTestQuestion(int id) {
        testQuestionRepository.deleteById(id);
    }
}
