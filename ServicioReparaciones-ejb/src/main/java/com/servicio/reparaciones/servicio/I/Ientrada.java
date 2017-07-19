/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.Entrada;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Ientrada {

    public Integer generatedCodigo();

    public Boolean insert(Entrada entrada);

    public Boolean update(Entrada entrada);

    public Entrada findByCodigo(Entrada entrada);

    public void delete(Entrada entrada);

    public Boolean deleteFlag(Entrada entrada);

    public List<Entrada> ObtenerListaEntradas(Integer flag);
}
