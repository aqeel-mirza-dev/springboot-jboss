package com.norfolk.dbdisconnectivitypoc.service;

import com.norfolk.dbdisconnectivitypoc.model.User;

import java.util.List;

public interface IUserService {

    public User createUser();

    List<User> getUserFromDB();

    public List<User> getUsers();
}
