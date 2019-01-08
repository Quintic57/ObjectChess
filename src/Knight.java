/**
 * Created by Destin on 1/26/17.
 */
public class Knight extends Piece
{
    public Knight(int row, int col, int team)
    {
        super(row, col, team, Chess.KNIGHT);

    }

    public boolean validMove(int toRow, int toCol)
    {
        int fromRow = this.getRow();
        int fromCol = this.getCol();

        if(!this.teamKill(toRow,toCol)){
            if(Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 1)
                return true;
            else if(Math.abs(fromRow - toRow) == 1 && Math.abs(fromCol - toCol) == 2)
                return true;
        }

        return false;
    }

    public String toString()
    {
        String output = "";

        if(this.getTeam() == 0)
            output = output + "\u001B[31m";
        else if(this.getTeam() == 1)
            output = output + "\u001B[34m";

        return output + "N" + "\u001B[0m";
    }
}

