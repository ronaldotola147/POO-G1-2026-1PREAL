package pe.edu.upeu;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

// Clase Producto
class Producto {
    String nombre;
    double precio;
    int cantidad; // Cantidad de este producto en el carrito
    String categoria;

    public Producto(String nombre, double precio, int cantidad, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // Getters
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public String getCategoria() { return categoria; }

    // Setters
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    // Sobreescribe equals() y hashCode() para poder usar .remove() correctamente con el nombre del producto
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(nombre, producto.nombre); // Comparar solo por nombre
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}

// Clase CarritoDeCompras
class CarritoDeCompras {
    private ArrayList<Producto> carrito;

    public CarritoDeCompras() {
        carrito = new ArrayList<>();
    }

    public void agregarAlCarrito(Producto nuevoProducto) {
        boolean encontrado = false;
        for (Producto p : carrito) {
            if (p.equals(nuevoProducto)) { // Usa el equals que compara por nombre
                p.setCantidad(p.getCantidad() + nuevoProducto.getCantidad());
                encontrado = true;
                System.out.println("Se actualizó la cantidad de '" + nuevoProducto.getNombre() + "' en el carrito.");
                break;
            }
        }
        if (!encontrado) {
            carrito.add(nuevoProducto);
            System.out.println("'" + nuevoProducto.getNombre() + "' agregado al carrito.");
        }
    }

    public void eliminarDelCarrito(String nombreProducto) {
        // Creamos un producto "dummy" solo para usar el método equals y eliminar por nombre
        Producto productoABorrar = new Producto(nombreProducto, 0, 0, "");
        if (carrito.remove(productoABorrar)) {
            System.out.println("'" + nombreProducto + "' eliminado del carrito.");
        } else {
            System.out.println("'" + nombreProducto + "' no se encontró en el carrito.");
        }
    }

    public double calcularSubtotal() {
        return carrito.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();
    }

    public double aplicarDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            System.out.println("Porcentaje de descuento inválido. Debe estar entre 0 y 100.");
            return calcularSubtotal();
        }
        double subtotal = calcularSubtotal();
        double descuento = subtotal * (porcentajeDescuento / 100);
        System.out.printf("Descuento aplicado (%.1f%%): S/ %.2f\n", porcentajeDescuento, descuento);
        return subtotal - descuento;
    }

    public void mostrarResumen() {
        if (carrito.isEmpty()) {
            System.out.println("\nEl carrito de compras está vacío.");
            return;
        }
        System.out.println("\n--- RESUMEN DEL CARRITO ---");
        System.out.printf("%-20s %-10s %-10s %-10s\n", "Producto", "Precio", "Cantidad", "Subtotal");
        System.out.println("-----------------------------------------------------");
        for (Producto p : carrito) {
            System.out.printf("%-20s S/%-9.2f %-10d S/%-9.2f\n",
                    p.getNombre(), p.getPrecio(), p.getCantidad(), p.getPrecio() * p.getCantidad());
        }
        System.out.println("-----------------------------------------------------");
        System.out.printf("SUBTOTAL GENERAL: S/ %.2f\n", calcularSubtotal());
        System.out.println("-----------------------------------------------------");
    }

    public void filtrarPorCategoria(String categoria) {
        System.out.println("\n--- Productos en la categoría '" + categoria + "' ---");
        ArrayList<Producto> filtrados = carrito.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toCollection(ArrayList::new));
        if (filtrados.isEmpty()) {
            System.out.println("No hay productos en esta categoría.");
            return;
        }
        for (Producto p : filtrados) {
            System.out.printf("  - %s (Cantidad: %d, Precio: S/%.2f)\n", p.getNombre(), p.getCantidad(), p.getPrecio());
        }
        System.out.println("-----------------------------------------------------");
    }

    public static void main(String[] args) {
        CarritoDeCompras miCarrito = new CarritoDeCompras();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        String nombre, categoria;
        double precio, descuento;
        int cantidad;

        do {
            System.out.println("\n--- MENÚ CARRITO DE COMPRAS ---");
            System.out.println("1. Agregar producto al carrito");
            System.out.println("2. Eliminar producto del carrito");
            System.out.println("3. Mostrar resumen del carrito");
            System.out.println("4. Aplicar descuento");
            System.out.println("5. Filtrar por categoría");
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
                    System.out.print("Nombre del producto: ");
                    nombre = scanner.nextLine();
                    System.out.print("Precio: S/");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Precio inválido. Ingresa un número decimal.");
                        scanner.next();
                        System.out.print("Precio: S/");
                    }
                    precio = scanner.nextDouble();
                    System.out.print("Cantidad: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Cantidad inválida. Ingresa un número entero.");
                        scanner.next();
                        System.out.print("Cantidad: ");
                    }
                    cantidad = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea
                    System.out.print("Categoría: ");
                    categoria = scanner.nextLine();
                    miCarrito.agregarAlCarrito(new Producto(nombre, precio, cantidad, categoria));
                    break;
                case 2:
                    System.out.print("Nombre del producto a eliminar: ");
                    nombre = scanner.nextLine();
                    miCarrito.eliminarDelCarrito(nombre);
                    break;
                case 3:
                    miCarrito.mostrarResumen();
                    break;
                case 4:
                    System.out.print("Porcentaje de descuento a aplicar (ej. 10 para 10%): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Descuento inválido. Ingresa un número decimal.");
                        scanner.next();
                        System.out.print("Porcentaje de descuento a aplicar (ej. 10 para 10%): ");
                    }
                    descuento = scanner.nextDouble();
                    scanner.nextLine(); // Consumir el salto de línea
                    double totalConDescuento = miCarrito.aplicarDescuento(descuento);
                    System.out.printf("TOTAL A PAGAR CON DESCUENTO: S/ %.2f\n", totalConDescuento);
                    break;
                case 5:
                    System.out.print("Ingresa la categoría a filtrar: ");
                    categoria = scanner.nextLine();
                    miCarrito.filtrarPorCategoria(categoria);
                    break;
                case 0:
                    System.out.println("Saliendo del programa. ¡Gracias por tu compra!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}