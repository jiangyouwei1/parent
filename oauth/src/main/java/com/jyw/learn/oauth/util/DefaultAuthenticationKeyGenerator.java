package com.jyw.learn.oauth.util;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DefaultAuthenticationKeyGenerator {

	public String generateKey(JSONObject values) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest(values.toJSONString().getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		} catch (NoSuchAlgorithmException nsae) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", uee);
		}
	}
}
