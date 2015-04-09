package OLDcom.monolc.fell.graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import OLDcom.monolc.fell.resources.Texture;

public class Text {
	public static Texture getTexture(String text) {
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		BufferedImage image = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setFont(font);
		g.drawString(text, 0, 0);
		/*
		 * LineMetrics l = font.getLineMetrics("text",
		 * g.getFontRenderContext()); TextLayout t =
		 */
		int width = 200;// get text width somehow
		int height = 50;// get text height somehow (l.getHeight() seems
						// promising)
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int b = 0; b < height; b++) {
			for (int a = 0; a < width; a++) {
				int pixel = pixels[b * width + a];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		buffer.flip();
		g.dispose();
		return new Texture(buffer, width, height);
	}
}
