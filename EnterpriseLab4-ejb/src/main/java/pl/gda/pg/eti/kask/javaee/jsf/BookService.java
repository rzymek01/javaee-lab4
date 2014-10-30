package pl.gda.pg.eti.kask.javaee.jsf;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Author;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Book;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Library;
import pl.gda.pg.eti.kask.javaee.jsf.entities.ObjectFactory;

/**
 *
 * @author psysiu
 */
@ManagedBean
@ApplicationScoped
@Log
public class BookService implements Serializable {

    private SortedMap<Integer, Book> books;

    private SortedMap<Integer, Author> authors;

    public BookService() {
        books = new TreeMap<>();
        authors = new TreeMap<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
            Unmarshaller u = jaxbContext.createUnmarshaller();
            Library library = (Library) u.unmarshal(getClass().getResourceAsStream("/pl/gda/pg/eti/kask/javaee/jsf/xml/books.xml"));
            for (Book book : library.getBooks()) {
                books.put(book.getId(), book);
                for (Author author : book.getAuthors()) {
                    authors.put(author.getId(), author);
                }
            }
        } catch (JAXBException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

    private List<Author> asList(Author... authors) {
        List<Author> list = new ArrayList<>();
        for (Author author : authors) {
            list.add(author);
        }
        return list;
    }

    public List<Book> findAllBooks() {
        return new ArrayList<>(books.values());
    }

    public Book findBook(int id) {
        return books.get(id);
    }

    public void removeBook(Book book) {
        books.remove(book.getId());
    }

    public void saveBook(Book book) {
        if (book.getId() == 0) {
            book.setId(books.lastKey() + 1);
        }
        books.put(book.getId(), book);
    }

    public List<Author> findAllAuthors() {
        return new ArrayList<>(authors.values());
    }

    public Author findAuthor(int id) {
        return authors.get(id);
    }
    
    public void marshalLibrary(OutputStream out) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
            Marshaller m = jaxbContext.createMarshaller();
            Library library = new Library();
            library.getBooks().addAll(books.values());
            m.marshal(library, out);
        } catch (JAXBException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}
