public class Main {

    public static void main(String[] args) {
	    Board board = new Board();
	    Move m = new Move();


	    m.AddToBoard(board.getBoard(), Node.playerID.player1,"CAT","L",7,6);
	    m.AddToBoard(board.getBoard(), Node.playerID.player2,"DAT","D",6,7);

	    board.displayBoard();
    }
}
