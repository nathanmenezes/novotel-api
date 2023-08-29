package com.meinz.novotelapi.service;


import com.meinz.novotelapi.model.EmailToken;
import com.meinz.novotelapi.model.User;

import java.util.UUID;

public interface EmailService {
    EmailToken findByUserAndUsed(User user);

    EmailToken save(EmailToken emailToken);

    EmailToken findByToken(UUID token);

    void useToken();
}
