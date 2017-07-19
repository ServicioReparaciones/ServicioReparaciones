/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.Salida;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Isalida {

    public Integer generatedCodigo();

    public Boolean insert(Salida salida);

    public Boolean update(Salida salida);

    public Salida findByCodigo(Salida salida);

    public void delete(Salida salida);

    public Boolean deleteFlag(Salida salida);

    public List<Salida> ObtenerListaSalidas(Integer flag);
}
