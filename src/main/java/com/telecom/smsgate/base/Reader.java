package com.telecom.smsgate.base;

import java.io.IOException;

/**
 * 读取基类
 */
public interface Reader {
    Message read() throws IOException;
}
