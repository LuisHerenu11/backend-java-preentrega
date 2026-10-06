package com.techlab.pedidos;

import com.techlab.productos.Producto;
import com.techlab.excepciones.StockInsuficienteException;
import java.util.ArrayList;

public class Pedido {
    private static int contadorId = 1;
    private int id;
    private ArrayList<LineaPedido> lineas;

    public Pedido() {
        this.id = contadorId++;
        this.lineas = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) throws StockInsuficienteException {
        if (producto.getCantidadEnStock() < cantidad) {
            throw new StockInsuficienteException("No hay stock suficiente de " + producto.getNombre() + ". Stock actual: " + producto.getCantidadEnStock());
        }
        lineas.add(new LineaPedido(producto, cantidad));
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.calcularSubtotal();
        }
        return total;
    }

    public void confirmarPedido() {
        for (LineaPedido linea : lineas) {
            Producto p = linea.getProducto();
            int nuevoStock = p.getCantidadEnStock() - linea.getCantidad();
            p.setCantidadEnStock(nuevoStock);
        }
    }

    public void mostrarDetalle() {
        System.out.println("\n=== DETALLE DEL PEDIDO #" + id + " ===");
        for (LineaPedido linea : lineas) {
            System.out.println("- " + linea.getProducto().getNombre() + " (x" + linea.getCantidad() + ") -> Subtotal: $" + linea.calcularSubtotal());
        }
        System.out.println("TOTAL A PAGAR: $" + calcularTotal());
        System.out.println("================================");
    }
}
