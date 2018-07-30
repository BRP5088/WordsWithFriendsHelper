import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Brett Patterson on 7/1/2018.
 */
public class Game {

    private Board board;
    private Move move;
    private LetterRack letterRack;
    private HashSet dictionary;
    public String letters [];
    public String player1Name;
    public String player2Name;

    String BLACK = "\u001B[30m";  //to see the console in colors
    String RED = "\u001B[31m";  //to see the console in colors
    String GREEN = "\u001B[32m"; //to see the console in colors
    String YELLOW = "\u001B[33m"; //to see the console in colors
    String BLUE = "\u001B[34m"; //to see the console in colors
    String MAGENTA = "\u001B[35m"; //to see the console in colors
    String CYAN = "\u001B[36m"; //to see the console in colors
    String WHITE = "\u001B[37m"; //to see the console in colors
    String RESET = "\u001B[0m"; //to see the console in colors


    String BGBLACK = "\u001B[40";  //to see the console in colors
    String BGRED = "\u001B[41";  //to see the console in colors
    String BGGREEN = "\u001B[42"; //to see the console in colors
    String BGYELLOW = "\u001B[43"; //to see the console in colors
    String BGBLUE = "\u001B[44"; //to see the console in colors
    String BGMAGENTA = "\u001B[45"; //to see the console in colors
    String BGCYAN = "\u001B[46"; //to see the console in colors
    String BGWHITE = "\u001B[47"; //to see the console in colors

    String BBGBLACK = "\u001B[40;1m";  //to see the console in colors
    String BBGRED = "\u001B[41;1m";  //to see the console in colors
    String BBGGREEN = "\u001B[42;1m"; //to see the console in colors
    String BBGYELLOW = "\u001B[43;1m"; //to see the console in colors
    String BBGBLUE = "\u001B[44;1m"; //to see the console in colors
    String BBGMAGENTA = "\u001B[45;1m"; //to see the console in colors
    String BBGCYAN = "\u001B[46;1m"; //to see the console in colors
    String BBGWHITE = "\u001B[47;1m"; //to see the console in colors

    String BOLD = "\u001B[1m";  //to see the console in colors
    String UNDERLINE = "\u001B[4m";  //to see the console in colors
    String REVERSED = "\u001B[7m"; //to see the console in colors


    public Game() throws FileNotFoundException {
        board = new Board();
        move = new Move( board );
        letterRack = new LetterRack();
        dictionary = letterRack.getDictionary();
    }

    public void passFileName( String path) throws FileNotFoundException {
        move.preGameMoves( board, path );
        setLetters( move.getLetters() );
        board.setPlayer1Name( move.getPlayer1Name() );
        board.setPlayer2Name( move.getPlayer2Name() );
    }

    public Board getBoard(){
        return board;
    }

    public void setLetters( String letters){
        this.letters = new String[ letters.length() ];

        for( int n = 0; n < letters.length(); n++){
            this.letters[n] = letters.substring(n, n + 1);
        }
    }


