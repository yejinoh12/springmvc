package hello.springmvc.mvc1.basic.requestmapping;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    //메서드를 지정하지 않으면 메서드와 무관하게 호출, 모두 허용
    @RequestMapping("/hello-basic")
    public String helloBasic(){
        log.info("helloBasic");
        return "Ok";
    }

    //@PathVariable
    //최근 HTTP API 리소스 경로에 식별자 넣는 스타일 선호
    //다중 사용 가능
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId={}", data);
        return "OK";
    }

    //Content-Type 헤더 기반 추가 매핑, Media Type
    //맞지 않는 경우 HTTP 415 상태 코드(Unsupported Media Type) 반환
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }

    //HTTP 요청의 Accept 헤더 기반 Media Type 매핑
    //맞지 않는  경우 HTTP 상태 코드 406(Not Acceptable) 반환
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces(){
        log.info("mappingProduces");
        return "ok";
    }


}
