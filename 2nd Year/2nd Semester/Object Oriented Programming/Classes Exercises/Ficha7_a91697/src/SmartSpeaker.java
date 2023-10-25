public class SmartSpeaker extends SmartDevice {

    public static final int MAX = 20;
    private String ch;
    private int vol;

    public SmartSpeaker()
    {
        super();
        this.ch = "";
        this.vol = 0;
    }


    public SmartSpeaker(String ID, String ch, int vol)
    {
        super(ID);
        this.setChannel(ch);
        this.setVolume(vol);
    }

    public SmartSpeaker(String ID)
    {
        this(ID, "", 25);
    }

    public int getVolume()
    {
        return this.vol;
    }

    public String getChannel()
    {
        return ch;
    }

    public void setVolume(int vol)
    {
        if (vol < MAX+1)
            this.vol = vol;
        if (vol < 0)
            this.vol = 0;
    }

    public void setChannel(String ch)
    {
        this.ch = ch;
    }

    public void volumeUp()
    {
        if (this.vol + 1 < MAX+1)
            this.vol++;
    }

    public void volumeDown()
    {
        if (this.vol > 0)
            this.vol--;
    }


}
