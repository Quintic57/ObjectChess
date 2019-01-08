/**
 * Created by Destin on 1/23/17.
 */
public abstract class Piece
{
    private int row;
    private int col;
    private int team;
    private int rank;

    public Piece(int row, int col, int team, int rank)
    {
        this.row = row;
        this.col = col;
        this.team = team;
        this.rank = rank;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCoordinate(Coordinate temp)
    {
        this.row = temp.getRow();
        this.col = temp.getCol();
    }

    public int getTeam()
    {
        return team;
    }

    public int getRank()
    {
        return rank;
    }

    public abstract boolean validMove(int toRow, int toCol);

    public boolean teamKill(int toRow, int toCol)
    {
        if(Driver.game.getBoard()[toRow][toCol])
            if(Driver.game.pieceAt(new Coordinate(this.getRow(), this.getCol())).getTeam() == Driver.game.pieceAt(new Coordinate(toRow, toCol)).getTeam())
                return true;

        return false;
    }
}
