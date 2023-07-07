package ds.project1task1;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ComputeHasesModel {

    public String computeHashesHex(String text, String hashChoice) throws IOException {
        String hash_hex = "";
        try {
            // access MessageDigest class for SHA-265 or MD5
            MessageDigest md = MessageDigest.getInstance(hashChoice);
            // compute the digest and convert to hex
            md.update(text.getBytes("UTF-8"));
            hash_hex = jakarta.xml.bind.DatatypeConverter.printHexBinary(md.digest());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Algorithm available" + e);
        }
        return hash_hex;
    }

    public String computeHashesBase64(String text, String hashChoice) throws IOException {
        String hash_base = "";
        try {
            // access MessageDigest class for SHA-265 or MD5
            MessageDigest md = MessageDigest.getInstance(hashChoice);
            // compute the digest and convert to base64
            md.update(text.getBytes("UTF-8"));
            hash_base = jakarta.xml.bind.DatatypeConverter.printBase64Binary(md.digest());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Algorithm available" + e);
        }
        return hash_base;
    }

}
