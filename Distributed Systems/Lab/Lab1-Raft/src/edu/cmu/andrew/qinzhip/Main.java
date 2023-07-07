package edu.cmu.andrew.qinzhip;

import java.security.MessageDigest;

public class Main {

    public static void main(String[] args) throws Exception {

        int flag = 0;
        int nonce = 0;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        do {
            String str = String.valueOf(nonce) + ",4,19,Pink,Orange,002fdb16086d97e03613fa0caa87b280eca956216e61a35400408bdd3a449e45";
            md.update(str.getBytes());
            String newStr = bytesToHex(md.digest());
            if (newStr.substring(0, 2).equals("00")) {
                flag = 1;
                System.out.println(nonce);
                System.out.println(str);
                System.out.println(newStr);
            } else {
                nonce++;
            }
        } while(flag == 0);


    }

    // Code from stack overflow
    // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    // Returns a hex string given an array of bytes
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}