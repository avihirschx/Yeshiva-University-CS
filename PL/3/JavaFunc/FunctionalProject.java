import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalProject {
    public static void main(String[] args) {
        if (args[0] != null) {
            if (args[0].equals("wc")) {
                //simple wc args.
                String text = readFile(new File(args[1]));
                Map<String, Integer> result = wc(text);
                for (String str : result.keySet()) {
                    System.out.println(str + "\t" + result.get(str));
                }
            } else if (args[0].equals("grep")) {
                if (args.length == 3) {
                    //simple grep args.
                    String text = readFile(new File(args[2]));
                    String result = grep(args[1], text);
                    System.out.println(result);
                } else {
                    //assume grep is piped with WC
                    String text = readFile(new File(args[2]));
                    Map<String, Integer> result = pipe(args [1], text);
                    for (String str : result.keySet()) {
                        System.out.println(str + "\t" + result.get(str));
                    }
                }
            } else {
                System.out.println("Invalid args.");
            }
        } else
            System.out.println("Requires args line arguments.");
    }

    private static String grep(String searchString, String text) {
        return Arrays.stream(text.split(System.getProperty("line.separator")))
                .filter(line -> line.contains(searchString))
                .collect(Collectors.joining("\n"));
    }

    private static Map<String, Integer> wc(String text) {
        return Arrays.stream(text.split(System.getProperty("line.separator")))
                .map(String::toLowerCase)
                .map(s -> Stream.of(s.split("\\s+")))
                .flatMap(word -> word)
                .map(word -> word.replaceAll("[^A-Za-z0-9]", ""))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(x -> 1)));
    }
    private static Map<String, Integer> pipe(String searchString, String text) {
        return Arrays.stream(text.split(System.getProperty("line.separator")))
                .filter(line -> line.contains(searchString))
                .map(String::toLowerCase)
                .map(s -> Stream.of(s.split("\\s+")))
                .flatMap(s -> s)
                .map(word -> word.replaceAll("[^A-Za-z0-9]", ""))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(x -> 1)));
    }

    private static String readFile(File file) {
        //read file into string
        FileInputStream fis = null;
        StringBuilder fileString = new StringBuilder();
        try {
            fis = new FileInputStream(file);
            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                fileString.append((char) content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return fileString.toString();
    }
}

