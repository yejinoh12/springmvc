package hello.springmvc.mvc2.message;

import hello.springmvc.item.Item;
import hello.springmvc.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/message/items")
@RequiredArgsConstructor
public class MessageItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "mvc2/message/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "mvc2/message/item";
    }

    //등록 페이지
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "mvc2/message/addForm";
    }

    @PostMapping("/add")
    public String addItemV3(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/message/items/{itemId}";
    }

    //수정 페이지
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "mvc2/message/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, @ModelAttribute("item") Item item) {
        itemRepository.updateItem(itemId, item);
        return "redirect:/message/items/{itemId}";
    }
}
