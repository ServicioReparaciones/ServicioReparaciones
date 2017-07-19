/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicio.reparaciones.web.bean;

import com.servicio.reparaciones.modelo.nosql.Servicio;
import com.servicio.reparaciones.modelo.nosql.Tecnico;
import com.servicio.reparaciones.modelo.nosql.Usuario;
import com.servicio.reparaciones.servicio.ArticuloService;
import com.servicio.reparaciones.servicio.BodegaService;
import com.servicio.reparaciones.servicio.EntradaService;
import com.servicio.reparaciones.servicio.SalidaService;
import com.servicio.reparaciones.servicio.ServicioServicio;
import com.servicio.reparaciones.servicio.TecnicoServicio;
import com.servicio.reparaciones.servicio.UsuarioServicio;
import com.servicio.reparaciones.web.util.FacesUtil;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author luis
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -6124049081154351792L;

    private Usuario usuario;

    @Inject
    private CredencialBean session;
    @Inject
    private UsuarioServicio usuarioService;
    @Inject
    private TecnicoServicio tecnicoService;
    @Inject
    private ServicioServicio servicioService;
    @Inject
    private BodegaService bodegaService;
    @Inject
    private ArticuloService articuloService;
    @Inject
    private EntradaService entradaService;
    @Inject
    private SalidaService salidaService;

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
        this.createAdmin();
        this.tecnicoBlank();
        this.servicioBlank();
    }

    public void login(ActionEvent evt) {
        RequestContext context = RequestContext.getCurrentInstance();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        Boolean LoggedIn = Boolean.FALSE;
        if (this.usuarioService.existsUsername(this.usuario)) {
            if (this.usuarioService.stateUsername(this.usuario)) {
                if (this.usuarioService.checkPassword(this.usuario)) {
                    Usuario loginUser = this.usuarioService.findByUsername(this.usuario);
                    this.session.startSession(loginUser);
                    url = url + "/faces/views/principal.xhtml";
                    LoggedIn = Boolean.TRUE;
                    this.init();
                } else {
                    FacesUtil.addMessageError(null, "Username y/o contrseña incorreoctos.");
                }
            } else {
                FacesUtil.addMessageInfo("El usuario no esta habilitado porfavor comuniquese con el administrador.");
            }
        } else {
            FacesUtil.addMessageError(null, "El usuario no se encuentra registrado.");
        }
        context.addCallbackParam("loggedIn", LoggedIn);
        context.addCallbackParam("ruta", url);
    }

    private void createAdmin() {
        Usuario admin = new Usuario();
        admin.setEstado(Boolean.TRUE);
        admin.setUsername("admin.servi.2017");
        admin.setPassword("admin.servi.2017");
        admin.setAdmin(Boolean.TRUE);
        admin = this.usuarioService.findByUsername(admin);
        if (admin.getCodigo() == null) {
            Usuario adminServi = new Usuario();
            adminServi.setEstado(Boolean.TRUE);
            adminServi.setAdmin(Boolean.TRUE);
            adminServi.setUsername("admin.servi.2017");
            adminServi.setPassword("admin.servi.2017");
            adminServi.getDatosPersonales().setApellidos("admin");
            adminServi.getDatosPersonales().setNombres("admin");
            adminServi.getDatosPersonales().setCedula("1111111111");
            adminServi.getDatosPersonales().setMovil("9999999999");
            adminServi.getDatosPersonales().setEmail("admin@info.com");
            adminServi.getDatosPersonales().setFechaNacimiento(new Date());
            Usuario mUsername = this.usuarioService.findByUsername(adminServi);
            if (mUsername.getId() == null) {
                this.usuarioService.insert(adminServi);
            }
        }
    }

    private void tecnicoBlank() {
        Tecnico blank = new Tecnico();
        blank.getDatosPersonales().setApellidos("admin");
        blank.getDatosPersonales().setNombres("admin");
        blank.getDatosPersonales().setCedula("1111111111");
        blank.getDatosPersonales().setMovil("9999999999");
        blank.getDatosPersonales().setEmail("admin@info.com");
        blank.getDatosPersonales().setFechaNacimiento(new Date());
        blank.setCargo("blank");
        Usuario admin = new Usuario();
        admin.setEstado(Boolean.TRUE);
        admin.setUsername("admin.servi.2017");
        admin.setPassword("admin.servi.2017");
        admin.setAdmin(Boolean.TRUE);
        admin = this.usuarioService.findByUsername(admin);
        blank.setUsername(admin);
        Tecnico mblank = this.tecnicoService.findByCargo(blank);
        if (mblank.getCodigo() == null) {
            this.tecnicoService.insert(blank);
        }
    }

    private void servicioBlank() {
        Servicio blank = new Servicio();
        blank.setDescripcion("blank");
        blank.setArtefacto("blank");
        blank.setMarca("blank");
        blank.setPrecio(0d);
        Usuario admin = new Usuario();
        admin.setEstado(Boolean.TRUE);
        admin.setUsername("admin.servi.2017");
        admin.setPassword("admin.servi.2017");
        admin.setAdmin(Boolean.TRUE);
        admin = this.usuarioService.findByUsername(admin);
        blank.setUsername(admin);
        Servicio mblank = this.servicioService.findByDescripcion(blank);
        if (mblank.getCodigo() == null) {
            this.servicioService.insert(blank);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
