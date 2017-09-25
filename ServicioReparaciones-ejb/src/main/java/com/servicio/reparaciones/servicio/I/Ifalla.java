/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.Falla;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Ifalla {

    public Integer generatedCodigo();

    public Boolean insert(Falla falla);

    public Boolean update(Falla falla);

    public Falla findByCodigo(Falla falla);

    public List<Falla> ObtenerListaFallas(Integer flag);

    public Integer count();

    public Integer count(Integer flag);
}
