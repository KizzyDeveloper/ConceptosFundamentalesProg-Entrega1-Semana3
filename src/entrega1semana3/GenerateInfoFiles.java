package entrega1semana3;

import java.io.*;
import java.util.*;

public class GenerateInfoFiles {

    // Listas de nombres, apellidos y productos para generar datos aleatorios
    private static final String[] NAMES = {"Tatiana", "Andres", "Erika", "Blanca", "Oliver", "Julian"};
    private static final String[] SURNAMES = {"Garcia", "Palacio", "Marquez", "Perez", "Velazques", "Ruiz"};
    private static final String[] PRODUCTS = {"Laptop", "Telefono", "Tablet", "Audifonos", "Monitor", "Teclado"};

    private static List<String> validProducts = new ArrayList<>();
    private static List<String> validSalesmen = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Generar archivos de prueba
            createSalesManInfoFile(5); // Genera información de 5 vendedores
            createProductsFile(5);     // Genera información de 5 productos
            createSalesMenFile(10);    // Genera archivo de ventas para vendedores existentes
            System.out.println("Archivos generados con éxito.");
        } catch (IOException e) {
            System.out.println("Error al generar los archivos: " + e.getMessage());
        }
    }

    /**
     * Genera un archivo de información de vendedores.
     * Cada línea tiene el formato: TipoDocumento;NúmeroDocumento;Nombre;Apellido
     * @param salesmanCount Número de vendedores a generar.
     * @throws IOException Si ocurre un error durante la escritura del archivo.
     */
    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        FileWriter writer = new FileWriter("vendedores.txt");
        Random random = new Random();

        for (int i = 0; i < salesmanCount; i++) {
            String typeDoc = "CC"; // Tipo de documento fijo como ejemplo
            long id = 10000000 + random.nextInt(90000000); // ID aleatorio
            String name = NAMES[random.nextInt(NAMES.length)];
            String surname = SURNAMES[random.nextInt(SURNAMES.length)];
            writer.write(typeDoc + ";" + id + ";" + name + ";" + surname + "\n");
            validSalesmen.add(id + ""); // Guardamos el ID del vendedor generado
        }

        writer.close();
    }

    /**
     * Genera un archivo de información de productos.
     * Cada línea tiene el formato: IDProducto;NombreProducto;PrecioPorUnidad
     * @param productsCount Número de productos a generar.
     * @throws IOException Si ocurre un error durante la escritura del archivo.
     */
    public static void createProductsFile(int productsCount) throws IOException {
        FileWriter writer = new FileWriter("productos.txt");
        Random random = new Random();

        for (int i = 0; i < productsCount; i++) {
            String productID = "P" + (100 + i); // ID de producto incremental
            String productName = PRODUCTS[random.nextInt(PRODUCTS.length)];
            double price = 100 + random.nextDouble() * 1000; // Precio aleatorio
            writer.write(productID + ";" + productName + ";" + String.format("%.2f", price) + "\n");
            validProducts.add(productID); // Guardamos el ID del producto generado
        }

        writer.close();
    }

    /**
     * Genera un archivo de ventas de vendedores. Solo se generan ventas
     * de productos válidos y vendedores existentes.
     * Cada línea tiene el formato: IDProducto;CantidadProductoVendido
     * @param randomSalesCount Número de ventas a generar.
     * @throws IOException Si ocurre un error durante la escritura del archivo.
     */
    public static void createSalesMenFile(int randomSalesCount) throws IOException {
        Random random = new Random();

        for (String salesmanID : validSalesmen) {
            FileWriter writer = new FileWriter("ventas_" + salesmanID + ".txt");

            // Escribimos el vendedor en el archivo
            writer.write("CC;" + salesmanID + "\n");

            // Generamos ventas de productos que existen
            for (int i = 0; i < randomSalesCount; i++) {
                String productID = validProducts.get(random.nextInt(validProducts.size())); // Producto válido
                int quantity = 1 + random.nextInt(10); // Cantidad de producto vendida
                writer.write(productID + ";" + quantity + "\n");
            }

            writer.close();
        }
    }
}
