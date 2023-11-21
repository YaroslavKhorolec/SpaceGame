package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpriteImg {

    public Integer w;
    public Integer h;
    public Integer posX;
    public Integer posY;
    public Image img;
    public String type;
    public Boolean isDead;

    public SpriteImg(String type, Integer width, Integer height, Integer posX, Integer posY, String imgPath) {
        this.type = type;
        this.w = width;
        this.h = height;
        this.posX = posX;
        this.posY = posY;
        this.img = new Image(imgPath, this.w, this.h, false, false);
        this.isDead = false;
    }

    public void draw(GraphicsContext context)
    {
        context.save();

        context.translate(this.posX, this.posY);
        if (this.type.equals("enemy"))
        {
            context.translate((double)(this.w)/2, (double)(this.h)/2);
            context.rotate(180);
            context.translate(-(double)(this.w)/2, -(double)(this.h)/2);
        }

        context.drawImage(this.img, 0, 0);
        context.restore();
    }

    public boolean collides(SpriteImg anotherSprite) {
        Boolean collisionX = (this.posX < anotherSprite.posX + anotherSprite.w) &
                             (this.posX > anotherSprite.w) ||
                             (this.posX + this.w < anotherSprite.posX + anotherSprite.w) &
                             (this.posX + this.w > anotherSprite.posX);

        Boolean collisionY = (this.posY < anotherSprite.posY + anotherSprite.h) &
                             (this.posY > anotherSprite.h) ||
                             (this.posY + this.h < anotherSprite.posY + anotherSprite.h) &
                             (this.posY + this.h > anotherSprite.posY);

        return collisionX && collisionY;
    }

    public void moveUp() // метод для перемещения спрайта вверх
    {
        this.posY -= 5;  // установитьY(взятьТекущийY() - 5)
    }

    public void moveDown()  // метод для перемещения спрайта вниз
    {
        this.posY += 5;  // установитьY(взятьТекущийY() + 5)
    }

    public void moveLeft()  // метод для перемещения спрайта влево
    {
        this.posX -= 5;  // установитьX(взятьТекущийX() - 5)
    }

    public void moveRight()   // метод для перемещения спрайта вправо
    {
        this.posX += 5;  // установитьX(взятьТекущийX() + 5)
    }
}
