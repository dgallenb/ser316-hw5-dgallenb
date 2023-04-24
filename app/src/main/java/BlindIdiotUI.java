
public class BlindIdiotUI implements UI {

    @Override
    public void display(String s) {
        System.out.println(s);
        
    }

    @Override
    public int getInt(int min, int max) {
        if (min > max) {
            int temp = max;
            max = min;
            min = temp;
        }
        if (min == max) {
            return min;
        }
        int choice = (int) Utility.d(max) - 1 + min;
        display("Blind Idiot chose " + choice + ".");
        return choice;
    }

    @Override
    public String getString(int max) {
        // TODO Auto-generated method stub
        return null;
    }

}
