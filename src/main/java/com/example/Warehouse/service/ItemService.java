package com.example.Warehouse.service;

import com.example.Warehouse.dto.ItemRequest;
import com.example.Warehouse.dto.ItemResponse;
import com.example.Warehouse.dto.VariantResponse;
import com.example.Warehouse.exception.NotFoundException;
import com.example.Warehouse.model.Item;
import com.example.Warehouse.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ItemResponse getItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found: " + id));
        return toResponse(item);
    }

    public ItemResponse createItem(ItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        Item saved = itemRepository.save(item);
        return toResponse(saved);
    }

    public ItemResponse updateItem(Long id, ItemRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found: " + id));
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        return toResponse(itemRepository.save(item));
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new NotFoundException("Item not found: " + id);
        }
        itemRepository.deleteById(id);
    }

    private ItemResponse toResponse(Item item) {
        ItemResponse resp = new ItemResponse();
        resp.setId(item.getId());
        resp.setName(item.getName());
        resp.setDescription(item.getDescription());
        resp.setVariants(
                item.getVariants().stream().map(variant -> {
                    VariantResponse v = new VariantResponse();
                    v.setId(variant.getId());
                    v.setName(variant.getName());
                    v.setPrice(variant.getPrice());
                    v.setStockQuantity(variant.getStockQuantity());
                    v.setItemId(item.getId());
                    return v;
                }).collect(Collectors.toList())
        );
        return resp;
    }
}
