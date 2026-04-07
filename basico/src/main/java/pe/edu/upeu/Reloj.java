package pe.edu.upeu;

import java.util.Scanner;
import java.util.concurrent.TimeUnit; // Para el retardo de tiempo

class Reloj {
    private int hora;
    private int minuto;
    private int segundo;
    private int alarmaHora;
    private int alarmaMinuto;
    private boolean alarmaActivada;

    public Reloj(int hora, int minuto, int segundo) {
        establecerHora(hora, minuto, segundo);
        this.alarmaActivada = false;
    }

    // Métodos para establecer la hora
    public void establecerHora(int hora, int minuto, int segundo) {
        if (hora >= 0 && hora < 24) this.hora = hora;
        else System.out.println("Hora inválida. Se mantiene la actual.");

        if (minuto >= 0 && minuto < 60) this.minuto = minuto;
        else System.out.println("Minuto inválido. Se mantiene el actual.");

        if (segundo >= 0 && segundo < 60) this.segundo = segundo;
        else System.out.println("Segundo inválido. Se mantiene el actual.");
    }

    // Métodos para establecer la alarma
    public void establecerAlarma(int alarmaHora, int alarmaMinuto) {
        if (alarmaHora >= 0 && alarmaHora < 24 && alarmaMinuto >= 0 && alarmaMinuto < 60) {
            this.alarmaHora = alarmaHora;
            this.alarmaMinuto = alarmaMinuto;
            this.alarmaActivada = true;
            System.out.printf("Alarma establecida para las %02d:%02d\n", this.alarmaHora, this.alarmaMinuto);
        } else {
            System.out.println("Horario de alarma inválido.");
        }
    }

    public void desactivarAlarma() {
        this.alarmaActivada = false;
        System.out.println("Alarma desactivada.");
    }

    // Método para avanzar el tiempo un segundo
    public void tic() {
        segundo++;
        if (segundo == 60) {
            segundo = 0;
            minuto++;
            if (minuto == 60) {
                minuto = 0;
                hora++;
                if (hora == 24) {
                    hora = 0; // Pasa a medianoche
                }
            }
        }
        verificarAlarma();
    }

    // Método para mostrar la hora
    public void mostrarHora() {
        System.out.printf("\r%02d:%02d:%02d", hora, minuto, segundo);
        System.out.flush(); // Para que se actualice en la misma línea
    }

    // Verificar si la alarma debe sonar
    private void verificarAlarma() {
        if (alarmaActivada && hora == alarmaHora && minuto == alarmaMinuto && segundo == 0) {
            System.out.println("\n¡¡¡RIIIIIIING!!! ¡Es la hora de la alarma!");
            alarmaActivada = false; // La alarma suena una vez y se desactiva
        }
    }

    public static void main(String[] args) throws InterruptedException { // Manejar InterruptedException
        Scanner scanner = new Scanner(System.in);
        Reloj miReloj = new Reloj(0, 0, 0); // Empieza en 00:00:00

        System.out.println("--- SIMULADOR DE RELOJ DIGITAL ---");
        System.out.print("¿Deseas establecer una hora inicial? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Ingresa la hora (0-23): ");
            int h = scanner.nextInt();
            System.out.print("Ingresa los minutos (0-59): ");
            int m = scanner.nextInt();
            System.out.print("Ingresa los segundos (0-59): ");
            int s = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            miReloj.establecerHora(h, m, s);
        }

        System.out.print("¿Deseas establecer una alarma? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Ingresa la hora de la alarma (0-23): ");
            int ah = scanner.nextInt();
            System.out.print("Ingresa los minutos de la alarma (0-59): ");
            int am = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            miReloj.establecerAlarma(ah, am);
        }

        System.out.println("\nIniciando reloj (presiona Ctrl+C para detener)...");
        // Bucle infinito para simular el paso del tiempo
        while (true) {
            miReloj.tic();
            miReloj.mostrarHora();
            TimeUnit.SECONDS.sleep(1); // Espera 1 segundo para simular el tiempo real
        }
        // scanner.close(); // No se cierra si el bucle es infinito
    }
}