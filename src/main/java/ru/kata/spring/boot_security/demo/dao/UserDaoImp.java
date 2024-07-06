package ru.kata.spring.boot_security.demo.dao;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public void save(User user) {

        if (user != null) {
            String encoderPass = passwordEncoder().encode(user.getPassword());
            user.setPassword(encoderPass);
            entityManager.persist(user);
        }

    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    public Collection<Role> getAllRoles() {
        TypedQuery<Role> list = (TypedQuery<Role>) entityManager.createQuery("from Role");
        return list.getResultList();
    }

    public User getUserByName(String name) {
        List<User> query =
                entityManager.createQuery("SELECT u FROM User u where u.name = :userName", User.class)
                        .setParameter("userName", name).getResultList();
        if (!query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public Role getRoleByName(String roleName) {
        List<Role> role =
                entityManager.createQuery("SELECT u FROM Role u where u.role = :userRole", Role.class)
                        .setParameter("userRole", roleName).getResultList();
        if (!role.isEmpty()) {
            return role.get(0);
        }
        return null;
    }

    @Override
    public Collection<Role> getRoleById(long id) {
        TypedQuery<Role> role =
                entityManager.createQuery("SELECT r FROM Role r WHERE r.id = :roleId", Role.class)
                        .setParameter("roleId", id);
        return role.getResultList();
    }



    @Override
    public List<User> read() {

        TypedQuery<User> query = (TypedQuery<User>) entityManager.createQuery("from User ");

        return query.getResultList();
    }

    @Override
    public User update(long id, String name, String lastname, String password, Collection<Role> role) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setName(name);
            user.setLastName(lastname);
            String encoderPass = passwordEncoder().encode(password);
            user.setPassword(encoderPass);
            user.setRoles(role);
            entityManager.merge(user);
        }
        return user;
    }

    @Override
    public User upPage(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void delete(long id) {
        entityManager.createQuery("DELETE FROM User u WHERE u.id = :userId")
                .setParameter("userId", id)
                .executeUpdate();
    }

}
