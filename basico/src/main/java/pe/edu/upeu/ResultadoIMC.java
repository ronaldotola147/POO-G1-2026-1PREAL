package pe.edu.upeu;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate; // Para registrar la fecha de la medición

// Clase para almacenar el resultado de cada medición de IMC
class ResultadoIMC {
    private double imc;
    private String clasificacion;
    private LocalDate fecha;

    public ResultadoIMC(double imc, String clasificacion) {
        this.imc = imc;
        this.clasificacion = clasificacion;
        this.fecha = LocalDate.now(); // Registra la fecha actual
    }

    @Override
    public String toString() {
        return String.format("Fecha: %s, IMC: %.2f (%s)", fecha, imc, clasificacion);
    }
}

// Clase Persona
class Persona {
    private String nombre;
    private double altura; // en metros
    private double peso;   // en kilogramos
    private ArrayList<ResultadoIMC> historialIMC;

    public Persona(String nombre, double altura, double peso) {
        this.nombre = nombre;
        this.altura = altura;
        this.peso = peso;
        this.historialIMC = new ArrayList<>();
        // Calcula el IMC inicial al crear la persona
        calcularYRegistrarIMC();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public double getAltura() { return altura; }
    public double getPeso() { return peso; }
    public ArrayList<ResultadoIMC> getHistorialIMC() { return historialIMC; }

    public void setAltura(double altura) {
        if (altura > 0) {
            this.altura = altura;
        } else {
            System.out.println("La altura debe ser un valor positivo.");
        }
    }

    public void setPeso(double peso) {
        if (peso > 0) {
            this.peso = peso;
        } else {
            System.out.println("El peso debe ser un valor positivo.");
        }
    }

    // Método para calcular el IMC
    public double calcularIMC() {
        if (altura <= 0 || peso <= 0) {
            System.out.println("No se puede calcular el IMC con altura o peso no válidos.");
            return 0.0;
        }
        return peso / (altura * altura);
    }

    // Método para clasificar el IMC
    public String clasificarIMC(double imc) {
        if (imc < 18.5) return "Bajo peso";
        else if (imc < 24.9) return "Peso normal";
        else if (imc < 29.9) return "Sobrepeso";
        else if (imc < 34.9) return "Obesidad clase I";
        else if (imc < 39.9) return "Obesidad clase II";
        else return "Obesidad clase III";
    }

    // Método para calcular el IMC actual y añadirlo al historial
    public void calcularYRegistrarIMC() {
        double imcActual = calcularIMC();
        if (imcActual > 0) {
            String clasificacionActual = clasificarIMC(imcActual);
            ResultadoIMC resultado = new ResultadoIMC(imcActual, clasificacionActual);
            historialIMC.add(resultado);
            System.out.printf("IMC actual de %s: %.2f (%s) - Registrado.\n", nombre, imcActual, clasificacionActual);
        }
    }

    // Método para mostrar el historial de IMC
    public void mostrarHistorialIMC() {
        System.out.println("\n--- Historial de IMC de " + nombre + " ---");
        if (historialIMC.isEmpty()) {
            System.out.println("No hay registros de IMC.");
        } else {
            for (ResultadoIMC resultado : historialIMC) {
                System.out.println("- " + resultado);
            }
        }
        System.out.println("------------------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- CALCULADORA DE IMC CON HISTORIAL ---");
        System.out.print("Ingresa tu nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingresa tu altura en metros (ej. 1.75): ");
        double altura = 0;
        while (!scanner.hasNextDouble()) {
            System.out.println("Altura inválida. Ingresa un número decimal.");
            scanner.next();
            System.out.print("Ingresa tu altura en metros (ej. 1.75): ");
        }
        altura = scanner.nextDouble();

        System.out.print("Ingresa tu peso en kilogramos (ej. 70.5): ");
        double peso = 0;
        while (!scanner.hasNextDouble()) {
            System.out.println("Peso inválido. Ingresa un número decimal.");
            scanner.next();
            System.out.print("Ingresa tu peso en kilogramos (ej. 70.5): ");
        }
        peso = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea

        Persona persona = new Persona(nombre, altura, peso);
        persona.mostrarHistorialIMC();

        int opcion;
        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Actualizar peso y recalcular IMC");
            System.out.println("2. Actualizar altura y recalcular IMC");
            System.out.println("3. Mostrar historial de IMC");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
                scanner.next();
                System.out.print("Elige una opción: ");
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingresa el nuevo peso en kilogramos: ");
                    double nuevoPeso = 0;
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Peso inválido. Ingresa un número decimal.");
                        scanner.next();
                        System.out.print("Ingresa el nuevo peso en kilogramos: ");
                    }
                    nuevoPeso = scanner.nextDouble();
                    persona.setPeso(nuevoPeso);
                    persona.calcularYRegistrarIMC();
                    break;
                case 2:
                    System.out.print("Ingresa la nueva altura en metros: ");
                    double nuevaAltura = 0;
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Altura inválida. Ingresa un número decimal.");
                        scanner.next();
                        System.out.print("Ingresa la nueva altura en metros: ");
                    }
                    nuevaAltura = scanner.nextDouble();
                    persona.setAltura(nuevaAltura);
                    persona.calcularYRegistrarIMC();
                    break;
                case 3:
                    persona.mostrarHistorialIMC();
                    break;
                case 0:
                    System.out.println("Saliendo de la calculadora de IMC. ¡Cuídate!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}