import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.math.BigInteger;

// The servant class extends UnicastRemoteObject and implements Bumper
public class BumperServant extends UnicastRemoteObject implements Bumper {
        private static BigInteger bigInteger;
        public BumperServant() throws RemoteException {
                bigInteger = new BigInteger("0");
        }

        // The remote bump() method behaves as follows:
        // A call on bump() adds 1 to a BigInteger held by the service.
        // It then returns true on completion.
        // The BigInteger is changed by the call on bump(). That is,
        // 1 is added to the BigInteger and that value persists until
        // another call on bump occurs.
        @Override
        public boolean bump() throws RemoteException {
                bigInteger = bigInteger.add(new BigInteger("1"));
                return true;
        }

        // a call on get returns the BigInteger held by the service
        @Override
        public BigInteger get() throws RemoteException {
              return bigInteger;
        }

}