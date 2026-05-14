package org.lms.dto.library;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddLibraryCmd {
    @NotEmpty
    private String name;
    private String isbn;
    @NotEmpty
    private String author;
    @NotEmpty
    private String publisher;
    @NotNull
    private LocalDate publishTime;
    private String categoryName;

    private BigDecimal price;
}
