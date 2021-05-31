package com.project.bootcamp.repository;

import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository //O Stock e o Long estão sinalizando que esse repository é do Stock e Long é a primary key. JpaRepository tem o método inteligente save() que quando é passado o Conjunto de dados com o id, ele interpreta que é uma atualização de dados, quando não passa o id é uma inserção do conjunto de dados
public interface StockRepository extends JpaRepository<Stock, Long> {
    //O jpa entende esse método de forma inteligente, foi criado lá no serviço esse método de forma que atenda a regra de negócio.
    Optional<Stock> findByNameAndDate(String name, LocalDate date);

    //O jpa não entende esse método, por isso que foi criado a @Query e o sql
    @Query("SELECT stock " +
           "FROM Stock stock " +
           "WHERE stock.name = :name AND stock.date = :date AND stock.id <> :id ")
    Optional<Stock> findByStockUpdate(String name, LocalDate date, Long id);

    //CURRENT_DATE pega a data atual diretamente do banco postgres
    @Query("SELECT stock " +
            "FROM Stock stock " +
            "WHERE stock.date = :date ")
    Optional<List<Stock>> findByToday(LocalDate date);
}
