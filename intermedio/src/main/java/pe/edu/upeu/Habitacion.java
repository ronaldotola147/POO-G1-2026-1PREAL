package pe.edu.upeu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException; // Para manejar errores de entrada
import java.util.List;
import java.util.Scanner;
import java.util.Random; // Para generar IDs de reserva

// Clase Habitacion
class Habitacion {
    private int numero;
    private String tipo; // Ej: "Individual", "Doble", "Suite"
    private double precioPorNoche;
    private boolean disponible;

    public Habitacion(int numero, String tipo, double precioPorNoche) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.disponible = true; // Por defecto, una habitación está disponible
    }

    // Getters
    public int getNumero() { return numero; }
    public String getTipo() { return tipo; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public boolean isDisponible() { return disponible; }

    // Setters (para cambiar la disponibilidad)
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return String.format("Habitación %d (%s) - Precio: S/%.2f - Estado: %s",
                numero, tipo, precioPorNoche, (disponible ? "Disponible" : "Ocupada"));
    }
}

// Clase Reserva
class Reserva {
    private String idReserva;
    private int numeroHabitacion;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private String nombreCliente;

    public Reserva(String idReserva, int numeroHabitacion, LocalDate fechaCheckIn, LocalDate fechaCheckOut, String nombreCliente) {
        this.idReserva = idReserva;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.nombreCliente = nombreCliente;
    }

    // Getters
    public String getIdReserva() { return idReserva; }
    public int getNumeroHabitacion() { return numeroHabitacion; }
    public LocalDate getFechaCheckIn() { return fechaCheckIn; }
    public LocalDate getFechaCheckOut() { return fechaCheckOut; }
    public String getNombreCliente() { return nombreCliente; }

    @Override
    public String toString() {
        return String.format("Reserva ID: %s | Cliente: %s | Habitación: %d | Check-in: %s | Check-out: %s",
                idReserva, nombreCliente, numeroHabitacion, fechaCheckIn, fechaCheckOut);
    }
}

// Clase SistemaReservasHotel
class SistemaReservasHotel {
    private ArrayList<Habitacion> habitaciones;
    private ArrayList<Reserva> reservas;
    private Random random;

    public SistemaReservasHotel() {
        habitaciones = new ArrayList<>();
        reservas = new ArrayList<>();
        random = new Random();
        inicializarHabitaciones();
    }

    private void inicializarHabitaciones() {
        // Añadir algunas habitaciones de ejemplo
        habitaciones.add(new Habitacion(101, "Individual", 150.00));
        habitaciones.add(new Habitacion(102, "Doble", 250.00));
        habitaciones.add(new Habitacion(103, "Doble", 250.00));
        habitaciones.add(new Habitacion(201, "Suite", 400.00));
        habitaciones.add(new Habitacion(202, "Individual", 150.00));
        System.out.println("Hotel inicializado con " + habitaciones.size() + " habitaciones.");
    }

    public void buscarHabitacionDisponible(String tipo) {
        System.out.println("\n--- Habitaciones disponibles de tipo '" + tipo + "' ---");
        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion h : habitaciones) {
            if (h.isDisponible() && h.getTipo().equalsIgnoreCase(tipo)) {
                disponibles.add(h);
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles de tipo '" + tipo + "' en este momento.");
        } else {
            disponibles.forEach(System.out::println);
        }
        System.out.println("-----------------------------------------------------");
    }

    public void mostrarTodasLasHabitaciones() {
        System.out.println("\n--- Todas las Habitaciones del Hotel ---");
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones registradas.");
            return;
        }
        for (Habitacion h : habitaciones) {
            System.out.println(h);
        }
        System.out.println("----------------------------------------");
    }

    public void realizarReserva(int numeroHabitacion, LocalDate checkIn, LocalDate checkOut, String nombreCliente) {
        // 1. Validar fechas
        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            System.out.println("Error: La fecha de check-out debe ser posterior a la de check-in.");
            return;
        }
        if (checkIn.isBefore(LocalDate.now())) {
            System.out.println("Error: La fecha de check-in no puede ser en el pasado.");
            return;
        }

        // 2. Buscar la habitación
        Habitacion habitacionAReservar = null;
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numeroHabitacion) {
                habitacionAReservar = h;
                break;
            }
        }

        if (habitacionAReservar == null) {
            System.out.println("Error: La habitación " + numeroHabitacion + " no existe.");
            return;
        }

        if (!habitacionAReservar.isDisponible()) {
            System.out.println("Error: La habitación " + numeroHabitacion + " no está disponible.");
            return;
        }

        // 3. Realizar la reserva
        String idReserva = "RES-" + (random.nextInt(9000) + 1000); // Genera un ID aleatorio de 4 dígitos
        Reserva nuevaReserva = new Reserva(idReserva, numeroHabitacion, checkIn, checkOut, nombreCliente);
        reservas.add(nuevaReserva);
        habitacionAReservar.setDisponible(false); // Marcar como ocupada
        System.out.println("¡Reserva realizada con éxito! " + nuevaReserva);
    }

    public void cancelarReserva(String idReserva) {
        Reserva reservaACancelar = null;
        for (Reserva r : reservas) {
            if (r.getIdReserva().equalsIgnoreCase(idReserva)) {
                reservaACancelar = r;
                break;
            }
        }

        if (reservaACancelar == null) {
            System.out.println("Error: Reserva con ID '" + idReserva + "' no encontrada.");
            return;
        }

        // Marcar la habitación como disponible de nuevo
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == reservaACancelar.getNumeroHabitacion()) {
                h.setDisponible(true);
                break;
            }
        }
        reservas.remove(reservaACancelar);
        System.out.println("Reserva ID '" + idReserva + "' cancelada con éxito.");
    }

    public void mostrarReservas() {
        System.out.println("\n--- RESERVAS ACTUALES ---");
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas activas.");
        } else {
            reservas.forEach(System.out::println);
        }
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaReservasHotel hotel = new SistemaReservasHotel();
        int opcion;

        do {
            System.out.println("\n--- MENÚ SISTEMA DE RESERVAS ---");
            System.out.println("1. Mostrar todas las habitaciones");
            System.out.println("2. Buscar habitación disponible por tipo");
            System.out.println("3. Realizar reserva");
            System.out.println("4. Cancelar reserva");
            System.out.println("5. Mostrar todas las reservas");
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
                    hotel.mostrarTodasLasHabitaciones();
                    break;
                case 2:
                    System.out.print("Tipo de habitación (Individual, Doble, Suite): ");
                    String tipo = scanner.nextLine();
                    hotel.buscarHabitacionDisponible(tipo);
                    break;
                case 3:
                    System.out.print("Número de habitación a reservar: ");
                    int numHab = -1;
                    try {
                        numHab = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Número de habitación inválido.");
                        scanner.nextLine();
                        break;
                    }

                    System.out.print("Fecha Check-in (YYYY-MM-DD): ");
                    LocalDate checkIn = LocalDate.parse(scanner.nextLine());
                    System.out.print("Fecha Check-out (YYYY-MM-DD): ");
                    LocalDate checkOut = LocalDate.parse(scanner.nextLine());
                    System.out.print("Nombre del cliente: ");
                    String cliente = scanner.nextLine();

                    hotel.realizarReserva(numHab, checkIn, checkOut, cliente);
                    break;
                case 4:
                    System.out.print("ID de la reserva a cancelar: ");
                    String idReserva = scanner.nextLine();
                    hotel.cancelarReserva(idReserva);
                    break;
                case 5:
                    hotel.mostrarReservas();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema de reservas. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}