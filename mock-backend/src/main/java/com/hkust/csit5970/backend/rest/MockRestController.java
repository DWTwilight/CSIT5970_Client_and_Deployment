package com.hkust.csit5970.backend.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class MockRestController {
    private final Map<String, Job> jobMap = new ConcurrentHashMap<>();

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadVideo(@RequestParam("video") MultipartFile videoFile) throws IOException {
        long totalBytes = 0;
        byte[] buffer = new byte[8192]; // 8KB缓冲区（最佳实践参考[5](@ref)）

        try (InputStream inputStream = videoFile.getInputStream()) {
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                totalBytes += bytesRead;
                log.info("Received {} bytes in chunk (Total: {})", bytesRead, totalBytes);
            }
        }

        log.info("Final total bytes received: {}", totalBytes);
        String jobId = UUID.randomUUID().toString();
        jobMap.put(jobId, new Job(jobId, 0, JobStatus.PENDING)); // 存储初始状态
        return ResponseEntity.ok(Map.of("job_id", jobId));
    }

    @GetMapping("/job/{job_id}")
    public ResponseEntity<?> getJobStatus(
            @PathVariable("job_id") String jobId,
            ObjectMapper objectMapper
    ) throws Exception {
        // in-progress
        var job = jobMap.get(jobId);
        if (Objects.isNull(job)) {
            var errorResponse = Map.of("status", 2, "message", "Job not found");
            return ResponseEntity.ok(errorResponse);
        }
        if (job.getCounter() < 10) {
            var inProgressResponse = Map.of("status", 1, "frames", 1000, "processed_frames", job.getCounter() * 100);
            job.setCounter(job.getCounter() + 1);
            return ResponseEntity.ok(inProgressResponse);
        }

        // success
        ClassPathResource resource = new ClassPathResource("mock/mock_query_job.json");
        try (InputStream inputStream = resource.getInputStream()) {
            var response = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {
            });
            return ResponseEntity.ok(response);
        }
    }

    enum JobStatus {
        SUCCESS,
        PENDING,
        FAILED
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class Job {
        private String id;
        private int counter;
        private JobStatus status;
    }
}
