package com.carros.domain;

import com.carros.domain.dto.CarroDTO;
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

    public Optional<CarroDTO> getCarroById(Long id) {
        return rep.findById(id).map(CarroDTO::create);
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

    public boolean delete(Long id) {
        if (getCarroById(id).isPresent()){
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
