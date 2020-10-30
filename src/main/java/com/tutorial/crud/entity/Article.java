package com.tutorial.crud.entity;

import java.io.Serializable;
//import java.util.Date;

//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Size;
//import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Article implements Serializable, Comparable<Article> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String title;

//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	private Date day;

	private String subtitle;

	@NotEmpty
	private String link;

//	private String text;

	@ManyToOne
	private Category category;

	public Article() {
	}

	public Article(Long id, String title, String subtitle, String link, Category category) {

		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.link = link;
		this.category = category;
	}

	public Article(String title, String subtitle, String link, Category category) {

		this.title = title;
		this.subtitle = subtitle;
		this.link = link;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int compareTo(Article comparearticle) {
		Long compareids = ((Article) comparearticle).getId();
		return (int) (compareids - this.id);
	}

}
