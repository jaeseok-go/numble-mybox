package jaeseok.numble.mybox.member.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Converter
public class PasswordConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String plain) {
        return encode(plain);
    }

    @Override
    public String convertToEntityAttribute(String encrypt) {
        return encrypt;
    }

    private String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA256");
            md.update(password.getBytes());
            return String.format("%0128x", new BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
