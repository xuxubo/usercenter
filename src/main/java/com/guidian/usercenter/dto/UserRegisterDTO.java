/**
 * @作者 徐振博
 * @创建时间 2024/5/6 8:06
 */
package com.guidian.usercenter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class UserRegisterDTO {

    @NonNull
    private String userAccount;
    @Length(min = 8,max = 20)
    private String password1;
    @Length(min = 8,max = 20)
    private String password2;
}
