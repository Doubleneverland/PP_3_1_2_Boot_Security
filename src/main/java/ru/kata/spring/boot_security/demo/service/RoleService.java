package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Collection;

public interface RoleService {

    void saveRole(Role role);

    Role getRoleByName(String roleName);

    Collection<Role> getRoleById(long id);

    Collection<Role> getAllRoles();
}
