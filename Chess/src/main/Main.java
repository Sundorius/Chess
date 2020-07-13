package main;

import chess.Chess;
import java.util.Scanner;

/**
 * Main class.
 * This class starts the appropriate chess match according to user selection.
 * 
 * @author Sundorius
 */
public class Main
{
    public static void main(String [ ] args)
    {
        boolean moveResult = false;
        
        /* Gets user selection of the game. */
        int option = -1;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("This is a chess game simulator.\n");
        System.out.println("Options:\n1.Human vs Human\n2.Human vs AI\n3.AI vs AI");
        System.out.print("Option: ");
        option = keyboard.nextInt();
        while(option!=1 && option!=2 && option!=3)
        {
            System.out.println("Wrong option, please choose again!");
            System.out.println("\nOptions:\n1. Human vs Human\n2.Human vs AI\n3.AI vs AI");
            System.out.print("Option: ");
            option = keyboard.nextInt();
            
        }
        /* Creates a chess game, depending on the user selection. */
        Chess chess = new Chess(option);
        while(!moveResult)
        {
            /* If the game ends, the result will be true. */
            moveResult = chess.makeMove();
        }  
    }
}