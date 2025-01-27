package hello.springmvc.mvc1.basic.response;

import hello.springmvc.mvc1.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Controller
//@ResController => ResponseBody 를 클래스 레벨에서 적용, Rest API 만들떄 사용하는 컨트롤러
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3(){
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        return new ResponseEntity<>(new HelloData("userA", 20), HttpStatus.OK);
    }

    //이 경우 상태 코드를 바꿀 수 없는데 애노테이션 이용 가능
    //하지만 애노테이션이기 때문에 동적으로 변경할 수 없음
    //동적으로 변경이 필요할 경우 ResponseEntity 사용
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        return new HelloData("userA", 20);
    }
}
