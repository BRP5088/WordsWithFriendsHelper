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
    private boolean beingUsed;
    private boolean blankTile;

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
        beingUsed = false;
        type = tileType.regular;
        player = playerID.player0;
        blankTile = false;
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

    public tileType getNodeType() {
        return type;
    }

    public void setType(tileType type) {
        this.type = type;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor( Node neighbor) {
        if ( !neighbors.contains( neighbor) ){
            neighbors.add( neighbor );
        }
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


    public boolean isBeingUsed() {
        return beingUsed;
    }

    public boolean isBlankTile() {
        return blankTile;
    }

    @Override
    public String toString() {
        String BOLD = "\u001B[1m";
        String RESET = "\u001B[0m";
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
//        return BOLD+str;
//        if( str.equals("  ") )
            return str;
//        else
//            return BOLD+str+RESET;
    }


    public void moveCall(playerID player, String letter, boolean blankState, int row, int col) {
//        this.player = player;
        this.letter = letter;
        this.row = row;
        this.col = col;
//        beingUsed = true;
        blankTile = blankState;
    }

    public void setBeingUsed(boolean beingUsed) {
        this.beingUsed = beingUsed;
    }
}
