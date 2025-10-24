package com.example.storyspeak;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

public class JasyptEncryptionTest {

    @Test
    void encryptPassword() {
        // Manually create the encryptor, same as in JasyptConfig.java
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        
        String secretKey = "mykey"; // The REAL secret key

        config.setPassword(secretKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String plaintextPassword = "111111"; // The password to encrypt
        String encryptedPassword = encryptor.encrypt(plaintextPassword);

        System.out.println("Secret Key: " + secretKey);
        System.out.println("Plaintext Password: " + plaintextPassword);
        System.out.println("Encrypted Password: " + encryptedPassword);
    }
}
