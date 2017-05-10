package ch.steponline.identity.model;

import org.jasypt.hibernate4.type.AbstractEncryptedAsStringType;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 09:46
 */
public final class EncryptedStringType extends AbstractEncryptedAsStringType {
    public EncryptedStringType() {
    }

    protected Object convertToObject(String stringValue) {
        return stringValue;
    }

    public Class returnedClass() {
        return String.class;
    }
}
