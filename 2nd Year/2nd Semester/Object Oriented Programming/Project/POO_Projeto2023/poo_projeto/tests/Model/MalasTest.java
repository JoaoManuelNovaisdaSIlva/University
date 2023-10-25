package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MalasTest {

    @Test
    void atualizaPrecoDesconto() {
        Transportadora testeT = new Transportadora();
        Vendedor v1 = new Vendedor();
        Malas testeMala1 = new Malas(v1.getId(), true, "Mala usada", 10.50f, 2.50f, Estado.Medio, 2, testeT, 10, Dimenssao.Medio, TexturaMala.Tecido, 2020);
        Malas testeMala2 = new Malas(v1.getId(), false, "Mala Nova", 50.00f, 5.00f, Estado.Bom, 0, testeT, 4, Dimenssao.Grande, TexturaMala.Pele, 2022);
        testeMala1.atualizaPrecoDesconto(2023);
        testeMala2.atualizaPrecoDesconto(2023);
        assertEquals(2.875f, testeMala1.getDicountPrice(), "O novo valor de desconto da mala 1 está errado! Devia ser: 2.875 ; mas foi: " + testeMala1.getDicountPrice());
        assertEquals(5.00f, testeMala2.getDicountPrice(), "O novo valor de desonto da mala 2 está errado! Devia ser: 5.00 ; mas foi: " + testeMala2.getDicountPrice());
    }
}