package br.com.sansyro.service;

import br.com.sansyro.entity.Instituition;
import br.com.sansyro.exception.ServiceException;
import br.com.sansyro.util.GeralUtil;

public class InstituitionService {



    public Instituition update(Long id, Instituition instituition) {
        validateNotNull(instituition);
        Instituition instituitionUpdate = Instituition.findById(id);
        instituitionUpdate.setName(instituition.getName());
        instituitionUpdate.setAddress(instituition.getAddress());
        instituitionUpdate.setQuantity(instituition.getQuantity());
        instituitionUpdate.persist();
        //validateDuplicate(instituition.getAddress());
        return instituitionUpdate;
    }

    private void validateNotNull(Instituition instituition) {
        if(GeralUtil.stringNullOrEmpty(instituition.getName())){
            throw new ServiceException("Nome da instituição é obrigatório");
        }
        if(GeralUtil.stringNullOrEmpty(instituition.getAddress())){
            throw new ServiceException("Endereço da instituition é obrigatório");
        }
    }


}
