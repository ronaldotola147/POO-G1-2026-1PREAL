package pe.edu.upeu;

import java.util.ArrayList;
import java.util.Scanner;

class ProductoExpendedora {
    private String nombre;
    private double precio;
    private int stock;
    private String codigo; // Código para seleccionar el producto

    public ProductoExpendedora(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    // Setter para stock (cuando se vende un producto)
    public void reducirStock() {
        if (this.stock > 0) {
            this.stock--;
        }
    }
}

class MaquinaExpendedora {
    private ArrayList<ProductoExpendedora> productos;
    private double dineroIngresado;

    public MaquinaExpendedora() {
        productos = new ArrayList<>();
        dineroIngresado = 0.0;
        // Cargar productos de ejemplo
        productos.add(new ProductoExpendedora("A1", "Agua", 1.50, 5));
        productos.add(new ProductoExpendedora("A2", "Refresco", 2.00, 3));
        productos.add(new ProductoExpendedora("B1", "Snack", 1.75, 7));
        productos.add(new ProductoExpendedora("B2", "Chocolate", 2.25, 4));
    }

    public void mostrarProductos() {
        System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
        System.out.printf("%-5s %-12s %-8s %-5s\n", "Cod", "Producto", "Precio", "Stock");
        System.out.println("----------------------------------");
        for (ProductoExpendedora p : productos) {
            System.out.printf("%-5s %-12s S/%-7.2f %-5d\n", p.getCodigo(), p.getNombre(), p.getPrecio(), p.getStock());
        }
        System.out.println("----------------------------------");
        System.out.printf("Dinero ingresado: S/%.2f\n", dineroIngresado);
    }

    public void ingresarDinero(double monto) {
        if (monto > 0) {
            this.dineroIngresado += monto;
            System.out.printf("Dinero ingresado: S/%.2f. Total: S/%.2f\n", monto, dineroIngresado);
        } else {
            System.out.println("Monto inválido.");
        }
    }

    public void seleccionarProducto(String codigo) {
        ProductoExpendedora productoSeleccionado = null;
        for (ProductoExpendedora p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                productoSeleccionado = p;
                break;
            }
        }

        if (productoSeleccionado == null) {
            System.out.println("Error: Código de producto inválido.");
            return;
        }

        if (productoSeleccionado.getStock() <= 0) {
            System.out.println("Lo sentimos, el producto '" + productoSeleccionado.getNombre() + "' está agotado.");
            return;
        }

        if (dineroIngresado >= productoSeleccionado.getPrecio()) {
            dineroIngresado -= productoSeleccionado.getPrecio();
            productoSeleccionado.reducirStock();
            System.out.println("¡Disfruta tu '" + productoSeleccionado.getNombre() + "'!");
            if (dineroIngresado > 0) {
                System.out.printf("Tu cambio: S/%.2f\n", dineroIngresado);
                dineroIngresado = 0; // Se entrega el cambio y se resetea el dinero
            }
        } else {
            System.out.printf("Dinero insuficiente. Faltan S/%.2f para '" + productoSeleccionado.getNombre() + "'.\n",
                    productoSeleccionado.getPrecio() - dineroIngresado);
        }
    }

    public void devolverDinero() {
        if (dineroIngresado > 0) {
            System.out.printf("Dinero devuelto: S/%.2f\n", dineroIngresado);
            dineroIngresado = 0;
        } else {
            System.out.println("No hay dinero ingresado para devolver.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MaquinaExpendedora miMaquina = new MaquinaExpendedora();
        int opcion;

        do {
            miMaquina.mostrarProductos();
            System.out.println("\n--- MENÚ MÁQUINA EXPENDEDORA ---");
            System.out.println("1. Ingresar dinero");
            System.out.println("2. Seleccionar producto");
            System.out.println("3. Devolver dinero");
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
                    System.out.print("Ingresa el monto: S/");
                    double monto = 0;
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Monto inválido. Ingresa un número decimal.");
                        scanner.next();
                        System.out.print("Ingresa el monto: S/");
                    }
                    monto = scanner.nextDouble();
                    scanner.nextLine(); // Consumir el salto de línea
                    miMaquina.ingresarDinero(monto);
                    break;
                case 2:
                    System.out.print("Ingresa el código del producto: ");
                    String codigo = scanner.nextLine();
                    miMaquina.seleccionarProducto(codigo);
                    break;
                case 3:
                    miMaquina.devolverDinero();
                    break;
                case 0:
                    miMaquina.devolverDinero(); // Devuelve el cambio antes de salir
                    System.out.println("Gracias por usar la máquina expendedora. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}