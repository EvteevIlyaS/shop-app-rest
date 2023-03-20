package com.ilyaevteev.shopapp.repository;


import com.ilyaevteev.shopapp.model.auth.User;

import java.util.List;

public interface UserRepository {
    User findByUsername(String name);

    User save(User user);

    List<User> findAll();

    User findById(Long id);

    void deleteById(Long id);
}
