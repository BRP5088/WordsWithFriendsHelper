import java.io.FileNotFoundException;
import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
		String RED = "\u001B[31m";
		String RESET = "\u001B[0m";
		String BLUE = "\u001B[34m";
		String BOLD = "\u001B[1m";

//		String p1 = RED+"|"+RESET;
//		String p2 = BLUE+"|"+RESET;

//		String d1 = p1 + BOLD+"Test"+RESET +p2+ RESET;



//		System.out.println( d1 );
//		System.exit( 1 );

		Board boards [] = new Board[2];








	    Board board = new Board();

		Node b [][] = board.getBoard();
		Move m = new Move( board );

		System.out.println( RED + "*Making the dictionary*" + RESET );
		LetterRack lr = new LetterRack();
		System.out.println( RED + "*Finished making the dictionary*" + RESET );


		String dictionary [][] = lr.getDictionary();



		m.preGameMoves( b );

//		System.out.println();
//		System.out.println();
//		System.out.println();



//	    m.addToBoard(b, Node.playerID.player1,"CAT","L",3,0);
//	    m.addToBoard(b, Node.playerID.player2,"DAT","D",2,1);


		System.out.println();
		System.out.println();
		System.out.println();
		System.out.print( "Player 1 pts: "+board.getPlayer1pts() );
		System.out.println( "    ||||||||||||||||||||| Player 2 pts: "+board.getPlayer2pts() );

	    board.displayBoard();

    }
}
