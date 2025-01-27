package hello.springmvc.mvc2;

import hello.springmvc.mvc1.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping
    public String start() {
        return "mvc2/basic/index";
    }

    //escape: HTML에서 사용하는 특수 문자를 HTML엔티티로 변경하는 것
    @GetMapping("text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello <b>Spring</b>");
        return "mvc2/basic/text-basic";
    }

    //escpae를 사용하지 않아서 html이 정상 랜더링 되지 않는 문제 발생,
    //escape를 기본으로 하고 필요할 때만 unescape 사용
    @GetMapping("text-unescaped")
    public String textUnescape(Model model) {
        model.addAttribute("data", "Hello <b>Spring</b>");
        return "mvc2/basic/text-unescape";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        HelloData helloDataA = new HelloData("userA", 10);
        HelloData helloDataB = new HelloData("userB", 20);

        List<HelloData> list = new ArrayList<>();
        list.add(helloDataA);
        list.add(helloDataB);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("userA", helloDataA);
        map.put("userB", helloDataB);

        model.addAttribute("user", helloDataA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "mvc2/basic/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObject(HttpSession session,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model) {
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "mvc2/basic/basic-objects";
    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "mvc2/basic/date";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "mvc2/basic/link";
    }

    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring!");
        return "mvc2/basic/literal";
    }

    @GetMapping("/operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "mvc2/basic/operation";
    }

    @GetMapping("/attribute")
    public String attribute() {
        return "mvc2/basic/attribute";
    }

    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);
        return "mvc2/basic/each";
    }

    @GetMapping("/condition")
    public String condition(Model model) {
        addUsers(model);
        return "mvc2/basic/condition";
    }

    @GetMapping("/block")
    public String block(Model model) {
        addUsers(model);
        return "mvc2/basic/block";
    }

    @GetMapping("/javascript")
    public String javascript(Model model) {
        model.addAttribute("user", new User("userA", 10));
        addUsers(model);
        return "mvc2/basic/javascript";
    }

    private void addUsers(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));
        model.addAttribute("users", list);
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello" + data;
        }
    }

    @Data
    @AllArgsConstructor
    static class User {
        private String username;
        private int age;
    }
}
