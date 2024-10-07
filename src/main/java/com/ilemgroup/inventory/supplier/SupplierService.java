package com.ilemgroup.inventory.supplier;

import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class SupplierService {
    
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ce fournisseur n'existe pas"));
    }
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplier) {
            supplierRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ce fournisseur n'existe pas"));
            supplier.setSupplier_id(id);
            return supplierRepository.save(supplier);
    }

    public Supplier patchSupplier(Long id, Map<String, Object> updates) {
            Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ce fournisseur n'existe pas"));
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Supplier.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, supplier, value);
                }
            });
            return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }


}
