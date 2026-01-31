package com.example.bankcards.mapper;

import com.example.bankcards.dto.AccountDTO;
import com.example.bankcards.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "status", source = "status")
    AccountDTO toDto(Account account);
}
