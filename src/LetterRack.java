import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Brett Patterson on 6/21/2018.
 */
public class LetterRack {

    private HashSet dictionary;
    private String rack [];


    public LetterRack() throws FileNotFoundException {

//        int total = 0;
//        dictionary = new String[26][11000];
        dictionary = new HashSet();

//        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        File f = new File( "src\\wordList_Complete.txt" );
        Scanner s = new Scanner( f );

        while( s.hasNext() ){
            String tmp = s.next().toUpperCase();

//            int place = alphabet.indexOf( tmp.substring(0, 1) );
//            int newLetter = 0;

//            for(int n = 0; n < dictionary[place].length; n++ ){
//                if( dictionary[place][n] == null ){
//                    newLetter = n;
//                    break;
//                }
//            }

//            dictionary[ place ][newLetter] = tmp;
            dictionary.add( tmp );
//            total++;
        }
        s.close();

//        System.out.println("Total words read in: " + total);
    }

    public HashSet getDictionary() {
        return dictionary;
    }

    public String[] getRack() {
        return rack;
    }

    public void setRack(String[] rack) {
        this.rack = rack;
    }
}
