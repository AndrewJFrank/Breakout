public class Ball
{
    private int x;
    private int y;
    public Ball(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public void setXY(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public int x()
    {
        return x;
    }
    public int y()
    {
        return y;
    }
}