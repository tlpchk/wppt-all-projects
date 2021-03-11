import java.io.IOException;
import java.net.*;

public class Z2Receiver {
    private static final int datagramSize = 50;
    private static final int buffer_size = 16;

    private InetAddress localHost;
    private int destinationPort;
    private DatagramSocket socket;

    private ReceiverThread receiver;
    private RetransmissionThread retransmission;
    private ProcessingThread processing;

    private int completed_blocks;
    private int completed_bytes;
    private char[] buffer;

    private Z2Receiver(int myPort, int destPort)
            throws Exception {
        localHost = InetAddress.getByName("127.0.0.1");
        destinationPort = destPort;
        socket = new DatagramSocket(myPort);
        receiver = new ReceiverThread();
        retransmission = new RetransmissionThread();
        processing = new ProcessingThread();
        buffer = new char[buffer_size];
        clearBuffer();
        completed_blocks = 0;
    }

    void clearBuffer(){
        for (int i = 0; i < buffer_size; i++) {
            buffer[i] = 0;
        }
        //System.out.println("clear buffer");
    }

    class ReceiverThread extends Thread {
        int number;
        byte[] data;

        DatagramPacket packet;
        public void run() {
            completed_bytes = completed_blocks * buffer_size;

            try {
                while (true) {
                    data = new byte[datagramSize];
                    packet = new DatagramPacket(data, datagramSize);
                    socket.receive(packet);

                    Z2Packet p = new Z2Packet(packet.getData());
                    number = p.getIntAt(0);
                    if((buffer[number % buffer_size] == 0)
                            && (completed_bytes <= number)
                            && (number < (completed_bytes + buffer_size))){
                        buffer[number % buffer_size] = (char) p.data[4];
                        //System.out.println("Remember packet #"+ number + " (" + (char) p.data[4] + ")");
                    }

                }
            } catch (Exception e) {
                System.out.println("Z2Receiver.ReceiverThread.run: " + e);
            }
        }

    }

    class RetransmissionThread extends Thread {
        DatagramPacket packet;
        int i;

        public void run() {

            while (true) {
                try {
                    sleep(2 * Z2Forwarder.maxDelay);
                    for (i = 0; i < buffer_size; i++) {
                        if (buffer[i] == 0) {

                            Z2Packet p = new Z2Packet(4 + 1);
                            p.setIntAt(i + completed_blocks * buffer_size, 0);
                            p.data[4] = (byte) 0;

                            packet = new DatagramPacket(p.data, p.data.length,localHost,destinationPort);
                            packet.setPort(destinationPort);
                            socket.send(packet);
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ProcessingThread extends Thread {
        int i;
        int iterator;

        public void run() {
            iterator = 0;
            while (true) {
                try {
                    sleep(2 * Z2Forwarder.maxDelay);
                    for (i = 0; i < buffer_size; i++) {
                        if(buffer[iterator % buffer_size] != 0){
                            System.out.println("Receive: " + buffer[iterator % buffer_size]);
                            iterator++;
                            if(iterator == completed_bytes + buffer_size){
                                clearBuffer();
                                completed_blocks++;
                                completed_bytes = completed_blocks*buffer_size;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args)
            throws Exception {
        Z2Receiver receiver = new Z2Receiver(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));

        receiver.receiver.start();
        receiver.retransmission.start();
        receiver.processing.start();
    }


}
