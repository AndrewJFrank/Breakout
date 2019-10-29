public class Platform
{
    private int x;
    private int y;
    private String pos;
    public Platform(int x, int y, String pos)
    {
        this.x = x;
        this.y = y;
        this.pos = pos;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public void setPos(String pos)
    {
        this.pos = pos;
    }
    public int x()
    {
        return x;
    }
    public int y()
    {
        return y;
    }
    public String pos()
    {
        return pos;
    }
}