import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 * Created by admin on 25.05.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("testlog.log"));
        String line = "";
        while ((line=reader.readLine())!=null)
        {
         //   System.out.println(line);
            getDate(line);
        }

    }
    public static Date getDate(String string)
    {

        String year = string.substring(0,4);
        String month = string.substring(5,7);
        String day = string.substring(8,10);
        String hours = string.substring(11,13);
        String minutes = string.substring(14,16);
        String seconds = string.substring(17,19);
        System.out.println(year + " "+month+" "+day + " "+hours+" "+minutes+" "+seconds);
        Date date = new Date(year,month,day);
        return date;
    }
}
