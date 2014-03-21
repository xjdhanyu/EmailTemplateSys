import org.junit.Before;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestTemplate {
    private Template template;

    @Before
    public void setUp() throws Exception {
        template = new Template("${one}, ${two}, ${three}");
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }

    @Test
    public void multipleVariables() throws Exception {
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void unknownVariablesAreIgnored() throws Exception {
        template.set("doesnotexist", "Hi");
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    public void missingValueRaisesException() throws Exception {
        try{
            new Template("${foo}").evaluate();
            fail("evaluate() should throw an exception if a variable was left without a value.");
        } catch(MissingValueException excepted) {
            assertEquals("No value for ${foo}",  excepted.getMessage());
        }
    }

    @Test
    public void variableGetProcessedJustOnce() throws Exception {
        template.set("one", "${one}");
        template.set("two", "${three}");
        template.set("three", "${two}");
        assertTemplateEvaluatesTo("${one}, ${three}, ${two}");
    }

    private void assertTemplateEvaluatesTo(String excepted) {
        assertEquals(excepted, template.evaluate());
    }
}
