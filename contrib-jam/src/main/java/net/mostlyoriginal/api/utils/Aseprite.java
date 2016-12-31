package net.mostlyoriginal.api.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helper for loading AseSprites.
 *
 * @author Daan van Yperen
 */

public final class Aseprite {

    public static final float ASESPRITE_TO_GDX_DURATION = 0.001f;

    private Aseprite() {
    }

    /**
     * Load Asesprite as one or more animations.
     *
     * @param name Name in asset folder, assumes name.json and name.png exists. Animations also prefixed by name.
     * @return HashMap of animations.
     */
    public static HashMap<String, Animation<TextureRegion>> fromFile(String name) {
        return fromFile(name, name);
    }

    /**
     * Load Asesprite as one or more animations.
     *
     * @param name Name in asset folder, assumes name.json and name.png exists. Animations also prefixed by name.
     * @param key Key for animations. (dog = dog, dog/walk)
     * @return HashMap of animations.
     */
    public static HashMap<String, Animation<TextureRegion>> fromFile(String name, String key) {
        return fromFile(key, Gdx.files.internal(name + ".json"), Gdx.files.internal(name + ".png"));
    }

    /**
     * Load Asesprite as one or more animations.
     * <p>
     * <p>
     * One animation per frametag named key + '/' + frametag name, plus one frametag
     * for the full animation named as the key.
     * <p>
     * Example: dog.json, dog.png with walk and jump frametag will return a hashmap
     * with the following frametags.
     * dog
     * dog/jump
     * dog/walk
     *
     * @return HashMap of animations, with key.
     */
    public static HashMap<String, com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>> fromFile(String key, FileHandle jsonFile, FileHandle pngFile) {
        return fromAse(key, fromJson(jsonFile), new Texture(pngFile));
    }

    public static HashMap<String, Animation<TextureRegion>> fromAse(String key, AseFormat ase, Texture texture) {
        if (!ase.durationsDivisibleByShortest()) {
            throw new RuntimeException("One or more frame durations not divisible by shortest duration for " + key + ". To convert Asesprite animations to Gdx animations this is required.");
        }

        return createGdxAnimationMap(ase, key, texture);
    }

    private static HashMap<String, Animation<TextureRegion>> createGdxAnimationMap(AseFormat ase, String key, Texture texture) {
        HashMap<String, Animation<TextureRegion>> results = new HashMap<>();

        final TextureRegion[] frames = framesAsTextureRegions(ase, texture);

        if (frames.length > 0) {
            results.put(key, asGdxAnimation(ase, frames, 0, frames.length - 1));

            for (AseFormat.FrameTag frameTag : ase.meta.frameTags) {
                results.put(key + "/" + frameTag.name,
                        asGdxAnimation(ase, frames, frameTag.from, frameTag.to));
            }
        }
        return results;
    }

    private static AseFormat fromJson(FileHandle jsonFile) {
        final Json json = new Json();
        json.setIgnoreUnknownFields(true);
        return json.fromJson(AseFormat.class, jsonFile);
    }

    private static Animation<TextureRegion> asGdxAnimation(AseFormat ase, TextureRegion[] frames, int from, int to) {
        Animation<TextureRegion> animation = new Animation<>(
                ase.shortestFrameDuration() * ASESPRITE_TO_GDX_DURATION,
                framesAsAnimationArray(ase, frames, from, to, ase.frames));
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    private static TextureRegion[] framesAsTextureRegions(AseFormat ase, Texture texture) {
        final TextureRegion[] frames = new TextureRegion[ase.frames.size()];
        int index = 0;
        for (AseFormat.AseKeyFrame frame : ase.frames) {
            frames[index++] = new TextureRegion(texture, frame.frame.x, frame.frame.y, frame.frame.w, frame.frame.h);
        }
        return frames;
    }

    private static TextureRegion[] framesAsAnimationArray(AseFormat ase, TextureRegion[] frames, int from, int to, ArrayList<AseFormat.AseKeyFrame> aseFrames) {

        // Determine the length of the GDX animation.
        final int shortestDuration = ase.shortestFrameDuration();
        final int gdxFrameCount = ase.durationOf(from, to) / shortestDuration;

        final TextureRegion[] array = new TextureRegion[gdxFrameCount];
        for (int i = from, index = 0; i <= to; i++) {
            // GDXX Anims use fixed framerates. Ase uses variable. We repeat frames if duration requires it.
            for (int j = 0, s = aseFrames.get(i).duration / shortestDuration; j < s; j++) {
                array[index++] = frames[i];
            }
        }
        return array;
    }

    public static final class AseFormat {
        ArrayList<AseKeyFrame> frames;
        AseMeta meta;

        int smallestDuration = -1;

        static final class AseMeta {
            ArrayList<FrameTag> frameTags;
        }

        static final class FrameTag {
            String name;
            int from;
            int to;
            String direction;

            public FrameTag() {
            }

            public FrameTag(String name, int from, int to) {
                this.name = name;
                this.from = from;
                this.to = to;
            }
        }

        static final class AseKeyFrame {
            Dim frame;
            int duration;

            static final class Dim {
                int x, y, w, h;
            }
        }

        int shortestFrameDuration() {
            if (smallestDuration == -1) {
                for (AseFormat.AseKeyFrame frame : frames) {
                    if (smallestDuration == -1 || smallestDuration > frame.duration) {
                        smallestDuration = frame.duration;
                    }
                }
            }
            return smallestDuration;
        }

        int durationOf(int from, int to) {
            int duration = 0;
            for (int i = from; i <= to; i++) {
                duration += frames.get(i).duration;
            }
            return duration;
        }

        boolean durationsDivisibleByShortest() {
            for (AseFormat.AseKeyFrame frame : frames) {
                if (frame.duration % shortestFrameDuration() != 0) return false;
            }
            return true;
        }
    }

}