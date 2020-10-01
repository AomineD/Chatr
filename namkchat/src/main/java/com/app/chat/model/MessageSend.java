package com.app.chat.model;

import java.util.Map;

public class MessageSend extends Message {
    public long getHora() {
        return hora;
    }

    public void setHora(long hora) {
        this.hora = hora;
    }

    private long hora;

    public MessageSend(){}


}
