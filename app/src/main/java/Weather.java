
public class Weather {
    // types of weather: clear, cloudy, rainy, windy, stormy, snowy, eclipse
    
    protected int type;
    
    public Weather(int i) {
        setType(i);
    }
    
    public Weather(String s) {
        setType(s);
    }
    
    public void setType(int i) {
        if((i < 0) || (i > 6)) {
            type = 0;
        }
        type = i;
    }
    
    public void setType(String s) {
        switch(s) {
            case "SUNNY":
            case "sunny":
            case "SUN":
            case "sun":
            case "CLEAR":
            case "clear":
                type = 0;
                break;
            case "CLOUDY":
            case "cloudy":
            case "CLOUD":
            case "cloud":
                type = 1;
                break;
            case "RAINY":
            case "rainy":
            case "RAIN":
            case "rain":
                type = 2;
                break;
            case "WINDY":
            case "windy":
            case "WIND":
            case "wind":
                type = 3;
                break;
            case "STORMY":
            case "stormy":
            case "STORM":
            case "storm":
                type = 4;
                break;
            case "SNOWY":
            case "snowy":
            case "SNOW":
            case "snow":
                type = 5;
                break;
            case "ECLIPSE":
            case "eclipse":
                type = 6;
                break;
            default:
                type = 1;
        }
    }
    
    public int getTypeNum() {
        return type;
    }
    
    public String toString() {
        switch(type) {
        
        case 0:
            return "clear";
        case 1:
            return "cloudy";
        case 2: 
            return "rainy";
        case 3:
            return "windy";
        case 4:
            return "stormy";
        case 5:
            return "snowy";
        case 6:
            return "eclipse";
        default:
            return "clear";
        }
    }
    
    public Weather copy() {
        return new Weather(type);
    }
    
    /**
     * Randomly changes the weather to the next state, as dictated by the table below.
     * @return The string name for the new weather.
     */
    public String transition() {
        double rng = Math.random();
        
        double[][] transitionMatrix =  {
                new double[] {0.35, 0.29, 0.1, 0.1, 0.05, 0.1, 0.01}, // clear
                new double[] {0.2, 0.29, 0.2, 0.05, 0.12, 0.13, 0.01}, // cloudy
                new double[] {0.05, 0.3, 0.29, 0, 0.3, 0.05, 0.01}, // rainy
                new double[] {0.25, 0.1, 0, 0, 0.64, 0, 0.01}, // windy
                new double[] {0.05, 0.1, 0.3, 0.1, 0.39, 0.05, 0.01}, // stormy
                new double[] {0.05, 0.15, 0.3, 0, 0.05, 0.44, 0.01}, // snowy
                new double[] {0.15, 0, 0, 0, 0, 0, 0.85} // eclipse
        };
        
        type = Utility.rollOnTable(transitionMatrix[type]);
        return this.toString();
    }
}
