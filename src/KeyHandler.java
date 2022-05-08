import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

        boolean leftPressed;
        boolean rightPressed;
        boolean spacePressed;
        boolean shiftPressd;
        boolean enterPressed;
        boolean isShooting;
        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {
            int kCode = e.getKeyCode();

                if( kCode == KeyEvent.VK_A )
                {
                    leftPressed=true;
                }else if( kCode == KeyEvent.VK_D )
                {
                     rightPressed = true;
                }

                 if( kCode == KeyEvent.VK_SPACE )
                {
                    spacePressed=true;
                }
                if( kCode == KeyEvent.VK_SHIFT )
                {
                    shiftPressd=true;
                }
                if( kCode == KeyEvent.VK_ENTER )
                {
                    enterPressed=true;
                }
                if(kCode == KeyEvent.VK_E)
                {
                    isShooting=true;
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int kCode = e.getKeyCode();
            if( kCode == KeyEvent.VK_A )
            {
                leftPressed=false;
            }else if( kCode == KeyEvent.VK_D )
            {
                rightPressed = false;
            }

            if( kCode == KeyEvent.VK_SPACE )
            {
                spacePressed=false;
            }
            if( kCode == KeyEvent.VK_SHIFT )
            {
                shiftPressd=false;
            }
            if( kCode == KeyEvent.VK_ENTER )
            {
                enterPressed=false;
            }
            if(kCode == KeyEvent.VK_E)
            {
                isShooting=false;
            }
        }

}
