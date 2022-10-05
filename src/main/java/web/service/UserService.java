package web.service;

import web.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void add(User user);
    User getOne(Long id);
    void update(User user);
    void deleteById(Long id);
}
