package com.cybersentinel.api_intermedia;

import java.util.List;

public record CyberResponseDTO(
    String prediccion,
    Double confianza,
    Integer ranking,
    Double score,
    List<String> recomendaciones,
    String justificacion
) {}