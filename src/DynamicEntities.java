import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class DynamicEntities extends Walker {
    float hp;
    float Speed=90;
    boolean isGrounded=true;
    DynamicEntities(World w,Shape s,int x,int y)
    {
        super(w,s);
        this.setPosition(new Vec2(x,y));

    }

    public void switchImage(BodyImage img)
    {   //switching images manually for animation since there was no setImage method in game engine library
        if(this.getImages().isEmpty() == false){

            if(this.getImages().get(0).getBodyImage()== img) {
                return;
            }
        }
        this.removeAllImages();
        this.addImage(img);
    }


}
