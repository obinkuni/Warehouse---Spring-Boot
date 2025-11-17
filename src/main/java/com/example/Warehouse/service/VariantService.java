package com.example.Warehouse.service;

import com.example.Warehouse.dto.StockOperationRequest;
import com.example.Warehouse.dto.VariantRequest;
import com.example.Warehouse.dto.VariantResponse;
import com.example.Warehouse.exception.NotFoundException;
import com.example.Warehouse.exception.OutOfStockException;
import com.example.Warehouse.model.Item;
import com.example.Warehouse.model.Variant;
import com.example.Warehouse.repository.ItemRepository;
import com.example.Warehouse.repository.VariantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VariantService {

    private final VariantRepository variantRepository;
    private final ItemRepository itemRepository;

    public VariantService(VariantRepository variantRepository, ItemRepository itemRepository) {
        this.variantRepository = variantRepository;
        this.itemRepository = itemRepository;
    }

    public List<VariantResponse> getVariantsForItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found: " + itemId));

        return item.getVariants().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public VariantResponse getVariant(Long id) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Variant not found: " + id));
        return toResponse(variant);
    }

    public VariantResponse createVariant(Long itemId, VariantRequest request) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found: " + itemId));

        Variant variant = new Variant();
        variant.setItem(item);
        variant.setName(request.getName());
        variant.setPrice(request.getPrice());
        variant.setStockQuantity(request.getStockQuantity());

        Variant saved = variantRepository.save(variant);
        return toResponse(saved);
    }

    public VariantResponse updateVariant(Long id, VariantRequest request) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Variant not found: " + id));

        variant.setName(request.getName());
        variant.setPrice(request.getPrice());
        variant.setStockQuantity(request.getStockQuantity());

        return toResponse(variantRepository.save(variant));
    }

    public void deleteVariant(Long id) {
        if (!variantRepository.existsById(id)) {
            throw new NotFoundException("Variant not found: " + id);
        }
        variantRepository.deleteById(id);
    }

    @Transactional
    public VariantResponse sell(Long variantId, StockOperationRequest request) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new NotFoundException("Variant not found: " + variantId));

        int quantity = request.getQuantity();
        if (variant.getStockQuantity() < quantity) {
            throw new OutOfStockException("Not enough stock. Available: "
                    + variant.getStockQuantity() + ", requested: " + quantity);
        }

        variant.setStockQuantity(variant.getStockQuantity() - quantity);
        return toResponse(variant);
    }

    @Transactional
    public VariantResponse restock(Long variantId, StockOperationRequest request) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new NotFoundException("Variant not found: " + variantId));

        variant.setStockQuantity(variant.getStockQuantity() + request.getQuantity());
        return toResponse(variant);
    }

    private VariantResponse toResponse(Variant variant) {
        VariantResponse resp = new VariantResponse();
        resp.setId(variant.getId());
        resp.setName(variant.getName());
        resp.setPrice(variant.getPrice());
        resp.setStockQuantity(variant.getStockQuantity());
        resp.setItemId(variant.getItem().getId());
        return resp;
    }
}
