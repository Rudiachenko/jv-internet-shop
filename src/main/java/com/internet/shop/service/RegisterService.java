package com.internet.shop.service;

import com.internet.shop.exceptions.IncorrectRegistrationDataException;
import com.internet.shop.model.User;

public interface RegisterService {
    User register(String login, String password, String repeatPassword)
            throws IncorrectRegistrationDataException;
}
