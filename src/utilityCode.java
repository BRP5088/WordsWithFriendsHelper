import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Brett Patterson on 8/6/2018.
 */
public class utilityCode {

    public String BLACK = "\u001B[30m";
    public String RED = "\u001B[31m";
    public String GREEN = "\u001B[32m";
    public String YELLOW = "\u001B[33m";
    public String BLUE = "\u001B[34m";
    public String MAGENTA = "\u001B[35m";
    public String CYAN = "\u001B[36m";
    public String WHITE = "\u001B[37m";
    public String RESET = "\u001B[0m";

    public String BGBLACK = "\u001B[40";
    public String BGRED = "\u001B[41";
    public String BGGREEN = "\u001B[42";
    public String BGYELLOW = "\u001B[43";
    public String BGBLUE = "\u001B[44";
    public String BGMAGENTA = "\u001B[45";
    public String BGCYAN = "\u001B[46";
    public String BGWHITE = "\u001B[47";

    public String BBGBLACK = "\u001B[40;1m";
    public String BBGRED = "\u001B[41;1m";
    public String BBGGREEN = "\u001B[42;1m";
    public String BBGYELLOW = "\u001B[43;1m";
    public String BBGBLUE = "\u001B[44;1m";
    public String BBGMAGENTA = "\u001B[45;1m";
    public String BBGCYAN = "\u001B[46;1m";
    public String BBGWHITE = "\u001B[47;1m";

    public String BOLD = "\u001B[1m";
    public String UNDERLINE = "\u001B[4m";
    public String REVERSED = "\u001B[7m";


    public utilityCode() {}

    public String sort(String str ) {

        char[] charArray = str.toCharArray();
        Arrays.sort( charArray );
        String sortedString = new String(charArray);

        return sortedString;
    }

    public boolean dictionaryContains( HashMap map, String str){
        String sorted = sort( str );

        if( map.containsKey( sorted ) ){
            HashSet<String> list = (HashSet<String>) map.get( sorted );

            if( list.contains( str ) ){
                if( list != null){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

}