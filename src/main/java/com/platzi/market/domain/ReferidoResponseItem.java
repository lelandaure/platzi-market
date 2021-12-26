package com.platzi.market.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class ReferidoResponseItem {
    private String nombre; //producto
    private BigDecimal precioVenta; //producto
    private int cantidadStock; //producto
    private Timestamp fecha; //compra
    private String medioPago; //compra
    private boolean estado; //comprasProducto
    private String descripcion; //categoria
}