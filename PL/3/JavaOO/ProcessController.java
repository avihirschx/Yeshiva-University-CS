import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ProcessController {

    private void parse(String[] command) {
        if (command[0] != null) {
            if (command[0].equals("wc")) {
                //simple wc command.
                HashMap<String, Integer> result = this.wc(new File(command[1]));
                for (String str : result.keySet()) {
                    System.out.println(str + "\t" + result.get(str));
                }
            } else if (command[0].equals("grep")) {
                if (command.length == 3) {
                    //simple grep command.
                    String result = this.grep(command[1], new File(command[2]));
                    System.out.println(result);
                } else {
                    //assume grep is piped with WC
                    HashMap<String, Integer> result = this.pipe(command [1], new File(command[2]));
                    for (String str : result.keySet()) {
                        System.out.println(str + "\t" + result.get(str));
                    }
                }
            } else {
                System.out.println("Invalid command.");
            }
        } else
            System.out.println("Requires command line arguments.");
    }

    private HashMap<String, Integer> wc(File file) {
        String text = readFile(file);
        //process file string
        return new DocumentProcessor.DocumentProcessorBuilder()
                .setCConverter(new CaseConverter())
                .setWFinder(new WordFinder())
                .setNonABCFilterer(new NonABCFilterer())
                .setWCounter(new WordCounter())
                .build()
                .process(text);
    }
    private String grep(String searchString, File file) {
        String text = readFile(file);

        //process file string
        HashMap<String, Integer> map = new DocumentProcessor.DocumentProcessorBuilder()
                .setLineFilterer(new LineFilterer(searchString))
                .build()
                .process(text);
        return (String) map.keySet().toArray()[0];
    }
    private HashMap<String, Integer> pipe(String searchString, File file) {
        String text = readFile(file);
        //process file string
        return new DocumentProcessor.DocumentProcessorBuilder()
                .setLineFilterer(new LineFilterer(searchString))
                .setCConverter(new CaseConverter())
                .setWFinder(new WordFinder())
                .setNonABCFilterer(new NonABCFilterer())
                .setWCounter(new WordCounter())
                .build()
                .process(text);
    }
    private String readFile(File file) {
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
    public static void main(String[] args) {
        //construct process controller
        ProcessController pc = new ProcessController();
        pc.parse(args);
    }
}
