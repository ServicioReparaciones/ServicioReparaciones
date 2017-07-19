/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.Tecnico;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Itecnico {

    public Integer generatedCodigo();

    public Boolean insert(Tecnico tecnico);

    public Boolean update(Tecnico tecnico);

    public Tecnico findByCodigo(Tecnico tecnico);

    public void delete(Tecnico tecnico);

    public Boolean deleteFlag(Tecnico tecnico);

    public List<Tecnico> ObtenerListaTecnicos(Integer flag);
}
