import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class GamePanel extends UserView
{
    Player player;
    KeyHandler keyInput = new KeyHandler();
    ArrayList<Coins> coins_List = new ArrayList();
    ArrayList<Enemy> Enemy_List = new ArrayList();
    ArrayList<JetFuel> JetFuel_List = new ArrayList();
    GameManager gm;
    KeyCode kc;
    World currWorld;
    int Wwidth;
    int WHeight;
    Bullets bullet;
    float currVolume;
    JComboBox<String> SelectLvl;
    GamePanel(World wrld,int sWdith,int sHeight,GameManager g)
    {
        super(wrld,sWdith,sHeight);
        gm=g;
        currWorld = wrld;

        gm = new GameManager();
        this.setZoom(1f);
        wrld.setGravity(9f);
        this.setCentre(new Vec2(Config.sWdith/2,Config.sHeight/2));
        this.setFocusable(true); //enables the game to receive KeyInput
        Config.bgPlayer = new MusicPlayer();
        Config.sfxPlayer = new MusicPlayer();
        JButton pause = new JButton(new ImageIcon(Config.pauseIcon));
        pause.setBorderPainted(false);
        pause.setBackground(Color.white);
        pause.setLayout(null);
        pause.setPreferredSize(new Dimension(64,64));
        pause.setFocusable(false);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.setisPaused(!gm.getisPaused());
                if(gm.getisPaused() == false)
                {
                    System.out.println("UnPaused");
                    currWorld.start();
                    backGroundMusicHandler();
                }
            }
        });
        JButton restart = new JButton(new ImageIcon(Config.restartIcon));
        restart.setBorderPainted(false);
        restart.setBackground(Color.white);
        restart.setLayout(null);
        restart.setPreferredSize(new Dimension(64,64));
        restart.setFocusable(false);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetWorld(currWorld);
            }
        });
        JButton Save = new JButton(new ImageIcon(Config.saveIcon));
        Save.setBorderPainted(false);
        Save.setBackground(Color.white);
        Save.setLayout(null);
        Save.setPreferredSize(new Dimension(64,64));
        Save.setFocusable(false);
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteSaveFile("_File",gm.getCurr_Lvl(),gm.getCoinCollected(),gm.getCurrentPlayer_Health(),gm.getCurrPlayer_Fuel(),player.getPosition().x,player.getPosition().y);

            }
        });
        JPanel jpan = new JPanel();
        JButton Load = new JButton(new ImageIcon(Config.loadIcon));
        Load.setBorderPainted(false);
        Load.setBackground(Color.white);
        Load.setLayout(null);
        Load.setPreferredSize(new Dimension(64,64));
        Load.setFocusable(false);
        Load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame Chose_A_File = new JFrame("Chose a File");
                JFileChooser chosefile = new JFileChooser();
                Chose_A_File.requestFocus();

                chosefile.setCurrentDirectory(new java.io.File(".."));
                chosefile.setDialogTitle("Select a save File");
                if(new File("res/assets/Saves").exists() == false)
                {
                    new File("res/assets/Saves").mkdir();

                    System.out.println("New Directory Created");
                }
                chosefile.setCurrentDirectory(new File("res/assets/Saves"));
                chosefile.addActionListener(

                        new ActionListener()
                        {
                            public void actionPerformed(ActionEvent ee)
                            {
                                if(ee.getActionCommand() == JFileChooser.APPROVE_SELECTION)
                                {
                                    String User_SelectedFile =chosefile.getSelectedFile().getAbsolutePath();
                                    System.out.println("File is: "+User_SelectedFile);
                                    getLevel(new File(User_SelectedFile));
                                    Chose_A_File.dispose();


                                }else if (ee.getActionCommand() == JFileChooser.CANCEL_SELECTION)
                                {
                                    Chose_A_File.dispose();
                                }
                            }
                        }
                );
                chosefile.setFileSelectionMode(JFileChooser.FILES_ONLY);
                Chose_A_File.add(chosefile);
                Chose_A_File.setSize(600,400);
                Chose_A_File.setLocationRelativeTo(null);
                Chose_A_File.setResizable(false);
                Chose_A_File.setDefaultCloseOperation(Chose_A_File.DISPOSE_ON_CLOSE);
                Chose_A_File.setVisible(true);
            }
        });
        JSlider volSlider = new JSlider();
        volSlider.setMaximum(10);
        volSlider.setMinimum(0);
        volSlider.setValue(volSlider.getValue()/2);
        volSlider.setSnapToTicks(true);
        volSlider.setOpaque(false);
        volSlider.setLayout(null);
        volSlider.setPreferredSize(new Dimension(150,64));
        volSlider.setFocusable(false);
        currVolume = (float)volSlider.getValue()/10;
        System.out.println(currVolume+" Va "+volSlider.getValue());
        volSlider.addChangeListener(new  ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                 currVolume = volSlider.getValue();

                currVolume = currVolume/10;

                System.out.println("Volume Changed TO: "+currVolume);
                Config.bgPlayer.setVolume(currVolume);

            }
        });

        String[] SelectLevelOptions = {"Level1", "Level2", "Level3"};
         SelectLvl = new JComboBox<>(SelectLevelOptions);
        SelectLvl.setPreferredSize(new Dimension(100,30));

        SelectLvl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.setCurr_Lvl((SelectLvl.getSelectedIndex()+1));
                resetWorld(currWorld);
            }
        });
        SelectLvl.setFocusable(false);
        JPanel temp = new JPanel();
        JPanel temp2 = new JPanel();

        temp.add(pause);
        temp.add(restart);
        temp.add(Save);
        temp.add(Load);
        jpan.setLayout(new BorderLayout());
        temp2.add(SelectLvl);
        temp2.add(volSlider);

        jpan.add(temp, SpringLayout.NORTH);
        jpan.add(temp2, SpringLayout.SOUTH);
        jpan.setOpaque(false);

        this.add(jpan);
    }
    private void WriteSaveFile(String FileName,int Level,int TotalCoins,int hp,int fuel,float posx,float posy)
    {
        System.out.println("Saving Game...");
        int max = 1000;
        int min = 1;
        int range = max - min + 1;
        try
        {
            if(new File("res/assets/Saves").exists() == false)
            {
                new File("res/assets/Saves").mkdir();

                System.out.println("New Directory Created");
            }

                int randomvalue = (int)(Math.random() * range) + min;
                FileName=FileName+""+randomvalue;



            String Q= "";
            PrintWriter writer = new PrintWriter(new FileWriter("res/assets/Saves/Save"+FileName+".txt",true));

                writer.println(Level);//write value line by line in file
                writer.println(TotalCoins);//write value line by line in file
                writer.println(hp);//write value line by line in file
                writer.println(fuel);//write value line by line in file
                writer.println(Integer.valueOf((int) posx));//write value line by line in file
                writer.println(Integer.valueOf((int) posy));//write value line by line in file



            writer.close();
        }catch (Exception e)
        {
            System.out.println("Failed to Create File");
            e.printStackTrace();
        }
    }
    private void getLevel(File Dictionary)  {
        //gets Words from Dictionary
        System.out.println("Loading Game..");
        try
        {
            ArrayList<Integer> lis  = new ArrayList();
            if(Dictionary.exists() == false)
            {
                new File("res/assets/Saves").mkdir();
                System.out.println("New Directory Created");
                PrintWriter writer = new PrintWriter(Dictionary, "UTF-8");
            }

            InputStreamReader isr = new InputStreamReader(new FileInputStream(Dictionary.getAbsoluteFile()));
            BufferedReader br=new BufferedReader(isr);
            String str;
            //store values in arrray
            while((str = br.readLine())!=null){
                if(str.equals("") == false)
                {
                    System.out.println("Got: "+str);
                    int t = Integer.parseInt(str);
                    lis.add(Integer.valueOf(t));
                }
            }
            gm.setCurr_Lvl((int) lis.get(0));
            resetWorld(currWorld);
            gm.setCoinCollected((int) lis.get(1));
            gm.setCurrPlayer_Health((int) lis.get(2));
            gm.setCurrPlayer_fuel((int) lis.get(3));
            player.setPosition(new Vec2( lis.get(4),lis.get(5)));
            br.close();
            isr.close();
        }catch (Exception e)
        {
            System.out.println("--[Error at getting Words] Cant Find File "+Dictionary.getAbsolutePath());
            e.printStackTrace();
        }
    }
    @Override
    public void paintBackground(Graphics2D gc)
    {
        if(gm.getCurr_Lvl()==1)
        {
            try{
                Config.backgroundImg= ImageIO.read(new File(Config.bg));
            }catch (Exception e){
                e.printStackTrace();
            }
                gc.drawImage(Config.backgroundImg,0,0,1200,1200,null);
            //new StaticEntities(currWorld,750,400,0,Config.bg);
        }else if(gm.getCurr_Lvl()==2)
        {
            try{
            Config.backgroundImg= ImageIO.read(new File(Config.bg));
            }catch (Exception e){

            }
            gc.drawImage(Config.backgroundImg,0,250,1200,1200,null);

            new StaticEntities(currWorld,750,400,600,Config.bg);
        }else if(gm.getCurr_Lvl()==3)
        {
            StaticEntities e  = new StaticEntities(currWorld,750,400,600,Config.bg3);
        }
    }
    @Override
    public void paintForeground(Graphics2D gc)
    {
        gc.setColor(Color.BLACK);

        if(gm.getisPaused() == true)
        {

            gc.setFont(Config.fontBig_UiElement);
            gc.drawString("Paused",Config.sWdith/2-100,Config.sHeight/2);

        }
        if(gm.getGameFinished()==true )
        {
            gc.setFont(Config.fontBig_UiElement);
            gc.setColor(Color.WHITE);
            gc.drawString("Game Completed",Config.sWdith/2-180,Config.sHeight/2);
            gc.setFont(Config.font_UiElement);
            gc.drawString("Coins Collected: "+gm.getCoinCollected(),Config.sWdith/2-90,Config.sHeight/2+70);
            gc.drawString("Press Enter to Play Again",Config.sWdith/2-125,Config.sHeight/2+150);
            return;
        }
        if(gm.getGameover()==true )
        {
            gc.setFont(Config.fontBig_UiElement);
            gc.setColor(Color.BLACK);
            gc.drawString("Game Over",Config.sWdith/2-110,Config.sHeight/2);
            gc.setFont(Config.font_UiElement);
            gc.drawString("Coins Collected- "+gm.getCoinCollected(),Config.sWdith/2-90,Config.sHeight/2+70);

            gc.drawString("Press Enter to try Again",Config.sWdith/2-115,Config.sHeight/2+150);
            return;
        }
        gc.setFont(Config.font_UiElement);
        gc.drawString("Level: "+gm.getCurr_Lvl(),10,50);
        gc.drawString("Coins Collected: "+gm.getCoinCollected(),10,100);





        //hp
        gc.setColor(Color.BLACK);
        gc.drawString("HP   ",Config.sWdith-200,30);
        gc.setColor(Color.decode("#DC143C"));
        gc.fillRoundRect(Config.sWdith-150,10, (int) (gm.getMaxPlayer_Health()*1.3f),20,5,5);
        gc.setColor(Color.decode("#32CD32"));
        gc.fillRoundRect(Config.sWdith-150,10, (int) (gm.getCurrentPlayer_Health()*1.3f),20,5,5);
        //fuel
        gc.setColor(Color.BLACK);
        gc.drawString("Fuel",Config.sWdith-220,60);
        gc.setColor(Color.black);
        gc.fillRoundRect(Config.sWdith-150,40, (int) (gm.getMaxPlayer_Fuel()*1.3f),20,5,5);
        gc.setColor(Color.decode("#77883"));
        gc.fillRoundRect(Config.sWdith-150,40, (int) (gm.getCurrPlayer_Fuel()*1.3f),20,5,5);

    }

    void backGroundMusicHandler()
    {
        if(Config.bgPlayer.getAudioPlayer() != null)
        {
            System.out.println("Stopped Music Player");
            Config.bgPlayer.getAudioPlayer().stop();
            Config.bgPlayer.getAudioPlayer().flush();
            Config.bgPlayer.getAudioPlayer().close();
        }

        if(gm.getCurr_Lvl()==1)
        {
            Config.bgPlayer.Play(Config.bgm,-1,currVolume);
        }else if(gm.getCurr_Lvl()==2)
        {
            Config.bgPlayer.Play(Config.bgm2,-1,currVolume);
        }else
        {
            Config.bgPlayer.Play(Config.bgm3,-1,currVolume);
        }


    }
    void start(World world)
    {
        System.out.println("starting new world");
        this.setZoom(1f);
        world.setGravity(12f);
        this.setCentre(new Vec2(Config.sWdith/2,Config.sHeight/2));
        this.setFocusable(true); //enables the game to receive KeyInput

        this.setBackground(Color.black);
        player = new Player(world,new BoxShape(Config.oTileSize-1,Config.oTileSize*1.6f,new Vec2(-5,0)),0,0);

        if(GenerateLevel(world) == false)
        {
            System.out.println("Game Finished");
            gm.setGameFinished(true);
            blankWorld(currWorld);

            return;
        }
        gm.setCurrPlayer_Health(gm.getMaxPlayer_Health());
        if(gm.getisLvlComplete() == true)
        {
            gm.setisLvlComplete(false);
            gm.setCurrPlayer_Health(gm.getCurrentPlayer_Health());
        }else
        {
            gm.setCurrPlayer_Health(gm.getMaxPlayer_Health());

        }

       //
        backGroundMusicHandler();
        gm.setCurrPlayer_fuel(gm.getMaxPlayer_Fuel());
        player.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent collisionEvent) {

                if(collisionEvent.getOtherBody().getName()==null)
                {
                    return;
                }
                if(collisionEvent.getOtherBody().getName().equals("Coins"))
                {

                    int finalI = coins_List.indexOf(collisionEvent.getOtherBody());
                    coins_List.get(finalI).destroy();
                    gm.setCoinCollected(gm.getCoinCollected()+1);
                    coins_List.remove(finalI);
                    Config.sfxPlayer.Play(Config.pickup,currVolume);
                }
                if(collisionEvent.getOtherBody().getName().equals("Fuel"))
                {

                    int finalI = JetFuel_List.indexOf(collisionEvent.getOtherBody());
                    JetFuel_List.get(finalI).destroy();
                    JetFuel_List.remove(finalI);
                    gm.setCurrPlayer_fuel(100);
                    Config.sfxPlayer.Play(Config.pickup,currVolume);
                }
                if(collisionEvent.getOtherBody().getName().equals("Enemy"))
                {
                        gm.setCurrPlayer_Health(gm.getCurrentPlayer_Health()-10);
                }
                if(collisionEvent.getOtherBody().getName().equals("Tiles"))
                {
                    player.isGrounded=true;
                }

                if(collisionEvent.getOtherBody().getName().equals("FinishPlatform")){

                    System.out.println("Level Finished");
                    player.destroy();
                    gm.setCurr_Lvl(gm.getCurr_Lvl()+1);

                    resetWorld(world);

                }

            }
        });


        this.addKeyListener(keyInput); //gets KeyInput



        gameLoop(currWorld);

    }
    boolean GenerateLevel(World world)
    {
        char[][] terrain = TerrainHandler.getTerrian(gm.getCurr_Lvl());

        if(terrain == null)
        {
            System.out.println("Reached End Of levels");
            return false;
        }
        int col=0;//table cord
        int row=0;
        int x=0;//world cords
        int y=0;
        int Worldcol=terrain[0].length;
        int worldRow=terrain.length;
        int s = Config.oTileSize;


        while (col < Worldcol && row < worldRow)
        {
            if(terrain[row][col] == 'p')
            {
                player.setName("Player");
                player.setPosition(new Vec2(x+Config.tileSize,y+Config.tileSize));
//                player.hp = gm.getMaxPlayer_Health();
            }

            if(terrain[row][col] == 'e')
            {
               Enemy e = new Enemy(world,new BoxShape(s-1,s*1.6f,new Vec2(0,1)),x,y);
               e.setName("Enemy");
                Enemy_List.add(e);
            }
            if(terrain[row][col] == 'c')
            {
                Coins c = new Coins(world,new BoxShape(s,s,new Vec2()),s,x+s,y+s,Config.coin_);
                c.setName("Coins");
                coins_List.add(c);
            }
            if(terrain[row][col] == 'm')
            {
                StaticEntities se =new StaticEntities(world,new BoxShape(s,s-7.5f,new Vec2()),s,x+s,y+s,Config.platform_Grass_mid);
                se.setName("Tiles");

            }
            if(terrain[row][col] == 1)
            {
                StaticEntities se = new StaticEntities(world,new BoxShape(s,s-7.5f,new Vec2()),s,x+s,y+s,Config.grass_);
                se.setName("Tiles");

            }
            if(terrain[row][col] == 2)
            {
                StaticEntities se = new StaticEntities(world,new BoxShape(s,s-7.5f,new Vec2()),s,x+s,y+s,Config.grass2_);
                se.setName("Tiles");

            }
            if(terrain[row][col] == 't')
            {
                StaticEntities se = new StaticEntities(world,new BoxShape(s,s-7.5f,new Vec2()),s,x+s,y+s,Config.platform_Grass_tail);
                se.setName("Tiles");
            }
            if(terrain[row][col] == 'd')
            {
               StaticEntities se =  new StaticEntities(world,new BoxShape(s,s,new Vec2()),s,x+s,y+s,Config.platform_dirt);
               se.setName("Tiles");
            }
            if(terrain[row][col] == 'f')
            {
                JetFuel jf = new JetFuel(world,new BoxShape(s,s,new Vec2()),s,x+s,y+s,Config.jetFuel);
                jf.setName("Fuel");
                JetFuel_List.add(jf);
            }
            if(terrain[row][col] == 'n')
            {

                StaticEntities finishPlatform = new StaticEntities(world,new BoxShape(Config.oTileSize,Config.oTileSize,new Vec2(Config.tileSize,0)),Config.oTileSize*2,x,y+s,Config.gFinish);
                finishPlatform.setName("FinishPlatform");
            }
            col++;
            x+= Config.tileSize;
            if(col == Worldcol)
            {
                col=0;
                x=0;
                row++;
                y+= Config.tileSize;
            }
        }
        Wwidth = Worldcol*Config.tileSize;
        WHeight = worldRow*Config.tileSize;



        return  true;
    }

    private void resetWorld(World world)
    {
        world.stop();
        Enemy_List.clear();
        coins_List.clear();
        JetFuel_List.clear();
        player = null;

        currWorld = new World(60);
        setWorld(currWorld);
        start(currWorld);
    }
    private void blankWorld(World world)
    {

        world.stop();
        player=null;
        currWorld = new World(60);
        setWorld(currWorld);
        currWorld.start();
        this.setZoom(1f);
        world.setGravity(12f);
        this.setCentre(new Vec2(Config.sWdith/2,Config.sHeight/2));
        this.setFocusable(true); //enables the game to receive KeyInput
        this.setBackground(Color.black);
        this.addKeyListener(keyInput); //gets KeyInput
        currWorld.addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent stepEvent) {
                if(keyInput.enterPressed==true)
                {
                    if(gm.getGameFinished()==true)
                    {
                        gm.setCurr_Lvl(1);
                        gm.setGameFinished(false);
                    }
                    gm.setCoinCollected(0);

                    gm.setGameOver(false);
                    resetWorld(currWorld);
                }
            }
            @Override
            public void postStep(StepEvent stepEvent) {

            }
        });
    }
    public void gameLoop(World world) {
        world.start();
        gm.setisGameRunning(true);
        world.addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent stepEvent) {
                if(gm.getGameover()==true)
                {
                    System.out.println("game over");
                }
            }
            @Override
            public void postStep(StepEvent stepEvent) {

                if(gm.getisGameRunning() == false)
                {
                    return;
                }
                if(gm.getisPaused())
                {
                    world.stop();
                    Config.bgPlayer.Stop();
                }
                if(gm.getGameover() == true )
                {
                    currWorld.stop();
                    System.out.println("Game Over");
                    return;
                    //gameover
                }
                update();
            }
        });
    }

    private void update()
    {
        if(gm.getGameFinished()==true)
        {
            System.out.println("Game Finished");
            return;
        }
        if(gm.getCurrentPlayer_Health() <=0)
        {
            if(gm.getGameover()==false)
            {
                gm.setGameOver(true);
                blankWorld(currWorld);
            }
            return;
        }
        player.update();
        if(keyInput.isShooting)
        {
            if(player.bulletShot==false )
            {

                bullet = new Bullets(currWorld,new BoxShape(10,5),(int)player.getPosition().x,(int)player.getPosition().y, player.direction);
                player.bulletShot=true;
                Config.sfxPlayer.Play(Config.shoot,currVolume);

                    player.bulletcounter++;

            }
        }
        if(bullet != null)
        {
            bullet.movebullet();

            player.bulletcounter++;
            if(player.bulletcounter > 100)
            {
                player.bulletShot = false;
                player.bulletcounter=0;
            }

            if(bullet.getPosition().x < 0 || bullet.getPosition().y < 0)
            {
                bullet.destroy();
                bullet = null;
                return;
            }else if(bullet.getPosition().x > Wwidth || bullet.getPosition().y > WHeight )
            {
                bullet.destroy();
                bullet = null;
                return;
            }
            if(bullet.bulletHit==true)
            {
                bullet.destroy();
                bullet = null;
            }
        }else if(bullet == null)
        {
            player.bulletShot=false;
        }
        if(this.getCentre().x != player.getPosition().x && this.getCentre().y != player.getPosition().y)
        {
            if(player.getPosition().x > 480  && player.getPosition().x < 895)
            {
                this.setCentre(new Vec2(player.getPosition().x,Config.sHeight/2));
            }
        }

        if(keyInput.leftPressed)
        {

            if(player.getPosition().x <= 0)
            {
                player.movePlayer(player.Speed, keyInput.shiftPressd);
                return;
            }
            kc = kc.VK_A;
            player.movePlayer(-player.Speed, keyInput.shiftPressd);
            if(gm.sNum == 1 && player.isGrounded==true)
            {
                player.switchImage(Config.Player_Left1);
            }else
            {
                player.switchImage(Config.Player_Left2);

            }
        }else if(keyInput.rightPressed)
        {
            if(player.getPosition().x >= Wwidth)
            {
                player.movePlayer(-player.Speed, keyInput.shiftPressd);
                return;
            }
            kc = kc.VK_D;
            player.movePlayer(player.Speed, keyInput.shiftPressd);
            if(gm.sNum == 1 && player.isGrounded == true)
            {
                player.switchImage(Config.Player_Right1);
            }else
            {
                player.switchImage(Config.Player_Right2);
            }
        } else
        {
            player.stopWalking();
            player.setLinearVelocity(new Vec2(0,-player.getMass()));
            player.switchImage(Config.Player_Idle);
        }
        if(keyInput.spacePressed)
        {
            if(keyInput.shiftPressd)
            {
                gm.setCurrPlayer_fuel(gm.getCurrPlayer_Fuel()-1);
                if(gm.getCurrPlayer_Fuel() < 0)
                {
                    return;
                }
                if(kc == kc.VK_A)
                {
                    player.switchImage(Config.Player_Jet_Left);
                }else if(kc == kc.VK_D)
                {
                    player.switchImage(Config.Player_Jet_Right);
                }else
                {
                    player.switchImage(Config.Player_Jet_Right);

                }
            }

            player.jumpPlayer(player.Speed, keyInput.shiftPressd);
        }
        //sprite animation counter
        gm.sCounter++;
        if(gm.sCounter > 5)
        {
            if(gm.sNum == 1){
                gm.sNum=2;
            }else if(gm.sNum == 2)
            {
                gm.sNum=1;
            }
            gm.sCounter=0;
        }
        for(Enemy e : Enemy_List)
        {


            if(e.getBodiesInContact().isEmpty() == false)
            {
                int min=0;
                int max=6;
                int randomeMove = (int)Math.floor(Math.random()*(max-min+1)+min);
                if(randomeMove == 0){
                    e.startWalking(-100);
                }else if(randomeMove ==1)
                {
                    e.jump(100);
                }else if(randomeMove == 2)
                {
                    e.startWalking(100);
                }else
                {
                    e.stopWalking();
                }
                if(e.getPosition().x <=0)
                {
                    e.stopWalking();
                    e.startWalking(100);
                }
                if(e.getPosition().x >= Wwidth)
                {
                    e.stopWalking();
                    e.startWalking(-100);
                }
            }
        }
    }

}
