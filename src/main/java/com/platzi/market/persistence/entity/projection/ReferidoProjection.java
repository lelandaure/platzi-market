package com.platzi.market.persistence.entity.projection;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface ReferidoProjection {
  String getNombre();
  Double getPrecioVenta();
  Integer getCantidadStock();
  Timestamp getFecha();
  String getMedioPago();
  Boolean getEstado();
  String getDescripcion();
}
