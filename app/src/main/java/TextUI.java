import java.util.Scanner;

public class TextUI implements UI {
    Scanner s;
    
    public TextUI() {
        s = new Scanner(System.in);
    }
    
    public void display(String s) {
        System.out.println(s);
    }

    @Override
    public int getInt(int min, int max) {
        
        int input = getInt();
        while(true) {
            if((input <= max) && (input >= min)) {
                break;
            }
            display("Please input a value in the specified range");
            input = getInt();
        }
        return input;
    }
    
    private int getInt() {
        while(!s.hasNextInt()) {
            s.nextLine();
            display("Please input a valid value.");
        }
        return s.nextInt();
    }

    @Override
    public String getString(int max) {
        // TODO Auto-generated method stub
        return null;
    }

}
