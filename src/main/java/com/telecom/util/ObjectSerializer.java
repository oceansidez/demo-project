package com.telecom.util;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.*;

public class ObjectSerializer {

	public static String serialize(Serializable obj) {
		String value = "";
		if (obj == null)
			return value;
		try {
			ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
			ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
			objStream.writeObject(obj);
			objStream.close();
			value = encodeBytes(serialObj.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static Object deserialize(String str) {

		if (str == null || str.length() == 0)
			return null;
		try {
			ByteArrayInputStream serialObj = new ByteArrayInputStream(
					decodeBytes(str));
			ObjectInputStream objStream = new ObjectInputStream(serialObj);
			return objStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodeBytes(byte[] bytes) {
		StringBuffer strBuf = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
			strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
		}

		return strBuf.toString();
	}
	
	public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
	
	public static byte[] decodeBytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length(); i += 2) {
			char c = str.charAt(i);
			bytes[i / 2] = (byte) ((c - 'a') << 4);
			c = str.charAt(i + 1);
			bytes[i / 2] += (c - 'a');
		}
		return bytes;
	}
	
}