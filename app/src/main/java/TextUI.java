
public class TextUI implements UI {
    
    public TextUI() {
        
    }
    
    public void display(String s) {
        System.out.println(s);
    }

    @Override
    public int getInt(int min, int max) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getString(int max) {
        // TODO Auto-generated method stub
        return null;
    }

}
