/**
 * @作者 徐振博
 * @创建时间 2024/5/10 21:08
 */
package com.guidian.usercenter.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@PropertySource(value = "classpath:config/exception-code.properties")
@ConfigurationProperties(prefix = "lin")
@Component
@Getter
@Setter
public class ExceptionCodeConfiguration {

    private Map<Integer, String> codes = new HashMap<>();

    public String getMessage(int code) {
        return codes.get(code);
    }
}
