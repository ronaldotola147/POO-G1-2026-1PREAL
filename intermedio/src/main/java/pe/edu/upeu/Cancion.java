package pe.edu.upeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

// Clase Cancion CORREGIDA
class Cancion {
    String titulo;
    String artista;
    int duracionSeg; // Duración en segundos
    String genero;

    public Cancion(String titulo, String artista, int duracionSeg, String genero) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracionSeg = duracionSeg;
        this.genero = genero;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public int getDuracionSeg() { return duracionSeg; }
    public String getGenero() { return genero; }

    @Override
    public String toString() {
        int minutos = duracionSeg / 60;
        int segundos = duracionSeg % 60;
        // CORRECCIÓN AQUÍ: El formato %02d es para los segundos, y el género va al final.
        return String.format("'%s' de %s (%d:%02d) [%s]", titulo, artista, minutos, segundos, genero);
    }

    // Sobreescribe equals() y hashCode() para poder usar .remove() correctamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cancion cancion = (Cancion) o;
        // Consideramos que dos canciones son iguales si tienen el mismo título, artista y duración
        return duracionSeg == cancion.duracionSeg &&
                Objects.equals(titulo, cancion.titulo) &&
                Objects.equals(artista, cancion.artista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, artista, duracionSeg, genero);
    }
}

// Clase PlaylistMusical (mantengo el resto del código como estaba, solo para que compile)
class PlaylistMusical {
    private ArrayList<Cancion> playlist;

    public PlaylistMusical() {
        playlist = new ArrayList<>();
    }

    public void agregarCancion(Cancion cancion) {
        playlist.add(cancion);
        System.out.println("Canción '" + cancion.getTitulo() + "' agregada a la playlist.");
    }

    public void eliminarCancion(String tituloCancion) {
        boolean eliminado = playlist.removeIf(c -> c.getTitulo().equalsIgnoreCase(tituloCancion));
        if (eliminado) {
            System.out.println("Canción '" + tituloCancion + "' eliminada de la playlist.");
        } else {
            System.out.println("Canción '" + tituloCancion + "' no encontrada en la playlist.");
        }
    }

    public void moverCancion(String tituloCancion, int nuevaPosicion) {
        int indiceActual = -1;
        for (int i = 0; i < playlist.size(); i++) {
            if (playlist.get(i).getTitulo().equalsIgnoreCase(tituloCancion)) {
                indiceActual = i;
                break;
            }
        }

        if (indiceActual != -1) {
            if (nuevaPosicion >= 0 && nuevaPosicion < playlist.size()) {
                Cancion cancion = playlist.remove(indiceActual);
                playlist.add(nuevaPosicion, cancion);
                System.out.println("Canción '" + tituloCancion + "' movida a la posición " + (nuevaPosicion + 1) + ".");
            } else {
                System.out.println("Posición inválida. Debe estar entre 1 y " + playlist.size());
            }
        } else {
            System.out.println("Canción '" + tituloCancion + "' no encontrada en la playlist.");
        }
    }

    public void reproducirAleatoria() {
        if (playlist.isEmpty()) {
            System.out.println("La playlist está vacía. No se puede reproducir.");
            return;
        }
        Collections.shuffle(playlist);
        System.out.println("\n--- Reproduciendo playlist en orden aleatorio ---");
        playlist.stream().limit(3).forEach(c -> System.out.println("-> " + c));
        if (playlist.size() > 3) {
            System.out.println("(y " + (playlist.size() - 3) + " canciones más...)");
        }
        System.out.println("----------------------------------------------");
    }

    public int duracionTotal() {
        return playlist.stream()
                .mapToInt(Cancion::getDuracionSeg)
                .sum();
    }

