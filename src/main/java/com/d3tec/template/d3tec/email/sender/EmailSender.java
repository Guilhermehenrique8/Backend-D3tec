package com.d3tec.template.d3tec.email.sender;

import com.d3tec.template.d3tec.email.model.TransactionalEmail;

public interface EmailSender {
    void send(TransactionalEmail email);
}
