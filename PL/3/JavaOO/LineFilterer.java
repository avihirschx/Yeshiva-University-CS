import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class LineFilterer implements LineFiltererInterface {
    private String toFind;

    public LineFilterer(String searchString) {
        this.toFind = searchString;
    }

    @Override
    public String process(String text) {
        StringBuilder result = new StringBuilder();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(text.split(System.getProperty("line.separator"))));
        Iterator itr = lines.iterator();
        while(itr.hasNext()) {
            String line = (String)itr.next();
            if (!line.contains(this.toFind))
                itr.remove();
            else {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }
}
