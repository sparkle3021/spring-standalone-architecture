package com.yiyan.boot.controller;

import com.yiyan.boot.common.model.result.Result;
import com.yiyan.boot.common.utils.BeanCopierUtils;
import com.yiyan.boot.model.dto.ResourceDTO;
import com.yiyan.boot.model.dto.RoleDTO;
import com.yiyan.boot.model.dto.RoleUpdateDTO;
import com.yiyan.boot.model.request.RoleCreateRequest;
import com.yiyan.boot.model.request.RolePageRequest;
import com.yiyan.boot.model.request.RoleResourceRequest;
import com.yiyan.boot.model.request.RoleUpdateRequest;
import com.yiyan.boot.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Alex Meng
 * @createDate 2023-11-21 05:01
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理API")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping("/create")
    @ResponseBody
    public Result<String> create(@Valid @RequestBody RoleCreateRequest request) {
        Integer createResult = roleService.create(request.getName(), request.getDescription());
        return createResult > 0 ? Result.createSuccess() : Result.error();
    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    @ResponseBody
    public Result<String> update(@PathVariable("id") Long id, @Valid @RequestBody RoleUpdateRequest request) {
        RoleUpdateDTO roleUpdateDTO = BeanCopierUtils.copyProperties(request, RoleUpdateDTO.class);
        roleUpdateDTO.setId(id);
        Integer updateResult = roleService.update(roleUpdateDTO);
        return updateResult > 0 ? Result.updateSuccess() : Result.error();
    }

    @ApiOperation("批量删除角色")
    @PostMapping("/delete")
    @ResponseBody
    public Result<String> delete(@RequestParam List<Long> ids) {
        Integer delete = roleService.delete(ids);
        return delete > 0 ? Result.success() : Result.error();
    }

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    @ResponseBody
    public Result<List<RoleDTO>> listAll() {
        return Result.success(roleService.listAll());
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public Result<List<RoleDTO>> list(RolePageRequest request) {
        List<RoleDTO> list = roleService.list(request.getPageNum(), request.getPageSize(), BeanCopierUtils.copyProperties(request, RoleDTO.class));
        return Result.success(list);
    }

    @ApiOperation("修改角色状态")
    @PostMapping("/updateStatus/{id}")
    @ResponseBody
    public Result<String> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        Integer update = roleService.update(RoleDTO.builder().id(id).status(status).build());
        return update > 0 ? Result.updateSuccess() : Result.error();
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping(value = "/listResource/{roleId}")
    @ResponseBody
    public Result<List<ResourceDTO>> listResource(@PathVariable("roleId") Long roleId) {
        return Result.success(roleService.listResource(roleId));
    }


    @ApiOperation("给角色分配资源")
    @PostMapping("/allowResource")
    @ResponseBody
    public Result<Integer> allocResource(@RequestBody RoleResourceRequest request) {
        Integer bindResource = roleService.updateHasResource(request.getRoleId(), request.getResourceIds());
        return Result.success(bindResource);
    }
}
