package pieces;

import chess.Node;


/**
 * Represents the King piece of the chess game.
 * 
 * @author Sundorius
 *
 */
public class King implements Piece
{
    private String type;
    private int horizontal;
    private int vertical;
    private int moveCounter;
    private final int strength; /* It is final because the strength must not change, that's why there is no setter for strength. */
    
    
    /**
     * Creates a King piece with the specified coordinates.
     * 
     * @param horizontal The horizontal coordinate.
     * @param vertical The vertical coordinate.
     */
    public King(int horizontal, int vertical)
    {
        /* super("king", horizontal, vertical, 0 , 10000); */
        this.type = "king";
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.moveCounter = 0;
        this.strength = 10000;
    }

    
    /**
     * Empty constructor.
     */
    public King()
    {
        /* super("king", -1, -1, 0 , 10000); */
        this.type = "king";
        this.horizontal = -1;
        this.vertical = -1;
        this.moveCounter = 0;
        this.strength = 10000;
    } 
    
    
    /**
     * Returns the type of the piece.
     * 
     * @return The type of the piece.
     */
    public String getType()
    {
        return this.type;
    }
    
    
    /**
     * Returns the horizontal placement of the piece.
     * 
     * @return The horizontal placement of the piece.
     */    
    public int getHorizontal()
    {
        return this.horizontal;
    }
    
    
    /**
     * Returns the vertical placement of the piece.
     * 
     * @return The vertical placement of the piece.
     */
    public int getVertical()
    {
        return this.vertical;
    }
    
    
    /**
     * Returns the number of moves.
     * 
     * @return The number of moves.
     */
    public int getMoveCounter()
    {
        return this.moveCounter;
    }
    
    
    /**
     * Returns the strength of the piece.
     * 
     * @return The strength of the piece.
     */
    public int getStrength()
    {
        return this.strength;
    }
    
    
    /**
     * Sets the type of the piece.
     * 
     * @param type The type of the piece.
     */
    public void setType(String type)
    {
        this.type = type;
    }
    
    
    /**
     * Sets the vertical placement of the piece.
     * 
     * @param vertical The vertical placement of the piece.
     */
    public void setVertical(int vertical)
    {
        this.vertical = vertical;
    }
    
    
    /**
     * Sets the horizontal placement of the piece.
     * 
     * @param horizontal The horizontal placement of the piece.
     */
    public void setHorizontal(int horizontal)
    {
        this.horizontal = horizontal;
    }
    
    
    /**
     * Sets the move number.
     * 
     * @param moveCounter The move counter.
     */
    public void setMoveCounter(int moveCounter)
    {
        this.moveCounter = moveCounter;
    }
    

    /**
     * Returns all the information of the piece.
     * 
     * @return All the information of the piece.
     */
    @Override
    public String toString()
    {
       return "Piece Type: " + this.type + "\nStrength: "+this.strength+ "\nMove Counter: " + this.moveCounter + "\nHorizontal: "+this.horizontal + "\nVertical: " + this.vertical;
    }
    
    
    /**
     * Returns a copy by value of its self.
     * 
     * @return A copy by value of its self.
     */
    public King clone()
    {
        King newKing = new King(this.getHorizontal(), this.getVertical());
        newKing.setMoveCounter(this.moveCounter);
        return newKing;
    }
    
    
    /**
     * Checks if the move is valid or not, by checking the user target coordinates and the type of the piece.
     * 
     * @param isAI If it is in AI mode.
     * @param board The board of the game.
     * @param currHor The current horizontal position.
     * @param currVert The current vertical position.
     * @param targetHor The target's horizontal position.
     * @param targetVert The target's horizontal position.
     * @return True if move is acceptable, else false.
     */
    @Override
    public boolean checkMove(boolean isAI, Node[][] board, int currHor, int currVert, int targetHor, int targetVert)
    {
        /* Out of bounds check. */
        if(targetHor > 5 && targetHor < 0 && targetVert > 5 && targetVert < 0)
        {
            if(!isAI) System.out.println("Wrong placement given, out of bounds!(KING)");
            return false;
        }
        /* Check if the square user wants to move to/attack is the same he wants to move from. */
        else if(targetHor == currHor && targetVert == currVert)
        {
            if(!isAI) System.out.println("You chose the current square, please choose another!(KING)");
            return false;
        }   
        /* Check if the square user wants to move to/attack is on the same team. */
        if (board[currHor][currVert].getPieceColor().equals(board[targetHor][targetVert].getPieceColor())) 
        {
            if(!isAI) System.out.println("Wrong placement, target square has a friendly piece!(KING)");
            return false;
        }
        
        /* King can move 1 square in each direction. */
        if(targetVert == currVert || targetVert == currVert+1 || targetVert == currVert-1)
        {
            if(targetHor == currHor || targetHor == currHor+1 || targetHor == currHor-1)
            {
                return true;
            }
        }
        if(!isAI) System.out.println("King move not acceptable!");
        return false;
    }  
}