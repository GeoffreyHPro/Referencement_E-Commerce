package fr.univ.lille.referencement.configuration;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5 implements Encoder {

    public String encode(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytesOfMessage = message.getBytes(StandardCharsets.UTF_8);
            md.update(bytesOfMessage);
            return Base64.getEncoder().encodeToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate MD5 : " + e.getMessage());
        }
    }
}
