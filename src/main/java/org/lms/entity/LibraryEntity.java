package org.lms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@TableName("lms_library")
public class LibraryEntity {
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
    private Date createTime;
    private String lastModifiedBy;
    private Date lastModifiedTime;
}
