package za.co.sww.rwars.robot.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import za.co.sww.rwars.robot.model.Battle;
import za.co.sww.rwars.robot.model.Robot;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Implementation of the Robot Wars API client.
 */
public class RobotWarsApiClientImpl implements RobotWarsApiClient {
    
    private static final String BASE_URL = "https://api.rwars.steven-webber.com";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    /**
     * Constructs a new Robot Wars API client implementation.
     */
    public RobotWarsApiClientImpl() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public Battle createBattle() throws RobotWarsApiException {
        return postRequest("/battles", Battle.class);
    }

    @Override
    public Battle getBattle(String battleId) throws RobotWarsApiException {
        return getRequest("/battles/" + battleId, Battle.class);
    }

    @Override
    public Robot registerRobot(String battleId, String robotName) throws RobotWarsApiException {
        String url = String.format("/battles/%s/robots", battleId);
        return postRequest(url, new RobotRequest(robotName), Robot.class);
    }

    @Override
    public Robot getRobotState(String robotId) throws RobotWarsApiException {
        return getRequest("/robots/" + robotId, Robot.class);
    }

    @Override
    public Robot moveRobot(String robotId, int direction, int distance) throws RobotWarsApiException {
        String url = String.format("/robots/%s/move", robotId);
        return postRequest(url, new MoveRequest(direction, distance), Robot.class);
    }

    @Override
    public List<Robot> scanRadar(String robotId, int direction, int range) throws RobotWarsApiException {
        String url = String.format("/robots/%s/radar", robotId);
        return postRequest(url, new ScanRequest(direction, range), List.class);
    }

    @Override
    public boolean fireLaser(String robotId, int direction, int range) throws RobotWarsApiException {
        String url = String.format("/robots/%s/fire", robotId);
        return postRequest(url, new FireRequest(direction, range), Boolean.class);
    }

    @Override
    public Battle startBattle(String battleId) throws RobotWarsApiException {
        String url = String.format("/battles/%s/start", battleId);
        return postRequest(url, Battle.class);
    }

    private <T> T postRequest(String endpoint, Object requestBody, Class<T> responseType) throws RobotWarsApiException {
        String url = BASE_URL + endpoint;
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json");
            
            if (requestBody != null) {
                String requestJson = objectMapper.writeValueAsString(requestBody);
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestJson));
            } else {
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString("{}"));
            }
            
            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return parseResponse(response, responseType);
        } catch (InterruptedException | IOException e) {
            throw new RobotWarsApiException("Failed to process request: " + e.getMessage(), e);
        }
    }

    private <T> T postRequest(String endpoint, Class<T> responseType) throws RobotWarsApiException {
        return postRequest(endpoint, null, responseType);
    }

    private <T> T getRequest(String endpoint, Class<T> responseType) throws RobotWarsApiException {
        String url = BASE_URL + endpoint;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return parseResponse(response, responseType);
        } catch (IOException | InterruptedException e) {
            throw new RobotWarsApiException("Failed to process request: " + e.getMessage(), e);
        }
    }

    private <T> T parseResponse(HttpResponse<String> response, Class<T> responseType) throws RobotWarsApiException {
        try {
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return objectMapper.readValue(response.body(), responseType);
            } else {
                throw new RobotWarsApiException("API request failed with status: " + response.statusCode(), response.statusCode());
            }
        } catch (JsonProcessingException e) {
            throw new RobotWarsApiException("Failed to parse JSON response: " + e.getMessage(), e);
        }
    }

    // Helper classes for requests
    private record RobotRequest(String name) {}
    private record MoveRequest(int direction, int distance) {}
    private record ScanRequest(int direction, int range) {}
    private record FireRequest(int direction, int range) {}
}

