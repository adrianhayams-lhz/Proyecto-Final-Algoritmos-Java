package com.tienda.app;

import com.tienda.algoritmos.AlgoritmosBusqueda;
import com.tienda.algoritmos.AlgoritmosOrdenamiento;
import com.tienda.datos.GeneradorProductos;
import com.tienda.modelo.Producto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Principal {
    // cantidad de veces que se repite cada prueba
    private static final int REPETICIONES = 5_000;

    // comparadores usados para cada tipo de orden
    private static final Comparator<Producto> POR_PRECIO = Comparator.comparingDouble(Producto::getPrecio);
    private static final Comparator<Producto> POR_CALIFICACION = Comparator.comparingDouble(Producto::getCalificacionPromedio).reversed();
    private static final Comparator<Producto> POR_ID = Comparator.comparingInt(Producto::getId);

    public static void main(String[] args) throws IOException {
        // usamos punto para los números decimales
        Locale.setDefault(Locale.US);

        // generamos los 50 productos del proyecto
        List<Producto> productos = GeneradorProductos.generar(50, 20260815L);
        verificarDatos(productos);

        // guardamos el catálogo para poder abrirlo en Excel
        Files.createDirectories(Paths.get("resultados"));
        Files.writeString(Paths.get("resultados", "productos.csv"), productosCsv(productos));

        // abrimos el menú principal
        mostrarMenu(productos);
    }

    /** Muestra el menú hasta que el usuario decida salir. */
    private static void mostrarMenu(List<Producto> productos) throws IOException {
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n====================================================");
            System.out.println("  SISTEMA DE PRODUCTOS - ALGORITMOS Y BÚSQUEDAS");
            System.out.println("====================================================");
            System.out.println("1. Mostrar los 50 productos");
            System.out.println("2. Ordenar productos por precio (ascendente)");
            System.out.println("3. Ordenar productos por calificación (descendente)");
            System.out.println("4. Buscar un producto por ID");
            System.out.println("5. Buscar productos por nombre");
            System.out.println("6. Ejecutar comparación de algoritmos");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    mostrarProductos(productos);
                    break;
                case 2:
                    mostrarOrdenados(productos, POR_PRECIO, "PRODUCTOS ORDENADOS POR PRECIO");
                    break;
                case 3:
                    mostrarOrdenados(productos, POR_CALIFICACION,
                            "PRODUCTOS ORDENADOS POR CALIFICACIÓN");
                    break;
                case 4:
                    buscarIdDesdeMenu(productos, teclado);
                    break;
                case 5:
                    buscarNombreDesdeMenu(productos, teclado);
                    break;
                case 6:
                    ejecutarComparacionCompleta(productos);
                    break;
                case 7:
                    System.out.println("Programa finalizado. ¡Gracias!");
                    break;
                default:
                    System.out.println("Opción inválida. Escriba un número del 1 al 7.");
            }
        }
        teclado.close();
    }

    /** Muestra los productos en forma de tabla. */
    private static void mostrarProductos(List<Producto> productos) {
        System.out.println("\nID    NOMBRE                                  PRECIO     CATEGORÍA       STOCK  CALIF.");
        System.out.println("--------------------------------------------------------------------------------------");
        for (Producto p : productos) {
            System.out.printf("%-5d %-39s B/.%-8.2f %-15s %-6d %.1f%n",
                    p.getId(), p.getNombre(), p.getPrecio(), p.getCategoria(),
                    p.getStock(), p.getCalificacionPromedio());
        }
    }

    /** ordena una copia para no cambiar la lista original. */
    private static void mostrarOrdenados(List<Producto> productos,
            Comparator<Producto> comparador, String titulo) {
        List<Producto> copia = new ArrayList<>(productos);
        AlgoritmosOrdenamiento.mergeSort(copia, comparador);
        System.out.println("\n=== " + titulo + " ===");
        mostrarProductos(copia);
    }

    private static void buscarIdDesdeMenu(List<Producto> productos, Scanner teclado) {
        System.out.print("Escriba el ID que desea buscar (1001-1050): ");
        try {
            int id = Integer.parseInt(teclado.nextLine().trim());
            List<Producto> porId = new ArrayList<>(productos);
            AlgoritmosOrdenamiento.mergeSort(porId, POR_ID);
            Producto encontrado = AlgoritmosBusqueda.busquedaBinariaPorId(porId, id);
            if (encontrado == null) System.out.println("No existe un producto con ese ID.");
            else {
                System.out.println("Producto encontrado:");
                System.out.println(encontrado);
            }
        } catch (NumberFormatException e) {
            System.out.println("El ID debe ser un número entero.");
        }
    }

    private static void buscarNombreDesdeMenu(List<Producto> productos, Scanner teclado) {
        System.out.print("Escriba el nombre o una parte del nombre: ");
        String texto = teclado.nextLine().trim();
        List<Producto> encontrados = AlgoritmosBusqueda.busquedaLinealPorNombre(productos, texto);
        if (encontrados.isEmpty()) System.out.println("No se encontraron coincidencias.");
        else {
            System.out.println("Se encontraron " + encontrados.size() + " producto(s):");
            mostrarProductos(encontrados);
        }
    }

    /** ejecuta las mediciones y actualiza tiempos.csv. */
    private static void ejecutarComparacionCompleta(List<Producto> productos) throws IOException {
        StringBuilder csv = new StringBuilder("tipo,criterio,algoritmo,tiempo_promedio_ns\n");
        System.out.println("\n=== ORDENAMIENTO: promedio de " + REPETICIONES + " ejecuciones (ns) ===");
        medirCriterio(productos, "Precio ascendente", POR_PRECIO, csv);
        medirCriterio(productos, "Calificación descendente", POR_CALIFICACION, csv);

        List<Producto> porId = new ArrayList<>(productos);
        AlgoritmosOrdenamiento.mergeSort(porId, POR_ID);
        ejecutarBusquedas(porId, csv);
        Files.writeString(Paths.get("resultados", "tiempos.csv"), csv.toString());
        System.out.println("\nMediciones guardadas en resultados/tiempos.csv");
    }

    private static void medirCriterio(List<Producto> original, String criterio,
            Comparator<Producto> comparador, StringBuilder csv) {
        // probamos los tres algoritmos con el mismo criterio
        medir("Merge Sort", original, comparador, AlgoritmosOrdenamiento::mergeSort, criterio, csv);
        medir("Quick Sort", original, comparador, AlgoritmosOrdenamiento::quickSort, criterio, csv);
        medir("Heap Sort", original, comparador, AlgoritmosOrdenamiento::heapSort, criterio, csv);
    }

    private static void medir(String nombre, List<Producto> original, Comparator<Producto> comparador,
            BiConsumer<List<Producto>, Comparator<Producto>> algoritmo, String criterio, StringBuilder csv) {
        // hacemos unas ejecuciones antes de comenzar a medir
        for (int i = 0; i < 500; i++) algoritmo.accept(new ArrayList<>(original), comparador);
        long total = 0;
        List<Producto> ultima = null;
        for (int i = 0; i < REPETICIONES; i++) {
            // cada prueba recibe una copia de la lista original
            ultima = new ArrayList<>(original);

            // el cronometro solo mide el ordenamiento
            long inicio = System.nanoTime();
            algoritmo.accept(ultima, comparador);
            total += System.nanoTime() - inicio;
        }
        // comprobamos que el resultado este bien ordenado
        if (!estaOrdenada(ultima, comparador)) throw new IllegalStateException(nombre + " produjo un orden incorrecto");
        long promedio = total / REPETICIONES;
        System.out.printf("%-25s %-12s %,d ns%n", criterio, nombre, promedio);
        csv.append("ordenamiento,").append(criterio).append(',').append(nombre).append(',').append(promedio).append('\n');
    }

    private static void ejecutarBusquedas(List<Producto> porId, StringBuilder csv) {
        Random random = new Random(77);
        int[] existentes = new int[10], inexistentes = new int[10];

        // preparamos 10 ids existentes y 10 inexistentes
        for (int i = 0; i < 10; i++) { existentes[i] = porId.get(random.nextInt(50)).getId(); inexistentes[i] = 2001 + i; }
        long binaria = medirBinaria(porId, existentes, inexistentes, 20_000);
        // consultas con resultados y sin resultados
        String[] conResultado = {"camisa", "libro", "lámpara", "nova", "orion", "cámara", "hogar", "algodón", "mesa", "urbana"};
        String[] sinResultado = {"teléfono", "bicicleta", "reloj", "jardín", "perfume", "consola", "zapato rojo", "xyz", "tablet", "nevera"};
        long lineal = medirLineal(porId, conResultado, sinResultado, 20_000);
        System.out.println("\n=== BÚSQUEDAS: tiempo promedio total para 20 consultas ===");
        System.out.printf("Binaria por id: %,d ns%n", binaria);
        System.out.printf("Lineal por subcadena: %,d ns%n", lineal);
        csv.append("busqueda,20 consultas,Binaria por id,").append(binaria).append('\n');
        csv.append("busqueda,20 consultas,Lineal por nombre,").append(lineal).append('\n');
    }

    private static long medirBinaria(List<Producto> lista, int[] si, int[] no, int repeticiones) {
        // contamos los ids encontrados para validar la prueba
        long total = 0; int encontrados = 0;
        for (int r = 0; r < repeticiones; r++) {
            long inicio = System.nanoTime();
            for (int id : si) if (AlgoritmosBusqueda.busquedaBinariaPorId(lista, id) != null) encontrados++;
            for (int id : no) if (AlgoritmosBusqueda.busquedaBinariaPorId(lista, id) != null) encontrados++;
            total += System.nanoTime() - inicio;
        }
        if (encontrados != repeticiones * 10) throw new IllegalStateException("Error en búsqueda binaria");
        return total / repeticiones;
    }

    private static long medirLineal(List<Producto> lista, String[] si, String[] no, int repeticiones) {
        // sumamos las coincidencias encontradas
        long total = 0; int coincidencias = 0;
        for (int r = 0; r < repeticiones; r++) {
            long inicio = System.nanoTime();
            for (String q : si) coincidencias += AlgoritmosBusqueda.busquedaLinealPorNombre(lista, q).size();
            for (String q : no) coincidencias += AlgoritmosBusqueda.busquedaLinealPorNombre(lista, q).size();
            total += System.nanoTime() - inicio;
        }
        if (coincidencias == 0) throw new IllegalStateException("Las consultas válidas no produjeron coincidencias");
        return total / repeticiones;
    }

    private static boolean estaOrdenada(List<Producto> a, Comparator<Producto> c) {
        // revisamos cada pareja de elementos
        for (int i = 1; i < a.size(); i++) if (c.compare(a.get(i - 1), a.get(i)) > 0) return false;
        return true;
    }

    private static void verificarDatos(List<Producto> p) {
        // con esto comprobamos que sean 50 productos con ids únicos
        if (p.size() != 50 || p.stream().map(Producto::getId).distinct().count() != 50)
            throw new IllegalStateException("Deben existir 50 productos con id único");
    }

    private static String productosCsv(List<Producto> productos) {
        // aqui convertimos los productos al formato CSV
        StringBuilder s = new StringBuilder("id,nombre,precio,categoria,stock,calificacionPromedio\n");
        for (Producto p : productos) s.append(String.format(Locale.US, "%d,\"%s\",%.2f,%s,%d,%.1f%n",
                p.getId(), p.getNombre(), p.getPrecio(), p.getCategoria(), p.getStock(), p.getCalificacionPromedio()));
        return s.toString();
    }
}
