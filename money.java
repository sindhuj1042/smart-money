package oops;

import java.io.*;
import java.util.*;

// Interface to define balance operations
interface BalanceOperations {
    int present(String name, String nam2, int am);
    int getBalance(String name);
    void saveData();
    void loadData();
}

// Base class for storing names
class getname {
    String name;

    getname(String name) {
        this.name = name;
    }

    String printname() {
        return name;
    }
}

// Class to manage balance with the ability to save and load data
class balance extends getname implements BalanceOperations {
    private Map<String, Integer> amt;
    private Map<String, Integer> amt2;
    private static final String FILE_NAME = "balanceData.txt";

    balance(String name) {
        super(name.toLowerCase());
        amt = new HashMap<>();
        amt.put("shriney", 230000);
        amt.put("poul", 320000);
        amt.put("rinson", 300000);
        amt.put("jesta", 900000);

        amt2 = new HashMap<>();
        loadData(); 
        amt2.putAll(amt);
    }

    @Override
    public int present(String name, String nam2, int am) {
        if (amt2 == null) {
            System.out.println("Not present");
            return 0;
        }
        name = name.toLowerCase();
        nam2 = nam2.toLowerCase();

        if (amt2.containsKey(name) && amt2.containsKey(nam2)) {
            int cb = amt2.getOrDefault(name, 0);
            int cd = amt2.getOrDefault(nam2, 0);

            System.out.println(cb);

            if (am > cb) {
                System.out.println("SORRY, YOUR BALANCE IS LOW! :(");
                return cb;
            } else if (cb <= 600 && am <= 600) {
                System.out.println("SORRY, YOU'RE UNDER BALANCE! :(");
                return cb;
            } else {
                cb -= am;
                cd += am;
                amt2.put(name, cb);
                amt2.put(nam2, cd);
               // System.out.println(amt2);

                System.out.println("YOU SUCCESSFULLY LENT MONEY! :)");
                saveData();
              //  return amt2.get(name);
            }
        }

        return 0;
    }

    @Override
    public int getBalance(String name) {
        return amt2.getOrDefault(name.toLowerCase(), 0);
    }

    // Save the amt2 map to a file
    @Override
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(amt2);
            System.out.println("saved");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load the amt2 map from a file
    @SuppressWarnings("unchecked")
	@Override
    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            amt2 = (Map<String, Integer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
            amt2 = new HashMap<>(amt);
        }
    }
}

// Class to manage lending operations
class lend extends balance {

    lend(String name) {
        super(name);
    }

    void displayUpdatedAmt(String name) {
        int updatedBalance = getBalance(name);
        System.out.println("Updated balance for " + name + ": " + updatedBalance);
    }
}


public class money {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        lend n = new lend("default");
        String name;
        System.out.print("Enter your name: ");
        name = sc.nextLine();
        boolean running = true;
        while (running) {
            System.out.println("---------------------------------------------");
            System.out.println("Enter 1 to check balance");
            System.out.println("Enter 2 to lend money");
            System.out.println("Enter 3 to check remaining money");
            System.out.println("Enter 4 to exit");
            System.out.println("---------------------------------------------");
            System.out.println("Enter your choice");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            String  name1;
            
            int amtnee;
            

            switch (choice) {
                case 1:
                   
                    n.displayUpdatedAmt(name);
                    break;

                case 2:
                    while (true) {
                        System.out.print("Enter the person you want to lend money to: ");
                        name1 = sc.nextLine();
                        System.out.print("Enter amount to lend: ");
                        amtnee = sc.nextInt();
                        sc.nextLine(); 
                        n.present(name, name1, amtnee);
                        System.out.print("Do you want to perform another lending operation? (yes/no): ");
                        String continueLending = sc.nextLine();
                        if (!continueLending.equalsIgnoreCase("yes")) {
                            break;
                        }
                    }
                    break;

                case 3:
                    n.displayUpdatedAmt(name);
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    System.out.println("Enter a number between 1 and 4");
            }

            if (choice != 4) {
                System.out.print("Do you want to continue? (yes/no): ");
                String continueOption = sc.nextLine();
                if (!continueOption.equalsIgnoreCase("yes")) {
                    running = false;
                }
            }
        }

        sc.close();
    }
}
