/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.Interfaz;

import com.servicio.reparaciones.modelo.nosql.Articulo;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Iarticulo {

    public Integer generatedCodigo();

    public Boolean insert(Articulo articulo);

    public Boolean update(Articulo articulo);

    public Articulo findByCodigo(Articulo articulo);

    public void delete(Articulo articulo);

    public Boolean deleteFlag(Articulo articulo);

    public List<Articulo> ObtenerListaArticulos(Integer flag);
}
