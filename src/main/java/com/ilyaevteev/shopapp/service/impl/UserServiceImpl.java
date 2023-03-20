package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.auth.Role;
import com.ilyaevteev.shopapp.model.auth.User;
import com.ilyaevteev.shopapp.repository.RoleRepository;
import com.ilyaevteev.shopapp.repository.UserRepository;
import com.ilyaevteev.shopapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registeredUser = userRepository.save(user);

        return registeredUser;
    }

    @Override
    @Transactional
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        return result;
    }

    @Override
    @Transactional
    public User findById(Long id) {
        User result = userRepository.findById(id);

        if (result == null) {
            return null;
        }

        return result;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
