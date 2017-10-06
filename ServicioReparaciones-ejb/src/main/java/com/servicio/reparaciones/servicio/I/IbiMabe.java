/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.I;

import com.servicio.reparaciones.modelo.nosql.BiMabe;
import java.util.List;

/**
 *
 * @author luis
 */
public interface IbiMabe {

    public Integer generatedCodigo();

    public Boolean insert(BiMabe biMabe);

    public BiMabe findByCodigo(BiMabe biMabe);

    public List<BiMabe> list(Integer flag);

    public List<BiMabe> lazy(int first, int pageSize, Integer flag);

    public List<BiMabe> filterd(String filterProperty, String filterValue, Integer flag);

    public Integer count();

    public Integer count(Integer flag);
}
