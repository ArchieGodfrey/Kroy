package com.sprites;

// LibGDX imports
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Color;

/**
 * Resource bars used by sprites to indicate properties of sprites.
 * These are graphical and displayed to the user.
 * 
 * @author Archie
 * @since 17/12/2019
 */
public class ResourceBar {

    // Private values to be used in this class only
    private Batch batch;
    private ProgressBar bar;
    private int currentResourceAmount;
    private int maxResourceAmount;
    private int barWidth;
    private int barHeight;
    private float scaleFactor;
    private float x;
    private float y;

    /**
     * Constructor for this class, gathers required information so that it can
     * be drawn.
     * 
     * @param barBatch     The batch the sprite should be drawn on.
     * @param spriteWidth  The width of the sprite.
     * @param spriteHeight The height of the sprite.
     */
    public ResourceBar(Batch barBatch, float spriteWidth, float spriteHeight ) {
        this.batch = barBatch;
        // Adjust bar to fit sprite dimensions
        this.barWidth = (int) (0.75 * spriteWidth);
        this.barHeight = (int) (0.05 * spriteHeight);
        this.maxResourceAmount = this.barWidth;
        this.currentResourceAmount = this.barWidth;
        this.scaleFactor = (float) this.barWidth / (float) this.maxResourceAmount;
        this.x = 0;
        this.y = 0;
        this.bar = new ProgressBar(0, 100, 0.5f, false, getResourceBarStyle());
    }

    /**
     * Draw the resource bar.
     */
    public void update() {
        drawResourceBar();
    }

    /**
     * Create the rectangles for the resource bar, and fill them with the given
     * colour.
     * 
     * @param pixmapName The name of the pixmap to draw.
     * @param colour     The colour to fill the resource bar.
     * @param barWidth   The width of the resource bar.
     * @param barHeight  The height of the resource bar.
     * @return           The filled bar.
     */
    private Skin createBarFill(String pixmapName, Color colour, int barWidth, int barHeight) {
        Skin barFill = new Skin();
        Pixmap pixMap= new Pixmap(barWidth, barHeight, Format.RGBA8888);
        pixMap.setColor(colour);
        pixMap.fill();
        barFill.add(pixmapName, new Texture(pixMap));
        return barFill;
    }

    /**
     * Update the colour of the resource bar and then return the new style.
     * 
     * @return Updated resource bar style.
     */
    private ProgressBarStyle getResourceBarStyle() {
        Color color = this.currentResourceAmount <= this.maxResourceAmount * 0.5 ? this.currentResourceAmount <= this.maxResourceAmount * 0.25 ? Color.RED : Color.ORANGE : Color.GREEN;
        int scaledResource = (int) (this.currentResourceAmount * this.scaleFactor);
        int scaledEmpty = (int) ((this.maxResourceAmount - this.currentResourceAmount) >= 0 ? (this.maxResourceAmount - this.currentResourceAmount) * this.scaleFactor : 0);
        Skin healthColour = createBarFill("healthPixmap", color, scaledResource, this.barHeight);
        Skin background = createBarFill("backgroundPixmap", Color.GRAY, scaledEmpty, this.barHeight);
        return new ProgressBarStyle(background.newDrawable("backgroundPixmap"), healthColour.newDrawable("healthPixmap"));
    }

    /**
     * Draw the resource bar onto the batch at the correct position.
     */
    private void drawResourceBar() {
        this.batch.begin();
        this.bar.setPosition(this.x, this.y);
        this.bar.setSize(this.barWidth, this.barHeight);
        this.bar.draw(batch, 1);
        this.batch.end();
    }

    /** 
     * Set the position of the bar, takes into account the sprite's dimensions
     * @param spriteXPos The x-coordinate of the sprite.
     * @param spriteYPos The y-coordinate of the sprite.
     */
    public void setPosition(float spriteXPos, float spriteYPos) {
        // Get sprite height and width by reversing previous calculations
        float spriteHeight = barHeight / 0.05f, spriteWidth = barWidth / 0.75f;
        this.x = spriteXPos + spriteWidth * 0.125f;
        this.y = spriteYPos + spriteHeight * 1.1f;
    }

    /**
     * Set the maximum limit for the resource bar.
     * 
     * @param maxAmount The maximum limit for the resource bar.
     */
    public void setMaxResource(int maxAmount) {
        // 100 / 1000
        this.scaleFactor = (float) this.barWidth / (float) maxAmount;
        // 1000
        this.maxResourceAmount = maxAmount;
        // 100 / 0.1 = 1000
        this.currentResourceAmount = (int) (this.currentResourceAmount / this.scaleFactor);
        this.bar.setStyle(getResourceBarStyle());
    }

    /**
     * The percentage for the bar to show.
     * 
     * @param percent The percentage the bar shows.
     */
    public void setResourcePercentage(int percent) {
        this.currentResourceAmount = (percent * this.maxResourceAmount) / 100;
        this.bar.setStyle(getResourceBarStyle());
    }

    /**
     * Subtrace a value from the resource bar.
     * 
     * @param amount The value to subtrace from the resource bar.
     */
    public void subtractResourceAmount(int amount) {
        this.currentResourceAmount -= amount;
        this.bar.setStyle(getResourceBarStyle());
    }
}