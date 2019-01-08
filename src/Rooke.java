/**
 * Created by Destin on 1/26/17.
 */
public class Rooke extends Piece
{
    public Rooke(int row, int col, int team)
    {
        super(row, col, team, Chess.ROOKE);
    }

    public boolean validMove(int toRow, int toCol)
    {
        int fromRow = this.getRow();
        int fromCol = this.getCol();

        boolean[][] board = Driver.game.getBoard();

        if(((fromRow != toRow) && (fromCol != toCol)) || this.teamKill(toRow, toCol))
            return false;

        if(fromRow > toRow)
            for(int r = fromRow - 1; r > toRow; r--)
                if(board[r][fromCol])
                    return false;

        if(fromRow < toRow)
            for(int r = fromRow + 1; r < toRow; r++)
                if(board[r][fromCol])
                    return false;

        if(fromCol > toCol)
            for(int c = fromCol -1; c < toCol; c--)
                if(board[fromRow][c])
                    return false;

        if(fromCol < toCol)
            for(int c = fromCol +1; c < toCol; c++)
                if(board[fromRow][c])
                    return false;

        return true;
    }

    public String toString()
    {
        String output = "";

        if(this.getTeam() == 0)
            output = output + "\u001B[31m";
        else if(this.getTeam() == 1)
            output = output + "\u001B[34m";

        return output + "R" + "\u001B[0m";
    }
}

