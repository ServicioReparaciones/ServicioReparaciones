/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.Interfaz;

import com.servicio.reparaciones.modelo.nosql.Bodega;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Ibodega {

    public Integer generatedCodigo();

    public Boolean insert(Bodega bodega);

    public Boolean update(Bodega bodega);

    public Bodega findByCodigo(Bodega bodega);

    public void delete(Bodega bodega);

    public Boolean deleteFlag(Bodega bodega);

    public List<Bodega> ObtenerListaBodegas(Integer flag);
}
