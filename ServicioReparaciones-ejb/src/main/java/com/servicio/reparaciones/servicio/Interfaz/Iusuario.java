/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.servicio.Interfaz;

import com.servicio.reparaciones.modelo.nosql.Usuario;
import java.util.List;

/**
 *
 * @author luis
 */
public interface Iusuario {

    public Integer generatedCodigo();

    public Boolean insert(Usuario usuario);

    public Boolean update(Usuario usuario);

    public Usuario findByCodigo(Usuario usuario);

    public void delete(Usuario usuario);

    public Boolean deleteFlag(Usuario usuario);

    public List<Usuario> ObtenerListaUsuarios(Integer flag);
}
