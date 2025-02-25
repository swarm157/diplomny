package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.UserState;
import ru.nightmare.diplomny.repository.UserStateRepository;

@Service
public class UserStateService {
    @Autowired
    UserStateRepository userStateRepository;

    public UserState createUserState(String name) {
        UserState ut = new UserState();
        ut.setState(name);
        return userStateRepository.save(ut);
    }

    // Read
    public Iterable<UserState> getUserStates() {
        return userStateRepository.findAll();
    }

    public UserState getUserState(int id) {
        return userStateRepository.findById(id).orElseThrow();
    }

    // Update
    public UserState updateUserState(int id, String name) {
        UserState ut = userStateRepository.findById(id).orElseThrow();
        ut.setState(name);
        return userStateRepository.save(ut);
    }

    // Delete
    public void deleteUserState(int id) {
        userStateRepository.deleteById(id);
    }
}
