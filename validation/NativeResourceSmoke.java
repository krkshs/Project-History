import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/** Checks packaged native libraries and graphics resources in a hidden window. */
public final class NativeResourceSmoke {
    private static Throwable failure;
    private static String version;
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(320, 240);
        config.setInitialVisible(false);
        config.disableAudio(true);
        new Lwjgl3Application(new ApplicationAdapter() {
            @Override public void create() {
                try {
                    version = Gdx.gl.glGetString(GL20.GL_VERSION);
                    Texture icon = new Texture(new FileHandle("gfx/icon/icon_16x16.png"));
                    icon.dispose();
                    Pixmap mask = PixmapIO.readCIM(new FileHandle("map/Earth3/data/scales/1/0"));
                    mask.dispose();
                    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(new FileHandle("game/fonts/Roboto-Regular.ttf"));
                    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                    parameter.size = 16;
                    BitmapFont font = generator.generateFont(parameter);
                    font.dispose();
                    generator.dispose();
                    ShaderProgram shader = new ShaderProgram(new FileHandle("game/shader/default_vertex.glsl"), new FileHandle("game/shader/default_fragment.glsl"));
                    if (!shader.isCompiled()) throw new IllegalStateException(shader.getLog());
                    shader.dispose();
                } catch (Throwable error) { failure = error; }
                finally { Gdx.app.exit(); }
            }
        }, config);
        if (failure != null) { failure.printStackTrace(); System.exit(1); }
        System.out.println("{\"openGL\":\"" + version.replace("\\", "\\\\").replace("\"", "\\\"") + "\",\"pngLoaded\":true,\"binaryPixmapLoaded\":true,\"freeTypeFontGenerated\":true,\"shaderCompiled\":true,\"windowVisible\":false}");
    }
}
