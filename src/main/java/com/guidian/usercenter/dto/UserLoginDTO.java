/**
 * @作者 徐振博
 * @创建时间 2024/5/6 10:03
 */
package com.guidian.usercenter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Primary;

@Data
@NoArgsConstructor
public class UserLoginDTO {

    @NonNull
    private String userAccount;
    @Length(min = 8,max = 12)
    private String userPassword;
}
