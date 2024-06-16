package dao;

import Exceptions.DealRepositoryException;
import models.User;

import java.util.HashMap;

public class UserRepository {

    private HashMap<Integer, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    public void addUser(User user) throws Exception {
        if (users.containsKey(user.getId())) {
            throw new Exception("user already exists");
        }

        users.put(user.getId(), user);
    }
}
