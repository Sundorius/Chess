package pieces;

import chess.Node;

/**
 * This interface has the common "parts" of a chess piece.
 * 
 * @author Sundorius
 *
 */
public interface Piece
{

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
    public boolean checkMove(boolean isAI, Node[][] board, int currHor, int currVert, int targetHor, int targetVert);
    

    /**
     * Returns the type of the piece.
     * 
     * @return The type of the piece.
     */
    public String getType();
    
    /**
     * Returns the horizontal placement of the piece.
     * 
     * @return The horizontal placement of the piece.
     */
    public int getHorizontal();
    
    
    /**
     * Returns the vertical placement of the piece.
     * 
     * @return The vertical placement of the piece.
     */
    public int getVertical();
    
    
    /**
     * Returns the number of moves.
     * 
     * @return The number of moves.
     */
    public int getMoveCounter();
    

    /**
     * Returns the strength of the piece.
     * 
     * @return The strength of the piece.
     */
    public int getStrength();

    
    /**
     * Sets the type of the piece.
     * 
     * @param type The type of the piece.
     */
    public void setType(String type);
    
    
    /**
     * Sets the vertical placement of the piece.
     * 
     * @param vertical The vertical placement of the piece.
     */
    public void setVertical(int vertical);
    

    /**
     * Sets the horizontal placement of the piece.
     * 
     * @param horizontal The horizontal placement of the piece.
     */
    public void setHorizontal(int horizontal);

    /**
     * Sets the move number.
     * 
     * @param moveCounter The move counter.
     */
    public void setMoveCounter(int moveCounter);
    

    /**
     * Returns all the information of the piece.
     * 
     * @return All the information of the piece.
     */
    @Override
    public String toString();

    
    /**
     * Returns a copy by value of its self.
     * 
     * @return A copy by value of its self.
     */
    public Piece clone() ;
}