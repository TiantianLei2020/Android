/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.service;

import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import m3.Usertable;

/**
 *
 * @author 123
 */
@Stateless
@Path("m3.usertable")
public class UsertableFacadeREST extends AbstractFacade<Usertable> {

    @PersistenceContext(unitName = "M3v2PU")
    private EntityManager em;

    public UsertableFacadeREST() {
        super(Usertable.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usertable entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usertable entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usertable find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usertable> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usertable> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    @GET     
    @Path("findByUserId/{userId}")     
    @Produces({"application/json"})     
    public List<Usertable> findByUserId(@PathParam("userId") Integer userId) {         
        Query query = em.createNamedQuery("Usertable.findByUserId"); 
        query.setParameter("userId", userId);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByUserName/{userName}")     
    @Produces({"application/json"})     
    public List<Usertable> findByUserName(@PathParam("userName") String userName) {         
        Query query = em.createNamedQuery("Usertable.findByUserName"); 
        query.setParameter("userName", userName);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findBySurname/{surname}")     
    @Produces({"application/json"})     
    public List<Usertable> findBySurname(@PathParam("surname") String surname) {         
        Query query = em.createNamedQuery("Usertable.findBySurname"); 
        query.setParameter("surname", surname);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByGender/{gender}")     
    @Produces({"application/json"})     
    public List<Usertable> findByGender(@PathParam("gender") String gender) {         
        Query query = em.createNamedQuery("Usertable.findByGender"); 
        query.setParameter("gender", gender);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByDob/{dob}")     
    @Produces({"application/json"})     
    public List<Usertable> findByDob(@PathParam("dob") Date dob) throws Exception{         
        Query query = em.createNamedQuery("Usertable.findByDob"); 
        query.setParameter("dob", dob);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findAddressByUserId/{userId}")     
    @Produces({"application/json"})     
    public List<Usertable> findAddressByUserId(@PathParam("userId") Integer userId) {         
        Query query = em.createNamedQuery("Usertable.findAddressByUserId"); 
        query.setParameter("userId", userId);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByPostcode/{postcode}")     
    @Produces({"application/json"})     
    public List<Usertable> findByPostcode(@PathParam("postcode") Integer postcode) {         
        Query query = em.createNamedQuery("Usertable.findByPostcode"); 
        query.setParameter("postcode", postcode);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByThreeAttr/{userName}/{surname}/{dob}")     
    @Produces({"application/json"})     
    public List<Usertable> findByThreeAttr(@PathParam("userName") String userName, 
            @PathParam("surname") String surname,
            @PathParam("dob") Date dob) {         
        TypedQuery<Usertable> q = em.createQuery("SELECT u FROM Usertable u WHERE u.userName = :userName AND u.surname = :surname AND u.dob = :dob", Usertable.class);         
        q.setParameter("userName", userName);      
        q.setParameter("surname", surname);
        q.setParameter("dob", dob);
        return q.getResultList();     
    }        
}
