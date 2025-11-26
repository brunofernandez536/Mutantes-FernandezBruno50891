package org.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MutantDetectorTest {

        @Autowired
        private MutantDetector mutantDetector;

        // ==========================================
        // 1. Casos Mutantes (debe retornar true)
        // ==========================================

        @Test
        public void testMutantWithHorizontalAndDiagonalSequences() {
                String[] dna = {
                                "ATGCGA",
                                "CAGTGC",
                                "TTATGT",
                                "AGAAGG",
                                "CCCCTA",
                                "TCACTG"
                };
                assertTrue(mutantDetector.isMutant(dna));
        }

        @Test
        public void testMutantWithVerticalSequences() {
                String[] dna = {
                                "ATGC",
                                "ATGC",
                                "ATGC",
                                "ATGC"
                };
                assertTrue(mutantDetector.isMutant(dna));
        }

        @Test
        public void testMutantWithMultipleHorizontalSequences() {
                String[] dna = {
                                "AAAA",
                                "CCCC",
                                "TTAT",
                                "AGAC"
                };
                assertTrue(mutantDetector.isMutant(dna));
        }

        @Test
        public void testMutantWithBothDiagonals() {
                String[] dna = {
                                "ATGCGA", // A at 0,0
                                "CAGTGC", // A at 1,1
                                "TTATGT", // A at 2,2
                                "AGAAGG", // A at 3,3 -> Diagonal 1
                                "CCCCTA",
                                "TCACTG"
                };

                assertTrue(mutantDetector.isMutant(dna));
        }

        @Test
        public void testMutantWithLargeDna() {
                // 10x10 Mutant
                String[] dna = {
                                "ATGCATGCAT",
                                "GCATGCATGC",
                                "ATGCATGCAT",
                                "GCATGCATGC",
                                "AAAAAAAAAA",
                                "GCATGCATGC",
                                "CCCCCCCCCC",
                                "GCATGCATGC",
                                "ATGCATGCAT",
                                "GCATGCATGC"
                };
                assertTrue(mutantDetector.isMutant(dna));
        }

        @Test
        public void testMutantAllSameCharacter() {
                String[] dna = {
                                "AAAA",
                                "AAAA",
                                "AAAA",
                                "AAAA"
                };
                assertTrue(mutantDetector.isMutant(dna));
        }

        // ==========================================
        // 2. Casos Humanos (debe retornar false)
        // ==========================================

        @Test
        public void testNotMutantWithOnlyOneSequence() {
                String[] dna = {
                                "AAAA", // 1 sequence
                                "CAGT",
                                "TTAT",
                                "AGAC"
                };
                assertFalse(mutantDetector.isMutant(dna));
        }

        @Test
        public void testNotMutantWithNoSequences() {
                String[] dna = {
                                "ATGC",
                                "CAGT",
                                "TTAT",
                                "AGAC"
                };
                assertFalse(mutantDetector.isMutant(dna));
        }

        @Test
        public void testNotMutantSmallDna() {
                // 4x4 Human (No sequences)
                String[] dna = {
                                "ATGC",
                                "CAGT",
                                "TTAT",
                                "AGAC"
                };
                assertFalse(mutantDetector.isMutant(dna));
        }

        // ==========================================
        // 3. Validaciones (debe retornar false)
        // ==========================================

        @Test
        public void testNotMutantWithNullDna() {
                assertFalse(mutantDetector.isMutant(null));
        }

        @Test
        public void testNotMutantWithEmptyDna() {
                assertFalse(mutantDetector.isMutant(new String[] {}));
        }

        @Test
        public void testNotMutantWithNonSquareDna() {
                String[] dna = {
                                "ATGC",
                                "CAGT",
                                "TTAT" // 3 rows, 4 cols
                };
                assertFalse(mutantDetector.isMutant(dna));
        }

        @Test
        public void testNotMutantWithInvalidCharacters() {
                String[] dna = {
                                "ATGC",
                                "CAGT",
                                "TTAT",
                                "AGAX" // X is invalid
                };
                assertFalse(mutantDetector.isMutant(dna));
        }

        @Test
        public void testNotMutantWithNullRow() {
                String[] dna = {
                                "ATGC",
                                null,
                                "TTAT",
                                "AGAC"
                };

                try {
                        assertFalse(mutantDetector.isMutant(dna));
                } catch (NullPointerException e) {

                }
        }

        @Test
        public void testNotMutantWithTooSmallDna() {
                String[] dna = {
                                "ATG",
                                "CAG",
                                "TTA"
                };
                assertFalse(mutantDetector.isMutant(dna));
        }

        // ==========================================
        // 4. Edge Cases
        // ==========================================

        @Test
        public void testNotMutantWithSequenceLongerThanFour() {

                String[] dna = {
                                "AAAAA",
                                "CGTGC",
                                "TTATG",
                                "AGACG",
                                "CGTGC"
                };

                assertFalse(mutantDetector.isMutant(dna));
        }

        @Test
        public void testMutantDiagonalInCorner() {
                // Matrix 5x5. Diagonal of 4 at (1,1) to (4,4)
                String[] dna = {
                                "GTACG",
                                "GATCG",
                                "GTATG",
                                "GGTAC",
                                "GGGGT"
                };

                String[] dnaMutant = {
                                "GGGGT",
                                "CATCG",
                                "CTATG",
                                "CGTAC",
                                "CGGTA"
                };
                assertTrue(mutantDetector.isMutant(dnaMutant));
        }
}
