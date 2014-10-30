package pl.gda.pg.eti.kask.javaee.jsf.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.gda.pg.eti.kask.javaee.jsf.entities.validators.InPast;

/**
 *
 * @author psysiu
 */
@ToString(of = "title")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "book")
public class Book implements Serializable {

    @XmlAttribute
    private int id;

    @XmlAttribute
    private String title;
    
    @XmlAttribute
    private Cover cover = Cover.SOFT;

    @XmlAttribute
    @InPast(date = "2013-10-19")
    private Date publishDate;

    @Size(min = 1)
    @XmlElement(name = "author")
    private List<Author> authors = new ArrayList<>();

}
