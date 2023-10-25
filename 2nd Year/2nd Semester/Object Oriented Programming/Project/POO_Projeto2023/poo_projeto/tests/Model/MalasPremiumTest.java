package Model;


import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MalasPremiumTest {

    @Test
    void testAtualizaPrecoMalasPremium() {
        ArrayList<Artigos> lista2 = new ArrayList<>();
        Vendedor v1 = new Vendedor();
        TransportadoraPremium transP1 = new TransportadoraPremium("CTT Premium", 777777777, "ctt@premium.ctt.pt", lista2, 1.02f, TipoFormula.Default, "", "");
        MalasPremium mp1 = new MalasPremium(v1.getId(), false, "Mala luis Viton", 400.00f, 20.00f, Estado.Bom, 0, Dimenssao.Grande, TexturaMala.Pele , 2020, transP1, 2, "Pierre");
        mp1.atualizaPrecoMalasPremium(2023);
        assertEquals(640.00f, mp1.getPrice(), "O novo valor da mala está errado! Era esperado 640.00f mas recebi: " + mp1.getPrice());
    }
}