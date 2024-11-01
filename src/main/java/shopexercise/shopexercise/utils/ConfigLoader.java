package shopexercise.shopexercise.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

public class ConfigLoader {
    private static final String ALGORITHM="AES";

    public static SecretKeySpec loadKey(){
        SecretKeySpec secretKeySpec=null;
try{
    String keyString=System.getenv("ENCRYPTION_KEY");

    if(keyString==null){
        throw new IllegalStateException("ENCRYPTION_KEY not defined");
    }
    byte[] keyBytes= Base64.getDecoder().decode(keyString);
    secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);

} catch (IllegalStateException e) {
    System.out.println(e.getMessage());
}catch (Exception e){
    System.out.println("Failure with the key " + e.getMessage());
}
return secretKeySpec;
    }

    public static String decrypt(SecretKeySpec key, String properties){
        String decrypted=null;

        try {
            Cipher cipher= Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue=Base64.getDecoder().decode(properties);
            byte[] decryptedBytes= cipher.doFinal(decodedValue);
            decrypted= new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Decryption failed");
        } catch (InvalidKeyException e) {
            System.out.println("Invalid Key");
        }
        return decrypted;
    }

    public static Properties loadConfig(){
        Properties properties=new Properties();
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream==null){
                throw new IllegalArgumentException();
            }
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Failed in config.properties");
        }
        return properties;
    }
}
