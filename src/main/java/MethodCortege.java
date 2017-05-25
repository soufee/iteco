/**
 * Created by admin on 25.05.2017.
 */
public class MethodCortege {
    public String method;
    public int id;
    public int time;

    public MethodCortege(String method, int id, int time) {
        this.method = method;
        this.id = id;
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "MethodCortege{" +
                "method='" + method + '\'' +
                ", id=" + id +
                ", time=" + time +
                '}';
    }
}
