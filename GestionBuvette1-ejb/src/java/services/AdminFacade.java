/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Admin;
import entities.Client;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Zbakh
 */
@Stateless
public class AdminFacade extends AbstractFacade<Admin> {
    @PersistenceContext(unitName = "GestionBuvette1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminFacade() {
        super(Admin.class);
    }
    
    
    public Admin authAdmin(Admin admin){
        String querString = "SELECT c FROM Admin c where "
                        + "c.username = :username and c.password = :password";
        TypedQuery<Admin> query = em.createQuery(querString,Admin.class);
        query.setParameter("username",admin.getUsername());
        query.setParameter("password", admin.getPassword());
        try{
            Admin res = query.getSingleResult();    
            return res;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
