# Literatura

<p>
Proyecto centrado en crear un catálogo de libros que permita interacción textual (vía consola) con cinco opciones. El proyecto manipula datos JSON, filtra y muestra los libros y autores de interés, y permite       guardarlos en una base de datos. Además, consume la API de Gutendex, que es un catálogo con información de más de 70,000 libros.
</p>

## :key: Interfaz textual

![Image](https://github.com/user-attachments/assets/5478d7e1-746b-4b29-8b86-d7e5d359a3e0)

## :computer: Herramientas de Desarrollo
<p>
 - IntelliJ IDEA
 
 +Java version "17.0.12"
 
-Maven

  +versión 4 en adelante
  
-Spring Boot:

  +versión 3.2.3
  
  -PostgreSQL:

   +versión 16 en adelante

-Dependencias:

 *Spring Data JPA
  
 *PostgreSQL Driver
  
 *Jackson (sugerido: versión 2.16)

</p>

### Interacción del usuario
<p>
  *Buscar libro por título: Permite encontrar un libro específico mediante su título a través de la API. 
  *Listado de libros registrados: Muestra todos los libros que se encuentran almacenados en la base de datos. 
  *Listado de autores registrados: Muestra todos los autores que están guardados en la base de datos. 
  *Buscar autores vivos en un año específico: Filtra los autores que estaban activos durante el año indicado. 
  *Buscar libro por idioma: Permite buscar libros según su idioma, al ingresar a esta opcion se deplegaran los idiomas disponibles.
  *Salir: Finalizar la aplicación o proceso actual.
</p>


![Image](https://github.com/user-attachments/assets/80a72ab0-6e53-4bb0-8514-a2e2196a2f8d)
