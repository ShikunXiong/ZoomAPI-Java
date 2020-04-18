package Utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyHttpHandler implements HttpHandler {

    public static Map<String, String> formData2Dic(String formData) {
        Map<String, String> result = new HashMap<>();
        if (formData == null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item -> {
            final String[] keyAndVal = item.split("=");
            if (keyAndVal.length == 2) {
                final String key = URLDecoder.decode(keyAndVal[0], StandardCharsets.UTF_8);
                final String val = URLDecoder.decode(keyAndVal[1], StandardCharsets.UTF_8);
                result.put(key, val);
            }
        });
        return result;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String response = "hello world";

        try {
            //获得查询字符串(get)
            StringBuilder htmlBuilder = new StringBuilder();
            String queryString = exchange.getRequestURI().getQuery();
            htmlBuilder.append("<html>").
                    append("<body>").
                    append("<h1>").
                    append("Hello, your authorize code is =  ")
                    .append(queryString.substring(5))
                    .append("</h1>")
                    .append("</body>")
                    .append("</html>");
            Map<String, String> queryStringInfo = formData2Dic(queryString);
            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(htmlBuilder.toString().getBytes());
            os.close();
        } catch (IOException ie) {

        } catch (Exception e) {

        }
    }

}