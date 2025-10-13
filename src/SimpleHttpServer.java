import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/code", new CodeHandler());
        server.createContext("/solve", new SolveHandler());
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(5));
        server.start();
    }

    private static class CodeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String origin = exchange.getRequestHeaders().getFirst("Origin");

            List<String> allowedOrigins = List.of("http://localhost:8888", "http://213.165.95.60");
            if (origin != null && allowedOrigins.contains(origin)) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", origin);
            }

            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            URI requestURI = exchange.getRequestURI();
            Map<String, String> params = parseQuery(requestURI.getRawQuery());

            String year = params.getOrDefault("year", "");
            String day = params.getOrDefault("day", "");
            String part = params.getOrDefault("part", "");

            int status = 200;
            String response;
            try {
                response = getCode(year, day, part);
            } catch (Exception e) {
                status = 404;
                response = "Code not found";
            }

            exchange.sendResponseHeaders(status, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private static class SolveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String origin = exchange.getRequestHeaders().getFirst("Origin");

            List<String> allowedOrigins = List.of("http://localhost:8888", "http://213.165.95.60");
            if (origin != null && allowedOrigins.contains(origin)) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", origin);
            }

            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            URI requestURI = exchange.getRequestURI();
            Map<String, String> params = parseQuery(requestURI.getRawQuery());

            String year = params.getOrDefault("year", "");
            String day = params.getOrDefault("day", "");
            String part = params.getOrDefault("part", "");
            List<String> input = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)
            ).lines().toList();

            int status = 200;
            String response;
            try {
                response = getSolution(year, day, part, input);
            } catch (Exception e) {
                status = 400;
                response = "Invalid input";
            }

            exchange.sendResponseHeaders(status, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private static String getCode(String year, String day, String part) throws IOException {
        Path path = Path.of("src/year" + year + "/Day" + day + "_" + part + ".java");
        List<String> lines = Files.readAllLines(path);
        return String.join("\n", lines);
    }

    private static String getSolution(String year, String day, String part, List<String> input) throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            InstantiationException
    {
        String className = "year" + year + ".Day" + day + "_" + part;
        Class<?> clazz = Class.forName(className);
        Object instance = clazz.getDeclaredConstructor().newInstance();

        String methodName = "part" + part;
        Method method = clazz.getMethod(methodName, List.class);
        Object result = method.invoke(instance, input);

        return result.toString();
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return result;
        }

        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
            String value = pair.length > 1 ? URLDecoder.decode(pair[1], StandardCharsets.UTF_8) : "";
            result.put(key, value);
        }
        return result;
    }
}
