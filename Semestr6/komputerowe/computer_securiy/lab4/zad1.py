import numpy
from collections import Counter
numpy.set_printoptions(threshold=numpy.nan)

def byteToInt(a):
    return int(a,2)

def intToByte(a):
    return format(a,'#010b')[-8:]

def readProb(fileName):
    d = dict()
    f = open(fileName,'r')
    for line in f:
        line = line.replace('\xa0\xa0', '')
        line = line.replace('\n', '')
        line = line.split('\t')
        d[line[0]] = float(line[1])

    return d

def mostCommonElem(arr):
    return Counter(arr).most_common(1)[0][0]

def argMaxList(list):
    return numpy.argwhere(list == numpy.amax(list)).flatten().tolist()

def getElems(array,indices):
    result = []
    for i in range(len(array)):
        if i in indices:
            result.append(array[i])
    return result

def formatCryptograms(cryptograms):
    m = max([len(c) for c in cryptograms])
    for i in range(len(cryptograms)):
        cryptograms[i] = cryptograms[i] + ['0' * 8] * (m - len(cryptograms[i]))
    return cryptograms

def readCryptograms(fileName):
    cryptogramsFile = open(fileName, "r")
    cryptograms = []
    for line in cryptogramsFile:
        if line[0] in ['0', '1']:
            line = line.replace(' \n', '')
            line = line.split(' ')
            cryptograms.append(line)
    return cryptograms

def xorBytes(b1,b2):
    return intToByte(byteToInt(b1) ^ byteToInt(b2))

def xorCryptograms(c1, c2):
    result = []
    n = min(len(c1), len(c2))
    if (len(c1) == n):
        c1 = c1 + ['0' * 8] * (len(c2) - n)
    else:
        c2 = c2 + ['0' * 8] * (len(c1) - n)
    for i in range(0, len(c1)):
        result.append(xorBytes(c1[i],c2[i]))
    return result

def canBeSpace(xoredByte):
    if(xoredByte[0:2] == "01"):
        return True
    else:
        return False

def tableOfSpaceOccurrences(cryptograms):
    n = max([len(c) for c in cryptograms])
    m = len(cryptograms)
    table = numpy.zeros((m, n))
    for i in range(0, m):
        for j in range(0, m):
            if i != j:
                xoredCryptogram = xorCryptograms(cryptograms[i], cryptograms[j])
                for k in range(0, len(xoredCryptogram)):
                    if canBeSpace(xoredCryptogram[k]):
                        table[j][k] += 1
                        table[i][k] += 1
    return table

def subKeys(tableOfSpaceOccurrences,cryptorgams):
    n = numpy.size(tableOfSpaceOccurrences, 1)
    m = numpy.size(tableOfSpaceOccurrences,0)
    subKeys= []

    for i in range(n):
        column = [ cryptograms[j][i] if i < len(cryptograms[j]) else '0'*8 for j in range(m)]
        subKey = getElems(column, argMaxList(tableOfSpaceOccurrences[:, i]))
        print(subKey)
        subKey = xorCryptograms(subKey, ['00100000'] * len(subKey))
        subKeys.append(subKey)

    return subKeys

def key(subKeys):
    key = []
    for arr in subKeys:
        key.append(mostCommonElem(arr))
    return key

def decode(sk,c,p):
    m = []
    for i in range(len(c)):
        d = {}
        char = ''
        maxProb = -1

        for j in range(len(sk[i])):
            temp = chr(byteToInt(xorBytes(c[i],sk[i][j]))).lower()
            #print(temp,maxProb,end=' ')

            if temp not in d:
                d[temp] = 0

            if temp in p:
                d[temp] += p[temp]

            else:
               d[temp] += 0.2

            if d[temp] > maxProb:
                char = temp
                maxProb = d[temp]

        m.append(char)
        #print('')
    return ''.join(m)

prob = readProb('probability')
cryptograms = readCryptograms(fileName="cryptograms")
tableOfSpaceOcc = tableOfSpaceOccurrences(cryptograms)
subKeys = subKeys(tableOfSpaceOcc,cryptograms)
key = key(subKeys)
print(decode(subKeys,cryptograms[0],prob))
