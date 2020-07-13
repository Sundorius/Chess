package chess;

import pieces.Piece;
import pieces.Bishop;
import pieces.King;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import player.Player;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Represents a chess game, with 3 different type of games.
 * Human vs Human , Human vs AI , AI vs AI.
 * 
 * @author Sundorius
 *
 */
public class Chess
{
    Node[][] board;
    Player blackPlayer;
    Player whitePlayer;
    String currPlayerToPlay;  /* white for white player, black for black player. */
    String currPlayerType;   /* human or ai. */
    String nextPlayer;      /* white for white player, black for black player. */
    String nextPlayerType; /* human or ai. */
    int roundNum;
    
    
    /**
     * Creates the appropriate chess game, depending on the option.
     * @param option The option of the game.
     */
    public Chess(int option)
    {
        switch(option)
        {
            case 1:
            {
                System.out.println("\n=====================\nHuman vs Human Chess.\n=====================");
                this.whitePlayer = new Player("human", "white");
                System.out.println("\nPlayer 1 is the white player.");
                this.currPlayerToPlay = "white";
                this.currPlayerType = "human";
                System.out.println("Player 2 is the black player.");
                this.nextPlayer = "black";
                this.nextPlayerType = "human";
                this.blackPlayer = new Player("human", "black");
                System.out.println("\nTo quit just type \"q\" in the vertical position"
                + " of the piece you want to move!\n");
                break;
            }
            case 2:
            {
                System.out.println("\n========================\nHuman vs Computer Chess.\n========================");
                System.out.println("\nHuman is the white player.");
                this.whitePlayer = new Player("human", "white");
                this.currPlayerToPlay = "white";
                this.currPlayerType = "human";
                System.out.println("Computer is the black player.");
                this.nextPlayer = "black";
                this.nextPlayerType = "ai";
                this.blackPlayer = new Player("ai", "black");
                System.out.println("\nTo quit just type \"q\" in the vertical position"
                + " of the piece you want to move!\n");
                break;
            }
            case 3:
            {
                System.out.println("\n===========================\nComputer vs Computer Chess.\n===========================");
                this.whitePlayer = new Player("ai", "white");
                System.out.println("\nComputer is the white player.");
                this.currPlayerToPlay = "white";
                this.currPlayerType = "ai";
                System.out.println("Computer is the black player.");
                this.nextPlayer = "black";
                this.nextPlayerType = "ai";
                this.blackPlayer = new Player("ai", "black");
                break;
            }
        }
        System.out.println("!!!!    After each round, program is paused for 4 seconds    !!!!");
        this.board = new Node[6][6]; 
        this.roundNum = 1;
        this.setBoard();
        this.printRound();
    }

    
    /**
     * Asks user to give the vertical and horizontal positions, then checks move if 
     *	the move is correct, and makes the attack (if there is an attack).
     * Generates a move for the AI and checks if it is correct, 
     *  and makes the attack (if there is an attack).
     *  
     * @return It returns true if game ends, else it returns false. 
     */
    public boolean makeMove()
    {
        int whiteKingResult = -1;
        int blackKingResult = -1;   
        int moveHorizontal = -1;
        int moveVertical = -1 ;
        int targetHorizontal = -1;
        int targetVertical = -1;
        String targetPieceType = null;
        String currPieceType = null;
        boolean moveCheckRes = false;
        
        /* Check if king can move. */
        if("black".equals(currPlayerToPlay))
        {
            blackKingResult = kingSearch(blackPlayer);
            whiteKingResult = kingSearch(whitePlayer);            
        } 
        else
        {
            whiteKingResult = kingSearch(this.whitePlayer);
            blackKingResult = kingSearch(this.blackPlayer);
        }  
        if(blackKingResult == 1 && blackKingResult == 1)
        {
            System.out.println("Both kings can not move, it is a draw!");
            this.printRound();
            return true;
        }
        else if(blackKingResult == 1)
        {
            System.out.println("Black king can not move, white player won!");
            this.printRound();
            return true;
        }
        else if(whiteKingResult == 1)
        {
            System.out.println("White king can not move, black player won!");
            this.printRound();
            return true;
        }
        else if(blackKingResult == 2 && whiteKingResult == 2)
        {
            System.out.println("There are only kings in the board, it is a draw!");
            this.printRound();
            return true;
        }
        
        /* Check if move is correct, if move is correct and there is a possible attack
            make the attack, else swap the square's pieces. If move is not correct, ask player to 
            make another move. */
        while(!moveCheckRes)
        {
            int[] currPlayerMoves = findCurrPlayerMoves();
            if(currPlayerMoves == null)
            {
                System.out.println("Error in MakeMove() player square selection!");
                System.out.println("Program exiting...");
                exit(1);
            }
            moveHorizontal = currPlayerMoves[0];
            moveVertical = currPlayerMoves[1];
            targetHorizontal = currPlayerMoves[2];
            targetVertical = currPlayerMoves[3];   

            System.out.println(this.currPlayerToPlay.toUpperCase()+" player trying to move from square  "+(moveHorizontal+1)+""+mapIntToChar(moveVertical)
                                                                               +" to square "+(targetHorizontal+1)+""+mapIntToChar(targetVertical)+"...");
            /* Saves the types of pieces of the current player, and the enemy, if the
             squares have a piece. */
            if(board[moveHorizontal][moveVertical].getPiece() != null)
            {
                currPieceType = board[moveHorizontal][moveVertical].getPiece().getType();
            }
            if(board[targetHorizontal][targetVertical].getPiece() != null)
            {
                targetPieceType = board[targetHorizontal][targetVertical].getPiece().getType();
            }
            
            /* Checks if selected square has a piece. */
            if(board[moveHorizontal][moveVertical].getPiece() == null)
            {
                System.out.println("The square you chose has no piece to move!");
                moveCheckRes = false;
            }
            /* Check if piece belongs to the opponent. */
            else if(nextPlayer.equals(board[moveHorizontal][moveVertical].getPieceColor()))
            {
                System.out.println("You can not move the opponent's pieces!");
                moveCheckRes = false;
            }
            /* Checks move for validity. */
            if(board[moveHorizontal][moveVertical].getPiece() != null)
            {
                moveCheckRes = board[moveHorizontal][moveVertical].getPiece().checkMove(this.currPlayerType.equals("ai"), board, moveHorizontal, moveVertical, targetHorizontal, targetVertical);
                /* Extra 1 more time, for AI. */
                if(nextPlayer.equals(board[moveHorizontal][moveVertical].getPieceColor()))
                {
                    System.out.println("You can not move the opponent's pieces!");
                    moveCheckRes = false;
                }
            }
            /*If move is false just repeat the question. */
            if(moveCheckRes == false)
            {
                if(this.currPlayerType.equals("human")) System.out.println("\nMove for this piece is not correct, please try again");
            }
        }
        
        /* Some information of the moves for the user (if piece attacks or just moves to the target piece).  */
        System.out.println("Successful move!");
        if(targetPieceType != null)
        {
            System.out.println(this.currPlayerToPlay.toUpperCase()+" player attacked with the "+currPieceType.toUpperCase()+" from square "+(moveHorizontal+1)+""+mapIntToChar(moveVertical)
                                                                        +" the "+targetPieceType.toUpperCase()+" to square "+(targetHorizontal+1)+""+mapIntToChar(targetVertical));
        }
        else
        {
            System.out.println(this.currPlayerToPlay.toUpperCase()+" player moved the "+currPieceType.toUpperCase()+" from square "+(moveHorizontal+1)+""+mapIntToChar(moveVertical)
                                                                        +" to square "+(targetHorizontal+1)+""+mapIntToChar(targetVertical));
        }
        
        /* Increment move counter of the piece that is going to move/attack. */
        int oldMoveCounter = board[moveHorizontal][moveVertical].getPiece().getMoveCounter();
        board[moveHorizontal][moveVertical].getPiece().setMoveCounter(++oldMoveCounter); /* First increment and then set the new move counter! */
        
        /* Else, if move is correct carry on. */
        /*If target square has no piece, just swap characteristics. */
        if(board[targetHorizontal][targetVertical].getPiece() == null)
        {   
            /* Change the coordinates of the piece in the collection of the player. */
            if("black".equals(this.currPlayerToPlay))
            {
                /* Set the new coordinates to the piece that moved, in the player's alive pieces array. 
                      Increase the move counter of the piece. */
                for(int i=0; i<this.blackPlayer.getAlivePieces().size(); i++)
                {
                    if(this.blackPlayer.getAlivePieces().get(i).getHorizontal()== moveHorizontal && this.blackPlayer.getAlivePieces().get(i).getVertical()== moveVertical)
                    {
                        this.blackPlayer.getAlivePieces().get(i).setHorizontal(targetHorizontal);
                        this.blackPlayer.getAlivePieces().get(i).setVertical(targetVertical);
                        this.blackPlayer.getAlivePieces().get(i).setMoveCounter(++oldMoveCounter); /* First increment and then set the new move counter! */
                    }
                }
            }
            else
            {
                /* Set the new coordinates to the piece that moved, in the player's alive pieces array.
                      Increase the move counter of the piece. */
                for(int i=0; i<this.whitePlayer.getAlivePieces().size(); i++)
                {
                    if(this.whitePlayer.getAlivePieces().get(i).getHorizontal()== moveHorizontal && this.whitePlayer.getAlivePieces().get(i).getVertical()== moveVertical)
                    {
                        this.whitePlayer.getAlivePieces().get(i).setHorizontal(targetHorizontal);
                        this.whitePlayer.getAlivePieces().get(i).setVertical(targetVertical);
                        this.whitePlayer.getAlivePieces().get(i).setMoveCounter(++oldMoveCounter); /* First increment and then set the new move counter! */
                    }
                }
            }
        }
        /*If target square has a piece, attack! */
        else
        {
            if("king".equals(board[targetHorizontal][targetVertical].getPiece().getType()))
            {
                System.out.println("\n"+this.currPlayerToPlay.toUpperCase()+" attacked opponents king, player won!\n\n");
                return true;
            }
            if("black".equals(nextPlayer))
            {
                /*Search for the enemy piece, to kill it. */
                for(int i=0; i<this.blackPlayer.getAlivePieces().size(); i++)
                {
                    if(this.blackPlayer.getAlivePieces().get(i).getHorizontal()== targetHorizontal && this.blackPlayer.getAlivePieces().get(i).getVertical()== targetVertical)
                    {
                        this.blackPlayer.killPiece(this.blackPlayer.getAlivePieces().get(i).getType(), targetHorizontal, targetVertical);
                    }
                }
                /* Set the new coordinates to the piece that moved, in the player's alive pieces array.
                   Increase the move counter of the piece. */
                for(int i=0; i<this.whitePlayer.getAlivePieces().size(); i++)
                {
                    if(this.whitePlayer.getAlivePieces().get(i).getHorizontal()== moveHorizontal && this.whitePlayer.getAlivePieces().get(i).getVertical()== moveVertical)
                    {
                        this.whitePlayer.getAlivePieces().get(i).setHorizontal(targetHorizontal);
                        this.whitePlayer.getAlivePieces().get(i).setVertical(targetVertical);
                        this.whitePlayer.getAlivePieces().get(i).setMoveCounter(++oldMoveCounter);
                    }
                }
            }
            else
            {
                /*Search for the enemy piece, to kill it. */
                for(int i=0; i<this.whitePlayer.getAlivePieces().size(); i++)
                {
                    if(this.whitePlayer.getAlivePieces().get(i).getHorizontal()== targetHorizontal && this.whitePlayer.getAlivePieces().get(i).getVertical()== targetVertical)
                    {
                        this.whitePlayer.killPiece(this.whitePlayer.getAlivePieces().get(i).getType(), targetHorizontal, targetVertical);
                    }
                }
                /* Set the new coordinates to the piece that moved, in the player's alive pieces array.
                   Increase the move counter of the piece. */
                for(int i=0; i<this.blackPlayer.getAlivePieces().size(); i++)
                {
                    if(this.blackPlayer.getAlivePieces().get(i).getHorizontal()== moveHorizontal && this.blackPlayer.getAlivePieces().get(i).getVertical()== moveVertical)
                    {
                        this.blackPlayer.getAlivePieces().get(i).setHorizontal(targetHorizontal);
                        this.blackPlayer.getAlivePieces().get(i).setVertical(targetVertical);
                        this.blackPlayer.getAlivePieces().get(i).setMoveCounter(++oldMoveCounter);
                    }
                }
            }   
        }
        
        /* Make pawn promotion if any. */
        promotePawn(moveHorizontal, moveVertical, targetHorizontal, targetVertical);
        
        /* Last-minute adjustments.  */
        String tempPlayer = currPlayerToPlay;
        String tempType = currPlayerType;
        currPlayerToPlay = nextPlayer;
        currPlayerType = nextPlayerType;
        nextPlayer = tempPlayer;
        nextPlayerType = tempType;
        roundNum++;
        printRound();
        
        /* Pause execution for 4 seconds. */
        try
        {
            TimeUnit.SECONDS.sleep(4);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Chess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;    
    }

    
   /*====================================================================  Functions For AI Moves START ====================================================================

    
    
    /**
     * Finds the best move for the current round for the AI.
     * First it generates all the possible moves and then generates the possible moves for the enemy.
     * Then evaluates the enemy move returning the worst move for the enemy, and then evaluates current player's 
     * move and returns the best possible move.
     * 
     * @param board The board of the game.
     * @param alpha It is an Integer.MIN_VALUE (favors currPlayer).
     * @param beta It is an Integer.MAX_VALUE(disadvantages nextPlayer).
     * @param depth The maximum depth to search for the best move.
     * @param playerColor The color of the player.
     * @param favorOrNot If favored by alpha-beta pruning.
     * @param result The result.
     * @return It returns the coordinates of the best move possible, as an EvaluateResult class.
     */
    private EvaluateResult evaluatePosition(Node[][] board, int alpha, int beta, int depth, String playerColor, boolean favorOrNot, EvaluateResult result)
    {
        /* Finds enemy color. */
        String enemyColor="";
        if(playerColor.equals("white"))
        {
            enemyColor = "black";
        }
        else if(playerColor.equals("black"))
        {
            enemyColor = "white";
        }

        if(depth == 0)
        {
            EvaluateResult retResult;
            if(favorOrNot)
            {
                retResult = evaluateBoard(board, enemyColor, favorOrNot, result);
                /*result = evaluateBoard(board, playerColor, favorOrNot, result); */
            }
            else
            {
                retResult = evaluateBoard(board, enemyColor, !favorOrNot, result);
                /*result = evaluateBoard(board, playerColor, favorOrNot, result); */
            }
            return retResult; /* Returns the coordinates of the move, with the evaluation board result(that is useless here). */
        }

        ArrayList<EvaluateResult> validMoves = new ArrayList<EvaluateResult>();
        for(int i=0; i<6; i++)
        {
            for(int j=0; j<6; j++)
            {
                /* If square has a piece. */
                if(board[i][j].getPiece()!=null)
                {
                    /* If piece of the square belongs to the player. */
                    if(board[i][j].getPieceColor().equals(playerColor))
                    {
                        /* Check the valid moves for the current piece. */
                        for(int r=0; r<6; r++)
                        {
                            for(int c=0; c<6; c++)
                            {
                                if(board[i][j].getPiece().checkMove(this.currPlayerType.equals("ai"), board,i,j,r,c))
                                {
                                  /*  System.out.println("==== Valid Move for: "+board[i][j].getPieceColor().toUpperCase()+" "+board[i][j].getPiece().getType().toUpperCase()
                                                        +" from row "+i+" col "+j+" to row "+r+" col "+c+" there is "+board[r][c].getPieceColor()
                                                        +" "+((board[r][c].getPiece()!=null)?board[r][c].getPiece().getType(): null));
                                  */
                                    validMoves.add(new EvaluateResult(i,j,r,c,playerColor,-1111)); /* -1111 means that the board has not been evaluated yet! */
                                }
                            }
                        }
                    }
                }
            }
        }

        /* If this player is NOT favored by the alpha-beta pruning. */
        int i=0; /* We will use i outside of the if statement. */
        Node[][] tempBoard = new Node[6][6] ;
        if(!favorOrNot)
        {
            EvaluateResult newResult = result;
            int worstBoardEvaluation = beta;
            for(i=0; i<validMoves.size(); i++)
            {
                /* Copy the array by value, so we do not change the real board.
                   Makes a copy by value of the board given in parameters. */
                for(int k=0; k<6; k++)
                {
                    for(int l=0; l<6; l++)
                    {
                        if(board[k][l].getPiece()==null)
                        {
                            tempBoard[k][l] = new Node(null ,null);
                        }
                        else
                        {
                            tempBoard[k][l] = new Node(board[k][l]);
                        }
                    }
                }

                /* Swap characteristics. */
                Piece PieceToMove = tempBoard[validMoves.get(i).getMoveHorizontal()][validMoves.get(i).getMoveVertical()].getPiece();
                String squareColor = tempBoard[validMoves.get(i).getMoveHorizontal()][validMoves.get(i).getMoveVertical()].getPieceColor();
                tempBoard[validMoves.get(i).getMoveHorizontal()][validMoves.get(i).getMoveVertical()].setPiece(null);
                tempBoard[validMoves.get(i).getMoveHorizontal()][validMoves.get(i).getMoveVertical()].setPieceColor(null);
                tempBoard[validMoves.get(i).getTargetHorizontal()][validMoves.get(i).getTargetVertical()].setPiece(PieceToMove);
                tempBoard[validMoves.get(i).getTargetHorizontal()][validMoves.get(i).getTargetVertical()].setPieceColor(squareColor);

                /* Set new coordinates of accepted move. */
                newResult.setMoveHorizontal(validMoves.get(i).getMoveHorizontal());
                newResult.setMoveVertical(validMoves.get(i).getMoveVertical());
                newResult.setTargetHorizontal(validMoves.get(i).getTargetHorizontal());
                newResult.setTargetVertical(validMoves.get(i).getTargetVertical());
                newResult.setPlayerColor(playerColor);
                
                newResult = evaluatePosition(tempBoard, alpha, worstBoardEvaluation, depth-1, enemyColor, !favorOrNot, newResult);
                worstBoardEvaluation = Math.min(worstBoardEvaluation, newResult.getEvaluationBoardResult());
                if(worstBoardEvaluation<= alpha)
                {
                    /* Set coordinates of the best move. */
                    newResult.setMoveHorizontal(validMoves.get(i).getMoveHorizontal());
                    newResult.setMoveVertical(validMoves.get(i).getMoveVertical());
                    newResult.setTargetHorizontal(validMoves.get(i).getTargetHorizontal());
                    newResult.setTargetVertical(validMoves.get(i).getTargetVertical());
                    newResult.setPlayerColor(playerColor);
                    
                    newResult.setEvaluationBoardResult(worstBoardEvaluation);
                    break;
                }
            }
            /* System.out.println("++++ FOUND the valid move for "+playerColor+" player with board evaluation: "+newResult.getEvaluationBoardResult()); */

            /* Returns the move with the lowest board evaluation. */
            return newResult; 
        }
        /* If this player IS favored by the alpha-beta pruning. */
        else
        {
            int bestBoardEvaluation = alpha;
            EvaluateResult newResult = result;
            for(i=0; i<validMoves.size(); i++)
            {
                /* Copy the array by value, so we do not change the real board.
                   Makes a copy by value of the board given in parameters. */
                for(int k=0; k<6; k++)
                {
                    for(int l=0; l<6; l++)
                    {
                        if(board[k][l].getPiece()==null)
                        {
                            tempBoard[k][l] = new Node(null ,null);
                        }
                        else
                        {
                            tempBoard[k][l] = new Node(board[k][l]);
                        }
                    }
                }    
                
                /* Swap characteristics. */
                Piece PieceToMove = tempBoard[validMoves.get(i).getMoveHorizontal()][validMoves.get(i).getMoveVertical()].getPiece();
                String squareColor = tempBoard[validMoves.get(i).getMoveHorizontal()][validMoves.get(i).getMoveVertical()].getPieceColor();
                tempBoard[validMoves.get(i).getMoveHorizontal()][validMoves.get(i).getMoveVertical()].setPiece(null);
                tempBoard[validMoves.get(i).getMoveHorizontal()][validMoves.get(i).getMoveVertical()].setPieceColor(null);
                tempBoard[validMoves.get(i).getTargetHorizontal()][validMoves.get(i).getTargetVertical()].setPiece(PieceToMove);
                tempBoard[validMoves.get(i).getTargetHorizontal()][validMoves.get(i).getTargetVertical()].setPieceColor(squareColor);

                /* Set new coordinates of accepted move. */
                newResult.setMoveHorizontal(validMoves.get(i).getMoveHorizontal());
                newResult.setMoveVertical(validMoves.get(i).getMoveVertical());
                newResult.setTargetHorizontal(validMoves.get(i).getTargetHorizontal());
                newResult.setTargetVertical(validMoves.get(i).getTargetVertical());
                newResult.setPlayerColor(playerColor);
                
                newResult = evaluatePosition(tempBoard, bestBoardEvaluation, beta, depth-1, enemyColor, favorOrNot, newResult);
                bestBoardEvaluation = Math.max(bestBoardEvaluation, newResult.getEvaluationBoardResult());
                if(beta<= bestBoardEvaluation)
                {
                    /* Set coordinates of the best move. */
                    newResult.setMoveHorizontal(validMoves.get(i).getMoveHorizontal());
                    newResult.setMoveVertical(validMoves.get(i).getMoveVertical());
                    newResult.setTargetHorizontal(validMoves.get(i).getTargetHorizontal());
                    newResult.setTargetVertical(validMoves.get(i).getTargetVertical());
                    newResult.setPlayerColor(playerColor);
                    
                    newResult.setEvaluationBoardResult(bestBoardEvaluation);
                    break;
                }
            }
            /* System.out.println("++++ FOUND the valid move for "+playerColor+" player."); */

            /* Returns the move with the highest board evaluation. */
            return newResult;
        }
    }
    
    
    /**
     * Checks all the squares, if square has a piece, then finds the color of 
     * the piece and adds its strength to the player with the same color. 
     * 
     * @param board The board of the game.
     * @param playerColor The color of the player.
     * @param favorOrNot If favored by alpha-beta pruning.
     * @param result The result.
     * @return
     */
    private EvaluateResult evaluateBoard(Node[][] board,String playerColor, boolean favorOrNot, EvaluateResult result)
    {
        int whitePlayerScore = 0;
        int blackPlayerScore = 0;
        EvaluateResult newResult = result;
        for(int i=0; i<6; i++)
        {
            for(int j=0; j<6; j++)
            {
                /* If square has a piece. */
                if(board[i][j].getPiece()!=null)
                {
                    /* If square has a white piece. */
                    if(board[i][j].getPieceColor().equals("white"))
                    { 
                        whitePlayerScore += board[i][j].getPiece().getStrength();
                    }
                    /* If square has a black piece. */
                    else if(board[i][j].getPieceColor().equals("black"))
                    {
                        blackPlayerScore += board[i][j].getPiece().getStrength();
                    }
                }
            }
        }
        if(playerColor.equals("black"))
        {
            newResult.setEvaluationBoardResult(blackPlayerScore-whitePlayerScore);
            /*if(favorOrNot)  newResult.setEvaluationBoardResult(blackPlayerScore-whitePlayerScore); */
            /*else  newResult.setEvaluationBoardResult(whitePlayerScore-blackPlayerScore); */
        }

        else if(playerColor.equals("white"))
        {
            newResult.setEvaluationBoardResult(whitePlayerScore-blackPlayerScore);
            /*if(favorOrNot)  newResult.setEvaluationBoardResult(whitePlayerScore-blackPlayerScore); */
            /*else  newResult.setEvaluationBoardResult(blackPlayerScore-whitePlayerScore); */
        }
        return newResult;
    }
    
    
    
/*====================================================================  Functions For AI Moves END ====================================================================  */   

    
    /**
     * From a given piece type, creates a new instance of that specific piece type.
     *
     * @param pieceType The type of the piece.
     * @return The new piece type.
     */
    private Piece makePiece(String pieceType)
    {
        switch(pieceType)
        {
            case "pawn": return new Pawn();
            case "rook": return new Rook();
            case "bishop": return new Bishop();
            case "queen": return new Queen();
            case "king": return new King();
            default:
                System.out.println("Error in Chess.makePiece(), piece type not found!");
                exit(1);
        }
        return null;
    }
    
    
    /**
     * Initializes the board.
     */
    private void setBoard()
    {
        ArrayList<Piece> whitePieces = this.whitePlayer.getAlivePieces();
        ArrayList<Piece> blackPieces = this.blackPlayer.getAlivePieces();
        int horizontal=0;
        int vertical=0;
        String pieceType=null;
        Piece piecee = null;
        for(int i=0; i<whitePieces.size(); i++)
        {
            horizontal = whitePieces.get(i).getHorizontal();
            vertical = whitePieces.get(i).getVertical();
            pieceType = whitePieces.get(i).getType();

            piecee = makePiece(pieceType);
            piecee.setHorizontal(horizontal);
            piecee.setVertical(vertical);
            this.board[horizontal][vertical] = new Node(piecee, "white");
        }
        horizontal = 5;
        vertical = 0;
        for(int i=0; i<blackPieces.size(); i++)
        {
            horizontal = blackPieces.get(i).getHorizontal();
            vertical = blackPieces.get(i).getVertical();
            pieceType = blackPieces.get(i).getType();

            piecee = makePiece(pieceType);
            piecee.setHorizontal(horizontal);
            piecee.setVertical(vertical);
            this.board[horizontal][vertical] = new Node(piecee, "black");
        }
        /* Empty middle lines 2 and 3. */
        for(int i=0; i<6; i++)
        {
            this.board[2][i] = new Node();
            this.board[3][i] = new Node();
        }
    }
    

    /**
     * Prints the board and the round. 
     */
    private void printRound()
    {
        String color;
        String pieceType="";
        String boardToPrint="";
        int numPlus=6;
        for(int i=5; i>=0; i--)
        {
            boardToPrint+=numPlus;
            for(int j=0; j<6; j++)
            {
                if(this.board[i][j].getPiece()==null)
                {
                    boardToPrint+=(" |     ");
                }
                else
                {
                    if(this.board[i][j].getPieceColor().equals("black")) color="B ";
                    else color="W ";

                    switch (this.board[i][j].getPiece().getType())
                    {
                        case "pawn":
                            pieceType="p";
                            break;
                        case "rook":
                            pieceType="r";
                            break;
                        case "bishop":
                            pieceType="b";
                            break;
                        case "queen":
                            pieceType="Q";
                            break;
                        case "king":
                            pieceType="K";
                            break;
                        default:
                            break;
                    }
                    
                    boardToPrint+=" | "+color+" "+pieceType;
                }
            }
            numPlus--;
            boardToPrint+=" |\n";
        }
        boardToPrint+="---------------------------------------------\n";
        
        boardToPrint+="  |   a  |   b  |   c  |   d  |   e  |   f  |\n";
        System.out.println("______________________________________________");
        System.out.println("\n                  Round  "+this.roundNum);
        System.out.println("______________________________________________");
        System.out.println(boardToPrint);
    } 
    
    
    /**
     * Finds if the moves are correct.
     * If current player is human, he/she is asked to choose a source square and the target square to move.
     * If it is AI the moves are generated and returned.
     * 
     * @return An integer array with the moves, else null.
     */
    private int[] findCurrPlayerMoves()
    {
        if(this.currPlayerType.equals("human"))
        {
            String userInputCoord = "";
            int moveHorizontal = -1;
            int moveVertical = -1 ;
            String SmoveVertical="";
            int targetHorizontal = -1;
            int targetVertical = -1;
            String StargetVertical="";
            Scanner keyboard = new Scanner(System.in);
            
            /* Check coordinates of the square user wants to move from. */
            do
            {
               
               System.out.println(this.currPlayerToPlay.toUpperCase()+ " player which piece do you want to move?");
                userInputCoord = keyboard.next();
                if(userInputCoord.toCharArray().length<2)/* If user gives input for horizontal position only! */
                {
                    moveHorizontal = -1;
                    moveVertical = -1;
                }
                else
                {
                    moveHorizontal =Integer.parseInt(String.valueOf(userInputCoord.charAt(0))); /* Get horizontal value and cast it to string, 
                                                                                                	to be casted into an integer. */
                    moveHorizontal--;
                    SmoveVertical =userInputCoord.charAt(1)+""; /* Get vertical value and cast  it to string; */
                    moveVertical = mapCharToInt(SmoveVertical); /* Find the integer representation of the vertical value. */
                }
                /* Checks if current player wants to quit. */
                if(SmoveVertical.equals("q"))
                {
                    System.out.println(this.currPlayerToPlay.toUpperCase()+" player  withdraws.");
                    System.out.println(nextPlayer.toUpperCase()+" player wins.");
                    exit(1);                
                }
                if(moveHorizontal > 5 || moveHorizontal < 0 || moveVertical == -1)
                {
                    System.out.println("\nCoordinates given for the square you want to move, are"
                        + " out of bounds, horizontal position must be within the bounds of [1,6]\n "
                       + "and vertical position must be within the bounds of [a,f]!");
                }
            }while(moveHorizontal > 5 || moveHorizontal < 0 || moveVertical == -1); 

            /* Check coordinates of the square user wants to move to. */
            do
            {
                System.out.println(this.currPlayerToPlay.toUpperCase()+ " player which square you want to go to?");
                userInputCoord = keyboard.next();
                if(userInputCoord.toCharArray().length<2)/* If user gives input for horizontal position only! */
                {
                    targetHorizontal = -1;
                    targetVertical = -1;
                }
                else
                {
                    targetHorizontal =Integer.parseInt(String.valueOf(userInputCoord.charAt(0))); ; /* Get horizontal value and cast it to string, 
                    																					to be casted into an integer. */
                    targetHorizontal--;
                    StargetVertical =userInputCoord.charAt(1)+""; /* Get vertical value and cast  it to string; */
                    targetVertical = mapCharToInt(StargetVertical); /* Find the integer representation of the vertical value. */
                }
                if(targetHorizontal > 5 || targetHorizontal < 0 || targetVertical == -1)
                {
                    System.out.println("\nCoordinates given for the square you want to move to, are"
                        + " out of bounds, horizontal position must be within the bounds of [1,6]\n"
                        + "and vertical position must be within the bounds of [a,f]!");
                }
            }while(targetHorizontal > 5 || targetHorizontal < 0 || targetVertical == -1);
            return new int[] {moveHorizontal, moveVertical , targetHorizontal, targetVertical};
        }
        else if(this.currPlayerType.equals("ai"))
        {
            System.out.println("Computer will generate a move with the Alpha-Beta pruning algorithm.\n");
            EvaluateResult AIresult = new EvaluateResult();
            AIresult.setEvaluationBoardResult(Integer.MIN_VALUE);
            AIresult.setPlayerColor(currPlayerToPlay);
            AIresult = this.evaluatePosition(board, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, currPlayerToPlay, true, AIresult);
            return new int [] {AIresult.getMoveHorizontal(), AIresult.getMoveVertical(), AIresult.getTargetHorizontal(), AIresult.getTargetVertical()};
        }
        /* Else. */
        return null;
    }
    
    
    /**
     * Makes the pawn promotion, if there can be any promotion at all.
     * 
     * @param moveHorizontal The horizontal coordinate of the source square. 
     * @param moveVertical The vertical coordinate of the source square.
     * @param targetHorizontal The horizontal coordinate of the target square.
     * @param targetVertical The vertical coordinate of the target square.
     */
    private void promotePawn(int moveHorizontal, int moveVertical, int targetHorizontal, int targetVertical)
    {
        Scanner keyboard = new Scanner(System.in);
        Piece currPlayerPiece = this.board[moveHorizontal][moveVertical].getPiece();
        /* If a white pawn gets promoted. */
        if(currPlayerPiece.getType().equals("pawn") && targetHorizontal == 5)
        {
            /* If there are no dead pieces. */
            if(this.whitePlayer.getDeadPieces().isEmpty())
            {
                System.out.println("\n"+currPlayerToPlay.toUpperCase()+" player trying to promote your pawn...");
                System.out.println("You have no dead pieces, that pawn can't get promoted!");
                /* Swap characteristics. */
                this.swapCharacteristics(this.board, moveHorizontal, moveVertical, targetHorizontal, targetVertical, currPlayerPiece);
            }
            /* If there are dead pieces. */
            else
            {
                ArrayList<String> pawnChoices = new ArrayList<String>();
                for(int i=0; i<this.whitePlayer.getDeadPieces().size(); i++)
                {
                    /* The promoted piece must not be a pawn or king. */
                    if(this.whitePlayer.getDeadPieces().get(i).getType().equals("pawn")
                            || this.whitePlayer.getDeadPieces().get(i).getType().equals("king"))
                    {
                        continue;
                    }
                    pawnChoices.add(this.whitePlayer.getDeadPieces().get(i).getType());
                }

                /* If there are NO suitable pieces to promote the pawn to. */
                if(pawnChoices.isEmpty())
                {
                    System.out.println("\n"+currPlayerToPlay.toUpperCase()+" player trying to promote your pawn...");
                    System.out.println("Pawn can't be promoted, none of the dead pieces can be promoted!");
                    /* Swap characteristics. */
                    this.swapCharacteristics(this.board, moveHorizontal, moveVertical, targetHorizontal, targetVertical, currPlayerPiece);
                }
                /* If there are suitable pieces to promote the pawn to. */
                else
                {
                    String selection="";
                    /* AI chooses randomly the desired piece to promote to. */
                    if(this.currPlayerType.equals("ai"))
                    {
                        Random rand = new Random();
                        int randChoice = rand.nextInt((pawnChoices.size()-1) - 0 + 1) + 0; /* Random integer in range [0, pawnChoices.size-1]  */
                        selection = pawnChoices.get(randChoice);
                        System.out.println("Computer promotes the pawn to "+pawnChoices.get(randChoice));
                    }
                    /* Human chooses the desired piece to promote to. */
                    else if(this.currPlayerType.equals("human"))
                    {
                        /*Check if user gave a correct piece type. */
                        boolean correct = false;
                        while(!correct)
                        {
                            System.out.println("\n"+currPlayerToPlay.toUpperCase()+" player trying to promote your pawn...");
                            System.out.println("You can promote your pawn to: "+pawnChoices);
                            System.out.print("Make your selection: ");
                            selection = keyboard.next().toLowerCase();
                            for(int i=0; i<pawnChoices.size(); i++)
                            {
                                if(selection.equals(pawnChoices.get(i)))
                                {
                                    correct = true;
                                }
                            }
                            /* If wrong selection. */
                            if(!correct) System.out.println("Wrong choice!");
                        }
                    }
                    
                    /* Kill the pawn since it got promoted. 
                       Careful, that pawn has now the targets coordinates, because when a piece attacks an enemy piece  
                       it gets its coordinates immediately!! */
                    this.whitePlayer.killPiece("pawn", targetHorizontal, targetVertical);
                    /* Resurrect the selected piece.
                    this.whitePlayer.resurrectPiece(selection, targetHorizontal, targetVertical);
                    /* Remove the piece from the old position. 
                       The "dead" piece is now resurrected, and it will be in the alive pieces of the player, 
                       at the last size of the array. */
                    this.swapCharacteristics(this.board, moveHorizontal, moveVertical, targetHorizontal, targetVertical, this.whitePlayer.getAlivePieces().get(this.whitePlayer.getAlivePieces().size()-1));
                } 
            }
        }
        /* If a black pawn gets promoted. */
        else if(currPlayerPiece.getType().equals("pawn") && targetHorizontal == 0)
        {
            /* If there are no dead pieces. */
            if(this.blackPlayer.getDeadPieces().isEmpty())
            {
                System.out.println("\n"+currPlayerToPlay.toUpperCase()+" player trying to promote your pawn...");
                System.out.println("You have no dead pieces, that pawn can't get promoted!");
                /* Swap characteristics. */
                this.swapCharacteristics(this.board, moveHorizontal, moveVertical, targetHorizontal, targetVertical, currPlayerPiece);
                
            }
            /* If there are dead pieces. */
            else
            {
                ArrayList<String> pawnChoices = new ArrayList<String>();
                for(int i=0; i<this.blackPlayer.getDeadPieces().size(); i++)
                {
                    /* The promoted piece must not be a pawn or king. */
                    if(this.blackPlayer.getDeadPieces().get(i).getType().equals("pawn")
                            || this.blackPlayer.getDeadPieces().get(i).getType().equals("king"))
                    {
                        continue;
                    }
                    pawnChoices.add(this.blackPlayer.getDeadPieces().get(i).getType());
                }

                /* If there are NO suitable pieces to promote the pawn to. */
                if(pawnChoices.isEmpty())
                {
                    System.out.println("\n"+currPlayerToPlay.toUpperCase()+" player trying to promote your pawn...");
                    System.out.println("Pawn can't be promoted, none of the dead pieces can be promoted!");
                    /* Swap characteristics. */
                    this.swapCharacteristics(this.board, moveHorizontal, moveVertical, targetHorizontal, targetVertical, currPlayerPiece);
                }
                /* If there are suitable pieces to promote the pawn to. */
                else
                {
                    String selection="";
                    /* AI chooses randomly the desired piece to promote to. */
                    if(this.currPlayerType.equals("ai"))
                    {
                        Random rand = new Random();
                        int randChoice = rand.nextInt((pawnChoices.size()-1) - 0 + 1) + 0; /* Random integer in range [0, pawnChoices.size-1] */ 
                        selection = pawnChoices.get(randChoice);
                        System.out.println("Computer promotes the pawn to "+pawnChoices.get(randChoice));
                    }
                    /* Human chooses the desired piece to promote to. */
                    else if(this.currPlayerType.equals("human"))
                    {
                        /*Check if user gave a correct piece type. */
                        boolean correct = false;
                        while(!correct)
                        {
                            System.out.println("\n"+currPlayerToPlay.toUpperCase()+" player trying to promote your pawn...");
                            System.out.println("You can promote your pawn to: "+pawnChoices);
                            System.out.print("Make your selection: ");
                            selection = keyboard.next().toLowerCase();
                            for(int i=0; i<pawnChoices.size(); i++)
                            {
                                if(selection.equals(pawnChoices.get(i)))
                                {
                                    correct = true;
                                }
                            }
                            /* If wrong selection. */
                            if(!correct) System.out.println("Wrong choice!");
                        }
                    }

                    /* Kill the pawn since it got promoted.
                       Careful, that pawn has now the targets coordinates, because when a piece attacks an enemy piece 
                       it gets its coordinates immediately!! */
                    this.blackPlayer.killPiece("pawn", targetHorizontal, targetVertical);
                    /* Resurrect the selected piece.
                    this.blackPlayer.resurrectPiece(selection, targetHorizontal, targetVertical);
                    
                    /* Remove the piece from the old position.
                       The "dead" piece is now resurrected, and it will be in the alive pieces of the player,
                      at the last size of the array. */
                    this.swapCharacteristics(this.board, moveHorizontal, moveVertical, targetHorizontal, targetVertical, this.blackPlayer.getAlivePieces().get(this.blackPlayer.getAlivePieces().size()-1));
                }
            }
        }
        /* Since the swap of the characteristics is common in each case, we do it here, at the end of all cases. */
        else
        {            
            /* Swap characteristics. */
            this.swapCharacteristics(this.board, moveHorizontal, moveVertical, targetHorizontal, targetVertical, currPlayerPiece);
        }  
    }
    

   /**
    * Swaps characteristics of the two given pieces.
    * 
	 * @param board The board of the game.
	 * @param moveHorizontal The horizontal coordinate of the source square. 
     * @param moveVertical The vertical coordinate of the source square.
     * @param targetHorizontal The horizontal coordinate of the target square.
     * @param targetVertical The vertical coordinate of the target square.
	 * @param currPlayerPiece The source piece.
	 */
    private void swapCharacteristics(Node[][] board, int moveHorizontal, int moveVertical, int targetHorizontal, int targetVertical, Piece currPlayerPiece)
   {
       board[moveHorizontal][moveVertical].setPiece(null);
       board[moveHorizontal][moveVertical].setPieceColor(null);
       board[targetHorizontal][targetVertical].setPiece(currPlayerPiece);
       board[targetHorizontal][targetVertical].setPieceColor(this.currPlayerToPlay);
   }
   
    
    /**
     * Finds out if a king is blocked or is the only piece alive from the player.
     * 
     * @param player The  with the king.
     * @return 1 if king is blocked, 2 if king is the only pawn else 0.  
     */
    private int kingSearch(Player player)
    {
        int horizontal=-1;
        int vertical=-1;
        String palyerColor = player.getPlayerColor();
        for(int i=0; i<player.getAlivePieces().size();i++)
        {
            if("king".equals(player.getAlivePieces().get(i).getType()))
            {
                horizontal = player.getAlivePieces().get(i).getHorizontal();
                vertical = player.getAlivePieces().get(i).getVertical();
            }
        }
        if(horizontal == -1 || vertical == -1)
        {
            System.out.println("King not found in Chess.SearchKing()!\nProgram exiting...");
            exit(1);
        }
        /* Check if king is the only piece alive.
           If there is only 1 piece left, it has to be the king
           so we do not check if that piece is the king! */
        if(player.getAlivePieces().size() == 1)
        {
            return 2;
        }
        
        /* Check if king is blocked.
           First we check if the king is in one of the 4 corners, next we check if the king is in one of
           the 4 "wall" rows/columns and last if he is in any other position.
           During the checks, each square we check, we check if it has a piece, and if there is a piece in all the squares, 
           then we check for the piece color. If the colors of all the pieces we check are different from the king's color
           then the king is blocked. */
        if(horizontal == 5)
        {
            if(vertical == 0)
            {
                if(board[5][1].getPiece()!=null && board[4][1].getPiece()!=null && board[4][0].getPiece()!=null)
                {
                    if(!board[5][1].getPieceColor().equals(palyerColor) && !board[4][1].getPieceColor().equals(palyerColor)
                            && !board[4][0].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
            else if(vertical == 5)
            {
                if(board[5][4].getPiece()!=null && board[4][4].getPiece()!=null && board[4][5].getPiece()!=null)
                {
                    if(!board[5][4].getPieceColor().equals(palyerColor) && !board[4][4].getPieceColor().equals(palyerColor)
                            && !board[4][5].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
            else
            {
                if(board[5][vertical+1].getPiece()!=null && board[5][vertical-1].getPiece()!=null 
                        && board[4][vertical-1].getPiece()!=null && board[4][vertical].getPiece()!=null && board[4][vertical-1].getPiece()!=null)
                {
                    if(!board[5][vertical+1].getPieceColor().equals(palyerColor) && !board[5][vertical-1].getPieceColor().equals(palyerColor)
                            && !board[4][vertical-1].getPieceColor().equals(palyerColor) && !board[4][vertical].getPieceColor().equals(palyerColor)
                            && !board[4][vertical-1].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
        }
        else if(horizontal == 0)
        {
            if(vertical == 0)
            {
                if(board[0][1].getPiece()!=null && board[1][1].getPiece()!=null && board[1][0].getPiece()!=null)
                {
                    if(!board[0][1].getPieceColor().equals(palyerColor) && !board[1][1].getPieceColor().equals(palyerColor)
                            && !board[1][0].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
            else if(vertical == 5)
            {
                if(board[0][4].getPiece()!=null && board[1][4].getPiece()!=null && board[1][5].getPiece()!=null)
                {
                    if(!board[0][4].getPieceColor().equals(palyerColor) && !board[1][4].getPieceColor().equals(palyerColor)
                            && !board[1][5].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
            else
            {
                if(board[0][vertical+1].getPiece()!=null && board[0][vertical-1].getPiece()!=null 
                        && board[1][vertical-1].getPiece()!=null && board[1][vertical].getPiece()!=null && board[1][vertical-1].getPiece()!=null)
                {
                    if(!board[0][vertical+1].getPieceColor().equals(palyerColor) && !board[0][vertical-1].getPieceColor().equals(palyerColor)
                            && !board[1][vertical-1].getPieceColor().equals(palyerColor) && !board[1][vertical].getPieceColor().equals(palyerColor)
                            && !board[1][vertical-1].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
        }
        else
        {
            if(vertical == 0)
            {
                if(board[horizontal+1][0].getPiece()!=null && board[horizontal-1][0].getPiece()!=null 
                        && board[horizontal+1][1].getPiece()!=null && board[horizontal][1].getPiece()!=null && board[horizontal-1][1].getPiece()!=null)
                {
                    if(!board[horizontal+1][0].getPieceColor().equals(palyerColor) && !board[horizontal-1][0].getPieceColor().equals(palyerColor)
                            && !board[horizontal+1][1].getPieceColor().equals(palyerColor) && !board[horizontal][1].getPieceColor().equals(palyerColor)
                            && !board[horizontal-1][1].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
            else if(vertical == 5)
            {
                if(board[horizontal+1][5].getPiece()!=null && board[horizontal-1][5].getPiece()!=null 
                        && board[horizontal+1][4].getPiece()!=null && board[horizontal][4].getPiece()!=null && board[horizontal-1][4].getPiece()!=null)
                {
                    if(!board[horizontal+1][5].getPieceColor().equals(palyerColor) && !board[horizontal-1][5].getPieceColor().equals(palyerColor)
                            && !board[horizontal+1][4].getPieceColor().equals(palyerColor) && !board[horizontal][4].getPieceColor().equals(palyerColor)
                            && !board[horizontal-1][4].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
            else
            {
                if(board[horizontal+1][vertical-1].getPiece()!=null && board[horizontal+1][vertical].getPiece()!=null && board[horizontal+1][vertical+1].getPiece()!=null
                        && board[horizontal-1][vertical-1].getPiece()!=null && board[horizontal-1][vertical].getPiece()!=null && board[horizontal-1][vertical+1].getPiece()!=null
                        && board[horizontal][vertical-1].getPiece()!=null && board[horizontal][vertical+1].getPiece()!=null)
                {
                    if(!board[horizontal+1][vertical-1].getPieceColor().equals(palyerColor) && !board[horizontal+1][vertical].getPieceColor().equals(palyerColor)
                            && !board[horizontal+1][vertical+1].getPieceColor().equals(palyerColor) && !board[horizontal-1][vertical-1].getPieceColor().equals(palyerColor)
                            && !board[horizontal-1][vertical].getPieceColor().equals(palyerColor) && !board[horizontal-1][vertical+1].getPieceColor().equals(palyerColor)
                            && !board[horizontal][vertical-1].getPieceColor().equals(palyerColor) && !board[horizontal][vertical+1].getPieceColor().equals(palyerColor))
                    {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
    

    /**
     * Maps the character given to a specific integer.
     * 
     * @param input The given character.
     * @return THe integer representation of the character.
     */
    private int mapCharToInt(String input)
    {
        switch(input)
        {
            case "a": return 0;
            case "b": return 1;
            case "c": return 2;
            case "d": return 3;
            case "e": return 4;
            case "f": return 5;
        }
        return -1;
    }
    

    /**
     * Maps the integer given to a specific character.
     * 
     * @param input The given integer.
     * @return The character representation of the integer.
     */
    private String mapIntToChar(int input)
    {
        switch(input)
        {
            case 0: return "a";
            case 1: return "b";
            case 2: return "c";
            case 3: return "d";
            case 4: return "e";
            case 5: return "f";
        }
        return null;
    }
    
    
    /**
     * Sets the number of the next round.
     * 
     * @param roundNum The round number.
     */
    public void setRoundNum(int roundNum)
    {
        this.roundNum = roundNum;
    }
    
    
    /**
     * Returns the black player.
     * 
     * @return The black player.
     */
    public Player getBlackPlayer()
    {
        return this.blackPlayer;
    }
    
    
    /**
     * Returns the white player.
     * 
     * @return The white player.
     */
    public Player getWhitePlayer()
    {
        return this.whitePlayer;
    }
    
    
    /**
     * Returns the board.
     * 
     * @return The board.
     */
    public Node[][] getBoard()
    {
        return this.board;
    }
    
    
    /**
     * Returns number of current round.
     * 
     * @return The number of the current round.
     */
    public int getRoundNum()
    {
        return this.roundNum;
    }
}