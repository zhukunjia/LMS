package org.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.lms.annotation.Permission;
import org.lms.app.LibraryApplication;
import org.lms.dto.PageDTO;
import org.lms.dto.library.AddLibraryCmd;
import org.lms.dto.library.LibraryDTO;
import org.lms.dto.library.LibraryQuery;
import org.lms.dto.library.UpdateLibraryCmd;
import org.lms.util.Constant;
import org.lms.util.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/library")
@Tag(name = "图书管理接口", description = "提供图书管理的CRUD 接口")
public class LibraryController {

    @Autowired
    LibraryApplication libraryApplication;

    @PostMapping("/add")
    @Operation(summary = "新增图书")
    @Permission(role = {Constant.MANAGE})
    public ServiceData<String> addLibrary(@RequestBody @Valid AddLibraryCmd cmd) {
        String libraryId = libraryApplication.addLibrary(cmd);

        return ServiceData.success(libraryId);
    }

    @PostMapping("/update")
    @Operation(summary = "更新图书")
    @Permission(role = {Constant.MANAGE})
    public ServiceData<Boolean> updateLibrary(@RequestBody @Valid UpdateLibraryCmd updateLibraryCmd) {
        boolean updated = libraryApplication.updateLibrary(updateLibraryCmd);

        return ServiceData.success(updated);
    }

    @DeleteMapping("/{libraryId}")
    @Operation(summary = "根据ID 删除图书")
    @Permission(role = {Constant.MANAGE})
    public ServiceData<Boolean> removeLibrary(@PathVariable("libraryId") String libraryId) {
        boolean removed = libraryApplication.removeLibrary(libraryId);

        return ServiceData.success(removed);
    }

    @DeleteMapping("/batchRemove")
    @Operation(summary = "根据ID 列表批量删除图书")
    @Permission(role = {Constant.MANAGE})
    public ServiceData<Boolean> batchRemoveLibrary(@RequestBody List<String> libraryIds) {
        boolean removed = libraryApplication.batchRemoveLibrary(libraryIds);

        return ServiceData.success(removed);
    }

    @GetMapping("/{libraryId}")
    @Operation(summary = "查询图书详情")
    @Permission(role = {Constant.MANAGE, Constant.USER})
    public ServiceData<LibraryDTO> getLibraryDetail(@PathVariable("libraryId") String libraryId) {
        LibraryDTO libraryDTO = libraryApplication.getLibraryDetail(libraryId);

        return ServiceData.success(libraryDTO);
    }

    @PostMapping("/pageQuery")
    @Operation(summary = "分页查询图书")
    @Permission(role = {Constant.MANAGE, Constant.USER})
    public ServiceData<PageDTO<LibraryDTO>> pageQueryLibrary(@RequestBody LibraryQuery query) {
        PageDTO<LibraryDTO> page = libraryApplication.pageQuery(query);

        return ServiceData.success(page);
    }

}
