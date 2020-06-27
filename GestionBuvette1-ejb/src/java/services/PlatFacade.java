/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Commande;
import entities.Plat;
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
public class PlatFacade extends AbstractFacade<Plat> {

    @PersistenceContext(unitName = "GestionBuvette1-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlatFacade() {
        super(Plat.class);
    }
    
    public List<Plat> getPlatsLimit(int limit) throws Exception{
        String queryString = "SELECT p from Plat p";
        TypedQuery<Plat> query = em.createQuery(queryString, Plat.class)
                .setMaxResults(limit);
        List<Plat> plats = query.getResultList();
        return plats;
     
    }
    
    public List<Plat> getPlatsByCategory(int idCategory){
        TypedQuery<Plat> query = em.createQuery("SELECT p from Plat p WHERE p.idCat.idCat = :idCategory",Plat.class);
        return query.getResultList();
        
    }

    @Override
    public void create(Plat entity) {
        super.create(entity);
        em.flush();
        em.refresh(entity);
    }
    
    
    public List<Commande> getTopPlats() throws Exception{
    //    List<Plat> plats = new ArrayList<Plat>();
        String queryString = "SELECT c, SUM(c.quantite) as orders FROM Commande c GROUP BY c.idPlat ORDER BY orders desc";
        TypedQuery<Object[]> query = em.createQuery(queryString, Object[].class).setMaxResults(5);
        List<Object[]> commandes1 = query.getResultList();
        List<Commande> commandes = new ArrayList<Commande>();
        for (Object[] x : commandes1) {
            Commande commande = (Commande)x[0];
            commandes.add(commande);
        }
        return commandes;    
    }
    
    public List<Plat> getAllPlats() throws Exception{
        String queryString = "SELECT c FROM Plat c";
        TypedQuery<Plat> query = em.createQuery(queryString, Plat.class);
        List<Plat> plats = query.getResultList();

        return plats;    
    }
    
}
