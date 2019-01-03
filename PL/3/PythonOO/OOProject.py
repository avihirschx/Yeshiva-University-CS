import sys


class DocumentProcessor(object):
    def __init__(self, builder):
        self.lineFilterer = builder.lineFilterer
        self.caseConverter = builder.caseConverter
        self.nonABCFilterer = builder.nonABCFilterer
        self.wordCounter = builder.wordCounter
        self.wordFinder = builder.wordFinder

    # processes text based on which objects exist:
    def process(self, text):
        if self.nonABCFilterer is None:
            return self.lineFilterer\
                .process(text)
        elif self.lineFilterer is None:
            return self.wordCounter\
                .process(self.nonABCFilterer
                        .process(self.wordFinder
                                 .process(self.caseConverter
                                          .process(text))))
        else:
            return self.wordCounter\
                .process(self.nonABCFilterer
                         .process(self.wordFinder
                                  .process(self.caseConverter
                                           .process(self.lineFilterer
                                                    .process(text)))))


class DocumentProcessorBuilder:

    def build(self):
        return DocumentProcessor(self)

    def __init__(self):
        self.lineFilterer = None
        self.caseConverter = None
        self.nonABCFilterer = None
        self.wordCounter = None
        self.wordFinder = None

    def set_line_filterer(self, linefilterer):
        self.lineFilterer = linefilterer
        return self

    def set_case_converter(self, caseconverter):
        self.caseConverter = caseconverter
        return self

    def set_non_abc_filterer(self, nonabcfilterer):
        self.nonABCFilterer = nonabcfilterer
        return self

    def set_word_counter(self, wordcounter):
        self.wordCounter = wordcounter
        return self

    def set_word_finder(self, wordfinder):
        self.wordFinder = wordfinder
        return self


class LineFilterer:
    # the text to find. will be initialized with class initialization
    toFind = None

    def __init__(self, tofind):
        LineFilterer.toFind = tofind

    @staticmethod
    def process(text):
        lines = [line for line in text.splitlines() if line.find(LineFilterer.toFind) != -1]
        return '\n'.join(lines)


class CaseConverter:
    @staticmethod
    def process(text):
        return text.lower()


class WordFinder:
    @staticmethod
    def process(text):
        return text.split()


class NonABCFilterer:
    @staticmethod
    def process(words):
        result = []
        for word in words:
            result.append("".join([c for c in word if c.isalnum()]))
        return list(filter(None, result))


class WordCounter:
    @staticmethod
    def process(words):
        uniquewords = set(words)
        result = {}
        for word in uniquewords:
            result[word] = words.count(word)
        return result


class ProcessController(object):

        @staticmethod
        def grep(searchstring, text):
            return DocumentProcessorBuilder().set_line_filterer(LineFilterer(searchstring)) \
                .build().process(text)

        @staticmethod
        def pipe(searchstring, text):
            return DocumentProcessorBuilder()\
                .set_line_filterer(LineFilterer(searchstring))\
                .set_case_converter(CaseConverter())\
                .set_word_finder(WordFinder)\
                .set_non_abc_filterer(NonABCFilterer())\
                .set_word_counter(WordCounter)\
                .build().process(text)

        @staticmethod
        def wc(text):
            return DocumentProcessorBuilder()\
                .set_case_converter(CaseConverter())\
                .set_word_finder(WordFinder)\
                .set_non_abc_filterer(NonABCFilterer())\
                .set_word_counter(WordCounter)\
                .build().process(text)

        @staticmethod
        def parse(command):
            if len(command) > 1:
                if command[1] == 'wc':
                    file = command[2]
                    with open(file, 'r') as myfile:
                        text = myfile.read()
                        count = ProcessController.wc(text)
                        for word, freq in count.items():
                            print(word + '\t' + str(freq))
                elif command[1] == 'grep':
                    file = command[3]
                    with open(file, 'r') as myfile:
                        text = myfile.read()
                        if len(command) == 4:
                            print(ProcessController.grep(str(command[2]), text))
                        else:
                            count = ProcessController.pipe(str(command[2]), text)
                            for word, freq in count.items():
                                print(word + '\t' + str(freq))
                else:
                    print('Invalid Command.')
            else:
                print('Requires command line args.')


ProcessController().parse(sys.argv)
