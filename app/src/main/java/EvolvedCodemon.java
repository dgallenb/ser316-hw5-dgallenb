
public class EvolvedCodemon extends Codemon {
    private Codemon mon;
    
    protected int hpBoost;
    protected int atkBoost;
    protected int defBoost;
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

    protected int spdBoost;
    
    public EvolvedCodemon(Codemon m) {
        this.mon = m;
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
