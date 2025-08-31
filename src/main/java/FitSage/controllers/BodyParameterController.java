package fitsage.controllers;

import fitsage.dto.BodyParameterDto;
import fitsage.mappers.BodyParameterMapper;
import fitsage.model.BodyParameter;
import fitsage.model.User;
import fitsage.repositories.BodyParameterRepository;
import fitsage.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/body-parameters")
@RequiredArgsConstructor
public class BodyParameterController {

    private final BodyParameterRepository bodyParamRepo;
    private final UserRepository userRepo;

    // ➤ Create or update body parameters for a user
    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody BodyParameterDto dto) {
     //seems fishy
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BodyParameter bodyParam = bodyParamRepo.findByUserId(dto.getUserId())
                .orElse(BodyParameter.builder().user(user).build());

        bodyParam.setAge(dto.getAge());
        bodyParam.setHeight(dto.getHeight());
        bodyParam.setWeight(dto.getWeight());
        bodyParam.setGender(dto.getGender());
        bodyParam.setGoal(dto.getGoal());
        bodyParam.setActivityLevel(dto.getActivityLevel());
        bodyParam.setDietaryPreference(dto.getDietaryPreference());
        bodyParam.setAllergies(dto.getAllergies());

        BodyParameter saved = bodyParamRepo.save(bodyParam);
        return ResponseEntity.ok(BodyParameterMapper.toDto(saved));
    }

    // ➤ Get body parameters for a user
    @GetMapping("/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable UUID userId) {
        BodyParameter bp = bodyParamRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Body parameters not found"));
        return ResponseEntity.ok(BodyParameterMapper.toDto(bp));
    }
}
