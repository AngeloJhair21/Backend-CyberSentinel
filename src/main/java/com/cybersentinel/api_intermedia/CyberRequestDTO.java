package com.cybersentinel.api_intermedia;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CyberRequestDTO (
    @NotNull(message = "Falta intentos_login_fallidos")
    @Min(0) @Max(200) Integer intentos_login_fallidos,

    @NotNull(message = "Falta puertos_abiertos")
    @Min(0) @Max(100) Integer puertos_abiertos,

    @NotNull(message = "Falta vulnerabilidades_criticas")
    @Min(0) @Max(20) Integer vulnerabilidades_criticas,
    
    @NotNull(message = "Falta trafico_anomalo_pct")
    @Min(0) @Max(100) Double trafico_anomalo_pct,

    @NotNull(message = "Falta equipos_afectados")
    @Min(0) @Max(500) Integer equipos_afectados,

    @NotNull(message = "Falta parcheado_pct")
    @Min(0) @Max(100) Double parcheado_pct
) {}


