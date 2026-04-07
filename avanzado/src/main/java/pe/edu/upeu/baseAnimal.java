package pe.edu.upeu;

import java.util.ArrayList;
import java.util.List;

// 1. Clase base Animal
abstract class Animal {
    private String nombre;
    private String especie;

    public Animal(String nombre, String especie) {
        this.nombre = nombre;
        this.especie = especie;
    }

    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }

    public abstract void comer(); // Cada animal come de forma diferente
    public abstract void dormir(); // Cada animal duerme de forma diferente

    public void presentarse() {
        System.out.println("Hola, soy " + nombre + ", un " + especie + ".");
    }
}

// 2. Interfaces para comportamientos
interface Volador {
    void volar();
}

interface Nadador {
    void nadar();
}

interface Corredor {
    void correr();
}

// 3. Clases de animales concretas

// Águila: Es un animal y puede volar
class Aguila extends Animal implements Volador {
    public Aguila(String nombre) {
        super(nombre, "Águila");
    }

    @Override
    public void comer() {
        System.out.println(getNombre() + " está cazando ratones y serpientes.");
    }

    @Override
    public void dormir() {
        System.out.println(getNombre() + " duerme en lo alto de un árbol.");
    }

    @Override
    public void volar() {
        System.out.println(getNombre() + " vuela majestuosamente en el cielo.");
    }
}

// Pez: Es un animal y puede nadar
class Pez extends Animal implements Nadador {
    public Pez(String nombre) {
        super(nombre, "Pez");
    }

    @Override
    public void comer() {
        System.out.println(getNombre() + " está comiendo algas y pequeños insectos acuáticos.");
    }

    @Override
    public void dormir() {
        System.out.println(getNombre() + " duerme flotando en el agua.");
    }

    @Override
    public void nadar() {
        System.out.println(getNombre() + " nada ágilmente en el agua.");
    }
}

// León: Es un animal y puede correr
class Leon extends Animal implements Corredor {
    public Leon(String nombre) {
        super(nombre, "León");
    }

    @Override
    public void comer() {
        System.out.println(getNombre() + " está devorando su presa.");
    }

    @Override
    public void dormir() {
        System.out.println(getNombre() + " duerme en la sabana bajo el sol.");
    }

    @Override
    public void correr() {
        System.out.println(getNombre() + " corre a gran velocidad tras su objetivo.");
    }
}

// Pato: Es un animal que puede volar, nadar y correr (caminar)
class Pato extends Animal implements Volador, Nadador, Corredor {
    public Pato(String nombre) {
        super(nombre, "Pato");
    }

    @Override
    public void comer() {
        System.out.println(getNombre() + " está picoteando grano y vegetación.");
    }

    @Override
    public void dormir() {
        System.out.println(getNombre() + " duerme con la cabeza bajo el ala.");
    }

    @Override
    public void volar() {
        System.out.println(getNombre() + " aletea y se eleva en el aire.");
    }

    @Override
    public void nadar() {
        System.out.println(getNombre() + " chapotea en el estanque.");
    }

    @Override
    public void correr() {
        System.out.println(getNombre() + " camina torpemente por tierra.");
    }
}

// Clase principal para probar el zoológico
class Zoológico {
    public static void main(String[] args) {
        List<Animal> animales = new ArrayList<>();
        animales.add(new Aguila("Águila Real"));
        animales.add(new Pez("Pez Payaso"));
        animales.add(new Leon("León Simba"));
        animales.add(new Pato("Pato Donald"));

        System.out.println("--- BIENVENIDO AL ZOOLÓGICO ---");
        for (Animal animal : animales) {
            animal.presentarse();
            animal.comer();
            animal.dormir();

            // Demostración de polimorfismo con interfaces
            if (animal instanceof Volador) {
                ((Volador) animal).volar();
            }
            if (animal instanceof Nadador) {
                ((Nadador) animal).nadar();
            }
            if (animal instanceof Corredor) {
                ((Corredor) animal).correr();
            }
            System.out.println("---------------------------------");
        }

        // Otro ejemplo de polimorfismo
        System.out.println("\n--- Solo los Voladores ---");
        for (Animal animal : animales) {
            if (animal instanceof Volador) {
                animal.presentarse(); // También podemos llamar a métodos de la clase base
                ((Volador) animal).volar();
            }
        System.out.println("---------------------------------");
        }
    }
}