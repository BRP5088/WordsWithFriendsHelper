import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Brett Patterson on 6/19/2018.
 */
public class Board {

    private Node board[][];
    private int player1pts;
    private int player2pts;
    private HashMap<String, Integer> values;
    private ArrayList<String> wordLst;
    private String player1Name;
    private String player2Name;


    String RED = "\u001B[31m";  //to see the console in colors
    String RESET = "\u001B[0m";

    public Board() {

        player1pts = 0;
        player2pts = 0;

        values = new HashMap<>();
        wordLst = new ArrayList<>();

        String letters = "AB CDEFGHIJKLMNOPQRSTUVWXYZ";

        String one = "AERSTIO";
        String two = "DLNU";
        String three = "YGH";
        String four = "BCFPMW";
        String five = "KV";
        String eight = "X";
        String ten = "ZJQ";

        int val = 0;
        for(int i = 0; i < letters.length(); i++ ){

            if( one.contains(letters.substring(i, i + 1) ) ){
                val = 1;
            }
            else if( two.contains(letters.substring(i, i + 1) ) ) {
                val = 2;
            }
            else if( three.contains(letters.substring(i, i + 1) ) ) {
                val = 3;
            }
            else if( four.contains(letters.substring(i, i + 1) ) ) {
                val = 4;
            }
            else if( five.contains(letters.substring(i, i + 1) ) ) {
                val = 5;
            }
            else if( eight.contains(letters.substring(i, i + 1) ) ) {
                val = 8;
            }
            else if( ten.contains(letters.substring(i, i + 1) ) ) {
                val = 10;
            }
            else if( letters.substring(i, i + 1).equals(" ") ){
                val = 0;
            }
            values.put( letters.substring(i, i + 1), val );
        }



        board = new Node[15][15];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = new Node(r, c);
            }
        }

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                makeNeighborhors(board[r][c]);
            }
        }


        // --- The starting place ---
        board[7][7].setType( Node.tileType.start );
        // --- end ---


        // --- This is the TripleWordScore in the corners ---
        board[0][3].setType( Node.tileType.TripleWordScore );
        board[3][0].setType( Node.tileType.TripleWordScore );
        board[0][11].setType( Node.tileType.TripleWordScore );
        board[3][14].setType( Node.tileType.TripleWordScore );
        board[11][0].setType( Node.tileType.TripleWordScore );
        board[14][3].setType( Node.tileType.TripleWordScore );
        board[11][14].setType( Node.tileType.TripleWordScore );
        board[14][11].setType( Node.tileType.TripleWordScore );
        // --- end ---

        // --- This is the top left "line" segment ---
        board[6][0].setType( Node.tileType.TripleLetterScore );
        board[5][1].setType( Node.tileType.DoubleWordScore );
        board[4][2].setType( Node.tileType.DoubleLetterScore );
        board[3][3].setType( Node.tileType.TripleLetterScore );
        board[2][4].setType( Node.tileType.DoubleLetterScore );
        board[1][5].setType( Node.tileType.DoubleWordScore );
        board[0][6].setType( Node.tileType.TripleLetterScore );
        // --- end ---



        // --- This is the top right "line" segment ---
        board[0][8].setType( Node.tileType.TripleLetterScore );
        board[1][9].setType( Node.tileType.DoubleWordScore );
        board[2][10].setType( Node.tileType.DoubleLetterScore );
        board[3][11].setType( Node.tileType.TripleLetterScore );
        board[4][12].setType( Node.tileType.DoubleLetterScore );
        board[5][13].setType( Node.tileType.DoubleWordScore );
        board[6][14].setType( Node.tileType.TripleLetterScore );
        // --- end ---



        // --- This is the bottom right "line" segment ---
        board[14][8].setType( Node.tileType.TripleLetterScore );
        board[13][9].setType( Node.tileType.DoubleWordScore );
        board[12][10].setType( Node.tileType.DoubleLetterScore );
        board[11][11].setType( Node.tileType.TripleLetterScore );
        board[10][12].setType( Node.tileType.DoubleLetterScore );
        board[9][13].setType( Node.tileType.DoubleWordScore );
        board[8][14].setType( Node.tileType.TripleLetterScore );
        // --- end ---



        // --- This is the bottom left "line" segment ---
        board[8][0].setType( Node.tileType.TripleLetterScore );
        board[9][1].setType( Node.tileType.DoubleWordScore );
        board[10][2].setType( Node.tileType.DoubleLetterScore );
        board[11][3].setType( Node.tileType.TripleLetterScore );
        board[12][4].setType( Node.tileType.DoubleLetterScore );
        board[13][5].setType( Node.tileType.DoubleWordScore );
        board[14][6].setType( Node.tileType.TripleLetterScore );
        // --- end ---


        // --- This is the middle area bottom left 5 ---
        board[7][3].setType( Node.tileType.DoubleWordScore );
        board[8][4].setType( Node.tileType.DoubleLetterScore );
        board[9][5].setType( Node.tileType.TripleLetterScore );
        board[10][6].setType( Node.tileType.DoubleLetterScore );
        board[11][7].setType( Node.tileType.DoubleWordScore );
        // --- end ---



        // --- This is the middle area bottom right 4 ---
        board[10][8].setType( Node.tileType.DoubleLetterScore );
        board[9][9].setType( Node.tileType.TripleLetterScore );
        board[8][10].setType( Node.tileType.DoubleLetterScore );
        board[7][11].setType( Node.tileType.DoubleWordScore );
        // --- end ---



        // --- This is the middle area top right 4 ---
        board[6][10].setType( Node.tileType.DoubleLetterScore );
        board[5][9].setType( Node.tileType.TripleLetterScore );
        board[4][8].setType( Node.tileType.DoubleLetterScore );
        board[3][7].setType( Node.tileType.DoubleWordScore );
        // --- end ---



        // --- This is the middle area top left 3 ---
        board[4][6].setType( Node.tileType.DoubleLetterScore );
        board[5][5].setType( Node.tileType.TripleLetterScore );
        board[6][4].setType( Node.tileType.DoubleLetterScore );
        // --- end ---



        // --- This is the top left DoubleLetter type ---
        board[2][1].setType( Node.tileType.DoubleLetterScore );
        board[1][2].setType( Node.tileType.DoubleLetterScore );
        // --- end ---



        // --- This is the top right DoubleLetter type ---
        board[1][12].setType( Node.tileType.DoubleLetterScore );
        board[2][13].setType( Node.tileType.DoubleLetterScore );
        // --- end ---


        // --- This is the bottom left DoubleLetter type ---
        board[12][1].setType( Node.tileType.DoubleLetterScore );
        board[13][2].setType( Node.tileType.DoubleLetterScore );
        // --- end ---



        // --- This is the bottom right DoubleLetter type ---
        board[13][12].setType( Node.tileType.DoubleLetterScore );
        board[12][13].setType( Node.tileType.DoubleLetterScore );
        // --- end ---
    }

    public Node[][] getBoard() {
        return board;
    }

    public void setPlayer1pts(int player1pts) {
        this.player1pts = player1pts;
    }

    public void setPlayer2pts(int player2pts) {
        this.player2pts = player2pts;
    }

    public boolean isNodeBlank( int row, int col){
        return board[row][col].isBlankTile();
    }

    public void setBeingUsed(int row, int col, Node.playerID ID){
        board[row][col].setBeingUsed( true );

        if( board[row][col].getPlayer() == Node.playerID.player0 ) {
            board[row][col].setPlayer(ID);
        }
    }

    public boolean addToWordsLst( String word ){

        if( wordLst.contains( word ) ){
            return false;
        }
        wordLst.add( word );
         return true;
    }

    public ArrayList<String> getWordLst(){
        return wordLst;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public void displayBoard(){

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

        String str = "";


        String pl1 = BLUE + player1Name + " pts: " + getPlayer1pts() + RESET;
        String pl2 = BLUE + player2Name + " pts: " + getPlayer2pts() + RESET;
        String bars = "";

        for( int num = 0; num < 78 - ( pl1.length() + pl2.length() ); num++ ){
            bars += " ";
        }

        System.out.println( pl1 + bars + pl2 );

        for( Node row [] : board ){
            for(Node node : row){

                if( node.getNodeType() == Node.tileType.TripleWordScore ){
                    str += YELLOW +"|"+ node.toString()+YELLOW +"|"+ RESET;
                }
                if( node.getNodeType() == Node.tileType.TripleLetterScore ){
                    str += GREEN +"|"+ node.toString()+GREEN +"|"+ RESET;
                }
                if( node.getNodeType() == Node.tileType.DoubleWordScore ){
                    str += RED +"|"+ node.toString()+RED +"|"+ RESET;
                }
                if( node.getNodeType() == Node.tileType.DoubleLetterScore ){
                    str +=  BLUE +"|"+ node.toString()+BLUE +"|"+ RESET;
                }
                if( node.getNodeType() == Node.tileType.start ) {
                    str += MAGENTA +"|"+ node.toString()+MAGENTA +"|"+ RESET;
                }
                if( node.getNodeType() == Node.tileType.regular ) {
                    str += BBGWHITE +"|"+ node.toString() + BBGWHITE +"|"+ RESET;
                }
            }
            str += "\n";
        }
        System.out.println( str );
    }

    public int getPlayer1pts() {
        return player1pts;
    }

    public int getPlayer2pts() {
        return player2pts;
    }

    public HashMap<String, Integer> getValues() {
        return values;
    }

    public void makeNeighborhors(Node node) {
        int row = node.getRow();
        int col = node.getCol();

//        System.out.println("R:"+row+", C:"+col);
//        System.out.println("Down");
        if (row < board.length - 1) {
                board[row + 1][col].addNeighbor(node);
                node.addNeighbor( board[row + 1][col] );
        }
//        System.out.println("UP");
        if (row >= 1) {
                board[row - 1][col].addNeighbor(node);
                node.addNeighbor(board[row - 1][col]);
        }
//        System.out.println("RIGHT");
        if (col < board[0].length - 1) {
                board[row][col + 1].addNeighbor(node);
                node.addNeighbor(board[row][col + 1]);
        }
//        System.out.println("LEFT");
        if (col > 0) {
                board[row][col - 1].addNeighbor(node);
                node.addNeighbor(board[row][col - 1]);
        }
    }

    public Node getNode( int row, int col){
        return board[row][col];
    }

    public void setWordLst(ArrayList<String> wordLst) {
        this.wordLst = wordLst;
    }

    public Board copyBoard( Board b ){
        Board board = new Board();

        board.setPlayer1pts( b.getPlayer1pts() );
        board.setPlayer2pts( b.getPlayer2pts() );

        board.setPlayer1Name( b.getPlayer1Name() );
        board.setPlayer2Name( b.getPlayer2Name() );

        board.setWordLst( b.getWordLst() );

        for( int r = 0; r < board.getBoard().length; r++){
            for( int c = 0; c < board.getBoard()[0].length; c++ ){
                b.getBoard()[r][c].copyNode( board.getBoard()[r][c] );
            }
        }

        return board;
    }



}
