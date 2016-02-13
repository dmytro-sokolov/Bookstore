package com.yevhenchmykhun.controller.admin;

import com.yevhenchmykhun.dao.BookDao;
import com.yevhenchmykhun.dao.DaoFactory;
import com.yevhenchmykhun.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/admin/addbook")
public class AddNewBookController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String language = request.getParameter("language");
        String publisher = request.getParameter("publisher");
        String format  = request.getParameter("format");
        String isbn = request.getParameter("isbn");
        String pages = request.getParameter("pages");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");
        String categoryId = request.getParameter("selection");
        String releasedate = request.getParameter("releasedate");
        String description = request.getParameter("description");

        Book book = new Book();

        book.setName(title);
        book.setAuthor(author);
        book.setLanguage(language);
        book.setPublisher(publisher);
        book.setFormat(format);
        book.setIsbn(isbn);
        book.setPages(Integer.parseInt(pages));
        book.setPrice(new BigDecimal(Double.parseDouble(price)));
        book.setQuantity(Integer.parseInt(quantity));
        book.setCategory(new DaoFactory().getCategoryDao().getEntityById(Integer.parseInt(categoryId)));
        book.setDescription(description);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(releasedate + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        book.setReleaseDate(new Timestamp(date.getTime()));

        BookDao bookDao = new DaoFactory().getBookDao();
        bookDao.saveEntity(book);

        String url = "/admin/view/addbook.jsp";
        request.getRequestDispatcher(url).forward(request, response);

    }

}
