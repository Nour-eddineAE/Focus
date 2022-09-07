package com.webapps.Focus.service;

import com.webapps.Focus.dao.RoleRepository;
import com.webapps.Focus.entities.role.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        super();
        this.roleRepository = roleRepository;
    }


    public void saveRole(Role role) {
        this.roleRepository.save(role);
    }
}
