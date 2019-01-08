/**
 * Created by Destin on 1/23/17.
 */
import java.util.ArrayList;

public class Chess
{
    public static final int PAWN = 0;
    public static final int ROOKE = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;

    public static final int RED_TEAM = 0;
    public static final int BLUE_TEAM = 1;

    private Piece[] remainingPieces;
    private boolean[][] board;

    public Chess()
    {
        board = new boolean[8][8];
        for(int c = 0; c < board.length; c++){
            board[0][c] = true;
            board[1][c] = true;
            board[6][c] = true;
            board[7][c] = true;
        }

        remainingPieces = new Piece[32];
        for(int i = 0; i < board.length; i++){
            remainingPieces[i] = new Pawn(1,i,0);
            remainingPieces[board.length+i] = new Pawn(6,i,1);
        }
        remainingPieces[16] = new Rooke(0,0,RED_TEAM);
        remainingPieces[17] = new Rooke(0,7,RED_TEAM);
        remainingPieces[18] = new Rooke(7,0,BLUE_TEAM);
        remainingPieces[19] = new Rooke(7,7,BLUE_TEAM);
        remainingPieces[20] = new Knight(0,1,RED_TEAM);
        remainingPieces[21] = new Knight(0,6,RED_TEAM);
        remainingPieces[22] = new Knight(7,1,BLUE_TEAM);
        remainingPieces[23] = new Knight(7,6,BLUE_TEAM);
        remainingPieces[24] = new Bishop(0,2,RED_TEAM);
        remainingPieces[25] = new Bishop(0,5,RED_TEAM);
        remainingPieces[26] = new Bishop(7,2,BLUE_TEAM);
        remainingPieces[27] = new Bishop(7,5,BLUE_TEAM);
        remainingPieces[28] = new Queen(0,3,RED_TEAM);
        remainingPieces[29] = new Queen(7,3,BLUE_TEAM);
        remainingPieces[30] = new King(0,4,RED_TEAM);
        remainingPieces[31] = new King(7,4,BLUE_TEAM);
    }

    public Chess(Piece... pieces)
    {
        remainingPieces = new Piece[32];
        board = new boolean[8][8];

        for(int i = 0; i < pieces.length; i++)
            remainingPieces[i] = pieces[i];

        for(int i = pieces.length; i < remainingPieces.length; i++)
            remainingPieces[i] = new Pawn(-1, -1, 1);

        for(Piece piece: remainingPieces)
            if(piece.getRow() >= 0 && piece.getCol() >= 0)
                board[piece.getRow()][piece.getCol()] = true;
    }

