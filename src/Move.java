import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
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

    public void preGameMoves(Node b [][]) throws FileNotFoundException {



        File f = new File( "src\\PreGameMoves.txt" );

        Scanner s = new Scanner( f );

        while( s.hasNext() ){
            String tmp = s.next();

            String arr [] = tmp.split(":");

            if( arr[0].equals("Player1") ) {
                addToBoard(b, Node.playerID.player1, arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]) );
            }
            else{
                addToBoard(b, Node.playerID.player2, arr[1], arr[2], Integer.parseInt(arr[3]), Integer.parseInt(arr[4]) );
            }
        }

        s.close();
    }

    public void addToBoard(Node board [][], Node.playerID playerID, String word, String direction, int row, int col) {

        updatePoints( board, playerID, word, row, col);

        if ( direction.equals("L") ) {  // Left to right
            int tmpCol = col;
            for (int i = 0; i < word.length(); i++) {
                board[row][tmpCol].moveCall( playerID, word.substring(i, i+1), row, col);
                tmpCol++;
            }
        }
        else {  // up to down
            int tmpRow = row;
            for (int i = 0; i < word.length(); i++) {
                board[tmpRow][col].moveCall( playerID, word.substring(i, i+1), row, col);
                tmpRow++;
            }
        }
    }

    public void updatePoints( Node board[][], Node.playerID playerID, String word, int row, int col ) {
        int total = 0;
        int multiplier = 1;

        int numlst[] = new int[word.length()];

        HashMap<String, Integer> values = new HashMap<>(new Board().getValues());

        for (int i = 0; i < word.length(); i++) {

            numlst[i] = values.get( word.substring(i, i + 1) );

            if (i == 0) {
                if( board[row][col].getNodeType() == Node.tileType.TripleWordScore) {
                    multiplier = 3;
                }
                else if( board[row][col].getNodeType() == Node.tileType.DoubleWordScore) {
                    multiplier = 2;
                }
                else if( board[row][col].getNodeType() == Node.tileType.TripleLetterScore) {
                    numlst[i] *= 3;
                }
                else if( board[row][col].getNodeType() == Node.tileType.DoubleLetterScore) {
                    numlst[i] *= 2;
                }
            }
        }
//        System.out.println( numlst[0] );
//        System.out.println( numlst[1] );
//        System.out.println( numlst[2] );
//        System.out.println("_____________");
        total = IntStream.of(numlst).sum();

        if (multiplier != 1) {
            total *= multiplier;
        }

        if( playerID == Node.playerID.player1){
            this.board.setPlayer1pts( this.board.getPlayer1pts() + total );
        }
        else{
            this.board.setPlayer2pts( this.board.getPlayer2pts() + total );
        }
    }


}
