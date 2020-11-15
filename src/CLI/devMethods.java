package CLI;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.*;

public class devMethods {

    public devMethods() {
    }

    private LocalDate parseDate(String date) {
        String[] dateObj = date.split("-");
        LocalDate d = LocalDate.of(Integer.valueOf(dateObj[2]), Integer.valueOf(dateObj[1]),
                Integer.valueOf(dateObj[0]));
        return d;
    }

    public int signInPage() {

        System.out.println("---------------Welcome to Play Store---------------");
        Scanner in = new Scanner(System.in);

        blInterface.devInterface dev = new businessLayer.Dev();

        while (true) {

            System.out.println("Press S to sign in");
            System.out.println("Press U to sign up if you don't have an account");

            String input = in.nextLine();

            if (input.equalsIgnoreCase("S")) {
                System.out.flush();
                Scanner signIn = new Scanner(System.in);
                System.out.println("Enter your email");
                String e = signIn.nextLine();

                System.out.println("Enter your password");
                String p = signIn.nextLine();

                int loginSuccessfull = dev.authenticateUser(e, p);
                signIn.close();
                in.close();
                return loginSuccessfull;
            } else if (input.equalsIgnoreCase("U")) {

                System.out.flush();

                System.out.println("Enter your Name");
                String name = in.nextLine();

                System.out.println("Enter your Date of Birth in format dd-MM-yyyy");
                // String dateFormat = "dd-MM-yyyy HH:mm:ss";
                String date = in.nextLine();
                // DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(dateFormat);
                LocalDate dob = parseDate(date); // LocalDate.parse(date, myFormatObj);
                System.out.println("Enter your email");
                String email = in.nextLine();

                System.out.println("Enter your password");
                String password = in.nextLine();

                blInterface.userDetails developer = new blInterface.userDetails(name, 0, dob, email, password);

                int addDevSuccessfull = dev.addDev(developer);
                in.close();
                return addDevSuccessfull;

            } else {
                System.out.println("Wrong Input. Try Again :)");
            }

        }

    }

    public void categoryPage() {

        blInterface.devInterface dev = new businessLayer.Dev();

        while (true) {
            int successfull = signInPage();
            if (successfull == -1) {
                System.out.println("Login Unsuccessfull. Try Again:)");
            }

            else {
                while (true) {
                    System.out.flush();
                    System.out.println("Press P to publish new App");
                    System.out.println("Update an existing App");
                    System.out.println("Remove an App");

                    Scanner in = new Scanner(System.in);
                    String input = in.nextLine();

                    if (input.equalsIgnoreCase("P")) {

                        System.out.flush();
                        System.out.println("Enter the name of the App");
                        String name = in.nextLine();

                        System.out.println("Enter the version of the App");
                        int version = in.nextInt();
                        String categ;
                        while (true) {
                            System.out.println("Select the Category of the App from");
                            System.out.println("G for Games");
                            System.out.println("E for Education");
                            System.out.println("P for Productivity");
                            System.out.println("C for Communication");
                            categ = in.nextLine();

                            if (categ.equalsIgnoreCase("g")) {
                                categ = "Games";
                                break;
                            } else if (categ.equalsIgnoreCase("e")) {
                                categ = "Education";
                                break;
                            } else if (categ.equalsIgnoreCase("P")) {
                                categ = "Productivity";
                                break;
                            } else if (categ.equalsIgnoreCase("C")) {
                                categ = "Communication";
                                break;
                            } else {
                                System.out.println("Wrong Input. Try Again :)");
                            }
                        }

                        System.out.println("Enter description of the App");
                        String desc = in.nextLine();
                        List<Integer> UserRatings = null;

                        blInterface.App newApp = new blInterface.App(0, name, desc, version, categ, UserRatings, 0,
                                null);
                        dev.addApp(successfull, newApp);
                        System.out.println("New App Added Successfully");
                    }

                    else if (input.equalsIgnoreCase("U")) {

                    }

                    else if (input.equalsIgnoreCase("R")) {

                    }

                    else {
                        System.out.println("Wrong Input. Try Again :)");
                    }

                }
            }
        }

    }

}