/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.Interfaz;

import com.servicio.reparaciones.modelo.nosql.Repuesto;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Irepuesto {

    public Integer generatedCodigo();

    public Boolean insert(Repuesto repuesto);

    public Boolean update(Repuesto repuesto);

    public Repuesto findByCodigo(Repuesto repuesto);

    public void delete(Repuesto repuesto);

    public Boolean deleteFlag(Repuesto repuesto);

    public List<Repuesto> ObtenerListaRepuestos(Integer flag);
}
