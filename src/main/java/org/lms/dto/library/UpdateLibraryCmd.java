package org.lms.dto.library;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateLibraryCmd {
    @NotEmpty
    private String id;
    private String name;
    private String isbn;
    private String author;
    private String publisher;
    private LocalDate publishTime;
    private String categoryName;

    private BigDecimal price;
}
