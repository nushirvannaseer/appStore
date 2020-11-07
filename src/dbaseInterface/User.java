package dbaseInterface;

import java.io.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class User {

    public int addUser(userDetails user) {

        //first we see how many users are already there
        File count_file = new File("totalUsers.txt");
        int file_status = FileControl.CheckFile(count_file);

        if(file_status == -1)   //couldn't make file
           return -1;

        else {              //file created or exists

            try {

                int total_users = 0;
                if(file_status == 0) {      //file exists already and we can scan data from it

                    Scanner scanner = new Scanner(count_file);
                    String data = scanner.nextLine();
                    scanner.close();
                    total_users = Integer.valueOf(data); // gives no. of users from first line of text file
                }
                
                //Till here, we know exactly how many users are already there;
                //updating the new number in count file;

                FileWriter writer = new FileWriter(count_file);
                int _newID = total_users+1;
                writer.write(_newID + "\n");

                //now, its time to append our new user in user_record;
                //for that, opening/creating new file i.e. app_file
                
                File user_file = new File("user_record.txt");
                file_status = FileControl.CheckFile(user_file);
                writer.close();

                if(file_status == -1)   //couldn't make file
                    return -1;

                else {          //file opened or created;

                    String output;  //this string will contain all the required user info to be printed
                    /*
                        Syntax of filing:
                        UserID
                        Name
                        DOB
                        password
                        email
                    */

                    output = String.valueOf(_newID);      //userID has been assignedS
                    FileControl.AppendInFile(user_file, output);     //this call will append new user data into our file
                    output = user.Name;
                    FileControl.AppendInFile(user_file, output);
                    output = String.valueOf(user.DOB);
                    FileControl.AppendInFile(user_file, output);
                    output = user.password;
                    FileControl.AppendInFile(user_file, output);
                    output = user.email;
                    FileControl.AppendInFile(user_file, output);

                    //following is basically the original code which is called in AppendInFile() call
                    // FileWriter fw = new FileWriter(app_file, true);
                    // BufferedWriter bw = new BufferedWriter(fw);
                    // PrintWriter out = new PrintWriter(bw);
                    // out.println(String.valueOf(app.avgRatings));
                    // out.close();
                    // bw.close();
                    // fw.close();
                    return _newID;
                }
                
            } catch (IOException e) {

                System.out.println("An error occured in writing user file\n");
                e.printStackTrace();
            }

        }

        return -1;
    }

    public userDetails getUserDetails(int userID) {

        //initializing the returning object
        userDetails details = new userDetails();
        //first we check if users even exist or not
        File count_file = new File("totalUsers.txt");
        int file_status = FileControl.CheckFile(count_file);

        if(file_status == -1 || file_status == 1)   //file wasn't present earlier
            return details;
        
        //now we know that file exists
        //up to the real work now. Reading file to find the userID ughh

        File user_file = new File("user_record.txt");
        file_status = FileControl.CheckFile(user_file);

        if(file_status == -1 || file_status == 1)       //couldn't open file
            return details;
        
        try {
            Scanner scanner = new Scanner(user_file);
            while(scanner.hasNextLine()) {              //will read the file till end
                
                String data = scanner.nextLine();
                try {
                    if(Integer.valueOf(data) == userID) {

                        details.userID = userID;            //ID found, hence stored in returning obj
                        details.Name = scanner.nextLine();  //next line has name, so stored
                        details.DOB = LocalDate.parse(scanner.nextLine());  //date is converted from String to LocalDate type to store back in returning obj
                        details.password = scanner.nextLine();
                        details.email = scanner.nextLine();

                        break;
                    }
                }
                catch (Exception ex) {
                    continue;
                }
            }
            scanner.close();
            return details;
        }
        catch (Exception e) {

            System.out.println("An error occured in writing user file\n");
            e.printStackTrace();
        }

        return details;

    }
    // made this main() to test the functions ill be making in this class
    public static void main(String[] args) {

        // LocalDate dobby = LocalDate.now();
        //User testing = new User();
        // userDetails obj = new userDetails("Afaq", 13233, dobby, "afaq@gmail", "nooo");
        // testing.addUser(obj);

        // for(int i=1; i<9; i++) {
        //     userDetails obj = testing.getUserDetails(i);
        //     if(obj.userID != -1) 
        //         System.out.println("\n" + obj.userID + "\n" + obj.Name + "\n" + obj.DOB + "\n" + obj.password + "\n" + obj.email + "\n");
            
        //     else
        //         System.out.println("\nUSER NOT FOUND\n");
        // }
    }
}
