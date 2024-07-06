package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {


    private final UserDao userDao;
    public UserServiceImp(UserDao userDao) {this.userDao = userDao;

    }

    @PostConstruct
    public void firstUser() {

        Set<Role> roles = new HashSet<>();
        Role adminRole = new Role("ROLE_ADMIN");
        roles.add(adminRole);
        Role userRole = new Role("ROLE_USER");
        roles.add(userRole);
        userDao.saveRole(adminRole);
        userDao.saveRole(userRole);


        User admin = new User("admin", "admin", "admin", new HashSet<>());
        admin.getRoles().add(userDao.getRoleByName("ROLE_ADMIN"));
        userDao.save(admin);

        User user = new User("user", "user", "user", new HashSet<>());
        user.getRoles().add(userDao.getRoleByName("ROLE_USER"));
        userDao.save(user);

        User user1 = new User("user1", "user1", "user1", roles);
        userDao.save(user1);

    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public List<User> read() {
        return userDao.read();
    }

    @Override
    @Transactional
    public User getUserByName(String name) { return userDao.getUserByName(name);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public User update(long id, String name, String lastname, String password, Collection<Role> role) {
        return userDao.update(id, name, lastname, password, role);
    }

    @Override
    public User upPage(long id) {
        return userDao.upPage(id);
    }

    @Override
    @Transactional
    public Collection<Role> getAllRoles() {
        return userDao.getAllRoles();
    }

    @Override
    @Transactional
    public Collection<Role> getRoleById(long id) {return userDao.getRoleById(id);}

}
