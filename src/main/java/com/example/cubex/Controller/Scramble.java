package com.example.cubex.Controller;

import java.util.ArrayList;

public class Scramble {
    private static String[] moves = {"U", "R", "B", "L", "D", "F"};

    public String generateScramble(int random) {
        String scramble = "", lastMove = null;
        for (int i = 0; i < random; i++) {
            if(i == 16){
                // HACER UN SALTO DE LINEA DESPUES DE 17 MOVIMIENTOS
                scramble = scramble + "\n";
            }
            String move = moves[(int) (Math.random() * moves.length)]; //U

            //SE ASEGURA QUE EL MOVIMIENTO ACTUAL NO SEA IGUAL AL ANTERIOR,
            //ASI SE EVITA LAS REPETICIONES CONSECUTIVAS DEL MISMO MOVIMIENTO
            if(lastMove != null){
                while (move.contains(lastMove)) {
                    move = moves[(int) (Math.random() * moves.length)];
                }
                //AGRGAMOS UN GIRO ADICIONAL OPCIONAL (como U', F2,.. )
                int randomMove = (int) (Math.random() * 100);
                if (randomMove <= 30) {
                    scramble = scramble + " " + move + "'";
                    lastMove = move;
                } else if (randomMove <= 60){
                    scramble = scramble + " " + move + "2";
                    lastMove = move;
                } else {
                    //SE AGREGA
                    scramble = scramble + " " + move;
                    lastMove = move;
                }
            } else {
                //SE AGREGA
                scramble = scramble + " " + move;
                lastMove = move;
            }

        }
        return scramble;
    }


}
