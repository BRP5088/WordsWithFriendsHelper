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

        for (int i = 0; i < word.length(); i++) {
            if( word.substring(i, i+1).equals("*") ){
                blank = true;
            }
            else {

                if( !board[nRow][nCol].isBeingUsed()  ) {
                    board[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), blank, row, col);
                }
                else{
                    if( !board[nRow][nCol].getLetter().equals( word.substring(i, i + 1) ) ){

                        System.out.println( RED+ BOLD + "TRYING TO OVERWRITE A LETTER! " + RESET);
                        System.out.println("Letter on the board: " + board[nRow][nCol] );
                        System.out.println("Letter trying to replace it with: " + word.substring(i, i + 1));
                        System.exit(3);
                    }
                    board[nRow][nCol].moveCall(playerID, word.substring(i, i + 1), blank, row, col);
                    board[nRow][nCol].setWord(word);
                }

                if ( direction.equals("L") ) {
                    nCol++;
                }
                else{
                    nRow++;
                }
                blank = false;
            }
        }


        if( word.contains("*") ){
            word = word.replace("*", "");
        }

//        System.out.println("wordzz: "+word);

        int total = getPlayerMovePts( board, playerID, word, direction, row, col);

        if( playerID == Node.playerID.player1){
            this.board.setPlayer1pts( total + this.board.getPlayer1pts() );
        }
        else{
            this.board.setPlayer2pts( total + this.board.getPlayer2pts() );
        }


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



                ArrayList<Node> neghbors = getNeghborsWithWords(  nRow, nCol, word);

//                        this.board.getNode( nRow, nCol).getNeighbors();

//                for( Node node : neghbors){
//                    node
//                }








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

//        if( playerID == Node.playerID.player2)
//            System.out.println("pre total: "+total);
        if (multiplier != 1) {
            total *= multiplier;
        }
//        if( playerID == Node.playerID.player2)
//            System.out.println("aft total: "+total);
//        System.out.println();
//        System.out.println();
//        System.out.println();


//        System.out.println("MULTI: "+multiplier);


//        if( playerID == Node.playerID.player2) {
//            System.out.println(word);
//            System.out.println("Player2:");
//            for (int n : numlst) {
//                System.out.print(n+"|");
//            }
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println( "MULRI: " + multiplier);
//            System.out.println("TOTAL: "+ total);
//        }

//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();

//        System.exit( 1 );
        return total;
    }

    private ArrayList<Node> getNeghborsWithWords(int nRow, int nCol, String word) {
        ArrayList<Node> lst = new ArrayList<>();
        Node node = this.board.getNode(nRow, nCol);

        for(Node n : node.getNeighbors() ){
            if( !n.getWord().equals("") ){
                lst.add( n );
            }
        }

        if( lst.size() > 0) {
            System.out.println("++++ " + word);

            System.out.println("PPPPPPP");
            for (Node n : lst)
                System.out.println(n.getWord());
            System.out.println("+++++++++++++++");
            System.out.println();
            System.out.println();
            System.out.println();
        }

        System.out.println("13,7 neighbors: "+this.board.getNode(13,7).getNeighbors() );

        return null;
    }


}
