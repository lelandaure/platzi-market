package com.platzi.market.web.controller;

import com.platzi.market.domain.ReferidoResponseItem;
import com.platzi.market.domain.service.ReferidoService;
import com.platzi.market.persistence.crud.ComprasProductoCrudRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/referido")
public class ReferidoController {
  @Autowired
  private ReferidoService referidoService;

  @Autowired
  private ProductoCrudRepository productoCrudRepository;

  @Autowired
  private ComprasProductoCrudRepository comprasProductoCrudRepository;

  @GetMapping("/{id}")
  @ApiOperation("Get all supermarket products")
  @ApiResponse(code = 200, message = "OK")
  public ResponseEntity<List<ReferidoResponseItem>> getAll(@PathVariable String id) {
    log.info("entrando al metodo referido con {}", id);
    return new ResponseEntity<>(referidoService.getAllReferidoByName(id), HttpStatus.OK);
  }

  @GetMapping("/referido/{id}")
  @ApiOperation("Get all supermarket products")
  @ApiResponse(code = 200, message = "OK")
  public ResponseEntity<List<ReferidoResponseItem>> getAllBestPractice(@PathVariable String id) {
    log.info("entrando al metodo referido con {}", id);
    return referidoService.getAllBestPractice(id)
        .map(referidoResponseItems -> new ResponseEntity<>(referidoResponseItems, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/projectionss/{id}")
  @ApiOperation("Get all supermarket products")
  @ApiResponse(code = 200, message = "OK")
  public List<ReferidoResponseItem> getProjectionss(@PathVariable Integer id) {
    log.info("entrando al metodo referido con {}", id);
    return comprasProductoCrudRepository.findProjectionById_IdCompra(id).stream()
        .map(comprasProductoProjection -> ReferidoResponseItem.builder()
            .nombre(comprasProductoProjection.getProducto().getNombre())
            .precioVenta(BigDecimal.valueOf(comprasProductoProjection.getProducto().getPrecioVenta()))
            .cantidadStock(comprasProductoProjection.getProducto().getCantidadStock())
            .fecha(Timestamp.valueOf(comprasProductoProjection.getCompra().getFecha()))
            .medioPago(comprasProductoProjection.getCompra().getMedioPago())
            .estado(comprasProductoProjection.getEstado())
            .descripcion(comprasProductoProjection.getProducto().getCategoria().getDescripcion())
            .build())
        .collect(Collectors.toList());
  }

  @GetMapping("/projectionsss/{id}")
  @ApiOperation("Get all supermarket products")
  @ApiResponse(code = 200, message = "OK")
  public List<ReferidoResponseItem> getProjectionsss(@PathVariable Integer id) {
    log.info("entrando al metodo referido con {}", id);
    return comprasProductoCrudRepository.findProjectionById_IdCompra(id).stream()
        .map(comprasProductoProjection -> ReferidoResponseItem.builder()
            .nombre(comprasProductoProjection.getProducto().getNombre())
            .precioVenta(BigDecimal.valueOf(comprasProductoProjection.getProducto().getPrecioVenta()))
            .cantidadStock(comprasProductoProjection.getProducto().getCantidadStock())
            .fecha(Timestamp.valueOf(comprasProductoProjection.getCompra().getFecha()))
            .medioPago(comprasProductoProjection.getCompra().getMedioPago())
            .estado(comprasProductoProjection.getEstado())
            .descripcion(comprasProductoProjection.getProducto().getCategoria().getDescripcion())
            .build())
        .collect(Collectors.toList());
  }
  @GetMapping("/pruebaProjection/{clientId}")
  public Optional<List<ReferidoResponseItem>> getReferidosProjection(@PathVariable String clientId) {
    return referidoService.getReferidosProjection(clientId);
  }

}
