package org.lms.dto.library;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddLibraryCmd {
    @NotEmpty
    @Length(max = 100)
    private String name;

    @Length(max = 40)
    private String isbn;

    @NotEmpty
    @Length(max = 50)
    private String author;

    @NotEmpty
    @Length(max = 50)
    private String publisher;

    @NotNull
    private LocalDate publishTime;

    @Length(max = 50)
    private String categoryName;

    @Length(max = 200)
    private String cover;

    private BigDecimal price;
}
