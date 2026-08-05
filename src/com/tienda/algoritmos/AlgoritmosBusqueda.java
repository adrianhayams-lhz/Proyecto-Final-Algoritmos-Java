package com.tienda.algoritmos;

import com.tienda.modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class AlgoritmosBusqueda {
    private AlgoritmosBusqueda() { }

    /**
     * Busca un producto por id.
     * La lista debe estar ordenada antes de usar este método.
     */
    public static Producto busquedaBinariaPorId(List<Producto> productos, int id) {
        int izquierda = 0, derecha = productos.size() - 1;
        while (izquierda <= derecha) {
            // calculamos la posición central
            int medio = (izquierda + derecha) >>> 1;
            Producto actual = productos.get(medio);
            if (actual.getId() == id) return actual;

            // descartamos la mitad donde no puede estar el id
            if (actual.getId() < id) izquierda = medio + 1;
            else derecha = medio - 1;
        }
        // si no aparece se devuelve null
        return null;
    }

    public static List<Producto> busquedaLinealPorNombre(List<Producto> productos, String subcadena) {
        // pasamos todo a minúsculas para comparar mejor
        String consulta = subcadena.toLowerCase(Locale.ROOT);
        List<Producto> resultados = new ArrayList<>();

        // revisamos todos los productos uno por uno
        for (Producto producto : productos)
            if (producto.getNombre().toLowerCase(Locale.ROOT).contains(consulta)) resultados.add(producto);
        return resultados;
    }
}
