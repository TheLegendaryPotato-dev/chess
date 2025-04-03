//Name: Obiefuna
//Piece name: Sweeper
//Description: The piece can move in a cone shape up to three squares in front and behind it.
import java.awt.Graphics;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Cone extends Piece {
 

    public Cone(boolean isWhite, String img_file) {
        super(isWhite, img_file);
        
    }
    @Override
    public String toString() {
        return "A" + super.toString() + "cone";
    }
   

    // TO BE IMPLEMENTED!
    // return a list of every square that is "controlled" by this piece. A square is controlled
    // if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        // Precondition: board is a valid 8x8 Square array, and start is a valid Square within the board.
        // Postcondition: Returns an ArrayList of Square objects that are controlled by the piece.
        ArrayList<Square> moves = new ArrayList<>();
        int[] rows = {-1, -2, -3, -2, -2, -3, -3, -3, -3, -3, 1, 2, 3, 2, 2, 3, 3, 3, 3, 3};
        int[] columns = {0, 0, 0, -1, 1, 0, -1, 1, 2, -2, 0, 0, 0, -1, 1, 0, -1, 1, 2, -2};
        for (int k = 0; k < rows.length; k++) {
            int j = columns[k];
            int i = rows[k];
            if (start.getRow() + i >= 0 && start.getRow() + i < 8 && start.getCol() + j >= 0 && start.getCol() + j < 8) {
                moves.add(board[start.getRow() + i][start.getCol() + j]);
            }
        }
        return moves;
    }

    // TO BE IMPLEMENTED!
    // implement the move function here
    // it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    // returns an arraylist of squares which are legal to move to
    // please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    // going to score any points.
     
    // Precondition: b is a valid Board object, and start is a valid Square object within the board.
        // Postcondition: Returns an ArrayList of Square objects that are legal moves for the piece.public ArrayList<Square> getLegalMoves(Board b, Square start) {
            public ArrayList<Square> getLegalMoves(Board b, Square start) { 
        ArrayList<Square> moves = new ArrayList<>();

        int[] rows = {-1, -2, -3, -2, -2, -3, -3, -3, -3, -3, 1, 2, 3, 2, 2, 3, 3, 3, 3, 3};
        int[] columns = {0, 0, 0, -1, 1, 0, -1, 1, 2, -2, 0, 0, 0, -1, 1, 0, -1, 1, 2, -2};
        for (int k = 0; k < rows.length; k++) {
            int j = columns[k];
            int i = rows[k];
            if (start.getRow() + i >= 0 && start.getRow() + i < 8 && start.getCol() + j >= 0 && start.getCol() + j < 8 && (!b.getSquareArray()[start.getRow() + i][start.getCol() + j].isOccupied() || b.getSquareArray()[start.getRow() + i][start.getCol() + j].getOccupyingPiece().getColor() != color)) {
                moves.add(b.getSquareArray()[start.getRow() + i][start.getCol() + j]);
            }
        }
        return moves;
    }
}