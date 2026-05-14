package org.lms.dto.library;

import lombok.Data;

@Data
public class LibraryQuery {
    private Long pageSize = 10L;
    private Long current = 1L;
    private String name;
    private String isbn;
    private String author;
    private String publisher;
    private String categoryName;
}
