import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(8080)) {
            System.out.println("Server is running...");
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);
                String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String response;
                try {
                    int n = Integer.parseInt(receivedData.trim());
                    if (n <= 0) {
                        throw new Exception("Invalid input!");
                    }
                    response = "Primes less than " + n + " divisible by 5: " + findPrimes(n);
                } catch (Exception e) {
                    response = "Error: Please enter a positive integer!";
                }
                sendBuffer = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String findPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i < n; i++) {
            if (i % 5 == 0 && isPrime(i)) {
                primes.add(i);
            }
        }
        return primes.toString();
    }
    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
