import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            Scanner scanner = new Scanner(System.in);
            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];
            while (true) {
                System.out.print("Enter a positive integer (n): ");
                String input = scanner.nextLine();
                sendBuffer = input.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 8080);
                clientSocket.send(sendPacket);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server: " + response);
                if (!response.startsWith("Error")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
