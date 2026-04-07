package pe.edu.upeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class ListaCompras {
    private ArrayList<String> productos;

    public ListaCompras() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(String producto) {
        if (!productos.contains(producto)) {
            productos.add(producto);
            System.out.println("'" + producto + "' agregado a la lista.");
        } else {
            System.out.println("'" + producto + "' ya existe en la lista. No se agregó.");
        }
    }

    public void eliminarProducto(String producto) {
        if (productos.remove(producto)) {
            System.out.println("'" + producto + "' eliminado de la lista.");
        } else {
            System.out.println("'" + producto + "' no se encontró en la lista.");
        }
    }

    public boolean buscarProducto(String producto) {
        return productos.contains(producto);
    }

    public int contarProductos() {
        return productos.size();
    }

    public void ordenarAlfabeticamente() {
        Collections.sort(productos);
        System.out.println("Lista ordenada alfabéticamente.");
    }

    public void mostrarLista() {
        if (productos.isEmpty()) {
            System.out.println("La lista de compras está vacía.");
            return;
        }
        System.out.println("\n--- Lista de Compras ---");
        for (int i = 0; i < productos.size(); i++) {
            System.out.println((i + 1) + ". " + productos.get(i));
        }
        System.out.println("------------------------");
    }

    public static void main(String[] args) {
        ListaCompras miLista = new ListaCompras();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        String producto;

        do {
            System.out.println("\n--- MENÚ DE LISTA DE COMPRAS ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Buscar producto");
            System.out.println("4. Contar productos");
            System.out.println("5. Ordenar alfabéticamente");
            System.out.println("6. Mostrar lista");
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
                    System.out.print("Nombre del producto a agregar: ");
                    producto = scanner.nextLine();
                    miLista.agregarProducto(producto);
                    break;
                case 2:
                    System.out.print("Nombre del producto a eliminar: ");
                    producto = scanner.nextLine();
                    miLista.eliminarProducto(producto);
                    break;
                case 3:
                    System.out.print("Nombre del producto a buscar: ");
                    producto = scanner.nextLine();
                    if (miLista.buscarProducto(producto)) {
                        System.out.println("'" + producto + "' SÍ está en la lista.");
                    } else {
                        System.out.println("'" + producto + "' NO está en la lista.");
                    }
                    break;
                case 4:
                    System.out.println("Total de productos en la lista: " + miLista.contarProductos());
                    break;
                case 5:
                    miLista.ordenarAlfabeticamente();
                    miLista.mostrarLista();
                    break;
                case 6:
                    miLista.mostrarLista();
                    break;
                case 0:
                    System.out.println("Saliendo del programa. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}