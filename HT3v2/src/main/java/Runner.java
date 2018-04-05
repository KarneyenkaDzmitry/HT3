import application.AppClass;

import java.io.IOException;

public class Runner {
    private static String help = "Put two parameters via space as arguments." +
            "The first parameter has to be existing file with instructions." +
            "The second parameter has to be existing or not existing file for statistics.";
    public static void main(String[] args) {
        if (args.length == 2) {
            AppClass application = new AppClass(args[0], args[1]);
            try {
                application.run();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Wrong parameters!!!!\nhelp");
            }
        } else {
            System.out.println(help);
        }
    }
}
