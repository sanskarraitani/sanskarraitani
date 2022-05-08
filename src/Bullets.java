import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

public class Bullets extends DynamicBody {
    boolean bulletHit=false;
    int Speed;
    int counter=0;
    Thread bThread;
    public Bullets(World w, Shape s,int x,int y,int dir) {
        super(w, s);

        this.setBullet(true);
        Speed = dir;
        setGravityScale(9);
        if(dir > 0)
        {
            this.setPosition(new Vec2(x+Config.tileSize*2,y));
            this.addImage(Config.Bullet_Right);


        }else
        {
            this.setPosition(new Vec2(x-Config.tileSize*2,y));

            this.addImage(Config.Bullet_Left);
        }

        System.out.println("Speed:"+this.getLinearVelocity().x);

        this.setGravityScale(0f);
        this.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {
                bulletHit =true;

                if(collisionEvent.getOtherBody().getName()==null)
                {
                    collisionEvent.getOtherBody().destroy();
                    collisionEvent.getReportingBody().destroy();

                    return;
                }
                if(collisionEvent.getOtherBody().getName().equals("Enemy"))
                {
                    collisionEvent.getOtherBody().destroy();
                    collisionEvent.getReportingBody().destroy();
                }
            }
        });
        bThread = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.destroy();
        });
        bThread.start();
    }
    void movebullet()
    {
            this.setLinearVelocity(new Vec2(Speed*500,0));

    }

}
