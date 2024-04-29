package com.trip97.modules.plan.controller;

import com.trip97.modules.plan.model.Plan;
import com.trip97.modules.plan.model.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/group/{groupId}/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    /**
     * 여행 계획 목록 조회
     * @param groupId
     * @return 여행 계획 목록
     */
    @GetMapping
    public ResponseEntity<?> getPlans(@PathVariable int groupId) {
        List<Plan> list = planService.selectPlans(groupId);
        if (list != null && !list.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(headers).body(list);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    /**
     * 여행 계획 상세 조회
     * @param groupId
     * @param planId
     * @return 여행 계획 상세 정보
     */
    @GetMapping("/{planId}")
    public ResponseEntity<?> getPlan(@PathVariable int groupId, @PathVariable int planId) {
        Plan plan = planService.selectPlan(planId);
        if (plan != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(headers).body(plan);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    /**
     * 여행 계획 생성
     * @param groupId
     * @param plan
     * @return 생성된 여행 계획
     */
    @PostMapping
    public ResponseEntity<?> createPlan(@PathVariable int groupId, @RequestBody Plan plan) {
        planService.createPlan(plan);
        Plan createdPlan = planService.selectPlan(plan.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(createdPlan);
    }

    /**
     * 여행 계획 수정
     * @param groupId
     * @param planId
     * @param plan
     * @return 수정된 여행 계획 정보
     */
    @PutMapping("/{planId}")
    public ResponseEntity<?> updatePlan(@PathVariable int groupId, @PathVariable int planId, @RequestBody Plan plan) {
        planService.editPlan(plan);
        Plan updatedPlan = planService.selectPlan(planId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(updatedPlan);
    }

    /**
     * 여행 계획 삭제
     * @param groupId
     * @param planId
     * @return Void
     */
    @DeleteMapping("/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable int groupId, @PathVariable int planId) {
        planService.removePlan(planId);
        return ResponseEntity.noContent().build();
    }
}
