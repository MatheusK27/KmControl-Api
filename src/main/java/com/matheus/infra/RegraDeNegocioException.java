package com.matheus.infra;

public class RegraDeNegocioException extends  RuntimeException{
    public  RegraDeNegocioException(String mensage){
        super(mensage);
    }
}
