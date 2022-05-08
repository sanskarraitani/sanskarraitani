import javax.sound.sampled.*;
import java.io.IOException;

public class MusicPlayer  {

    private  Clip AudioPlayer;
    private  String musicfile;
    private  int isloop=0;
    FloatControl Flc;
    MusicPlayer()  {


    }
    public  void Play(String bgm,float vol)
    {
        System.out.println("Playing");
        musicfile = bgm;
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Playing Music");
                try{
                    AudioPlayer = AudioSystem.getClip();

                    AudioInputStream bgstream=  AudioSystem.getAudioInputStream(Config.class.getResourceAsStream(musicfile));
                    AudioPlayer.open(bgstream);
                    AudioPlayer.start();
                    setVolume(vol);

                }catch (Exception e)
                {
                    System.out.println("Unable To Play Music");
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
    public  void Play(String bgm,int loop,float vol)
    {
        musicfile = bgm;
        isloop=loop;
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    AudioPlayer = AudioSystem.getClip();

                    AudioInputStream bgstream=  AudioSystem.getAudioInputStream(Config.class.getResourceAsStream(musicfile));
                    AudioPlayer.open(bgstream);
                    AudioPlayer.loop(isloop);
                    AudioPlayer.start();
                    setVolume(vol);
                }catch (Exception e)
                {
                    System.out.println("Unable To Play Music");
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
    public void setVolume(float volume)
    {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);

        Flc = (FloatControl) AudioPlayer.getControl(FloatControl.Type.MASTER_GAIN);
        Flc.setValue(20f * (float) Math.log10(volume));//better gain
    }
    public Clip getAudioPlayer()
    {
        return AudioPlayer;
    }
    public boolean isPlaying()
    {
        return AudioPlayer.isOpen();
    }
    public  void Stop()
    {
        System.out.println("Stopping Music");
        AudioPlayer.stop();
        AudioPlayer.flush();
        AudioPlayer.drain();
        AudioPlayer.close();
    }
    public  void Resume()
    {
        AudioPlayer.start();
    }

}
