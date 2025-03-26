package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.User;
import ru.nightmare.diplomny.repository.TestAnswerRepository;
import ru.nightmare.diplomny.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    // Create
    public User createUser(String name, String lastName, String email, String description, String password) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDescription(description);
        user.setPassword(password);
        return userRepository.save(user);
    }

    // Read
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    // Update
    public User updateUser(int id, Byte[] avatar) {
        User user = userRepository.findById(id).orElseThrow();
        user.setAvatar(avatar);
        return userRepository.save(user);
    }
    // Update
    public User updateUser(int id, String description) {
        User user = userRepository.findById(id).orElseThrow();
        user.setDescription(description);
        return userRepository.save(user);
    }

    // Delete
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
