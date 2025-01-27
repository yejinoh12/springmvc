package hello.springmvc.mvc2.form;

import hello.springmvc.item.DeliveryCode;
import hello.springmvc.item.Item;
import hello.springmvc.item.ItemRepository;
import hello.springmvc.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions(){
        LinkedHashMap<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL",  "서울");
        regions.put("BUSAN",  "부산");
        regions.put("JEJU",  "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "mvc2/form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "mvc2/form/item";
    }

    //등록 페이지
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "mvc2/form/addForm";
    }

    //hidden _open이 있으면 open값이 체크되지 않았다고 인식
    //null이 아니라 false로 나옴
    @PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {
        log.info("item.getOpen={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    //수정 페이지
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "mvc2/form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, @ModelAttribute("item") Item item) {
        itemRepository.updateItem(itemId, item);
        return "redirect:/form/items/{itemId}";
    }
}
