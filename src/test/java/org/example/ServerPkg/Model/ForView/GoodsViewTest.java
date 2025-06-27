package org.example.ServerPkg.Model.ForView;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;

public class GoodsViewTest extends TestCase {

    public void testGetColour() {
        Goods good = new Goods(GoodsColour.YELLOW);

        // 2. Crea una GoodsView passando il Goods
        GoodsView goodsView = new GoodsView(good);

        // 3. Verifica che il colore sia corretto
        assertEquals(GoodsColour.YELLOW, goodsView.getColour());
    }
}