    public boolean checkMate()
    {
        Piece temp;
        if((temp = check()) != null){
            Coordinate king = pieceLocator(
                    Chess.KING, Math.abs(temp.getTeam() - 1));
            //Moving out of Check
            int kingRow = king.getRow();
            int kingCol = king.getCol();
            int KTeam = Math.abs(temp.getTeam()-1);
            int i;
            if (kingRow-1 >= 0 && (!board[kingRow-1][kingCol] || pieceAt(
                    new Coordinate(kingRow-1, kingCol)).getTeam() != KTeam)) {
                i = pieceIndex(new Coordinate(kingRow-1, kingCol));
                movePiece(new Coordinate(kingRow, kingCol),
                        new Coordinate(kingRow-1, kingCol));
                if (check() == null) {
                    movePiece(new Coordinate(kingRow-1, kingCol),
                            new Coordinate(kingRow, kingCol));
                    if(i >= 0) {
                        remainingPieces[i].setCoordinate(
                                new Coordinate(kingRow-1, kingCol));
                        board[kingRow-1][kingCol] = true;
                    }
                    return false;
                }
                movePiece(new Coordinate(kingRow-1, kingCol), new Coordinate(kingRow, kingCol));
                if(i >= 0) {
                    remainingPieces[i].setCoordinate(new Coordinate(kingRow-1, kingCol));
                    board[kingRow-1][kingCol] = true;
                }
            }
            if(kingRow-1 >= 0 && kingCol+1 < board.length && (!board[kingRow-1][kingCol+1] || pieceAt(new Coordinate(kingRow-1, kingCol+1)).getTeam() != KTeam)){
                i = pieceIndex(new Coordinate(kingRow-1, kingCol+1));
                movePiece(new Coordinate(kingRow, kingCol), new Coordinate(kingRow-1, kingCol+1));
                if(check() == null){
                    movePiece(new Coordinate(kingRow-1, kingCol+1), new Coordinate(kingRow, kingCol));
                    if(i >= 0) {
                        remainingPieces[i].setCoordinate(new Coordinate(kingRow-1, kingCol+1));
                        board[kingRow-1][kingCol+1] = true;
                    }
                    return false;
                }
                movePiece(new Coordinate(kingRow-1, kingCol+1), new Coordinate(kingRow, kingCol));
                if(i >= 0) {
                    remainingPieces[i].setCoordinate(new Coordinate(kingRow-1, kingCol+1));
                    board[kingRow-1][kingCol+1] = true;
                }
            }
            if(kingCol+1 < board.length && (!board[kingRow][kingCol+1] || pieceAt(new Coordinate(kingRow, kingCol+1)).getTeam() != KTeam)) {
                i = pieceIndex(new Coordinate(kingRow, kingCol+1));
                movePiece(new Coordinate(kingRow, kingCol), new Coordinate(kingRow, kingCol+1));
                if (check() == null) {
                    movePiece(new Coordinate(kingRow, kingCol+1), new Coordinate(kingRow, kingCol));
                    if(i >= 0) {
                        remainingPieces[i].setCoordinate(new Coordinate(kingRow, kingCol+1));
                        board[kingRow][kingCol+1] = true;
                    }
                    return false;
                }
                movePiece(new Coordinate(kingRow, kingCol+1), new Coordinate(kingRow, kingCol));
                if(i >= 0) {
                    remainingPieces[i].setCoordinate(new Coordinate(kingRow, kingCol+1));
                    board[kingRow][kingCol+1] = true;
                }
            }
            if(kingRow+1 < board.length && kingCol+1 < board.length && (!board[kingRow+1][kingCol+1] || pieceAt(new Coordinate(kingRow+1, kingCol+1)).getTeam() != KTeam)){
                i = pieceIndex(new Coordinate(kingRow+1, kingCol+1));
                movePiece(new Coordinate(kingRow, kingCol), new Coordinate(kingRow+1, kingCol+1));
                if(check() == null){
                    movePiece(new Coordinate(kingRow+1, kingCol+1), new Coordinate(kingRow, kingCol));
                    if(i >= 0) {
                        remainingPieces[i].setCoordinate(new Coordinate(kingRow+1, kingCol+1));
                        board[kingRow+1][kingCol+1] = true;
                    }
                    return false;
                }
                movePiece(new Coordinate(kingRow+1, kingCol+1), new Coordinate(kingRow, kingCol));
                if(i >= 0) {
                    remainingPieces[i].setCoordinate(new Coordinate(kingRow+1, kingCol+1));
                    board[kingRow+1][kingCol+1] = true;
                }
            }
            if(kingRow+1 < board.length && (!board[kingRow+1][kingCol] || pieceAt(new Coordinate(kingRow+1, kingCol)).getTeam() != KTeam)) {
                i = pieceIndex(new Coordinate(kingRow+1, kingCol));
                movePiece(new Coordinate(kingRow, kingCol), new Coordinate(kingRow+1, kingCol));
                if (check() == null) {
                    movePiece(new Coordinate(kingRow+1, kingCol), new Coordinate(kingRow, kingCol));
                    if(i >= 0) {
                        remainingPieces[i].setCoordinate(new Coordinate(kingRow+1, kingCol));
                        board[kingRow+1][kingCol] = true;
                    }
                    return false;
                }
                movePiece(new Coordinate(kingRow+1, kingCol), new Coordinate(kingRow, kingCol));
                if(i >= 0) {
                    remainingPieces[i].setCoordinate(new Coordinate(kingRow+1, kingCol));
                    board[kingRow+1][kingCol] = true;
                }
            }
            if(kingRow+1 < board.length && kingCol-1 >= 0 && (!board[kingRow+1][kingCol-1] || pieceAt(new Coordinate(kingRow+1, kingCol-1)).getTeam() != KTeam)){
                i = pieceIndex(new Coordinate(kingRow+1, kingCol-1));
                movePiece(new Coordinate(kingRow, kingCol), new Coordinate(kingRow+1, kingCol-1));
                if(check() == null){
                    movePiece(new Coordinate(kingRow+1, kingCol-1), new Coordinate(kingRow, kingCol));
                    if(i >= 0) {
                        remainingPieces[i].setCoordinate(new Coordinate(kingRow+1, kingCol-1));
                        board[kingRow+1][kingCol-1] = true;
                    }
                    return false;
                }
                movePiece(new Coordinate(kingRow+1, kingCol-1), new Coordinate(kingRow, kingCol));
                if(i >= 0) {
                    remainingPieces[i].setCoordinate(new Coordinate(kingRow+1, kingCol-1));
                    board[kingRow+1][kingCol-1] = true;
                }
            }
            if(kingCol-1 >= 0 && (!board[kingRow][kingCol-1] || pieceAt(new Coordinate(kingRow, kingCol-1)).getTeam() != KTeam)) {
                i = pieceIndex(new Coordinate(kingRow, kingCol-1));
                movePiece(new Coordinate(kingRow, kingCol), new Coordinate(kingRow, kingCol-1));
                if (check() == null) {
                    movePiece(new Coordinate(kingRow, kingCol-1), new Coordinate(kingRow, kingCol));
                    if(i >= 0) {
                        remainingPieces[i].setCoordinate(new Coordinate(kingRow, kingCol-1));
                        board[kingRow][kingCol-1] = true;
                    }
                    return false;
                }
                movePiece(new Coordinate(kingRow, kingCol-1), new Coordinate(kingRow, kingCol));
                if(i >= 0) {
                    remainingPieces[i].setCoordinate(new Coordinate(kingRow, kingCol-1));
                    board[kingRow][kingCol-1] = true;
                }
            }
            if(kingRow-1 >= 0 && kingCol-1 >= 0 && (!board[kingRow-1][kingCol-1] || pieceAt(new Coordinate(kingRow-1, kingCol-1)).getTeam() != KTeam)){
                i = pieceIndex(new Coordinate(kingRow-1, kingCol-1));
                movePiece(new Coordinate(kingRow, kingCol), new Coordinate(kingRow-1, kingCol-1));
                if(check() == null) {
                    movePiece(new Coordinate(kingRow-1, kingCol-1), new Coordinate(kingRow, kingCol));
                    if (i >= 0) {
                        remainingPieces[i].setCoordinate(new Coordinate(kingRow-1, kingCol-1));
                        board[kingRow-1][kingCol-1] = true;
                    }
                    return false;
                }
                movePiece(new Coordinate(kingRow-1, kingCol-1), new Coordinate(kingRow, kingCol));
                if(i >= 0) {
                    remainingPieces[i].setCoordinate(new Coordinate(kingRow-1, kingCol-1));
                    board[kingRow-1][kingCol-1] = true;
                }
            }

            //Blocking the attacking piece
            ArrayList<Coordinate> blocks = new ArrayList();
            int pieceRow = temp.getRow();
            int pieceCol = temp.getCol();
            //Vertical
            if(pieceRow == kingRow){
                if(pieceCol > kingCol)
                    for(int c = kingCol+1; c < pieceCol; c++)
                        blocks.add(new Coordinate(kingRow, c));
                else if(pieceCol < kingCol)
                    for(int c = kingCol-1; c > pieceCol; c--)
                        blocks.add(new Coordinate(kingRow, c));
            }
            else if(pieceCol == kingCol){
                if(pieceRow > kingRow)
                    for(int r = kingRow+1; r < pieceRow; r++)
                        blocks.add(new Coordinate(r, kingCol));
                else if(pieceRow < kingRow)
                    for(int r = kingRow-1; r > pieceRow; r--)
                        blocks.add(new Coordinate(r, kingCol));
            }
            //Diagonal
            if(Math.abs(pieceRow-kingRow) == Math.abs(pieceCol-kingCol)){
                if((pieceRow < kingRow) && (pieceCol < kingCol))
                    for(int r = kingRow-1, c = kingCol-1; r > pieceRow; r--, c--)
                        blocks.add(new Coordinate(r, c));
                else if((pieceRow < kingRow) && (pieceCol > kingCol))
                    for(int r = kingRow-1, c = kingCol+1; r > pieceRow; r--, c++)
                        blocks.add(new Coordinate(r, c));
                else if((pieceRow > kingRow) && (pieceCol > kingCol))
                    for(int r = kingRow+1, c = kingCol+1; r < pieceRow; r++, c++)
                        blocks.add(new Coordinate(r, c));
                else if((pieceRow > kingRow) && (pieceCol < kingCol))
                    for(int r = kingRow+1, c = kingCol-1; r < pieceRow; r++, c--)
                        blocks.add(new Coordinate(r, c));
            }
            blocks.add(new Coordinate(pieceRow, pieceCol));

            for(i = 0; i < remainingPieces.length; i++) {
                if (remainingPieces[i].getTeam() == KTeam)
                    for (Coordinate block : blocks)
                        if (remainingPieces[i].getRank() != Chess.KING && remainingPieces[i].validMove(block.getRow(), block.getCol()))
                            return false;
            }

            return true;
        }

        return false;
    }

