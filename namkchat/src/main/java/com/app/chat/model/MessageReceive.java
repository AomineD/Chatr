package com.app.chat.model;

import com.app.chat.NamkoFragment;

public class MessageReceive extends Message {
    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }

    private Long hora;

    public MessageReceive(){

    }



}
