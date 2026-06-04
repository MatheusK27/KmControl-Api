package com.matheus.entidades;

import com.matheus.entidades.entidades.Posto;
import com.matheus.infra.RegraDeNegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PostoEntidadeTest {



    @Test
    void validarPostoAtivo(){
        Posto posto = new Posto();
        posto.setAtivo(false);
        assertThrows(RegraDeNegocioException.class, posto::validarAtivo);
    }

}
