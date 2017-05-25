import org.apache.log4j.Logger;

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

    private static Map<String, String> entries = new HashMap<String, String>();
    private static Map<String, String> exits = new HashMap<String, String>();
    private static Set<String> set = new HashSet<String>();
    private static String line = "";
    private static final Logger logger = Logger.getLogger(GetData.class);

    public int getSetSize() {
        return set.size();
    }

    public void soutResults(String fileName) {
        logger.debug("Starting...");
        fillInMaps(fileName);
        ArrayList<MethodCortege> list = null;

        try {
            list = (ArrayList<MethodCortege>) getList();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i).getMethod());
        }

        for (String string : set) {
            System.out.println(string + " min " + getMinTime(string, list) + " max " + getMaxTime(string, list)
                    + " avg " + getAvgTime(string, list) + " max id " + getMaxId(string, list) + " count " + count(string, list));
        }
        logger.debug("Started...");
    }


    private void fillInMaps(String fileName) {
        try {
            logger.debug("Trying to fill maps");
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                shareToMap(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }


    private List<MethodCortege> getList() {
        logger.debug("getting list");
        List<MethodCortege> result = new ArrayList<MethodCortege>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            String string = entry.getValue();
            long time = 0;
            try {
                time = getTime(getMethodNameWithId(string));
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
            if (time != -1) {
                MethodCortege cortege = new MethodCortege(getMethodName(entry.getKey()), getMethodId(entry.getKey()), (int) time);
                result.add(cortege);
            }
        }
        return result;
    }

    private void shareToMap(String string) {

        if (string.contains("entry")) {
            entries.put(getMethodNameWithId(string), string);
        } else if (string.contains("exit")) {
            exits.put(getMethodNameWithId(string), string);
        }
    }

    private long getTime(String string) {
        if (exits.containsKey(string)) {
            String entry = entries.get(string);
            String exit = exits.get(string);
            long starttime = getDate(entry);
            long endTime = getDate(exit);
            return endTime - starttime;
        } else return -1;
    }

    private long getDate(String string) {

        String d = string.substring(0, 23);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
        Date date = null;
        try {
            date = simpleDateFormat.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        long time = date.getTime();

        return time;
    }

    private String getMethodNameWithId(String line) {
        String classname = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
        String methodWithId = classname + ":" + line.substring(line.indexOf('(') + 1, line.indexOf(')'));

        return methodWithId;
    }

    private String getMethodName(String line) {
        String methodName = line.substring(0, line.lastIndexOf(":"));
        return methodName;
    }

    private int getMethodId(String line) {

        int methodId = Integer.parseInt(line.substring(line.lastIndexOf(":") + 1));
        return methodId;
    }

    private int getMaxId(String methodName, List<MethodCortege> list) {
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

    private int getMaxTime(String methodName, List<MethodCortege> list) {
        int maxTime = 0;

        for (MethodCortege cortege : list) {
            if ((cortege.getMethod().equals(methodName)) && (cortege.getTime() > maxTime)) maxTime = cortege.getTime();
        }
        return maxTime;
    }


    private int getMinTime(String methodName, List<MethodCortege> list) {
        int minTime = Integer.MAX_VALUE;

        for (MethodCortege cortege : list) {
            if ((cortege.getMethod().equals(methodName)) && (cortege.getTime() < minTime)) {
                minTime = cortege.getTime();

            }
        }
        return minTime;
    }

    private long getAvgTime(String methodName, List<MethodCortege> list) {
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

    private int count(String methodName, List<MethodCortege> list) {
        int counter = 0;
        for (MethodCortege cortege : list) {
            if (cortege.getMethod().equals(methodName))
                counter++;
        }
        return counter;
    }


}
