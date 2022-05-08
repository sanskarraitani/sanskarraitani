import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.util.Random;

public class Enemy extends DynamicEntities implements Entity{


    Enemy(World w,Shape s,int x,int y)
    {
        super(w,s,x,y);
        this.setGravityScale(9.8f);

        this.setFillColor(Color.red);
        this.switchImage(Config.Enemy_Right1);
    }

    private void move(int sp)
    {

            this.startWalking(sp*100);

    }

    @Override
    public void update() {

    }
}
