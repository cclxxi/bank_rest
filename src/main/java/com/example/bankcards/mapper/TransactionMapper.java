package com.example.bankcards.mapper;

import com.example.bankcards.dto.TransactionDTO;
import com.example.bankcards.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "type", source = "type")
    @Mapping(target = "status", source = "status")
    TransactionDTO toDto(Transaction transaction);
}
