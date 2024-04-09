import speech_recognition as sr

r=sr.Recognizer()


 
with sr.Microphone() as source:
    print("Say Something I am Listening")
    r.adjust_for_ambient_noise(source, duration=0.2)
    audio=r.listen(source)
    
try:
    text=r.recognize_google(audio)
    print("You said: {}".format(text))
    
except:
    print("Sorry, Can't Listen")

print(text)       