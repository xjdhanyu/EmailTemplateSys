import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template {
    private Map<String, String> variableValues;
    private String templateText;

    public Template(String templateText){
        this.variableValues = new HashMap<String, String>();
        this.templateText = templateText;
    }

    public  void set(String name, String value) {
        this.variableValues.put(name, value);
    }

    public String evaluate() {
        TemplateParse parser = new TemplateParse();
        List<String> segments = parser.parse(this.templateText);
        return concatenate(segments);
    }

    private String concatenate(List<String> segments) {
        StringBuilder result = new StringBuilder();
        for (String segment : segments) {
            append(segment, result);
        }
        return result.toString();
    }

    private void append(String segment, StringBuilder result) {
        if (isVariable(segment)) {
            evaluateVariable(segment, result);
        } else {
            result.append(segment);
        }
    }

    public static boolean isVariable(String segment) {
        return segment.startsWith("${") && segment.endsWith("}");
    }

    private void evaluateVariable(String segment, StringBuilder result) {
        String var = segment.substring(2, segment.length() - 1);
        if (!variableValues.containsKey(var)){
            throw new MissingValueException("No value for " + segment);
        }
        result.append(variableValues.get(var));
    }
}
