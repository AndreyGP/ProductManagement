package labs.pm.utils.http;

import labs.pm.data.CommodityManager;
import labs.pm.exceptions.CommodityManagerException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Incoming request processor
 * FileName: Worker.java
 * Date/time: 01 май 2021 in 21:21
 *
 * @author Andrei G. Pastushenko
 */

public class Worker extends Thread {

    private static final Map<String, String> CONTENT_TYPES = new HashMap<>() {{
        put("bmp", "image/bmp");
        put("png", "image/png");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("", "text/plain");
        put("csv", "text/csv");
        put("xml", "text/xml");
        put("htm", "text/html");
        put("html", "text/html");
        put("txt", "text/plain");
        put("text", "text/plain");
        put("json", "application/json");
    }};
    private static final String NOT_FOUND_MSG = "PAGE NOT FOUND...";

    private final Socket socket;
    private final String directory;

    Worker(final Socket socket, final String directory) {
        this.socket = socket;
        this.directory = directory;
    }

    @Override
    public void run() {
        try (final InputStream input = socket.getInputStream(); final OutputStream output = socket.getOutputStream()) {
            String url = this.getRequestUrl(input);
            Path filePath = Path.of(this.directory + (url.length() == 1 ? "index.html" : url));
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) response(filePath, output);
            else notFound(output);
        } catch (IOException FuckedUp) {
            FuckedUp.printStackTrace();
        }
    }

    private String getRequestUrl(final InputStream input) {
        return new Scanner(input)
                .useDelimiter("\r\n")
                .next()
                .trim()
                .split(" ")[1];

    }

    private void response(final Path path, final OutputStream output) throws IOException {
        String type = this.getFileExtension(path);
        byte[] fileBytes = Files.readAllBytes(path);
        this.sendHeader(output, 200, "OK", type, fileBytes.length);
        output.write(fileBytes);
    }

    private String getFileExtension(final Path path) {
        String name = path.getFileName().toString();
        int extensionStart = name.lastIndexOf('.');
        return extensionStart == -1 ? "" : name.substring(extensionStart + 1);
    }

    private void sendHeader(final OutputStream output, final int statusCode, final String statusText, final String type, final int length) {
        PrintStream printStream = new PrintStream(output);
        printStream.printf("HTTP/1.1 %s %s%n", statusCode, statusText);
        printStream.printf("Content-Type: %s%n", type);
        printStream.printf("Content-Length: %s%n%n", length);
    }

    private void notFound(final OutputStream output) throws IOException {
        this.sendHeader(output, 404, "Not Found", CONTENT_TYPES.get("text"), NOT_FOUND_MSG.length());
        output.write(NOT_FOUND_MSG.getBytes());
    }
}
