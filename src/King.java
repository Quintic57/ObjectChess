/**
 * Created by Destin on 1/26/17.
 */
public class King extends Piece
{
    public King(int row, int col, int team)
    {
        super(row, col, team, Chess.KING);
    }

    public boolean validMove(int toRow, int toCol)
    {
        int fromRow = this.getRow();
        int fromCol = this.getCol();

        if((Math.abs(fromRow - toRow) <= 1 && Math.abs(fromCol - toCol) <= 1) && !teamKill(toRow, toCol))
            return true;

        return false;
    }

    public String toString()
    {
        String output = "";

        if(this.getTeam() == 0)
            output = output + "\u001B[31m";
        else if(this.getTeam() == 1)
            output = output + "\u001B[34m";

        return output + "K" + "\u001B[0m";
    }
}

