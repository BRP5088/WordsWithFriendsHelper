import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	// 35 points if all the tiles are used a single round


	public static void testMethod() throws FileNotFoundException {

		Game g = new Game();
		Board b = g.getBoard();
		g.passFileName( "src\\Games\\IllegalMoveTest.txt");

		System.out.println("REFIT: "+  ( !g.legalMove( b, "REFIT","D", 12,6) ) );

		b.displayBoard();
	}



    public static void main(String[] args) throws FileNotFoundException {
		String RED = "\u001B[31m";
		String RESET = "\u001B[0m";
		String BLUE = "\u001B[34m";
		String BOLD = "\u001B[1m";

//		String p1 = RED+"|"+RESET;
//		String p2 = BLUE+"|"+RESET;

//		String d1 = p1 + BOLD+"Test"+RESET +p2+ RESET;



//		System.out.println( d1 );

//		testMethod();
//		System.exit( 1 );


		File folder = new File( "src\\Games");
		File fileList [] = folder.listFiles();


		if ( fileList == null) throw new AssertionError();

		int numOfGames = fileList.length;


		numOfGames = 1;

		Game games [] = new Game[ numOfGames ];

		for(int n = 0; n < numOfGames; n++){
			games[n] = new Game();
			games[n].passFileName( fileList[n].getPath() );
			games[n].findPossibleWords( games[n].getBoard() );
		}

		for( int n = 0; n < numOfGames; n++){
			System.out.println("Game: "+ ( n + 1 ) );
			games[n].getBoard().displayBoard();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("_________________________________");
		}


    }
}
