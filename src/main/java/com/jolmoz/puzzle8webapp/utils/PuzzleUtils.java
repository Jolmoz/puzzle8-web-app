package com.jolmoz.puzzle8webapp.utils;

import aima.core.agent.Action;

public class PuzzleUtils {

    /**
     * Este método imprime en forma de matriz el estado de un puzzle 8 representado
     * por un arreglo de 9 números
     * 
     * @param state
     */
    public static void printState(int[] state) {
        for (int i = 0; i < state.length; i++) {
            System.out.print(" " + state[i]);
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }

    /**
     * Esta función aplica una acción a un puzzle 8 representado por un arreglo de 9
     * de números y retorna el nuevo estado del puzzle
     * 
     * @param action
     * @param state
     * @return
     */
    public static int[] stepState(Action action, int[] state) {
        int index = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                index = i;
                break;
            }
        }
        switch (action.toString()) {
            case "Action[name==Right]":
                state[index] = state[index + 1];
                state[index + 1] = 0;
                break;

            case "Action[name==Left]":
                state[index] = state[index - 1];
                state[index - 1] = 0;
                break;

            case "Action[name==Up]":
                state[index] = state[index - 3];
                state[index - 3] = 0;
                break;

            case "Action[name==Down]":
                state[index] = state[index + 3];
                state[index + 3] = 0;
                break;

            default:
                break;
        }

        return state;
    }
}
