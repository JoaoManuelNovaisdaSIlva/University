public class SmartBulb extends SmartDevice {

    public static final int WARM = 2;
    public static final int COLD = 0;
    public static final int NEUTRAL = 1;

    private int tone;

    public SmartBulb()
    {
        super();
    }

    public SmartBulb(String ID)
    {
        this(ID, 1);
    }

    public SmartBulb(String ID, int state)
    {
        super(ID);
        this.tone = state;
    }

    public int getTone()
    {
        return this.tone;
    }

    public void setTone(int newTone)
    {
        if (newTone < COLD) newTone = COLD;
        if (newTone > WARM) newTone = WARM;
        this.tone = newTone;
    }
}
