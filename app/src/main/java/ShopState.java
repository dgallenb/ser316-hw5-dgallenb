import java.util.ArrayList;

public class ShopState implements GameState {
    protected ArrayList<TrainerEntity> trainers;
    protected UI ui;
    protected Weather weather;
    protected int nextState;
    protected int idiotLimit;
    
    public ShopState(ArrayList<TrainerEntity> trainers,  UI ui, Weather weather) {
        this.trainers = trainers;
        this.ui = ui;
        this.weather = weather;
        nextState = 0;
        idiotLimit = 5;
        
    }

    @Override
    public ArrayList<TrainerEntity> processState(ArrayList<TrainerEntity> trainers) {
        this.trainers = trainers;
        if(weather.isDay()) {
            shopOpen();
        }
        else {
            shopClosed();
        }
        
        return this.trainers;
    }
   
    public void shopOpen() {
        ui.display("You waltz into the shop on a " + weather.toString() + " day.");
        shopMenu();
    }
    
    public void shopMenu() {
        String s = "";
        s += "You have $" + trainers.get(0).getTrainer().getMoney() + " to spend.\n";
        s += "1. Potion ($200)\n" + "2. Capture Stone ($200)\n" + "3. XAttack ($2000)\n";
        s += "4. XDefend ($2000)\n" + "5. XSpeed ($2000)\n" + "6. Move Stone ($500)\n";
        s += "7. Mighty Move Stone ($2000)\n" + "8. Epic Move Stone ($5000)\n";
        s += "9. Leave\n";
        ui.display(s);
        int choice = ui.getInt(1, 9);
        Item item;
        int itemCost = 0;
        switch(choice) {
        case 1:
            item = new Item("Potion", "Heals 20HP.", 1);
            itemCost = 200;
            break;
        case 2:
            item = new Item("Capture Stone", "Captures Codemon (probably)", 1);
            itemCost = 200;
            break;
        case 3:
            item = new Item("XAttack", "Temporarily increases a Codemon's attack.", 1);
            itemCost = 2000;
            break;
        case 4:
            item = new Item("XDefend", "Temporarily increases a Codemon's defense.", 1);
            itemCost = 2000;
            break;
        case 5:
            item = new Item("XSpeed", "Temporarily increases a Codemon's speed.", 1);
            itemCost = 2000;
            break;
        case 6:
            item = new MoveItem("Move Stone", "Teaches a codemon a new move.", 1);
            itemCost = 500;
            break;
        case 7:
            item = new MoveItem("Mighty Move Stone", "Teaches a codemon a stronger move.", 1);
            itemCost = 2000;
            break;
        case 8:
            item = new MoveItem("Epic Move Stone", "Teaches a codemon a powerful move.", 1);
            itemCost = 5000;
            break;
        case 9:
            ui.display("You leave, knowing you'll return sooner or later.");
            return;

        default:
            item = new Item("Mysterious Berry", "It probably does something. Who knows?", 1);
            itemCost = 200;
            break;
        }
        if(moneyCheck(trainers.get(0).getTrainer(), itemCost)) {
            trainers.get(0).getTrainer().addMoney(-1 * itemCost);
            trainers.get(0).getTrainer().addItem(item);
            ui.display("You bought a " + item.getName() + ". ");
            
        }
        else {
            ui.display("You don't have enough money for " + item.getName());
            if(idiotLimit <= 0) {
                idiotLimit = 5;
                ui.display("After throwing a fit, the staff ask you to leave");
                return;
            }
            else {
                --idiotLimit;
            }
        }
        shopMenu();
    }
    
    public boolean moneyCheck(Trainer t, int cost) {
        return t.getMoney() >= cost;
    }
    
    public void shopClosed() {
        ui.display("You spend hours staring at the shop window in the dark of the night. " +
                "You might wish to consider getting a less disturbing hobby.");
    }

    @Override
    public int nextState() {
        return 0;
    }

}
