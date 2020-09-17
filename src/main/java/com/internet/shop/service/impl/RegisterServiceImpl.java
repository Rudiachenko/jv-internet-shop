package com.internet.shop.service.impl;

import com.internet.shop.exceptions.IncorrectRegistrationDataException;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Override
    public User register(String login, String password, String repeatPassword)
            throws IncorrectRegistrationDataException {
        if (password.length() < 6) {
            throw new IncorrectRegistrationDataException("Your password length should "
                    + "be at least 6 characters.");
        }
        if (!password.equals(repeatPassword)) {
            throw new IncorrectRegistrationDataException("Your password and repeat "
                    + "password aren't the same.");
        }
        return new User(login, password);
    }
}
