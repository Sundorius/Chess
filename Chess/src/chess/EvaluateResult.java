package chess;

/**
 * Represents the result of the method evaluatePosition() int file chess.Chess.
 * 
 * @author Sundorius
 *
 */
public class EvaluateResult
{
    private int moveHorizontal = -1;
    private int moveVertical = -1;
    private int targetHorizontal = -1;
    private int targetVertical = -1;
    private String playerColor = null;
    private int evaluationBoardResult = -1;

    
    /**
     * Empty Constructor.
     */
    public EvaluateResult() {}

    
    /**
     * Creates an EvaluateResult class with the specified type and color.
     * 
     * @param moveHorizontal The horizontal coordinate of the source square. 
     * @param moveVertical The vertical coordinate of the source square.
     * @param targetHorizontal The horizontal coordinate of the target square.
     * @param targetVertical The vertical coordinate of the target square.
     * @param playerColor The color of the player.
     * @param evaluationBoardResult The evaluation of the board. 
     */
    public EvaluateResult(int moveHorizontal,int moveVertical,int targetHorizontal,int targetVertical,String playerColor, int evaluationBoardResult)
    {
        this.moveHorizontal = moveHorizontal;
        this.moveVertical = moveVertical;
        this.targetHorizontal = targetHorizontal;
        this.targetVertical = targetVertical;
        this.playerColor = playerColor;
        this.evaluationBoardResult = evaluationBoardResult;
    }	

    
    /**
     *  Sets the result of evaluation board.
     * 
     * @param evaluationBoardResult The result of evaluation board.
     */
    public void setEvaluationBoardResult(int evaluationBoardResult)
    {
        this.evaluationBoardResult = evaluationBoardResult;
    }
    
    
    /**
     * Sets current horizontal position.
     * 
     * @param moveHorizontal The current horizontal position.
     */
    public void setMoveHorizontal(int moveHorizontal)
    {
        this.moveHorizontal = moveHorizontal;
    }
    
    
    /**
     * Sets current vertical position.
     * 
     * @param moveVertical The current vertical position.
     */
    public void setMoveVertical(int moveVertical)
    {
        this.moveVertical = moveVertical;
    }
    
    
    /**
     * Sets target horizontal position.
     * 
     * @param targetHorizontal The target's horizontal position.
     */
    public void setTargetHorizontal(int targetHorizontal)
    {
        this.targetHorizontal = targetHorizontal;
    }
    
    
    /**
     * Sets target vertical position.
     * 
     * @param targetVertical The target's vertical position.
     */
    public void setTargetVertical(int targetVertical)
    {
        this.targetVertical = targetVertical;
    }
    
    
    /**
     * Sets the color of the player.
     * 
     * @param playerColor The color of the player.
     */
    public void setPlayerColor(String playerColor)
    {
        this.playerColor = playerColor;
    }
    

    /**
     * Returns the current horizontal coordinate.
     * 
     * @return The current horizontal coordinate.
     */
    public int getMoveHorizontal()
    {
        return this.moveHorizontal;
    }

    
    /**
     * Returns the current vertical coordinate.
     * 
     * @return The current vertical coordinate.
     */
    public int getMoveVertical()
    {
        return this.moveVertical;
    }

    
    /**
     * Returns the target's horizontal coordinate.
     * 
     * @return The target's horizontal coordinate.
     */
    public int getTargetHorizontal()
    {
        return this.targetHorizontal;
    }

    
    /**
     * Returns the target's vertical coordinate.
     * 
     * @return The target's vertical coordinate.
     */
    public int getTargetVertical()
    {
        return this.targetVertical;
    }

    
    /**
     * Returns the color of the player.
     * 
     * @return The color of the player.
     */
    public String getPlayerColor()
    {
        return this.playerColor;
    }

    
    /**
     * Returns the result of evaluation board.
     * 
     * @return The result of evaluation board.
     */
    public int getEvaluationBoardResult()
    {
        return this.evaluationBoardResult;
    }
    
    /**
     *
     */
    @Override
    public String toString()
    {
        return "\nMove Horizontal: "+this.moveHorizontal+"\nMove Vertical: "+this.moveVertical+"\nTarget Horizontal: "+this.targetHorizontal
                +"\nTarget Vertical: "+this.targetVertical+"\nPlayer Color: "+this.playerColor+"\nEvaluation Board Result: "+this.evaluationBoardResult;
    }
}