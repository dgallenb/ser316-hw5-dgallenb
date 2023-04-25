import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WeatherTest {
    
    @Test
    public void testWeather() {
        for(int i = 0; i < 7; ++i) {
            testWeather(i);
        }
    }
    
    public void testWeather(int i) {
        Weather w = new Weather(i);
        w.setType(i);
        assertEquals(w.getTypeNum(), i);
        assertEquals(w.toString(), getStringFromInt(i));
        
        Weather w2 = new Weather(getStringFromInt(i));
        assertEquals(w2.getTypeNum(), i);
        assertEquals(w2.toString(), getStringFromInt(i));
        
        Weather w3 = new Weather(getStringFromInt(i).toUpperCase());
        assertEquals(w3.getTypeNum(), i);
        assertEquals(w3.toString(), getStringFromInt(i));
        
    }
    
    @Test
    public void testWeatherBounds() {
        Weather w = new Weather(-1);
        assertEquals(w.getTypeNum(), 0);
        assertEquals(w.toString(), getStringFromInt(0));
        
        Weather w2 = new Weather(7);
        assertEquals(w2.getTypeNum(), 0);
        assertEquals(w2.toString(), getStringFromInt(0));
        
        w2.setType("cat");
        assertEquals(w2.getTypeNum(), 1);
        assertEquals(w2.toString(), getStringFromInt(1));
        
        Weather w3 = w2.copy();
        assertEquals(w3.getTypeNum(), 1);
        assertEquals(w3.toString(), getStringFromInt(1));
        
    }
    
    public String getStringFromInt(int i) {
        switch(i) {
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
        }
        return "sunny";
    }
    
    @Test
    public void weatherDamageBonusTest() {
        for(int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                weatherDamageTestSpecific(i, j);
            }
        }
    }
    
    
    public void weatherDamageTestSpecific(int weatherType, int monType) {
        Weather w = new Weather(weatherType);
        MonType m = new MonType(monType);
        int bonus = Utility.weatherDamageBonus(m, w);
        int expectedBonus = 0;
        if((weatherType == 6) && (monType == 6)) {
            expectedBonus = 15;
        } else if((weatherType == 6) && (monType == 0)) {
            expectedBonus = 5;
        } else if((weatherType == 6)) {
            expectedBonus = -5;
        } else if(weatherType == monType) {
            expectedBonus = 5;
        }
        assertEquals(expectedBonus, bonus);
    }
}