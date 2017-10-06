/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.BiMabeRepuesto;
import java.util.List;

/**
 *
 * @author luis
 */
public interface IbiMabeRepuesto {

    public Integer generatedCodigo();

    public Boolean insert(BiMabeRepuesto biMabeRepuesto);

    public BiMabeRepuesto findByCodigo(BiMabeRepuesto biMabeRepuesto);

    public List<BiMabeRepuesto> list(Integer flag);

    public List<BiMabeRepuesto> lazy(int first, int pageSize, Integer flag);

    public List<BiMabeRepuesto> filterd(String filterProperty, String filterValue, Integer flag);

    public Integer count();

    public Integer count(Integer flag);
}