    public Piece check()
    {
        Coordinate redKing = pieceLocator(Chess.KING, Chess.RED_TEAM);
        Coordinate blueKing = pieceLocator(Chess.KING, Chess.BLUE_TEAM);

        if(redKing.getRow() >= 0 && redKing.getCol() >= 0) {
            Coordinate temp;
            if ((temp = verticalCheck(redKing.getRow(), redKing.getCol(), Chess.RED_TEAM)) != null)
                return pieceAt(temp);
            else if ((temp = diagonalCheck(redKing.getRow(), redKing.getCol(), Chess.RED_TEAM)) != null)
                return pieceAt(temp);
            else if ((temp = LCheck(redKing.getRow(), redKing.getCol(), Chess.RED_TEAM)) != null)
                return pieceAt(temp);
        }

        if(blueKing.getRow() >= 0 && blueKing.getCol() >= 0) {
            Coordinate temp;
            if ((temp = verticalCheck(blueKing.getRow(), blueKing.getCol(), Chess.BLUE_TEAM)) != null)
                return pieceAt(temp);
            else if ((temp = diagonalCheck(blueKing.getRow(), blueKing.getCol(), Chess.BLUE_TEAM)) != null)
                return pieceAt(temp);
            else if ((temp = LCheck(blueKing.getRow(), blueKing.getCol(), Chess.BLUE_TEAM)) != null)
                return pieceAt(temp);
        }

        return null;
    }

