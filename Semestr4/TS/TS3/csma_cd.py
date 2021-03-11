import time
import os
import random

INTERVAL = 0.1

def printList(list):
    """
    Used to print an array in format of simulation

    :param list: printed list
    :return: None
    """

    print('.', end="")
    for i in list:
        print(i,end="")

    print(".")



def step(chanel,signalA,signalB,indexOfHeadA,indexOfHeadB):

    """
    Responsible for simulating the signal transition from
    one undivided cell of the communication channel to the next one

    :param chanel: channel in which we create simulations
    :param signalA: array that simulates a signal from computer A
    :param signalB: array that simulates a signal from computer A
    :param indexOfHeadA: index of the first element of SignalA
    :param indexOfHeadB: index of the first element of SignalB
    :return: None
    """

    #Signing signals in the right place of the connection channel

    indexOfTailA = indexOfHeadA
    unWrittenElementsA = len(signalA)
    while(indexOfTailA >= 0 and unWrittenElementsA > 0 ):
        if chanel[indexOfTailA] != 'C':
            chanel[indexOfTailA] = signalA[unWrittenElementsA-1]
        indexOfTailA -= 1
        unWrittenElementsA -= 1

    indexOfTailB = indexOfHeadB
    unWrittenElementsB = len(signalB)
    while(indexOfTailB < len(chanel) and unWrittenElementsB > 0 ):
        if chanel[indexOfTailB] != 'C':
            chanel[indexOfTailB] = signalB[len(signalB) - unWrittenElementsB]
        indexOfTailB += 1
        unWrittenElementsB -= 1

    #Mashing of traces that remain from the signals

    if indexOfHeadA < indexOfTailB or indexOfTailA >= indexOfTailB:
        if unWrittenElementsB == 0 and len(signalB) > 0:
            if indexOfTailB < len(chanel):
                chanel[indexOfTailB] = '_'

    if indexOfHeadB > indexOfTailA or indexOfTailB <= indexOfTailA:
        if unWrittenElementsA == 0 and len(signalA) > 0:
                chanel[indexOfTailA] = '_'

    #Collision occurrence

    if (indexOfHeadA < len(chanel)-1) and len(signalA)!= 0 and (chanel[indexOfHeadA + 1] == 'B' or (len(signalB)!= 0 and signalB[0] == 'C') or signalA[0] == 'C'):
        signalA.pop(0)
        signalA.append('C')
        signalB.pop(-1)
        signalB.insert(0,'C')

def fixCollision(chanel,signalA,signalB,randRange):
    """
    Responsible for fixing a collision

    :param chanel: channel in which we create simulations
    :param signalA: array that simulates a signal from computer A
    :param signalB: array that simulates a signal from computer A
    :param randRange: th range from which we random numbers is [1:randRange]
    :return: None
    """
    os.system('clear')
    printList(chanel)

    print("Collision")
    time.sleep(2)
    print("Well, let's random two numbers between 1 and {}".format(randRange))

    forA = random.randint(1,randRange)
    forB = random.randint(1,randRange)

    time.sleep(2)

    if forA == forB:
        os.system('clear')
        print("{} intervals later".format(forA - 1))
        time.sleep(2)
        os.system('clear')
        print('Both intervals are {} :('.format(forA))
        time.sleep(2)
        connection(chanel,signalA,signalB,randRange * 2)
    elif forA < forB:
        os.system('clear')
        print("{} intervals later".format(forA - 1))
        time.sleep(2)
        os.system('clear')
        printList(chanel)
        print("Hi, i'm computer A, my interval is {} :)".format(forA))
        time.sleep(2)
        connection(chanel,signalA,[],randRange)
        time.sleep(2)
        os.system('clear')
        print("{} intervals later".format(forB - forA))
        time.sleep(2)
        os.system('clear')
        printList(chanel)
        print("Hi, i'm computer B, my interval is {} :)".format(forB))
        time.sleep(2)
        connection(chanel,[],signalB,randRange)
        time.sleep(2)
    else:
        os.system('clear')
        print("{} intervals later".format(forB - 1))
        time.sleep(2)
        os.system('clear')
        printList(chanel)
        print("Hi, i'm computer B, my interval is {} :)".format(forB))
        time.sleep(2)
        connection(chanel,[],signalB,randRange)
        time.sleep(2)
        os.system('clear')
        print("{} intervals later".format(forA - forB))
        time.sleep(2)
        os.system('clear')
        printList(chanel)
        print("Hi, i'm computer A, my interval is {} :)".format(forA))
        time.sleep(2)
        connection(chanel,signalA,[],randRange)
        time.sleep(2)

def connection(chanel,signalA,signalB,randRange = 2):
    """
    Is responsible for simulating the CSMA_CD connection between computer A and B

    :param chanel: channel in which we create simulations
    :param signalA: array that simulates a signal from computer A
    :param signalB: array that simulates a signal from computer A
    :param randRange: th range from which we random numbers is [1:randRange]
    :return: None
    """
    copyOfSignalA = list(signalA)
    copyOfSignalB = list(signalB)

    collisionOccurred= False
    i1 = 0
    i2 = len(chanel)-1
    A = len(signalA)
    B = len(signalB)

    # Simulation
    while (i1 < len(chanel) or i2 >= 0) and (A or B):
        step(chanel,signalA,signalB,i1,i2)

        # When the signal has reached the recipient, com "eat" the bits we receive
        if i1 == len(chanel)-1 and len(signalA) != 0 and A:
            signalA.pop(-1)
            i1 -= 1

        if i2 == 0 and len(signalB) != 0 and B:
            signalB.pop(-1)
            i2 += 1

        # Repaint
        os.system('clear')
        printList(chanel)
        time.sleep(INTERVAL)

        # Now computers know that collision was occurrenced
        if chanel[-1] == 'C' or  chanel[0] == 'C':
            collisionOccurred = True

        # Mashing of traces that remain from the signals
        if len(signalA) == 0:
            chanel[-1] = '_'
            A = False

        if len(signalB) == 0:
            chanel[0] = '_'
            B = False

        i1 += 1
        i2 -= 1

    if collisionOccurred:
        fixCollision(chanel,copyOfSignalA,copyOfSignalB,randRange)

    else:
        os.system('clear')
        printList(chanel)
        print('Success!')

#main
chanel = ['_'] * 30
signalA = ['A'] * 6
signalB = ['B'] * 4
connection(chanel,signalA,signalB)
print('Good job!')
