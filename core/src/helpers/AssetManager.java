package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.w3c.dom.Text;

public class AssetManager {

    public static Texture sheet;
    // Nau i fons
    public static Texture player, playerLeft, playerRight, background;

    public static Texture enemy;
    // Asteroide
    //public static Texture[] enemy;
    //public static Animation asteroidAnim;

    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;

    // Sons
    public static Sound explosionSound;
    public static Music music;
    // Font
    public static BitmapFont font;
    public static void load() {

        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet = new Texture(Gdx.files.internal("sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        // Sprites de la nau
        player = new Texture(Gdx.files.internal("bucket.png"));

        playerLeft = new Texture(Gdx.files.internal("bucket.png"));

        playerRight = new Texture(Gdx.files.internal("bucket.png"));

        enemy = new Texture(Gdx.files.internal("droplet.png"));
        // Carreguem els 16 estats de l'asteroide
        /*enemy = new Texture[16];
        for (int i = 0; i < enemy.length; i++) {

            String path = "enemy"+i+".png";
            enemy[i] = new Texture(Gdx.files.internal(path));

        }

        // Creem l'animació de l'asteroide i fem que s'executi contínuament en sentit antihorari
        asteroidAnim = new Animation(0.05f, asteroid);
        asteroidAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);*/

        // Creem els 16 estats de l'explosió
        explosion = new TextureRegion[16];

        // Carreguem els 16 estats de l'explosió
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                explosion[index++] = new TextureRegion(sheet, j * 64, i * 64 + 49, 64, 64);
                explosion[index-1].flip(false, true);
            }
        }
        explosionAnim = new Animation(0.05f, explosion);
        explosionAnim.setPlayMode(Animation.PlayMode.NORMAL);

        // Fons de pantalla
        background = new Texture(Gdx.files.internal("background.png"));

        /******************************* Sounds *************************************/
        // Explosió
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        music.setVolume(0.2f);
        music.setLooping(true);

        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/space.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);
    }

    public static void dispose() {
        player.dispose();
        playerRight.dispose();
        playerLeft.dispose();
        background.dispose();
        explosionSound.dispose();
        sheet.dispose();
        enemy.dispose();
        music.dispose();
    }

}