package za.co.sww.rwars.robot.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import za.co.sww.rwars.robot.model.Battle;
import za.co.sww.rwars.robot.model.Robot;
import za.co.sww.rwars.robot.model.RadarResponse;
import za.co.sww.rwars.robot.model.LaserResponse;
import za.co.sww.rwars.robot.model.ErrorResponse;
import za.co.sww.rwars.robot.model.CreateBattleRequest;
import za.co.sww.rwars.robot.model.RegisterRobotRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        this(HttpClient.newHttpClient());
    }
    
    /**
     * Constructs a new Robot Wars API client implementation with a custom HttpClient.
     * This constructor is primarily for testing purposes.
     * 
     * @param httpClient the HTTP client to use for requests
     */
    public RobotWarsApiClientImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules();
    }

    @Override
    public Battle createBattle(CreateBattleRequest request) throws RobotWarsApiException {
        return postRequest("/api/battles", request, Battle.class);
    }

    @Override
    public Battle getBattle(String battleId) throws RobotWarsApiException {
        return getRequest("/api/robots/battle/" + battleId, Battle.class);
    }

    @Override
    public Robot registerRobot(String battleId, String robotName) throws RobotWarsApiException {
        String url = String.format("/api/robots/register/%s", battleId);
        RegisterRobotRequest request = new RegisterRobotRequest(robotName);
        return postRequest(url, request, Robot.class);
    }

    @Override
    public Robot getRobotState(String battleId, String robotId) throws RobotWarsApiException {
        String url = String.format("/api/robots/battle/%s/robot/%s/status", battleId, robotId);
        return getRequest(url, Robot.class);
    }

    @Override
    public Robot moveRobot(String battleId, String robotId, String direction, int blocks) throws RobotWarsApiException {
        String url = String.format("/api/robots/battle/%s/robot/%s/move", battleId, robotId);
        return postRequest(url, new MoveRequest(direction, blocks), Robot.class);
    }

    @Override
    public RadarResponse scanRadar(String battleId, String robotId, int range) throws RobotWarsApiException {
        String url = String.format("/api/robots/battle/%s/robot/%s/radar", battleId, robotId);
        return postRequest(url, new ScanRequest(range), RadarResponse.class);
    }

    @Override
    public LaserResponse fireLaser(String battleId, String robotId, String direction, int range) throws RobotWarsApiException {
        String url = String.format("/api/robots/battle/%s/robot/%s/laser", battleId, robotId);
        return postRequest(url, new FireRequest(direction, range), LaserResponse.class);
    }

    @Override
    public Battle startBattle(String battleId) throws RobotWarsApiException {
        String url = String.format("/api/battles/%s/start", battleId);
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
                // Try to parse error response from server
                String serverMessage = extractServerErrorMessage(response.body());
                String errorMessage = "API request failed with status: " + response.statusCode();
                throw new RobotWarsApiException(errorMessage, response.statusCode(), serverMessage);
            }
        } catch (JsonProcessingException e) {
            throw new RobotWarsApiException("Failed to parse JSON response: " + e.getMessage(), e);
        }
    }
    
    /**
     * Extracts error message from server response body.
     * 
     * @param responseBody the HTTP response body
     * @return the server error message, or null if not parseable
     */
    private String extractServerErrorMessage(String responseBody) {
        if (responseBody == null || responseBody.trim().isEmpty()) {
            return null;
        }
        
        try {
            ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
            return errorResponse.getMessage();
        } catch (JsonProcessingException e) {
            // If we can't parse as JSON, return the raw response body if it's reasonable length
            if (responseBody.length() <= 500) {
                return responseBody;
            }
            return "Unable to parse server error message";
        }
    }

    // Helper classes for requests
    private record MoveRequest(String direction, int blocks) {}
    private record ScanRequest(int range) {}
    private record FireRequest(String direction, int range) {}
}

