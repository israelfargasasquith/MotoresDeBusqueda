import re
import sys
lista = []
counter = 1
#lista.append("Example for calling python from Java",counter)
counter+=1
#print("Example for calling python from Java",counter)
text = ".I 123"
text2 = "pepe" 
text3 = ".W" 
regex = "^\.I\s\d{1,4}$|^.W$"

match = re.match(regex,text3)
#if( match == None):
    #print("No contiene ER")
#else:
    #print(match.group())

if __name__ == "__main__":
    # Read input from Jython call
    input_text = sys.argv[1]  # Receive input parameter
    result = input_text.upper()
    print(result)