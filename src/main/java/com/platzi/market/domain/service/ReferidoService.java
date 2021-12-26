package com.platzi.market.domain.service;

import com.platzi.market.domain.ReferidoResponseItem;
import com.platzi.market.persistence.crud.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReferidoService {
  @Autowired
  private ComprasProductoCrudRepository comprasProductoCrudRepository;

  @Autowired
  private ClienteCrudRepository clienteCrudRepository;

  @Autowired
  private ProductoCrudRepository productoCrudRepository;

  @Autowired
  private CategoriaCrudRepository categoriaCrudRepository;

  @Autowired
  private CompraCrudRepository compraCrudRepository;


  public List<ReferidoResponseItem> getAllReferidoByName(String idCliente) {
    var object = comprasProductoCrudRepository.getReferidoByClienteId(idCliente);

    var list = Arrays.asList(object);

    return  list.stream().map(item -> ReferidoResponseItem.builder()
        .nombre(List.of(item).get(0).toString())
        .precioVenta((BigDecimal) Arrays.asList((Object[]) item).get(1))
        .cantidadStock((Integer) Arrays.asList((Object[]) item).get(2))
        .fecha((Timestamp) Arrays.asList((Object[]) item).get(3))
        .medioPago(Arrays.asList((Object[]) item).get(4).toString())
        .estado((Boolean) Arrays.asList((Object[]) item).get(5))
        .descripcion(Arrays.asList((Object[]) item).get(6).toString())
        .build()).collect(Collectors.toList());


  }

  public Optional<List<ReferidoResponseItem>> getAllBestPractice(String idCliente) {

    return compraCrudRepository.findByIdCliente(idCliente)
        .map(compraList -> compraList.stream()
            .flatMap(compra -> comprasProductoCrudRepository
                .findById_IdCompra(compra.getIdCompra())
                .stream()
                .flatMap(comprasProducto -> productoCrudRepository
                    .findById(comprasProducto.getId().getIdProducto())
                    .stream()
                    .flatMap(producto -> categoriaCrudRepository
                        .findById(producto.getIdCategoria())
                        .stream()
                        .map(categoria -> ReferidoResponseItem.builder()
                            .nombre(producto.getNombre())
                            .precioVenta(BigDecimal.valueOf(producto.getPrecioVenta()))
                            .cantidadStock(producto.getCantidadStock())
                            .fecha(Timestamp.valueOf(compra.getFecha()))
                            .medioPago(compra.getMedioPago())
                            .estado(comprasProducto.getEstado())
                            .descripcion(categoria.getDescripcion())
                            .build()
                        ))))
            .collect(Collectors.toList()));

  }

  public void getWithProjection(String idCliente) {

  }

  public Optional<List<ReferidoResponseItem>> getReferidosProjection(String clienteId) {
    return Optional.of(comprasProductoCrudRepository.getReferidosProjection(clienteId))
        .map(referidoProjections -> referidoProjections.stream()
            .map(referidoProjection -> ReferidoResponseItem.builder()
                .nombre(referidoProjection.getNombre())
                .precioVenta(BigDecimal.valueOf(referidoProjection.getPrecioVenta()))
                .cantidadStock(referidoProjection.getCantidadStock())
                .fecha(referidoProjection.getFecha())
                .medioPago(referidoProjection.getMedioPago())
                .estado(referidoProjection.getEstado())
                .descripcion(referidoProjection.getDescripcion())
                .build()).collect(Collectors.toList()));

    /*comprasProductoCrudRepository.getReferidosProjection(clienteId)
        .stream()
            .map(referidoProjection -> ReferidoResponseItem.builder()
                .nombre(referidoProjection.getNombre())
                .precioVenta(BigDecimal.valueOf(referidoProjection.getPrecioVenta()))
                .cantidadStock(referidoProjection.getCantidadStock())
                .fecha(referidoProjection.getFecha())
                .medioPago(referidoProjection.getMedioPago())
                .estado(referidoProjection.getEstado())
                .descripcion(referidoProjection.getDescripcion())
                .build()).collect(Collectors.toList())
        ;*/
  }

}