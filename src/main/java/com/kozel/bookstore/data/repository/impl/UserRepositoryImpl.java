package com.kozel.bookstore.data.repository.impl;

import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private static final String GET_ALL =
            "SELECT u FROM User u";
    private static final String GET_BY_LOGIN =
            "SELECT u FROM User u where u.login = :login";
    private static final String DELETE =
            "DELETE FROM User";
    private static final String COUNT_ALL =
            "SELECT COUNT(u) FROM User u";
    private static final String GET_BY_LAST_NAME =
            "SELECT u FROM User u where u.lastName = :lastName";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        Optional<User> user = Optional.ofNullable(
                session.find(User.class, email));

        disableDeletedFilter(session);
        return user;
    }

    @Override
    public List<User> findByLastName(String lastName) {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        List<User> users = session.createQuery(GET_BY_LAST_NAME, User.class)
                .setParameter("lastName", lastName)
                .getResultList();

        disableDeletedFilter(session);
        return users;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        List<User> users = session.createQuery(GET_BY_LOGIN, User.class)
                .setParameter("login", login)
                .setMaxResults(1)
                .getResultList();

        disableDeletedFilter(session);
        return users.stream().findFirst();
    }

    @Override
    public long countAll() {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        long result = session.createQuery(COUNT_ALL, Long.class).getSingleResult();

        disableDeletedFilter(session);
        return result;
    }

    @Override
    public void clearDeletedRows() {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, true);

        session.createQuery(DELETE, User.class).executeUpdate();

        disableDeletedFilter(session);
    }

    @Override
    public User save(User user) {
        if (user.getId() != null){
            manager.merge(user);
        }
        else {
            manager.persist(user);
        }
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        Optional<User> user = Optional.ofNullable(
                session.find(User.class, id));

        disableDeletedFilter(session);
        return user;
    }

    @Override
    public List<User> findAll() {
        Session session = manager.unwrap(Session.class);
        activateDeletedFilter(session, false);

        List<User> users = session.createQuery(GET_ALL, User.class).getResultList();

        disableDeletedFilter(session);

        return users;
    }


    @Override
    public void delete(User user) {
        user.setDeleted(true);
        manager.merge(user);
    }
}
