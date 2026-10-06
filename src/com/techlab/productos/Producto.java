package com.techlab.productos;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int cantidadEnStock;

    // Constructor
    public Producto(int id, String nombre, double precio, int cantidadEnStock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadEnStock = cantidadEnStock;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadEnStock() {
        return cantidadEnStock;
    }

    // Setters con validación
    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }

    public void setCantidadEnStock(int cantidadEnStock) {
        if (cantidadEnStock >= 0) {
            this.cantidadEnStock = cantidadEnStock;
        }
    }

    // Método para imprimir el producto fácilmente
    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Precio: $" + precio + " | Stock: " + cantidadEnStock;
    }
}