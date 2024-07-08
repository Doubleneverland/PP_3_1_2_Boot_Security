package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Collection;

@Service
public class RoleServiceImp implements RoleService{

    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Override
    @Transactional
    public Role getRoleByName(String roleName) {
       return roleDao.getRoleByName(roleName);
    }

    @Override
    @Transactional
    public Collection<Role> getRoleById(long id) {
        return roleDao.getRoleById(id);
    }

    @Override
    @Transactional
    public Collection<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }
}
