/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author achra
 */
@Entity
@Table(name = "consomation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consomation.findAll", query = "SELECT c FROM Consomation c")
    , @NamedQuery(name = "Consomation.findByIdConsom", query = "SELECT c FROM Consomation c WHERE c.idConsom = :idConsom")
    , @NamedQuery(name = "Consomation.findByDateCreation", query = "SELECT c FROM Consomation c WHERE c.dateCreation = :dateCreation")
    , @NamedQuery(name = "Consomation.findByMontant", query = "SELECT c FROM Consomation c WHERE c.montant = :montant")
    , @NamedQuery(name = "Consomation.findByDatePayement", query = "SELECT c FROM Consomation c WHERE c.datePayement = :datePayement")
    , @NamedQuery(name = "Consomation.findByDatePrepa", query = "SELECT c FROM Consomation c WHERE c.datePrepa = :datePrepa")})
public class Consomation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CONSOM")
    private Integer idConsom;
    @Column(name = "DATE_CREATION",insertable = false, updatable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTANT")
    private Float montant;
    @Column(name = "DATE_PAYEMENT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePayement;
    @Column(name = "DATE_PREPA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePrepa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConsom")
    private List<Commande> commandeList;
    @JoinColumn(name = "ID_CLIENT", referencedColumnName = "ID_CLIENT")
    @ManyToOne(optional = false)
    private Client client;
    
    public Consomation() {
    }

    public Consomation(Integer idConsom, Date dateCreation, Float montant) {
        this.idConsom = idConsom;
        this.dateCreation = dateCreation;
        this.montant = montant;
    }
    
    

    public Consomation(Integer idConsom) {
        this.idConsom = idConsom;
    }

    public Integer getIdConsom() {
        return idConsom;
    }

    public void setIdConsom(Integer idConsom) {
        this.idConsom = idConsom;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public Date getDatePayement() {
        return datePayement;
    }

    public void setDatePayement(Date datePayement) {
        this.datePayement = datePayement;
    }
    
    public void setDatePayement() {
        Date date= new Date();
        this.datePayement = date;
    }

    public Date getDatePrepa() {
        return datePrepa;
    }

    public void setDatePrepa(Date datePrepa) {
        this.datePrepa = datePrepa;
    }

    public void setDatePrepa() {
        Date date= new Date();
        this.datePrepa = date;
    }
    
    
    @XmlTransient
    public List<Commande> getCommandeList() {
        return commandeList;
    }

    public void setCommandeList(List<Commande> commandeList) {
        this.commandeList = commandeList;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
   
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConsom != null ? idConsom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consomation)) {
            return false;
        }
        Consomation other = (Consomation) object;
        if ((this.idConsom == null && other.idConsom != null) || (this.idConsom != null && !this.idConsom.equals(other.idConsom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Consomation[ idConsom=" + idConsom + " ]";
    }
    
    
    
}
