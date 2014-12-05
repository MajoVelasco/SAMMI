/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.sami.jsf.controllers;

import co.edu.sena.sami.jpa.sessions.EmailSessionBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.naming.NamingException;

/**
 *
 * @author leoandresm
 */
@RequestScoped
@Named
public class EmailController {

    private String body;
    private String subject;
    private String to;
    @EJB
    EmailSessionBean emailBean;

    /**
     * Creates a new instance of EmailController
     */
    public EmailController() {
    }
    
    public String sendMail (){
        try {
            emailBean.sendMail(to, subject, body);
            addSuccessMessage("Enviar Mensaje", "Mensaje Enviado Exitosamente");
            limpiar();
            return "confirmacion";
        } catch (NamingException | MessagingException ex) {
            addErrorMessage("Error sending message " + ex.getClass().getName(), "Message: " + ex.getMessage());
            return null;
        }
        
    }

    private void limpiar() {
        setBody("");
        setSubject("");
        setTo("");
    }

    /**
     * Get the value of body
     *
     * @return the value of body
     */
    public String getBody() {
        return body;
    }

    /**
     * Set the value of body
     *
     * @param body new value of body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Get the value of subject
     *
     * @return the value of subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the value of subject
     *
     * @param subject new value of subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get the value of to
     *
     * @return the value of to
     */
    public String getTo() {
        return to;
    }

    /**
     * Set the value of to
     *
     * @param to new value of to
     */
    public void setTo(String to) {
        this.to = to;
    }

    private void addErrorMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    private void addSuccessMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
}
