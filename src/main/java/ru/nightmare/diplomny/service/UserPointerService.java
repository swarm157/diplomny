package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.UserPointer;
import ru.nightmare.diplomny.repository.UserPointerRepository;

@Service
public class UserPointerService {
    @Autowired
    UserPointerRepository userPointerRepository;

    public UserPointer createUserPointer(int userId, int stateId) {
        UserPointer up = new UserPointer();
        up.setUserID(userId);
        up.setUserStateID(stateId);
        return userPointerRepository.save(up);
    }

    // Read
    public Iterable<UserPointer> getAllUserPointers() {
        return userPointerRepository.findAll();
    }

    public UserPointer getUserPointer(int userId) {
        return userPointerRepository.findByUserID(userId);
    }

    // Update
    public UserPointer updateUserPointer(int id, int userId, int stateId) {
        UserPointer up = userPointerRepository.findById(id).orElseThrow();
        up.setUserID(userId);
        up.setUserStateID(stateId);
        return userPointerRepository.save(up);
    }

    // Delete
    public void deleteUserPointer(int id) {
        userPointerRepository.deleteById(id);
    }
}
