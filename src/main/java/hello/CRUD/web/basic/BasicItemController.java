package hello.CRUD.web.basic;

import hello.CRUD.domain.Item;
import hello.CRUD.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    // 상품 목록
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품을 찾을 수 없습니다: " + itemId));
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 등록 폼
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "basic/addForm";
    }

    // 상품 등록
    @PostMapping("/add")
    public String save(@ModelAttribute Item item) {
        // 초기 상태 설정
        if (item.getStatus() == null || item.getStatus().isBlank()) {
            item.setStatus("판매중");
        }
        itemRepository.save(item);
        return "redirect:/basic/items";
    }

    // 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    // 상품 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item updateParam) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 변경할 항목들만 업데이트
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
        item.setStatus(updateParam.getStatus());
        item.setTitle(updateParam.getTitle());
        item.setDescription(updateParam.getDescription());
        item.setImagePath(updateParam.getImagePath());
        item.setSeller(updateParam.getSeller());

        itemRepository.save(item);
        return "redirect:/basic/items/{itemId}";
    }

    // 상품 삭제
    @PostMapping("/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemRepository.deleteById(itemId);
        return "redirect:/basic/items";
    }

    // 상태 변경 (판매중 → 예약중 → 판매완료 → 판매중 순환)
    @PostMapping("/{itemId}/change-status")
    public String changeStatus(@PathVariable Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null) {
            switch (item.getStatus()) {
                case "판매중" -> item.setStatus("예약중");
                case "예약중" -> item.setStatus("판매완료");
                default -> item.setStatus("판매중");
            }
            itemRepository.save(item);
        }
        return "redirect:/basic/items";
    }

}
