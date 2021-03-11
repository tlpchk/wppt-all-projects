import java.net.*;

class Z2Sender {
    private static final int datagramSize = 50;
    private static final int buffer_size = 16;

    private InetAddress localHost;
    private int destinationPort;
    private DatagramSocket socket;
    private SenderThread sender;
    private ReceiverThread receiver;

    private char[] buffer;
    private int completed_blocks;
    private boolean nextBlock;

    Z2Sender(int myPort, int destPort)
            throws Exception {
        localHost = InetAddress.getByName("127.0.0.1");
        destinationPort = destPort;
        socket = new DatagramSocket(myPort);
        sender = new SenderThread();
        receiver = new ReceiverThread();
        completed_blocks = 0;
        buffer = new char[buffer_size];
    }

    void clearBuffer(){
        for (int i = 0; i < buffer_size; i++) {
            buffer[i] = 0;
        }
    }

    class SenderThread extends Thread {
        public void run() {
            Z2Packet p;
            DatagramPacket packet;
            int i, x;
            nextBlock = true;
            try {
                for (i = 0; (x = System.in.read()) >= 0; i++) {
                    buffer[i % buffer_size] = (char) x;

                    p = new Z2Packet(4 + 1);
                    p.setIntAt(i, 0);
                    p.data[4] = (byte) x;

                    packet = new DatagramPacket(p.data, p.data.length,
                                    localHost, destinationPort);
                    socket.send(packet);
                    System.out.println("Send: " + (char)x);

                    //buffer is full
                    if(buffer[buffer_size-1] != 0) {
                        nextBlock = false;
                        //wait while block isn't complete
                        while (true) {
                            sleep(Z2Forwarder.maxDelay);
                            if(nextBlock) break;
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println("Z2Sender.SenderThread.run: " + e);
            }
        }

    }


    class ReceiverThread extends Thread {
        Z2Packet p;
        DatagramPacket packet;
        byte[] data;
        int number;
        int completed_bytes;

        public void run() {
            completed_bytes = completed_blocks * buffer_size;
            try {
                while (true) {
                    data = new byte[datagramSize];
                    packet = new DatagramPacket(data, datagramSize);
                    socket.receive(packet);

                    p = new Z2Packet(packet.getData());
                    number = p.getIntAt(0);
                    //Receiver asking for packet #number


                    if(number >= (completed_bytes + buffer_size)){
                        completed_blocks += 1;
                        completed_bytes += buffer_size;
                        clearBuffer();
                        nextBlock = true;
                    }
                    else if(((int) p.data[4] == 0)
                            && (completed_bytes <= number)
                            && (number < (completed_bytes + buffer_size))){

                        p = new Z2Packet(4 + 1);
                        p.setIntAt(number,0);
                        if(buffer[number % buffer_size] != 0) {
                            p.data[4] = (byte) buffer[number % buffer_size];
                            packet = new DatagramPacket(p.data, p.data.length, localHost, destinationPort);
                            socket.send(packet);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Z2Sender.ReceiverThread.run: " + e);
            }
        }

    }


    public static void main(String[] args) throws Exception {

        Z2Sender sender = new Z2Sender(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        sender.sender.start();
        sender.receiver.start();
    }
}
