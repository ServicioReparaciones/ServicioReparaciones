/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.BiOrden;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Ibi {

    public Integer generatedCodigo();

    public Boolean insert(BiOrden biOrden);

    public Boolean update(BiOrden biOrden);

    public BiOrden findByCodigo(BiOrden biOrden);

    public BiOrden findByBarcode(BiOrden biOrden);

    public List<BiOrden> list(Integer flag);

    public List<BiOrden> lazy(int first, int pageSize, Integer flag);

    public List<BiOrden> filterd(String filterProperty, String filterValue, Integer flag);

    public Integer count();

    public Integer count(Integer flag);
}
