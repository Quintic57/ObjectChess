/**
 * Created by Destin on 4/4/17.
 */
public class Coordinate
{
    private int row;
    private int col;

    public Coordinate(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public String toString()
    {
        return row + "," + col;
    }
}
