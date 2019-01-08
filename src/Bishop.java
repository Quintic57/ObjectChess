/**
 * Created by Destin on 1/26/17.
 */
public class Bishop extends Piece
{
    public Bishop(int row, int col, int team)
    {
        super(row, col, team, Chess.BISHOP);
    }

    public boolean validMove(int toRow, int toCol)
    {
        int fromRow = this.getRow();
        int fromCol = this.getCol();

        boolean[][] board = Driver.game.getBoard();

        if((Math.abs(fromRow-toRow) != Math.abs(fromCol-toCol)) || this.teamKill(toRow, toCol))
            return false;

        if(fromRow > toRow){
            if(fromCol > toCol)
                for(int r = fromRow - 1, c = fromCol - 1; r > toRow; r--, c--)
                    if(board[r][c])
                        return false;
            if(fromCol < toCol)
                for(int r = fromRow - 1, c = fromCol + 1; r > toRow; r--, c++)
                    if(board[r][c])
                        return false;
        }

        if(fromRow < toRow){
            if(fromCol > toCol)
                for(int r = fromRow + 1, c = fromCol - 1; r < toRow; r++, c--)
                    if(board[r][c])
                        return false;
            if(fromCol < toCol)
                for(int r = fromRow + 1, c = fromCol + 1; r < toRow; r++, c++)
                    if(board[r][c])
                        return false;
        }

        return true;
    }

    public String toString()
    {
        String output = "";

        if(this.getTeam() == 0)
            output = output + "\u001B[31m";
        else if(this.getTeam() == 1)
            output = output + "\u001B[34m";

        return output + "B" + "\u001B[0m";
    }
}

