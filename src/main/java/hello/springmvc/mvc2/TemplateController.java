package hello.springmvc.mvc2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    @GetMapping("/fragment")
    public String template() {
        return "mvc2/template/fragment/fragmentMain";
    }

    @GetMapping("/layout")
    public String layout() {
        return "mvc2/template/layout/layoutMain";
    }

    @GetMapping("/layoutExtend")
    public String layoutExtends() {
        return "mvc2/template/layoutExtend/layoutExtendMain";
    }


}
