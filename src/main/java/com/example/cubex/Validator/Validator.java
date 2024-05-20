package com.example.cubex.Validator;

import javafx.print.PageOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean noNumber;

    public static boolean isValidMail(String mail) {
        Pattern pattern =
                Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)" + // INDICAMOS QUE PUEDA TENER CARACTERES MAY,MIN. NUM, ETC
                        "*@" // LUEGO DEBE CONTENER UN @
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]{2,})$"); //DEBE TENER UN PUNTO Y LUEGO TENER MIN 2 CARACTERES DESPUES DE ESE PUNTO
        // LLAMAMOS A LA CLASE PATTERN Y USAMOS EL METODO MATCHER. Y PASAMOS COMO PARAMETRO EL CORREO
        Matcher matcher = pattern.matcher(mail);
        // SI ENCUENTRA COINCIDENCIA CON EL CORREO Y LA EXPRESION REGULAR
        return matcher.find();
    } // VALIDAR CON EXPRESIONES REGULARES EL MAIL (texto) @ (texto) . (texto)

    public static boolean isValidPassword(String password) {
        if (password.length() >= 8) { // TIENE QUE TENER MINIMO 8 CARACTERES
            boolean mayuscula = false;
            boolean numero = false;
            boolean especial = false;

            // TIENE QUE CONTENER CARACTERES ESPECIALES
            Pattern special = Pattern.compile("[?!¡@¿.,´)]");
            Matcher hasSpecial = special.matcher(password);

            // RECORRER LA CONTRASEÑA ARA VALIDAR QUE TIENE TODOS LOS REQUISITOS
            for (int i = 0; i < password.length(); i++) {
                char l = password.charAt(i);
                // SE USA LA CLASE Character PARA OBTENER INFORMACION SOBRE LOS CARACTERES
                if (Character.isDigit(l)) {// CONTIENE MINIMO UN NUMERO.
                    numero = true;
                }
                if (Character.isUpperCase(l)) { // CONTIENE MINIMO UNA MAYUSCULA
                    mayuscula = true;
                }
                if (hasSpecial.find()) { // CONTIENE CARACTERES ESPECIALES
                    especial = true;
                }
            }
            return numero && mayuscula && especial;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contraseña no válida.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese una contraseña válida.\nFor example : Ps.contains(8).");
            alert.showAndWait();
            return false;
        }
    } // VALIDAR LA CONTRASEÑA


    public static boolean isValidCard(long cardNumber) {
        int sumaPares = 0, imparMulti, digito, sumaImpares = 0;
        boolean isValid = false;
        // noNumber = true;
        // CONVERTIR EL NUMERO A CADENA PARA PODER OPERAR MEJORAR Y SE LE CONCATENA UN CERO DELANTE PARA QUE EN EL BUCLE
        // EL 1 SEA EL PRIMER NUMERO DEL CARDNUMBER. POR EJEMPLO, SI EL CARDNUMBER EMPIEZA POR 4, SE LE AÑADE UN 0 (04)
        // Y EN EL BUCLE EL PRIMERO QUE ES NUMERO IMPAR SEA 4
        String cardString = 0 + String.valueOf(cardNumber);

        /*if (!isNumber(cardString)) {
            noNumber = false;
        }*/
        // SE USA EL ALGORITMO DE LUHN PARA VALIDAR LA TARJETA
        // SE VERIFICA QUE LA LONGUITUD DEL NUMERO DE LA TARJETA ESTE ENTRE 16 Y 19
        if (cardString.length() <= 19 && cardString.length() >= 16) {
            // SE EMPIEZA EN 1 Y SE RECORRE UNO MENOS POR EL 0 QUE SE AÑADIO ANTERIORMENTE
            for (int i = 1; i <= cardString.length() - 1; i++) {
                // SE UTILIZA LA CLASE CHARACTER PARA OBTENER EL VALOR NUMERICO DEL CARACTER DE LA POSICION i
                // CONVIERTE EL CARACTER EQUIVALENTE A NUMERICO, POR EJEMPLO '5' SE CONVIERTE EN 5
                digito = Character.getNumericValue(cardString.charAt(i));
                if (i % 2 == 0) { // SI EL INDICE ES PAR SE SUMA Y SI ES IMPAR SE MULTIPLICA POR 2 (SIGUIENDO EL PATRON 2 1 2 1...)
                    sumaPares += digito;
                } else {// SE MULTIPLICA POR 2 LOS DIGITOS DE LA POSICION IMPAR
                    imparMulti = digito * 2;

                    // SE SUMA LOS RESULTADOS DE LA MULTIPLICACION AL TOTAL
                    if (imparMulti >= 10) { // SI LA MULTIPLICACION ES MAYOR O IGUAL A 10 SE RESTA 9 AL RESULTADO
                        imparMulti -= 9;
                        sumaImpares = sumaImpares + imparMulti;
                        // SE RESTA NUEVE PORQUE CUANDO ES MAYOR DE 10 SE SUMA LOS DOS NUMERO Y PARA HACERLO MAS SENCILLO
                        // SE RESTA 9. POR EJEMPLO, SI DA 14 SE SUMA 1 + 4 = 5, QUE ES LO MISMO QUE SI RESTAMOS 14 - 9 = 5
                    } else {
                        sumaImpares = sumaImpares + imparMulti;
                    }
                }//if
            }//for
            if ((sumaPares + sumaImpares) % 10 == 0) {
                isValid = true;
            } // SI LA SUMA TOTAL ES DIVISIBLE POR 0, LA TARJETA ES VALIDA
        } else {
            // SI LA LONGUITUD NO ES VALIDA O TIENE ESPACIOS, SE MUESTRA UN MENSAJE DE ERROR
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Numero de tarjeta no válida.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese un numero de tarjeta válida.\nFor example : 4000123456789017");
            alert.showAndWait();
        }
        return isValid;
    } // VALIDAR NUMERO DE LA TARJETA


    public static boolean isValidCvc(int cvcNumber) {
        int sumaPares = 0, imparMulti, digito, sumaImpares = 0;
        boolean isValid = false;
        // noNumber = true;
        // SE APLICA LOS MISMOS PASOS Y EL ALGORITMO DE LUHN PARA VALIDAR EL CVC
        String cvcString = 0 + String.valueOf(cvcNumber);
        /*if (!isNumber(cvcString)) {
            noNumber = false;
        }*/
        // SE VERIFICA QUE LA LONGUITUD DEL CVC DE LA TARJETA ESTE ENTRE 3 Y 4
        if (cvcString.length() <= 4 && cvcString.length() >= 3) {
            for (int i = 1; i <= cvcString.length() - 1; i++) {
                digito = Character.getNumericValue(cvcString.charAt(i));
                if (i % 2 == 0) {
                    sumaPares += digito;
                } else {
                    imparMulti = digito * 2;
                    if (imparMulti >= 10) {
                        imparMulti -= 9;
                        sumaImpares = sumaImpares + imparMulti;
                    } else {
                        sumaImpares = sumaImpares + imparMulti;
                    }
                }//if
            }//for
            if ((sumaPares + sumaImpares) % 10 == 0) {
                isValid = true;
            } // SI LA SUMA TOTAL ES DIVISIBLE POR 0, EL CVC ES VALIDA
        } else {
            // SI LA LONGUITUD NO ES VALIDA O TIENE ESPACIOS, SE MUESTRA UN MENSAJE DE ERROR
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("CVC de la tarjeta no válida.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese un cvc de la tarjeta válida.\nFor example : 670.");
            alert.showAndWait();
        }
        return isValid;
} // VALIDAR NUMERO DE CVC


    public static boolean isValidNameCard(String fullName) {
        //DEBE CONTENER DESDE EL PRINCIPIO (^) HASTA EL FINAL ($) AL MENOS TRES CARACTERES SEGUIDO OPCIONALMENTE
        // POR CERO O UN GRUPO DE UN ESPACIO Y DOS O MAS LETRAS (para nombres compuestos) Y SEGUIDO DE UNO O MAS
        // GRUPOS DE UN ESPACIO Y DOS O MAS LETRAS (permitiendo asi nombres como Maria del Carmen Garcia Fernandez)
        Pattern pattern = Pattern.compile("^[a-zA-Z]{3,}(\\s[a-zA-Z]+)*\\s[a-zA-Z]{2,}$");
        // LLAMAMOS A LA CLASE PATTERN Y USAMOS EL METODO MATCHER. Y PASAMOS COMO PARAMETRO EL CORREO
        Matcher matcher = pattern.matcher(fullName);
        // SI ENCUENTRA COINCIDENCIA CON EL CORREO Y LA EXPRESION REGULAR
        return matcher.find();
    } // VALIDAR NOMBRE DEL PROPIETARIO DE LA TARJETA

    public static boolean isNumeric(String text) {
        String regex = "^[0-9]*$";
        if (!text.matches(regex)) {
            return false;
        } else {
            return true;
        }
    }// VALIDAR QUE SOLO TENGA NUMEROS

    public static boolean isValidMY(String aux) {
        Pattern pattern = Pattern.compile("\\d{2}/\\d{2}"); // DOS NUMEROS SEGUIDO DE UNA BARRA Y OTROS DOS NUMEROS
        Matcher matcher = pattern.matcher(aux);
        return matcher.find();
    } // VALIDAR FECHA VENCIMIENTO

    public static boolean isValidTime (TextField detailTimeTxt){
        boolean isValid = false;
        String time = detailTimeTxt.getText(); // TIEMPO INGRESADO
        time = time.replace(',', '.'); // SE REEMPLAZA LA COMA POR EL PUNTO

        String regex = "^([01]):[0-5][0-9]\\.[0-9]{1,2}$"; // COMIENZA CON UN NUMERO ENTRE 0 Y 1 SEGUIDO DE :
                                                         // SEGUIDO DE UN DIGITO ENTRE 0-5 CON UN DIGITO ENTRE 0-9
                                                         // SEGUIDO DE UN CARACTER CON DOS DIGITO ENTRE 0-9 Y OTRO OPCIONAL
        // COMPROBAR SI EL TIEMPO CUMPLE EL FORMATO
        if (time.matches(regex)) {
            /* ESTABLECES MINUTOS Y SEGUNDOS */
            int indiceMinutos = time.indexOf(":");
            String subMinutos = time.substring(0, indiceMinutos);
            String subSeconds = time.substring(indiceMinutos + 1, indiceMinutos + (time.length() - indiceMinutos));
            int minutes = Integer.parseInt(subMinutos);
            double seconds = Double.parseDouble(subSeconds);

            // VERIFICAR QUE LOS MINUTOS Y SEGUNDOS ESTEN EN UN RANDO CORRECTO (0-59)
            if (minutes <= 1 && minutes >=0 && seconds >= 0 && seconds <= 59) {
                isValid = true;
            }
        }
        return isValid;
    }

}
