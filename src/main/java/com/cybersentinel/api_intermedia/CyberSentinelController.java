package com.cybersentinel.api_intermedia;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.util.Map;

@RestController
@RequestMapping("/api/prediccion")
@CrossOrigin(origins = "http://localhost:5173")
public class CyberSentinelController {

    private final RestTemplate restTemplate = new RestTemplate();
    
    // Inyecta la URL de Python desde application.properties (con localhost como respaldo)
    @Value("${python.api.url:http://localhost:8001}")
    private String pythonBaseUrl;

    @PostMapping("/cyber-sentinel")
    public ResponseEntity<?> predecirRiesgo(@Valid @RequestBody CyberRequestDTO request) {
        try {
            // Construimos la URL completa
            String urlFinal = pythonBaseUrl + "/predict/cyber-sentinel";
            
            // Reemplazamos Object.class por tu nuevo CyberResponseDTO
            ResponseEntity<CyberResponseDTO> respuestaPython = restTemplate.postForEntity(urlFinal, request, CyberResponseDTO.class);
            
            return ResponseEntity.ok(respuestaPython.getBody());
            
        } catch (RestClientException e) {
            return ResponseEntity.internalServerError()
                 .body(Map.of("error", "Error al conectar con el modelo IA: " + e.getMessage()));
        }
    }
}
