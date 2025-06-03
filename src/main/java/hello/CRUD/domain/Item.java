package hello.CRUD.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Integer price;
    private Integer quantity;
    private String status; // 예: 판매중, 예약중, 판매완료

    private String title;
    private String description;
    private String imagePath; // 이미지 파일 경로
    private String seller;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.status = "판매중";
    }
}
