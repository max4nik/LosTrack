public class Tag {
    public enum VISIBILITY{
        VISIBLE, INVISIBLE;
    }
    private String address;
    private String name;
    private double RSS;

    public void setVisibility(VISIBILITY visibility) {
        this.visibility = visibility;
    }

    private VISIBILITY visibility;


    public double getDistanceMeter(){
        return Math.pow(RSS, 10);
    }
    //Does not work correctly

    boolean isVisible(){
        return this.visibility == VISIBILITY.VISIBLE;
    }
    public Tag(String address, String name, VISIBILITY visibility) {
        this.visibility = visibility;
        this.address = address;
        this.name = name;
    }

    public String getAddress(){
        return this.address;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String Name){
        this.name = Name;
    }

    public double getRSS() {
        return RSS;
    }

    public void setRSS(double RSS) {
        this.RSS = RSS;
    }
}

//you cannot set an address, it changes once per item


