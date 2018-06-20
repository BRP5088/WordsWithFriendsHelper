import java.util.ArrayList;

/**
 * Created by Brett Patterson on 6/19/2018.
 */
public class Node {

    private int row;
    private int col;
    private tileType type;
    private ArrayList<Node> neighbors;
    private String letter;
    private playerID player;

    enum tileType{
        TripleWordScore,
        TripleLetterScore,
        DoubleWordScore,
        DoubleLetterScore,
        start,
        regular
    }

    enum playerID{
        player0,
        player1,
        player2
    }



    public Node(int r, int c){
        row = r;
        col = c;
        letter = "";
        type = tileType.regular;
        player = playerID.player0;
        neighbors = new ArrayList<>();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public tileType getType() {
        return type;
    }

    public void setType(tileType type) {
        this.type = type;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public playerID getPlayer() {
        return player;
    }

    public void setPlayer(playerID player) {
        this.player = player;
    }

    @Override
    public String toString() {
        String str;

        switch( type ){
            case TripleWordScore:
                if( letter.length() == 0 )
                    str = "TW";
                else
                    str = letter;
                break;

            case TripleLetterScore:
                if( letter.length() == 0 )
                    str = "TL";
                else
                    str = letter+" ";
                break;

            case DoubleWordScore:
                if( letter.length() == 0 )
                    str = "DW";
                else
                    str = letter+" ";
                break;

            case DoubleLetterScore:
                if( letter.length() == 0 )
                    str = "DL";
                else
                    str = letter+" ";
                break;

            case start:
                if( letter.length() == 0 )
                    str = "**";
                else
                    str = letter+" ";
                break;

            case regular:
                if( letter.length() == 0 )
                    str = "  ";
                else
                    str = letter+" ";
                break;
            default:
                System.out.println("type is invalid!!");
                str = "INVALID";
                break;
        }
        return str;
    }


    public void moveCall(  Node.playerID player, String letter, int row, int col) {
        this.player = player;
        this.letter = letter;
        this.row = row;
        this.col = col;
    }

}
