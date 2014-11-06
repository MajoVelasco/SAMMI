package co.edu.sena.sami.jsf.controllers;

import co.edu.sena.sami.jpa.entities.TiposDeServiciosSolicitudDeServicios;
import co.edu.sena.sami.jsf.controllers.util.JsfUtil;
import co.edu.sena.sami.jsf.controllers.util.JsfUtil.PersistAction;
import co.edu.sena.sami.jpa.sessions.TiposDeServiciosSolicitudDeServiciosFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("tiposDeServiciosSolicitudDeServiciosController")
@SessionScoped
public class TiposDeServiciosSolicitudDeServiciosController implements Serializable {

    @EJB
    private co.edu.sena.sami.jpa.sessions.TiposDeServiciosSolicitudDeServiciosFacade ejbFacade;
    private List<TiposDeServiciosSolicitudDeServicios> items = null;
    private TiposDeServiciosSolicitudDeServicios selected;

    public TiposDeServiciosSolicitudDeServiciosController() {
    }

    public TiposDeServiciosSolicitudDeServicios getSelected() {
        return selected;
    }

    public void setSelected(TiposDeServiciosSolicitudDeServicios selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TiposDeServiciosSolicitudDeServiciosFacade getFacade() {
        return ejbFacade;
    }

    public TiposDeServiciosSolicitudDeServicios prepareCreate() {
        selected = new TiposDeServiciosSolicitudDeServicios();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/resources/Bundle").getString("TiposDeServiciosSolicitudDeServiciosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/resources/Bundle").getString("TiposDeServiciosSolicitudDeServiciosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/resources/Bundle").getString("TiposDeServiciosSolicitudDeServiciosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TiposDeServiciosSolicitudDeServicios> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public TiposDeServiciosSolicitudDeServicios getTiposDeServiciosSolicitudDeServicios(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<TiposDeServiciosSolicitudDeServicios> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TiposDeServiciosSolicitudDeServicios> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TiposDeServiciosSolicitudDeServicios.class)
    public static class TiposDeServiciosSolicitudDeServiciosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TiposDeServiciosSolicitudDeServiciosController controller = (TiposDeServiciosSolicitudDeServiciosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tiposDeServiciosSolicitudDeServiciosController");
            return controller.getTiposDeServiciosSolicitudDeServicios(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TiposDeServiciosSolicitudDeServicios) {
                TiposDeServiciosSolicitudDeServicios o = (TiposDeServiciosSolicitudDeServicios) object;
                return getStringKey(o.getIdSolicitudServicio());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TiposDeServiciosSolicitudDeServicios.class.getName()});
                return null;
            }
        }

    }

}
