package com.telecom.smsgate.base;

import java.io.IOException;

/**
 * 写入基类
 */
public interface Writer {
    void write(Message message) throws IOException;
}
