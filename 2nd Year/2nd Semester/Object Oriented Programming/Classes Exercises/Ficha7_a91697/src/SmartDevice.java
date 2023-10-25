public class SmartDevice {

    private String ID;
    private boolean on;

    public SmartDevice()
    {
        this("Device#" + Math.random());
    }

    public SmartDevice(SmartDevice s)
    {
        this(s.ID, s.on);
    }

    public SmartDevice(String ID)
    {
        this(ID, false);
    }

    public SmartDevice(String ID, boolean on)
    {
        this.ID = ID;
        this.on = on;
    }


    public String getID()
    {
        return ID;
    }

    public boolean getOn()
    {
        return this.on;
    }

    public void setOn(boolean newState)
    {
        this.on = newState;
    }

    public SmartDevice clone()
    {
        return new SmartDevice(this);
    }

    public boolean equals(Object s)
    {
        if (s == this)
            return true;

        if (s == null )
            return false;
        SmartDevice sd = (SmartDevice) s;
        return this.ID == sd.ID &&
                this.getOn() == sd.getOn();

    }

}
