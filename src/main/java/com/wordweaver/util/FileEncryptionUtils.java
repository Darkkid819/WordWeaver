package com.wordweaver.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class FileEncryptionUtils {
    // 256-bit key
    private static final byte[] encryptionKey = "iUU68NkHFyxB3VvYSyfQPwBj8SJSPBhg".getBytes();

    // used to encrypt the blacklist file
    public static void encryptFile(Path inputFilePath, Path outputFilePath) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(encryptionKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] fileBytes = Files.readAllBytes(inputFilePath);
        byte[] encryptedBytes = cipher.doFinal(fileBytes);

        Files.write(outputFilePath, encryptedBytes);
    }

    // used to decrypt the blacklist file
    public static void readEncryptedFile(Path inputFilePath, Set<String> set) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKey = new SecretKeySpec(encryptionKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encryptedBytes = Files.readAllBytes(inputFilePath);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        String decryptedData = new String(decryptedBytes);

        String[] words = decryptedData.split("\n");
        for (String word : words) {
            set.add(word.trim().toLowerCase());
        }

        // Encrypt the data and write it back to the file
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(decryptedData.getBytes());
        Files.write(inputFilePath, encryptedData);
    }
}
