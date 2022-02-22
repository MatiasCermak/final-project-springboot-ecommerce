package com.mcrmk.springboot.ecommerce.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException  {

    private final String message;
    private final String entity;
    private final String entityId;

    public EntityNotFoundException(String entity, String entityId){
        super("El objeto " + entity + "con ID " + entityId + " no fue encontrado.");
        this.message = "El objeto " + entity + "con ID " + entityId + " no fue encontrado.";
        this.entityId = entityId;
        this.entity  = entity;
    }

}
