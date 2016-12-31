package net.mostlyoriginal.api.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.mock;

/**
 * Test for Aseprite helper class.
 *
 * @author Daan van Yperen
 */
public class AsepriteTest {

    @Test
    public void when_no_frametags_should_have_main_animation() {
        final Aseprite.AseFormat ase = createTestAse(50);
        Assert.assertNotNull(Aseprite.fromAse("test", ase, mock(Texture.class)).get("test"));
    }

    @Test(expected = RuntimeException.class)
    public void when_frame_duration_not_multiple_of_shortest_duration_should_throw_exception() {
        final Aseprite.AseFormat ase = createTestAse(2, 3);
        Aseprite.fromAse("test", ase, mock(Texture.class));
    }

    @Test
    public void when_frame_has_longer_duration_should_repeat_frames() {
        final Aseprite.AseFormat ase = createTestAse(10,50);
        Animation<TextureRegion> animation = Aseprite.fromAse("test", ase, mock(Texture.class)).get("test");
        Assert.assertEquals(6,animation.getKeyFrames().length);
        Assert.assertFalse(
                animation.getKeyFrames()[0].equals(animation.getKeyFrames()[1]));
        Assert.assertTrue(
                animation.getKeyFrames()[1].equals(animation.getKeyFrames()[2]));
    }

    @Test
    public void when_multiple_tags_should_create_animation_for_each() {
        final Aseprite.AseFormat ase = createTestAse(50,50);
        createTestFrametag(ase,"jump",0,1);
        createTestFrametag(ase,"walk",0,1);

        final HashMap<String, Animation<TextureRegion>> animations = Aseprite.fromAse("test", ase, mock(Texture.class));
        Assert.assertNotNull(animations.get("test"));
        Assert.assertNotNull(animations.get("test/jump"));
        Assert.assertNotNull(animations.get("test/walk"));
    }


    private boolean createTestFrametag(Aseprite.AseFormat ase, String name, int from, int to) {
        return ase.meta.frameTags.add(new Aseprite.AseFormat.FrameTag(name,from,to));
    }

    @Test
    public void when_no_frames_should_have_no_animation() {
        final Aseprite.AseFormat ase = createTestAse();
        Assert.assertNull(Aseprite.fromAse("test", ase, mock(Texture.class)).get("test"));
    }

    private Aseprite.AseFormat createTestAse(int ... durations) {
        Aseprite.AseFormat ase = new Aseprite.AseFormat();
        ase.frames = new ArrayList<>();
        for (int duration : durations) {
            ase.frames.add(createFrame(duration));
        }
        ase.meta = new Aseprite.AseFormat.AseMeta();
        ase.meta.frameTags = new ArrayList<>();
        return ase;
    }

    private Aseprite.AseFormat.AseKeyFrame createFrame(int duration) {
        Aseprite.AseFormat.AseKeyFrame frame = new Aseprite.AseFormat.AseKeyFrame();
        frame.duration= duration;
        frame.frame = new Aseprite.AseFormat.AseKeyFrame.Dim();
        return frame;
    }
}