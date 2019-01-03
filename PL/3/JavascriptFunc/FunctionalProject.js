'use strict';
let fs = require("fs");

let grep = function(searchString, text) {
    return text.split(/\r?\n/)
        .filter(line => line.includes(searchString))
        .reduce((a, b) => (a + '\n' + b));
};

let wc = function(text) {
    return text.split(/\r?\n/)
        .map(x => x.toLowerCase())
        .reduce((a, b) => (a + ' ' + b))
        .split(/(\s+)/)
        .filter(word => word.trim().length > 0)
        .map(word => word.replace(/[^0-9a-z]/gi, ''))
        .filter(Boolean)
        .reduce(function(a, b) {
            a[b] = a.hasOwnProperty(b) ? a[b] + 1 : 1;
            return a;
            }, {});
};

let pipe = function(searchString, text) {
    return text.split(/\r?\n/)
        .filter(line => line.includes(searchString))
        .map(x => x.toLowerCase())
        .reduce((a, b) => (a + ' ' + b))
        .split(/(\s+)/)
        .filter(word => word.trim().length > 0)
        .map(word => word.replace("[^A-Za-z0-9]", ""))
        .filter(Boolean)
        .reduce(function(a, b) {
            a[b] = a.hasOwnProperty(b) ? a[b] + 1 : 1;
            return a;
        }, {});
};


let parse = function(command) {
    if (command[2] != null) {
        if (command[2] === "wc") {
            let result = wc(fs.readFileSync(command[3]).toString('utf-8'));
            for (let key in result) {
                if (result.hasOwnProperty(key)) {
                    console.log(key + "\t" + result[key]);
                }
            }
        } else if (command[2] === "grep") {
            if (command.length === 5) {
                console.log(grep(command[3],
                    fs.readFileSync(command[4]).toString('utf-8')));
            } else {
                let result = pipe(command[3],
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

parse(process.argv);