    public Coordinate verticalCheck(int row, int col, int KTeam)
    {
        //N
        for(int r = row - 1; r >= 0; r--)
            if(board[r][col]) {
                Piece temp = pieceAt(new Coordinate(r, col));
                if((temp.getTeam() != KTeam) && (temp.getRank() == Chess.ROOKE || temp.getRank() == Chess.QUEEN))
                    return new Coordinate(r, col);

                break;
            }

        //S
        for(int r = row + 1; r < board.length; r++)
            if(board[r][col]) {
                Piece temp = pieceAt(new Coordinate(r, col));
                if((temp.getTeam() != KTeam) && (temp.getRank() == Chess.ROOKE || temp.getRank() == Chess.QUEEN))
                    return new Coordinate(r, col);

                break;
            }

        //W
        for(int c = col - 1; c >= 0; c--)
            if(board[row][c]) {
                Piece temp = pieceAt(new Coordinate(row, c));
                if((temp.getTeam() != KTeam) && (temp.getRank() == Chess.ROOKE || temp.getRank() == Chess.QUEEN))
                    return new Coordinate(row, c);

                break;
            }

        //E
        for(int c = col + 1; c < board.length; c++)
            if(board[row][c]) {
                Piece temp = pieceAt(new Coordinate(row, c));
                if((temp.getTeam() != KTeam) && (temp.getRank() == Chess.ROOKE || temp.getRank() == Chess.QUEEN))
                    return new Coordinate(row, c);

                break;
            }

        return null;
    }

