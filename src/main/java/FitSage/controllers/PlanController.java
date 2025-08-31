package fitsage.controllers;

import fitsage.impl.PlanServiceImpl;
import fitsage.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    @Autowired
    private PlanServiceImpl planService;

    @PostMapping("/generate/{userId}")
    public ResponseEntity<?> generatePlans(@PathVariable UUID userId) {
        return ResponseEntity.ok(planService.generatePlans(userId));
    }
}
