/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.Producto;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Iproducto {

    public Integer generatedCodigo();

    public Boolean insert(Producto producto);

    public Boolean update(Producto producto);

    public Producto findByCodigo(Producto producto);

    public void delete(Producto producto);

    public Boolean deleteFlag(Producto producto);

    public List<Producto> ObtenerListaProductos(Integer flag);

    public List<Producto> lazy(int first, int pageSize, Integer flag);

    public List<Producto> filterd(String filterProperty, String filterValue,Integer flag);

    public Integer count();

    public Integer count(Integer flag);
}
