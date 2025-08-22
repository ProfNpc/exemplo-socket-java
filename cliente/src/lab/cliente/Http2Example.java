package lab.cliente;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

/**
 * https://dev.to/philip_zhang_854092d88473/how-to-send-http2-request-28e7
 * https://www.javacodegeeks.com/2020/01/introduction-to-http-2-support-in-java-9.html
 */
public class Http2Example {
    public static void main(String[] args) throws Exception {
        HttpClient client = 
        	HttpClient
        		.newBuilder()
        		.version(HttpClient.Version.HTTP_2)
        		.build();
        
        HttpRequest request = 
        	HttpRequest
        		.newBuilder()
        		.uri(URI.create("https://nic.br"))
        		.build();
        
        HttpResponse<String> response = 
        	client.send(
        		request, 
        		HttpResponse.BodyHandlers.ofString());

        
        //System.out.println(response.toString());
        System.out.println(response.body());
        
    }
}

