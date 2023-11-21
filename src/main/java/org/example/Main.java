package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main extends Application {


    float t = 0.f;  // Переменная, в которую записывается сколько прошло времени. Будет использоваться чтобы враги не стреляли постоянно.
    Pane root = new Pane(); // Наше игровое поле, на котором отображается все, что происходит (рисуются и обновляются спрайты)

    Pane menu = new Pane();
    BorderPane game = new BorderPane();

    Canvas canvas = new Canvas(1000, 800);
    GraphicsContext context = canvas.getGraphicsContext2D();

    SpriteImg player = new SpriteImg("player", 60, 60, 500, 400, "img/spaceship.png");
    SpriteImg bg = new SpriteImg("background", 1000, 800, 0, 0, "img/space.png");

    ArrayList<SpriteImg> playerShots = new ArrayList<>();
    ArrayList<SpriteImg> enemyShots = new ArrayList<>();
    ArrayList<SpriteImg> enemies = new ArrayList<>();

    Random rd = new Random();

    int scoreEnemy = 0;
    int lives = 3;

    Text scoreText = new Text(20, 750, "score: " + scoreEnemy);
    Font font = new Font(25);
    Text livesText = new Text(20, 775, "lives: " + lives);

    AnimationTimer timer = new AnimationTimer() {  // Создаем таймер для обновления root (чтобы спрайты могли двигаться)
        @Override
        public void handle(long l) {
            update();
        }
    };

    private Parent createGame(){
        game.getChildren().clear();
        lives = 3;
        scoreEnemy = 0;
        livesText.setText("lives: " + lives);
        scoreText.setText("score: " + scoreEnemy);
        player.isDead = false;
        game.setPrefSize(1000, 800);

        context = canvas.getGraphicsContext2D();
        game.setCenter(canvas);

        game.setStyle("-fx-background-color: #000020;");
        scoreText.setFont(font);
        scoreText.setFill(Color.WHITE);
        game.getChildren().add(scoreText);
        livesText.setFont(font);
        livesText.setFill(Color.WHITE);
        game.getChildren().add(livesText);

        player.draw(context);

        return game;
    }
    private void showGame(){
        timer.start();
        root.getChildren().clear();
        root.getChildren().add(createGame());
    }

    private Parent createMenu(){
        menu.setPrefSize(1000, 800);
        menu.setStyle("-fx-background-color: #000020;");

        Button startButton = new Button();
        startButton.setText("START");
        startButton.setTranslateX(500);
        startButton.setTranslateY(400);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showGame();
            }
        });

        Button exitButton = new Button();
        exitButton.setText("EXIT");
        exitButton.setTranslateX(500);
        exitButton.setTranslateY(450);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        menu.getChildren().add(startButton);
        menu.getChildren().add(exitButton);
        return menu;
    }

    private void showMenu(){
        root.getChildren().add(createMenu());
    }
    private Parent createRoot() // Метод для настройки и наполнения root спрайтами
    {
        root.setPrefSize(1000, 800);  // Устанавливаем размеры root
        root.setStyle("-fx-background-color: #000020;");  // Устанавливаем фон root
        return root;  // Возвращаем настроенный root (в то место, где будет вызван метод createContent() r122)
    }


    private void update()  // Метод для обновления положений спрайтов и расчета попаданий ракет (и других действий)
    {
        t += 0.016;  // При каждом апдейте добавляем к переменной t какое-то время (сколько занимает один апдейт), чтобы знать сколько прошло с какого-то события

        for (SpriteImg enemy: enemies)
        {
            int shootChance = rd.nextInt(1001);
            if(shootChance > 995)
            {
                enemyShots.add(shoot(enemy));
            }
        }

        for(SpriteImg shot: playerShots)
        {
            for (SpriteImg enemy: enemies)
            {
                if(shot.collides(enemy))
                {
                    enemy.isDead = true;  // Враг уничтожен
                    shot.isDead = true;  // ракета уничтожена
                    scoreEnemy++;
                    scoreText.setText("score: " + scoreEnemy);
                }
            }
        }

        for (SpriteImg shot: enemyShots)
        {
            if(!player.isDead)
            {
                if (shot.collides(player)) {
                    player.isDead = true;
                    shot.isDead = true;
                    lives--;
                    livesText.setText("lives: " + lives);
                }
            }
        }


        if (enemies.isEmpty())
        {
            spawnEnemy(4);  // Создаем спрайт врага
        }

        playerShots.removeIf(shot -> (shot.isDead));
        enemyShots.removeIf(shot -> (shot.isDead));
        enemies.removeIf(enemy -> (enemy.isDead));

        bg.draw(context);

        if(!player.isDead)
        {
            player.draw(context);
        }

        for (SpriteImg enemy: enemies)
        {

            if(player.collides(enemy))
            {
                System.out.println("Collision detected.");
            }
            enemy.draw(context);
        }

        for(SpriteImg shot: playerShots)
        {
            shot.draw(context);
            shot.posY -= 10;
        }

        for(SpriteImg shot: enemyShots)
        {
            shot.draw(context);
            shot.posY += 10;
        }

    }
