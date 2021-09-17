package com.csmates.wp.email;

public interface EmailSender {
    void send(String to, String text);
}
