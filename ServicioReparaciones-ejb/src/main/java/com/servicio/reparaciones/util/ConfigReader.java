/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luis
 */
public class ConfigReader {

    private static final Logger LOGGER = Logger.getLogger(ConfigReader.class.getName());
    private static final String CONFIG_RESOURCE = "mongodb-cfg.properties";
    private static final String HOST_KEY = "host";
    private static final String PORT_KEY = "port";
    private static final String DATABASE_KEY = "database";
    private static final String SOCKET_TIMEOUT_KEY = "socket-timeout";
    private static final String CONNECTION_TIMEOUT_KEY = "connection-timeout";
    private static final String PACKAGES_KEY = "packages";
    private Properties props = new Properties();

    public ConfigReader() {
        this.loadProperties();
    }

    public ConfigDTO getConfiguration() {
        ConfigDTO config = null;
        if (this.props != null) {
            StringBuilder errors = new StringBuilder();
            try {
                config = new ConfigDTO();
                if (this.props.getProperty("host") != null) {
                    config.setHost(this.props.getProperty("host"));
                } else {
                    errors.append("\nEl archivo de configuracion no tiene valor para la clave: host");
                }
                try {
                    config.setPort(Integer.parseInt(this.props.getProperty("port", "27017")));
                } catch (NumberFormatException e) {
                    errors.append("\nEl valor es inv\u00e1lido para la clave: port");
                }
                if (this.props.getProperty("database") != null) {
                    config.setDatabase(this.props.getProperty("database"));
                } else {
                    errors.append("\nEl archivo de configuracion no tiene valor para la clave: database");
                }
                try {
                    config.setSocketTimeout(Integer.parseInt(this.props.getProperty("socket-timeout", "30000")));
                } catch (NumberFormatException e) {
                    errors.append("\nEl valor es inv\u00e1lido para la clave: socket-timeout");
                }
                try {
                    config.setConnectionTimeout(Integer.parseInt(this.props.getProperty("connection-timeout", "60000")));
                } catch (NumberFormatException e) {
                    errors.append("\nEl valor es inv\u00e1lido para la clave: connection-timeout");
                }
                if (errors.length() > 0) {
                    config = null;
                    throw new RuntimeException("Valores erroneos en la configuracion: " + errors.toString());
                }
                System.err.println("Config:" + config.toString());
                String packagesN = this.props.getProperty("packages");
                if (packagesN != null && packagesN.length() > 1) {
                    config.setPackages(packagesN.split(" "));
                } else {
                    LOGGER.log(Level.WARNING, "No se han definido paquetes con clases de persistencia");
                }
            } catch (RuntimeException e) {
                LOGGER.log(Level.SEVERE, "Error al leer la configuracion de conexion a MongoDB: \n" + e.getMessage(), e);
            }
        }
        System.err.println("Config:" + config);
        return config;
    }

    private void loadProperties() {
        try {
            this.props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mongodb-cfg.properties"));
            LOGGER.info("Se ha cargado el archivo de propiedades.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error en lectura de configuraci\u00f3n.", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
