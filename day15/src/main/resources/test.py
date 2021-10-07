inp = open("input").read().split("\n")
D = len(inp)
discs = []
for line in inp:
    line = line.split(' ')
    discs.append([int(line[3]), int(line[-1][:-1])])
t = 0

def bad(time):
    d2 = [[h for h in g] for g in discs]
    for i in range(D):
        d2[i][1] += time + 1
        d2[i][1] %= d2[i][0]
    for i in range(D):
        if (d2[i][1] + i) % d2[i][0] != 0:
            return True
    print time, d2
    return False

while bad(t):
    t += 1
print "Part 1:", t

