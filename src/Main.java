import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	// 25 points if all the tiles are used a single round


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


		File folder = new File( "src\\Games");
		File fileList [] = folder.listFiles();


		if ( fileList == null) throw new AssertionError();

		int numOfGames = fileList.length;


		numOfGames = 1;


		Board boards [] = new Board[ numOfGames ];
		Move m [] = new Move[numOfGames];

		for( int x = 0; x < numOfGames; x++){
			boards[x] = new Board();
			m[x] = new Move( boards[x] );
			m[x].preGameMoves( boards[x].getBoard(), fileList[x].getPath() );
		}

		System.out.println( RED + "*Making the dictionary*" + RESET );
		LetterRack lr = new LetterRack();
		System.out.println( RED + "*Finished making the dictionary*" + RESET );

		String dictionary [][] = lr.getDictionary();


		System.out.println();
		System.out.println();
		System.out.println();

		for( int n = 0; n < numOfGames; n++){
			System.out.println("Game: "+ (n+1) );
			boards[n].displayBoard();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("_________________________________");
		}
    }
}
