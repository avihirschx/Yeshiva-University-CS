import java.util.ArrayList;
import java.util.Iterator;

public class NonABCFilterer implements NonABCFiltererInterface {
    @Override
    public ArrayList<String> process(ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>();
        Iterator itr = words.iterator();

        while (itr.hasNext()) {
            String word = (String) itr.next();
            String replacement = word.replaceAll("[^A-Za-z0-9]", "");
            if (!replacement.equals("")){
                result.add(replacement);
            }
        }

        return result;
    }
}
