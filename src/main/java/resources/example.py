import re

lista = []
counter = 1
lista.append("Example for calling python from Java",counter)
counter+=1
print("Example for calling python from Java",counter)
text = ".I 123"
text2 = "pepe" 
text3 = ".W" 
regex = "^\.I\s\d{1,4}$|^.W$"

match = re.match(regex,text3)
if( match == None):
    print("No contiene ER")
else:
    print(match.group())

