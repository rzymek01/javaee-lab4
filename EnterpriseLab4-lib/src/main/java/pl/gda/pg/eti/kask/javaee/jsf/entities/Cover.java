package pl.gda.pg.eti.kask.javaee.jsf.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author psysiu
 */
@XmlType(name = "cover")
@XmlEnum
public enum Cover {
    
    HARD,
    SOFT;
    
}
