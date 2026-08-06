Proyecto final de algoritmos

En este proyecto hice un programa en Java que trabaja con una lista de 50 productos de una tienda. La idea principal es probar tres algoritmos de ordenamiento: Merge Sort, Quick Sort y Heap Sort, para comparar el tiempo que tarda cada uno.

El programa también permite ordenar los productos por precio o calificación y hacer búsquedas por id o por una parte del nombre.

Que se necesita

Java 17 o superior.

Visual Studio Code con el paquete de extensiones Extension Pack for Java.

Como abrir el proyecto

Abre la carpeta del proyecto en Visual Studio Code.

Entra a src/com/tienda/app/Principal.java.

Presiona el botón Run que aparece arriba del método main.

Otra forma de correrlo es presionar Ctrl + Shift + P, escribir Tasks: Run Task y seleccionar Ejecutar proyecto.

Ejecutarlo desde la terminal

Desde la raíz del proyecto, en Linux/macOS:

mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out com.tienda.app.Principal

En Windows PowerShell:

New-Item -ItemType Directory -Force out
$archivos = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out $archivos
java -cp out com.tienda.app.Principal

Cuando se ejecuta, el programa revisa que los 50 productos tengan ids diferentes y que los algoritmos estén ordenando correctamente. Los productos se guardan en resultados/productos.csv y los tiempos obtenidos en resultados/tiempos.csv.

Opciones del programa

Al correr Principal.java aparece un menú con estas opciones:

Mostrar los 50 productos.

Ordenar por precio ascendente.

Ordenar por calificación descendente.

Buscar un producto por ID.

Buscar productos por nombre o subcadena.

Ejecutar las mediciones solicitadas en el proyecto.

Salir del programa.

Los tiempos pueden cambiar un poco dependiendo de la computadora y de los programas que estén abiertos en ese momento.

Archivos principales

modelo/Producto.java: contiene los datos de cada producto.

datos/GeneradorProductos.java: se encarga de crear la lista de productos.

algoritmos/AlgoritmosOrdenamiento.java: contiene Merge Sort, Quick Sort y Heap Sort.

algoritmos/AlgoritmosBusqueda.java: contiene la búsqueda binaria y la búsqueda lineal.

app/Principal.java: ejecuta el menú y las pruebas del programa.
