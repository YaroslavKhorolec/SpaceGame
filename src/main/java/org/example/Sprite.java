package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {  // класс для спрайта, который наследуется от класса Rectangle (прямоугольник) из JavaFX
    String type;
    boolean isDead;  // атрибут, который обозначает уничтожен ли спрайт

    public Sprite(int w, int h, int x, int y, String type, Color color) // Конструктор для быстрого создания объекта класса Sprite
    {
        super(w, h, color);  // super - вызов родительского класса (прямоугольника), в который мы передали ширину, высоту и цвет
        this.type = type;  // устанавливаем тип спрайта
        this.isDead = false; // сразу после создания спрайт не уничтожен
        setTranslateX(x);  // устанавливаем положение спрайта по x (лево/право)
        setTranslateY(y);  // устанавливаем положение спрайта по y (верх/низ)
    }

    public void moveUp() // метод для перемещения спрайта вверх
    {
        setTranslateY(getTranslateY() - 5);  // установитьY(взятьТекущийY() - 5)
    }

    public void moveDown()  // метод для перемещения спрайта вниз
    {
        setTranslateY(getTranslateY() + 5);  // установитьY(взятьТекущийY() + 5)
    }

    public void moveLeft()  // метод для перемещения спрайта влево
    {
        setTranslateX(getTranslateX() - 5);  // установитьX(взятьТекущийX() - 5)
    }

    public void moveRight()   // метод для перемещения спрайта вправо
    {
        setTranslateX(getTranslateX() + 5);  // установитьX(взятьТекущийX() + 5)
    }

    // Верхний левый угол игрового поля = (0, 0)
    // Нижний правый угол игрового поля = (MAX X, MAX Y)

}
