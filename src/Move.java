/**
 * Created by Brett Patterson on 6/20/2018.
 */
public class Move {


//    public Move() {
//    }

    public void AddToBoard( Node board [][], Node.playerID playerID, String word, String direction, int row, int col) {

        if ( direction.equals("L") ) {  // Left to right
            int tmpCol = col;
            for (int i = 0; i < word.length(); i++) {
                board[row][tmpCol].moveCall( playerID,word.substring(i,i+1), row, col);
                tmpCol++;
            }
        }
        else {
            int tmpRow = row;
            for (int i = 0; i < word.length(); i++) {
                board[tmpRow][col].moveCall( playerID,word.substring(i,i+1), row, col);
                tmpRow++;
            }
        }
    }

}
