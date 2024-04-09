import jellyfish
from fuzzywuzzy import fuzz   
import TTS
import STT
import Soundex

code1 = Soundex.soundex_generator('Hello')
print(code1)
code2 = Soundex.soundex_generator('Lehho')
print(code2)
print('fuzz ratio for code1 and code2')
print(fuzz.ratio(code1,code2))
print('Ratio for code1 and code2 Irrespective of occurence of their position')
print(fuzz.token_sort_ratio(code1, code2))

