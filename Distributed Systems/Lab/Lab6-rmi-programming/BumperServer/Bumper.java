import java.rmi.*;
import java.math.BigInteger;

public interface Bumper extends Remote {
    boolean bump() throws RemoteException;
    BigInteger get() throws RemoteException;
}
