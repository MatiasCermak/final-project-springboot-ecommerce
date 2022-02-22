package com.mcrmk.springboot.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class EntityNotFoundException extends RuntimeException  {

    private final String message;
    private final String entity;
    private final String entityId;

    public EntityNotFoundException(String entity, String entityId){
        this.message = "El objeto " + entity + "con ID " + entityId + " no fue encontrado.";
        this.entityId = entityId;
        this.entity  = entity;
    }

}
