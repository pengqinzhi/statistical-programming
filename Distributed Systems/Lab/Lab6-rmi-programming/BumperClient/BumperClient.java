import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.StringTokenizer;
import java.math.BigInteger;

public class BumperClient {
    public static void main(String args[]) throws Exception {
        // connect to the rmiregistry and get a remote reference to the Bumper object.
        Bumper b  = (Bumper) Naming.lookup("//localhost/bumper");
        System.out.println("Found bumper.");

        BigInteger ctr = new BigInteger("0");
        BigInteger n = new BigInteger("10000");

        long start = System.currentTimeMillis();
        while(! ctr.equals(n)) {
            try {
                ctr = ctr.add(new BigInteger("1"));
                b.bump();
            } catch(RemoteException e) {
                System.out.println("allComments: " + e.getMessage());
            }
        }
        long stop = System.currentTimeMillis();

        System.out.println("value of the BigInteger: " + b.get().toString());
        System.out.println("number of seconds: " + (stop-start)/1000F);
    }
}