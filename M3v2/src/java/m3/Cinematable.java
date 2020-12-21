/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 123
 */
@Entity
@Table(name = "CINEMATABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cinematable.findAll", query = "SELECT c FROM Cinematable c")
    , @NamedQuery(name = "Cinematable.findByCinemaId", query = "SELECT c FROM Cinematable c WHERE c.cinemaId = :cinemaId")
    , @NamedQuery(name = "Cinematable.findByCinemaname", query = "SELECT c FROM Cinematable c WHERE c.cinemaname = :cinemaname")
    , @NamedQuery(name = "Cinematable.findByCinemaPostcode", query = "SELECT c FROM Cinematable c WHERE c.cinemaPostcode = :cinemaPostcode")})
public class Cinematable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CINEMA_ID")
    private Integer cinemaId;
    @Size(max = 50)
    @Column(name = "CINEMANAME")
    private String cinemaname;
    @Column(name = "CINEMA_POSTCODE")
    private Integer cinemaPostcode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinemaId")
    private Collection<Memoirtable> memoirtableCollection;

    public Cinematable() {
    }

    public Cinematable(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public Integer getCinemaPostcode() {
        return cinemaPostcode;
    }

    public void setCinemaPostcode(Integer cinemaPostcode) {
        this.cinemaPostcode = cinemaPostcode;
    }

    @XmlTransient
    public Collection<Memoirtable> getMemoirtableCollection() {
        return memoirtableCollection;
    }

    public void setMemoirtableCollection(Collection<Memoirtable> memoirtableCollection) {
        this.memoirtableCollection = memoirtableCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cinemaId != null ? cinemaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cinematable)) {
            return false;
        }
        Cinematable other = (Cinematable) object;
        if ((this.cinemaId == null && other.cinemaId != null) || (this.cinemaId != null && !this.cinemaId.equals(other.cinemaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "m3.Cinematable[ cinemaId=" + cinemaId + " ]";
    }
    
}
