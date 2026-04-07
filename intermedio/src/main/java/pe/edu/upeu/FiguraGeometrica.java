package pe.edu.upeu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Clase Abstracta FiguraGeometrica
abstract class FiguraGeometrica {
    // Un método abstracto no tiene implementación en la clase abstracta
    public abstract double calcularArea();

    // Puedes tener métodos concretos también
    public void mostrarDescripcion() {
        System.out.println("Esta es una figura geométrica.");
    }
}

// Clase Concreta Circulo
class Circulo extends FiguraGeometrica {
    private double radio;

    public Circulo(double radio) {
        this.radio = radio;
    }

    public double getRadio() { return radio; }
    public void setRadio(double radio) { this.radio = radio; }

    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;
    }

    @Override
    public String toString() {
        return String.format("Círculo (Radio: %.2f) - Área: %.2f", radio, calcularArea());
    }
}

// Clase Concreta Rectangulo
class Rectangulo extends FiguraGeometrica {
    private double base;
    private double altura;

    public Rectangulo(double base, double altura) {
        this.base = base;
        this.altura = altura;
    }

    public double getBase() { return base; }
    public double getAltura() { return altura; }
    public void setBase(double base) { this.base = base; }
    public void setAltura(double altura) { this.altura = altura; }

    @Override
    public double calcularArea() {
        return base * altura;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo (Base: %.2f, Altura: %.2f) - Área: %.2f", base, altura, calcularArea());
    }
}

// Clase Concreta Triangulo
class Triangulo extends FiguraGeometrica {
    private double base;
    private double altura;

    public Triangulo(double base, double altura) {
        this.base = base;
        this.altura = altura;
    }

    public double getBase() { return base; }
    public double getAltura() { return altura; }
    public void setBase(double base) { this.base = base; }
    public void setAltura(double altura) { this.altura = altura; }

    @Override
    public double calcularArea() {
        return (base * altura) / 2;
    }

    @Override
    public String toString() {
        return String.format("Triángulo (Base: %.2f, Altura: %.2f) - Área: %.2f", base, altura, calcularArea());
    }
}

// Clase Principal para probar
class GestorFigurasGeometricas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<FiguraGeometrica> figuras = new ArrayList<>();
        int opcion;

        do {
            System.out.println("\n--- CALCULADORA DE ÁREAS DE FIGURAS ---");
            System.out.println("1. Crear Círculo");
            System.out.println("2. Crear Rectángulo");
            System.out.println("3. Crear Triángulo");
            System.out.println("4. Mostrar todas las figuras y sus áreas");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
                scanner.nextLine(); // Limpiar la entrada inválida
                opcion = -1; // Para que el bucle continúe
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingresa el radio del círculo: ");
                    double radio = 0;
                    try {
                        radio = scanner.nextDouble();
                        if (radio <= 0) throw new IllegalArgumentException("El radio debe ser positivo.");
                        figuras.add(new Circulo(radio));
                        System.out.println("Círculo creado.");
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para el radio.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        scanner.nextLine(); // Consumir el salto de línea
                    }
                    break;
                case 2:
                    System.out.print("Ingresa la base del rectángulo: ");
                    double baseRect = 0;
                    try {
                        baseRect = scanner.nextDouble();
                        if (baseRect <= 0) throw new IllegalArgumentException("La base debe ser positiva.");
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para la base.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("Ingresa la altura del rectángulo: ");
                    double alturaRect = 0;
                    try {
                        alturaRect = scanner.nextDouble();
                        if (alturaRect <= 0) throw new IllegalArgumentException("La altura debe ser positiva.");
                        figuras.add(new Rectangulo(baseRect, alturaRect));
                        System.out.println("Rectángulo creado.");
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para la altura.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        scanner.nextLine(); // Consumir el salto de línea
                    }
                    break;
                case 3:
                    System.out.print("Ingresa la base del triángulo: ");
                    double baseTri = 0;
                    try {
                        baseTri = scanner.nextDouble();
                        if (baseTri <= 0) throw new IllegalArgumentException("La base debe ser positiva.");
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para la base.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("Ingresa la altura del triángulo: ");
                    double alturaTri = 0;
                    try {
                        alturaTri = scanner.nextDouble();
                        if (alturaTri <= 0) throw new IllegalArgumentException("La altura debe ser positiva.");
                        figuras.add(new Triangulo(baseTri, alturaTri));
                        System.out.println("Triángulo creado.");
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida para la altura.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        scanner.nextLine(); // Consumir el salto de línea
                    }
                    break;
                case 4:
                    if (figuras.isEmpty()) {
                        System.out.println("No hay figuras creadas aún.");
                    } else {
                        System.out.println("\n--- FIGURAS Y SUS ÁREAS ---");
                        for (FiguraGeometrica figura : figuras) {
                            System.out.println(figura); // Polimorfismo en acción!
                            // figura.mostrarDescripcion(); // Puedes llamar a métodos concretos de la clase abstracta
                        }
                        System.out.println("---------------------------");
                    }
                    break;
                case 0:
                    System.out.println("Saliendo de la calculadora de figuras. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}