/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.Interfaz;

import com.servicio.reparaciones.modelo.nosql.Orden;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Iorden {

    public Integer generatedCodigo();

    public Boolean insert(Orden orden);

    public Boolean update(Orden orden);

    public Orden findByCodigo(Orden orden);

    public void delete(Orden orden);

    public Boolean deleteFlag(Orden orden);

    public List<Orden> ObtenerListaOrdens(Integer flag);
}
