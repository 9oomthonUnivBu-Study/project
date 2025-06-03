package hello.CRUD.domain;


import org.springframework.stereotype.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}