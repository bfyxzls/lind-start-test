import org.junit.Test;

import java.util.Arrays;

public class WordTest {
    @Test
    public void wordSplit() {
        String msg = "lind is man,he is great man.";
        System.out.println(Arrays.asList(msg.toLowerCase().split("\\W+")));
    }

}
