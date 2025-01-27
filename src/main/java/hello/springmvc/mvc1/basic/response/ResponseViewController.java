package hello.springmvc.mvc1.basic.response;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseView1(){
        return new ModelAndView("mvc1/response/hello")
                .addObject("data", "hello!");
    }

    //return type String && responseBody 가 없으면 뷰 리졸버가 실행되어 뷰를 찾고 렌더링
    //responseBody 가 있으면 뷰 리졸버가 실행되지 않고 HTTP 메시지 바디에 직접 문자 입력
    @RequestMapping("/response-view-v2")
    public String responseView2(Model model){
        model.addAttribute("data", "hello!");
        return "mvc1/response/hello";
    }

    //컨트롤러의 이름과 뷰의 논리적이름이 같으면 생략 가능
    //명시성이 떨어지고 이렇게 딱 맞는 경우도 없어서 권장 하지 않음
    @RequestMapping("/mvc1/response/hello")
    public void responseView3(Model model){
        model.addAttribute("data", "hello!");
    }
}
