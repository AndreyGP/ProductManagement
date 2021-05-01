package labs.pm.utils.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Incoming web request handler
 * FileName: HTTPServer.java
 * Date/time: 01 май 2021 in 21:10
 *
 * @author Andrei G. Pastushenko
 */

public class HTTPServer {

    private final int port;
    private final String directory;

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        System.out.println(args[1]);
        String directory = args[1];
        new HTTPServer(port, directory).start();
    }

    public HTTPServer(final int port, final String directory) {
        this.port = port;
        this.directory = directory;
    }

    void start() {
        try (final ServerSocket server = new ServerSocket(this.port)) {
            while (true) {
                Socket socket = server.accept();
                Thread thread = new Worker(socket, this.directory);
                thread.start();
            }
        } catch (IOException FuckedUpIgnore) {/* Do nothing*/}
    }
}
