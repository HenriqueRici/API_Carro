package com.carros.domain;

import com.carros.domain.dto.CarroDTO;


import com.carros.domain.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros(){
        List<Carro> carros = rep.findAll();
        List<CarroDTO> list = new ArrayList<>();

        for (Carro c: carros){
            list.add(CarroDTO.create(c));
        }
         return list;
    }

    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = rep.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {
        List<Carro> carros = rep.findByTipo(tipo);
        List<CarroDTO> list = new ArrayList<>();

        for (Carro c: carros){
            list.add(CarroDTO.create(c));
        }
        return list;
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não é possivel inserir o registro");
        return  CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não é possivel atualizar o registro");

        Optional<Carro> optional = rep.findById(id);
        if (optional.isPresent()){
            Carro db = optional.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            rep.save(db);

            return CarroDTO.create(db);
        }else {
            return null;
        }
    }

    public void delete(Long id) {
            rep.deleteById(id);
    }
}
