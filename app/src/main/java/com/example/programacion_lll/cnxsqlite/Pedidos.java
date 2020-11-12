package com.example.programacion_lll.cnxsqlite;

public class Pedidos {
    String id;
    String numero_pedido;
    String nombre_cliente;
    String direcion;
    String pedidos;
    String urlImg;

    public Pedidos(String id, String numero_pedido, String nombre_cliente, String direcion, String pedidos,  String urlImg) {
        this.id = id;
        this.numero_pedido = numero_pedido;
        this.nombre_cliente = nombre_cliente;
        this.direcion = direcion;
        this.pedidos = pedidos;
        this.urlImg = urlImg;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero_pedido() {
        return numero_pedido;
    }

    public void setNumero_pedido(String numero_pedido) {
        this.numero_pedido = numero_pedido;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getDirecion() {
        return direcion;
    }

    public void setDirecion(String direcion) {
        this.direcion = direcion;
    }

    public String getPedidos() {
        return pedidos;
    }

    public void setPedidos(String pedidos) {
        this.pedidos = pedidos;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

}
