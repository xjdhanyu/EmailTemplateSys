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
        List<Segment> segments = parser.parseSegments(this.templateText);
        return concatenate(segments);
    }

    private String concatenate(List<Segment> segments) {
        StringBuilder result = new StringBuilder();
        for (Segment segment : segments) {
            result.append(segment.evaluate(variableValues));
        }
        return result.toString();
    }
}
