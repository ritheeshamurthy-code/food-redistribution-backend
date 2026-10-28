package com.foodredistribution.service;

import com.foodredistribution.model.User;
import com.foodredistribution.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User updated) {
        User existing = getById(id);
        existing.setEmail(updated.getEmail());
        existing.setPasswordHash(updated.getPasswordHash());
        existing.setRole(updated.getRole());
        existing.setActive(updated.isActive());
        return userRepository.save(existing);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
