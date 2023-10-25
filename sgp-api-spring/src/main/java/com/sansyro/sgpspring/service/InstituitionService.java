package com.sansyro.sgpspring.service;

import com.sansyro.sgpspring.entity.Instituition;
import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.repository.InstituitionRepository;
import com.sansyro.sgpspring.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sansyro.sgpspring.constants.StringConstaint.NAME;

@Service
public class InstituitionService {

    @Autowired
    private InstituitionRepository instituitionRepository;

    public List<Instituition> list() {
        return (List<Instituition>) instituitionRepository.findAll(Sort.by(Sort.Direction.ASC, NAME));
    }
    public Page<Instituition> list(Pageable pageable) {
        return instituitionRepository.findAll(pageable);
    }

    public Instituition getById(Long id) {
        Optional<Instituition> instituitionOp = instituitionRepository.findById(id);
        if(instituitionOp.isPresent()) {
            return instituitionOp.get();
        }
        throw new ServiceException("Instituição não encontrada");
    }

    public Instituition save(Instituition instituition) {
        validateNotNull(instituition);
        //validateDuplicate(instituition.getAddress());
        return instituitionRepository.save(instituition);
    }

    public Instituition update(Long id, Instituition instituition) {
        validateNotNull(instituition);
        Instituition instituitionUpdate = getById(id);
        instituitionUpdate.setName(instituition.getName());
        instituitionUpdate.setAddress(instituition.getAddress());
        instituitionUpdate.setQuantity(instituition.getQuantity());
        //validateDuplicate(instituition.getAddress());
        return instituitionRepository.save(instituitionUpdate);
    }

    public void delete(Long id) {
        instituitionRepository.deleteById(id);
    }

    private void validateNotNull(Instituition instituition) {
        if(GeneralUtil.stringNullOrEmpty(instituition.getName())){
            throw new ServiceException("Nome da instituição é obrigatório");
        }
        if(GeneralUtil.stringNullOrEmpty(instituition.getAddress())){
            throw new ServiceException("Endereço da instituition é obrigatório");
        }
    }

}
