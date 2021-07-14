package etocrm.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chengrong.yang
 * @Date 2020/12/28 10:19
 */
@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean(value = "saasApi")
    @Order(value = 1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .globalOperationParameters(getGlobalOperationParameters())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //.apis(RequestHandlerSelectors.basePackage("org.etocrm.uam"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo groupApiInfo(){
        return new ApiInfoBuilder()
                .title("Saas系统API文档")
                .description("<div style='font-size:14px;color:red;'>UAM系统API文档</div>")
                //.termsOfServiceUrl("http://localhost:21200/")
                .version("1.0")
                .build();
    }

    private List<Parameter> getGlobalOperationParameters() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false);
        pars.add(parameterBuilder.build());
        return pars;
    }
}
