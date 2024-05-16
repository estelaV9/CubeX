package com.example.cubex.Controller;

public class Scramble {
    private static String[] moves = {"U", "R", "B", "L", "D", "F"}; // ARRAY CON LAS CAPAS QUE TIEN EL CUBO
    public String generateScramble(int random) {
        String scramble = "", lastMove = null;
        for (int i = 0; i < random; i++) {
            if(i == 16){
                // HACER UN SALTO DE LINEA DESPUES DE 17 MOVIMIENTOS
                scramble = scramble + "\n";
            }
            String move = moves[(int) (Math.random() * moves.length)]; // SE COGE UNA CAPA ALEATORIAMENTE

            //SE ASEGURA QUE EL MOVIMIENTO ACTUAL NO SEA IGUAL AL ANTERIOR,
            //ASI SE EVITA LAS REPETICIONES CONSECUTIVAS DEL MISMO MOVIMIENTO
            if(lastMove != null){
                while (move.contains(lastMove)) {
                    move = moves[(int) (Math.random() * moves.length)];
                } // CUANDO LA CAPA SEA IGUAL A LA ANTERIOR CAPA, SE COGE OTRA CAPA ALEATORIAMENTE

                //AGRGAMOS UN GIRO ADICIONAL OPCIONAL (como U', F2,.. )
                int randomMove = (int) (Math.random() * 100);
                if (randomMove <= 30) {
                    // SI ES MENOR O IGUAL A 30 SE LE COLOCA '
                    scramble += " " + move + "'";
                    lastMove = move; // EN LASTMOVE SOLO SE GUARDA LA CAPA, SIN EL GIRO ADICIONAL
                } else if (randomMove <= 60){
                    // SI NO ES MENOR O IGUAL A 30 Y ES MENOR O IGUAL A 60 SE LE COLOCA UN 2
                    scramble += " " + move + "2";
                    lastMove = move;
                } else {
                    // Y SI NO, SE AGREGA SOLO LA CAPA
                    scramble += " " + move;
                    lastMove = move;
                }
            } else {
                // PARA EL PRIMER MOVIMIENTO SOLO SE AGREGA LA CAPA Y SE ESTABLECE LA ULTIMA CAPA
                scramble += " " + move;
                lastMove = move;
            }
        }
        return scramble;
    }
}
