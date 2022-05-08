import city.cs.engine.Body;
import city.cs.engine.UserView;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import city.cs.engine.WorldView.*;

import javax.swing.*;
import java.awt.*;

public class Main  {
    GameManager gm;


    Main()
    {
        System.out.println("Starting Game");
         Config.Window = new JFrame(Config.gameName);

        GamePanel gp = new GamePanel(new World(60),Config.sWdith,Config.sHeight,gm);
        Config.Window.setContentPane(gp);
        gp.start(gp.currWorld); // starts game loop
        Config.Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Config.Window.setResizable(false);
        Config.Window.pack();
        Config.Window.setLocationRelativeTo(null);
        Config.Window.setVisible(true);
    }


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new Main();
                    }
                }
        );
    }
}
