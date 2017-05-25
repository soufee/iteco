import org.junit.Assert;
import org.junit.Test;

/**
 * Created by admin on 25.05.2017.
 */
public class GetDataTest extends Assert {
    int expectedlist = 4;

    @Test
    public void testsoutResults() {
        GetData getData = new GetData();
        getData.soutResults("test.log");
        assertEquals(expectedlist, getData.getSetSize());

    }


}
