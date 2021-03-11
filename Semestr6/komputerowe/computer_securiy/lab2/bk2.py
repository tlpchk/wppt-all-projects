import collections
import csv
import math

import matplotlib.pyplot as plt
import random

def entropy(X):
    e = 0
    counter = collections.Counter(X)
    for key in counter:
        p_i = counter[key]/len(X)
        e = e - p_i * math.log(p_i)
    return e

def deviation(a,b):
    """
    :param a: pin właściwy
    :param b: pin na kłódce
    """

    changed = 0
    sum = 0
    for i in range(0,4):
        x = min(int(a[i]),int(b[i]))
        y = max(int(a[i]),int(b[i]))

        r = min(y - x, x + 10 - y ) # Mamy cykliczną budowę, dla tego czasami jest wygodniej kręcić sekcję w przeciwną stronę stronę
        if r != 0:
            changed += 1
        sum += r
    return sum * changed


d1 = []
d2 = []
with open('data.csv') as file:
    csv_reader = csv.reader(file,delimiter='-')
    line_count = 0
    for row in csv_reader:
        if line_count != 0:
            a = '%04d' % int(row[2])
            b = '%04d' % int(row[3])
            if row[0] == '1':
                d1.append(deviation(a,b))
            elif line_count != 0 and row[0] == '2':
                d2.append(deviation(a, b))
        line_count += 1
    random.shuffle(d1)
    d1 = d1[:len(d2)]
    d1.sort()
    d2.sort()



plt.ylabel('Odchylenie')
plt.plot([x for x in range(1,len(d1) + 1)], d1,'g--', [x for x in range(1,len(d2) + 1)], d2, 'r--')
plt.legend(['Blokada Elektroniczna','Blokada zamkowa'])
plt.show()

print('Entropia dla kategorji 1: %f' % entropy(d1))
print('Entropia dla kategorji 2: %f' % entropy(d2))

