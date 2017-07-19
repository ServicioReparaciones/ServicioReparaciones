/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.Inventario;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Iinventario {
    public Integer generatedCodigo();

    public Boolean insert(Inventario inventario);

    public Boolean update(Inventario inventario);

    public Inventario findByCodigo(Inventario inventario);

    public void delete(Inventario inventario);

    public Boolean deleteFlag(Inventario inventario);

    public List<Inventario> ObtenerListaInventarios(Integer flag);
}
