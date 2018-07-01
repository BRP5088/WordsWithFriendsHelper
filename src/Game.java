import java.io.FileNotFoundException;

/**
 * Created by Brett Patterson on 7/1/2018.
 */
public class Game {

    private Board board;
    private Move move;
    private LetterRack letterRack;
    private String dictionary [][];
    public String letters [];

    public Game() throws FileNotFoundException {
        board = new Board();
        move = new Move( board );
        letterRack = new LetterRack();
        dictionary = letterRack.getDictionary();
    }

    public void passFileName( String path) throws FileNotFoundException {
        move.preGameMoves( board.getBoard(), path );
        setLetters( move.getLetters() );
    }

    public Board getBoard(){
        return board;
    }

    public void setLetters( String letters){
        this.letters = new String[ letters.length() ];

        for( int n = 0; n < letters.length(); n++){
            this.letters[n] = letters.substring(n, n + 1);
        }

        System.out.println( letters );
    }







    private int factorial( int start ){
        if( start == 0){
            return 1;
        }

        return start * factorial( start-1);
    }


    public void findPossibleWords() {
        int maxCombo = factorial( letters.length );
        System.out.println( maxCombo );
    }
}
