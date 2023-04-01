package de.vantrex.springpaste.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
public class Paste {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date lastModified;

    private String title;

    private String content;

    public Paste( String title, String content) {
        this.title = title;
        this.content = content;
    }
}
