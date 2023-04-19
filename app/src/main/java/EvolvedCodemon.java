/**
 * I might include dual-type functionality, but we'll see if I have time for that.
 * @author DJ
 *
 */
public class EvolvedCodemon extends Codemon {
    private Codemon mon;
    
    protected int hpBoost;
    protected int atkBoost;
    protected int defBoost;
    protected int spdBoost;
    
    @Override 
    public EvolvedCodemon(Codemon basemon) {
        this.mon = basemon;
        this.setHpBoost(20);
        this.setAtkBoost(10);
        this.setDefBoost(10);
        this.setSpdBoost(3);
    }
    
    public EvolvedCodemon(Codemon basemon, int[] statBoosts) {
        this.mon = basemon;
        this.setHpBoost(statBoosts[0]);
        this.setAtkBoost(statBoosts[1]);
        this.setDefBoost(statBoosts[2]);
        this.setSpdBoost(statBoosts[3]);
    }
    
    @Override
    public int getHp() {
        // TODO Auto-generated method stub
        return super.getHp();
    }

    @Override
    public int getAtk() {
        // TODO Auto-generated method stub
        return super.getAtk() + atkBoost;
    }

    @Override
    public int getDef() {
        // TODO Auto-generated method stub
        return super.getDef() + defBoost;
    }

    @Override
    public int getSpd() {
        // TODO Auto-generated method stub
        return super.getSpd() + spdBoost;
    }

    public int getHpBoost() {
        return hpBoost;
    }

    public void setHpBoost(int hpBoost) {
        this.hpBoost = hpBoost;
    }

    public int getAtkBoost() {
        return atkBoost;
    }

    public void setAtkBoost(int atkBoost) {
        this.atkBoost = atkBoost;
    }

    public int getDefBoost() {
        return defBoost;
    }

    public void setDefBoost(int defBoost) {
        this.defBoost = defBoost;
    }

    public int getSpdBoost() {
        return spdBoost;
    }

    public void setSpdBoost(int spdBoost) {
        this.spdBoost = spdBoost;
    }

}
