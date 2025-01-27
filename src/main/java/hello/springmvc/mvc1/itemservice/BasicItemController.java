package hello.springmvc.mvc1.itemservice;


import hello.springmvc.item.Item;
import hello.springmvc.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "mvc1/basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "mvc1/basic/item";
    }

    //등록 페이지
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "mvc1/basic/addForm";
    }

    //상품 저장
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {

        Item newItem = new Item();
        newItem.setItemName(itemName);
        newItem.setPrice(price);
        newItem.setQuantity(quantity);

        itemRepository.save(newItem);
        model.addAttribute("item", newItem);

        return "basic/item";
    }

    /**
     * @ModelAttribute 의 두가지 기능
     * 1) Item 객체를 생성하고 요청 파라미터의 값을 프로퍼팉 접근법 set 으로 입력해줌
     * 2) 모델에 @ModelAttribute 로 지정한 객체를 자동으로 넣으줌
     * 2-1) 모델에 객체를 넣을 떄에는 name(value)를 사용한다.
     * ex) @ModelAttribute HelloData data -> 이 경우 클래스 이름 첫글자를 소문자로 바꾼 helloData 가 담김
     */

    //상품 저장
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        //주석 처리 가능
        //model.addAttribute("item", item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    //수정 페이지
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "mvc1/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, @ModelAttribute("item") Item item) {
        itemRepository.updateItem(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
