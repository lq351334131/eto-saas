package etocrm.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dkx
 * @Date: 18:13 2021/1/28
 * @Desc:
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    public HttpMessageConverter<String> stringConverter() {
      return new StringHttpMessageConverter(
                Charset.forName(StandardCharsets.UTF_8.name()));
    }

    /**
     * 格式化返回体，去除null为空字符串
     *
     * @return
     */
    public HttpMessageConverter fastConverter() {
        //1、定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2、添加fastjson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //字符串null返回空字符串
                SerializerFeature.WriteNullStringAsEmpty,
                //数值类型为0
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteDateUseDateFormat,
                //空字段保留
                SerializerFeature.WriteNullListAsEmpty
                );

        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        //2-1 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        //3、在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(fastConverter());
        converters.add(stringConverter());
        converters.add(new ResourceHttpMessageConverter());
    }

}
