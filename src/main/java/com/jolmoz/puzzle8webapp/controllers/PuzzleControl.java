package com.jolmoz.puzzle8webapp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jolmoz.puzzle8webapp.imp.EightPuzzleGoalImp;
import com.jolmoz.puzzle8webapp.utils.PuzzleUtils;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.uninformed.DepthFirstSearch;

public class PuzzleControl {

    // Seleccion del objetivo para el problema puzzle 8
    private static int[] OBJECTIVE = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

    /**
     * Esta función recibe las acciones que se deben realizar para solucionar el
     * puzzle y devuelve un mapa con listas de cada accion y estado del puzzle con
     * cada accion
     * 
     * @param actions
     * @param state
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Map<String, List<?>> describeSolution(List<Action> actions, int[] state) {
        Map<String, List<?>> puzzleSolved = new HashMap<>();
        puzzleSolved.put("actions", new ArrayList<String>());
        puzzleSolved.put("states", new ArrayList<int[]>());

        // Se recorre cada accion para la solucion

        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i).toString();
            System.out.println("action: " + action);

            // Se encuentra el resultado de aplicar la accion al puzzle
            int[] nextState = PuzzleUtils.stepState(actions.get(i), state).clone();
            PuzzleUtils.printState(state);

            ((List<String>) puzzleSolved.get("actions")).add(action);
            ((List<int[]>) puzzleSolved.get("states")).add(nextState);
        }
        return puzzleSolved;
    }

    /*
     * Este metodo imprime las propiedades de la solución del puzzle
     */
    private static void printInstrumentation(Properties properties) {
        Iterator<Object> keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }

    /**
     * Esta función recibe un arreglo de 9 numeros que representa el estado inicial
     * del puzzle 8 y encuentra una solución usando el método de búsqueda primero en
     * profundidad
     * 
     * @param initialState
     * @return
     * @throws Exception
     */
    public static Map<String, List<?>> solvePuzzle(int[] initialState) throws Exception {

        PuzzleUtils.printState(initialState);
        EightPuzzleBoard puzzleBoard = new EightPuzzleBoard(initialState);

        Problem problem = new Problem(puzzleBoard,
                EightPuzzleFunctionFactory.getActionsFunction(),
                EightPuzzleFunctionFactory.getResultFunction(),
                new EightPuzzleGoalImp(OBJECTIVE));
        DepthFirstSearch search = new DepthFirstSearch(new GraphSearch());
        SearchAgent agent = new SearchAgent(problem, search);

        Map<String, List<?>> puzzleSolved = describeSolution(agent.getActions(), initialState);
        printInstrumentation(agent.getInstrumentation());

        return puzzleSolved;
    }
}
