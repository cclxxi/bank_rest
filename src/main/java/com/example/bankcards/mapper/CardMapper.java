package com.example.bankcards.mapper;

import com.example.bankcards.dto.AdminCardSummaryDTO;
import com.example.bankcards.dto.CardDTO;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDTO toDto(Card card);

    @Mapping(target = "cardId", source = "id")
    @Mapping(target = "accountNumber", source = "account.number")
    @Mapping(target = "userId", source = "account.user.id")
    @Mapping(target = "status", source = "status")
    AdminCardSummaryDTO toAdminSummaryDto(Card card);
}
