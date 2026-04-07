package pe.edu.upeu;

import java.util.InputMismatchException;
import java.util.Scanner;

// 1. Definir un enum con métodos abstractos
enum Operacion {
    SUMA("+") {
        @Override
        public double ejecutar(double a, double b) {
            return a + b;
        }
    },
    RESTA("-") {
        @Override
        public double ejecutar(double a, double b) {
            return a - b;
        }
    },
    MULTIPLICACION("*") {
        @Override
        public double ejecutar(double a, double b) {
            return a * b;
        }
    },
    DIVISION("/") {
        @Override
        public double ejecutar(double a, double b) {
            if (b == 0) {
                throw new ArithmeticException("No se puede dividir por cero.");
            }
            return a / b;
        }
    };

    private final String simbolo; // Para representar el símbolo de la operación

    Operacion(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    // 2. Método abstracto que cada constante del enum debe implementar
    public abstract double ejecutar(double a, double b);

    // Método estático para obtener una operación por su símbolo
    public static Operacion obtenerPorSimbolo(String simbolo) {
        for (Operacion op : Operacion.values()) {
            if (op.getSimbolo().equals(simbolo)) {
                return op;
            }
        }
        return null; // O lanzar una excepción si el símbolo no es válido
    }
}

// Clase principal para probar la calculadora
class CalculadoraEnum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double num1 = 0, num2 = 0;
        String simboloOperacion;
        Operacion operacionSeleccionada;

        System.out.println("--- CALCULADORA AVANZADA CON ENUM ---");

        try {
            System.out.print("Ingresa el primer número: ");
            num1 = scanner.nextDouble();

            System.out.print("Ingresa el segundo número: ");
            num2 = scanner.nextDouble();
            scanner.nextLine(); // Consumir el salto de línea

            System.out.print("Ingresa la operación (+, -, *, /): ");
            simboloOperacion = scanner.nextLine();

            operacionSeleccionada = Operacion.obtenerPorSimbolo(simboloOperacion);

            if (operacionSeleccionada == null) {
                System.out.println("Error: Operación no válida.");
            } else {
                double resultado = operacionSeleccionada.ejecutar(num1, num2);
                System.out.printf("El resultado de %.2f %s %.2f es: %.2f\n", num1, simboloOperacion, num2, resultado);
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar números válidos.");
        } catch (ArithmeticException e) {
            System.out.println("Error aritmético: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
