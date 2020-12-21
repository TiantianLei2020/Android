/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 123
 */
@Entity
@Table(name = "USERTABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usertable.findAll", query = "SELECT u FROM Usertable u")
    , @NamedQuery(name = "Usertable.findByUserId", query = "SELECT u FROM Usertable u WHERE u.userId = :userId")
    , @NamedQuery(name = "Usertable.findByUserName", query = "SELECT u FROM Usertable u WHERE u.userName = :userName")
    , @NamedQuery(name = "Usertable.findBySurname", query = "SELECT u FROM Usertable u WHERE u.surname = :surname")
    , @NamedQuery(name = "Usertable.findByGender", query = "SELECT u FROM Usertable u WHERE u.gender = :gender")
    , @NamedQuery(name = "Usertable.findByDob", query = "SELECT u FROM Usertable u WHERE u.dob = :dob")
    , @NamedQuery(name = "Usertable.findByStNumber", query = "SELECT u FROM Usertable u WHERE u.stNumber = :stNumber")
    , @NamedQuery(name = "Usertable.findByStName", query = "SELECT u FROM Usertable u WHERE u.stName = :stName")
    , @NamedQuery(name = "Usertable.findByUserState", query = "SELECT u FROM Usertable u WHERE u.userState = :userState")
    , @NamedQuery(name = "Usertable.findByPostode", query = "SELECT u FROM Usertable u WHERE u.postode = :postode")
    , @NamedQuery(name = "Usertable.findAddressByUserId", query = "SELECT u FROM Usertable u WHERE u.userId = :userId")})
public class Usertable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Size(max = 10)
    @Column(name = "USER_NAME")
    private String userName;
    @Size(max = 10)
    @Column(name = "SURNAME")
    private String surname;
    @Size(max = 10)
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "ST_NUMBER")
    private Integer stNumber;
    @Size(max = 20)
    @Column(name = "ST_NAME")
    private String stName;
    @Size(max = 20)
    @Column(name = "USER_STATE")
    private String userState;
    @Column(name = "POSTODE")
    private Integer postode;
    @OneToMany(mappedBy = "userId")
    private Collection<Memoirtable> memoirtableCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Credentialstable> credentialstableCollection;

    public Usertable() {
    }

    public Usertable(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getStNumber() {
        return stNumber;
    }

    public void setStNumber(Integer stNumber) {
        this.stNumber = stNumber;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Integer getPostode() {
        return postode;
    }

    public void setPostode(Integer postode) {
        this.postode = postode;
    }

    @XmlTransient
    public Collection<Memoirtable> getMemoirtableCollection() {
        return memoirtableCollection;
    }

    public void setMemoirtableCollection(Collection<Memoirtable> memoirtableCollection) {
        this.memoirtableCollection = memoirtableCollection;
    }

    @XmlTransient
    public Collection<Credentialstable> getCredentialstableCollection() {
        return credentialstableCollection;
    }

    public void setCredentialstableCollection(Collection<Credentialstable> credentialstableCollection) {
        this.credentialstableCollection = credentialstableCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usertable)) {
            return false;
        }
        Usertable other = (Usertable) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "m3.Usertable[ userId=" + userId + " ]";
    }
    
}
