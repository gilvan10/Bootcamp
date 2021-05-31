package com.project.bootcamp.service;

import com.project.bootcamp.exceptions.BusinessException;
import com.project.bootcamp.exceptions.NotFoundException;
import com.project.bootcamp.mapper.StockMapper;
import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;
import com.project.bootcamp.repository.StockRepository;
import com.project.bootcamp.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockMapper mapper;

    @Transactional//É aqui que vai abrir uma transação e depois de inserir fecha a transação com o @Transaction. Por exemplo se for inserir em três tabelas dos dados, e em uma delas não conseguir inserir, o @Transactional faz roolbak em todas as tabelas para ficar com boas persistência os dados.
    public StockDTO save(StockDTO dto) {
        //Por padrão só retorna o código 200. Algum código diferenre da família 200 precisa ser tratado.
        //Ajuda nos comandos de checagem (if e else, uma forma mais bonita)
        Optional<Stock> optionalStock = repository.findByNameAndDate(dto.getName(), dto.getDate());
        //Se acontecer entrar dentro desse if é porque já tem um name com uma data especifica e não pode ter dois names iguais com a mesma da data. Automaticamente va chamar a classe ExceptionsHandler(Que vai tratar do código do erro da fámilia 400).
        if(optionalStock.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        //Mapper.toEntity transforma um dto em uma entidade
        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        //Mapper.toDto transforma um stock(entidade) em um dto
        return mapper.toDto(stock);
    }

    @Transactional
    public StockDTO update(StockDTO dto) {//No método update também usa o save do repository
        Optional<Stock> optionalStock = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());
        if(optionalStock.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);
    }

    @Transactional(readOnly = true ) //(readOnly = true ) Assegura que somente é para fazer select neste metodo
    public List<StockDTO> findAll() {
        List<Stock> list = repository.findAll();
        return mapper.toDto(list);
    }

    public StockDTO delete(Long id) {
        StockDTO dto = this.findById(id);
        repository.deleteById(dto.getId());
        return dto;
    }

    @Transactional(readOnly = true )
    public StockDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);

    }

    @Transactional(readOnly = true )
    public List<StockDTO> findByToday() {
        return repository.findByToday(LocalDate.now()).map(mapper::toDto).orElseThrow(NotFoundException::new);

    }
}
