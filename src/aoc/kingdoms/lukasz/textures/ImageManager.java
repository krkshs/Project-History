package aoc.kingdoms.lukasz.textures;

import aoc.kingdoms.lukasz.jakowski.CFG;
import aoc.kingdoms.lukasz.jakowski.FileManager;
import aoc.kingdoms.lukasz.jakowski.Steam.SteamManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

public class ImageManager {
   public static List<Image> images = new ArrayList<>();

   public static final int addImage(String imageName) {
      return addImage(imageName, TextureFilter.Linear);
   }

   public static final int addImage(String imageName, TextureFilter nTextureFilter) {
      return addImage(imageName, Format.RGBA8888, nTextureFilter);
   }

   public static final int addImage(String imageName, Format nFormat, TextureFilter nTextureFilter) {
      images.add(new Image(loadTexture(imageName, nFormat), nTextureFilter));
      return images.size() - 1;
   }

   public static final int addImage(String imageName, Format nFormat, TextureFilter nTextureFilter, TextureWrap nTextureWrap) {
      images.add(new Image(loadTexture(imageName, nFormat), nTextureFilter, nTextureWrap));
      return images.size() - 1;
   }

   public static final Texture loadTexture(String sFile) {
      return loadTexture(sFile, Format.RGBA8888);
   }

   public static final Texture loadTexture_RGB888(String sFile) {
      return loadTexture(sFile, Format.RGB888);
   }

   public static final Texture loadTexture(String sFile, Format nFormat) {
      try {
         try {
            if (CFG.isDesktop()) {
               if (FileManager.IS_MAC) {
                  for (int i = 0; i < SteamManager.modsFoldersSize; i++) {
                     if (Gdx.files.external(SteamManager.modsFolders.get(i) + sFile).exists()) {
                        return new Texture(Gdx.files.external(SteamManager.modsFolders.get(i) + sFile), nFormat, false);
                     }
                  }
               } else {
                  for (int ix = 0; ix < SteamManager.modsFoldersSize; ix++) {
                     if (Gdx.files.internal(SteamManager.modsFolders.get(ix) + sFile).exists()) {
                        return new Texture(Gdx.files.internal(SteamManager.modsFolders.get(ix) + sFile), nFormat, false);
                     }
                  }
               }

               for (int ixx = 0; ixx < SteamManager.itemsInstalledSize; ixx++) {
                  if (Gdx.files.absolute(SteamManager.itemsInstalled.get(ixx).getFolder() + "/" + sFile).exists()) {
                     return new Texture(Gdx.files.absolute(SteamManager.itemsInstalled.get(ixx).getFolder() + "/" + sFile), nFormat, false);
                  }
               }
            }
         } catch (Exception var3) {
            CFG.exceptionStack(var3);
         }

         return FileManager.IS_MAC && Gdx.files.external(sFile).exists()
            ? new Texture(Gdx.files.external(sFile), nFormat, false)
            : new Texture(Gdx.files.internal(sFile), nFormat, false);
      } catch (GdxRuntimeException var4) {
         CFG.exceptionStack(var4);
         return new Texture("gfx/imageNotFound.png");
      }
   }

   public static final Image loadImage(String imageName) {
      return loadImage(imageName, Format.RGBA8888, TextureFilter.Linear);
   }

   public static final Image loadImage(String imageName, Format pixmapFormat) {
      return loadImage(imageName, pixmapFormat, TextureFilter.Linear);
   }

   public static final Image loadImage(String imageName, TextureFilter textureFilter) {
      return loadImage(imageName, Format.RGBA8888, textureFilter);
   }

   public static final Image loadImage(String imageName, Format pixmapFormat, TextureFilter textureFilter) {
      return new Image(loadTexture(imageName, pixmapFormat), textureFilter);
   }

   public static final Image loadImage(String imageName, Format nFormat, TextureFilter nTextureFilter, TextureWrap nTextureWrap) {
      return new Image(loadTexture(imageName, nFormat), nTextureFilter, nTextureWrap);
   }

   public static final ImageRegion loadImageRegion(String imageName, int regionHeight) {
      return loadImageRegion(imageName, Format.RGBA8888, TextureFilter.Linear, regionHeight);
   }

   public static final ImageRegion loadImageRegion(String imageName, Format pixmapFormat, TextureFilter textureFilter, int regionHeight) {
      return new ImageRegion(loadTexture(imageName, pixmapFormat), textureFilter, regionHeight);
   }

   public static final ImageRegion loadImageRegion(String imageName, int regionWidth, int regionHeight) {
      return loadImageRegion(imageName, Format.RGBA8888, TextureFilter.Linear, regionWidth, regionHeight);
   }

   public static final ImageRegion loadImageRegion(String imageName, Format pixmapFormat, int regionWidth, int regionHeight) {
      return loadImageRegion(imageName, pixmapFormat, TextureFilter.Linear, regionWidth, regionHeight);
   }

   public static final ImageRegion loadImageRegion(String imageName, TextureFilter textureFilter, int regionWidth, int regionHeight) {
      return loadImageRegion(imageName, Format.RGBA8888, textureFilter, regionWidth, regionHeight);
   }

   public static final ImageRegion loadImageRegion(String imageName, Format pixmapFormat, TextureFilter textureFilter, int regionWidth, int regionHeight) {
      return new ImageRegion(loadTexture(imageName, pixmapFormat), textureFilter, regionWidth, regionHeight);
   }

   public static final int buildPix() {
      Pixmap nPix = new Pixmap(1, 1, Format.RGBA8888);
      nPix.drawPixel(0, 0, Color.WHITE.toIntBits());
      images.add(new Image(new Texture(nPix)));
      return images.size() - 1;
   }

   public static final Image buildPix_IMG() {
      Pixmap nPix = new Pixmap(1, 1, Format.RGBA8888);
      nPix.drawPixel(0, 0, Color.WHITE.toIntBits());
      return new Image(new Texture(nPix));
   }

   public static final Image getImage(int ID) {
      return images.get(ID);
   }

   public static final int getImagesSize() {
      return images.size();
   }
}
