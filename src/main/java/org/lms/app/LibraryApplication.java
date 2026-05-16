package org.lms.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lms.converter.LibraryConverter;
import org.lms.dto.PageDTO;
import org.lms.dto.library.AddLibraryCmd;
import org.lms.dto.library.LibraryDTO;
import org.lms.dto.library.LibraryQuery;
import org.lms.dto.library.UpdateLibraryCmd;
import org.lms.entity.LibraryEntity;
import org.lms.exception.LmsException;
import org.lms.service.LibraryService;
import org.lms.util.Constant;
import org.lms.util.IdGenUtil;
import org.lms.util.RequestContextHolder;
import org.lms.util.RetCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class LibraryApplication {

    @Autowired
    LibraryService libraryService;

    public String addLibrary(AddLibraryCmd addLibraryCmd) {
        // 虽然isbn 已经加了唯一索引，但是这里还是需要校验一下isbn 是否被使用
        LambdaQueryWrapper<LibraryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(LibraryEntity::getId, LibraryEntity::getIsbn)
                .eq(LibraryEntity::getIsbn, addLibraryCmd.getIsbn())
                .last(" limit 1");

        LibraryEntity existEntity = libraryService.getOne(queryWrapper);
        if (null != existEntity) {
            log.info("failed to add library, isbn = {} is exist", addLibraryCmd.getIsbn());

            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "isbn is exist");
        }

        String id = IdGenUtil.genLibraryId();
        // AddLibraryCmd 转换到 LibraryEntity
        LibraryEntity libraryEntity = LibraryConverter.INSTANCE.addLibraryCmdToEntity(addLibraryCmd);
        libraryEntity.setId(id);
        libraryEntity.setCreateBy(RequestContextHolder.getUserId());
        libraryEntity.setLastModifiedBy(RequestContextHolder.getUserId());

        libraryService.save(libraryEntity);

        log.info("Add library successful, name = {} and  id = {}", addLibraryCmd.getName(), id);

        return id;
    }

    public boolean removeLibrary(String libraryId) {
        boolean removed = libraryService.removeById(libraryId);
        if (removed) {
            log.info("remove library = {} successful by {}", libraryId, RequestContextHolder.getUserId());
        }

        return removed;
    }

    @Transactional
    public boolean batchRemoveLibrary(List<String> libraryIds) {
        if (libraryIds == null || libraryIds.isEmpty()) {
            return true;
        }
        boolean removed = libraryService.removeBatchByIds(libraryIds);
        if (removed) {
            log.info("remove library = {} successful by {}", libraryIds, RequestContextHolder.getUserId());
        }
        return removed;
    }

    public boolean updateLibrary(UpdateLibraryCmd updateLibraryCmd) {
        // 根据 id 查询图书信息，不存在则提示图书不存在
        LibraryEntity entity = libraryService.getById(updateLibraryCmd.getId());
        if (null == entity) {
            log.info("The libraryId = {} does not exist.", updateLibraryCmd.getId());
            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "the library does not exist");
        }

        // 如果修改的是isbn，需要检验一下这个isbn 是否已经被使用了
        if (StringUtils.isNotEmpty(updateLibraryCmd.getIsbn()) && !StringUtils.equals(updateLibraryCmd.getIsbn(), entity.getIsbn())) {
            LambdaQueryWrapper<LibraryEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(LibraryEntity::getId, LibraryEntity::getIsbn)
                    .eq(LibraryEntity::getIsbn, updateLibraryCmd.getIsbn())
                    .last(" limit 1");

            LibraryEntity existEntity = libraryService.getOne(queryWrapper);
            if (null != existEntity) {
                log.info("failed to update library of id ={}, the isbn = {} is exist", updateLibraryCmd.getId(), updateLibraryCmd.getIsbn());

                throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "isbn is exist");
            }
        }

        entity.setName(updateLibraryCmd.getName());
        entity.setIsbn(updateLibraryCmd.getIsbn());
        entity.setAuthor(updateLibraryCmd.getAuthor());
        entity.setPublisher(updateLibraryCmd.getPublisher());
        entity.setPublishTime(updateLibraryCmd.getPublishTime());
        entity.setPrice(updateLibraryCmd.getPrice());
        entity.setLastModifiedBy(RequestContextHolder.getUserId());
        entity.setLastModifiedTime(new Date());

        boolean updated = libraryService.updateById(entity);
        if (updated) {
            log.info("update library = {} successful by {}", updateLibraryCmd.getId(), RequestContextHolder.getUserId());
        }
        return updated;
    }

    public LibraryDTO getLibraryDetail(String libraryId) {
        // 理论上可以加上缓存，提高接口性能。在修改的时候要同步删除缓存。避免缓存和数据不一致
        LibraryEntity entity = libraryService.getById(libraryId);
        if (null == entity) {
            log.info("The libraryId = {} does not exist.", libraryId);
            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "the library does not exist");
        }

        LibraryDTO libraryDTO = LibraryConverter.INSTANCE.entityToDTO(entity);

        return libraryDTO;
    }

    public PageDTO<LibraryDTO> pageQuery(LibraryQuery query) {
        Page<LibraryEntity> page = new Page<>(query.getCurrent(), query.getPageSize());
        LambdaQueryWrapper<LibraryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(query.getIsbn()), LibraryEntity::getIsbn, query.getIsbn())
                .eq(Objects.nonNull(query.getStatus()), LibraryEntity::getStatus, query.getStatus())
                .like(StringUtils.isNotEmpty(query.getName()), LibraryEntity::getName, query.getName())
                .like(StringUtils.isNotEmpty(query.getAuthor()), LibraryEntity::getAuthor, query.getAuthor())
                .like(StringUtils.isNotEmpty(query.getPublisher()), LibraryEntity::getPublisher, query.getPublisher())
                .like(StringUtils.isNotEmpty(query.getCategoryName()), LibraryEntity::getCategoryName, query.getCategoryName());

        Page<LibraryEntity> entityPage = libraryService.page(page, queryWrapper);
        List<LibraryDTO> libraryDTOS = LibraryConverter.INSTANCE.entityListToDTO(entityPage.getRecords());

        PageDTO<LibraryDTO> dtoPage = new PageDTO<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());
        dtoPage.setRecords(libraryDTOS);

        return dtoPage;
    }

    public boolean offLineLibrary(String libraryId) {
        return updateLibraryStatus(libraryId, Constant.OFFLINE);
    }

    public boolean onLineLibrary(String libraryId) {
        return updateLibraryStatus(libraryId, Constant.ONLINE);
    }

    private boolean updateLibraryStatus(String libraryId, Integer status) {
        LibraryEntity entity = libraryService.getById(libraryId);
        if(null == entity) {
            log.info("update library status failed,libraryId = {} does not exist", libraryId);

            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "the library does not exist");
        }

        LambdaUpdateWrapper<LibraryEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LibraryEntity::getId, libraryId)
                .set(LibraryEntity::getStatus, status)
                .set(LibraryEntity::getLastModifiedBy, RequestContextHolder.getUserId())
                .set(LibraryEntity::getLastModifiedTime, new Date());

        boolean update = libraryService.update(updateWrapper);

        return update;
    }

}
