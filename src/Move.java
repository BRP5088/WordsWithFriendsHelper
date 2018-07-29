import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Brett Patterson on 6/20/2018.
 */
public class Move {

    private Board board;
    String RED = "\u001B[31m";  //to see the console in colors
    String GREEN = "\u001B[32m";
    String RESET = "\u001B[0m";
    private String letters;

    private String player1Name;
    private String player2Name;



//    private String letters = "";


    public Move(Board board ) {
        this.board = board;
        letters = "";
        player1Name = "";
        player2Name = "";
    }

    public void preGameMoves(Board b , String path) throws FileNotFoundException {

//        File f = new File( "src\\PreGameMoves.txt" );
        File f = new File( path );

        Scanner s = new Scanner( f );

        while( s.hasNext() ){
            String tmp = s.next();

            String arr [] = tmp.split(":");

            if( arr[0].contains("#") ){
                continue;
            }
            if( arr[0].equals("Player1") ) {
                if( player1Name.equals( "" ) ){
                    player1Name = arr[1];
                }
                else {
                    addToBoard(b, Node.playerID.player1, arr[1], arr[2], Integer.parseInt( arr[3] ), Integer.parseInt( arr[4] ) );
                }
            }
            if( arr[0].equals("Player2") ){
                if( player2Name.equals( "" ) ){
                    player2Name = arr[1];
                }
                else {
                    addToBoard(b, Node.playerID.player2, arr[1], arr[2], Integer.parseInt( arr[3] ), Integer.parseInt( arr[4] ) );
                }
            }
            if (arr[0].equals("LetterRack") ){
                letters = arr[1];
            }
        }
        s.close();
    }

