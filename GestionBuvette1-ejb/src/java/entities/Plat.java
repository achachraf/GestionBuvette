/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author achra
 */
@Entity
@Table(name = "plat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plat.findAll", query = "SELECT p FROM Plat p")
    , @NamedQuery(name = "Plat.findByIdPlat", query = "SELECT p FROM Plat p WHERE p.idPlat = :idPlat")
    , @NamedQuery(name = "Plat.findByNomPlat", query = "SELECT p FROM Plat p WHERE p.nomPlat = :nomPlat")
    , @NamedQuery(name = "Plat.findByPrix", query = "SELECT p FROM Plat p WHERE p.prix = :prix")
    , @NamedQuery(name = "Plat.findByValable", query = "SELECT p FROM Plat p WHERE p.valable = :valable")
    , @NamedQuery(name = "Plat.findByImage", query = "SELECT p FROM Plat p WHERE p.image = :image")})
public class Plat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nom_plat")
    private String nomPlat;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRIX")
    private Float prix;
    @Column(name = "VALABLE")
    private Integer valable;
    @Size(max = 500)
    @Column(name = "image")
    private String image;
    @JoinColumn(name = "ID_CAT", referencedColumnName = "ID_CAT")
    @ManyToOne(optional = false)
    private Category idCat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPlat")
    private List<Commande> commandeList;

    public Plat() {
    }

    public Plat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Plat(Integer idPlat, String nomPlat) {
        this.idPlat = idPlat;
        this.nomPlat = nomPlat;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Integer getValable() {
        return valable;
    }

    public void setValable(Integer valable) {
        this.valable = valable;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getIdCat() {
        return idCat;
    }

    public void setIdCat(Category idCat) {
        this.idCat = idCat;
    }

    @XmlTransient
    public List<Commande> getCommandeList() {
        return commandeList;
    }

    public void setCommandeList(List<Commande> commandeList) {
        this.commandeList = commandeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlat != null ? idPlat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plat)) {
            return false;
        }
        Plat other = (Plat) object;
        if ((this.idPlat == null && other.idPlat != null) || (this.idPlat != null && !this.idPlat.equals(other.idPlat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Plat[ idPlat=" + idPlat + " + nomPlat= "+nomPlat+ "]";
    }
    
}
