package pe.edu.upeu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID; // Para generar IDs únicos

// 1. Clases Base
// Se elimina 'public' de Persona
class Persona {
    private String id;
    private String nombre;
    private String fechaNacimiento; // Simplificado como String

    public Persona(String nombre, String fechaNacimiento) {
        this.id = UUID.randomUUID().toString().substring(0, 8); // ID único corto
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getFechaNacimiento() { return fechaNacimiento; }

    // Método genérico
    public void saludar() {
        System.out.println("Hola, soy " + nombre);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Nacimiento: " + fechaNacimiento;
    }
}

// Se elimina 'public' de Empleado
class Empleado extends Persona {
    private String puesto;
    private double salario;

    public Empleado(String nombre, String fechaNacimiento, String puesto, double salario) {
        super(nombre, fechaNacimiento);
        this.puesto = puesto;
        this.salario = salario;
    }

    // Getters
    public String getPuesto() { return puesto; }
    public double getSalario() { return salario; }

    public void trabajar() {
        System.out.println(getNombre() + " está realizando sus funciones de " + puesto + ".");
    }

    @Override
    public String toString() {
        return super.toString() + ", Puesto: " + puesto + ", Salario: S/" + salario;
    }
}

// Se elimina 'public' de Paciente
class Paciente extends Persona {
    private String diagnostico;
    private List<String> historialMedico;

    public Paciente(String nombre, String fechaNacimiento, String diagnostico) {
        super(nombre, fechaNacimiento);
        this.diagnostico = diagnostico;
        this.historialMedico = new ArrayList<>();
        this.historialMedico.add("Diagnóstico inicial: " + diagnostico + " - " + LocalDate.now());
    }

    // Getters y Setters
    public String getDiagnostico() { return diagnostico; }
    public List<String> getHistorialMedico() { return historialMedico; }

    public void agregarNotaHistorial(String nota) {
        historialMedico.add(nota + " - " + LocalDate.now());
        System.out.println("Nota agregada al historial de " + getNombre());
    }

    @Override
    public String toString() {
        return super.toString() + ", Diagnóstico: " + diagnostico;
    }
}

// 2. Clases Especializadas (Herencia)
// Se elimina 'public' de Doctor
class Doctor extends Empleado {
    private String especialidad;
    private List<Paciente> pacientesAsignados; // Lista de pacientes que este doctor ha asignado a su cargo

    public Doctor(String nombre, String fechaNacimiento, double salario, String especialidad) {
        super(nombre, fechaNacimiento, "Doctor", salario);
        this.especialidad = especialidad;
        this.pacientesAsignados = new ArrayList<>();
    }

    public String getEspecialidad() { return especialidad; }

    public void asignarPaciente(Paciente paciente) {
        if (!pacientesAsignados.contains(paciente)) {
            pacientesAsignados.add(paciente);
            System.out.println("Paciente " + paciente.getNombre() + " asignado al Dr. " + getNombre());
        } else {
            System.out.println("El paciente " + paciente.getNombre() + " ya está asignado al Dr. " + getNombre());
        }
    }

    public void realizarDiagnostico(Paciente paciente, String nuevoDiagnostico) {
        if (pacientesAsignados.contains(paciente)) {
            paciente.agregarNotaHistorial("Nuevo diagnóstico por Dr. " + getNombre() + ": " + nuevoDiagnostico);
            System.out.println("Dr. " + getNombre() + " ha diagnosticado a " + paciente.getNombre() + ".");
        } else {
            System.out.println("Error: El paciente " + paciente.getNombre() + " no está asignado a este doctor.");
        }
    }

    @Override
    public void trabajar() {
        System.out.println("Dr. " + getNombre() + " (" + especialidad + ") está atendiendo a pacientes.");
    }

    @Override
    public String toString() {
        return super.toString() + ", Especialidad: " + especialidad + ", Pacientes a cargo: " + pacientesAsignados.size();
    }
}

// Se elimina 'public' de Enfermero
class Enfermero extends Empleado {
    private String areaAsignada;

    public Enfermero(String nombre, String fechaNacimiento, double salario, String areaAsignada) {
        super(nombre, fechaNacimiento, "Enfermero(a)", salario);
        this.areaAsignada = areaAsignada;
    }

