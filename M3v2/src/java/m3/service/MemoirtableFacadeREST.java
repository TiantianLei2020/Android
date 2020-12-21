/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
import m3.Memoirtable;

/**
 *
 * @author 123
 */
@Stateless
@Path("m3.memoirtable")
public class MemoirtableFacadeREST extends AbstractFacade<Memoirtable> {

    @PersistenceContext(unitName = "M3v2PU")
    private EntityManager em;

    public MemoirtableFacadeREST() {
        super(Memoirtable.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoirtable entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoirtable entity) {
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
    public Memoirtable find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoirtable> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoirtable> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByMemorirId/{memorirId}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByMemorirId(@PathParam("memorirId") Integer memorirId) {         
        Query query = em.createNamedQuery("Memoirtable.findByMemorirId"); 
        query.setParameter("memorirId", memorirId);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByMoviename/{moviename}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByMoviename(@PathParam("moviename") String moviename) {         
        Query query = em.createNamedQuery("Memoirtable.findByMoviename"); 
        query.setParameter("moviename", moviename);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByMoviereleasedate/{moviereleasedate}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByMoviereleasedate(@PathParam("moviereleasedate") Date moviereleasedate) {         
        Query query = em.createNamedQuery("Memoirtable.findByMoviereleasedate"); 
        query.setParameter("moviereleasedate", moviereleasedate);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByWatchedDate/{watchedDate}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByWatchedDate(@PathParam("watchedDate") Date watchedDate) {         
        Query query = em.createNamedQuery("Memoirtable.findByWatchedDate"); 
        query.setParameter("watchedDate", watchedDate);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByWatchedTime/{watchedTime}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByWatchedTime(@PathParam("watchedTime") Time watchedTime) {         
        Query query = em.createNamedQuery("Memoirtable.findByWatchedTime"); 
        query.setParameter("watchedTime", watchedTime);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByComment/{comment}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByComment(@PathParam("comment") String comment) {         
        Query query = em.createNamedQuery("Memoirtable.findByComment"); 
        query.setParameter("comment", comment);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByRating/{rating}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByRating(@PathParam("rating") Double rating) {         
        Query query = em.createNamedQuery("Memoirtable.findByRating"); 
        query.setParameter("rating", rating);
        return query.getResultList();     
    }
    
    @GET     
    @Path("findByMovieNameANDCinemaName/{moviename}/{cinemaname}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByMovieNameANDCinemaName(@PathParam("moviename") String moviename, 
            @PathParam("cinemaname") String cinemaname) {         
        TypedQuery<Memoirtable> q = em.createQuery("SELECT m FROM Memoirtable m WHERE m.moviename = :moviename AND m.cinemaId.cinemaname = :cinemaname", Memoirtable.class);         
        q.setParameter("moviename", moviename);      
        q.setParameter("cinemaname", cinemaname);
        return q.getResultList();     
    }
    
    @GET     
    @Path("findByMovieNameANDCinemaPostcode/{moviename}/{cinemaPostcode}")     
    @Produces({"application/json"})     
    public List<Memoirtable> findByMovieNameANDCinemaPostcode(@PathParam("moviename") String moviename,
        @PathParam("cinemaPostcode") Integer cinemaPostcode ) {         
        Query query = em.createNamedQuery("Memoirtable.findByMovieNameANDCinemaPostcode"); 
        query.setParameter("moviename", moviename);
        query.setParameter("cinemaPostcode", cinemaPostcode);
        return query.getResultList();     
    }
    
    // accept a user id, a starting date and an ending date and return a list that contains the cinema’s suburbs/postcodes and the total number of movies watched per suburb/postcode during that period. 
    @GET 
    @Path("Task1/{userId}/{startDate}/{endDate}") 
    @Produces({MediaType.APPLICATION_JSON}) 
    public Object Task1(@PathParam("userId") Integer userId, @PathParam("startDate") Date startDate, @PathParam("endDate") Date endDate) { 
        TypedQuery<Object[]> q = em.createQuery(
                "SELECT m.cinemaId.cinemaPostcode,count(m.memoirId) FROM Memoirtable m WHERE m.userId.userId = :userId AND m.watchedDate >= :startDate AND m.watchedDate<= :endDate GROUP BY m.cinemaId.cinemaPostcode", Object[].class);       
        q.setParameter("userId", userId); 
        q.setParameter("startDate", startDate); 
        q.setParameter("endDate", endDate); 
        
        List<Object[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
        
        for (Object[] row : queryList) {             
            JsonObject personObject = Json.createObjectBuilder().                          
                add("cinemaPostcode", (Integer)row[0])                          
                    .add("watchedNumber", (long)row[1]).build();             
            arrayBuilder.add(personObject);        
        }        
        JsonArray jArray = arrayBuilder.build();        
        return jArray; 
    }
    
    // accept a user id and a year, and return a list that contains the month names and the total number of movies watched per month in that year
    @GET 
    @Path("Task2/{userId}/{year}") 
    @Produces({MediaType.APPLICATION_JSON}) 
    public Object Task2(@PathParam("userId") Integer userId, @PathParam("year") String year) { 
        TypedQuery<Object[]> q = em.createQuery(
                "SELECT EXTRACT(MONTH FROM m.watchedDate),count(m.memoirId) FROM Memoirtable m WHERE m.userId.userId = :userId AND EXTRACT(YEAR FROM m.watchedDate) = :year GROUP BY EXTRACT(MONTH FROM m.watchedDate) ORDER BY EXTRACT(MONTH FROM m.watchedDate)", Object[].class);       
        q.setParameter("userId", userId); 
        q.setParameter("year", year);  
        
        List<Object[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
     
        for(int i = 1 ; i <= 13 ; i++) {
            for(Object[] row : queryList) {
                if(i < (int)row[0]) {
                    JsonObject personObject = Json.createObjectBuilder().                          
                    add("month", i)                          
                        .add("watchedNumber", 0).build();             
                            arrayBuilder.add(personObject);
                            break;
                }else if(i == (int)row[0]) {
                    JsonObject personObject = Json.createObjectBuilder().                          
                        add("month", row[0].toString())                          
                        .add("watchedNumber", (long)row[1]).build();             
                            arrayBuilder.add(personObject);
                            break;
                }else if(i == 12){
                    JsonObject personObject = Json.createObjectBuilder().                          
                        add("month", "12")                          
                        .add("watchedNumber", 0).build();             
                            arrayBuilder.add(personObject);
                            break;
                }
            }
        }
        
        JsonArray jArray = arrayBuilder.build();        
        return jArray; 
    }
    
    
    //accept a user id and return the name(s), the rating score(s) and release date(s) of the movie(s) with the highest rating score given by that user
    @GET 
    @Path("Task3/{userId}") 
    @Produces({MediaType.APPLICATION_JSON}) 
    public Object Task3(@PathParam("userId") Integer userId) { 
        TypedQuery<Object[]> q = em.createQuery(
                "SELECT DISTINCT m.moviename,m.rating,m.moviereleasedate FROM Memoirtable m WHERE m.rating = (SELECT MAX(m1.rating) from Memoirtable m1) AND m.userId.userId = :userId", Object[].class);       
        q.setParameter("userId", userId); 
        
        List<Object[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
        
        for (Object[] row : queryList) {             
            JsonObject personObject = Json.createObjectBuilder().                          
                add("moviename", (String)row[0])                          
                    .add("rating", (Double)row[1])
                    .add("moviereleasedate", row[2].toString()).build();             
            arrayBuilder.add(personObject);        
        }        
        JsonArray jArray = arrayBuilder.build();        
        return jArray; 
    }
    
    // accept a user id and return a list of movie names and their release years for those movies that their release year is the same as the year the user watched them. 
    @GET 
    @Path("Task4/{userId}") 
    @Produces({MediaType.APPLICATION_JSON}) 
    public Object Task4(@PathParam("userId") Integer userId) { 
        TypedQuery<Object[]> q = em.createQuery(
                "SELECT m.moviename,m.moviereleasedate FROM Memoirtable m WHERE EXTRACT(YEAR FROM m.moviereleasedate) = EXTRACT(YEAR FROM m.watchedDate) AND m.userId.userId = :userId", Object[].class);       
        q.setParameter("userId", userId); 
        
        List<Object[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
        
        for (Object[] row : queryList) {             
            JsonObject personObject = Json.createObjectBuilder().                          
                add("moviename", (String)row[0])
                    .add("moviereleasedate", row[1].toString()).build();             
            arrayBuilder.add(personObject);        
        }        
        JsonArray jArray = arrayBuilder.build();        
        return jArray; 
    }
    
    // accept a user id and return a list of movie names and their release years for those movies that the user has watched their remakes as well. 
    @GET 
    @Path("Task5/{userId}") 
    @Produces({MediaType.APPLICATION_JSON}) 
    public Object Task5(@PathParam("userId") Integer userId) { 
        TypedQuery<Object[]> q = em.createQuery(
                "SELECT DISTINCT m1.moviename,m1.watchedDate,m2.watchedDate FROM Memoirtable m1 JOIN Memoirtable m2 ON m1.moviename = m2.moviename WHERE m1.userId.userId = :userId  AND m1.moviereleasedate <> m2.moviereleasedate", Object[].class);       
        q.setParameter("userId", userId); 
        
        List<Object[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
        
        for (Object[] row : queryList) {             
            JsonObject personObject = Json.createObjectBuilder().                          
                add("MovieName", (String)row[0])
                    .add("FirstReleaseDate", row[1].toString())
                    .add("RemakeReleaseDate", row[2].toString()).build();            
            arrayBuilder.add(personObject);        
        }        
        JsonArray jArray = arrayBuilder.build();        
        return jArray; 
    }
    
    //accept a user id and return a list of the movie names, their release dates and rating scores for FIVE movies that have been released in the recent year and have the highest rating score (five top ones)
    @GET 
    @Path("Task6/{userId}") 
    @Produces({MediaType.APPLICATION_JSON}) 
    public Object Task6(@PathParam("userId") Integer userId) throws Exception{ 
        TypedQuery<Object[]> q = em.createQuery(
                "SELECT m.moviename,m.moviereleasedate,m.rating FROM Memoirtable m WHERE m.userId.userId = :userId AND EXTRACT(YEAR FROM m.moviereleasedate) = EXTRACT(YEAR CURRENT_TIMESTAMP) ORDER BY m.rating DESC", Object[].class);       
        q.setParameter("userId", userId); 
       
        List<Object[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
        int count = 0;
        for (Object[] row : queryList) { 
            if(count < 5){
                JsonObject personObject = Json.createObjectBuilder().                          
                    add("MovieName", (String)row[0])
                    .add("MovieReleaseDate", this.formatDate(row[1].toString()))
                    .add("MovieRating", (Double)row[2]).build();             
                    arrayBuilder.add(personObject); 
                count++;
            }else{
                break;
            } 
        }     
        JsonArray jArray = arrayBuilder.build();        
        return jArray; 
    }    
    
    //accept a user id return rating desc
    @GET 
    @Path("findMemoirByUserId/{userId}") 
    @Produces({MediaType.APPLICATION_JSON}) 
    public Object findMemoirByUserId(@PathParam("userId") Integer userId) throws Exception{ 
        TypedQuery<Object[]> q = em.createQuery(
                "SELECT m.moviename,m.rating,m.moviereleasedate,m.watchedDate,m.comment,m.cinemaId.cinemaPostcode FROM Memoirtable m WHERE m.userId.userId = :userId ORDER BY m.rating DESC", Object[].class);       
        q.setParameter("userId", userId); 
        
        List<Object[]> queryList = q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();        
        
        for (Object[] row : queryList) {             
            JsonObject personObject = Json.createObjectBuilder().                          
                add("moviename", (String)row[0])  
                    .add("rating", (Double)row[1])                                        
                    .add("moviereleasedate", this.formatDate(row[2].toString()))
                    .add("watchedDate", this.formatDate(row[3].toString()))
                    .add("comment", (String)row[4])
                    .add("cinemapostcode", (Integer)row[5])
                    .build();             
            arrayBuilder.add(personObject);        
        }        
        JsonArray jArray = arrayBuilder.build();        
        return jArray; 
    }
    
    private String formatDate(String date) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zyyyy",Locale.US);
        java.util.Date d = sdf.parse(date);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }    
    
    
}
