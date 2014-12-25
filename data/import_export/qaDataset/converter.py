def load(datafile):
    with open(datafile) as f:
        lines = [line.rstrip('\n') for line in f]
    return lines

lines = load("question_answer_pairs.txt")
f = open('question_answer_pairs_converted.txt','w')

for line in lines:
    splits = line.split('\t')
    newline = (splits[1] + '; ' + splits[2])
    print(newline)
    f.write(newline + "\n")

f.close()