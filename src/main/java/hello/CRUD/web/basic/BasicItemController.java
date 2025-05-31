package hello.CRUD.web.basic;

import hello.CRUD.domain.Item;
import hello.CRUD.domain.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    // 전체 목록
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 상세 페이지
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + itemId));
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 등록 폼
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "basic/addForm";
    }

    // 등록 처리
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        item.setStatus("판매중");

        if (item.getImagePath() == null || item.getImagePath().isEmpty()) {
            item.setImagePath("/images/sample.png"); // 업로드 안했을 때 기본 이미지 지정
        }

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }


    // 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + itemId));
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    // 수정 처리
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item updateParam) {
        itemRepository.update(itemId, updateParam);
        return "redirect:/basic/items/{itemId}";
    }

    // 상태 변경
    @PostMapping("/{itemId}/change-status")
    public String changeStatus(@PathVariable Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + itemId));
        switch (item.getStatus()) {
            case "판매중" -> item.setStatus("예약중");
            case "예약중" -> item.setStatus("판매완료");
            default -> item.setStatus("판매중");
        }
        return "redirect:/basic/items/" + itemId;
    }

    // 삭제 기능 폼
    @PostMapping("/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemRepository.deleteById(itemId);
        return "redirect:/basic/items";
    }



    // 테스트용 초기 데이터
   /* @PostConstruct
    public void init() {
        itemRepository.save(new Item("노트북", 1500000, 5));


        itemRepository.save(new Item("스마트폰", 800000, 10));

    }
    */
}
