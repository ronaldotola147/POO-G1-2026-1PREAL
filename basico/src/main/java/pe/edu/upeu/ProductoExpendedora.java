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
    }
}