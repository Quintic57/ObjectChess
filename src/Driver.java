/**
 * Created by Destin on 2/1/17.
 */
import java.util.Scanner;
import java.util.Arrays;

public class Driver
{
//    public static Chess game = new Chess(new Rooke(0,0,Chess.RED_TEAM), new Bishop(0, 2, Chess.RED_TEAM), new King(0, 4, Chess.RED_TEAM), new Bishop(0,5,Chess.RED_TEAM), new Knight(0,6,Chess.RED_TEAM), new Rooke(0,7,Chess.RED_TEAM), new Pawn(1,0,Chess.RED_TEAM), new Knight(1,3,Chess.RED_TEAM), new Pawn(1,4,Chess.RED_TEAM), new Pawn(1,5,Chess.RED_TEAM), new Pawn(1,6,Chess.RED_TEAM), new Bishop(2, 2, Chess.BLUE_TEAM), new Queen(3, 3, Chess.BLUE_TEAM), new Pawn(3,7,Chess.RED_TEAM), new Pawn(6,0,Chess.BLUE_TEAM), new Pawn(6,1,Chess.BLUE_TEAM), new Pawn(6,5,Chess.BLUE_TEAM), new Pawn(6,6,Chess.BLUE_TEAM), new Pawn(6,7,Chess.BLUE_TEAM), new Rooke(7,0,Chess.BLUE_TEAM), new Knight(7,1,Chess.BLUE_TEAM), new Bishop(7,2,Chess.BLUE_TEAM), new King(7,4,Chess.BLUE_TEAM), new Knight(7,6,Chess.BLUE_TEAM), new Rooke(7,7,Chess.BLUE_TEAM));
    public static Chess game = new Chess();

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        String reference = "ABCDEFGH";
        String currentPlayer = "";
        int turn = 0;

        System.out.println(game);

        for(; !game.checkMate(); turn++) {
            String[] coordinates;
            String[] fromCoordinate;
            String[] toCoordinate;
            Coordinate from = null;
            Coordinate to = null;

            if (turn % 2 == 0)
                currentPlayer = "\u001B[31m" + "RED" + "\u001B[0m";
            else
                currentPlayer = "\u001B[34m" + "BLUE" + "\u001B[0m";
            System.out.println(currentPlayer + " Player's Turn" + "(fromCoordinate,toCoordinate)");

            String moveInput = input.nextLine();
            if(moveInput.contains(",") && moveInput.split(",").length >= 2) {
                coordinates = moveInput.split(",");
                fromCoordinate = coordinates[0].split("");
                toCoordinate = coordinates[1].split("");
                from = new Coordinate(reference.indexOf(fromCoordinate[0]), Integer.parseInt(fromCoordinate[1]) - 1);
                to = new Coordinate(reference.indexOf(toCoordinate[0]), Integer.parseInt(toCoordinate[1]) - 1);
            }

            while (!moveInput.contains(",")
                    ||game.pieceAt(from) == null
                    || (currentPlayer.contains("RED") && game.pieceAt(from).getTeam() == Chess.BLUE_TEAM) || (currentPlayer.contains("BLUE") && game.pieceAt(from).getTeam() == Chess.RED_TEAM)
                    || !game.pieceAt(from).validMove(to.getRow(), to.getCol())
                    || game.kingCheck(from, to) == game.pieceAt(from).getTeam()) {
                System.out.println("Invalid Move");

                moveInput = input.nextLine();
                if(moveInput.contains(",") && moveInput.split(",").length >= 2) {
                    coordinates = moveInput.split(",");
                    fromCoordinate = coordinates[0].split("");
                    toCoordinate = coordinates[1].split("");
                    from = new Coordinate(reference.indexOf(fromCoordinate[0]), Integer.parseInt(fromCoordinate[1]) - 1);
                    to = new Coordinate(reference.indexOf(toCoordinate[0]), Integer.parseInt(toCoordinate[1]) - 1);
                }
            }

            game.movePiece(from, to);
            System.out.println(game);

            if(game.check() != null)
                System.out.println("Check!");
        }

        System.out.println("CheckMate! " + currentPlayer + " player " + "wins.");
    }
}
