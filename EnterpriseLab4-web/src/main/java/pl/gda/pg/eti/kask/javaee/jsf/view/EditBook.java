package pl.gda.pg.eti.kask.javaee.jsf.view;

import pl.gda.pg.eti.kask.javaee.jsf.BookService;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Author;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Book;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Cover;

/**
 *
 * @author psysiu
 */
@ViewScoped
@ManagedBean
@Log
public class EditBook implements Serializable {

    @ManagedProperty("#{bookService}")
    private BookService bookService;

    @Getter
    @Setter
    private int bookId;

    @Getter
    @Setter
    private Author a;
    
    @Getter
    @Setter
    private Book book;
    
    private List<SelectItem> authorsAsSelectItems;
    
    private List<SelectItem> coversAsSelectItems;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    
    public List<SelectItem> getAuthorsAsSelectItems() {
        if (authorsAsSelectItems == null) {
            authorsAsSelectItems = new ArrayList<>();
            for (Author author : bookService.findAllAuthors()) {
                authorsAsSelectItems.add(new SelectItem(author, author.getName() + " " + author.getSurname()));
            }
        }
        return authorsAsSelectItems;
    }
    
    public List<SelectItem> getCoversAsSelectItems() {
        if (coversAsSelectItems == null) {
            coversAsSelectItems = new ArrayList<>();
            coversAsSelectItems.add(new SelectItem(null, "---"));
            for (Cover cover : Cover.values()) {
                coversAsSelectItems.add(new SelectItem(cover, cover.name().toLowerCase()));
            }
        }
        return coversAsSelectItems;
    }

    public void init() {
        if (book == null && bookId != 0) {
            book = bookService.findBook(bookId);
        } else if (book == null && bookId == 0) {
            book = new Book();
        }
        if (book == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    public String saveBook() {
        bookService.saveBook(book);
        return "list_books?faces-redirect=true";
    }
}