//klds;dfklsd;f
    @Override
    public void start(Stage stage) throws Exception {  // Абстрактный метод класса Application (наследование от него на r20), для которого сы должны написать логику
        Scene scene = new Scene(createRoot());  // Создаем "рабочий стол" и помещаем на него наше игровое поле (которое уде наполнено методом createContent())

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {  // Прикручиваем к нашему "рабочему столу" детектор событий с клавиатуры (реагирует на нажатия клавиш)
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode())  // В зависимости от нажатой клавиши выбираем что делать
                {
                    case A:  // клавиша A
                        player.moveLeft();  // двигаем игрока влево
                        break;

                    case D:  // клавиша D
                        player.moveRight();  // двигаем игрока вправо
                        break;

                    case W:  // клавиша W
                        player.moveUp();  // двигаем игрока вверх
                        break;

                    case S:  // клавиша S
                        player.moveDown();  // двигаем игрока вниз
                        break;

                    case SPACE:  // клавиша SPACE
                        playerShots.add(shoot(player));
                        break;

                    case R:
                        if(lives > 0){
                            player.isDead = false;
                            //game.getChildren().add(player);
                        }
                        break;
                }
            }
        });

        showMenu();
        stage.setScene(scene);  // отправляем наш "рабочий стол" на экран
        stage.show();  // показываем экран
    }

    private SpriteImg shoot(SpriteImg who) // Метод для стрельбы, с помощью аргумента who выбираем кто стреляет
    {
        String rocketType; // создаем переменную rocketType, в которую запишем разный тип ракеты в зависимости от того кто стреляет
        if(who.type.equals("player"))  // если стрелял игрок (who.type.equals("player") == true) <- тип стреляющего спрайта == player
        {
            rocketType = "player_rocket";  // записываем в переменную rocketType значение player_rocket (ракета игрока)
        }
        else {  // если стрелял игрок (who.type.equals("player") == false) <- тип стреляющего спрайта != player
            rocketType = "enemy_rocket";  // записываем в переменную rocketType значение enemy_rocket (ракета врага)
        }

        SpriteImg rocket = new SpriteImg(rocketType, 10, 20, who.posX+(who.w/2), who.posY,
                "img/laser_blast.png");
        return rocket;
    }
    public void spawnEnemy(int numOfEnemies){
        for(int i = 0; i < numOfEnemies; i++){
            int x = rd.nextInt(1000);
            SpriteImg enemy = new SpriteImg("enemy", 60, 60, x, 100, "img/enemy.png");  // Создаем спрайт врага
            enemies.add(enemy);
        }
    }

    public static void main(String[] args) {  // главный метод, с которого стартует программа
        launch(args);  // метод, который запускает приложения JavaFX
    }
}