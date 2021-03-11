from PyCRC.CRC32 import CRC32

soh = '01111110'
eot = '01111110'

def encode():
    """
    Packs data using the method of 'bits stuffing' from the input file, adds to the data CRC
    :return:None
    """
    Z = open('Z','r')
    W = open('W','w')

    data = str(Z.read())[:-1]
    crc = str(bin(CRC32().calculate(data))[2:])
    data += crc
    k = 0
    i = 0
    n = len(data)
    while(i < n):
        if data[i] == '1':
            if k == 4:
                i += 1
                data = data[:i] + '0' + data[i:]
                n += 1
                k = 0
            else:
                k += 1
        else:
            k = 0
        i += 1


    W.write(soh + data + eot)

    Z.close()
    W.close()

def decode():
    """
    Unpacks data using the method of 'bits stuffing' to the input file, adds to the data CRC
    :return:None
    """
    Z = open('Z','w')
    W = open('W','r')

    data = str(W.read())
    data = data[8:-8]
    k = 0
    i = 0
    n = len(data)
    while ( i < n ):
        if data[i] == '1':
            if k == 4:
                i += 1
                data = data[:i] + data[i+1:]
                i -= 1
                n -= 1
                k = 0


            else:
                k += 1
        else:
            k = 0

        i += 1

    data = data[:-32]

    Z.write(data)

    Z.close()
    W.close()


encode()
decode()
