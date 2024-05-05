package com.example.cubex.Controller;

import java.util.ArrayList;

public class Scramble {
    private static String[] moves = {"U", "R", "B", "L", "D", "F"};

    public String generateScramble(int random) {
        String scramble = "";
        String lastMove = "";
        for (int i = 0; i < random; i++) {
            String move = moves[(int) (Math.random() * moves.length)];

            //SE ASEGURA QUE EL MOVIMIENTO ACTUAL NO SEA IGUAL AL ANTERIOR,
            //ASI SE EVITA LAS REPETICIONES CONSECUTIVAS DEL MISMO MOVIMIENTO
            while (move.equals(lastMove)) {
                move = moves[(int) (Math.random() * moves.length)];
            }

            //SE AGREGA
            scramble = scramble + " " + move;

            //AGRGAMOS UN GIRO ADICIONAL OPCIONAL (como U', F2,.. )
            if (Math.random() * 2 < 1) {
                scramble = scramble + " " + move + "2";
                lastMove = move + "2";
            } else {
                scramble = scramble + " " + move + "'";
                lastMove = move + "'";
            }

            lastMove = move;
        }
        return scramble;
    }
}
