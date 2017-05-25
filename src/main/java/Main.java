import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by admin on 25.05.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        GetData getData = new GetData();
        getData.soutResults(args[0]);
        System.out.println("Введите exit для выхода ");
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!(command=scanner.nextLine()).equals("exit")){
            System.out.println(command);
            System.out.println("Для выхода введите exit");
        }
    }


}
