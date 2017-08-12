/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.Visita;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Ivisita {

    public Integer generatedCodigo();

    public Boolean insert(Visita visita);

    public Boolean update(Visita visita);

    public Visita findByCodigo(Visita visita);

    public void delete(Visita visita);

    public Boolean deleteFlag(Visita visita);

    public List<Visita> ObtenerListaVisitas(Integer flag);

    public Integer count();

    public Integer count(Integer flag);
}
