import jellyfish
from fuzzywuzzy import fuzz   
import Soundex

code1 = Soundex.soundex_generator("I am fine Thank You")
print(code1)
code2 = Soundex.soundex_generator("I am fine")
print(code2)
print('fuzz ratio for code1 and code2')
print(fuzz.token_sort_ratio(code1,code2))

acc = jellyfish.jaro_similarity(code1, code2)
print(acc)