    public ArrayList<String> generateCombos( String letters[] ){  // 7 letter total: 13692
        ArrayList<String> possibleCombos = new ArrayList<>();

        for( int s = 0; s < letters.length; s++ ) {

            for (int e = 0; e < letters.length; e++) { //should be: 42
                if( s != e ) {
                    possibleCombos.add(letters[s] + letters[e] );
                }

                for (int three = 0; three < letters.length; three++) { //should be: 210
                    if( s != e && e != three && s != three ) {
                        possibleCombos.add(letters[s] + letters[e] + letters[three]);
                    }

                    for (int four = 0; four < letters.length; four++) { //should be: 840
                        if( s != e && e != three && s != three && s != four && e != four && three != four) {
                            possibleCombos.add(letters[s] + letters[e] + letters[three] + letters[four]);
                        }

                        for( int five = 0; five < letters.length; five++){ //should be: 2520
                            if( s != e && e != three && s != three && s != four && e != four && three != four && s != five && e != five && three != five && four != five ) {
                                possibleCombos.add(letters[s] + letters[e] + letters[three] + letters[four] + letters[five]);
                            }

                            for( int six = 0; six < letters.length; six++){ //should be: 5040
                                if( s != e && e != three && s != three && s != four && e != four && three != four && s != five && e != five && three != five && four != five && s != six && e != six && three != six && four != six && five != six) {
                                    possibleCombos.add(letters[s] + letters[e] + letters[three] + letters[four] + letters[five] + letters[six] );
                                }

                                for( int seven = 0; seven < letters.length; seven++){ //should be: 5040
                                    if( s != e && e != three && s != three && s != four && e != four && three != four && s != five && e != five && three != five && four != five && s != six && e != six && three != six && four != six && five != six &&
                                            s != seven && e != seven && three != seven && four != seven && five != seven && six != seven ) {
                                        possibleCombos.add(letters[s] + letters[e] + letters[three] + letters[four] + letters[five] + letters[six] + letters[seven]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return possibleCombos;
    }

    public void filterIllegalWords( ArrayList<String> boardWordLst, ArrayList<String> wordComboLst ){

        for( int wcw = 0; wcw < wordComboLst.size(); wcw++) {
            String tmp = wordComboLst.get( wcw );
            String word = "";

            int nRow = Integer.parseInt( tmp.substring( tmp.indexOf( "(" ) + 1 , tmp.indexOf( "," ) ) );
            int nCol = Integer.parseInt( tmp.substring( tmp.indexOf( "," ) + 1 , tmp.indexOf( ")" ) ) );
            String dir = tmp.substring( tmp.indexOf( "|" ) + 1 , tmp.indexOf( "|" ) + 2 );

            word += tmp.substring( 0, tmp.indexOf( "(" ) );
            word += tmp.substring( tmp.indexOf( ")" ) + 1, tmp.indexOf("|") );
            word += tmp.substring( tmp.indexOf( "|" ) + 2 );

            System.out.println( BLUE + "OG Cords: (" + nRow +"," + nCol + ")" +RESET);


            if( dir.equals( "D" ) ) {
                if (!tmp.substring(0, tmp.indexOf("(")).equals("")) {
                    nRow -= tmp.substring(0, tmp.indexOf("(")).length();
                }
            }

            if( dir.equals( "L" ) ) {
                if (!tmp.substring(0, tmp.indexOf("(") ).equals( "" ) ) {
                    nCol -= tmp.substring(0, tmp.indexOf( "(" ) ).length();
                }
            }

            System.out.println( BLUE + "Cords: (" + nRow +"," + nCol + ")" +RESET);

            if( dir.equals("D") ) {
                System.out.println("Down");
                System.out.println( nRow + word.length() - 1 );
            }
            else {
                System.out.println("Left");
                System.out.println( nCol + word.length() - 1 );
            }

            System.out.println( BLUE+ "Word: " + word + " length: " + word.length() + RESET);

            if( dir.equals( "D" ) ) {
                if ( 0 < nRow + word.length() - 1 && nRow + word.length() - 1 < board.getBoard()[0].length) {
                    if( dictionary.contains( board.getBoard()[nRow][nCol - 1].getLetter() + word ) ) {

                        int newLetters = 0;

                        for( String w : boardWordLst ){
                            System.out.println( w );
                            if( word.contains( w ) ){
                                System.out.println( GREEN + " asdasd" + RESET );
//                                String wordTemp = word;
//                                wordTemp.replace( w, "" );
//                                System.out.println( "WordTEMP " + wordTemp );
                            }
                        }

                        System.exit( 1001 );
                        System.out.println(GREEN + "going DOWN and works, word:" + word + RESET);
                    }
                    else{
                        System.out.println( RED + "going Down, word: " + board.getBoard()[nRow][nCol - 1].getLetter() + word + " , is not in the dictionary" + RESET );
                    }
//                    System.out.println(GREEN + "going DOWN and works, word:" + word + RESET);
                }
                else{
                    System.out.println(RED + "going DOWN : DELETE word: " + word + RESET);
                }
            }

            if( dir.equals( "L" ) ) {
                if (0 < nCol + word.length() - 1 && nCol + word.length() - 1 < board.getBoard().length) {

                    if( dictionary.contains( board.getBoard()[nRow - 1][nCol].getLetter() + word ) ) {

                        System.out.println(GREEN + "going LEFT and works, word:" + word + RESET);
                    }
                    else{
                        System.out.println( RED + "going LEFT, word: " + board.getBoard()[nRow - 1][nCol].getLetter() + word + " , is not in the dictionary" + RESET );
                    }
                }
                else{
                    System.out.println(RED + "going LEFT : DELETE word: " + word + RESET);
                }
            }

//            System.out.println("nRow: " + nRow );
//            System.out.println("nCol: " + nCol );
//            System.out.println("DIR: " + dir );
//            System.out.println("Word: " + word );




            System.out.println("____________");


//            for (int row = 0; row < board.getBoard().length; row++) {
//                for (int col = 0; col < board.getBoard()[0].length; col++) {
//
//                }
//            }
        }


    }


    public void findPossibleWords() {
        ArrayList<String> possibleCombos = new ArrayList<>();
        String letters [] = move.getLetters().split("");


        if( letters.length > 7 ){
            System.out.println( RED + "You have too many letters in the letter rack.");
            System.out.println( BLUE + "Length: " + letters.length + RESET );
            System.exit( 5 );
        }

        if( move.getLetters().contains("*") ){
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

            String nonBlank = "";

            int numberOfBlanks = 0;
            for( int x = 0; x < letters.length; x++){
                if( letters[x].equals("*") ){
                    numberOfBlanks++;
                }
                else{
                    nonBlank += letters[x];
                }
            }

            if( numberOfBlanks > 2 ){
                System.out.println( RED + "You have too many blank titles in the letter rack.");
                System.out.println( BLUE + "Number: " + numberOfBlanks + RESET );
                System.exit( 5 );
            }

            ArrayList<String> combos = new ArrayList<>();

            for( int blank1 = 0; blank1 < alphabet.length(); blank1++ ) {

                if( numberOfBlanks == 1){
                    combos.add( nonBlank + alphabet.substring(blank1, blank1 + 1) );
                }
                else {
                    for (int blank2 = 0; blank2 < alphabet.length(); blank2++) {
                        combos.add( nonBlank + alphabet.substring(blank1, blank1 + 1) + alphabet.substring(blank2, blank2 + 1) );
                    }
                }
            }

            for( String el : combos ){
                possibleCombos.addAll( generateCombos( el.split("") ) );
            }
        }
        else{
            possibleCombos = generateCombos( letters );
        }

        ArrayList<String> found = new ArrayList<>();

        for( int x = 0; x < possibleCombos.size(); x++ ){
            if( dictionary.contains( possibleCombos.get( x ) ) && !found.contains( possibleCombos.get( x ) ) ){
                found.add( possibleCombos.get( x ) );
            }
        }



        ArrayList<String> boardComboLst = new ArrayList<>();
        ArrayList<String> boardWords = new ArrayList<>( move.getBoardWords( board, true ) );
//        System.out.println( boardWords );

        for( int v = 0; v < letters.length; v++){
            possibleCombos.add( letters[v] );
        }

        for( int x = 0; x < possibleCombos.size(); x++ ){
            for( int bw = 0; bw < boardWords.size(); bw++) {

                String tmp = boardWords.get( bw );
                tmp = tmp.substring( tmp.indexOf(")") + 1, tmp.indexOf( "|" ) );

                if ( dictionary.contains( possibleCombos.get( x ) + tmp ) && !found.contains( possibleCombos.get( x ) + tmp ) ){
                    boardComboLst.add( possibleCombos.get( x ) + boardWords.get( bw ) );
                }

                if ( dictionary.contains( tmp + possibleCombos.get( x ) ) && !found.contains( tmp + possibleCombos.get( x ) ) ){
                    boardComboLst.add( boardWords.get( bw ) + possibleCombos.get( x ) );
                }

//                if ( dictionary.contains( possibleCombos.get( x )+ tmp + possibleCombos.get( x ) ) && !found.contains( possibleCombos.get( x ) + tmp + possibleCombos.get( x ) ) ){
//                    boardComboLst.add( possibleCombos.get( x ) + boardWords.get( bw ) + possibleCombos.get( x ) );
//                }
                //TODO
                /*
                    add the functionality to have different combos of before and after.
                 */
            }
        }

//        System.out.println("size is: " + boardComboLst.size() );

        filterIllegalWords( boardWords, boardComboLst );

//        for(String l : boardComboLst){
//            System.out.println( l );
//        }

    }
}
