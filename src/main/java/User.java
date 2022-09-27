

public class User {
    private String name;
    private String password;
    private int balance;
    private boolean isPremium;
    private final int PREMIUMCOST = 50;

    public User(String name, String password, int balance, boolean isPremium) {
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.isPremium = isPremium;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void streamMusic(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public void buyPremium() {
        if (isPremium) {
            System.out.println("ALREADY A PREMIUM MEMBER!");
        }
        if (balance - PREMIUMCOST < 0 ) {
            System.out.println("Not enough funds!");
            return;
        }
        System.out.println("\nYOU CAN NOW ACCESS PREMIUM SONGS!\n");
        balance -= PREMIUMCOST;
        isPremium = true;
    }

    public void cancelPremium() {
        System.out.println("PREMIUM MEMBERSHIP CANCELED!");
        System.out.println("\n");
        isPremium = false;
    }

    public void addBalance(int amount) {
        int MAXBALANCE = 1000;
        if (amount < 0) {
            return;
        }
        balance += amount;
        if (balance > MAXBALANCE) {
            System.out.println("MAX BALANCE is 1000!");
            balance = MAXBALANCE;
        }
    }
    public String getUserDetails() {
        return name + "," + password + "," + balance + "," + isPremium;
    }


    @Override
    public String toString() {
        return "Name: " + name + " || Balance: " + balance + " || Premium Member: " + isPremium;
    }

}
