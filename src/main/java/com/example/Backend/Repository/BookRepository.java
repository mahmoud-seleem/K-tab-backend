package com.example.Backend.Repository;

import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.model.Chapter;
import com.example.Backend.schema.BookHeader;
import com.example.Backend.schema.BookInfo;
import org.hibernate.validator.cfg.defs.UUIDDef;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

//    @Query(
//            value = "SELECT * FROM book WHERE book.title = ?1",
//            nativeQuery = true)
//    Book findByTitle(String title);
    @Query(
            value = "SELECT * FROM book WHERE book.title = ?1",
            nativeQuery = true)
    Book findByTitle(String title);
    List<BookInfo> findByTitleContainingAndAuthor(String title, Author author);

    @Query(
            value = "SELECT * FROM book WHERE book.book_id > ?1 " +
                    "ORDER BY book.book_id " +
                    "LIMIT ?2",
            nativeQuery = true)
    List<Book> getNextPage(UUID bookId,int limit );

    @Query(
            value = "SELECT * FROM book WHERE book.book_id < :bookId " +
                    "ORDER BY book.book_id DESC " +
                    "LIMIT :limit",
            nativeQuery = true)
    List<Book> getPrevPage(@Param("bookId") UUID bookId,@Param("limit") int limit);
    @Query(
            value = "SELECT * FROM book WHERE book.book_id < :bookId " +
                    "ORDER BY book.book_id ASC " +
                    "LIMIT :limit",
            nativeQuery = true)

    List<Book> getPrevPageASC(@Param("bookId") UUID bookId,@Param("limit") int limit);
    // the normal pagination methods without search
    List<Book> findTop7ByBookIdGreaterThanOrderByBookId(UUID bookId);
    List<Book> findTop7ByBookIdLessThanOrderByBookId(UUID bookId);
    List<Book> findTop7ByBookIdLessThanOrderByBookIdDesc(UUID bookId);

    // searching with only tags
    List<Book> findTop7ByTags_TagNameAndBookIdGreaterThanOrderByBookId(
            String tagName,UUID bookId);

    List<Book> findTop7ByTags_TagNameAndBookIdLessThanOrderByBookId(
            String tagName,UUID bookId);
    List<Book> findTop7ByTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
            String tagName,UUID bookId);
    // searching with title only
    List<Book> findTop7ByTitleContainingAndBookIdGreaterThanOrderByBookId(
            String infix,UUID bookId);

    List<Book> findTop7ByTitleContainingAndBookIdLessThanOrderByBookId(
            String infix,UUID bookId);
    List<Book> findTop7ByTitleContainingAndBookIdLessThanOrderByBookIdDesc(
            String infix,UUID bookId);
    // searching with title and tag
    List<Book> findTop7ByTitleContainingAndTags_TagNameAndBookIdGreaterThanOrderByBookId(
            String infix,String tagName,UUID bookId);

    List<Book> findTop7ByTitleContainingAndTags_TagNameAndBookIdLessThanOrderByBookId(
            String infix,String tagName,UUID bookId);
    List<Book> findTop7ByTitleContainingAndTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
            String infix,String tagName,UUID bookId);

    // searching with tags or title
    List<Book> findTop7ByTitleContainingOrTags_TagNameAndBookIdGreaterThanOrderByBookId(
            String infix,String tagName,UUID bookId);

    List<Book> findTop7ByTitleContainingOrTags_TagNameAndBookIdLessThanOrderByBookId(
            String infix,String tagName,UUID bookId);
    List<Book> findTop7ByTitleContainingOrTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
            String infix,String tagName,UUID bookId);


    @Query(
            value = "SELECT * FROM book " +
                    "ORDER BY book.book_id" ,
            nativeQuery = true)
    List<Book> findALlBooks();

    List<Book> findByTags_TagNameOrderByBookId(String tagName); // tags
    List<Book> findByTags_TagNameAndTitleContainingOrderByBookId(String tagName,String infix); // tags and title
    List<Book> findByTags_TagNameOrTitleContainingOrderByBookId(String tagName,String infix); // tags or title
    List<Book> findByTitleContainingOrderByBookId(String infix); // title only

}
