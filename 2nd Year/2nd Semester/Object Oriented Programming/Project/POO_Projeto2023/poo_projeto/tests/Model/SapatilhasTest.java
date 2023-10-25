package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SapatilhasTest {

    @Test
    void atualizaPrecoDesconto() {
        Transportadora testeT = new Transportadora();
        Vendedor v1 = new Vendedor();
        Sapatilhas s1 = new Sapatilhas(v1.getId(), true, "Sapatilha isada", 10.00f, 3.00f, Estado.Medio, 2, testeT, 5 , 45, true, "white", 2017);
        s1.atualizaPrecoDesconto(2023);
        assertEquals(3.90f, s1.getDicountPrice(), "O novo valor de desonto da sapatilha está errado! Devia ser: 3.90 ; mas foi: " + s1.getDicountPrice());
    }
}