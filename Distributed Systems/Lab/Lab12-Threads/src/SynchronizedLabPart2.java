// Isolation

// A shared account
class Account {
    private double balance = 0;

    public  void deposit(double amount) {
        balance = balance + amount;
    }
    public  void  withdraw(double amount) {
        balance = balance - amount;
    }
    public  double getBalance() {
        return balance;
    }
//synchronized
}

public class SynchronizedLabPart2 {

    int n = 1000000;

    Account acct = new Account();

    // deposit 1,2,3,...,n to the account
    Thread t1 = new Thread( new Runnable() {
        public void run() {
            for(int j = 1; j <= n; j++) {
                acct.deposit(1.0);
                //System.out.println("deposit " + acct.getBalance());
            }
        }});

    // withdraw 1,2,3,...,n from the account
    Thread t2 = new Thread( new Runnable() {
        public void run() {
            for(int j = 1; j <= n; j++) {
                acct.withdraw(1.0);
                //System.out.println("withdraw " + acct.getBalance());
            }
        }});

    public void doBanking() {

        t1.start();
        t2.start();


        // wait for t1 to finish
        try { t1.join(); } catch(InterruptedException e){}
        // wait for t2 to finish
        try { t2.join(); } catch(InterruptedException e){}
        System.out.println("Done waiting");
    }

    public static void main(String[] args) {
        SynchronizedLabPart2 m = new SynchronizedLabPart2();
        m.doBanking();
        System.out.println("Balance should be 0.0  balance = " + m.acct.getBalance());

    }
}
