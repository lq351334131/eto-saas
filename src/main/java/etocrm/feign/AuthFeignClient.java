package etocrm.feign;

import org.etocrm.config.FeignConfiguration;
import org.etocrm.database.util.SysUserRedisVO;
import org.etocrm.model.user.LoginUser;
import org.etocrm.model.user.TokenValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "etocrm-auth",configuration = FeignConfiguration.class)
public interface AuthFeignClient {

    @PostMapping("/login")
    TokenValue login(@RequestBody LoginUser user);

    @GetMapping("/token")
    SysUserRedisVO getToken();
}
