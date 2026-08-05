# Rendimiento con algoritmos de ordenamiento y búsqueda

Proyecto final en Java para administrar 50 productos de una tienda en línea y comparar Merge Sort, Quick Sort y Heap Sort. Incluye ordenamiento por precio ascendente y calificación descendente, búsqueda binaria por id y búsqueda lineal por subcadena de nombre.

## Requisitos

- Java 17 o superior.
- Visual Studio Code con el paquete de extensiones **Extension Pack for Java**.

## Abrirlo en Visual Studio Code

1. Descomprime el archivo ZIP.
2. Abre Visual Studio Code.
3. Selecciona **Archivo > Abrir carpeta**.
4. Elige la carpeta `ProyectoFinalAlgoritmos`.
5. Abre `src/com/tienda/app/Principal.java`.
6. Presiona el botón **Run** que aparece encima del método `main`.

También puedes presionar `Ctrl + Shift + P`, escribir **Tasks: Run Task** y
seleccionar **Ejecutar proyecto**. La configuración ya está incluida.

## Ejecución

Desde la raíz del proyecto, en Linux/macOS:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out com.tienda.app.Principal
```

En Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force out
$archivos = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out $archivos
java -cp out com.tienda.app.Principal
```

El programa valida automáticamente los 50 ids únicos, verifica que cada algoritmo ordene correctamente y guarda los datos en `resultados/productos.csv` y las mediciones en `resultados/tiempos.csv`.

## Menú interactivo

Al ejecutar `Principal.java` aparecerán estas opciones:

1. Mostrar los 50 productos.
2. Ordenar por precio ascendente.
3. Ordenar por calificación descendente.
4. Buscar un producto por ID.
5. Buscar productos por nombre o subcadena.
6. Ejecutar las mediciones solicitadas en el proyecto.
7. Salir del programa.

> Los tiempos cambian según el computador, la JVM y los procesos activos. Para reducir el ruido, el programa calienta la JVM y reporta el promedio de miles de ejecuciones sobre copias idénticas del catálogo.

## Estructura

- `modelo/Producto.java`: clase de dominio.
- `datos/GeneradorProductos.java`: catálogo aleatorio reproducible.
- `algoritmos/AlgoritmosOrdenamiento.java`: Merge, Quick y Heap Sort genéricos.
- `algoritmos/AlgoritmosBusqueda.java`: búsqueda binaria y lineal.
- `app/Principal.java`: pruebas, cronometraje, validación y exportación.
