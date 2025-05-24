package copilot12354.mpp.client;

import copilot12354.mpp.Flight;
import copilot12354.mpp.services.ServiceException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class FlightsClient {
    private final RestTemplate tpl = new RestTemplate();
    public static final String URL = "http://localhost:8080/flights";

    private <T> T execute(Callable<T> c) {
        try {
            return c.call();
        } catch (ResourceAccessException | HttpClientErrorException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Flight[] getAll() {
        return execute(() -> tpl.getForObject(URL, Flight[].class));
    }

    public Flight getById(int id) {
        return execute(() -> tpl.getForObject(URL + "/" + id, Flight.class));
    }

    public Flight create(Flight f) {
        return execute(() -> tpl.postForObject(URL, f, Flight.class));
    }

    public void update(int id, Flight f) {
        execute(() -> {
            tpl.put(URL + "/" + id, f);
            return null;
        });
    }

    public void delete(int id) {
        execute(() -> {
            tpl.delete(URL + "/" + id);
            return null;
        });
    }
}
