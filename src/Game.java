import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Brett Patterson on 7/1/2018.
 */
public class Game extends utilityCode{

    private Board board;
    private Move move;
    public LetterRack letterRack;
    private HashMap dictionary;
    public String letters [];
    public String player1Name;
    public String player2Name;


    public Game() throws FileNotFoundException {
        board = new Board();
        letterRack = new LetterRack();
        dictionary = letterRack.getDictionary();

        move = new Move( board, dictionary );
        letterRack.setRack( move.getLetters().split("") );
    }

    public void passFileName( String path ) throws FileNotFoundException {
        move.preGameMoves( board, path );
        setLetters( move.getLetters() );
        board.setPlayer1Name( move.getPlayer1Name() );
        board.setPlayer2Name( move.getPlayer2Name() );
    }

    public Board getBoard(){
        return board;
    }

    public void setLetters( String letters ){
        this.letters = new String[ letters.length() ];

        for( int n = 0; n < letters.length(); n++){
            this.letters[n] = letters.substring(n, n + 1);
        }
    }

    public String getLetters() {
        String tmp = "";
        for( String l: letters){
            tmp += l;
        }

        return tmp;
    }

    public ArrayList<String> generateCombos(String letters[] ){  // 7 letter total: 13692
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

                                        possibleCombos.add(letters[s] + letters[e] + letters[three] + letters[four] + letters[five] + letters[six] + letters[seven] );
                                    }

