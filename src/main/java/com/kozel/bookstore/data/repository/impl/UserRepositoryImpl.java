package com.kozel.bookstore.data.repository.impl;

import com.kozel.bookstore.data.dao.UserDao;
import com.kozel.bookstore.data.dto.UserDto;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final DataMapper dataMapper;

    @Override
    public User findByEmail(String email) {
        return dataMapper.toEntity(userDao.findByEmail(email));
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return userDao.findByLastName(lastName)
                .stream()
                .map(dataMapper::toEntity)
                .toList();
    }

    @Override
    public User findByLogin(String login) {
        return dataMapper.toEntity(userDao.findByLogin(login));
    }

    @Override
    public long countAll() {
        return userDao.countAll();
    }

    @Override
    public long clearDeletedRows() {
        return userDao.clearDeletedRows();
    }

    @Override
    public Long save(User user) {
        return userDao.save(dataMapper.toDto(user));
    }

    @Override
    public User findById(Long id) {
        return dataMapper.toEntity(userDao.findById(id));
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll()
                .stream()
                .map(dataMapper::toEntity)
                .toList();
    }

    @Override
    public User update(User user) {
        UserDto userDto = dataMapper.toDto(user);
        UserDto savedUserDto = userDao.update(userDto);

        return dataMapper.toEntity(savedUserDto);
    }

    @Override
    public void delete(User user) {
        userDao.delete(dataMapper.toDto(user));
    }
}
