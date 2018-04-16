package com.pazukdev;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class Hotel implements Serializable, Cloneable {

	private Long id;
	private String name = "";
	private String address = "";
	private String rating;
	private LocalDate operatesFrom;
	private HotelCategory category;
	private String url="http://";
	private String description;

	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public String toString() {
		return name + " " + rating +"stars " + address;
	}

	@Override
	protected Hotel clone() throws CloneNotSupportedException {
		return (Hotel)super.clone();
	}

	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.id == null) {
            return false;
        }

        if (obj instanceof Hotel && obj.getClass().equals(getClass())) {
            return this.id.equals(((Hotel) obj).id);
        }

        return false;
	}

	@Override
	public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (id == null ? 0 : id.hashCode());
        return hash;
	}

	public Hotel() {}

    public Hotel(Long id, String name, String address, String rating, LocalDate operatesFrom, HotelCategory category,
                 String url, String description) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.operatesFrom = operatesFrom;
        this.category = category;
        this.url = url;
        this.description=description;
    }


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}

	public LocalDate getOperatesFrom() {
		return operatesFrom;
	}
	public void setOperatesFrom(LocalDate operatesFrom) {
		this.operatesFrom = operatesFrom;
	}

	public HotelCategory getCategory() {
		return category;
	}
	public void setCategory(HotelCategory category) {
		this.category = category;
	}	

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
	    this.url=url;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description=description;
	}



}