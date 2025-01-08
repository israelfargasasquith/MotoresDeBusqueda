import re

# Input and output file paths
input_file = 'ENRICHED_MED.QRY'  # Replace with your input file path
output_file = 'unique_tags_qry.txt'  # Replace with your desired output file path

# Set to store unique tags
unique_tags = set()

# Read the file line by line
with open(input_file, 'r') as infile:
    for line in infile:
        # Find all tags using regex
        tags = re.findall(r'<(\w+)>', line)
        # Add tags to the set
        unique_tags.update(tags)

# Write unique tags to the output file
with open(output_file, 'w') as outfile:
    for tag in sorted(unique_tags):
        outfile.write(tag + '\n')

print(f"Unique tags have been saved to {output_file}")
