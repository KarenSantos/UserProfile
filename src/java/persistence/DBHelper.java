/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author karensantos
 */
public class DBHelper {
    public static UserProfile findUser(EntityManager em,String id) {
        UserProfile u = em.find(UserProfile.class, id);
        return u;
    }
    
    public static List findUsersByEmail(EntityManager em, String email) {
        Query query = em.createQuery(
                "SELECT u FROM UserProfile u" +
                " WHERE u.emailId = :emailId");
        query.setParameter("emailId",email);
        return performQuery(query);
    }
    
    private static List performQuery(final Query query) {
        List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } 
        List<UserProfile> results = new ArrayList<>();
        results.addAll(resultList);
        return results;
    }

   public static boolean addUserProfile(EntityManager em, UserTransaction utx, UserProfile userProfile) {
        try {
            utx.begin();
            em.persist(userProfile);
            utx.commit();
            return true;
        } catch (IllegalArgumentException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
