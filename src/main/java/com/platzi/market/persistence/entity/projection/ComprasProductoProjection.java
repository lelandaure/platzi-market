package com.platzi.market.persistence.entity.projection;

import java.time.LocalDateTime;

public interface ComprasProductoProjection {
  Boolean getEstado();

  CompraView getCompra();

  ProductoView getProducto();

  interface ProductoView {
    String getNombre();
    Double getPrecioVenta();
    Integer getCantidadStock();
    CategoriaView getCategoria();

    interface CategoriaView {
      String getDescripcion();
    }
  }

  interface CompraView {
    LocalDateTime getFecha();
    String getMedioPago();
  }

}
