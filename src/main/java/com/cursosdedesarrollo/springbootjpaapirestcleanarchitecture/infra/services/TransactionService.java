package com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.infra.services;

import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoInsertOrUpdate;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.dtos.ProductoView;
import com.cursosdedesarrollo.springbootjpaapirestcleanarchitecture.application.ports.in.AddTransactionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final AddTransactionUseCase addTransactionUseCase;

    @Autowired
    public TransactionService(AddTransactionUseCase addTransactionUseCase) {
        this.addTransactionUseCase = addTransactionUseCase;
    }

    @Transactional
    public ProductoView addTransaction(ProductoInsertOrUpdate productoInsertOrUpdate) {
        return addTransactionUseCase.addTransaction(productoInsertOrUpdate);
    }
}
