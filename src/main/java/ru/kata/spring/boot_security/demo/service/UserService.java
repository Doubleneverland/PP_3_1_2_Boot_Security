package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    void save(User user);

    List<User> read();

    User getUserByName(String name);

    void delete(long id);

    User update(long id, String name, String lastname, String password, Collection<Role> role);

    User upPage(long id);

    Collection<Role> getAllRoles();

    Collection<Role> getRoleById(long id);
}
