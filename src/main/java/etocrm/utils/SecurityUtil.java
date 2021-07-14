package etocrm.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.etocrm.database.util.RedisUtil;
import org.etocrm.database.util.SysUserRedisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class SecurityUtil {

    @Autowired
    private RedisUtil redisUtil;

    public SysUserRedisVO getCurrentLoginUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            String authorization = request.getHeader("SYS_SESSION_REDIS_KEY");
            if (StringUtils.isNotBlank(authorization)) {
                log.info("user_session={}", authorization);
                return redisUtil.get(authorization,  SysUserRedisVO.class);
            }
        }
        return null;
    }

}
