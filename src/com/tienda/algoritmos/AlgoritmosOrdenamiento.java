package com.tienda.algoritmos;

import java.util.Comparator;
import java.util.List;

/**
 * Contiene los tres algoritmos de ordenamiento del proyecto.
 * Comparator permite elegir el atributo que se quiere ordenar.
 */
public final class AlgoritmosOrdenamiento {
    private AlgoritmosOrdenamiento() { }

    public static <T> void mergeSort(List<T> lista, Comparator<T> comparador) {
        // con menos de dos elementos no hay nada que ordenar
        if (lista.size() < 2) return;
        mergeSort(lista, comparador, 0, lista.size() - 1);
    }

    private static <T> void mergeSort(List<T> a, Comparator<T> c, int inicio, int fin) {
        if (inicio >= fin) return;

        // dividimos la lista en dos partes
        int medio = (inicio + fin) >>> 1;
        mergeSort(a, c, inicio, medio);
        mergeSort(a, c, medio + 1, fin);

        // aquí juntamos las dos partes ordenadas
        java.util.ArrayList<T> temp = new java.util.ArrayList<>(fin - inicio + 1);
        int i = inicio, j = medio + 1;
        while (i <= medio && j <= fin)
            temp.add(c.compare(a.get(i), a.get(j)) <= 0 ? a.get(i++) : a.get(j++));
        while (i <= medio) temp.add(a.get(i++));
        while (j <= fin) temp.add(a.get(j++));
        // copiamos el resultado a la lista original
        for (int k = 0; k < temp.size(); k++) a.set(inicio + k, temp.get(k));
    }

    public static <T> void quickSort(List<T> lista, Comparator<T> comparador) {
        quickSort(lista, comparador, 0, lista.size() - 1);
    }

    private static <T> void quickSort(List<T> a, Comparator<T> c, int bajo, int alto) {
        if (bajo >= alto) return;

        // separamos los elementos usando el pivote
        int p = particionar(a, c, bajo, alto);
        quickSort(a, c, bajo, p - 1);
        quickSort(a, c, p + 1, alto);
    }

    private static <T> int particionar(List<T> a, Comparator<T> c, int bajo, int alto) {
        // usamos el último elemento como pivote
        T pivote = a.get(alto);
        int i = bajo - 1;
        for (int j = bajo; j < alto; j++) {
            // los menores se mueven hacia la izquierda
            if (c.compare(a.get(j), pivote) <= 0) intercambiar(a, ++i, j);
        }
        intercambiar(a, i + 1, alto);
        return i + 1;
    }

    public static <T> void heapSort(List<T> lista, Comparator<T> comparador) {
        int n = lista.size();

        // primero construimos el montículo
        for (int i = n / 2 - 1; i >= 0; i--) ajustarMonticulo(lista, n, i, comparador);

        // movemos el mayor al final y ajustamos otra vez
        for (int fin = n - 1; fin > 0; fin--) {
            intercambiar(lista, 0, fin);
            ajustarMonticulo(lista, fin, 0, comparador);
        }
    }

    private static <T> void ajustarMonticulo(List<T> a, int n, int raiz, Comparator<T> c) {
        // posiciones de los hijos de la raíz
        int mayor = raiz, izquierdo = 2 * raiz + 1, derecho = 2 * raiz + 2;
        if (izquierdo < n && c.compare(a.get(izquierdo), a.get(mayor)) > 0) mayor = izquierdo;
        if (derecho < n && c.compare(a.get(derecho), a.get(mayor)) > 0) mayor = derecho;
        if (mayor != raiz) {
            // intercambiamos y seguimos revisando
            intercambiar(a, raiz, mayor);
            ajustarMonticulo(a, n, mayor, c);
        }
    }

    private static <T> void intercambiar(List<T> a, int i, int j) {
        // intercambio sencillo entre dos posiciones
        T temporal = a.get(i); a.set(i, a.get(j)); a.set(j, temporal);
    }
}
