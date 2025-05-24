package copilot12354.mpp.client;

import copilot12354.mpp.Flight;
import copilot12354.mpp.services.ServiceException;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.HttpRequest;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class NewFlightsClient
{
    private final RestClient client = RestClient.builder()
            .requestInterceptor(new LoggingInterceptor())
            .build();

    public static final String URL = "http://localhost:8080/flights";

    private <T> T execute(Callable<T> call)
    {
        try
        {
            return call.call();
        } catch (ResourceAccessException | HttpClientErrorException e)
        {
            throw new ServiceException(e);
        } catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    public Flight[] getAll()
    {
        return execute(() -> client.get()
                .uri(URL)
                .retrieve()
                .body(Flight[].class));
    }

    public Flight getById(int id)
    {
        return execute(() -> client.get()
                .uri(URL + "/" + id)
                .retrieve()
                .body(Flight.class));
    }

    public Flight create(Flight f)
    {
        return execute(() -> client.post()
                .uri(URL)
                .contentType(APPLICATION_JSON)
                .body(f)
                .retrieve()
                .body(Flight.class));
    }

    public Flight update(int id, Flight f)
    {
        return execute(() -> client.put()
                .uri(URL + "/" + id)
                .contentType(APPLICATION_JSON)
                .body(f)
                .retrieve()
                .body(Flight.class));
    }

    public void delete(int id)
    {
        execute(() ->
        {
            client.delete()
                    .uri(URL + "/" + id)
                    .retrieve()
                    .toBodilessEntity();
            return null;
        });
    }

    private static class LoggingInterceptor implements ClientHttpRequestInterceptor
    {
        @Override
        public ClientHttpResponse intercept(
                HttpRequest req, byte[] body,
                ClientHttpRequestExecution exec) throws IOException
        {
            System.out.println("→ " + req.getMethod() + " " + req.getURI());
            if (body.length > 0) System.out.println("   body=" + new String(body));
            ClientHttpResponse resp = exec.execute(req, body);
            System.out.println("← status=" + resp.getStatusCode());
            return resp;
        }
    }
}
