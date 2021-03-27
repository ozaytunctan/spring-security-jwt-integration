package com.tr.app.entity.base;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5406804709818587673L;
	
	@Id
	@GeneratedValue(generator = "idGenerator",strategy = GenerationType.SEQUENCE)
	private T id;
	
	public BaseEntity(T id) {
		this.id=id;
	}
	
	

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}
	
	
}
