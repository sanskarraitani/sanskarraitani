public class GameManager {


    private int player_CoinCollected =0;
    private boolean gameOver =false;
    private boolean gameFinished =false;
    private int player_MaxHealth=100;
    private int player_MaxFuel=100;
    private int currPlayer_Health=0;
    private int currPlayer_fuel=0;
    private boolean isPaused=false;
    private boolean isGameRunning = false;
    private boolean isLvlComplete = false;
    private int curr_Lvl =1;

     public int sCounter=0;
     public int sNum=1;

    //getters
    public int getCoinCollected()
    {
        return player_CoinCollected;
    }
    public int getMaxPlayer_Health()
    {
        return player_MaxHealth;
    }
    public int getMaxPlayer_Fuel()
    {
        return player_MaxFuel;
    }
    public int getCurrPlayer_Fuel()
    {
        return currPlayer_fuel;
    }
    public int getCurrentPlayer_Health()
    {
        return currPlayer_Health;
    }
    public boolean getGameover()
    {
        return gameOver;
    }
    public boolean getGameFinished()
    {
        return gameFinished;
    }
    public boolean getisLvlComplete()
    {
        return isLvlComplete;
    }
    public boolean getisPaused(){
        return isPaused;
    }
    public boolean getisGameRunning(){
        return isGameRunning;
    }
    public int getCurr_Lvl(){
        return curr_Lvl;
    }


    //setters
    public void setCoinCollected(int coins)
    {
        player_CoinCollected = coins;
    }
    public void setCurrPlayer_Health(int hp)
    {
        currPlayer_Health= hp;
    }
    public void setCurrPlayer_fuel(int fuel)
    {
        currPlayer_fuel= fuel;
    }
    public void setGameOver(boolean bool)
    {
        gameOver= bool;
    }
    public void setGameFinished(boolean bool)
    {
        gameFinished= bool;
    }
    public void setisPaused(boolean bool)
    {
        isPaused = bool;
    }
    public void setisLvlComplete(boolean bool)
    {
        isLvlComplete = bool;
    }
    public void setisGameRunning(boolean bool)
    {
        isGameRunning = bool;
    }
    public void setCurr_Lvl(int level)
    {
        curr_Lvl = level;
    }

}
