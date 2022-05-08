import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;


public class Player extends DynamicEntities {
    World world;
    int direction;
    boolean bulletShot=false;
    int bulletcounter=0;
    Player(World w,Shape s,int x,int y)
    {
        super(w,s,x,y);
        world =w;
        this.setFillColor(Color.blue);
        this.setGravityScale(12);

        switchImage(Config.Player_Right1);
    }
    void update()
    {
        if(this.getBodiesInContact().isEmpty())
        {
            isGrounded=false;
        }
    }
    void movePlayer(float sp,boolean shift)
    {
        direction = (int) (sp*100);
        if(shift == true && isGrounded == true)
        {
            this.startWalking(sp*100);
            return;
        }
        this.startWalking(sp);
    }
    void jumpPlayer(float sp,boolean shift)
    {
        if(shift == true )
        {
            this.setLinearVelocity(new Vec2(sp,sp*100));
            return;
        }
        if(isGrounded == true)
        {

            isGrounded=false;

                this.setLinearVelocity(new Vec2(0,500));
                this.jump(sp*100);
        }


    }


}
