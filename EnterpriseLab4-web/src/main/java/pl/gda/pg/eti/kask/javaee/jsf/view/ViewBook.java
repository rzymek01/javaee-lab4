package pl.gda.pg.eti.kask.javaee.jsf.view;

import pl.gda.pg.eti.kask.javaee.jsf.BookService;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Book;

/**
 *
 * @author psysiu
 */
@ViewScoped
@ManagedBean
@Log
public class ViewBook implements Serializable {

    @ManagedProperty("#{bookService}")
    private BookService bookService;

    @Getter
    @Setter
    private int bookId;

    @Getter
    @Setter
    private Book book;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    
    public void init() {
        if (book == null) {
            book = bookService.findBook(bookId);
        }
        if (book == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

}
