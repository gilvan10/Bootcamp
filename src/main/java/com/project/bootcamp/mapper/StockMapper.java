package com.project.bootcamp.mapper;

import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component //@Component é o pai do @Service, @Repository, @RestController. Não tem uma anotação específica para o Mapper por isso que usa o @Component
public class StockMapper {

    public Stock toEntity(StockDTO dto) {
        Stock stock = new Stock();
        stock.setId(dto.getId());
        stock.setName(dto.getName());
        stock.setPrice(dto.getPrice());
        stock.setVariation(dto.getVariation());
        stock.setDate(dto.getDate());
        return stock;
    }

    public StockDTO toDto(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setName(stock.getName());
        dto.setPrice(stock.getPrice());
        dto.setVariation(stock.getVariation());
        dto.setDate(stock.getDate());
        return dto;
    }

    public List<StockDTO> toDto(List<Stock> listStock) {
        //Transforma a lista numa stream e percorre toda a lista, o map mapeia cada Stock da lista(stream) e vai mandando para o método toDto que só recebe um Stock (Aí está usando sobre carga), depois quando acaba de enviar todos a lista(stream) fica pronta, o collections transforma a lista(stream) em uma coleção, e depois transforma em uma lista(pura) novamente.
        return listStock.stream().map(this::toDto).collect(Collectors.toList());
    }

}
