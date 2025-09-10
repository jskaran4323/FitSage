package fitsage.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fitsage.model.BodyParameter;
import fitsage.model.Meal;
import fitsage.model.Workout;
import fitsage.services.AIService;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
//  Most of the prompt are hardcoded just ot learn the behaviour of AI.
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
    public List<Workout> generateWorkoutPlan(BodyParameter params) {
        String prompt = String.format("""
  
Generate a FULL workout plan as a JSON array of exercises.

Schema (every field MUST be valid JSON and properly typed):
{
  "name": "string",
  "type": "string",
  "sets": int,
  "reps": int,
  "weight": int,
  "duration": int,
  "caloriesBurned": int
}

Rules:
- Return ONLY a JSON array (no markdown, no ```).
- All numbers must be integers, not strings or words.
- Do NOT use phrases like "as many as possible". If unknown, put a number (e.g., 10).
- Include 5–8 exercises covering different muscle groups.

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
            String text = json.at("/candidates/0/content/parts/0/text").asText("[]");
            String clean = cleanJson(text);
            return Arrays.asList(mapper.readValue(clean, Workout[].class));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI workout response: " + response, e);
        }
    }

    @Override
    public List<Meal> generateMealPlan(BodyParameter params) {
        String prompt = String.format("""
             Generate a FULL daily meal plan as a JSON array of meals.
    Each meal must follow this schema:
    {
      "name": "string",
      "calories": 600,
      "protein": 40,
      "carbs": 50,
      "fats": 20
    }

    Rules:
    - Return ONLY a JSON array (no markdown, no explanations).
    - Include 3–5 meals for the day (breakfast, lunch, dinner, optional snacks).
    - Adapt portions to user's dietary preference and goal.

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
            String text = json.at("/candidates/0/content/parts/0/text").asText("[]");
            String clean = cleanJson(text);
            return Arrays.asList(mapper.readValue(clean, Meal[].class));
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
           // Remove markdown ```json ... ```
    String clean = text.replaceAll("```json", "")
    .replaceAll("```", "")
    .trim();

// Replace common bad outputs like "as many as possible"
clean = clean.replaceAll("(?i)as many as possible", "10");

return clean;

    }
}
