package player;

import pieces.Bishop;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a player of a chess game.
 *  
 * @author Sundorius
 */

public class Player
{
    private ArrayList<Piece> alivePieces;
    private ArrayList<Piece> deadPieces;
    private String playerType; /* Human or Computer. */
    private String playerColor; /* Black or white. */
   
    
    /**
     * Creates a player with the specified type and color.
     * 
     * @param playerType Type of the player
     * @param playerColor Color of the player  
     */
    public Player(String playerType, String playerColor)
    {
        this.alivePieces = new ArrayList<Piece>();
        this.deadPieces = new ArrayList<Piece>();
        this.playerType = playerType;
        this.playerColor = playerColor;
        this.setPieces();
    }
    
    
    /**
     * Creates a blank player.
     */
    public Player()
    {
        this.alivePieces = new ArrayList<Piece>();
        this.deadPieces = new ArrayList<Piece>();
        playerType = null;
        playerColor = null;
    }
    
    
    /**
     * Sets the pieces according to player's color.
     */
    private void setPieces()
    {
        if("black".equals(this.playerColor))
        { 
            this.alivePieces.add(new Rook(5,0));
            this.alivePieces.add(new Bishop(5,1));
            this.alivePieces.add(new Queen(5,2));
            this.alivePieces.add(new King(5,3)); 
            this.alivePieces.add(new Bishop(5,4));
            this.alivePieces.add(new Rook(5,5));   
            for(int i=0; i<6; i++)
            {
                this.alivePieces.add(new Pawn(4,i)); /* row:4 , line:i */
            }
        }
        else
        {
            this.alivePieces.add(new Rook(0,0));
            this.alivePieces.add(new Bishop(0,1));
            this.alivePieces.add(new Queen(0,2));
            this.alivePieces.add(new King(0,3));
            this.alivePieces.add(new Bishop(0,4));
            this.alivePieces.add(new Rook(0,5));
            for(int i=0; i<6; i++)
            {
                this.alivePieces.add(new Pawn(1,i)); /* row:1 , line:i */
            }
        }
    }
    
    
    /**
     * Sets the type of the player
     * 
     * @param playerType The type of the player  
     */
    public void setPlayerType(String playerType)
    {
        playerType=playerType.toLowerCase();
        Scanner keyboard = new Scanner(System.in);
        while(!playerType.equals("human") && !playerType.equals("ai"))
        {
            System.out.println("Wrong player type, please insert the right one!");
            playerType = keyboard.nextLine().toLowerCase();
        }
        this.playerType = playerType;
    }
    
    
    /**
     * Sets the color of the player
     * 
     * @param playerColor The color of the player  
     */
    public void setPlayerColor(String playerColor)
    {
        playerColor=playerColor.toLowerCase();
        Scanner keyboard = new Scanner(System.in);
        while(!playerColor.equals("black") && !playerColor.equals("white"))
        {
            System.out.println("Wrong color, please insert the right one!");
            playerColor = keyboard.nextLine().toLowerCase();
        }
        this.playerColor = playerColor;
    }
    
    
    /**
     * Removes a piece from alive piece array and adds it to dead piece array.
     * 
     * @param pieceType The type of the piece to be removed  
     * @param horizontal The horizontal position of the piece that will be removed  
     * @param vertical The vertical position of the piece that will be removed  
     */
    public void killPiece(String pieceType, int horizontal, int vertical)
    {
        for(int i=0; i<this.alivePieces.size(); i++)
        {
            if( pieceType.equals(this.alivePieces.get(i).getType()) && this.alivePieces.get(i).getHorizontal() == horizontal
                    && this.alivePieces.get(i).getVertical() == vertical)
            {
                Piece pieceToDie = this.alivePieces.get(i);
                pieceToDie.setHorizontal(-1);
                pieceToDie.setVertical(-1);
                pieceToDie.setMoveCounter(0);
                this.deadPieces.add(pieceToDie); /* Add the alive piece to the dead piece array. */
                this.alivePieces.remove(i); /* Remove alive piece from the alive piece array. */
                return;
            }
        }
        System.out.println("Unknown Error in Payer.killPiece()!");
    }
    
    
    /**
     * Removes the dead piece from the dead piece array, and adds it to the alive piece array 
     	assigning it the new coordinates.
     *	
     * @param pieceType The type of the piece to be removed.  
     * @param horizontal The horizontal position of the piece that will be removed.
     * @param vertical The vertical position of the piece that will be removed.
     */
    public void resurrectPiece(String pieceType, int horizontal, int vertical)
    {
        for(int i=0; i<this.deadPieces.size(); i++)
        {
            if( pieceType.equals(this.deadPieces.get(i).getType()) )
            {
                Piece pieceToResurrect = this.deadPieces.get(i);
                /* Move counter is automatically set to 0 at the death of the piece. */
                pieceToResurrect.setHorizontal(horizontal);
                pieceToResurrect.setVertical(vertical);
                this.alivePieces.add(pieceToResurrect); /* Add the dead piece to the alive piece array. */
                this.deadPieces.remove(i); /* Remove dead piece from the array. */
                return;
            }
        }
        System.out.println("Unknown Error in Player.resurrectPiece()!");
    }
     
    
    /**
     * Returns the type of the player.
     * 
     * @return The type of the player.
     */
    public String getPlayerType()
    {
        return this.playerType;
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
     * Returns the array with the alive pieces.
     * 
     * @return The array with the alive pieces.
     */
    public ArrayList<Piece> getAlivePieces()
    {
        return this.alivePieces;
    }
    
    
    /**
     * Returns the array with the dead pieces.
     * 
     * @return The array with the dead pieces.
     */
    public ArrayList<Piece> getDeadPieces()
    {
        return this.deadPieces;
    }
}