package pe.edu.upeu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class RegistroTemperaturas {
    private List<Double> temperaturas;

    public RegistroTemperaturas(List<Double> temps) {
        this.temperaturas = new ArrayList<>(temps);
    }

    public double calcularPromedio() {
        if (temperaturas.isEmpty()) {
            return 0.0;
        }
        return temperaturas.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public double obtenerMaxima() {
        if (temperaturas.isEmpty()) {
            return Double.MIN_VALUE; // O lanzar una excepción
        }
        return Collections.max(temperaturas);
    }

    public double obtenerMinima() {
        if (temperaturas.isEmpty()) {
            return Double.MAX_VALUE; // O lanzar una excepción
        }
        return Collections.min(temperaturas);
    }

    public long contarDiasSobre30() {
        return temperaturas.stream()
                .filter(t -> t > 30.0)
                .count();
    }

    public String determinarTendencia() {
        if (temperaturas.size() < 2) {
            return "No hay suficientes datos para determinar una tendencia.";
        }

        int sube = 0;
        int baja = 0;
        for (int i = 0; i < temperaturas.size() - 1; i++) {
            if (temperaturas.get(i + 1) > temperaturas.get(i)) {
                sube++;
            } else if (temperaturas.get(i + 1) < temperaturas.get(i)) {
                baja++;
            }
        }

        if (sube > baja) {
            return "La tendencia general es a subir.";
        } else if (baja > sube) {
            return "La tendencia general es a bajar.";
        } else {
            return "No hay una tendencia clara (estable o fluctuante).";
        }
    }

    public void mostrarTemperaturas() {
        if (temperaturas.isEmpty()) {
            System.out.println("No hay temperaturas registradas.");
            return;
        }
        System.out.println("Temperaturas registradas: " + temperaturas);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Double> tempsSemana = new ArrayList<>();
        System.out.println("Ingresa las 7 temperaturas diarias (ej. 28.5):");
        for (int i = 0; i < 7; i++) {
            System.out.print("Día " + (i + 1) + ": ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Entrada inválida. Por favor, ingresa un número decimal.");
                scanner.next();
                System.out.print("Día " + (i + 1) + ": ");
            }
            tempsSemana.add(scanner.nextDouble());
        }
        scanner.nextLine(); // Consumir el salto de línea

        RegistroTemperaturas registro = new RegistroTemperaturas(tempsSemana);

        registro.mostrarTemperaturas();
        System.out.printf("Promedio de temperaturas: %.2f°C\n", registro.calcularPromedio());
        System.out.printf("Temperatura máxima: %.1f°C\n", registro.obtenerMaxima());
        System.out.printf("Temperatura mínima: %.1f°C\n", registro.obtenerMinima());
        System.out.println("Días con temperatura sobre 30°C: " + registro.contarDiasSobre30());
        System.out.println("Tendencia de temperaturas: " + registro.determinarTendencia());

        scanner.close();
    }
}