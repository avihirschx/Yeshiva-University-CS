import sys
import re
import collections
from functools import reduce


def parse(command):
    if len(command) > 1:
        if command[1] == 'wc':
            file = command[2]
            with open(file, 'r') as myfile:
                text = myfile.read()
                count = wc(text)
                for word, freq in count.items():
                    print(word + '\t' + str(freq))
        elif command[1] == 'grep':
            file = command[3]
            with open(file, 'r') as myfile:
                text = myfile.read()
                if len(command) == 4:
                    print(grep(str(command[2]), text))
                else:
                    count = pipe(str(command[2]), text)
                    for word, freq in count.items():
                        print(word + '\t' + str(freq))
        else:
            print('Invalid Command.')
    else:
        print('Requires command line args.')


def grep(search_string, text):
    return reduce((lambda x, y: x + '\n' + y),
                  filter(lambda x: x.find(search_string) != -1,
                         text.splitlines()))


def wc(text):
    return to_dict(list(filter(None,
                               map(lambda x: re.sub("[^A-Za-z0-9]", "", x),
                                   text.lower().split()))))


def pipe(search_string, text):
    return to_dict(list(filter(None,
                               map(lambda x: re.sub("[^A-Za-z0-9]", "", x),
                                   reduce((lambda x, y: x + ' ' + y),
                                          filter(lambda x: x.find(search_string) != -1,
                                                 text.splitlines())).lower().split()))))


def to_dict(words):
    return dict(collections.Counter(words))


parse(sys.argv)
