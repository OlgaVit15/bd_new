package org.example.helpers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Checker {
    public boolean checkPort(String host, int port) {
        try (Socket socket = new Socket()) {
            // Устанавливаем таймаут на 2000 мс (2 секунды)
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
