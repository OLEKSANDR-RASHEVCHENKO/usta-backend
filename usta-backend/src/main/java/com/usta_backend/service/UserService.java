package com.usta_backend.service;

import com.usta_backend.exeption.EmailAlreadyUsedException;
import com.usta_backend.model.Role;
import com.usta_backend.model.User;
import com.usta_backend.repository.UserRepository;
import com.usta_backend.dto.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User createUser(User user) {           // как и было — для админа
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyUsedException(req.getEmail());
        }
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPhone(req.getPhone());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(Role.CLIENT);                   // регистрация создаёт CLIENT
        return userRepository.save(u);
    }
}
