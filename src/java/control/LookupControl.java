/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import beans.UserProfileBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.DBHelper;
import persistence.UserProfile;

/**
 *
 * @author karensantos
 */
@Named(value = "userControl")
@RequestScoped
public class LookupControl implements Serializable {

    @Inject
    private UserProfileBean userProfileBean;
    @PersistenceContext
    EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    /**
     * Creates a new instance of UserControl
     */
    public LookupControl() {
    }

    public void lookup() {
        List<UserProfile> results = new ArrayList();
        if (!"".equals(userProfileBean.getEmailId())) {
            // lookup by id
            results = getUserByEmailId(em, userProfileBean);
        }
        userProfileBean.setLookupResults(results);
    }

    /**
     * Find a user by id and check if any that the other fields are valid
     */
    private List<UserProfile> getUserByEmailId(EntityManager em, UserProfileBean userProfileBean) {
        List<UserProfile> result = new ArrayList<>();
        UserProfile user = DBHelper.findUser(em, userProfileBean.getEmailId());
        if (user != null && user.matches(userProfileBean)) {
            result.add(user);
        }
        return result;
    }

}
