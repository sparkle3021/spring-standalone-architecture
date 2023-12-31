package com.yiyan.boot.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Alex Meng
 * @createDate 2023-11-23 11:49
 */
@Data
@ApiModel(value = "用户修改密码请求参数")
public class UserUpdatePasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
