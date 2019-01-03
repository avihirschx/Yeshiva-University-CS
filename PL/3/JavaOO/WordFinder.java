import java.util.ArrayList;
import java.util.Arrays;

public class WordFinder implements WordFinderInterface{
    @Override
    public ArrayList<String> process(String text) {

        return new ArrayList<>(Arrays.asList(text.split("\\s+")));
    }
}