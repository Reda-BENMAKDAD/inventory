package com.ilemgroup.inventory.purchaseOrder;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/api/v1/purchaseorder")
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderService.getPurchaseOrderById(id);
    }

    public ResponseEntity<PurchaseOrder> createPurchaseOrder(PurchaseOrder purchaseOrder) {
         PurchaseOrder createdPurchaseOrder = purchaseOrderService.createPurchaseOrder(purchaseOrder);
         return new ResponseEntity<>(createdPurchaseOrder, HttpStatus.CREATED);
    }

    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(Long id, PurchaseOrder purchaseOrder) {
        PurchaseOrder updatedPurchaseOrder =  purchaseOrderService.updatePurchaseOrder(id, purchaseOrder);
        return ResponseEntity.ok(updatedPurchaseOrder);
    }

    public ResponseEntity<PurchaseOrder> patchPurchaseOrder(Long id, Map<String, Object> updates) {
        PurchaseOrder patchedPurchaseOrder = purchaseOrderService.patchPurchaseOrder(id, updates);
        return ResponseEntity.ok(patchedPurchaseOrder);
    }

    public void deletePurchaseOrder(Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
    }
}