    public void filtrarPorGenero(String genero) {
        System.out.println("\n--- Canciones de género '" + genero + "' ---");
        List<Cancion> filtradas = playlist.stream()
                .filter(c -> c.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
        if (filtradas.isEmpty()) {
            System.out.println("No hay canciones de este género.");
        } else {
            filtradas.forEach(System.out::println);
        }
        System.out.println("------------------------------------------");
    }

    public void buscarPorArtista(String artista) {
        System.out.println("\n--- Canciones del artista '" + artista + "' ---");
        List<Cancion> encontradas = playlist.stream()
                .filter(c -> c.getArtista().equalsIgnoreCase(artista))
                .collect(Collectors.toList());
        if (encontradas.isEmpty()) {
            System.out.println("No se encontraron canciones de este artista.");
        } else {
            encontradas.forEach(System.out::println);
        }
        System.out.println("----------------------------------------------");
    }

    public void mostrarPlaylist() {
        if (playlist.isEmpty()) {
            System.out.println("La playlist está vacía.");
            return;
        }
        System.out.println("\n--- MI PLAYLIST ---");
        playlist.stream()
                .sorted(Comparator.comparing(Cancion::getTitulo))
                .forEach(c -> System.out.println("- " + c));
        System.out.println("-------------------");
    }

    public static void main(String[] args) {
        PlaylistMusical miPlaylist = new PlaylistMusical();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        miPlaylist.agregarCancion(new Cancion("Bohemian Rhapsody", "Queen", 354, "Rock"));
        miPlaylist.agregarCancion(new Cancion("Thriller", "Michael Jackson", 357, "Pop"));
        miPlaylist.agregarCancion(new Cancion("Hotel California", "Eagles", 390, "Rock"));
        miPlaylist.agregarCancion(new Cancion("Shape of You", "Ed Sheeran", 233, "Pop"));
        miPlaylist.agregarCancion(new Cancion("Despacito", "Luis Fonsi", 279, "Pop Latino"));

        do {
            System.out.println("\n--- MENÚ PLAYLIST MUSICAL ---");
            System.out.println("1. Agregar canción");
            System.out.println("2. Eliminar canción");
            System.out.println("3. Mover canción");
            System.out.println("4. Reproducir aleatoriamente");
            System.out.println("5. Ver duración total");
            System.out.println("6. Filtrar por género");
            System.out.println("7. Buscar por artista");
            System.out.println("8. Mostrar playlist");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            // Manejo de entrada no numérica
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
                scanner.next(); // Consumir la entrada inválida
                System.out.print("Elige una opción: ");
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Título: "); String titulo = scanner.nextLine();
                    System.out.print("Artista: "); String artista = scanner.nextLine();
                    System.out.print("Duración en segundos: ");
                    int duracion = 0;
                    try {
                        duracion = scanner.nextInt();
                        if (duracion <= 0) throw new IllegalArgumentException("La duración debe ser positiva.");
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Duración inválida. Ingresa un número entero.");
                        scanner.nextLine(); // Limpiar la entrada inválida
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine(); // Consumir el salto de línea
                    System.out.print("Género: "); String genero = scanner.nextLine();
                    miPlaylist.agregarCancion(new Cancion(titulo, artista, duracion, genero));
                    break;
                case 2:
                    System.out.print("Título de la canción a eliminar: ");
                    miPlaylist.eliminarCancion(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Título de la canción a mover: "); String tMover = scanner.nextLine();
                    System.out.print("Nueva posición (1 en adelante): ");
                    int pos = 0;
                    try {
                        pos = scanner.nextInt();
                        if (pos <= 0) throw new IllegalArgumentException("La posición debe ser 1 o mayor.");
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Posición inválida. Ingresa un número entero.");
                        scanner.nextLine(); // Limpiar la entrada inválida
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        scanner.nextLine();
                        break;
                    }
                    scanner.nextLine(); // Consumir el salto de línea
                    miPlaylist.moverCancion(tMover, pos - 1); // -1 porque los índices son base 0
                    break;
                case 4:
                    miPlaylist.reproducirAleatoria();
                    break;
                case 5:
                    int totalSegundos = miPlaylist.duracionTotal();
                    int min = totalSegundos / 60;
                    int seg = totalSegundos % 60;
                    System.out.printf("Duración total de la playlist: %d:%02d\n", min, seg);
                    break;
                case 6:
                    System.out.print("Género a filtrar: ");
                    miPlaylist.filtrarPorGenero(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Artista a buscar: ");
                    miPlaylist.buscarPorArtista(scanner.nextLine());
                    break;
                case 8:
                    miPlaylist.mostrarPlaylist();
                    break;
                case 0:
                    System.out.println("Saliendo del reproductor. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
        scanner.close();
    }
}