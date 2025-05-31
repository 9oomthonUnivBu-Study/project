package hello.CRUD.domain;


import org.springframework.stereotype.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public class ItemRepository{
//public interface ItemRepository extends JpaRepository<Item, Long>{

    public static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public void deleteById(Long id) {
        store.remove(id);
    }

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item item = store.get(itemId);
        if (item != null) {
            item.setItemName(updateParam.getItemName());
            item.setPrice(updateParam.getPrice());
            item.setQuantity(updateParam.getQuantity());
            item.setStatus(updateParam.getStatus());
        }
    }

    


    public void clearStore() {
        store.clear();
    }
}