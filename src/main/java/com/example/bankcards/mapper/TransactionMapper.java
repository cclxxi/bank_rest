package com.example.bankcards.mapper;

import com.example.bankcards.dto.TransactionDTO;
import com.example.bankcards.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO toDto(Transaction transaction);
}
