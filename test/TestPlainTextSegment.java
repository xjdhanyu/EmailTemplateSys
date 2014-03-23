import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class TestPlainTextSegment {
    @Test
    public void plainTextEvaluateAsIs() throws Exception {
        Map<String, String> variables = new HashMap<String, String>();
        String text = "abc def";
        assertEquals(text, new PlainText(text).evaluate(variables));
    }
}
