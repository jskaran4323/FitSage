package fitsage.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fitsage.model.BodyParameter;
import fitsage.model.Meal;
import fitsage.model.Workout;
import fitsage.services.AIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AIServiceImpl implements AIService {

    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiKey;

    public AIServiceImpl(@Value("${genai.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models")
                .build();
    }

    @Override
    public Workout generateWorkoutPlan(BodyParameter params) {
        String prompt = String.format("""
            Generate ONE workout plan as a valid JSON object.
            Do NOT wrap inside "workoutPlan" or arrays.
            Return ONLY this JSON structure (no explanations, no markdown):

            {
              "name": "string",
              "type": "string",
              "sets": 3,
              "reps": 10,
              "weight": 50,
              "duration": 30,
              "caloriesBurned": 200
            }

            User: %d years old, %s, %d cm, %.1f kg
            Goal: %s
            """,
            params.getAge(),
            params.getGender() == 0 ? "male" : "female",
            (int) params.getHeight(),
            params.getWeight(),
            params.getGoal()
        );

        String response = callGeminiApi(prompt);

        try {
            JsonNode json = mapper.readTree(response);
            String text = json.at("/candidates/0/content/parts/0/text").asText("{}");
            return mapper.readValue(cleanJson(text), Workout.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI workout response: " + response, e);
        }
    }

    @Override
    public Meal generateMealPlan(BodyParameter params) {
        String prompt = String.format("""
            Generate ONE meal plan as a valid JSON object.
            Do NOT wrap inside "meals" or arrays.
            Return ONLY this JSON structure (no explanations, no markdown):

            {
              "name": "string",
              "calories": 600,
              "protein": 40,
              "carbs": 50,
              "fats": 20
            }

            User: %d years old, %s, %d cm, %.1f kg
            Goal: %s
            Dietary preference: %s
            Allergies: %s
            """,
            params.getAge(),
            params.getGender() == 0 ? "male" : "female",
            (int) params.getHeight(),
            params.getWeight(),
            params.getGoal(),
            params.getDietaryPreference(),
            params.getAllergies()
        );

        String response = callGeminiApi(prompt);

        try {
            JsonNode json = mapper.readTree(response);
            String text = json.at("/candidates/0/content/parts/0/text").asText("{}");
            return mapper.readValue(cleanJson(text), Meal.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI meal response: " + response, e);
        }
    }

    // ----------------- Helpers -----------------

    private String callGeminiApi(String prompt) {
        // Build JSON payload using ObjectMapper instead of string concatenation
        ObjectNode payload = mapper.createObjectNode();
        ArrayNode contents = mapper.createArrayNode();
        ObjectNode content = mapper.createObjectNode();
        ArrayNode parts = mapper.createArrayNode();
        parts.add(mapper.createObjectNode().put("text", prompt));
        content.set("parts", parts);
        contents.add(content);
        payload.set("contents", contents);

        return webClient.post()
                .uri("/gemini-1.5-flash:generateContent?key={apiKey}", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String cleanJson(String text) {
        // strip markdown fences if Gemini adds them
        return text.replaceAll("(?s)```json", "")
                   .replaceAll("(?s)```", "")
                   .trim();
    }
}
