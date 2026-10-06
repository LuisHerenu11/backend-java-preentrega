package com.techlab;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Producto;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Producto> inventario = new ArrayList<>();
    private static ArrayList<Pedido> historialPedidos = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static int contadorIdProducto = 1;

    public static void main(String[] args) {
        int opcion = 0;

        do {
            System.out.println("\n===================================");
            System.out.println("   SISTEMA DE GESTIÓN               ");
            System.out.println("===================================");
            System.out.println("1) Agregar producto");
            System.out.println("2) Listar productos");
            System.out.println("3) Buscar/Actualizar producto");
            System.out.println("4) Eliminar producto");
            System.out.println("5) Crear un pedido");
            System.out.println("6) Listar pedidos");
            System.out.println("7) Salir");
            System.out.print("Elija una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        agregarProducto();
                        break;
                    case 2:
                        listarProductos();
                        break;
                    case 3:
                        buscarActualizarProducto();
                        break;
                    case 4:
                        eliminarProducto();
                        break;
                    case 5:
                        crearPedido();
                        break;
                    case 6:
                        listarPedidos();
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema... ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
            }
        } while (opcion != 7);
    }

    private static void agregarProducto() {
        try {
            System.out.print("Nombre del producto: ");
            String nombre = sc.nextLine();
            System.out.print("Precio: ");
            double precio = Double.parseDouble(sc.nextLine());
            System.out.print("Cantidad en stock: ");
            int stock = Integer.parseInt(sc.nextLine());

            Producto nuevo = new Producto(contadorIdProducto++, nombre, precio, stock);
            inventario.add(nuevo);
            System.out.println("Producto agregado exitosamente: " + nuevo.getNombre());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de los datos ingresados. Operación cancelada.");
        }
    }

    private static void listarProductos() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        for (Producto p : inventario) {
            System.out.println(p.toString());
        }
    }

    private static void buscarActualizarProducto() {
        System.out.print("Ingrese el ID del producto a buscar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Producto encontrado = null;

            for (Producto p : inventario) {
                if (p.getId() == id) {
                    encontrado = p;
                    break;
                }
            }

            if (encontrado != null) {
                System.out.println("Producto encontrado: " + encontrado.toString());
                System.out.print("¿Desea actualizar su precio y stock? (s/n): ");
                String respuesta = sc.nextLine();
                if (respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("y")) {
                    System.out.print("Nuevo precio: ");
                    encontrado.setPrecio(Double.parseDouble(sc.nextLine()));
                    System.out.print("Nuevo stock: ");
                    encontrado.setCantidadEnStock(Integer.parseInt(sc.nextLine()));
                    System.out.println("Producto actualizado correctamente.");
                }
            } else {
                System.out.println("No se encontró ningún producto con ese ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID o datos inválidos.");
        }
    }

    private static void eliminarProducto() {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            boolean eliminado = inventario.removeIf(p -> p.getId() == id);

            if (eliminado) {
                System.out.println("Producto eliminado del inventario.");
            } else {
                System.out.println("No se encontró el producto.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void crearPedido() {
        if (inventario.isEmpty()) {
            System.out.println("No hay productos en el inventario para crear un pedido.");
            return;
        }

        Pedido nuevoPedido = new Pedido();
        boolean agregando = true;

        System.out.println("\n--- CREANDO NUEVO PEDIDO ---");
        while (agregando) {
            listarProductos();
            System.out.print("Ingrese el ID del producto a agregar (o 0 para finalizar): ");
            try {
                int id = Integer.parseInt(sc.nextLine());
                if (id == 0) break;

                Producto productoSeleccionado = null;
                for (Producto p : inventario) {
                    if (p.getId() == id) {
                        productoSeleccionado = p;
                        break;
                    }
                }

                if (productoSeleccionado != null) {
                    System.out.print("Cantidad deseada: ");
                    int cantidad = Integer.parseInt(sc.nextLine());
                    nuevoPedido.agregarProducto(productoSeleccionado, cantidad);
                    System.out.println("Producto agregado al pedido.");
                } else {
                    System.out.println("Producto no encontrado.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese números enteros.");
            } catch (StockInsuficienteException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        if (nuevoPedido.calcularTotal() > 0) {
            nuevoPedido.mostrarDetalle();
            System.out.print("¿Confirmar pedido? (s/n): ");
            if (sc.nextLine().equalsIgnoreCase("s")) {
                nuevoPedido.confirmarPedido();
                historialPedidos.add(nuevoPedido);
                System.out.println("Pedido confirmado y guardado.");
            } else {
                System.out.println("Pedido cancelado.");
            }
        } else {
            System.out.println("El pedido está vacío. Se cancela la operación.");
        }
    }

    private static void listarPedidos() {
        if (historialPedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        for (Pedido p : historialPedidos) {
            p.mostrarDetalle();
        }
    }
}
