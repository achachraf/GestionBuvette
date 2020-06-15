/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author achra
 */
@Entity
@Table(name = "commande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commande.findAll", query = "SELECT c FROM Commande c")
    , @NamedQuery(name = "Commande.findByIdComd", query = "SELECT c FROM Commande c WHERE c.idComd = :idComd")
    , @NamedQuery(name = "Commande.findByQuantite", query = "SELECT c FROM Commande c WHERE c.quantite = :quantite")})
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMD")
    private Integer idComd;
    @Column(name = "QUANTITE")
    private Integer quantite = 1;
    @JoinColumn(name = "ID_PLAT", referencedColumnName = "ID_PLAT")
    @ManyToOne(optional = false)
    private Plat idPlat;
    @JoinColumn(name = "ID_CONSOM", referencedColumnName = "ID_CONSOM")
    @ManyToOne(optional = false)
    private Consomation idConsom;

    public Commande() {
    }

    public Commande(Integer idComd) {
        this.idComd = idComd;
    }

    public Integer getIdComd() {
        return idComd;
    }

    public void setIdComd(Integer idComd) {
        this.idComd = idComd;
    }

    public String getQuantite() {
        return quantite.toString();
    }

    public void setQuantite(String quantite) {
        this.quantite = Integer.parseInt(quantite);
    }
    
    public Integer getIntQuantite(){
        return quantite;
    }

    public Plat getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Plat idPlat) {
        this.idPlat = idPlat;
    }

    public Consomation getIdConsom() {
        return idConsom;
    }

    public void setIdConsom(Consomation idConsom) {
        this.idConsom = idConsom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComd != null ? idComd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        if ((this.idComd == null && other.idComd != null) || (this.idComd != null && !this.idComd.equals(other.idComd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Commande{" + "idComd=" + idComd + ", quantite=" + quantite + ", idPlat=" + idPlat + ", idConsom=" + idConsom + '}';
    }

   
    
}
