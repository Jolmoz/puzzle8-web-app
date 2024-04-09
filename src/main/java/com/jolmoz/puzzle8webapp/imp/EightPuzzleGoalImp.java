package com.jolmoz.puzzle8webapp.imp;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.search.framework.problem.GoalTest;

/**
 * Esta implementación del objetivo del puzzle permite personalizar la matriz
 * objetivo por medio de un constructor
 */
public class EightPuzzleGoalImp implements GoalTest {
    private final int[] goalState;

    public EightPuzzleGoalImp(int[] goalState) {
        this.goalState = goalState;
    }

    @Override
    public boolean isGoalState(Object state) {
        if (!(state instanceof EightPuzzleBoard)) {
            throw new IllegalArgumentException("El estado no es una instancia de EightPuzzleBoard");
        }

        EightPuzzleBoard board = (EightPuzzleBoard) state;
        int[] currentState = board.getState();

        // Verificar si el estado actual coincide con el estado objetivo
        for (int i = 0; i < currentState.length; i++) {
            if (currentState[i] != goalState[i]) {
                return false;
            }
        }
        return true;
    }
}
