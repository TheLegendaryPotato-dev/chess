// Yuna Cho
//March 26, 2025
//Function: the piece captures forwards in the direction that a pawn would normally move, capturing to diagnals in the way that a pawn would normally move forward
//Preconditions:Z requires piece.java to exist and completion of other steps
//also requires for black to be at the bottom or then requries change
//Postcondition: you get an antipawn! It will move forward 1 and capture diagonally
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
public class AntiPawn extends Piece {

    public AntiPawn(boolean color, String img_file) {
       super(color, img_file);
       
    }
    
    public String toString() {
      if (color ==true)
        return "a white" + " anti-pawn, though I did once consider making it a reverse pawn instead";
      else
        return "a black" + " anti-pawn- it aspires to become a knight or some other honorable piece, " +
        "though this hope shall never come to fruition because of their lazy creator, though most" + 
        " players choose to make the bravest into concubines anyways...";
    }

    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
      ArrayList<Square> moves = new ArrayList();
      if (color != true){
      if(start.getRow()>0){
        //your right: pawn moves one down and could captures an opposing piece 
        moves.add(board[start.getRow()-1][start.getCol()]);
      }
    }
    if (color != true){
      if(start.getRow()> 0){
          //your right: pawn moves one down and could potentially capture an opposing piece 
          moves.add(board[start.getRow()+1][start.getCol()]);
      }
    }
    return moves;
  }
    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.

    //anti pawn- captures forward and moves diagonally, but only moves forward (relative to the player's colour)
    public ArrayList<Square> getLegalMoves(Board b, Square start){
      ArrayList<Square> moves = new ArrayList();
// for black
// for non-capturing
      if(color != true){
       // System.out.println(b.getSquareArray()[start.getRow()-1][start.getCol()-1].isOccupied());
        if(start.getCol()+1<8 && start.getRow()-1>=0 && b.getSquareArray()[start.getRow()-1][start.getCol()+1].isOccupied()==false){
            //your right: pawn moves one up, one right
            moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()+1]);
          }
        
          if(start.getCol()-1>=0 && start.getRow()-1>=0 && b.getSquareArray()[start.getRow()-1][start.getCol()-1].isOccupied()==false){
            //your right: pawn moves one down, one left
            moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()-1]);
          }//capture starts
        if(start.getRow()-1>=0 && b.getSquareArray()[start.getRow()-1][start.getCol()].isOccupied()== true && b.getSquareArray()[start.getRow()-1][start.getCol()].getOccupyingPiece().getColor()==true){
          //your right: pawn moves one down and captures an opposing piece 
          moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()]);
        }
      } 
      //for white
      //for noncapture
       if (color == true){
        //System.out.println(b.getSquareArray()[start.getRow()+1][start.getCol()-1].isOccupied());
        if(start.getCol()-1>=0 && start.getRow()+1<8 && b.getSquareArray()[start.getRow()+1][start.getCol()-1].isOccupied()==false){
            //your right: pawn moves one down, one left
            moves.add(b.getSquareArray()[start.getRow()+1][start.getCol()-1]);
          }
          if(start.getCol()+1<8 && start.getRow()+1<8 && b.getSquareArray()[start.getRow()+1][start.getCol()+1].isOccupied()==false){
            //your right: pawn moves one down, one right
            moves.add(b.getSquareArray()[start.getRow()+1][start.getCol()+1]);
          }//capture starts
          if(start.getRow()+1<8 && b.getSquareArray()[start.getRow()+1][start.getCol()].isOccupied()== true && b.getSquareArray()[start.getRow()+1][start.getCol()].getOccupyingPiece().getColor()==false){
            //your right: pawn moves one down and captures an opposing piece 
            moves.add(b.getSquareArray()[start.getRow()+1][start.getCol()]);
          }
      }


      //unused code- just here so that you know what happened to it after I changed it
      // //for capture for white
      // if(color == true){
      //   if(start.getCol()>0 && start.getRow()>0 && b.getSquareArray()[start.getRow()+1][start.getCol()].getOccupyingPiece().getColor()==false){
      //       //your right: pawn moves one down and captures an opposing piece 
      //       moves.add(b.getSquareArray()[start.getRow()+1][start.getCol()]);
        
      //   }
      // }
//       // for black
// // for non-capturing
// if(color == false){
//   if(start.getCol()>0 && start.getRow()>0 && b.getSquareArray()[start.getRow()+1][start.getCol()-1].isOccupied()==false){
//     if(start.getCol()+1<8 && start.getRow()+1<8 && start.getCol()+1<0 && start.getRow()+1<0){
//       //your right: pawn moves one up, one right
//       moves.add(b.getSquareArray()[start.getRow()+1][start.getCol()+1]);
//     }
//     if(start.getCol()-1<8 && start.getRow()+1<8 && start.getCol()-1<0 && start.getRow()+1<0){
//       //your left: pawn moves one up, one left
//       moves.add(b.getSquareArray()[start.getRow()+1][start.getCol()-1]);
//     }
//   }
// }
// //for capture
// if(color == false){
//   if(start.getCol()>0 && start.getRow()>0 && b.getSquareArray()[start.getRow()+1][start.getCol()].getOccupyingPiece().getColor()==true){
//     if(start.getRow()+1<8 && start.getRow()+1<0){
//       //our right: pawn moves one down and captures an opposing piece piece
//       moves.add(b.getSquareArray()[start.getRow()+1][start.getCol()]);
//     }
//   }
// }
      return moves;
    }
}