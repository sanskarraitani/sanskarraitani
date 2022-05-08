import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class StaticEntities extends StaticBody {


    StaticEntities(World w, Shape s,int si,int x,int y,String img)
    {
        super(w,s);
        this.setPosition(new Vec2(x,y));

        BodyImage b_Img =  new BodyImage(img,si*2);
        switchImage(b_Img);
    }
    StaticEntities(World w,int size,int x,int y,String img)
    {
        super(w);
        this.setPosition(new Vec2(x,y));

        BodyImage b_Img =  new BodyImage(img,size*2);
        switchImage(b_Img);
    }
    void switchImage(BodyImage img)
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
