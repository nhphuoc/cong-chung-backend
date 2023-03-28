package vn.com.panda.learncardriving.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

import vn.com.panda.learncardriving.controller.exception.InvalidDataException;

public class Md5Helper {
    private Md5Helper() {}
    public static String hash(String str) {
        if(StringUtils.isEmpty(str)) {
            throw new InvalidDataException("Could not encrypt empty password");
        }
        String result = StringUtils.EMPTY;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest.digest());
            result = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
