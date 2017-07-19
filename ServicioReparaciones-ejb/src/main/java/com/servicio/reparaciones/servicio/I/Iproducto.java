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

    public Boolean insert(Producto repuesto);

    public Boolean update(Producto repuesto);

    public Producto findByCodigo(Producto repuesto);

    public void delete(Producto repuesto);

    public Boolean deleteFlag(Producto repuesto);

    public List<Producto> ObtenerListaProductos(Integer flag);
}
