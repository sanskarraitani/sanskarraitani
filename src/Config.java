import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

abstract class Config {
    //Screen Setting
    final static int oTileSize =16;
    final static int scale=2;
    final static int tileSize = oTileSize * scale;
    //4:3 ratio
    final static int maxCol = 30;
    final static int maxRow = 20;
    final static int sWdith = tileSize * maxCol;
    final static int sHeight = tileSize * maxRow;


    final static String gameName = "Coin Collector";

    final static Font font_UiElement = new Font("Helvetica Neue", Font.BOLD, 25);
    final static Font fontBig_UiElement = new Font("Helvetica Neue", Font.BOLD, 50);

    static JFrame Window = new JFrame();
    //common
    final static String player_Image_Left1 ="res/assets/player_Left_1.png";
    final static String player_Image_Left2 ="res/assets/player_Left_2.png";
    final static String player_Image_Right1 ="res/assets/player_Right_1.png";
    final static String player_Image_Right2 ="res/assets/player_Right_2.png";
    final static String player_Image_Idle ="res/assets/player_idle.png";
    final static String player_Image_Jet_Left ="res/assets/player_jetpack_left.png";
    final static String player_Image_Jet_Right ="res/assets/player_jetpack_right.png";
    final static String enemy_Left1 ="res/assets/player_Left_1.png";
    final static String enemy_Right1 ="res/assets/enemy_right.png";
    final static String jetFuel ="res/assets/jetFuel.png";
    final static String pauseIcon ="res/assets/pauseicon.png";
    final static String restartIcon ="res/assets/restarticon.png";
    final static String saveIcon ="res/assets/saveicon.png";
    final static String loadIcon ="res/assets/loadicon.png";
    final static String gFinish ="res/assets/Finishpoint.png";
    final static String bulletleft ="res/assets/bullet_Left.png";
    final static String bulletright ="res/assets/bullet_Right.png";

    static BufferedImage backgroundImg = null;

    //level 1
    final static String grass_ ="res/assets/grasstile.png";
    final static String grass2_ ="res/assets/grassTile2.png";
    final static String coin_ ="res/assets/Coin1.png";
    final static String coin2_ ="res/assets/Coin2.png";
    final static String platform_Grass_tail ="res/assets/grass_platform.png";
    final static String platform_Grass_mid ="res/assets/grass_platform_mid.png";
    final static String platform_dirt ="res/assets/dirt.png";
    final static String bg = "res/assets/BG.png";
    final static String bg3 = "res/assets/bg3.gif";


    final static BodyImage Player_Left1 = new BodyImage(player_Image_Left1,tileSize*1.5f);
    final static BodyImage Player_Left2 = new BodyImage(player_Image_Left2,tileSize*1.5f);
    final static BodyImage Player_Idle = new BodyImage(player_Image_Idle,tileSize*1.5f);
    final static BodyImage Player_Right1 = new BodyImage(player_Image_Right1,tileSize*1.5f);
    final static BodyImage Player_Right2 = new BodyImage(player_Image_Right2,tileSize*1.5f);
    final static BodyImage Player_Jet_Left = new BodyImage(player_Image_Jet_Left,tileSize*1.5f);
    final static BodyImage Player_Jet_Right = new BodyImage(player_Image_Jet_Right,tileSize*1.5f);
    final static BodyImage Enemy_Right1 = new BodyImage(enemy_Right1,tileSize*1.5f);
    final static BodyImage Enemy_Left1 = new BodyImage(enemy_Left1,tileSize*1.5f);
    final static BodyImage Coin_1 = new BodyImage(coin_,tileSize);
    final static BodyImage Coin_2 = new BodyImage(coin2_,tileSize);
    final static BodyImage Bullet_Left = new BodyImage(bulletleft,tileSize);
    final static BodyImage Bullet_Right = new BodyImage(bulletright,tileSize);

    //Music
    public static String bgm= "assets/backgroundmusic.wav";
    public static String bgm2= "assets/backgroundmusic2.wav";
    public static String bgm3= "assets/backgroundmusic3.wav";
    public static String shoot= "assets/gunShot.wav";
    public static String pickup= "assets/PickUp.wav";
    public static MusicPlayer bgPlayer ;
    public static MusicPlayer sfxPlayer ;

}
