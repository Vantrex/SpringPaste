package de.vantrex.springpaste.model.paste;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import java.util.Date;

@Document(collection = "Pastes")
@Getter
@Setter
@NoArgsConstructor
public class Paste {

    @Id
    private String id;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date lastModified;

    private String title;

    private String content;

    public Paste(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
