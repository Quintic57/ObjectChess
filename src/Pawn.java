/**
 * Created by Destin on 1/26/17.
 */
public class Pawn extends Piece
{
    public Pawn(int row, int col, int team)
    {
        super(row, col, team, Chess.PAWN);
    }

    public boolean validMove(int toRow, int toCol)
    {
        int fromRow = this.getRow();
        int fromCol = this.getCol();

        boolean[][] board = Driver.game.getBoard();
        if(Math.abs(fromRow-toRow) > 2 || Math.abs(fromCol-toCol) > 1 || this.teamKill(toRow, toCol))
            return false;

        if(this.getTeam() == Chess.RED_TEAM){
            if(board[toRow][toCol] && (fromRow-toRow) == -1 && Math.abs(fromCol-toCol) == 1)
                return true;
            else if(!board[toRow][toCol] && (fromRow-toRow) == -1 && (fromCol-toCol) == 0)
                return true;
            else if(!board[toRow][toCol] && (fromRow-toRow) == -2 && (fromCol-toCol) == 0 && fromRow == 1)
                return true;
        }
        else if(this.getTeam() == Chess.BLUE_TEAM){
            if(board[toRow][toCol] && (fromRow-toRow) == 1 && Math.abs(fromCol-toCol) == 1)
                return true;
            else if(!board[toRow][toCol] && (fromRow-toRow) == 1 && (fromCol-toCol) == 0)
                return true;
            else if(!board[toRow][toCol] && (fromRow-toRow) == 2 && (fromCol-toCol) == 0 && fromRow == 6)
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

        return output + "P" + "\u001B[0m";
    }
}
