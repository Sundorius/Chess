package chess;
import pieces.Piece;

/**
 * Represents a square in the chess game.
 * 
 * @author Sundorius
 */
public class Node
{
    private Piece piece;
    private String pieceColor;
    

    /**
     * Creates a node with the specified piece and piece color.
     * @param piece The piece.
     * @param pieceColor The color of the piece.  
     */
    public Node(Piece piece, String pieceColor)
    {
        this.piece = piece;
        this.pieceColor = pieceColor;
    }
    
    
    /**
     * Copies a node into another node.
     * 
     * @param node The node that is going to be copied.
     */
    public Node(Node node)
    {
        this.piece =  node.getPiece().clone();
        this.pieceColor = node.getPieceColor();
    }
    
    
    /**
     * Creates a node with the specified piece but with no piece color.
     * 
     * @param piece The piece.
     */
    public Node(Piece piece)
    {
        this.piece = piece;
        this.pieceColor = null;
    }
    
    
    /**
     * Empty constructor for Node.
     */
    public Node()
    {
        this.piece = null;
        this.pieceColor = null;
    }
    

    /**
     * Sets the piece of the node.
     * 
     * @param piece The piece of the node.
     */
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
    
    
    /**
     * Sets the color of the piece of the node.
     * 
     * @param pieceColor The color of the piece of the node.
     */
    public void setPieceColor(String pieceColor)
    {
        this.pieceColor = pieceColor;
    }
    

    /**
     * Returns the piece of the node.
     * 
     * @return The piece of the node.
     */
    public Piece getPiece()
    {
        return this.piece;
    }
    
    
    /**
     * Returns the color of the piece of the node.
     * 
     * @return The color of the piece of the node.
     */
    public String getPieceColor()
    {
        return this.pieceColor;
    }
}