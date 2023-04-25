package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.config.MessageStrings;
import com.prettyshopbe.prettyshopbe.exceptions.AuthenticationFailException;
import com.prettyshopbe.prettyshopbe.model.AuthenticationToken;
import com.prettyshopbe.prettyshopbe.model.User;
import com.prettyshopbe.prettyshopbe.respository.TokenRepository;
import com.prettyshopbe.prettyshopbe.until.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    TokenRepository repository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    public User getUser(String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_PRESENT);
        }
        if (!Helper.notNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_VALID);
        }
    }
}

