import java.net.*;
import java.io.*;
import javax.imageio.*;

import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Client {

    public static void main(String args[]) throws UnknownHostException {

        try {
            // Definir dados
            DatagramSocket aSocket = new DatagramSocket(6790);
            DatagramPacket request = null;
            DatagramPacket reply = null;

            byte[] byteMessageSend = null;
            byte[] byteMessageReceive = new byte[1000];
            ByteArrayOutputStream baos = null;

            BufferedImage imageRead = null;

            // pegar endereço e porta do servidor respectivamente
            InetAddress serverAddress = InetAddress.getByName(args[0]);
            int serverPort = 6789;
                //construir um novo fluxo diferente do anterior
                baos = new ByteArrayOutputStream();

                // Ler imagem
                imageRead = ImageIO.read(new File("./Imagem/" + args[1]));

                // converter a imagem lida para o fluxo de bytes
                ImageIO.write(imageRead, "jpg", baos);

                // converter fluxo de bytes para um array para que ele possa ser enviado
                byteMessageSend = baos.toByteArray();

                // Definir pacote para envio
                request = new DatagramPacket(byteMessageSend, byteMessageSend.length, serverAddress, serverPort);
                aSocket.send(request);

                // Definindo pacote para receber solicitação
                reply = new DatagramPacket(byteMessageReceive, byteMessageReceive.length);
                // Definindo tempo de resposta
                aSocket.setSoTimeout(5000);
                aSocket.receive(reply);

                // mostrar resposta do servidor
                System.out.println("Servidor: " + new String(reply.getData()));
                byteMessageReceive = new byte[1000];

            // Fechar socket de comunicação
            aSocket.close();

        } catch (SocketException e) {
            System.out.println("Erro no Socket: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
        }

    }

}
