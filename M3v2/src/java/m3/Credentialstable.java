/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 123
 */
@Entity
@Table(name = "CREDENTIALSTABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credentialstable.findAll", query = "SELECT c FROM Credentialstable c")
    , @NamedQuery(name = "Credentialstable.findByCredentialsId", query = "SELECT c FROM Credentialstable c WHERE c.credentialsId = :credentialsId")
    , @NamedQuery(name = "Credentialstable.findByUsername", query = "SELECT c FROM Credentialstable c WHERE c.username = :username")
    , @NamedQuery(name = "Credentialstable.findByPassword", query = "SELECT c FROM Credentialstable c WHERE c.password = :password")
    , @NamedQuery(name = "Credentialstable.findBySignDate", query = "SELECT c FROM Credentialstable c WHERE c.signDate = :signDate")
    , @NamedQuery(name = "Credentialstable.findByUserId", query = "SELECT c FROM Credentialstable c WHERE c.userId.userId = :userId")
    , @NamedQuery(name = "Credentialstable.findMaxCreId", query = "SELECT c FROM Credentialstable c WHERE c.credentialsId = (SELECT MAX(c1.credentialsId) FROM Credentialstable c1)")})
public class Credentialstable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CREDENTIALS_ID")
    private Integer credentialsId;
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "SIGN_DATE")
    @Temporal(TemporalType.DATE)
    private Date signDate;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
            CascadeType.REFRESH})
    private Usertable userId;

    public Credentialstable() {
    }

    public Credentialstable(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }

    public Integer getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Usertable getUserId() {
        return userId;
    }

    public void setUserId(Usertable userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (credentialsId != null ? credentialsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credentialstable)) {
            return false;
        }
        Credentialstable other = (Credentialstable) object;
        if ((this.credentialsId == null && other.credentialsId != null) || (this.credentialsId != null && !this.credentialsId.equals(other.credentialsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "m3.Credentialstable[ credentialsId=" + credentialsId + " ]";
    }
    
}
