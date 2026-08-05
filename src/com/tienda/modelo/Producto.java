package com.tienda.modelo;

import java.util.Locale;

/**
 * representa un producto del catálogo.
 * Los algoritmos se dejaron en otras clases para mantener todo ordenado.
 */
public class Producto {
    // id único usado en la búsqueda binaria
    private final int id;
    // nombre que se muestra en el catálogo
    private final String nombre;
    // double permite guardar precios con decimales
    private final double precio;
    private final String categoria;
    private final int stock;
    private final double calificacionPromedio;

    public Producto(int id, String nombre, double precio, String categoria,
                    int stock, double calificacionPromedio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
        this.calificacionPromedio = calificacionPromedio;
    }

    // métodos get para consultar los datos
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
    public int getStock() { return stock; }
    public double getCalificacionPromedio() { return calificacionPromedio; }

    @Override
    public String toString() {
        // formato usado para mostrar el producto en consola
        return String.format(Locale.US,
                "Producto{id=%d, nombre='%s', precio=%.2f, categoria='%s', stock=%d, calificacion=%.1f}",
                id, nombre, precio, categoria, stock, calificacionPromedio);
    }
}
