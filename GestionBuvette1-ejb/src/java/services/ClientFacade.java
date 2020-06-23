/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Client;
import entities.Consomation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author achra
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "GestionBuvette1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }
    
    public Client authClient(Client client){
        String querString = "SELECT c FROM Client c where "
                        + "c.username = :username and c.password = :password";
        TypedQuery<Client> query = em.createQuery(querString,Client.class);
        query.setParameter("username",client.getUsername());
        query.setParameter("password", client.getPassword());
        try{
            Client res = query.getSingleResult();    
            return res;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

   
    public Integer createAndReturn(Client client) {
        super.create(client); 
        em.flush();
        em.refresh(client);
        return client.getIdClient();
    }
    
    

}
