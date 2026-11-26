import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/** Verifies rebuilt game classes without running static initializers or starting the game. */
public final class ClassLoadCheck {
    public static void main(String[] args) throws Exception {
        int loaded = 0;
        int failed = 0;
        try (ZipFile zip = new ZipFile(args[0]);
             URLClassLoader loader = new URLClassLoader(new URL[] { new File(args[0]).toURI().toURL() }, null)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                String path = entries.nextElement().getName();
                if (!path.endsWith(".class") || !(path.startsWith("aoc/") || path.startsWith("age/"))) continue;
                String name = path.substring(0, path.length() - 6).replace('/', '.');
                try {
                    Class<?> type = Class.forName(name, false, loader);
                    type.getDeclaredConstructors();
                    type.getDeclaredMethods();
                    type.getDeclaredFields();
                    loaded++;
                } catch (Throwable error) {
                    failed++;
                    System.err.println(name + ": " + error);
                }
            }
        }
        System.out.println("{\"classesLoaded\":" + loaded + ",\"failures\":" + failed + ",\"javaVersion\":\"" + System.getProperty("java.version") + "\",\"initializersRun\":false}");
        if (failed != 0) System.exit(1);
    }
}
