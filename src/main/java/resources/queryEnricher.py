import sys
from CallingNer import *

if __name__ == "__main__":
    # Read input from Jython call
    input_text = sys.argv[1]  # Receive input parameter
    result = enrich_text_with_ner(input_text)
    print(result)
