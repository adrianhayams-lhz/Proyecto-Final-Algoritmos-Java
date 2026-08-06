# Proyecto final de algoritmos

En este proyecto hice un programa en Java que trabaja con una lista de 50 productos de una tienda. El programa permite ordenar los productos por precio y calificación, además de buscarlos por su id o escribiendo una parte del nombre.

También utilicé Merge Sort, Quick Sort y Heap Sort para comparar el tiempo que tarda cada algoritmo usando los mismos productos.

## Requisitos

- Java 17 o superior.
- Visual Studio Code.
- Extensión **Extension Pack for Java**.

## Cómo abrir el proyecto

1. Abre la carpeta del proyecto en Visual Studio Code.
2. Entra a `src/com/tienda/app/Principal.java`.
3. Presiona el botón **Run** que aparece arriba del método `main`.

También se puede ejecutar presionando `Ctrl + Shift + P`, buscando **Tasks: Run Task** y seleccionando **Ejecutar proyecto**.

## Ejecutarlo desde la terminal

En Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force out
$archivos = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out $archivos
java -cp out com.tienda.app.Principal
```

En Linux o macOS:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out com.tienda.app.Principal
```

## Opciones del programa

Cuando se ejecuta el programa aparece un menú con estas opciones:

1. Mostrar los 50 productos.
2. Ordenarlos por precio de menor a mayor.
3. Ordenarlos por calificación de mayor a menor.
4. Buscar un producto por su id.
5. Buscar productos por nombre.
6. Realizar las pruebas de tiempo.
7. Salir del programa.

## Resultados

El programa comprueba que los productos tengan ids diferentes y que los algoritmos ordenen correctamente.

La lista de productos se guarda en `resultados/productos.csv` y los tiempos obtenidos se guardan en `resultados/tiempos.csv`.

Los tiempos pueden variar dependiendo de la computadora y de los programas que estén abiertos mientras se realizan las pruebas.

## Archivos principales

- `modelo/Producto.java`: guarda los datos de cada producto.
- `datos/GeneradorProductos.java`: crea la lista de productos.
- `algoritmos/AlgoritmosOrdenamiento.java`: contiene los tres métodos de ordenamiento.
- `algoritmos/AlgoritmosBusqueda.java`: contiene las búsquedas.
- `app/Principal.java`: ejecuta el menú, las pruebas y guarda los resultados.