    public Coordinate diagonalCheck(int row, int col, int KTeam)
    {
        //NW
        for(int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--,c--){
            if(board[r][c]) {
                Piece temp = pieceAt(new Coordinate(r,c));
                if((temp.getTeam() != KTeam) && (temp.getRank() == Chess.BISHOP || temp.getRank() == Chess.QUEEN))
                    return new Coordinate(r, c);
                else if((temp.getTeam() != KTeam) && (temp.getTeam() == RED_TEAM) && (temp.getRank() == Chess.PAWN) && (col - c == 1))
                    return new Coordinate(r, c);

                break;
            }
        }

        //NE
        for(int r = row - 1, c = col + 1; r >= 0 && c < board.length; r--,c++){
            if(board[r][c]) {
                Piece temp = pieceAt(new Coordinate(r,c));
                if((temp.getTeam() != KTeam) && (temp.getRank() == Chess.BISHOP || temp.getRank() == Chess.QUEEN))
                    return new Coordinate(r, c);
                else if((temp.getTeam() != KTeam) && (temp.getTeam() == RED_TEAM) && (temp.getRank() == Chess.PAWN) && (col - c == -1))
                    return new Coordinate(r, c);

                break;
            }
        }

        //SW
        for(int r = row + 1,c = col - 1; r < board.length && c >= 0; r++,c--)
        {
            if(board[r][c]) {
                Piece temp = pieceAt(new Coordinate(r,c));
                if((temp.getTeam() != KTeam) && (temp.getRank() == Chess.BISHOP || temp.getRank() == Chess.QUEEN))
                    return new Coordinate(r, c);
                else if((temp.getTeam() != KTeam) && (temp.getTeam() == BLUE_TEAM) && (temp.getRank() == Chess.PAWN) && (col - c == 1))
                    return new Coordinate(r, c);

                break;
            }
        }

        //SE
        for(int r = row + 1,c = col + 1; r < board.length && c < board.length; r++, c++)
        {
            if(board[r][c]) {
                Piece temp = pieceAt(new Coordinate(r,c));
                if((temp.getTeam() != KTeam) && (temp.getRank() == Chess.BISHOP || temp.getRank() == Chess.QUEEN))
                    return new Coordinate(r, c);
                else if((temp.getTeam() != KTeam) && (temp.getTeam() == BLUE_TEAM) && (temp.getRank() == Chess.PAWN) && (col - c == -1))
                    return new Coordinate(r, c);

                break;
            }
        }

        return null;
    }

