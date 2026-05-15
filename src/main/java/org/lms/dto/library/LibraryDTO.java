package org.lms.dto.library;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class LibraryDTO {
    private String id;
    private String name;
    private String isbn;
    private String author;
    private String publisher;
    private LocalDate publishTime;
    private String categoryName;
    private BigDecimal price;

    /**
     * 状态 0-下架 1-正常
     */
    private Integer status;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String lastModifiedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastModifiedTime;
}
