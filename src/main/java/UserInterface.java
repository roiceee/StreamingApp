import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {
    private Scanner scanner;
    private MusicPlayer player;
    private Validator validator;
    private boolean proceed;
    private boolean exit;
    private User user;

    public UserInterface(Scanner scanner) {
        this.scanner = (scanner);
        try {
            this.player = new MusicPlayer();
            this.validator = new Validator();
        } catch (IOException e) {
            e.printStackTrace();
        }

        proceed = false;
        exit = false;
    }

    public void start() {
        validator.loadUsers();
        do {
            printStart();
            int choice = getInput(0);
            processChoice(choice);
        } while (!proceed);
        System.out.println("\nHi, " + user.getName() + "!\n");
        player.loadMusicFiles();
        do {
            printOptions();
            System.out.println(user);
            int choice = getInput(1);
            processMainChoice(choice);
        } while (!exit);

    }

    private void processChoice(int choice) {

        switch (choice) {
            case 1:

                try {
                    logIn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            return;
            case 2:
                try {
                    register();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return;
            case 3: System.exit(0);
        }
    }

    private void processMainChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    System.out.println("______________________________________");
                    player.printMusic(user.isPremium());
                    try {
                        playMusicChoices(user.isPremium());
                    } catch (UnsupportedAudioFileException | LineUnavailableException| IOException e) {
                        e.printStackTrace();
                    }
                    return;
                case 2:
                    addBalance();
                    Thread.sleep(2000);
                    return;
                case 3:
                    user.buyPremium();
                    Thread.sleep(2000);
                    return;
                case 4:
                    user.cancelPremium();
                    Thread.sleep(2000);
                    return;
                case 5:
                    System.out.println("\n\nSAVING DATA...");
                    Thread.sleep(2000);
                    System.out.println("COME LISTEN ANYTIME!");
                    try {
                        validator.saveUser(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    exit = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void register() throws IOException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (validator.isFound(name, password)) {
            System.out.println("USER ALREADY EXISTS!");
            return;
        }
        if (isValidInfo(name, password)) {
            validator.registerUser(name, password);
            System.out.println("USER REGISTERED SUCCESSFULLY!");
            return;
        }
        System.out.println("Only Alphanumeric Characters are allowed!");
    }

    private void logIn() throws InterruptedException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (validator.isFound(name, password)) {
            user = validator.getUser(name, password);
            proceed = true;
            return;
        }
        System.out.println("USER NOT FOUND!");
        Thread.sleep(2000);
    }

    private void playMusicChoices(boolean isPremium) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        System.out.println();
        int choice = getChoice(isPremium);
        if (player.getMusicPrice(choice - 1) > user.getBalance()) {
            System.out.println("\nNOT ENOUGH BALANCE\n");
            Thread.sleep(2000);
            return;
        }
        user.streamMusic(player.getMusicPrice(choice - 1));
        player.loadMusic(choice - 1);
        player.play();
        printMusicControls();
        choice = getInput(3);
        if (choice == 1) {
            player.stop();
        }
    }
    private void addBalance() {
        System.out.print("Enter amount to add: ");
        user.addBalance(Integer.parseInt(scanner.nextLine()));
        System.out.println("ADDED TO BALANCE!");
    }

    private static void printMusicControls() {
        System.out.println("\n(1) STOP");
    }

    private boolean isValidInfo(String name, String password) {
        Pattern pattern = Pattern.compile("[^\\d\\w]");
        Matcher matcher = pattern.matcher(name + password);
        return !matcher.find();
    }

    private int getChoice(boolean isPremium) {
        int choice;
        do {
            System.out.print("Enter Choice: ");
            choice = Integer.parseInt(scanner.nextLine());
        } while ((choice < 1 || choice > player.getNumOfMusic(isPremium)));
        return choice;
    }

    private int getInput(int flag) {
        String choice;
        do {
            System.out.print("Enter choice: ");
            choice = scanner.nextLine();
        } while (!isValid(choice, flag));
        return Integer.parseInt(choice);
    }

    private static void printStart() {
        System.out.println("""
                ----------------------
                ||ROICE MUSIC PLAYER||
                ||(1) LOG IN        ||
                ||(2) REGISTER      ||
                ||(3) EXIT          ||
                ----------------------
                """);
    }

    private static void printOptions() {
        System.out.println("""
                ------------------------------------------
                ||(1) PLAY MUSIC                        ||
                ||(2) ADD BALANCE                       ||
                ||(3) BUY PREMIUM MEMBERSHIP (COST: 50) ||
                ||(4) CANCEL PREMIUM MEMBERSHIP         ||
                ||(5) EXIT                              ||
                ------------------------------------------
                """);
    }

    private boolean isValid(String choice, int flag) {
        if (flag == 0) {
                switch (choice) {
                    case "1":
                    case "2":
                    case "3": return true;
                    default: return false;
                }
        }
        else if (flag == 1) {
            switch (choice) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5": return true;
                default: return false;
            }
        }
        else if (flag == 3) {
           return (choice.equals("1"));
        }
        return false;
    }
}
