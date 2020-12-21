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
@Table(name = "MEMOIRTABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoirtable.findAll", query = "SELECT m FROM Memoirtable m")
    , @NamedQuery(name = "Memoirtable.findByMemoirId", query = "SELECT m FROM Memoirtable m WHERE m.memoirId = :memoirId")
    , @NamedQuery(name = "Memoirtable.findByMoviename", query = "SELECT m FROM Memoirtable m WHERE m.moviename = :moviename")
    , @NamedQuery(name = "Memoirtable.findByMoviereleasedate", query = "SELECT m FROM Memoirtable m WHERE m.moviereleasedate = :moviereleasedate")
    , @NamedQuery(name = "Memoirtable.findByWatchedDate", query = "SELECT m FROM Memoirtable m WHERE m.watchedDate = :watchedDate")
    , @NamedQuery(name = "Memoirtable.findByWatchedTime", query = "SELECT m FROM Memoirtable m WHERE m.watchedTime = :watchedTime")
    , @NamedQuery(name = "Memoirtable.findByComment", query = "SELECT m FROM Memoirtable m WHERE m.comment = :comment")
    , @NamedQuery(name = "Memoirtable.findByRating", query = "SELECT m FROM Memoirtable m WHERE m.rating = :rating")
    , @NamedQuery(name = "Memoirtable.findByMovieNameANDCinemaPostcode", query = "SELECT m FROM Memoirtable m WHERE m.moviename = :moviename AND m.cinemaId.cinemaPostcode = :cinemaPostcode")
    , @NamedQuery(name = "Memoirtable.findByTask1", query = "SELECT m FROM Memoirtable m WHERE m.userId.userId = :userId ")})
public class Memoirtable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MEMOIR_ID")
    private Integer memoirId;
    @Size(max = 20)
    @Column(name = "MOVIENAME")
    private String moviename;
    @Column(name = "MOVIERELEASEDATE")
    @Temporal(TemporalType.DATE)
    private Date moviereleasedate;
    @Column(name = "WATCHED_DATE")
    @Temporal(TemporalType.DATE)
    private Date watchedDate;
    @Column(name = "WATCHED_TIME")
    @Temporal(TemporalType.TIME)
    private Date watchedTime;
    @Size(max = 250)
    @Column(name = "COMMENT")
    private String comment;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RATING")
    private Double rating;
    @JoinColumn(name = "CINEMA_ID", referencedColumnName = "CINEMA_ID")
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
            CascadeType.REFRESH})
    private Cinematable cinemaId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
            CascadeType.REFRESH})
    private Usertable userId;

    public Memoirtable() {
    }

    public Memoirtable(Integer memoirId) {
        this.memoirId = memoirId;
    }

    public Integer getMemoirId() {
        return memoirId;
    }

    public void setMemoirId(Integer memoirId) {
        this.memoirId = memoirId;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public Date getMoviereleasedate() {
        return moviereleasedate;
    }

    public void setMoviereleasedate(Date moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }

    public Date getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(Date watchedDate) {
        this.watchedDate = watchedDate;
    }

    public Date getWatchedTime() {
        return watchedTime;
    }

    public void setWatchedTime(Date watchedTime) {
        this.watchedTime = watchedTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Cinematable getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Cinematable cinemaId) {
        this.cinemaId = cinemaId;
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
        hash += (memoirId != null ? memoirId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoirtable)) {
            return false;
        }
        Memoirtable other = (Memoirtable) object;
        if ((this.memoirId == null && other.memoirId != null) || (this.memoirId != null && !this.memoirId.equals(other.memoirId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "m3.Memoirtable[ memoirId=" + memoirId + " ]";
    }
    
}
