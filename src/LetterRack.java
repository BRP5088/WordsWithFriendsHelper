import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


/**
 * Created by Brett Patterson on 6/21/2018.
 */
public class LetterRack extends utilityCode{

    private HashMap<String, HashSet<String> > dictionary;
    private String rack [];



    public LetterRack() throws FileNotFoundException {

        dictionary = new HashMap<>();

        File f = new File( "src\\wordList_Complete.txt" );
        Scanner s = new Scanner( f );

        while( s.hasNext() ){
            String word = s.next().toUpperCase();
            String sorted = sort( word );


//            if( word.equals( "NEROL" ) ){
//                System.out.println("word is in the text");
//            }

            if( dictionaryContains( dictionary, sorted ) ){
                dictionary.get( sorted ).add( word );
            }
            else{
                HashSet<String> list = new HashSet<>();
                list.add( word );
                dictionary.put( sorted, list );
            }
        }
        s.close();
    }

    public HashMap<String, HashSet<String> > getDictionary() {
        return dictionary;
    }

    public String[] getRack(){
        return rack;
    }

    public void setRack(String[] rack) {
        this.rack = rack;
    }
}
