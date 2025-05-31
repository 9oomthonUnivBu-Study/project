package hello.CRUD.domain;

import jakarta.persistence.*;

@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    private String status; // 판매 상태

    private String title;
    private String description;
    private String imagePath; // 이미지 파일 경로
    private String seller;

    public Item() {
    }


    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.status = "판매중";
    }

    //id
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    //itemName
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    //price
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    //quantity
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    //status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // title
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    // description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // imagePath
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    //String seller
    public String getSeller() {return seller;}
    public void setSeller(String seller) {this.seller = seller;}
}