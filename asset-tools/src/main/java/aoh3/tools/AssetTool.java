package aoh3.tools;

import age.of.civilizations2.jakowski.lukasz.Civilizations.CivTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import javax.imageio.ImageIO;

/** Converts the game's binary assets to editable files and compiles edits back. */
public final class AssetTool {
    private static final Gson JSON = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    private static final String TEMPLATE_CLASS = CivTemplate.class.getName();
    private static final int[] BYTES_PER_PIXEL = {0, 1, 2, 3, 4, 2, 2};

    public static final class Entry {
        String kind;
        String source;
        String output;
        String sourceSha256;
        String originalSha256;
        String payloadSha256;
        int width;
        int height;
        int format;
    }
    public static final class Manifest {
        int version = 1;
        String byteOrder = "little-endian (original Windows pixmaps)";
        List<Entry> entries = new ArrayList<Entry>();
    }
    private static final class Pixmap {
        int width, height, format;
        byte[] pixels;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) throw new IllegalArgumentException("Usage: AssetTool export|verify <assets> <asset-src>, or compile <asset-src> <distribution>");
        Path first = Paths.get(args[1]).toAbsolutePath().normalize();
        Path second = Paths.get(args[2]).toAbsolutePath().normalize();
        ImageIO.setUseCache(false);
        if ("export".equals(args[0])) exportAssets(first, second);
        else if ("compile".equals(args[0])) compileAssets(first, second);
        else if ("verify".equals(args[0])) verifyAssets(first, second);
        else if ("verify-edits".equals(args[0])) verifyCompiledEdits(first, second);
        else throw new IllegalArgumentException("Unknown command: " + args[0]);
    }

    private static Path resolve(Path root, String relative) throws IOException {
        Path path = root.resolve(relative).normalize();
        if (!path.startsWith(root)) throw new IOException("Asset path leaves its root: " + relative);
        return path;
    }
    private static String relative(Path root, Path path) { return root.relativize(path).toString().replace('\\', '/'); }
    private static String hex(byte[] data) {
        StringBuilder value = new StringBuilder(data.length * 2);
        for (byte b : data) value.append(String.format(Locale.ROOT, "%02x", b & 255));
        return value.toString();
    }
    private static String hash(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream in = Files.newInputStream(path)) {
            byte[] buffer = new byte[32768];
            int n;
            while ((n = in.read(buffer)) != -1) digest.update(buffer, 0, n);
        }
        return hex(digest.digest());
    }
    private static byte[] payload(Pixmap map) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(bytes)) {
            out.writeInt(map.width); out.writeInt(map.height); out.writeInt(map.format); out.write(map.pixels);
        }
        return bytes.toByteArray();
    }
    private static Pixmap readCim(Path path) throws IOException {
        Pixmap map = new Pixmap();
        try (DataInputStream in = new DataInputStream(new InflaterInputStream(new BufferedInputStream(Files.newInputStream(path))))) {
            map.width = in.readInt(); map.height = in.readInt(); map.format = in.readInt();
            if (map.width <= 0 || map.height <= 0 || map.format < 1 || map.format > 6) throw new IOException("Invalid pixmap header: " + path);
            long size = (long) map.width * map.height * BYTES_PER_PIXEL[map.format];
            if (size > 256L * 1024 * 1024) throw new IOException("Pixmap too large: " + path);
            map.pixels = new byte[(int) size];
            in.readFully(map.pixels);
            if (in.read() != -1) throw new IOException("Extra data after pixmap pixels: " + path);
        }
        return map;
    }
    private static BufferedImage toPng(Pixmap map) {
        int[] argb = new int[map.width * map.height];
        ByteBuffer bytes = ByteBuffer.wrap(map.pixels).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < argb.length; i++) {
            int r = 255, g = 255, b = 255, a = 255;
            switch (map.format) {
                case 1: a = bytes.get() & 255; break;
                case 2: r = g = b = bytes.get() & 255; a = bytes.get() & 255; break;
                case 3: r = bytes.get() & 255; g = bytes.get() & 255; b = bytes.get() & 255; break;
                case 4: r = bytes.get() & 255; g = bytes.get() & 255; b = bytes.get() & 255; a = bytes.get() & 255; break;
                case 5: {
                    int value = bytes.getShort() & 65535;
                    int rr = value >>> 11, gg = (value >>> 5) & 63, bb = value & 31;
                    r = (rr << 3) | (rr >>> 2); g = (gg << 2) | (gg >>> 4); b = (bb << 3) | (bb >>> 2); break;
                }
                case 6: {
                    int value = bytes.getShort() & 65535;
                    r = ((value >>> 12) & 15) * 17; g = ((value >>> 8) & 15) * 17;
                    b = ((value >>> 4) & 15) * 17; a = (value & 15) * 17; break;
                }
                default: throw new IllegalArgumentException("Invalid format");
            }
            argb[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }
        BufferedImage image = new BufferedImage(map.width, map.height, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, map.width, map.height, argb, 0, map.width);
        return image;
    }
    private static Pixmap fromPng(Path path, int format) throws IOException {
        BufferedImage image = ImageIO.read(path.toFile());
        if (image == null) throw new IOException("Invalid PNG: " + path);
        Pixmap map = new Pixmap();
        map.width = image.getWidth(); map.height = image.getHeight(); map.format = format;
        map.pixels = new byte[Math.multiplyExact(Math.multiplyExact(map.width, map.height), BYTES_PER_PIXEL[format])];
        ByteBuffer bytes = ByteBuffer.wrap(map.pixels).order(ByteOrder.LITTLE_ENDIAN);
        int[] argb = image.getRGB(0, 0, map.width, map.height, null, 0, map.width);
        for (int value : argb) {
            int a = value >>> 24, r = (value >>> 16) & 255, g = (value >>> 8) & 255, b = value & 255;
            switch (format) {
                case 1: bytes.put((byte) a); break;
                case 2: bytes.put((byte) r).put((byte) a); break;
                case 3: bytes.put((byte) r).put((byte) g).put((byte) b); break;
                case 4: bytes.put((byte) r).put((byte) g).put((byte) b).put((byte) a); break;
                case 5: bytes.putShort((short) (((r >>> 3) << 11) | ((g >>> 2) << 5) | (b >>> 3))); break;
                case 6: bytes.putShort((short) (((r >>> 4) << 12) | ((g >>> 4) << 8) | ((b >>> 4) << 4) | (a >>> 4))); break;
                default: throw new IOException("Unsupported format: " + format);
            }
        }
        return map;
    }
    private static CivTemplate readTemplate(Path path) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path)) {
            @Override protected Class<?> resolveClass(ObjectStreamClass descriptor) throws IOException, ClassNotFoundException {
                String name = descriptor.getName();
                if (!TEMPLATE_CLASS.equals(name) && !"java.util.ArrayList".equals(name) && !"java.lang.Integer".equals(name) && !"java.lang.Number".equals(name)) {
                    throw new InvalidClassException("Unexpected template class", name);
                }
                return super.resolveClass(descriptor);
            }
        }) {
            Object value = in.readUnshared();
            if (!(value instanceof CivTemplate)) throw new IOException("Not a CivTemplate: " + path);
            return (CivTemplate) value;
        }
    }
    private static CivTemplate readTemplateJson(Path path) throws IOException {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            CivTemplate value = JSON.fromJson(reader, CivTemplate.class);
            if (value == null || value.tag == null || value.year == null || value.provinces == null) throw new IOException("Missing template lists: " + path);
            if (value.tag.size() != value.year.size() || value.tag.size() != value.provinces.size()) throw new IOException("Template tag/year/provinces lengths differ: " + path);
            return value;
        }
    }
    private static void writeJson(Path path, Object value) throws IOException {
        Files.createDirectories(path.getParent());
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) { JSON.toJson(value, writer); }
    }
    private static Manifest manifest(Path sourceRoot) throws IOException {
        try (Reader reader = Files.newBufferedReader(sourceRoot.resolve("manifest.json"), StandardCharsets.UTF_8)) {
            Manifest manifest = JSON.fromJson(reader, Manifest.class);
            if (manifest == null || manifest.version != 1 || manifest.entries == null) throw new IOException("Unsupported asset manifest");
            return manifest;
        }
    }
    private static void exportAssets(final Path assets, final Path sourceRoot) throws Exception {
        Files.createDirectories(sourceRoot);
        final List<Path> inputs;
        try (Stream<Path> paths = Files.walk(assets.resolve("map"))) {
            inputs = paths.filter(Files::isRegularFile).filter(p -> {
                String path = relative(assets, p);
                return path.contains("/data/scales/") || path.contains("/civs_template/");
            }).filter(p -> !p.getFileName().toString().contains(".")).sorted().collect(Collectors.toList());
        }
        final List<Entry> entries = Collections.synchronizedList(new ArrayList<Entry>());
        final AtomicInteger done = new AtomicInteger();
        ForkJoinPool pool = new ForkJoinPool(Math.min(4, Runtime.getRuntime().availableProcessors()));
        try {
            pool.submit(() -> inputs.parallelStream().forEach(path -> {
                try {
                    Entry entry = new Entry();
                    entry.output = relative(assets, path);
                    entry.originalSha256 = hash(path);
                    Path output;
                    if (entry.output.contains("/civs_template/")) {
                        entry.kind = "civ-template"; entry.source = entry.output + ".json";
                        output = resolve(sourceRoot, entry.source);
                        writeJson(output, readTemplate(path));
                    } else {
                        entry.kind = "cim"; entry.source = entry.output + ".png";
                        Pixmap map = readCim(path);
                        entry.width = map.width; entry.height = map.height; entry.format = map.format;
                        entry.payloadSha256 = hex(MessageDigest.getInstance("SHA-256").digest(payload(map)));
                        output = resolve(sourceRoot, entry.source);
                        Files.createDirectories(output.getParent());
                        if (!ImageIO.write(toPng(map), "png", output.toFile())) throw new IOException("PNG writer unavailable");
                    }
                    entry.sourceSha256 = hash(output);
                    entries.add(entry);
                    int n = done.incrementAndGet();
                    if (n % 2000 == 0) System.out.println("Decoded " + n + "/" + inputs.size() + " binary assets");
                } catch (Exception error) { throw new CompletionException("Failed to decode " + path, error); }
            })).get();
        } finally { pool.shutdown(); }
        Manifest manifest = new Manifest();
        manifest.entries.addAll(entries);
        manifest.entries.sort(Comparator.comparing(e -> e.source));
        writeJson(sourceRoot.resolve("manifest.json"), manifest);
        long png = entries.stream().filter(e -> "cim".equals(e.kind)).count();
        System.out.println("Decoded " + png + " pixmaps to PNG and " + (entries.size() - png) + " civilization templates to JSON.");
    }
    private static void compileAssets(Path sourceRoot, Path distribution) throws Exception {
        Manifest manifest = manifest(sourceRoot);
        Set<String> known = new HashSet<String>();
        for (Entry entry : manifest.entries) known.add(entry.source);
        try (Stream<Path> paths = Files.walk(sourceRoot.resolve("map"))) {
            for (Path path : paths.filter(Files::isRegularFile).sorted().collect(Collectors.toList())) {
                String relative = relative(sourceRoot, path);
                if (known.contains(relative)) continue;
                Entry entry = new Entry();
                entry.source = relative;
                if (relative.matches("map/[^/]+/data/scales/[^/]+/[^/]+\\.png")) {
                    entry.kind = "cim"; entry.format = 1; entry.output = relative.substring(0, relative.length() - 4);
                } else if (relative.matches("map/[^/]+/civs_template/[^/]+\\.json")) {
                    entry.kind = "civ-template"; entry.output = relative.substring(0, relative.length() - 5);
                } else continue;
                manifest.entries.add(entry);
            }
        }
        int changed = 0, unchanged = 0;
        Set<String> generated = new TreeSet<String>();
        for (Entry entry : manifest.entries) {
            Path input = resolve(sourceRoot, entry.source);
            if (!Files.exists(input)) throw new IOException("Decoded asset is missing: " + entry.source);
            Path output = resolve(distribution, entry.output);
            if (entry.sourceSha256 != null && entry.sourceSha256.equals(hash(input))) { unchanged++; continue; }
            Files.createDirectories(output.getParent());
            if ("cim".equals(entry.kind)) {
                Pixmap map = fromPng(input, entry.format);
                try (DataOutputStream out = new DataOutputStream(new DeflaterOutputStream(Files.newOutputStream(output)))) {
                    out.writeInt(map.width); out.writeInt(map.height); out.writeInt(map.format); out.write(map.pixels);
                }
            } else if ("civ-template".equals(entry.kind)) {
                try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(output))) { out.writeObject(readTemplateJson(input)); }
            } else throw new IOException("Unsupported asset kind: " + entry.kind);
            changed++;
            generated.add(entry.output);
        }
        Files.createDirectories(distribution);
        Path tracking = distribution.resolve(".compiled-files.json");
        if (Files.isRegularFile(tracking)) {
            String[] previous;
            try (Reader reader = Files.newBufferedReader(tracking, StandardCharsets.UTF_8)) { previous = JSON.fromJson(reader, String[].class); }
            if (previous != null) for (String path : previous) if (!generated.contains(path)) Files.deleteIfExists(resolve(distribution, path));
        }
        writeJson(tracking, generated);
        Map<String, Object> report = new LinkedHashMap<String, Object>();
        report.put("decodedAssets", manifest.entries.size()); report.put("recompiled", changed); report.put("preservedOriginalBytes", unchanged);
        writeJson(distribution.getParent().resolve("asset-build-report.json"), report);
        System.out.println("Assets: compiled " + changed + " edits, preserved " + unchanged + " unchanged binary files.");
    }
    private static void verifyAssets(Path assets, Path sourceRoot) throws Exception {
        Manifest manifest = manifest(sourceRoot);
        int done = 0;
        for (Entry entry : manifest.entries) {
            Path source = resolve(sourceRoot, entry.source);
            if ("cim".equals(entry.kind)) {
                String actual = hex(MessageDigest.getInstance("SHA-256").digest(payload(fromPng(source, entry.format))));
                if (!actual.equals(entry.payloadSha256)) throw new IOException("PNG round trip changed pixels: " + entry.source);
            } else {
                CivTemplate original = readTemplate(resolve(assets, entry.output));
                CivTemplate rebuilt = readTemplateJson(source);
                if (!JSON.toJsonTree(original).equals(JSON.toJsonTree(rebuilt))) throw new IOException("JSON round trip changed template: " + entry.source);
            }
            if (++done % 5000 == 0) System.out.println("Verified " + done + "/" + manifest.entries.size());
        }
        Map<String, Object> report = new LinkedHashMap<String, Object>();
        report.put("verified", done); report.put("failures", 0); report.put("comparison", "Exact pixmap payloads and civilization-template values");
        writeJson(sourceRoot.resolve("roundtrip-report.json"), report);
        System.out.println("Verified " + done + " editable assets without data loss.");
    }

    private static void verifyCompiledEdits(Path sourceRoot, Path distribution) throws Exception {
        Manifest manifest = manifest(sourceRoot);
        int verified = 0;
        for (Entry entry : manifest.entries) {
            Path source = resolve(sourceRoot, entry.source);
            if (entry.sourceSha256.equals(hash(source))) continue;
            Path output = resolve(distribution, entry.output);
            if ("cim".equals(entry.kind)) {
                if (!Arrays.equals(payload(fromPng(source, entry.format)), payload(readCim(output)))) throw new IOException("Compiled PNG pixels differ: " + entry.source);
            } else {
                if (!JSON.toJsonTree(readTemplateJson(source)).equals(JSON.toJsonTree(readTemplate(output)))) throw new IOException("Compiled template values differ: " + entry.source);
            }
            verified++;
        }
        Map<String, Object> report = new LinkedHashMap<String, Object>();
        report.put("verifiedEditedAssets", verified); report.put("failures", 0);
        writeJson(distribution.getParent().resolve("asset-edits-verification.json"), report);
        System.out.println("Verified " + verified + " compiled asset edits.");
    }
}
