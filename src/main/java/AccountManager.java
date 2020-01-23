// Password Manager
// Nathan Hoersch
// Tyler Hoersch

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AccountManager implements Runnable {
    private static final int NEW_ACCOUNT = 0;
    private static final int VIEW_ACCOUNTS = 1;

    private final List<Account> accounts;
    private final File databaseFile;

    public AccountManager() {
        databaseFile = createFileIfNotExist();
        accounts = readAccountsFromFile(databaseFile);
    }

    private File createFileIfNotExist() {
        File databaseFile = new File("myDB.txt");
        try {
            databaseFile.createNewFile();
        } catch (IOException e) {
            displayErrorMessage("Unable to find or created database file.");
        }
        return databaseFile;
    }

    private final List<Account> readAccountsFromFile(File file) {
        List<Account> accounts = null;
        ObjectInputStream objectInputStream = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);
            accounts = (List<Account>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            displayErrorMessage("Unable to find database file.");
        } catch (IOException | ClassNotFoundException e) {
            accounts = new ArrayList<>();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return accounts;
    }

    private void writeAccountsToFile(List<Account> accounts, File file) {
        ObjectOutputStream outputStream = null;

        try {
            FileOutputStream fos = new FileOutputStream(file);
            outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(accounts);

        } catch (FileNotFoundException e) {
            displayErrorMessage("Unable to find database file.");
        } catch (IOException e) {
            displayErrorMessage("Internal error: file corruption.");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayErrorMessage(String error) {
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void enterNewPassword() {
        final Account account = new Account();


        while(account.getTitle().isEmpty()) {
            String title = JOptionPane.showInputDialog("Enter Account Title");
            if (title == null) {
                return;
            }
            account.setTitle(title);
        }

        while(account.getUsername().isEmpty()) {
            String username = JOptionPane.showInputDialog("Enter Username");
            if (username == null) {
                return;
            }
            account.setUsername(username);
        }

        while(account.getPassword().isEmpty()) {
            String password = JOptionPane.showInputDialog("Enter Password");
            if (password == null) {
                return;
            }
            account.setPassword(password);
        }

        final String confirmation = String.format("Is the following Correct?\n" + "Title: %s\n" + "Username: %s\n" + "Password: %s", account.getTitle(), account.getUsername(), account.getPassword());
        int confirm = JOptionPane.showConfirmDialog(null, confirmation);

        switch(confirm) {
            case 0 :
                accounts.add(account);
                JOptionPane.showMessageDialog(null, "Added");
                break;
            case 1 :
                enterNewPassword();
            case 2 :
                JOptionPane.showMessageDialog(null, "Cancelled");
                break;
        }

        writeAccountsToFile(accounts, databaseFile);

    }

    public void viewPasswordList() {
        final StringBuilder display = new StringBuilder();

        for(Account account : accounts) {
            display.append(account.toString()).append("\n\n");
        }

        //todo: make a button instead of just text so that you can click to view/edit an account
        JTextArea textArea = new JTextArea(display.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);  
        textArea.setLineWrap(true);  
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        scrollPane.setPreferredSize( new Dimension( 300, 500 ) );
        JOptionPane.showMessageDialog(null, scrollPane, "Account Manager",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void run() {
        while(true) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Object[] options = {"Add Account","View Accounts"};
            int selectedOption = JOptionPane.showOptionDialog(null,
                                        "Thanks for using Account Manager. Please choose from the following options:",
                                            "Account Manager",
                                                  JOptionPane.YES_NO_CANCEL_OPTION,
                                                  JOptionPane.QUESTION_MESSAGE,
                                            null,
                                                  options,
                                       null);


            if(selectedOption == NEW_ACCOUNT) {
                enterNewPassword();
            } else if(selectedOption == VIEW_ACCOUNTS) {
                viewPasswordList();
            } else{
                break;
            }
        }
    }

    public static void main(String args[]) {
        Thread thread = new Thread(new AccountManager());
        thread.start();
    }
}



