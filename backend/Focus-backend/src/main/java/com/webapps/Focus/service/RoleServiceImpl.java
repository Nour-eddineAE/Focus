package com.webapps.Focus.service;

import com.webapps.Focus.dao.RoleRepository;
import com.webapps.Focus.entities.role.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class RoleServiceImpl implements IRoleService{

    private RoleRepository roleRepository;
    @Override
    public void saveRole(Role role) {
        this.roleRepository.save(role);
    }
}
