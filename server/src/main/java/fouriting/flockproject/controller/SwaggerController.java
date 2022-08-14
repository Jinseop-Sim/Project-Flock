package fouriting.flockproject.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("/flock-api")
public class SwaggerController {
    @GetMapping
    public String showApi(){
        return "redirect:swagger-ui/index.html#";
    }
}