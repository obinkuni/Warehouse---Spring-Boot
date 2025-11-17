package com.example.Warehouse.repository;

import com.example.Warehouse.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
