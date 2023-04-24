import java.util.Scanner;

/**
 * A basic implementation of a UI that sanitizes input values. 
 * @author DJ
 *
 */
public class TextUI implements UI {
    Scanner scanner;
    
    public TextUI() {
        scanner = new Scanner(System.in);
    }
    
    public void display(String s) {
        System.out.println(s);
    }

    @Override
    public int getInt(int min, int max) {
        
        int input = getInt();
        while (true) {
            if ((input <= max) && (input >= min)) {
                break;
            }
            display("Please input a value in the specified range");
            input = getInt();
        }
        return input;
    }
    
    private int getInt() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            display("Please input a valid value.");
        }
        return scanner.nextInt();
    }

    @Override
    public String getString(int max) {
        // TODO Auto-generated method stub
        return null;
    }

}
