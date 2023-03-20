package com.ilyaevteev.shopapp.repository;


import com.ilyaevteev.shopapp.model.auth.Role;

public interface RoleRepository {
    Role findByName(String name);
}
