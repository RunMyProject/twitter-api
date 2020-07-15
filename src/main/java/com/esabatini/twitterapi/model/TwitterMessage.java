/**
 * 
 */
package com.esabatini.twitterapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author es
 *
 */
@Getter @Setter @NoArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"id"})})
public class TwitterMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @NonNull @PrimaryKeyJoinColumn private Long id;
	@NonNull private Long userId;
	@NonNull private String name;
	@NonNull private String message;
    @NonNull private LocalDateTime localDateTimeCreated;

    private TwitterMessage(Long id, Long userId, String name, String message, LocalDateTime localDateTimeCreated) {
        this.id = id;
    	this.userId = userId;
    	this.name = name;
        this.message = message;
        this.localDateTimeCreated = localDateTimeCreated;
    }
}
