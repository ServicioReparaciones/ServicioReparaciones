
package com.common.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {

    /**
     * Define el maximo numero de registros de consulta.
     */
    static Integer MAX_RESULTS = 100;
    
    /**
     * Busca un objeto de acuerdo a su clave primaria.
     *
     * @param id Objeto que contiene el valor de la clave primaria para realizar
     * la búsqueda.
     * @param lock TRUE si desea bloquear el objeto encontrado.
     * @return Objeto encontrado correspondiente con la clave primaria enviada
     * como parámetro, nulo en caso de que no se haya encontrado ningún objeto.
     */
    T findById(ID id, boolean lock);

    /**
     * Ejecuta una búsqueda retornando todos los registros de una tabla. SOLO se
     * puede utilizar para las entidades cuyo clave primaria sea compuesta y la
     * misma contenga el atributo 'numLicencia'. Es recomendable que en las
     * Clases DAO que heredan de esta se sobreescriba el método para las
     * entidades en las cuales se quiere prevenir la ejecución del mismo, la
     * sobreescritura deberá lanzar por defecto UnsoportedOperationException.
     * @return Retorna el listado de todos los registros de una tabla.
     */
    List<T> findAll();

    /**
     * Ejecuta una búsqueda en base a los criterios especificados por los
     * valores asignados a las propiedades de entityExample.
     *
     * @param entityExample Entidad que contiene los valores para la aplicación
     * de los criterios de búsqueda.
     * @param maxRegistrosConsulta Verdadero si se desea limitar la consulta con
     * la regla del sistema 'MAXIMO_REGISTROS_CONSULTA'.
     *
     * @return Retorna una lista con los objetos encontrados al ejecutar la
     * búsqueda.
     */
    List<T> find(T entityExample, Boolean... maxRegistrosConsulta);

    /**
     * Retorna el número total de registros que retornará la ejecución de una
     * búsqueda en base a los criterios especificados.
     *
     * @param entityExample Entidad que contiene los valores para la aplicación
     * de los criterios de búsqueda.
     * @return Número total de registros que devolverá la ejecución de la
     * búsqueda.
     */
    Integer count(T entityExample);

/**
     * Ejecuta la operación INSERT en el repositorio de datos.
     *
     * @param entity Objeto que contiene los valores con los cuales se va a
     * crear el nuevo registro en el repositorio de datos.
     * 
     */
    void insert(T entity);

    /**
     * Ejecuta la operación UPDATE en el repositorio de datos.
     *
     * @param entity Objeto que contiene los valores con los cuales se va a
     * actualizar el registro que se encuentra en el repositorio de datos.
     * 
     * @return Objeto con los nuevos valores que constan en el repositorio de
     * datos.
     */
    T update(T entity);

    /**
     * Ejecuta la operación DELETE en el repositorio de datos.
     *
     * @param entity Objeto que contiene la clave primaria del registro que va a
     * ser eliminado del repositorio de datos.
     */
    void makeTransient(T entity);

    /**
     * Ejecuta la operación DELETE en el repositorio de datos.
     *
     * @param entity Objeto que contiene la clave primaria del registro que va a
     * ser eliminado del repositorio de datos.
     */
    void remove(T entity);

    /**
     * Ejecutar sentencia nativa.
     *
     * @param sentencia la sentencia
     */
    void ejecutarNativo(String sentencia);

  
    /**
     * Refresca el estado de la entidad con su contenido en la base de datos.
     *
     * @param entidad el objeto a refrescar
     */
    void refresh(T entidad);

    /**
     * Refresca el estado de la entidad en la base de datos.
     */
    void flush();
    
}