    public void addToBoard(Board b , Node.playerID playerID, String word, String direction, int row, int col) {


        if( !this.board.getWordLst().contains( word ) ) {


            String RED = "\u001B[31m";  //to see the console in colors
            String BOLD = "\u001B[1m";  //to see the console in colors
            String UNDERLINE = "\u001B[4m";  //to see the console in colors
            String RESET = "\u001B[0m"; //to see the console in colors

            int nRow = row;
            int nCol = col;

            boolean blank = false;

            // --- Need to clean  up the string if it has a blank character ---
            String throwaway = word.substring(0);
            if (throwaway.contains("*")) {
                throwaway = throwaway.replace("*", "");
            }
            // --- end ---

            b.addToWordsLst("(" + row + "," + col + ")" + throwaway + "|" + direction);

            for (int i = 0; i < word.length(); i++) {
                if ( word.substring(i, i + 1).equals("*") ) {
                    blank = true;
                } else {

                    if (!b.getBoard()[nRow][nCol].isBeingUsed()) {
                        b.getBoard()[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), blank, row, col);
                    } else {
                        if (!b.getBoard()[nRow][nCol].getLetter().equals(word.substring(i, i + 1))) {

                            System.out.println(RED + BOLD + "TRYING TO OVERWRITE A LETTER! " + RESET);
                            System.out.println("Letter on the board: " + b.getBoard()[nRow][nCol]);
                            System.out.println("Letter trying to replace it with: " + word.substring(i, i + 1));
                            System.exit(3);
                        }

                        if( !this.board.getNode(nRow, nCol).isBeingUsed() ) {
                            b.getBoard()[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), blank, row, col);
                        }
                        else{
                            b.getBoard()[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), this.board.getNode(nRow, nCol).isBlankTile(), row, col);
                        }
                    }

                    if (direction.equals("L")) {
                        nCol++;
                    } else {
                        nRow++;
                    }
                    blank = false;
                }
            }

            if (word.contains("*")) {
                word = word.replace("*", "");
            }

            int total = getPlayerMovePts(b, playerID, word, direction, row, col);

            if (playerID == Node.playerID.player1) {
                this.board.setPlayer1pts(total + this.board.getPlayer1pts());
            } else {
                this.board.setPlayer2pts(total + this.board.getPlayer2pts());
            }
            String newWord = anyNewWords( b );

            if ( newWord.length() > 0) {
                String dir = "";

                if (newWord.substring(newWord.length() - 2).equals("L")) {
                    newWord = newWord.substring(0, newWord.length() - 2);
                    dir = "L";
                }
                else {
                    newWord = newWord.substring(0, newWord.length() - 2);
                    dir = "D";
                }

                int r = 0;
                int c = 0;

                r = Integer.parseInt(newWord.substring(1, newWord.indexOf(",")));
                newWord = newWord.substring(newWord.indexOf(",") + 1);

                c = Integer.parseInt(newWord.substring(0, newWord.indexOf(")")));
                newWord = newWord.substring(newWord.indexOf(")") + 1);

                addToBoard(b, playerID, newWord, dir, r, c);
            }

            int nR = row;
            int nC = col;

            for( int place = 0; place < word.length(); place++ ){
                this.board.setBeingUsed( nR, nC, playerID);
                if (direction.equals("L")) {
                    nC++;
                }
                else{
                    nR++;
                }
            }
        }
    }

    public int getPlayerMovePts(Board b, Node.playerID playerID, String word, String direction, int row, int col ) {
        int total = 0;
        int multiplier = 1;

        int nRow = row;
        int nCol = col;

        int numlst[] = new int[ word.length() ];

        HashMap<String, Integer> values = new HashMap<>( new Board().getValues() );

        for (int i = 0; i < word.length(); i++) {

            numlst[i] = values.get( word.substring(i, i + 1) );

            if ( !b.getBoard()[nRow][nCol].isBeingUsed() ) {

                if( b.getBoard()[nRow][nCol].getNodeType() == Node.tileType.TripleWordScore) {
                    multiplier *= 3;
                }
                if( b.getBoard()[nRow][nCol].getNodeType() == Node.tileType.DoubleWordScore) {
                    multiplier *= 2;
                }
                if( b.getBoard()[nRow][nCol].getNodeType() == Node.tileType.TripleLetterScore) {
                    numlst[i] *= 3;
                }
                if( b.getBoard()[nRow][nCol].getNodeType() == Node.tileType.DoubleLetterScore) {
                    numlst[i] *= 2;
                }
            }

            if( b.isNodeBlank( nRow, nCol) ) {
                numlst[i] = 0;
            }

            if( direction.equals("L") ){
                nCol++;
            }
            else{
                nRow++;
            }
        }

        total = IntStream.of(numlst).sum();

        if (multiplier != 1) {
            total *= multiplier;
        }

//        System.out.println( "the total is: " + total );

        return total;
    }

    public ArrayList<String> getBoardWords( Board b, boolean showExtraInfo ){
        ArrayList<String> boardLst = new ArrayList<>();

        // left to right
        for (int r = 0; r < b.getBoard().length; r++) {
            String tmp = "";
            String tmpStart = "";

            for (int c = 0; c < b.getBoard()[0].length; c++) {

                if( !this.board.getNode(r, c).getLetter().equals( "" ) ) {  //This takes account of left to right
                    if( tmp.length() == 0){
                        tmpStart +=  "("+r +"," +c+ ")";
                    }

                    tmp += board.getNode(r, c).getLetter();

                    if( c + 1 < b.getBoard()[0].length && this.board.getNode(r , c + 1).getLetter().equals( "" ) || tmp.length() > 1 && c + 1 == b.getBoard()[0].length ){
                        if( !tmp.equals("") && tmp.length() > 1) {
                            if( showExtraInfo ) {
                                boardLst.add( tmpStart + tmp + "|L" );
                            }
                            else{
                                boardLst.add( tmp );
                            }
                            tmp = "";
                            tmpStart = "";
                        }
                        else{
                            tmp = "";
                            tmpStart = "";
                        }
                    }
                }
            }
        }
        // up to down
        for (int c = 0; c < b.getBoard()[0].length; c++) {
            String tmmp = "";
            String tmmpStart = "";

            for (int r = 0; r < b.getBoard().length; r++) {
                if ( !this.board.getNode(r, c).getLetter().equals("") ) {  //This takes account of up to down
                    if ( tmmp.length() == 0 ) {
                        tmmpStart += "(" + r + "," + c + ")";
                    }

                    tmmp += board.getNode(r, c).getLetter();

                    if( r + 1 < b.getBoard().length && this.board.getNode(r + 1, c).getLetter().equals("") || tmmp.length() > 1 && r + 1 == b.getBoard().length ){

                        if ( !tmmp.equals("") && tmmp.length() > 1) {
                            if( showExtraInfo ){
                                boardLst.add(tmmpStart + tmmp + "|D");
                            }
                            else{
                                boardLst.add( tmmp );
                            }
                            tmmp = "";
                            tmmpStart = "";
                        }
                        else{
                            tmmp = "";
                            tmmpStart = "";
                        }
                    }
                }
            }
        }
        return boardLst;
    }

    public String anyNewWords( Board b) {

        ArrayList<String> boardLst = new ArrayList<>( getBoardWords( b, true ) );

        for( int n = 0; n < boardLst.size(); n++ ){
            if( this.board.getWordLst().contains( boardLst.get( n ) ) ){
                boardLst.remove( boardLst.get( n ) );
                n--;
            }
        }

//        System.out.println( boardLst );

        if( boardLst.size() > 0){
            b.addToWordsLst( boardLst.get( 0 ) );
            return boardLst.get( 0 );
        }
        return "";
    }

    public String getLetters() {
        return letters;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }
}
