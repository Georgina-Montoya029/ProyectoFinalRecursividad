
package proyectofinalrecursividad;


public class ProyectoFinalRecursividad {
    private static int posicion;
    private static String cadenaGlobal;

    public static int evaluar(String expresion) {
        if (expresion == null || expresion.trim().isEmpty()) {
            throw new IllegalArgumentException("La expresion esta vacia");
        }
       
        cadenaGlobal = expresion.replaceAll("\\s+", "");
        posicion = 0;
        
        int resultado = parsearSumaResta();
        
        if (posicion < cadenaGlobal.length()) {
            throw new IllegalArgumentException("Error de sintaxis en la posicion: " + posicion);
        }
        
        return resultado;
    }
    
    private static int parsearSumaResta() {
        int valor = parsearMultiplicacion();
        
        while (posicion < cadenaGlobal.length()) {
            char operador = cadenaGlobal.charAt(posicion);
            if (operador == '+') {
                posicion++;
                valor += parsearMultiplicacion();
            } else if (operador == '-') {
                posicion++;
                valor -= parsearMultiplicacion();
            } else {
                break;
            }
        }
        return valor;
    }

    private static int parsearMultiplicacion() {
        int valor = parsearFactor();
        
        while (posicion < cadenaGlobal.length()) {
            char operador = cadenaGlobal.charAt(posicion);
            if (operador == '*') {
                posicion++;
                valor *= parsearFactor();
            } else {
                break;
            }
        }
        return valor;
    }

   
    private static int parsearFactor() {
        if (posicion >= cadenaGlobal.length()) {
            throw new IllegalArgumentException("Expresion incompleta");
        }

        char caracter = cadenaGlobal.charAt(posicion);

        if (caracter == '(') {
            posicion++; 
            int valor = parsearSumaResta();
            if (posicion >= cadenaGlobal.length() || cadenaGlobal.charAt(posicion) != ')') {
                throw new IllegalArgumentException("Falta parentesis de cierre ')'");
            }
            posicion++; 
            return valor;
        }
        
        if (caracter == '-') {
            posicion++;
            return -parsearFactor();
        }
        if (caracter == '+') {
            posicion++;
            return parsearFactor();
        }

        if (Character.isDigit(caracter)) {
            int inicio = posicion;
            while (posicion < cadenaGlobal.length() && Character.isDigit(cadenaGlobal.charAt(posicion))) {
                posicion++;
            }
            return Integer.parseInt(cadenaGlobal.substring(inicio, posicion));
        }

        throw new IllegalArgumentException("Caracter inesperado: " + caracter);
    }

    
    public static void main(String[] args) {
        String[] pruebas = {
            "7+10+2",                    
            "1+5*(7-1)+10",              
            "5+7+(1+2*(8-9)+2)-10",       
            "9-2*(75-9+(6+3+(1+2)*1)+7)+9", 
            "2+6*(5*9)",
            "(5*7)*(8+13)",
            "((2+3)*(4+1))-5",
            "2*-3",
            "-(5+3)*2",
            "10+9-A",
            "5+3)"
        };

        for (String p : pruebas) {
            System.out.println(p + " = " + evaluar(p));
        }
    }
    
}
