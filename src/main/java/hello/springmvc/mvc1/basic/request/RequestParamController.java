package hello.springmvc.mvc1.basic.request;


import hello.springmvc.mvc1.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    //request.getParameter()
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    //@RequestParam
    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    //@RequestParam
    //파라미터 이름이 변ㅇ수 이름과 같으면 생략가능
    @RequestMapping("/request-param-v3")
    @ResponseBody
    public String requestParamV3(@RequestParam String username, @RequestParam String age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //@RequestParam 생략 가능
    @RequestMapping("/request-param-v4")
    @ResponseBody
    public String requestParamV4(String username, String age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //필수 파라미터
    //required = ture (default) 필수 값 => MissingServletRequestParameterException
    //required = false 생략 가능
    //빈 문자열 통과
    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //빈 문자열의 경우도 기본 값으로 설정
    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    //맵으로 조회
    @RequestMapping("/request-param-map")
    @ResponseBody
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String requestModelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("helloData={}", helloData);
        return "ok";
    }

    //@ModelAttribute 생략 가능
    @RequestMapping("/model-attribute-v2")
    @ResponseBody
    public String requestModelAttributeV2(HelloData helloData) {
        log.info("helloData={}", helloData);
        return "ok";
    }
}
