import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	// 35 points if all the tiles are used a single round


	public static void testMethod() throws FileNotFoundException {

		Game g = new Game();
		Board b = g.getBoard();
		g.passFileName( "src\\Games\\IllegalMoveTest.txt");

//		String [] l = new String[] {"X", "*", "Y", "G", "Y", "K" };

//		ArrayList comboLst = g.findPossibleWords( b );
//		System.out.println( comboLst );
//		System.out.println( comboLst.size() );


		//_____________________________________________________

//		System.out.println( "has key: " + g.getDictionary().containsKey( g.sort("act") ) );
//		System.out.println( g.getDictionary().size() );
//
//		Set keys = g.getDictionary().keySet();
//		ArrayList keepLst = new ArrayList();
//
//		for( Object k : keys ){
//			HashSet set = (HashSet) g.getDictionary().get( k );
//
//			if( set.size() > 1 ){
//				keepLst.add( k );
////				System.out.println("key: " + k );
////				System.out.println("set: " + set );
////				System.out.println( "__________________" );
//			}
//		}
//
//		System.out.println( keepLst );
		//_____________________________________________________


//		System.out.println( g.dictionaryContains( g.getDictionary(), "cat" ) );
		//_____________________________________________________


//		System.out.println( "REFIT: "+  ( !g.legalMove( b, "REFIT","D", 10,6) ) );
		//_____________________________________________________


//		g.findPossibleWords( b );



		//_____________________________________________________




//
//		ArrayList<String> wordsOnBoard = new ArrayList( b.getWordLst() );
//
////		for( String word : wordsOnBoard ){
//		wordsOnBoard.addAll( g.getBoardSingleLetters( b ) );
//
//		String letterRack = "";
//
////		for( String l: g.letterRack.getRack() ){
////			letterRack += l;
////		}
//		System.out.println( g.letterRack.getRack() );
//
//		wordsOnBoard.add( letterRack );
//
//
//		System.out.println( wordsOnBoard );


//		System.exit( 102 );


		Move m = new Move( b, g.getDictionary() );

		String [] l = new String[] {"O", "R", "E", "N" /*, "P", "F", "K" */};
		ArrayList<String> wordsOnTheBoard = new ArrayList<>( m.getBoardWords(b, false) );
		ArrayList<String> possibleCombos = new ArrayList<>( g.generateCombos( l ) );
		ArrayList<String> found = new ArrayList<>();


		wordsOnTheBoard.addAll( g.getBoardSingleLetters( b ) );

		System.out.println("Before");
		System.out.println( possibleCombos.size() );


		System.out.println("Words that are on the board: " + wordsOnTheBoard);


		for( int word = 0; word < wordsOnTheBoard.size(); word++) {

			for (int pre = 0; pre < possibleCombos.size(); pre++) {

				if( g.dictionaryContains( g.getDictionary(), possibleCombos.get( pre ) + wordsOnTheBoard.get( word ) ) && !found.contains( possibleCombos.get( pre ) + wordsOnTheBoard.get( word ) )) {
					found.add( possibleCombos.get(pre) + wordsOnTheBoard.get(word) );
				}

				for (int suf = 0; suf < possibleCombos.size(); suf++) {
					if( pre != suf ) {
						if( g.dictionaryContains( g.getDictionary(), possibleCombos.get( pre ) + wordsOnTheBoard.get( word ) + possibleCombos.get( suf ) ) && !found.contains( possibleCombos.get( pre ) + wordsOnTheBoard.get( word ) + possibleCombos.get( suf ) ) ) {
							found.add( possibleCombos.get(pre) + wordsOnTheBoard.get(word) + possibleCombos.get(suf) );
						}
						if( g.dictionaryContains( g.getDictionary(), wordsOnTheBoard.get( word ) + possibleCombos.get( suf ) ) && !found.contains( wordsOnTheBoard.get( word ) + possibleCombos.get( suf ) ) ) {
							found.add( wordsOnTheBoard.get(word) + possibleCombos.get(suf) );
						}
					}
				}
			}
		}

		System.out.println( found );
		System.out.println( "total number of words in found: " + found.size() );


//		HashMap<String, HashSet<String> > map = new HashMap<>( g.getDictionary() );

//		for( int num = 0; num < possibleCombos.size(); num++ ){
//
//			if( map.containsKey( g.sort( possibleCombos.get( num ) ) ) ){
//
//				HashSet<String> set = map.get( g.sort( possibleCombos.get( num ) ) );
//
//				if( !set.contains( possibleCombos.get( num ) ) ) {
////				System.out.println( sett );
//
//				possibleCombos.remove( possibleCombos.get( num ) );
//				num--;
////				System.out.println("asd");
//				}
//			}
//			else{
//
//				System.out.println( possibleCombos.get( num ) );
//				possibleCombos.remove( possibleCombos.get( num ) );
//				num--;
//			}
//
//		}



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

		testMethod();
		System.exit( 1 );


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


		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("here is the actual board:");

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
