package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    public void testGetStats() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(40L, response.getCount_mutant_dna());
        assertEquals(100L, response.getCount_human_dna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    public void testGetStats_DivisionByZero() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(10L, response.getCount_mutant_dna());
        assertEquals(0L, response.getCount_human_dna());
        assertEquals(0.0, response.getRatio()); // Should handle division by zero
    }
}
