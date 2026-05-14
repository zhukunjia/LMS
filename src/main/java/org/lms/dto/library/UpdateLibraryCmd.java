package org.lms.dto.library;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateLibraryCmd {
    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
    private String isbn;
    @NotEmpty
    private String author;
    @NotEmpty
    private String publisher;
    @NotEmpty
    private LocalDate publishTime;
    private String categoryName;

    private BigDecimal price;
}
