package com.telecom.smsgate.base;

import java.io.IOException;

/**
 * 短信通道会话
 */
public interface Session extends java.io.Closeable {
    String getSessionId();
    boolean isAuthenticated();
    boolean authenticate();
    void heartbeat();
    void submit(String content, String spNumber, String userNumber);
    void submit(String content, String spNumber, String userNumber, Integer sequenceNumber);
    void send(Message message);
    void process(Message message) throws IOException;
}
