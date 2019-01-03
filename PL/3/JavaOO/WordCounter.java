import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class WordCounter implements WordCounterInterface{

    @Override
    public HashMap<String, Integer> process(ArrayList<String> words) {

        HashSet<String> uniqueWords = new HashSet<>(words);
        HashMap<String, Integer> result = new HashMap<>();

        int freq;

        for (String word : uniqueWords) {
            freq = Collections.frequency(words, word);
            result.put(word, freq);
        }
        
        return result;
    }
}
