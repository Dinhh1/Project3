package cs122b.Models;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cs122b.DB.*;

import javax.xml.bind.annotation.XmlElement;
/**
 * Created by dinhho on 1/12/15.
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movie extends BaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1028298383193850418L;
	private int id = MovieDB.DBConstant.INVALID_ID;
    private String title = "";
    private int year = 2014;
    private String director = "";
    private String banner_url = null;
    private String trailer_url = null;

    private int count = 1;
    // nullable properties
    ArrayList<Star> stars_in_movie;
    ArrayList<Genre> genres_of_movie;
    
    public Movie() {
        super();
    }

    public Movie(int id, String t, int y, String d, String b, String tr) {
        super();
        this.id = id;
        this.title = t;
        this.year = y;
        this.director = d;
        this.banner_url = b;
        this.trailer_url = tr;
    }

    public Movie(int id, String t, int y, String d, String b, String tr, int count) {
        super();
        this.id = id;
        this.title = t;
        this.year = y;
        this.director = d;
        this.banner_url = b;
        this.trailer_url = tr;
        this.count = count;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public int getYear() {
        return this.year;
    }

    public String getDirector() {
        return this.director;
    }

    public String getBannerURL() {
        return this.banner_url;
    }

    public String getTrailerURL() {
        return this.trailer_url;
    }

    public int getCount() {return this.count;}
	@XmlElement(name="genre")
    public ArrayList<Genre> getGenresOfMovie() {
    	return this.genres_of_movie;
    }
    
	@XmlElement(name="stars")
    public ArrayList<Star> getStarsOfMovie() {

    	return this.stars_in_movie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setYear(int y) {
        this.year = y;
    }

    public void setDirector(String d) {
        this.director = d;
    }

    public void setBannerURL(String burl) {
        this.banner_url = burl;
    }

    public void setTrailerURL(String turl) {
        this.trailer_url = turl;
    }
    
    public void setGenresOfMovies(ArrayList<Genre> g) {
    	this.genres_of_movie = g;
    }
    
    public void setStarsInMovies(ArrayList<Star> m) {
    	this.stars_in_movie = m;
    }

    public void setCount(int c) { this.count = c;}


}
