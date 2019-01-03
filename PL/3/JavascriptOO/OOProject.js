'use strict';
let fs = require("fs");

function DocumentProcessor(builder) {
    this.lFilterer = builder.lFilterer;
    this.cConverter = builder.cConverter;
    this.wFinder = builder.wFinder;
    this.nonABCFilterer = builder.nonABCFilterer;
    this.wCounter = builder.wCounter;

    this.process = function(text) {
        if (this.nonABCFilterer == null) {
            return this.lFilterer.process(text);
        }  else if (this.lFilterer == null) {
            return this.wCounter.process(this.nonABCFilterer
                .process(this.wFinder
                    .process(this.cConverter
                        .process(text))));
        } else {
            return this.wCounter.process(this.nonABCFilterer
                .process(this.wFinder
                    .process(this.cConverter
                        .process(this.lFilterer
                            .process(text)))));
        }
    }
}

function DocumentProcessorBuilder() {

    this.lFilterer = null;
    this.cConverter = null;
    this.wFinder = null;
    this.nonABCFilterer = null;
    this.wCounter = null;

    this.setLineFilterer = function(lf) {
        this.lFilterer = lf;
        return this;
    };

    this.setCConverter = function(cc) {
        this.cConverter = cc;
        return this;
    };

    this.setWFinder = function(wf) {
        this.wFinder = wf;
        return this;
    };

    this.setNonABCFilterer = function(abcf) {
        this.nonABCFilterer = abcf;
        return this;
    };

    this.setWCounter = function(wc) {
        this.wCounter = wc;
        return this;
    };

    this.build = function() {
        return new DocumentProcessor(this);
    };
}

//command objects:

function LineFilterer(toFind) {
    this.toFind = toFind;
    this.process = function(text) {
        let lines = text.split(/\r?\n/);
        let i = lines.length;
        while (i--) {
            if (!lines[i].includes(this.toFind)) {
                lines.splice(i, 1);
            }
        }
        return lines.join('\n');
    };
}

function CaseConverter() {
    this.process = function(text) {
        return text.toLowerCase();
    };
}

function WordFinder() {
    this.process = function(text) {
        return text.split(/(\s+)/).filter( word => word.trim().length > 0 );
    };
}

function NonABCFilterer() {
    this.process = function(words) {
        let result = [];
        for(let i = 0; i < words.length; i++) {
            result[i] = words[i].replace(/[^0-9a-z]/gi, '');
        }
        return result.filter(Boolean);
    };
}

function WordCounter() {
    this.process = function(words) {
        let result = {};
        words.forEach(function (key) {
            if (result.hasOwnProperty(key)) {
                result[key]++;
            } else {
                result[key] = 1;
            }
        });
        return result;
    };
}

//process controller:

function ProcessController() {
    this.parse = function(command) {
        if (command[2] != null) {
            if (command[2] === "wc") {
                let result = this.wc(fs.readFileSync(command[3]).toString('utf-8'));
                for (let key in result) {
                    if (result.hasOwnProperty(key)) {
                        console.log(key + "\t" + result[key]);
                    }
                }
            } else if (command[2] === "grep") {
                if (command.length === 5) {
                    console.log(this.grep(command[3],
                        fs.readFileSync(command[4]).toString('utf-8')));
                } else {
                    let result = this.pipe(command[3],
                        fs.readFileSync(command[4]).toString('utf-8'));
                    for (let key in result) {
                        if (result.hasOwnProperty(key)) {
                            console.log(key + "\t" + result[key]);
                        }
                    }
                }
            } else {
                console.log("Invalid Command.");
            }
        } else {
            console.log("Requires command line args.");
        }
    };

    //command functions
    this.grep = function(searchString, text) {
        return new DocumentProcessorBuilder()
            .setLineFilterer(new LineFilterer(searchString))
            .build().process(text);
    };
    this.wc = function(text) {
        return new DocumentProcessorBuilder()
            .setCConverter(new CaseConverter())
            .setWFinder(new WordFinder())
            .setNonABCFilterer(new NonABCFilterer())
            .setWCounter(new WordCounter())
            .build().process(text);
    };
    this.pipe = function (searchString, text) {
        return new DocumentProcessorBuilder()
            .setLineFilterer(new LineFilterer(searchString))
            .setCConverter(new CaseConverter())
            .setWFinder(new WordFinder())
            .setNonABCFilterer(new NonABCFilterer())
            .setWCounter(new WordCounter())
            .build().process(text);
    };

}

//parse command line args

new ProcessController().parse(process.argv);