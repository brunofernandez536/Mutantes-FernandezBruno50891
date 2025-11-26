package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estadísticas de verificación de ADN")
public class StatsResponse {

    @Schema(description = "Cantidad de ADN mutante encontrado")
    private long count_mutant_dna;

    @Schema(description = "Cantidad de ADN humano encontrado")
    private long count_human_dna;

    @Schema(description = "Ratio de ADN mutante a ADN humano")
    private double ratio;
}
