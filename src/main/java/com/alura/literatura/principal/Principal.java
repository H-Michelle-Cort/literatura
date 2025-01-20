package com.alura.literatura.principal;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Datos;
import com.alura.literatura.model.DatosLibros;
import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import jakarta.transaction.Transactional;

import java.util.*;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<Libro> libros;
    private List<Autor> autores;
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository libroRepositorio, AutorRepository autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
            *******************  LITERALURA  *******************
            ----------------------------------------------------                    
            *** INGRESE UN NUMERO VALIDO PRESENTADO EN EL MENÚ ***
            
            1 - Buscar libro por título
            2 - Listado de libros registrados
            3 - Listado de autores registrados
            4 - Buscar autores vivos en un determinado año
            5 - Buscar libro por idioma
            0 - Salir
            
            ----------------------------------------------------
            """;
            System.out.println(menu);

            try {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Consumir la línea restante

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        mostrarListadoLibros();
                        break;
                    case 3:
                        mostrarListadoAutores();
                        break;
                    case 4:
                        buscarAutoresVivos();
                        break;
                    case 5:
                        buscarLibroPorIdioma();
                        break;
                    case 0:
                        System.out.println("\nAplicacion cerrada, vuelva pronto ...");
                        break;

                    default:
                        System.out.println("Opcion NO valida. Elija una opción válida del menú.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opcion NO valida. Elija una opción válida del menú.");
                teclado.nextLine();
            }
        }
    }

    private void buscarLibro() {
        System.out.println("\nIngrese el nombre del libro:");
        var tituloLibro = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream().findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("\nLibro encontrado\n");

            DatosLibros datosLibro = libroBuscado.get();

            // Verificador: Libro: Existe en la base de datos
            Optional<Libro> libroExistente = libroRepositorio.findByTitulo(datosLibro.titulo());

            if (libroExistente.isPresent()) {
                System.out.println("\nESTE LIBRO YA SE ENCUENTRA REGISTRADO EN LA BASE DE DATOS\n");
            } else {
                Libro libro = new Libro(datosLibro);
                libroRepositorio.save(libro);

                System.out.println("********* LIBRO *********");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Idiomas: " + libro.getIdiomas());
                System.out.println("Número de Descargas: " + libro.getNumeroDeDescargas());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("*************************");
            }
        } else {
            System.out.println("\nLibro NO encontrado\n");
        }
    }

    @Transactional
    private void mostrarListadoLibros() { // imprimir con tostring
        libros = libroRepositorio.findAll(); // Inicialización de la variable libros
        System.out.println("\nLibros encontrados:\n");
        libros.forEach(libro -> System.out.println(libro)); // Usando forEach y toString
    }


    @Transactional // imprimir con tostring
    private void mostrarListadoAutores() {
        autores = autorRepositorio.findAll();
        System.out.println("\nAutores registrados:\n");
        autores.forEach(autor -> System.out.println(autor)); // Usando forEach y toString
    }

    private void buscarAutoresVivos() {
        System.out.println("\nIngrese el año para consultar si el autor esta activo:");
        int año = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivos = autorRepositorio.findAutoresVivosEnAño(año);
        System.out.println("\nAutor activo " + año + ":");
        for (Autor autor : autoresVivos) {
            System.out.println(autor);
        }
    }

    private void buscarLibroPorIdioma() {
        Scanner teclado = new Scanner(System.in);

        // Mensaje inicial con opciones de idioma, cada una en una línea
        System.out.println("\nElija el idioma del libro:");
        System.out.println("es - español");
        System.out.println("en - inglés");
        System.out.println("fr - francés");
        System.out.println("pt - portugués");

        String idioma;
        boolean idiomaValido = false;

        // Bucle para obtener opcion valida
        do {
            System.out.print("\nIngrese el código del idioma: ");
            idioma = teclado.nextLine().toLowerCase();

            if (idioma.equals("es") || idioma.equals("en") || idioma.equals("fr") || idioma.equals("pt")) {
                idiomaValido = true;
            } else {
                System.out.println("Opcion no valida. Código de idioma válido (es, en, fr, pt).");
            }
        } while (!idiomaValido);

        List<Libro> librosPorIdioma = libroRepositorio.findByIdiomas(idioma);

        System.out.println("\nLibros en el idioma " + idioma + ":");
        for (Libro libro : librosPorIdioma) {
            System.out.println(libro);
        }
    }

}
