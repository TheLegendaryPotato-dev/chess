//Yuna Cho: 
// March 6th 2025
//Project chess i
// log: https://docs.google.com/document/d/1LC4giMF4OoGqneJzLoMXSrGRGkpov0zpOFFM_ymX0Z4/edit?tab=t.0

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    private Piece currPiece;
    public Piece endPiece;
    private Square fromMoveSquare;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    

    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
            if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1))
            {
                board[i][j]= new Square(this, true, i, j);
            }
            else{
                board[i][j]= new Square(this, false, i, j);
            }
            this.add(board[i][j]);
        }
        }  
        
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = false;

    }
    
    
	//set up the board such that the black pieces are on one side and the white pieces are on the other.
	//since we only have one kind of piece for now you need only set the same number of pieces on either side.
	//it's up to you how you wish to arrange your pieces. rook, knight, bishop, queen, king
    private void initializePieces() {
        //white
    	board[0][0].put(new CheckerPawn(true, RESOURCES_WROOK_PNG));//CheckerPawn replaces rook
        board[0][1].put(new Cone(true, RESOURCES_WKNIGHT_PNG));//cone replaces knight
        board[0][2].put(new Slime(true, RESOURCES_WBISHOP_PNG));// Slime replaces bishop
        board[0][3].put(new Helicopter(true, RESOURCES_WQUEEN_PNG));// the king loves his Helicopter more than the queen
        board[0][4].put(new King(true, RESOURCES_WKING_PNG));//king
        board[0][5].put(new Slime(true, RESOURCES_WBISHOP_PNG));// Slime replaces bishop
        board[0][6].put(new Cone(true, RESOURCES_WKNIGHT_PNG));//cone replaces knight
        board[0][7].put(new CheckerPawn(true, RESOURCES_WROOK_PNG));//CheckerPawn replaces rook
        for(int i = 0; i < board.length; i++){
            board[1][i].put(new AntiPawn(true, RESOURCES_WPAWN_PNG));//my pawns
        }
        //black
        board[7][0].put(new CheckerPawn(false, RESOURCES_BROOK_PNG));//CheckerPawn replaces rook
        board[7][1].put(new Cone(false, RESOURCES_BKNIGHT_PNG));//cone replaces knight
        board[7][2].put(new Slime(false, RESOURCES_BBISHOP_PNG));// Slime replaces bishop
        board[7][3].put(new Helicopter(false, RESOURCES_BQUEEN_PNG));// the king loves his Helicopter more than the queen
        board[7][4].put(new King(false, RESOURCES_BKING_PNG));//king
        board[7][5].put(new Slime(false, RESOURCES_BBISHOP_PNG));// Slime replaces bishop
        board[7][6].put(new Cone(false, RESOURCES_BKNIGHT_PNG));//cone replaces knight
        board[7][7].put(new CheckerPawn(false, RESOURCES_BROOK_PNG));//CheckerPawn replaces rook
        for(int i = 0; i < board.length; i++){
            board[6][i].put(new AntiPawn(false, RESOURCES_BPAWN_PNG));//my pawns
        }
    }


    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }
//precondition - the board is initialized and contains a king of either color. The boolean kingColor corresponds to the color of the king we wish to know the status of.
          //postcondition - returns true of the king is in check and false otherwise.
	public boolean isInCheck(boolean kingColor){
		for(int i= 0; i< board.length; i++){
            for(int j= 0; j< board[i].length; j++){
                if (board[i][j].isOccupied() && board[i][j].getColor() == kingColor){
                    for(Square s: board[i][j].getOccupyingPiece().getControlledSquares(board, board[i][j])){
                        //find out if any of these "s" squares contain a king of color kingColor if they do we're in check!
                        //if None of them do we are not in check.
                        if (s.getOccupyingPiece() instanceof King && s.getColor() == kingColor)
                        return true;
                    }
                }
            }
        }
        return false;
}

    @Override
    public void paintComponent(Graphics g) {
     
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            if (!currPiece.getColor() && whiteTurn)
                return;
            if (currPiece.getColor() && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    //TO BE IMPLEMENTED!
    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        
        //using currPiece
        if (currPiece != null){
         if (currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare)&& whiteTurn == currPiece.getColor()){
            // failed code trying to fix issue; endSquare = null;
            endPiece = endSquare.getOccupyingPiece();
            endSquare.put(currPiece);
            fromMoveSquare.put(null);

             if (isInCheck(whiteTurn)){
                //undo the move
               fromMoveSquare.put(currPiece);
               endSquare.put(endPiece);
            }
            whiteTurn = !whiteTurn;
           

         }
        }
       
        //loop through the board and for every square say s.setBorder(null)
    for(int i = 0; i < board.length; i++){
        for (int j = 0; j < board[i].length; j++){
                board[i][j].setBorder(null);
        }
    }
        fromMoveSquare.setDisplay(true);
        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
       
        if(currPiece != null){
            System.out.println(currPiece.getLegalMoves(this, fromMoveSquare));
            for(Square s: currPiece.getLegalMoves(this, fromMoveSquare)){
                s.setBorder(BorderFactory.createLineBorder(Color.magenta));
            }
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}