    public Coordinate LCheck(int row, int col, int KTeam)
    {
        if(((row-2 >= 0 && col-1 >= 0) && board[row-2][col-1] && pieceAt(new Coordinate(row-2, col-1)).getRank() == Chess.KNIGHT && pieceAt(new Coordinate(row-2, col-1)).getTeam() != KTeam))
            return new Coordinate(row-2, col-1);

        else if(((row-1 >= 0 && col-2 >= 0) && board[row-1][col-2] && pieceAt(new Coordinate(row-1, col-2)).getRank() == Chess.KNIGHT && pieceAt(new Coordinate(row-1, col-2)).getTeam() != KTeam))
            return new Coordinate(row-1, col-2);

        else if(((row-2 >= 0 && col+1 < board.length) && board[row-2][col+1] && pieceAt(new Coordinate(row-2, col+1)).getRank() == Chess.KNIGHT && pieceAt(new Coordinate(row-2, col+1)).getTeam() != KTeam))
            return new Coordinate(row-2, col+1);

        else if(((row-1 >= 0 && col+2 < board.length) && board[row-1][col+2] && pieceAt(new Coordinate(row-1, col+2)).getRank() == Chess.KNIGHT && pieceAt(new Coordinate(row-1, col+2)).getTeam() != KTeam))
            return new Coordinate(row-1, col+2);

        else if(((row+2 < board.length && col-1 >= 0) && board[row+2][col-1] && pieceAt(new Coordinate(row+2, col-1)).getRank() == Chess.KNIGHT && pieceAt(new Coordinate(row+2, col-1)).getTeam() != KTeam))
            return new Coordinate(row+2, col-1);

        else if(((row+1 < board.length && col-2 >= 0) && board[row+1][col-2] && pieceAt(new Coordinate(row+1, col-2)).getRank() == Chess.KNIGHT && pieceAt(new Coordinate(row+1, col-2)).getTeam() != KTeam))
            return new Coordinate(row+1, col-2);

        else if(((row+2 < board.length && col+1 < board.length) && board[row+2][col+1] && pieceAt(new Coordinate(row+2, col+1)).getRank() == Chess.KNIGHT && pieceAt(new Coordinate(row+2, col+1)).getTeam() != KTeam))
            return new Coordinate(row+2, col+1);

        else if(((row+1 < board.length && col+2 < board.length) && board[row+1][col+2] && pieceAt(new Coordinate(row+1, col+2)).getRank() == Chess.KNIGHT && pieceAt(new Coordinate(row+1, col+2)).getTeam() != KTeam))
            return new Coordinate(row+1, col+2);

        return null;
    }

    public int kingCheck(Coordinate from, Coordinate to)
    {
        Piece temp;
        int i = pieceIndex(to);
        movePiece(from, to);

        if((temp = check()) != null) {
            movePiece(to, from);
            if(i >= 0) {
                remainingPieces[i].setCoordinate(to);
                board[to.getRow()][to.getCol()] = true;
            }

            return Math.abs(temp.getTeam() - 1);
        }
        else {
            movePiece(to, from);
            if (i >= 0) {
                remainingPieces[i].setCoordinate(to);
                board[to.getRow()][to.getCol()] = true;
            }

            return -1;
        }
    }

    public Piece pieceAt(Coordinate coordinate)
    {
        for(int i = 0; i < remainingPieces.length; i++)
            if (remainingPieces[i].getRow() == coordinate.getRow() && remainingPieces[i].getCol() == coordinate.getCol())
                return remainingPieces[i];

        return null;
    }

    public Coordinate pieceLocator(int rank, int team)
    {
        for(int i = 0; i < remainingPieces.length; i++)
            if(remainingPieces[i].getRank() == rank && remainingPieces[i].getTeam() == team)
                return new Coordinate(remainingPieces[i].getRow(), remainingPieces[i].getCol());

        return null;
    }

    public int pieceIndex(Coordinate coordinate)
    {
        for(int i = 0; i < remainingPieces.length; i++)
            if(remainingPieces[i].getRow() == coordinate.getRow() && remainingPieces[i].getCol() == coordinate.getCol())
                return i;

        return -1;
    }

    public void movePiece(Coordinate from, Coordinate to)
    {
        if(board[to.getRow()][to.getCol()])
            pieceAt(to).setCoordinate(new Coordinate(-1, -1));
        pieceAt(from).setCoordinate(to);

        board[from.getRow()][from.getCol()] = false;
        board[to.getRow()][to.getCol()] = true;

    }

    public String toString()
    {
        String output = "";
        String reference = "ABCDEFGH";

        output = "    1   2   3   4   5   6   7   8   " + "\n"
                + "  ---------------------------------" + "\n";

        for(int r = 0; r < board.length; r++) {
            output = output + reference.charAt(r) + " ";
            output = output + "| ";
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c])
                    output = output + pieceAt(new Coordinate(r, c));
                else if (!board[r][c])
                    output = output + " ";
                output = output + " | ";
            }
            output = output + "\n"
                    + "  ---------------------------------" +"\n";
        }

        return output;
    }

    public Piece[] getRemainingPieces()
    {
        return remainingPieces;
    }

    public boolean[][] getBoard()
    {
        return board;
    }
}
