package com.destruction.myDemolish.controllers;

import com.destruction.myDemolish.mysql.Book;
import com.destruction.myDemolish.mysql.repos.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookRepository bookRepository;


    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/page/{page}")
    public Page<Book> getPage(@PathVariable("page") String page) {

        Pageable pageable = PageRequest.of(Integer.valueOf(page), 10);

        return bookRepository.findAll(pageable);

    }


    @GetMapping("/book")
    public Book getBookRecord() {
        Book b = bookRepository.save(new Book(null, "Hanna's little puppy", Calendar.getInstance(), "Isabela Kit"));
        return b;
    }

    @GetMapping("/all")
    public List<Book> getAllBookRecords() {

        return (List<Book>) bookRepository.findAll();
    }

    public void generateRandomDbData() {

        Random random = new Random();

        String article[] = {"the", "a", "one", "some", "any"};
        // define noun, verb, preposition in same way here
        String noun[] = {"boy", "dog", "car", "bicycle"};
        String verb[] = {"ran", "jumped", "sang", "moves"};
        String preposition[] = {"away", "towards", "around", "near"};

        String names[] = {"Mark", "Tom", "Julia", "Vlada", "Pera", "Misko", "Katarina", "Jelena"};
        String last_names[] = {"Sicily", "Osadkovki", "Markovis", "Omenda", "Virgo", "Opernal", "Makserna", "Molotovic"};

        String days[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String months[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        String years[] = {"1911", "1945", "1966", "1987", "1999", "1921", "1934", "1975"
                , "1971", "1998", "1995", "1959", "1912", "1855", "2002", "2010"};

        // randomly create sentence
        for (int j = 0; j < 200; j++) {
            int article1 = random.nextInt(article.length - 1);
            // generat others here like noun1, verb1, ....
            int noun1 = random.nextInt(article.length - 1);
            int verb1 = random.nextInt(article.length - 1);
            int preposition1 = random.nextInt(article.length - 1);
            int article2 = random.nextInt(article.length - 1);
            int noun2 = random.nextInt(article.length - 1);

            StringBuilder title = new StringBuilder();

            title.append(article[article1]).append(" ")
                    .append(noun[noun1]).append(" ")
                    .append(verb[verb1]).append(" ")
                    .append(preposition[preposition1]).append(" ")
                    .append(article[article2]).append(" ")
                    .append(noun[noun2]);


            StringBuilder author = new StringBuilder();
            int name_index = random.nextInt(names.length - 1);
            int lastname_index = random.nextInt(last_names.length - 1);
            author.append(names[name_index]).append(" ").append(last_names[lastname_index]);
            String authorName = author.toString();

            StringBuilder date = new StringBuilder();
            int day_index = random.nextInt(days.length - 1);
            int month_index = random.nextInt(months.length - 1);
            int year_index = random.nextInt(years.length - 1);

            int day = Integer.valueOf(days[day_index]);
            int month = Integer.valueOf(months[month_index]);
            int year = Integer.valueOf(years[year_index]);

            Calendar yearPublished = Calendar.getInstance();
            yearPublished.set(year, month, day);
            // capitalize first letter and display
            title.setCharAt(0, Character.toUpperCase(title.charAt(0)));

            String title_sentence = title.toString();

            bookRepository.save(new Book(null, title_sentence, yearPublished, authorName));
        }
    }
}
