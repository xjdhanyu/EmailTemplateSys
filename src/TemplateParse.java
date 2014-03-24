import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TemplateParse {
    public List<Segment> parseSegments(String template) {
        List<Segment> segments = new ArrayList<Segment>();
        int index = collectSegments(segments, template);
        addTail(segments, template, index);
        addEmptyStringIfTemplateWasEmpty(segments);
        return segments;
    }

    private int collectSegments(List<Segment> segs, String src) {
        Pattern pattern = Pattern.compile("\\$\\{[^}]*\\}");
        Matcher matcher = pattern.matcher(src);
        int index = 0;
        while(matcher.find()) {
            addPrecedingPlainText(segs, src, matcher, index);
            addVariable(segs, src, matcher);
            index = matcher.end();
        }
        return index;
    }

    private void addTail(List<Segment> segs, String template, int index) {
        if (index < template.length()) {
            segs.add(new PlainText(template.substring(index)));
        }
    }

    private void addVariable(List<Segment> segs, String src, Matcher m) {
        String variable = src.substring(m.start(), m.end());
        segs.add(new Variable(variable.substring(2, variable.length() - 1)));
    }

    private void addPrecedingPlainText(List<Segment> segs, String src, Matcher m, int index) {
        if (index != m.start()) {
            segs.add(new PlainText(src.substring(index, m.start())));
        }
    }

    private void addEmptyStringIfTemplateWasEmpty(List<Segment> segs) {
        if (segs.isEmpty())
            segs.add(new PlainText(""));
    }
}
