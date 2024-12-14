package catalogo4;

import java.util.Scanner;

/**
 *
 * @Diana Sanchez
 */

public class Catalogo4 {

    public static void main(String[] args) {
        Scanner entrada  = new Scanner(System.in);

        String[] nombres = new String[20];
        String[] categorias = new String[20];
        double[] precios = new double[20];
        double[] preciosConOferta = new double[20];
        int[] cantidades = new int[20];
        int[] totalPrendas = {0}; // Cambiamos de int a un arreglo para mantener referencia
        String codigoAcceso = "admin123";

        // Prendas iniciales cargadas desde un arreglo
        
        //Object[][]
        String[][] prendasIniciales = {
                {"Camiseta básica", "Ropa casual", "15.99", "10"},
                {"Jeans", "Ropa casual", "39.99", "5"},
                {"Vestido de noche", "Ropa formal", "89.99", "2"},
                {"Sudadera", "Ropa deportiva", "25.00", "8"}
        };

        totalPrendas[0] = cargarPrendasIniciales(prendasIniciales, nombres, categorias, precios, cantidades, preciosConOferta);

        int opcion;
        do {
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Apartado para Comprador");
            System.out.println("2. Apartado para Empleado");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = entrada.nextInt();

            if (opcion == 1) {
                apartadoComprador(entrada, nombres, categorias, precios, preciosConOferta, cantidades, totalPrendas[0]);
            } else if (opcion == 2) {
                codigoAcceso = apartadoEmpleado(entrada, nombres, categorias, precios, cantidades, preciosConOferta, totalPrendas, codigoAcceso);
            } else if (opcion != 3) {
                System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    public static int cargarPrendasIniciales(String[][] prendasIniciales, String[] nombres, String[] categorias, double[] precios, int[] cantidades, double[] preciosConOferta) {
        int totalPrendas = 0;
        for (int i = 0; i < prendasIniciales.length; i++) {
            if (totalPrendas < nombres.length) {
                nombres[totalPrendas] = prendasIniciales[i][0];
                categorias[totalPrendas] = prendasIniciales[i][1];
                precios[totalPrendas] = Double.parseDouble(prendasIniciales[i][2]);
                preciosConOferta[totalPrendas] = precios[totalPrendas] * 0.9; // 10% de descuento
                cantidades[totalPrendas] = Integer.parseInt(prendasIniciales[i][3]); // --> Convierte una cadena en un entero
                totalPrendas++;
            } else {
                System.out.println("El catálogo está lleno.");
                break;
            }
        }
        return totalPrendas;
    }

    public static void mostrarCatalogo(String[] nombres, String[] categorias, double[] precios, double[] preciosConOferta, int[] cantidades, int totalPrendas) {
        System.out.println("=== Catálogo ===");
        if (totalPrendas == 0) {
            System.out.println("El catálogo está vacío.");
        } else {
            for (int i = 0; i < totalPrendas; i++) {
                System.out.println((i + 1) + ". " + nombres[i] + ", Categoría: " + categorias[i] + ", Precio: $" + precios[i] + " (Oferta: $" + preciosConOferta[i] + "), Cantidad: " + cantidades[i]);
            }
        }
    }

    public static void apartadoComprador(Scanner entrada, String[] nombres, String[] categorias, double[] precios, double[] preciosConOferta, int[] cantidades, int totalPrendas) {
        int opcion;
        do {
            System.out.println("=== Catálogo para Comprador ===");
            System.out.println("1. Ver catálogo completo");
            System.out.println("2. Ver catálogo con ofertas");
            System.out.println("3. Realizar compra");
            System.out.println("4. Regresar al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = entrada.nextInt();

            if (opcion == 1) {
                mostrarCatalogo(nombres, categorias, precios, preciosConOferta, cantidades, totalPrendas);
            } else if (opcion == 2) {
                mostrarCatalogoConOfertas(nombres, categorias, preciosConOferta, cantidades, totalPrendas);
            } else if (opcion == 3) {
                realizarCompra(entrada, nombres, precios, preciosConOferta, cantidades, totalPrendas);
            } else if (opcion != 4) {
                System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    public static void mostrarCatalogoConOfertas(String[] nombres, String[] categorias, double[] preciosConOferta, int[] cantidades, int totalPrendas) {
        System.out.println("=== Catálogo con Ofertas ===");
        for (int i = 0; i < totalPrendas; i++) {
            System.out.println((i + 1) + ". " + nombres[i] + ", Categoría: " + categorias[i] + ", Precio con Oferta: $" + preciosConOferta[i] + ", Cantidad: " + cantidades[i]);
        }
    }

    public static void realizarCompra(Scanner entrada, String[] nombres, double[] precios, double[] preciosConOferta, int[] cantidades, int totalPrendas) {
        double totalCompra = 0;
        int opcion;
        do {
            System.out.print("Seleccione el número de la prenda que desea comprar (0 para salir): ");
            opcion = entrada.nextInt();
            if (opcion > 0 && opcion <= totalPrendas) {
                if (cantidades[opcion - 1] > 0) {
                    System.out.println("Ha seleccionado: " + nombres[opcion - 1]);
                    System.out.print("¿Desea aplicar el precio con oferta? (Sí/No): ");
                    entrada.nextLine(); // Limpiar buffer
                    
                    String respuesta = entrada.nextLine().toLowerCase();
                    if (respuesta.equals("sí") || respuesta.equals("si")) {
                        totalCompra += preciosConOferta[opcion - 1];
                    } else {
                        totalCompra += precios[opcion - 1];
                    }
                    cantidades[opcion - 1]--;
                    System.out.println("Total acumulado: $" + totalCompra);
                } else {
                    System.out.println("La prenda está agotada.");
                }
            } else if (opcion != 0) {
                System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        if (totalCompra > 0) {
            System.out.println("Compra finalizada. Total a pagar: $" + totalCompra);
        }
    }

    public static String apartadoEmpleado(Scanner entrada, String[] nombres, String[] categorias, double[] precios, int[] cantidades, double[] preciosConOferta, int[] totalPrendas, String codigoAcceso) {
        System.out.print("Ingrese el código de acceso: ");
        String codigo = entrada.next();
        if (codigo.equals(codigoAcceso)) {
            int opcion;
            do {
                System.out.println("=== Apartado para Empleado ===");
                System.out.println("1. Ver catálogo");
                System.out.println("2. Agregar nueva prenda");
                System.out.println("3. Aumentar cantidad de prenda existente");
                System.out.println("4. Cambiar código de acceso");
                System.out.println("5. Regresar al menú principal");
                System.out.print("Seleccione una opción: ");
                opcion = entrada.nextInt();
                if (opcion == 1) {
                    mostrarCatalogo(nombres, categorias, precios, preciosConOferta, cantidades, totalPrendas[0]);
                } else if (opcion == 2) {
                    entrada.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese el nombre: ");
                    String nombre = entrada.nextLine();
                    System.out.print("Ingrese la categoría: ");
                    String categoria = entrada.nextLine();
                    System.out.print("Ingrese el precio: ");
                    double precio = entrada.nextDouble();
                    System.out.print("Ingrese la cantidad: ");
                    int cantidad = entrada.nextInt();
                    if (totalPrendas[0] < nombres.length) {
                        nombres[totalPrendas[0]] = nombre;
                        categorias[totalPrendas[0]] = categoria;
                        precios[totalPrendas[0]] = precio;
                        preciosConOferta[totalPrendas[0]] = precio * 0.9; // 10% descuento
                        cantidades[totalPrendas[0]] = cantidad;
                        totalPrendas[0]++;
                        System.out.println("Prenda agregada exitosamente.");
                    } else {
                        System.out.println("El catálogo está lleno.");
                    }
                } else if (opcion == 3) {
                    mostrarCatalogo(nombres, categorias, precios, preciosConOferta, cantidades, totalPrendas[0]);
                    System.out.print("Seleccione el número de la prenda para aumentar la cantidad: ");
                    int indice = entrada.nextInt();
                    
                    if (indice > 0 && indice <= totalPrendas[0]) {
                        System.out.print("Ingrese la cantidad a agregar: ");
                        int cantidadAdicional = entrada.nextInt();
                        
                        if (cantidadAdicional > 0) {
                            cantidades[indice - 1] += cantidadAdicional;
                            System.out.println("Cantidad actualizada exitosamente.");
                        } else {
                            System.out.println("La cantidad debe ser mayor a cero.");
                        }
                    } else {
                        System.out.println("Opción no válida.");
                    }
                } else if (opcion == 4) {
                    entrada.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese el nuevo código de acceso: ");
                    codigoAcceso = entrada.nextLine();
                    
                } else if (opcion != 5) {
                    System.out.println("Opción no válida.");
                }
            } while (opcion != 5);
        } else {
            System.out.println("Código de acceso incorrecto.");
        }
        return codigoAcceso;
    }
}



