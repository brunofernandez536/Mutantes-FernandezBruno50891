package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0)
            return false;

        final int n = dna.length;
        // Optimizacion: Verifica que la longitud de la matriz sea correcta
        if (dna[0].length() != n)
            return false;

        int sequenceCount = 0;

        // Optimizacion 2: Convierte a char[][] para acceso O(1)
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            if (dna[i] == null)
                return false; // Handle null row
            matrix[i] = dna[i].toCharArray();
            // Validacion de caracteres
            for (char c : matrix[i]) {
                if (!VALID_BASES.contains(c)) {
                    return false; // Caracter invalido encontrado
                }
            }
        }

        // Optimizacion: Paso simple
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                char base = matrix[row][col];

                // Optimizacion 3: Verificacion de limites

                // Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    // Verifica los anteriores para evitar superposicion
                    if (col == 0 || matrix[row][col - 1] != base) {
                        if (checkHorizontal(matrix, row, col)) {
                            sequenceCount++;
                            if (sequenceCount > 1)
                                return true; // Optimizacion 1: Termino temprano
                        }
                    }
                }

                // Vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    // Verifica los anteriores para evitar superposicion
                    if (row == 0 || matrix[row - 1][col] != base) {
                        if (checkVertical(matrix, row, col)) {
                            sequenceCount++;
                            if (sequenceCount > 1)
                                return true; // Optimizacion 1: Termino temprano
                        }
                    }
                }

                // Diagonal
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    // Verifica los anteriores para evitar superposicion
                    if (row == 0 || col == 0 || matrix[row - 1][col - 1] != base) {
                        if (checkDiagonal(matrix, row, col)) {
                            sequenceCount++;
                            if (sequenceCount > 1)
                                return true; // Optimizacion 1: Termino temprano
                        }
                    }
                }

                // Anti-Diagonal
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    // Verifica los anteriores para evitar superposicion
                    if (row == 0 || col == n - 1 || matrix[row - 1][col + 1] != base) {
                        if (checkAntiDiagonal(matrix, row, col)) {
                            sequenceCount++;
                            if (sequenceCount > 1)
                                return true; // Optimizacion 1: Termino temprano
                        }
                    }
                }
            }
        }

        return false;
    }

    // Optimizacion 4: Comparacion directa
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }

    private boolean checkDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    private boolean checkAntiDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col - 1] == base &&
                matrix[row + 2][col - 2] == base &&
                matrix[row + 3][col - 3] == base;
    }
}
