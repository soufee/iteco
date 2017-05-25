import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 25.05.2017.
 */
public class GetData {
    public static Map<String, String> entries = new HashMap<String, String>();
    public static Map<String, String> exits = new HashMap<String, String>();
    public static Set<String> set = new HashSet<String>();
    public static String line = "";
    // public String fileName;
    // public List<MethodCortege> list = new ArrayList<MethodCortege>();

//    public GetData(String fileName) {
//        this.fileName = fileName;
//    }

    public void soutResults(String fileName) throws ParseException {
        fillInMaps(fileName);
        ArrayList<MethodCortege> list = (ArrayList<MethodCortege>) getList();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i).getMethod());
        }

        for (String string : set) {
            System.out.println("OperationsImpl:" + string + " min " + getMinTime(string, list) + " max " + getMaxTime(string, list)
                    + " avg " + getAvgTime(string, list) + " max id " + getMaxId(string, list) + " count " + count(string, list));
        }

    }


    public void fillInMaps(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                shareToMap(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("В entries " + entries.size() + " записей");
        System.out.println("В exits " + exits.size() + " записей");

    }

    //  public List<MethodCortege> list = new ArrayList<MethodCortege>();

    public List<MethodCortege> getList() throws ParseException {
        List<MethodCortege> result = new ArrayList<MethodCortege>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            String string = entry.getValue();
            long time = getTime(getMethodNameWithId(string));
            if (time != -1) {
                MethodCortege cortege = new MethodCortege(getMethodName(entry.getKey()), getMethodId(entry.getKey()), (int) time);
                result.add(cortege);
            }
        }
        return result;
    }

    public void shareToMap(String string) {

        if (string.contains("entry")) {
            entries.put(getMethodNameWithId(string), string);
        } else if (string.contains("exit")) {
            exits.put(getMethodNameWithId(string), string);
        }
    }

    public long getTime(String string) throws ParseException {
        if (exits.containsKey(string)) {
            String entry = entries.get(string);
            String exit = exits.get(string);
            long starttime = getDate(entry);
            long endTime = getDate(exit);
            return endTime - starttime;
        } else return -1;
    }

    public long getDate(String string) throws ParseException {
        String year = string.substring(0, 4);
        String month = string.substring(5, 7);
        String day = string.substring(8, 10);
        String hours = string.substring(11, 13);
        String minutes = string.substring(14, 16);
        String seconds = string.substring(17, 19);
        String milliseconds = string.substring(20, 23);
        String d = string.substring(0, 23);
        Calendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hours), Integer.parseInt(minutes), Integer.parseInt(seconds));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
        Date date = simpleDateFormat.parse(d);
        long time = date.getTime();

        return time;
    }

    public String getMethodNameWithId(String line) {
        String methodWithId = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        //  System.out.println(methodWithId);
        return methodWithId;
    }

    public String getMethodName(String line) {
        String methodName = line.substring(0, line.indexOf(":"));
        return methodName;
    }

    public int getMethodId(String line) {

        int methodId = Integer.parseInt(line.substring(line.indexOf(":") + 1));
        return methodId;
    }

    public int getMaxId(String methodName, List<MethodCortege> list) {
        int maxId = 0;
        int maxTime = 0;
        for (MethodCortege cortege : list) {
            if ((cortege.getMethod().equals(methodName)) && (cortege.getTime() > maxTime)) {
                maxTime = cortege.getTime();
                maxId = cortege.getId();
            }
        }
        return maxId;
    }

    public int getMaxTime(String methodName, List<MethodCortege> list) {
        int maxTime = 0;

        for (MethodCortege cortege : list) {
            if ((cortege.getMethod().equals(methodName)) && (cortege.getTime() > maxTime)) maxTime = cortege.getTime();
        }
        return maxTime;
    }


    public int getMinTime(String methodName, List<MethodCortege> list) {
        int minTime = Integer.MAX_VALUE;

        for (MethodCortege cortege : list) {
            if ((cortege.getMethod().equals(methodName)) && (cortege.getTime() < minTime)) {
                minTime = cortege.getTime();

            }
        }
        return minTime;
    }

    public long getAvgTime(String methodName, List<MethodCortege> list) {
        int count = 0;
        long avg = 0;
        for (MethodCortege cortege : list) {
            if (cortege.getMethod().equals(methodName)) {
                avg += cortege.getTime();
                count++;
            }
        }

        return avg / count;
    }

    public int count(String methodName, List<MethodCortege> list) {
        int counter = 0;
        for (MethodCortege cortege : list) {
            if (cortege.getMethod().equals(methodName))
                counter++;
        }
        return counter;
    }

}
