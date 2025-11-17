package com.example.Warehouse.controller;

import com.example.Warehouse.dto.StockOperationRequest;
import com.example.Warehouse.dto.VariantRequest;
import com.example.Warehouse.dto.VariantResponse;
import com.example.Warehouse.service.VariantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VariantController {

    private final VariantService variantService;

    public VariantController(VariantService variantService) {
        this.variantService = variantService;
    }

    @GetMapping("/items/{itemId}/variants")
    public List<VariantResponse> listVariants(@PathVariable Long itemId) {
        return variantService.getVariantsForItem(itemId);
    }

    @PostMapping("/items/{itemId}/variants")
    @ResponseStatus(HttpStatus.CREATED)
    public VariantResponse createVariant(@PathVariable Long itemId,
                                         @Valid @RequestBody VariantRequest request) {
        return variantService.createVariant(itemId, request);
    }

    @GetMapping("/variants/{id}")
    public VariantResponse getVariant(@PathVariable Long id) {
        return variantService.getVariant(id);
    }

    @PutMapping("/variants/{id}")
    public VariantResponse updateVariant(@PathVariable Long id,
                                         @Valid @RequestBody VariantRequest request) {
        return variantService.updateVariant(id, request);
    }

    @DeleteMapping("/variants/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVariant(@PathVariable Long id) {
        variantService.deleteVariant(id);
    }

    @PostMapping("/variants/{id}/sell")
    public VariantResponse sell(@PathVariable Long id,
                                @Valid @RequestBody StockOperationRequest request) {
        return variantService.sell(id, request);
    }

    @PostMapping("/variants/{id}/restock")
    public VariantResponse restock(@PathVariable Long id,
                                   @Valid @RequestBody StockOperationRequest request) {
        return variantService.restock(id, request);
    }
}
