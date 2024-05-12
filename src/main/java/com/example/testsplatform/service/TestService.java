package com.example.testsplatform.service;

import com.example.testsplatform.entity.Question;
import com.example.testsplatform.entity.StudentTest;
import com.example.testsplatform.entity.Test;
import com.example.testsplatform.entity.User;
import com.example.testsplatform.repository.StudentTestRepository;
import com.example.testsplatform.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private StudentTestRepository studentTestRepository;

    @Autowired
    UserService userService;

    public Test save(Test test) {
        return testRepository.save(test);
    }

    public Test findTestById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    public List<Test> saveAll(List<Test> tests) {
        return testRepository.saveAll(tests);
    }

    public List<Test> findByQuestionInTest(Question question) {
        return testRepository.findByQuestionInTest(question);
    }

    public List<Test> findByPreviousTest(Test previousTest) {
        return testRepository.findByPreviousTest(previousTest);
    }


    public List<Test> findStudentTestsByUser(User user) {
        return studentTestRepository.findStudentTestsByUser(user);
    }

    public List<Test> findTestByDifficulty(Test previousTest) {
        List<Test> listToAdd = findByPreviousTest(previousTest);

        List<Test> mainList = new ArrayList<>(listToAdd);
        listToAdd.forEach(test -> mainList.addAll(findTestByDifficulty(test)));

        return mainList;
    }

    public List<Test> checkAvailableTest() {
        List<Test> tests = findTestByDifficulty(null);
        List<Test> studentTests = findStudentTestsByUser(userService.getUserAuth());
        tests.forEach(test -> {
            if (test.getPreviousTest() == null) test.setAvailable(true);
            if (studentTests.contains(test)) {
                test.setAvailable(true);
            }
        });

        return tests;
    }

    public List<Test> getPopularTests() {
        return getStudentTestByTest()
                .stream()
                .sorted(Comparator.comparing(Test::getPeopleStarted))
                .toList();
    }

    public List<Test> getHardestTest() {
        return getStudentTestByTest()
                .stream()
                .sorted(Comparator.comparing(test -> test.getPeoplePassed() / test.getPeopleStarted()))
                .toList();
    }

    public List<Test> getStudentTestByTest() {
        return studentTestRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(StudentTest::getTest))
                .entrySet()
                .stream()
                .peek((testListEntry -> {
                    Test test = testListEntry.getKey();
                    int peopleStarted = testListEntry.getValue().size();
                    test.setPeopleStarted(peopleStarted);
                    long peoplePassed = testListEntry.getValue()
                            .stream()
                            .filter(StudentTest::isPassed)
                            .count();
                    test.setPeoplePassed(peoplePassed);
                    test.setPassPercentage(peopleStarted != 0 ? Math.round((double) peoplePassed / peopleStarted * 10000.0) / 100.0d : 0d);
                }))
                .map(Map.Entry::getKey)
                .toList();
    }
}

