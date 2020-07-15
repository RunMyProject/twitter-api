/**
 * 
 */
package com.esabatini.twitterapi.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author es
 *
 */
@Getter @Setter @NoArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"id", "name"})})
public class User implements Serializable {	
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @NonNull @PrimaryKeyJoinColumn private Long id;
	private @NonNull String name;	
	private @ElementCollection(targetClass=Long.class) @NonNull Set<Long> followers;
	
	public User(Long id, String name, Set<Long> followers) {
		this.id = id;
		this.name = name;
		this.followers = followers;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o)
	      return true;
	    if (!(o instanceof User))
	      return false;
	    User user = (User) o;
	    return Objects.equals(this.id, user.id) && Objects.equals(this.name, user.name);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(this.id, this.name);
	}

	@Override
	public String toString() {
	    return "User{" + "id=" + this.id + ", name='" + this.name + "\'" + ", followers=" + followers.toArray().toString() +"}";
	}
}
