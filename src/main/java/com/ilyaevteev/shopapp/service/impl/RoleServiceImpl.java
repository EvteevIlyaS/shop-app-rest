package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.auth.Role;
import com.ilyaevteev.shopapp.repository.RoleRepository;
import com.ilyaevteev.shopapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
