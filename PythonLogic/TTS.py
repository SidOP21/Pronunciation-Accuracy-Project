import pyttsx3


def SpeakText(command):
	# Initialize the engine
	engine = pyttsx3.init()
	engine.say(command)
	engine.runAndWait()
	
text = input("Write something to listen")
SpeakText(text) 
print(text)
	
	
 
 



