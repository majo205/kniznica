package org.seman.register.dbo;
import java.sql.Timestamp;

import javax.persistence.*;


@Entity //(name= "Book")
public class Book {
	

	
	@Id
	@Column (name= "id", nullable= false, unique= true)
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	public int id;
	
	public String autor= "autor";
	public int rokVydania= 1999;
	
	public String nazov= "nazov";
	public String vydavatelstvo= "vydavatelstvo";
	public String zaner= "zaner";
	public Timestamp timestamp= new Timestamp(new java.util.Date().getTime());

	public Book() {

	}
	
	public Book(String autor, int rokVydania, String nazov){
		this.setAutor(autor);
		this.setRokVydania(rokVydania);
		this.setNazov(nazov);
		
		
	}

	public Book(String autor, int rokVydania, String nazov,
			String vydavatelstvo, String zaner, Timestamp timestamp) {
		this(autor,rokVydania,nazov);
		this.setVydavatelstvo(vydavatelstvo);
		this.setZaner(zaner);
		this.setTimestamp(timestamp);

	}

	@Override
	public String toString() {
		return "Kniha [idKnihy=" + id + ", autor=" + autor
				+ ", rokVydania=" + rokVydania + ", nazov=" + nazov
				+ ", vydavatelstvo=" + vydavatelstvo + ", zaner=" + zaner
				+ ", timestamp=" + timestamp + "]";
	}

	public int getIdKnihy() {
		return id;
	}

	public void setIdKnihy(int idKnihy) {
		this.id = idKnihy;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getRokVydania() {
		return rokVydania;
	}

	public void setRokVydania(int rokVydania) {
		this.rokVydania = rokVydania;
	}

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}

	public String getVydavatelstvo() {
		return vydavatelstvo;
	}

	public void setVydavatelstvo(String vydavatelstvo) {
		this.vydavatelstvo = vydavatelstvo;
	}

	public String getZaner() {
		return zaner;
	}

	public void setZaner(String zaner) {
		this.zaner = zaner;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
