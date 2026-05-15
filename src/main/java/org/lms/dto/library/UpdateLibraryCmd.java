package org.lms.dto.library;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateLibraryCmd {
    @NotEmpty
    private String id;

    @Length(max = 100)
    private String name;

    @Length(max = 50)
    private String isbn;

    @Length(max = 50)
    private String author;

    @Length(max = 50)
    private String publisher;

    private LocalDate publishTime;

    @Length(max = 50)
    private String categoryName;

    @Length(max = 200)
    private String cover;

    private BigDecimal price;
}