    public String getAreaAsignada() { return areaAsignada; }

    public void administrarMedicamentos(Paciente paciente) {
        System.out.println(getNombre() + " está administrando medicamentos a " + paciente.getNombre() + " en " + areaAsignada + ".");
        paciente.agregarNotaHistorial("Medicamento administrado por enfermero(a) " + getNombre());
    }

    @Override
    public void trabajar() {
        System.out.println(getNombre() + " está cuidando pacientes en el área de " + areaAsignada + ".");
    }

    @Override
    public String toString() {
        return super.toString() + ", Área: " + areaAsignada;
    }
}

// 3. Interfaces
// Se elimina 'public' de Gestionable
interface Gestionable<T> {
    void agregar(T item);
    void eliminar(String id);
    T buscar(String id);
    void listarTodos();
}

// 4. Sistema Central: Hospital (Esta sí debe ser public y el archivo debe llamarse Hospital.java)
public class Hospital implements Gestionable<Persona> { // Aquí se mantiene public
    private List<Empleado> empleados;
    private List<Paciente> pacientes;
    private List<String> citas; // Simplificado como Strings

    public Hospital() {
        this.empleados = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    // Implementación de la interfaz Gestionable para Personas (empleados y pacientes)
    @Override
    public void agregar(Persona persona) {
        if (persona instanceof Empleado) {
            empleados.add((Empleado) persona);
            System.out.println("Empleado " + persona.getNombre() + " agregado al hospital.");
        } else if (persona instanceof Paciente) {
            pacientes.add((Paciente) persona);
            System.out.println("Paciente " + persona.getNombre() + " registrado en el hospital.");
        } else {
            System.out.println("Tipo de persona no soportado para agregar.");
        }
    }

    @Override
    public void eliminar(String id) {
        boolean eliminado = empleados.removeIf(e -> e.getId().equals(id));
        if (eliminado) {
            System.out.println("Empleado con ID " + id + " eliminado.");
            return;
        }
        eliminado = pacientes.removeIf(p -> p.getId().equals(id));
        if (eliminado) {
            System.out.println("Paciente con ID " + id + " eliminado.");
            return;
        }
        System.out.println("No se encontró persona (empleado o paciente) con ID " + id);
    }

    @Override
    public Persona buscar(String id) {
        for (Empleado e : empleados) {
            if (e.getId().equals(id)) return e;
        }
        for (Paciente p : pacientes) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    @Override
    public void listarTodos() {
        System.out.println("\n--- EMPLEADOS DEL HOSPITAL ---");
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            empleados.forEach(System.out::println);
        }

        System.out.println("\n--- PACIENTES REGISTRADOS ---");
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            pacientes.forEach(System.out::println);
        }
    }

    // Métodos específicos del Hospital
    public void agendarCita(String doctorId, String pacienteId, String fechaHora) {
        Doctor doctor = null;
        for (Empleado e : empleados) {
            if (e instanceof Doctor && e.getId().equals(doctorId)) {
                doctor = (Doctor) e;
                break;
            }
        }

        Paciente paciente = null;
        for (Paciente p : pacientes) {
            if (p.getId().equals(pacienteId)) {
                paciente = p;
                break;
            }
        }

        if (doctor != null && paciente != null) {
            String cita = "Cita: Dr. " + doctor.getNombre() + " con Paciente " + paciente.getNombre() + " el " + fechaHora;
            citas.add(cita);
            System.out.println("Cita agendada: " + cita);
        } else {
            System.out.println("Error: Doctor o Paciente no encontrados para agendar cita.");
        }
    }

