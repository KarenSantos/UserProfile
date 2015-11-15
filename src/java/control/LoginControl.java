/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import beans.UserProfileBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.context.RequestContext;
import persistence.DBHelper;
import persistence.UserProfile;

/**
 *
 * @author karensantos
 */
@Named(value = "loginControl")
@RequestScoped
public class LoginControl {

    private String email;
    private String password;
    private boolean submited;
    private boolean validated;
    @Inject
    private UserProfileBean userProfileBean;
    @PersistenceContext
    EntityManager em;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean getShowMessage() {
        return (submited && !validated);
    }

    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        submited = true;
        UserProfile user = DBHelper.findUser(em, email);

        if (user != null) {
            if (password.equals(user.getPassword())) {
                validated = true;
                loggedIn = true;
                userProfileBean.setEmailId(email);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", email);
            }
        } else {
            validated = false;
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }
}
