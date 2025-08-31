package fitsage.services;

import java.util.Map;
import java.util.UUID;

public interface PlanService {
    public Map<String, Object> generatePlans(UUID userId);
}
