package network.chap09;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class GuguClient2 {
    static Random random = new Random();

    static class GuguTask implements Runnable {
        @Override
        public void run() {
            final String HOST = "localhost";
            final int PORT = 9090;
            try (Socket socket = new Socket(HOST, PORT)) {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                // 클라는 10번 반복해서 서버에게 두 정수를 전송하고, 서버가 전송한 결과를 출력한다.
                for (int i = 0; i < 10; ++i) {
                    double a = random.nextInt(9) + 1;
                    double b = random.nextInt(9) + 1;
                    out.writeDouble(a);
                    out.writeDouble(b);
                    out.flush();
                    double result = in.readDouble();
                    System.out.printf("%f x %f = %f\n", a, b, result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i)
            new Thread(new GuguTask()).start();
    }

}

