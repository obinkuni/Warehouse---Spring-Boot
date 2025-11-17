package com.example.Warehouse.controller;

import com.example.Warehouse.dto.ItemRequest;
import com.example.Warehouse.dto.ItemResponse;
import com.example.Warehouse.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemResponse> listItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ItemResponse getItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse createItem(@Valid @RequestBody ItemRequest request) {
        return itemService.createItem(request);
    }

    @PutMapping("/{id}")
    public ItemResponse updateItem(@PathVariable Long id,
                                   @Valid @RequestBody ItemRequest request) {
        return itemService.updateItem(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
