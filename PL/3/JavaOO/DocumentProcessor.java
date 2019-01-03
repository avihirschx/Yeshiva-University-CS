import java.util.HashMap;

public class DocumentProcessor {

    private final LineFiltererInterface lFilterer;
    private final NonABCFiltererInterface nonABCFilterer;
    private final CaseConverterInterface cConverter;
    private final WordCounterInterface wCounter;
    private final WordFinderInterface wFinder;

    private DocumentProcessor (DocumentProcessorBuilder builder) {
        this.lFilterer = builder.lFilterer;
        this.cConverter = builder.cConverter;
        this.wFinder = builder.wFinder;
        this.nonABCFilterer = builder.nonABCFilterer;
        this.wCounter = builder.wCounter;
    }

//    public LineFiltererInterface getlFilterer() {
//        return lFilterer;
//    }
//
//    public NonABCFiltererInterface getNonABCFilterer() {
//        return nonABCFilterer;
//    }
//
//    public CaseConverterInterface getcConverter() {
//        return cConverter;
//    }
//
//    public WordCounterInterface getwCounter() {
//        return wCounter;
//    }
//
//    public WordFinderInterface getwFinder() {
//        return wFinder;
//    }

    public HashMap<String, Integer> process(String text) {
        HashMap<String, Integer> result = new HashMap<>();

        if(this.nonABCFilterer == null) {
            result.put(lFilterer.process(text), null);
            return result;
        } else if (this.lFilterer == null) {
            return wCounter.process(nonABCFilterer.process(wFinder.process(cConverter.process(text))));
        } else {
            return wCounter.process(nonABCFilterer.process(wFinder.process(cConverter.process(lFilterer.process(text)))));
        }
    }

    public static class DocumentProcessorBuilder {

        //these are the objects that control what doc processor does.
        //if null, it means nothing was set and that step is not needed.
        //when building, chain calls to relevant setters and then build() finishes.
        private LineFiltererInterface lFilterer = null;
        private CaseConverterInterface cConverter = null;
        private WordFinderInterface wFinder = null;
        private NonABCFiltererInterface nonABCFilterer = null;
        private WordCounterInterface wCounter = null;

        public DocumentProcessorBuilder setLineFilterer (LineFilterer lf) {
            this.lFilterer = lf;
            return this;
        }

        public DocumentProcessorBuilder setCConverter(CaseConverter cc) {
            this.cConverter = cc;
            return this;
        }

        public DocumentProcessorBuilder setWFinder(WordFinder wf) {
            this.wFinder = wf;
            return this;
        }

        public DocumentProcessorBuilder setNonABCFilterer (NonABCFilterer abcf) {
            this.nonABCFilterer = abcf;
            return this;
        }

        public DocumentProcessorBuilder setWCounter(WordCounter wc) {
            this.wCounter = wc;
            return this;
        }

        public DocumentProcessor build() {
            return new DocumentProcessor(this);
        }

    }
}
