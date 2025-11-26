package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    @Test
    public void testAnalyzeDna_NewMutant() {
        String[] dna = { "AAAA", "CCCC", "TTAT", "AGAC" };
        when(mutantDetector.isMutant(dna)).thenReturn(true);
        when(dnaRecordRepository.findByDnaHash(any())).thenReturn(Optional.empty());

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    public void testAnalyzeDna_NewHuman() {
        String[] dna = { "ATGC", "CAGT", "TTAT", "AGAC" };
        when(mutantDetector.isMutant(dna)).thenReturn(false);
        when(dnaRecordRepository.findByDnaHash(any())).thenReturn(Optional.empty());

        boolean result = mutantService.analyzeDna(dna);

        assertFalse(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    public void testAnalyzeDna_ExistingMutant() {
        String[] dna = { "AAAA", "CCCC", "TTAT", "AGAC" };
        DnaRecord record = new DnaRecord();
        record.setMutant(true);
        when(dnaRecordRepository.findByDnaHash(any())).thenReturn(Optional.of(record));

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
        verify(mutantDetector, never()).isMutant(any());
    }

    @Test
    public void testAnalyzeDna_ExistingHuman() {
        String[] dna = { "ATGC", "CAGT", "TTAT", "AGAC" };
        DnaRecord record = new DnaRecord();
        record.setMutant(false);
        when(dnaRecordRepository.findByDnaHash(any())).thenReturn(Optional.of(record));

        boolean result = mutantService.analyzeDna(dna);

        assertFalse(result);
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
        verify(mutantDetector, never()).isMutant(any());
    }
}
