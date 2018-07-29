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



//    private String letters = "";


    public Move(Board board ) {
        this.board = board;
        letters = "";
    }

    public void preGameMoves(Node b [][], String path) throws FileNotFoundException {

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
                addToBoard(b, Node.playerID.player1, arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]) );
            }
            if( arr[0].equals("Player2") ){
                addToBoard(b, Node.playerID.player2, arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]) );
            }
            if (arr[0].equals("LetterRack") ){
                letters = arr[1];
            }
        }

        s.close();
    }

    public void addToBoard(Node board [][], Node.playerID playerID, String word, String direction, int row, int col) {


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

            this.board.addToWordsLst("(" + row + "," + col + ")" + throwaway + "|" + direction);

            for (int i = 0; i < word.length(); i++) {
                if (word.substring(i, i + 1).equals("*")) {
                    blank = true;
                } else {

                    if (!board[nRow][nCol].isBeingUsed()) {
                        board[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), blank, row, col);
                    } else {
                        if (!board[nRow][nCol].getLetter().equals(word.substring(i, i + 1))) {

                            System.out.println(RED + BOLD + "TRYING TO OVERWRITE A LETTER! " + RESET);
                            System.out.println("Letter on the board: " + board[nRow][nCol]);
                            System.out.println("Letter trying to replace it with: " + word.substring(i, i + 1));
                            System.exit(3);
                        }

                        if( !this.board.getNode(nRow, nCol).isBeingUsed() ) {
                            board[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), blank, row, col);
                        }
                        else{
                            board[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), this.board.getNode(nRow, nCol).isBlankTile(), row, col);
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

            int total = getPlayerMovePts(board, playerID, word, direction, row, col);

            if (playerID == Node.playerID.player1) {
                this.board.setPlayer1pts(total + this.board.getPlayer1pts());
            } else {
                this.board.setPlayer2pts(total + this.board.getPlayer2pts());
            }
//            System.out.println( "The word entered is: " + word );
            String newWord = anyNewWords( board );

            if ( newWord.length() > 0) {
                String dir = "";

                if (newWord.substring(newWord.length() - 2).equals("L")) {
                    newWord = newWord.substring(0, newWord.length() - 2);
                    dir = "L";
                } else {
                    newWord = newWord.substring(0, newWord.length() - 2);
                    dir = "D";
                }

                int r = 0;
                int c = 0;

                r = Integer.parseInt(newWord.substring(1, newWord.indexOf(",")));
                newWord = newWord.substring(newWord.indexOf(",") + 1);

                c = Integer.parseInt(newWord.substring(0, newWord.indexOf(")")));
                newWord = newWord.substring(newWord.indexOf(")") + 1);

                addToBoard(board, playerID, newWord, dir, r, c);
            }
        }
    }

    public int getPlayerMovePts(Node board[][], Node.playerID playerID, String word, String direction, int row, int col ) {
        int total = 0;
        int multiplier = 1;

        int nRow = row;
        int nCol = col;

        int numlst[] = new int[ word.length() ];

        HashMap<String, Integer> values = new HashMap<>( new Board().getValues() );

        System.out.println(" The point word is: " + word );


        for (int i = 0; i < word.length(); i++) {

            numlst[i] = values.get( word.substring(i, i + 1) );

            if ( !board[nRow][nCol].isBeingUsed() ) {

//                System.out.println("Node: "+board[nRow][nCol].toString()  );
                if( board[nRow][nCol].getNodeType() == Node.tileType.TripleWordScore) {
                    multiplier *= 3;
                }
                if( board[nRow][nCol].getNodeType() == Node.tileType.DoubleWordScore) {
                    multiplier *= 2;
                }
                if( board[nRow][nCol].getNodeType() == Node.tileType.TripleLetterScore) {
                    numlst[i] *= 3;
                }
                if( board[nRow][nCol].getNodeType() == Node.tileType.DoubleLetterScore) {
                    numlst[i] *= 2;
                }
            }

            if( this.board.isNodeBlank( nRow, nCol) ) {
                numlst[i] = 0;
            }

            this.board.setBeingUsed(nRow, nCol, playerID);

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

        System.out.println( "the total is: " + total );

        return total;
    }

    public String anyNewWords( Node [][] b) {

        ArrayList<String> boardLst = new ArrayList<>();

        // left to right
//        System.out.println( RED + "left to right" + RESET );
        for (int r = 0; r < b.length; r++) {
            String tmp = "";
            String tmpStart = "";

            for (int c = 0; c < b[0].length; c++) {

                if( !this.board.getNode(r, c).getLetter().equals( "" )  ) {  //This takes account of left to right
                    if( tmp.length() == 0){
                        tmpStart +=  "("+r +"," +c+ ")";
                    }

                    tmp += board.getNode(r, c).getLetter();

                    if( c + 1 < b[0].length && this.board.getNode(r , c + 1).getLetter().equals( "" ) || tmp.length() > 1 && c + 1 == b[0].length ){
                        if( !tmp.equals("") && tmp.length() > 1) {
                            boardLst.add(tmpStart + tmp + "|L");
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
//        System.out.println( RED + "up to down" + RESET);
        for (int c = 0; c < b[0].length; c++) {
            String tmmp = "";
            String tmmpStart = "";

            for (int r = 0; r < b.length; r++) {
                if ( !this.board.getNode(r, c).getLetter().equals("") ) {  //This takes account of up to down
                    if ( tmmp.length() == 0 ) {
                        tmmpStart += "(" + r + "," + c + ")";
                    }

                    tmmp += board.getNode(r, c).getLetter();
//                    System.out.println( tmmp );



                    if( r + 1 < b.length && this.board.getNode(r + 1, c).getLetter().equals("") || tmmp.length() > 1 && r + 1 == b.length ){

//                        if( tmmp.length() > 1 && r + 1 == b.length ){
//                            System.out.println( GREEN + "Bingo" + RESET );
//                            System.out.println( tmmp );
//                            System.out.println( "___________" );
//                        }


//                        if( board.getNode(9,5).getLetter().equals( "J" ) ) {
//                            System.out.println(tmmp  + "(" + r + "," + c + ")" );
//                        }
                        if ( !tmmp.equals("") && tmmp.length() > 1) {
                            boardLst.add(tmmpStart + tmmp + "|D");
                            tmmp = "";
                            tmmpStart = "";
                        }
                        else{
//                            System.out.println("cleared");
                            tmmp = "";
                            tmmpStart = "";
                        }
                    }
                }
            }
        }

        for( int n = 0; n < boardLst.size(); n++ ){
            if( this.board.getWordLst().contains( boardLst.get( n ) ) ){
                boardLst.remove( boardLst.get( n ) );
                n--;
            }
        }

//        System.out.println( boardLst );

//        if( boardLst.size() > 1 ){
//            System.out.println( RED + "boardLst BIGGER THEN 1"+ RESET);
//            System.exit( 5 );
//        }

        if( boardLst.size() > 0){
            this.board.addToWordsLst( boardLst.get( 0 ) );
            return boardLst.get( 0 );
        }
        return "";
    }

    public String getLetters() {
        return letters;
    }
}
