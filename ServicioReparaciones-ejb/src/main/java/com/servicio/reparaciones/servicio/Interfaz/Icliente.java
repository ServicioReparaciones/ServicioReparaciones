/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.Interfaz;

import com.servicio.reparaciones.modelo.nosql.Cliente;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Icliente {

    public Integer generatedCodigo();

    public Boolean insert(Cliente cliente);

    public Boolean update(Cliente cliente);
    
    public Cliente findByCodigo(Cliente cliente);

    public void delete(Cliente cliente);

    public Boolean deleteFlag(Cliente cliente);

    public List<Cliente> ObtenerListaClientes(Integer flag);
}
