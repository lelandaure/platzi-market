package com.platzi.market.persistence.crud;

import com.platzi.market.domain.ReferidoResponseItem;
import com.platzi.market.persistence.entity.ComprasProducto;
import com.platzi.market.persistence.entity.ComprasProductoPK;
import com.platzi.market.persistence.entity.projection.ComprasProductoProjection;
import com.platzi.market.persistence.entity.projection.ReferidoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComprasProductoCrudRepository extends CrudRepository<ComprasProducto, ComprasProductoPK> {

    @Query(value = "select p.nombre, p.precio_venta, p.cantidad_stock, c.fecha, c.medio_pago, cp.estado, " +
            "cat.descripcion from productos p join compras_productos cp on p.id_producto = cp.id_producto " +
            "join compras c on cp.id_compra = c.id_compra join clientes cli on c.id_cliente = cli.id " +
            "join categorias cat on p.id_categoria = cat.id_categoria where c.id_cliente = ?",
            nativeQuery = true)
    Object[] getReferidoByClienteId(String clienteId);

    List<ComprasProducto> findById_IdCompraAndId_IdProducto(Integer id_idCompra, Integer id_idProducto);

    List<ComprasProducto> findById_IdCompra(Integer id_idCompra);

    List<ComprasProducto> findById_IdProducto(Integer id_idProducto);
    //projection
    List<ComprasProductoProjection> findProjectionById_IdCompra(Integer id_idCompra);
    //jpql
    //List

    @Query(value = "select p.nombre as nombre, " +
        "p.precio_venta as precioVenta, " +
        "p.cantidad_stock as cantidadStock, " +
        "c.fecha as fecha, " +
        "c.medio_pago as medioPago, " +
        "cp.estado as estado, " +
        "cat.descripcion as descripcion " +
        "from productos p join compras_productos cp on p.id_producto = cp.id_producto " +
        "join compras c on cp.id_compra = c.id_compra join clientes cli on c.id_cliente = cli.id " +
        "join categorias cat on p.id_categoria = cat.id_categoria where cli.id = ?1",
        nativeQuery = true)
    List<ReferidoProjection> getReferidosProjection(String clienteId);
}
