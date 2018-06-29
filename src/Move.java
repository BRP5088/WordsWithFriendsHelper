import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Created by Brett Patterson on 6/20/2018.
 */
public class Move {


    private Board board;
    String RED = "\u001B[31m";  //to see the console in colors
    String GREEN = "\u001B[32m";
    String RESET = "\u001B[0m";

    public Move(Board board ) {
        this.board = board;
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
            else if( arr[0].equals("Player2") ){
                addToBoard(b, Node.playerID.player2, arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]) );
            }
//            System.out.println(tmp);
        }

        s.close();
    }

    public void addToBoard(Node board [][], Node.playerID playerID, String word, String direction, int row, int col) {

        String RED = "\u001B[31m";  //to see the console in colors
        String BOLD = "\u001B[1m";  //to see the console in colors
        String UNDERLINE = "\u001B[4m";  //to see the console in colors
        String RESET = "\u001B[0m"; //to see the console in colors

        int nRow = row;
        int nCol = col;

        boolean blank = false;

        String throwaway = word.substring(0);

        if( throwaway.contains("*") ){
            throwaway = throwaway.replace("*", "");
        }

        if( this.board.addToWordsLst( "("+row+","+ col+ ")"+ throwaway + "|"+ direction ) ) {

            for (int i = 0; i < word.length(); i++) {
                if (word.substring(i, i + 1).equals("*")) {
                    blank = true;
                } else {

                    if (!board[nRow][nCol].isBeingUsed()) {
                        board[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), blank, row, col);
                    }
                    else {
                        if (!board[nRow][nCol].getLetter().equals(word.substring(i, i + 1))) {

                            System.out.println(RED + BOLD + "TRYING TO OVERWRITE A LETTER! " + RESET);
                            System.out.println("Letter on the board: " + board[nRow][nCol]);
                            System.out.println("Letter trying to replace it with: " + word.substring(i, i + 1));
                            System.exit(3);
                        }
                        board[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), blank, row, col);
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

            System.out.println("Running anyNewWords:++++");
            String newWord = anyNewWords();

            if ( newWord.length() > 0) {
                System.out.println( GREEN + "Found a new word" + RESET);

                this.board.addToWordsLst( newWord );

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

//            System.out.println("Word is:"+ newWord);
                addToBoard(board, playerID, newWord, dir, r, c);
            }

        }
//        System.out.println();
    }

    public int getPlayerMovePts(Node board[][], Node.playerID playerID, String word, String direction, int row, int col ) {
        int total = 0;
        int multiplier = 1;

        int nRow = row;
        int nCol = col;

        int numlst[] = new int[ word.length() ];

        HashMap<String, Integer> values = new HashMap<>( new Board().getValues() );

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
                if( this.board.isNodeBlank( nRow, nCol) ) {
                    numlst[i] = 0;
                }

            }
//            else {
//                System.out.println( board[nRow][nCol].getLetter() );
//            }
            this.board.setBeingUsed(nRow, nCol, playerID);

            if( direction.equals("L") ){
                nCol++;
            }
            else {
                nRow++;
            }
        }

        total = IntStream.of(numlst).sum();

        if (multiplier != 1) {
            total *= multiplier;
        }
//        System.exit( 1 );
        return total;
    }

    public String anyNewWords(){

        ArrayList<String> boardLst = new ArrayList<>();

        for( int r = 0; r < 14; r++){
            String tmp = "";
            String tmpStart = "";
            String tmmp = "";
            String tmmpStart = "";

            for( int c = 0; c < 14; c++) {
                if( !this.board.getNode(r, c).getLetter().equals("") ) { //left to right

                    if( tmp.length() == 0){
                        tmpStart +=  "("+r +"," +c+ ")";
                    }
                    tmp += board.getNode(r, c).getLetter();
                }
                else{
                    tmp = "";
                }

                if( !this.board.getNode(c, r).getLetter().equals("") ) {  // up to down
                    if( tmmp.length() == 0){
                        tmmpStart +=  "("+r +"," +c+ ")";
                    }
                      tmmp += board.getNode(c, r).getLetter();
                }
                else{
                    tmmp = "";
                }

            }
            if( !tmp.equals("")  & tmp.length() > 1) {
                boardLst.add( tmpStart + tmp+"|L" );
            }

            if( !tmmp.equals("")  & tmmp.length() > 1) {
                boardLst.add( tmmpStart + tmmp+"|D" );
            }
        }


        for( int n = 0; n < boardLst.size(); n++ ){
//            System.out.println( node );
            if( this.board.getWordLst().contains( boardLst.get( n ) ) ){
                boardLst.remove( boardLst.get( n ) );
            }
        }


//        for( String node : boardLst){
//            System.out.println( node );
//        }
//        System.out.println( this.board.getWordLst() );

        if( boardLst.size() > 1){
            System.out.println( RED + "boardLst BIGGER THEN 1"+ RESET);
            System.exit( 5);
        }


//        System.out.println("size: "+ boardLst.size() );

//        System.exit( 3);

        if( boardLst.size() == 1){
            return boardLst.get( 0 );
        }

        return "";
    }




    /*
    private ArrayList<Node> getNeghborsWithWords(int nRow, int nCol, String word) {
        ArrayList<Node> lst = new ArrayList<>();
        Node node = this.board.getNode(nRow, nCol);

        for(Node n : node.getNeighbors() ){
//            System.out.println(n.getWord());
            if( !n.getWord().equals("") ){
                lst.add( n );
            }
        }

        if( lst.size() > 0) {
            System.out.println("++++ " + word);

            for (Node n : lst)
                System.out.print ( n.getLetter()+"|" );
//                System.out.println( "node Word: "+ n.getWord() );
            System.out.println();
            System.out.println("+++++++++++++++");
//            System.out.println();
//            System.out.println();
        }


        return null;
    }
    */


}
