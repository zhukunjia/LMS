package org.lms.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lms.dto.library.AddLibraryCmd;
import org.lms.dto.library.LibraryQuery;
import org.lms.dto.library.UpdateLibraryCmd;
import org.lms.entity.LibraryEntity;
import org.lms.exception.LmsException;
import org.lms.service.LibraryService;
import org.lms.util.IdGenUtil;
import org.lms.util.RequestContextHolder;
import org.lms.util.RetCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class LibraryApplication {

    @Autowired
    LibraryService libraryService;

    public String addLibrary(AddLibraryCmd addLibraryCmd) {
        // TODO 可以考虑根据 isbn 查询一下是否存在，如果不存在才保存；存在了则提示isbn 已经存在
        // AddLibraryCmd 转换到 LibraryEntity
        // TODO 后续可以考虑引入 mapstruct
        String id = IdGenUtil.genLibraryId();
        LibraryEntity libraryEntity = new LibraryEntity();
        libraryEntity.setId(id);
        libraryEntity.setName(addLibraryCmd.getName());
        libraryEntity.setIsbn(addLibraryCmd.getIsbn());
        libraryEntity.setAuthor(addLibraryCmd.getAuthor());
        libraryEntity.setPublisher(addLibraryCmd.getPublisher());
        libraryEntity.setPublishTime(addLibraryCmd.getPublishTime());
        libraryEntity.setPrice(addLibraryCmd.getPrice());
        libraryEntity.setCreateBy(RequestContextHolder.getUserId());
        libraryEntity.setLastModifiedBy(RequestContextHolder.getUserId());

        libraryService.save(libraryEntity);

        log.info("Add library successful, name = {} and  id = {}", addLibraryCmd.getName(), id);

        return id;
    }

    public boolean removeLibrary(String libraryId) {
        boolean removed = libraryService.removeById(libraryId);
        if(removed) {
            log.info("remove library = {} successful by {}", libraryId, RequestContextHolder.getUserId());
        }

        return removed;
    }

    @Transactional
    public boolean batchRemoveLibrary(List<String> libraryIds) {
        if(libraryIds == null || libraryIds.isEmpty()) {
            return true;
        }
        boolean removed = libraryService.removeBatchByIds(libraryIds);
        if(removed) {
            log.info("remove library = {} successful by {}", libraryIds, RequestContextHolder.getUserId());
        }
        return removed;
    }

    public boolean updateLibrary(UpdateLibraryCmd updateLibraryCmd) {
        LibraryEntity entity = getLibraryDetail(updateLibraryCmd.getId());
        if(null == entity) {
            log.info("The libraryId = {} does not exist.", updateLibraryCmd.getId());
            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "the library does not exist");
        }

        // TODO 如果传进来的isbn 不一样，需要再根据 isbn 查一下数据库，查到数据则表示isbn 已经存在，抛异常提示isbn 已经存在

        entity.setName(updateLibraryCmd.getName());
        entity.setIsbn(updateLibraryCmd.getIsbn());
        entity.setAuthor(updateLibraryCmd.getAuthor());
        entity.setPublisher(updateLibraryCmd.getPublisher());
        entity.setPublishTime(updateLibraryCmd.getPublishTime());
        entity.setPrice(updateLibraryCmd.getPrice());
        entity.setLastModifiedBy(RequestContextHolder.getUserId());
        entity.setLastModifiedTime(new Date());

        boolean updated = libraryService.updateById(entity);
        if(updated) {
            log.info("update library = {} successful by {}", updateLibraryCmd.getId(), RequestContextHolder.getUserId());
        }
        return updated;
    }

    public LibraryEntity getLibraryDetail(String libraryId) {
        LibraryEntity entity = libraryService.getById(libraryId);
        if(null == entity) {
            log.info("The libraryId = {} does not exist.", libraryId);
            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "the library does not exist");
        }
        // 理论上可以加上缓存，提高接口性能。在修改的时候要同步删除缓存。避免缓存和数据不一致

        return entity;
    }

    public IPage<LibraryEntity> pageQuery(LibraryQuery query) {
        Page<LibraryEntity> page = new Page<>(query.getCurrent(), query.getPageSize());
        LambdaQueryWrapper<LibraryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(query.getIsbn()), LibraryEntity::getIsbn, query.getIsbn())
                .like(StringUtils.isNotEmpty(query.getName()), LibraryEntity::getName, query.getName())
                .like(StringUtils.isNotEmpty(query.getAuthor()), LibraryEntity::getAuthor, query.getAuthor())
                .like(StringUtils.isNotEmpty(query.getPublisher()), LibraryEntity::getPublisher, query.getPageSize())
                .like(StringUtils.isNotEmpty(query.getCategoryName()), LibraryEntity::getCategoryName, query.getCategoryName());

        // TODO 可以定义一个 DTO，用 mapstruct 转换一下再返回
        return libraryService.page(page, queryWrapper);
    }

}
