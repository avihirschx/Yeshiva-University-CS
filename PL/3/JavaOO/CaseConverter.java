public class CaseConverter implements CaseConverterInterface{

    @Override
    public String process(String text) {
        return text.toLowerCase();
    }
}