package com.tienda.datos;

import com.tienda.modelo.Producto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * crea los productos utilizados en las pruebas.
 * se dejo separado para poder cambiar los datos fácilmente.
 */
public final class GeneradorProductos {
    // cinco nombres por categoría para variar el catálogo
    private static final String[] NOMBRES = {
        "Auriculares Bluetooth", "Cámara Web HD", "Teclado Mecánico", "Ratón Inalámbrico",
        "Monitor LED", "Camisa de Algodón", "Pantalón Deportivo", "Chaqueta Impermeable",
        "Zapatillas Urbanas", "Vestido Casual", "Novela Histórica", "Manual de Programación",
        "Libro de Cocina", "Atlas Universal", "Cuentos Infantiles", "Lámpara de Mesa",
        "Juego de Sábanas", "Cafetera Eléctrica", "Organizador Modular", "Sartén Antiadherente"
    };
    private static final String[] MARCAS = {"Nova", "Orion", "Apex", "Lumen", "Terra"};
    private static final String[] CATEGORIAS = {"Electrónica", "Ropa", "Libros", "Hogar"};

    // no hace falta crear objetos de esta clase
    private GeneradorProductos() { }

    public static List<Producto> generar(int cantidad, long semilla) {
        // la semilla hace que siempre se generen los mismos datos
        Random random = new Random(semilla);
        List<Integer> ids = new ArrayList<>();

        // se crean ids únicos y luego se mezclan
        for (int i = 1; i <= cantidad; i++) ids.add(1000 + i);
        Collections.shuffle(ids, random);

        List<Producto> productos = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            // combinamos nombre, marca y número para no repetir nombres
            String base = NOMBRES[random.nextInt(NOMBRES.length)];
            String nombre = base + " " + MARCAS[random.nextInt(MARCAS.length)] + " " + (i + 1);
            String categoria = categoriaPara(base);

            // valores aleatorios dentro de rangos razonables
            double precio = Math.round((5.00 + random.nextDouble() * 495.00) * 100.0) / 100.0;
            int stock = random.nextInt(201);
            double calificacion = Math.round((1.0 + random.nextDouble() * 4.0) * 10.0) / 10.0;
            productos.add(new Producto(ids.get(i), nombre, precio, categoria, stock, calificacion));
        }
        return productos;
    }

    private static String categoriaPara(String nombre) {
        // cada grupo de cinco nombres pertenece a una categoría
        int indice = java.util.Arrays.asList(NOMBRES).indexOf(nombre);
        return CATEGORIAS[Math.min(indice / 5, CATEGORIAS.length - 1)];
    }
}
