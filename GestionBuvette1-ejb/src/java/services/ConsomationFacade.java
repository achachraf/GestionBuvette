/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Consomation;
import java.util.ArrayList;
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
public class ConsomationFacade extends AbstractFacade<Consomation> {

    @PersistenceContext(unitName = "GestionBuvette1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsomationFacade() {
        super(Consomation.class);
    }
    
    public List<Consomation> findNonPaidConsomationsByClientId(Integer idClient) throws Exception{
        TypedQuery<Consomation> query = em.createQuery(
                "SELECT c FROM Consomation c where c.client.idClient = :idClient and c.datePayement IS NULL"
                ,Consomation.class);
        query.setParameter("idClient", idClient);
        List<Consomation> consomations = query.getResultList();
        return consomations;
    }
    
    public List<Consomation> findLimitedConsomationsByClientId(Integer idClient,int limit) throws Exception{
        TypedQuery<Consomation> query = em.createQuery(
                "SELECT c FROM Consomation c where c.client.idClient = :idClient ORDER BY c.dateCreation DESC"
                ,Consomation.class).setMaxResults(limit);
        query.setParameter("idClient", idClient);
        List<Consomation> consomations = query.getResultList();
        return consomations;
    }
    
//   public Consomation getRanConsomation(){
//       Consomation consomation = new Consomation();
//       consomation.setMontant(30);
//   }
//    
//   public List<Consomation> getRandomConsomations(){
//       List<Consomation> consomations = new ArrayList<>();
//       consomations.add(new Consomation())
//   }
    
}
