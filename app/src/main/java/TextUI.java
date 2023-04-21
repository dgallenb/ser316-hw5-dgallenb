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
        int input = s.nextInt();
        while(true) {
            if((input <= max) && (input >= min)) {
                break;
            }
            input = s.nextInt();
        }
        return input;
    }

    @Override
    public String getString(int max) {
        // TODO Auto-generated method stub
        return null;
    }

}
