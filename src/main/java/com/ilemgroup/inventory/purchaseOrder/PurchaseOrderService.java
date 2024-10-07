package com.ilemgroup.inventory.purchaseOrder;

import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;




@Service
public class PurchaseOrderService {
    
    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cette commande n'existe pas")); 
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cette commande n'existe pas"));
        purchaseOrder.setOrder_id(id);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder patchPurchaseOrder(Long id, Map<String, Object> updates) {
        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseOrderRepository.findById(id);
        if (optionalPurchaseOrder.isPresent()) {
            PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(PurchaseOrder.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, purchaseOrder, value);
                }
            });
            return purchaseOrderRepository.save(purchaseOrder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cette commande n'existe pas");
        }
    }

    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }


}
