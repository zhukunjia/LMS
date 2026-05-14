package org.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lms.entity.LibraryEntity;
import org.lms.mapper.LibraryMapper;
import org.lms.service.LibraryService;
import org.springframework.stereotype.Service;

@Service
public class LibraryrServiceImpl extends ServiceImpl<LibraryMapper, LibraryEntity> implements LibraryService {
}