                                    for( int eight = 0; eight < letters.length; eight++){ //should be: ???? big number
                                        if( s != e && e != three && s != three && s != four && e != four && three != four && s != five && e != five && three != five && four != five && s != six && e != six && three != six && four != six && five != six &&
                                                s != seven && e != seven && three != seven && four != seven && five != seven && six != seven && s != eight && e != eight && three != eight && four != eight && five != eight && six != eight && seven != eight) {

                                            possibleCombos.add(letters[s] + letters[e] + letters[three] + letters[four] + letters[five] + letters[six] + letters[seven] + letters[eight]);
                                        }
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


    public boolean legalMove( Board b , String word, String direction, int row, int col){

        ArrayList<String> boardLst = new ArrayList<>( move.getBoardWords( b, true ) );



        for( int n = 0; n < boardLst.size(); n++ ){

            if( !dictionaryContains( dictionary, boardLst.get( n ).substring( boardLst.get( n ).indexOf(")") + 1, boardLst.get( n ).indexOf("|") ) ) ){
                return false;
            }

//            if( !dictionary.contains( boardLst.get( n ).substring( boardLst.get( n ).indexOf(")") + 1, boardLst.get( n ).indexOf("|") ) ) ){
//                return false;
//            }
        }

        return true;
    }


    public HashMap<String, HashSet<String> > getDictionary() {
        return dictionary;
    }

    public void filterIllegalWords(Board b, ArrayList<String> boardWordLst, ArrayList<String> wordComboLst ){

        Board boardLst [] = new Board[ wordComboLst.size() ];
        ArrayList<String> deleteLst  = new ArrayList<>();

        for( int bp = 0; bp < wordComboLst.size(); bp++){
            boardLst[bp] = new Board();
            boardLst[bp].copyBoard( b );
        }

        for( int wcw = 0; wcw < wordComboLst.size(); wcw++) {
            String tmp = wordComboLst.get(wcw);
            String word = "";

            int nRow = Integer.parseInt(tmp.substring(tmp.indexOf("(") + 1, tmp.indexOf(",")));
            int nCol = Integer.parseInt(tmp.substring(tmp.indexOf(",") + 1, tmp.indexOf(")")));
            String dir = tmp.substring(tmp.indexOf("|") + 1, tmp.indexOf("|") + 2);

            word += tmp.substring(0, tmp.indexOf("("));
            word += tmp.substring(tmp.indexOf(")") + 1, tmp.indexOf("|"));
            word += tmp.substring(tmp.indexOf("|") + 2);


            System.out.println( "here is the dir: " + dir);


            if (dir.equals("D") ) {
                if (!tmp.substring(0, tmp.indexOf("(")).equals("")) {
                    nRow -= tmp.substring(0, tmp.indexOf("(")).length();
                }
            }

            if( dir.equals( "L" ) ) {
                if ( !tmp.substring(0, tmp.indexOf("(") ).equals( "" ) ) {
                    nCol -= tmp.substring(0, tmp.indexOf( "(" ) ).length();
                }
                System.out.println( BLUE + "OG Cords: (" + nRow + "," + nCol + ")" + RESET);

            }

            if (dir.equals("L")) {

                System.out.println( BLUE + "Cords: (" + nRow + "," + nCol + ")" + RESET);

//                if ( dir.equals("D") ) {
//                    System.out.println("Down");
//                    System.out.println(nRow + word.length() - 1);
//                }

                System.out.println(BLUE + "Word: " + word + " length: " + word.length() + RESET);

//                if ( dir.equals("D") ) {
//
//                    if ( 0 < nRow && nRow + word.length() < boardLst[wcw].getBoard()[0].length) { // checks the bounds
//
//                        if ( dictionary.contains(boardLst[wcw].getBoard()[nRow][nCol - 1].getLetter() + word ) ) { //see if the word is even in the dictionary
//
//                            move.addToBoard(boardLst[wcw], Node.playerID.player1, word, dir, nRow, nCol);
//
//                            if( !legalMove(boardLst[wcw], word, dir, nRow, nCol)) {
//                                deleteLst.add("(" + nRow + "," + nCol + ")" + word + "|" + dir);
//                                System.out.println(RED + "going Down, word: " + boardLst[wcw].getBoard()[nRow][nCol - 1].getLetter() + word + " , is not legal move" + RESET);
//                                continue;
//                            }
//                            else{
//                                System.out.println(GREEN + "going DOWN and works, word:" + word + RESET);
//                            }
//                        }
//                        else{
//                            System.out.println(RED + "going Down, word: " + boardLst[wcw].getBoard()[nRow][nCol - 1].getLetter() + word + " , is not in the dictionary" + RESET);
//                            continue;
//                        }
////                    System.out.println(GREEN + "going DOWN and works, word:" + word + RESET);
//
//                        move.addToBoard( boardLst[wcw] , Node.playerID.player1, word, dir, nRow, nCol);
//                        boardLst[wcw].displayBoard();
//                    }
//                    else {
//                        System.out.println(RED + "going DOWN : DELETE word: " + word + " , out of bounds" + RESET);
//                        deleteLst.add( word );
//                    }
//                }


                //________________________________________________________


                if ( dir.equals("L") ) {

                    if ( 0 < nRow && nRow + word.length() < boardLst[wcw].getBoard()[0].length) { // checks the bounds

                        if ( dictionaryContains(dictionary, boardLst[wcw].getBoard()[nRow][nCol].getLetter() + word ) ) { //see if the word is even in the dictionary

                            move.addToBoard( boardLst[wcw], Node.playerID.player1, word, dir, nRow, nCol);

                            if( !legalMove( boardLst[wcw], word, dir, nRow, nCol) ) {
                                deleteLst.add("(" + nRow + "," + nCol + ")" + word + "|" + dir);
                                System.out.println(RED + "going left, word: " + boardLst[wcw].getBoard()[nRow][nCol - 1].getLetter() + word + " , is not legal move" + RESET);
                                continue;
                            }
                            else{
                                System.out.println(GREEN + "going left and works, word:" + word + RESET);
                            }
                        }
                        else{
                            System.out.println(RED + "going left, word: " + boardLst[wcw].getBoard()[nRow][nCol - 1].getLetter() + word + " , is not in the dictionary" + RESET);
                            continue;
                        }
//                    System.out.println(GREEN + "going left and works, word:" + word + RESET);

                        move.addToBoard( boardLst[wcw] , Node.playerID.player1, word, dir, nRow, nCol);
                        boardLst[wcw].displayBoard();
                    }
                    else {
                        System.out.println(RED + "going left : DELETE word: " + word + " , out of bounds" + RESET);
                        deleteLst.add( word );
                    }
                }
            }

            System.out.println("____________");
        }
    }


    public ArrayList<String> getBoardSingleLetters(Board b){
        ArrayList lettersLst = new ArrayList();

        for( int r = 0; r < b.getBoard().length; r++) {
            for (int c = 0; c < b.getBoard()[0].length; c++) {

                if( !b.getNode(r, c).getLetter().equals( "" ) ) {

                    lettersLst.add( b.getNode(r, c ).getLetter() );

                }
            }
        }

        return lettersLst;

    }


    public void findPossibleWords(Board b ) {
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
            if( dictionaryContains( dictionary, possibleCombos.get( x ) ) && !found.contains( possibleCombos.get( x ) ) ){
                found.add( possibleCombos.get( x ) );
            }
        }

        ArrayList<String> boardComboLst = new ArrayList<>();
        ArrayList<String> allTheLetters = new ArrayList<>( getBoardSingleLetters( b ) );
        ArrayList<String> boardWords = new ArrayList<>( move.getBoardWords( b, true ) );
//        System.out.println( boardWords );


        possibleCombos.addAll( Arrays.asList(letters) );
        possibleCombos.addAll( allTheLetters );

        boardWords.addAll( getBoardSingleLetters( b ) );


        for( int x = 0; x < possibleCombos.size(); x++ ){
            for( int bw = 0; bw < boardWords.size(); bw++) {

                String tmp = boardWords.get( bw );
                if( tmp.contains( "(") ) {
                    tmp = tmp.substring(tmp.indexOf(")") + 1, tmp.indexOf("|"));
                }

//                System.out.println( tmp + possibleCombos.get( x ) );
                if ( dictionaryContains(dictionary, possibleCombos.get( x ) + tmp ) && !found.contains( possibleCombos.get( x ) + tmp ) ){
                    boardComboLst.add( possibleCombos.get( x ) + boardWords.get( bw ) );
                }

//                System.out.println( tmp + possibleCombos.get( x ) );
                if ( dictionaryContains(dictionary, tmp + possibleCombos.get( x ) ) && !found.contains( tmp + possibleCombos.get( x ) ) ){
                    boardComboLst.add( boardWords.get( bw ) + possibleCombos.get( x ) );
                }

//                System.out.println( possibleCombos.get( x )+ tmp + possibleCombos.get( x ) );
                if ( dictionaryContains(dictionary, possibleCombos.get( x )+ tmp + possibleCombos.get( x ) ) && !found.contains( possibleCombos.get( x ) + tmp + possibleCombos.get( x ) ) ){
                    boardComboLst.add( possibleCombos.get( x ) + boardWords.get( bw ) + possibleCombos.get( x ) );
                }
                //TODO

//                System.out.println("________________________________________");
//                System.out.println( "X: " + x + " , out of: " + possibleCombos.size() );
                if( x % 1000 == 0){
                    float total = x;
                    total /= possibleCombos.size();
                    total *= 100;
                    System.out.println( total +"%" );
                }

//                System.out.println( total );
                for( int p = 0; p < possibleCombos.size(); p++){

                    String p1c = possibleCombos.get( p );
                    if( p1c.contains( "(") ) {
                        p1c = p1c.substring( p1c.indexOf(")") + 1, p1c.indexOf("|"));
                    }


                    if( p != x) {
//                    System.out.println( p1c + tmp + possibleCombos.get( x ) );
                        if (dictionaryContains(dictionary, p1c + tmp + possibleCombos.get(x)) && !found.contains(p1c + tmp + possibleCombos.get(x))) {
                            boardComboLst.add(p1c + tmp + possibleCombos.get(x));
                        }
                    }

                }
                /*
                    add the functionality to have different combos of before and after.
                 */
            }
        }
        System.out.println( boardComboLst );

//        System.out.println("size is: " + boardComboLst.size() );

        for( String w : boardComboLst ) {


            String word = w.substring(0, w.indexOf("("));
            word += w.substring( w.indexOf(")") + 1, w.indexOf("|"));
            word += w.substring( w.indexOf("|") + 2);

            System.out.println( word );
//            System.out.println( w );
//            System.out.println("___________");
        }

//        filterIllegalWords( b, boardWords, boardComboLst );

//        for(String l : boardComboLst){
//            System.out.println( l );
//        }

    }
}
