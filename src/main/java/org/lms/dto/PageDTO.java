package org.lms.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class PageDTO<T> {
    protected List<T> records;
    protected long total;
    protected long size;
    protected long current;

    public PageDTO() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
    }

    public PageDTO(long current, long size) {
        this.current = current;
        this.size = size;
    }

    public PageDTO(long size, long current, long total) {
        this.size = size;
        this.current = current;
        this.total = total;
    }


}
