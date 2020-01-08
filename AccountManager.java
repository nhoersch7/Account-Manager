//Password Manager
//Nathan Hoersch

//imports
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class AccountManager{

/*********************************************************************************************/
    //create array for password temp storage
    static ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    static ArrayList<String> type = new ArrayList<String>();
    static ArrayList<String> usernames = new ArrayList<String>();
    static ArrayList<String> passwords = new ArrayList<String>();
/*********************************************************************************************/

    //method for creating the password file upon first program use.
    //will not re-create file if it already exists.
    public void createFile() throws IOException {
        File testfile = new File("progfile.txt");
        testfile.createNewFile();
    }

/**
 * @throws IOException *******************************************************************************************/

    //method for entering a new password to the list.
    public void enterNewPassword() throws IOException {
      
        String str1 = null, str2 = null, str3 = null;
        Component frame = null;

        int quit = 0;

        while(quit == 0){
        
        
        str1 = JOptionPane.showInputDialog("Enter Account Type");
        while(str1.isEmpty()){
            str1 = JOptionPane.showInputDialog("Enter Account Type");
        }

        str2 = JOptionPane.showInputDialog("Enter Username");
        while(str2.isEmpty()){
            str2 = JOptionPane.showInputDialog("Enter Username");
        }

        str3 = JOptionPane.showInputDialog("Enter Password");
        while(str3.isEmpty()){
            str3 = JOptionPane.showInputDialog("Enter Password");

        }
        

        int confirm = JOptionPane.showConfirmDialog(frame, "Is the following Correct?\n" + "Type: " + str1 + "\n" + 
                                              "Username: " + str2 + "\n" + "Password: " + str3);
        
        switch(confirm){
            case 0 :
                type.add(str1);
                usernames.add(str2);
                passwords.add(str3);
                JOptionPane.showMessageDialog(frame, "Added");
                quit = 1;
                break;
            case 1 :
                JOptionPane.showMessageDialog(frame, "Please re-enter the account info.");
                break;
            case 2 :
                JOptionPane.showMessageDialog(frame, "Cancelled");
                quit = 1;
                break;
            default:
                quit = 1;
                break;
        }
    }
        
        
        BufferedWriter writer = new BufferedWriter(new FileWriter("progfile.txt"));
        
        for(int i=0; i<type.size(); i++) {
            writer.write(type.get(i) + "\n");
            writer.write(usernames.get(i) + "\n");
            writer.write(passwords.get(i) + "\n");
        }
        
        writer.close();
 
            return;

    }
    
/**
 * @throws FileNotFoundException *******************************************************************************************/
    public void viewPasswordList() throws FileNotFoundException{
            
        
        ArrayList<String> tempList = new ArrayList<String>();
        
        for(int i=0; i < type.size(); i++) {
            tempList.add(type.get(i));
            tempList.add(usernames.get(i));
            tempList.add(passwords.get(i));
            tempList.add("\n");
        }
        
        String str1 = tempList.toString();
        str1 = str1.substring(0, str1.length()-1);
        
        str1 = str1.replace('[', ' ');
        str1 = str1.replaceAll(",", "\n");

        
        JTextArea textArea = new JTextArea(str1);
        JScrollPane scrollPane = new JScrollPane(textArea);  
        textArea.setLineWrap(true);  
        textArea.setWrapStyleWord(true); 
        scrollPane.setPreferredSize( new Dimension( 300, 500 ) );
        JOptionPane.showMessageDialog(null, scrollPane, "Account Manager",  
                                               JOptionPane.YES_NO_OPTION);
        
        
        
    }

/*********************************************************************************************/

    public static void main(String args[]) throws IOException{
        
        AccountManager pass = new AccountManager();

        /*********************************************************************************************/
        //create the file if not already in existence.
        pass.createFile();

        /*********************************************************************************************/
        //load file
        try {
            BufferedReader br = new BufferedReader(new FileReader("progfile.txt"));
            br.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //create three buffered readers for adding elements to three different array lists with the same scanner.
        BufferedReader br = new BufferedReader(new FileReader("progfile.txt"));
        BufferedReader br1 = new BufferedReader(new FileReader("progfile.txt"));
        BufferedReader br2 = new BufferedReader(new FileReader("progfile.txt"));
        
        
        //set scanner and scan for types
        Scanner scan = new Scanner(br);

        while(scan.hasNext() == true) {
            if(scan.hasNext())type.add(scan.nextLine());
            if(scan.hasNext()) {
                scan.nextLine();
            }
            if(scan.hasNext()){
                scan.nextLine();
            }
        }
        
        scan.close();
        //set scanner and scan for user names
        scan = new Scanner(br1);
        
        while(scan.hasNext() == true) {
            if(scan.hasNext())scan.nextLine();
            if(scan.hasNext())usernames.add(scan.nextLine());
            if(scan.hasNext())scan.nextLine();
        }
           
        scan.close();
        //set scanner and scan for passwords
        scan = new Scanner(br2);
        while(scan.hasNext() == true) {
            if(scan.hasNext())scan.nextLine();
            if(scan.hasNext())scan.nextLine();
            if(scan.hasNext())passwords.add(scan.nextLine());
        }
        scan.close();

        /*********************************************************************************************/
        int quit = 0;
        while(quit == 0) {
        //create first dialog screen for user.
        Object[] options = {"ENTER NEW ACCOUNT INFO","SEE THE ACCOUNT INFO LIST"};
        Component frame = null;
        int n = JOptionPane.showOptionDialog(frame,
            "Thanks for using Account Manager. Please choose from the following options:",
            "Acount Manager", JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, null);

        /*********************************************************************************************/
        //run different operations based on the entered option.
        if(n == 0) {
            //run method to enter a new password to the list
            pass.enterNewPassword();
        }
        else if(n == 1) {
            // run method to show the password list.
            pass.viewPasswordList();
        }
        else{
            quit=1;
        }
    
        }

    }
}


