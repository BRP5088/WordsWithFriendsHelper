import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	    Board board = new Board();

		Node b [][] = board.getBoard();
		Move m = new Move( board );
//		m.preGameMoves( b );

//		System.out.println();
//		System.out.println();
//		System.out.println();



	    m.addToBoard(b, Node.playerID.player1,"CAT","L",3,0);
	    m.addToBoard(b, Node.playerID.player2,"DAT","D",2,1);

		System.out.println( "Player 1 pts: "+board.getPlayer1pts() );
		System.out.println( "Player 2 pts: "+board.getPlayer2pts() );

	    board.displayBoard();
    }
}
