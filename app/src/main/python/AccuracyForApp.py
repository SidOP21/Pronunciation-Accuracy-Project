from fuzzywuzzy import fuzz
from SoundexForApp import soundex_generator

def accuracy(TTSString , STTString):
    
    code1 = soundex_generator(TTSString)
    print(code1)
    code2 = soundex_generator(STTString)
    print(code2)
    print('Fuzz Ratio of code1 and code2 is :' )
    return fuzz.ratio(code1,code2)