package com.ilyaevteev.shopapp.service;


import com.ilyaevteev.shopapp.model.auth.Role;

public interface RoleService {
    Role findByName(String name);
}