    public void mostrarCitas() {
        System.out.println("\n--- CITAS AGENDADAS ---");
        if (citas.isEmpty()) {
            System.out.println("No hay citas agendadas.");
        } else {
            citas.forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();

        // Crear y agregar personal y pacientes
        Doctor drGarcia = new Doctor("Juan Garcia", "1980-05-15", 5000.00, "Cardiología");
        Enfermero enfMaria = new Enfermero("Maria Lopez", "1992-11-22", 2500.00, "Emergencias");
        Paciente pacAna = new Paciente("Ana Perez", "1975-03-10", "Hipertensión");
        Paciente pacCarlos = new Paciente("Carlos Ruíz", "2000-01-20", "Gripe Común");

        hospital.agregar(drGarcia);
        hospital.agregar(enfMaria);
        hospital.agregar(pacAna);
        hospital.agregar(pacCarlos);

        int opcion;
        do {
            System.out.println("\n--- SISTEMA HOSPITALARIO ---");
            System.out.println("1. Listar Empleados y Pacientes");
            System.out.println("2. Asignar paciente a Doctor");
            System.out.println("3. Doctor realiza diagnóstico");
            System.out.println("4. Enfermero administra medicamento");
            System.out.println("5. Agendar Cita");
            System.out.println("6. Mostrar Citas");
            System.out.println("7. Ver historial médico de Paciente");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
                scanner.nextLine();
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1:
                    hospital.listarTodos();
                    break;
                case 2:
                    System.out.print("ID del Doctor (ej: " + drGarcia.getId() + "): ");
                    String idDoc = scanner.nextLine();
                    System.out.print("ID del Paciente (ej: " + pacAna.getId() + "): ");
                    String idPac1 = scanner.nextLine();

                    Persona pDoc = hospital.buscar(idDoc);
                    Persona pPac1 = hospital.buscar(idPac1);

                    if (pDoc instanceof Doctor && pPac1 instanceof Paciente) {
                        ((Doctor) pDoc).asignarPaciente((Paciente) pPac1);
                    } else {
                        System.out.println("Error: Doctor o Paciente no encontrados o IDs incorrectos.");
                    }
                    break;
                case 3:
                    System.out.print("ID del Doctor (ej: " + drGarcia.getId() + "): ");
                    String idDocDiag = scanner.nextLine();
                    System.out.print("ID del Paciente (ej: " + pacAna.getId() + "): ");
                    String idPacDiag = scanner.nextLine();
                    System.out.print("Nuevo diagnóstico: ");
                    String diagnostico = scanner.nextLine();

                    Persona pDocDiag = hospital.buscar(idDocDiag);
                    Persona pPacDiag = hospital.buscar(idPacDiag);

                    if (pDocDiag instanceof Doctor && pPacDiag instanceof Paciente) {
                        ((Doctor) pDocDiag).realizarDiagnostico((Paciente) pPacDiag, diagnostico);
                    } else {
                        System.out.println("Error: Doctor o Paciente no encontrados o IDs incorrectos.");
                    }
                    break;
                case 4:
                    System.out.print("ID del Enfermero(a) (ej: " + enfMaria.getId() + "): ");
                    String idEnf = scanner.nextLine();
                    System.out.print("ID del Paciente (ej: " + pacCarlos.getId() + "): ");
                    String idPacMed = scanner.nextLine();

                    Persona pEnf = hospital.buscar(idEnf);
                    Persona pPacMed = hospital.buscar(idPacMed);

                    if (pEnf instanceof Enfermero && pPacMed instanceof Paciente) {
                        ((Enfermero) pEnf).administrarMedicamentos((Paciente) pPacMed);
                    } else {
                        System.out.println("Error: Enfermero(a) o Paciente no encontrados o IDs incorrectos.");
                    }
                    break;
                case 5:
                    System.out.print("ID del Doctor para la cita (ej: " + drGarcia.getId() + "): ");
                    String idDocCita = scanner.nextLine();
                    System.out.print("ID del Paciente para la cita (ej: " + pacAna.getId() + "): ");
                    String idPacCita = scanner.nextLine();
                    System.out.print("Fecha y Hora de la cita (ej: YYYY-MM-DD HH:MM): ");
                    String fechaHoraCita = scanner.nextLine();
                    hospital.agendarCita(idDocCita, idPacCita, fechaHoraCita);
                    break;
                case 6:
                    hospital.mostrarCitas();
                    break;
                case 7:
                    System.out.print("ID del Paciente para ver historial (ej: " + pacAna.getId() + "): ");
                    String idPacHist = scanner.nextLine();
                    Persona pPacHist = hospital.buscar(idPacHist);
                    if (pPacHist instanceof Paciente) {
                        System.out.println("\n--- Historial Médico de " + pPacHist.getNombre() + " ---");
                        ((Paciente) pPacHist).getHistorialMedico().forEach(System.out::println);
                        System.out.println("----------------------------------------");
                    }
            }
        }
    }
}
