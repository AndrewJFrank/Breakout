/*
To Play: Use right and left arrow keys or A and D keys to move the platform around.
If the 'ball' hits the ground, you lose. If you break all of the blocks, you win.
The game emulates stacks for columns of blocks so that if one block is hit, all other blocks below it are hit also.
*/
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application
{
    private static Stage window;
    //settings start
    private static int height = 18; //18
    private static int width = 18; //36
    private static int squareLength = 50; //50
    private static int platLength = 8; //8 (must be even number)
    private static boolean top = true; //true
    private static boolean gridLines = false; //false
    private static long speed = 100_000_000; //100_000_000
    //rotation
    private static boolean rotate = false; //false
    private static boolean rotateOnBreak = true; //false
    private static int angle = 1; //1
    private static long rotateSpeed = 0_000_000; //0_000_000
    //lights
    private static boolean lights = true; //true
    private static boolean randLights = false; //false
    private static boolean rowLights = false; //false
    private static boolean colLights = false; //false
    private static boolean breakRandLights = false; //false
    private static boolean breakRowLights = true; //true
    private static boolean breakColLights = false; //false
    private static long lightSpeed = 0_000_000; //0_000_000
    //cheats
    private static boolean cheat = false; //false (can toggle with c)
    private static boolean movePlat = true; //true (b)
    private static boolean change = false; //false (x)
    private static boolean changeDir = false; //false (v)
    private static boolean toggleSides = true; //true (z)
    private static boolean canDie = true; //true
    //settings end
    private static Shape array[][] = new Shape[height][width];
    private static Ball ball;
    private static ArrayList<Platform> plat = new ArrayList<>();
    private static long platSpeed = speed/2;
    private static boolean left = false;
    private static boolean right = false;
    private static int dz = 0;
    private static int dx = -1;
    private static int dy = 1;
    private static boolean esc = false;
    private static boolean pause = false;
    private static long lastUpdate = 0;
    private static long lastUpdate2 = 0;
    private static long lastUpdate3 = 0;
    private static long lastUpdate4 = 0;
    private static boolean dead = false;
    private static boolean win = false;
    private static boolean breakBlock = false;
    private static ArrayList<Ball> brokenBlocks = new ArrayList<>();

    public static void main(String[] args)
    {
        launch(args);
    }
    public void start(Stage window)
    {
        this.window = window;
        this.window.setTitle("Breakout - Andrew Frank");
        display();
    }
    public static void display()
    {
        GridPane layout = new GridPane();
        //creating the initial blocks
        for(int x = (height/2) - 1; x < height; x++)
        {
            for(int y = 0; y < width; y++)
            {
                if(x == height - 2 && y == width/2)
                {
                    array[x][y] = new Rectangle(squareLength, squareLength);
                    array[x][y].setFill(Color.ORANGE);
                    ball = new Ball(height - 2, width/2);
                }
                else if(x == height - 1 && y >= (width/2) - (platLength/2) && y < (width/2) + (platLength/2))
                {
                    array[x][y] = new Rectangle(squareLength, squareLength);
                    array[x][y].setFill(Color.BLUE);
                    if(y >= (width/2))
                    {
                        plat.add(new Platform(x, y, "right"));
                    }
                    else
                    {
                        plat.add(new Platform(x, y, "left"));
                    }
                }
                else
                {
                    array[x][y] = new Rectangle(squareLength, squareLength);
                    array[x][y].setFill(Color.WHITE);
                }
            }
        }
        if(top == false)
        {
            if(lights && (colLights || breakColLights))
            {
                for(int x = 0; x < width; x++)
                {
                    Color color = new Color(Math.random(),Math.random(),Math.random(),1);
                    for(int y = 0; y < (height/2) - 1; y++)
                    {
                        array[y][x] = new Rectangle(squareLength, squareLength);
                        array[y][x].setFill(color);
                    }
                }
            }
            else if(lights && (randLights || breakRandLights))
            {
                for(int x = 0; x < (height/2) - 1; x++)
                {
                    for(int y = 0; y < width; y++)
                    {
                        array[x][y] = new Rectangle(squareLength, squareLength);
                        array[x][y].setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                    }
                }
            }
            else
            {
                for(int x = 0; x < (height/2) - 1; x++)
                {
                    Color rowColor = new Color(Math.random(),Math.random(),Math.random(),1);
                    for(int y = 0; y < width; y++)
                    {
                        array[x][y] = new Rectangle(squareLength, squareLength);
                        array[x][y].setFill(rowColor);
                    }
                }
            }
        }
        else
        {
            if(lights && (colLights || breakColLights))
            {
                for(int x = 0; x < width; x++)
                {
                    Color color = new Color(Math.random(),Math.random(),Math.random(),1);
                    for(int y = (height/4); y < (height/2) - 1; y++)
                    {
                        array[y][x] = new Rectangle(squareLength, squareLength);
                        array[y][x].setFill(color);
                    }
                }
            }
            else if(lights && (randLights || breakRandLights))
            {
                for(int x = (height/4); x < (height/2) - 1; x++)
                {
                    for(int y = 0; y < width; y++)
                    {
                        array[x][y] = new Rectangle(squareLength, squareLength);
                        array[x][y].setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                    }
                }
            }
            else
            {
                for(int x = (height/4); x < (height/2) - 1; x++)
                {
                    Color rowColor = new Color(Math.random(),Math.random(),Math.random(),1);
                    for(int y = 0; y < width; y++)
                    {
                        array[x][y] = new Rectangle(squareLength, squareLength);
                        array[x][y].setFill(rowColor);
                    }
                }
            }
            for(int x = 0; x < height/4; x++)
            {
                for(int y = 0; y < width; y++)
                {
                    array[x][y] = new Rectangle(squareLength, squareLength);
                    array[x][y].setFill(Color.WHITE);
                }
            }

        }
        for(int x = 0; x < height; x++)
        {
            for(int y = 0; y < width; y++)
            {
                layout.add(array[x][y], y, x);
            }
        }
        layout.setGridLinesVisible(gridLines);
        //key listeners
        Scene mainScene = new Scene(layout, width * squareLength, height * squareLength);
        mainScene.setOnKeyPressed(e ->
        {
            switch(e.getCode())
            {
                case A: left = true; break;
                case D: right = true; break;
                case LEFT: left = true; break;
                case RIGHT: right = true; break;
                case C: cheat = !cheat; left = false; right = false; break;
                case B: movePlat = !movePlat; break;
                case X: change = !change; break;
                case V: changeDir = true; break;
                case Z: toggleSides = !toggleSides; break;
                case P: pause = !pause; break;
                case SPACE: pause = !pause; break;
                case ESCAPE: esc = true; break;
            }
        });
        mainScene.setOnKeyReleased(e ->
        {
            switch(e.getCode())
            {
                case A: left = false; break;
                case D: right = false; break;
                case LEFT: left = false; break;
                case RIGHT: right = false; break;
                case V: changeDir = false; break;
                case ESCAPE: esc = false; break;
            }
        });
        window.setScene(mainScene);
        window.show();
        //animation timer
        AnimationTimer timer = new AnimationTimer()
        {
            public void handle(long now)
            {
                if(!pause)
                {
                    //lighting effects
                    if(lights)
                    {
                        if(now - lastUpdate3 >= lightSpeed)
                        {
                            if(top && (randLights || (breakBlock && breakRandLights)))
                            {
                                boolean change;
                                for(int x = (height/4); x < (height/2) - 1; x++)
                                {
                                    for(int y = 0; y < width; y++)
                                    {
                                        change = true;
                                        for(int z = 0; z < brokenBlocks.size(); z++)
                                        {
                                            if(brokenBlocks.get(z).x() == x && brokenBlocks.get(z).y() == y)
                                            {
                                                change = false;
                                            }
                                        }
                                        if(change)
                                        {
                                            array[x][y].setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                                        }
                                    }
                                }
                            }
                            else if(!top && (randLights || (breakBlock && breakRandLights)))
                            {
                                boolean change;
                                for(int x = 0; x < (height/2) - 1; x++)
                                {
                                    for(int y = 0; y < width; y++)
                                    {
                                        change = true;
                                        for(int z = 0; z < brokenBlocks.size(); z++)
                                        {
                                            if(brokenBlocks.get(z).x() == x && brokenBlocks.get(z).y() == y)
                                            {
                                                change = false;
                                            }
                                        }
                                        if(change)
                                        {
                                            array[x][y].setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                                        }
                                    }
                                }
                            }
                            else if(top && (rowLights || (breakBlock && breakRowLights)))
                            {
                                boolean change;
                                for(int x = (height/4); x < (height/2) - 1; x++)
                                {
                                    Color color = new Color(Math.random(),Math.random(),Math.random(),1);
                                    for(int y = 0; y < width; y++)
                                    {
                                        change = true;
                                        for(int z = 0; z < brokenBlocks.size(); z++)
                                        {
                                            if(brokenBlocks.get(z).x() == x && brokenBlocks.get(z).y() == y)
                                            {
                                                change = false;
                                            }
                                        }
                                        if(change)
                                        {
                                            array[x][y].setFill(color);
                                        }
                                    }
                                }
                            }
                            else if(!top && (colLights || (breakBlock && breakColLights)))
                            {
                                boolean change;
                                for(int x = 0; x < width; x++)
                                {
                                    Color color = new Color(Math.random(),Math.random(),Math.random(),1);
                                    for(int y = 0; y < (height/2) - 1; y++)
                                    {
                                        change = true;
                                        for(int z = 0; z < brokenBlocks.size(); z++)
                                        {
                                            if(brokenBlocks.get(z).x() == y && brokenBlocks.get(z).y() == x)
                                            {
                                                change = false;
                                            }
                                        }
                                        if(change)
                                        {
                                            array[y][x].setFill(color);
                                        }
                                    }
                                }
                            }
                            else if(top && (colLights || (breakBlock && breakColLights)))
                            {
                                boolean change;
                                for(int x = 0; x < width; x++)
                                {
                                    Color color = new Color(Math.random(),Math.random(),Math.random(),1);
                                    for(int y = (height/4); y < (height/2) - 1; y++)
                                    {
                                        change = true;
                                        for(int z = 0; z < brokenBlocks.size(); z++)
                                        {
                                            if(brokenBlocks.get(z).x() == y && brokenBlocks.get(z).y() == x)
                                            {
                                                change = false;
                                            }
                                        }
                                        if(change)
                                        {
                                            array[y][x].setFill(color);
                                        }
                                    }
                                }
                            }
                            else if(!top && (rowLights || (breakBlock && breakRowLights)))
                            {
                                boolean change;
                                for(int x = 0; x < (height/2) - 1; x++)
                                {
                                    Color color = new Color(Math.random(),Math.random(),Math.random(),1);
                                    for(int y = 0; y < width; y++)
                                    {
                                        change = true;
                                        for(int z = 0; z < brokenBlocks.size(); z++)
                                        {
                                            if(brokenBlocks.get(z).x() == x && brokenBlocks.get(z).y() == y)
                                            {
                                                change = false;
                                            }
                                        }
                                        if(change)
                                        {
                                            array[x][y].setFill(color);
                                        }
                                    }
                                }
                            }
                            lastUpdate3 = now;
                        }
                    }
                    if(rotate)
                    {
                        if(now - lastUpdate4 >= rotateSpeed)
                        {
                            if((rotateOnBreak && breakBlock))
                            {
                                layout.getTransforms().add(new Rotate(angle, (width * squareLength)/2, (height * squareLength)/2));
                            }
                            else if(rotate && !rotateOnBreak)
                            {
                                layout.getTransforms().add(new Rotate(angle, (width * squareLength)/2, (height * squareLength)/2));
                            }
                            lastUpdate4 = now;
                        }
                    }
                    breakBlock = false;
                    //moving the ball
                    if(now - lastUpdate >= speed)
                    {
                        if(esc)
                        {
                            System.exit(0);
                        }
                        if(win == true)
                        {
                            Text youWin = new Text("You Win");
                            youWin.setFont(Font.font(450));
                            GridPane layout = new GridPane();
                            layout.add(youWin, 0, 0);
                            Scene scene = new Scene(layout);
                            scene.setOnKeyPressed(e ->
                            {
                                switch(e.getCode())
                                {
                                    case ESCAPE: esc = true; break;
                                }
                            });
                            window.setX(50);
                            window.setY(30);
                            window.setScene(scene);
                        }
                        else
                        {
                            if(dead == false)
                            {
                                win = true;
                                for(int x = 0; x < (height/2) - 1; x++)
                                {
                                    for(int y = 0; y < width; y++)
                                    {
                                        if(x == ball.x() && y == ball.y())
                                        {

                                        }
                                        else if(array[x][y].getFill().equals(Color.WHITE))
                                        {

                                        }
                                        else
                                        {
                                            win = false;
                                        }
                                    }
                                }
                                array[ball.x()][ball.y()].setFill(Color.WHITE);
                                if(ball.x() == 0)
                                {
                                    dx = 1;
                                }
                                if(ball.x() == height - 1)
                                {
                                    dx = -1;
                                }
                                if(ball.y() == 0)
                                {
                                    dy = 1;
                                }
                                if(ball.y() == width - 1)
                                {
                                    dy = -1;
                                }
                                for(int x = 0; x < plat.size(); x++)
                                {
                                    if(cheat && toggleSides)
                                    {
                                        if((ball.y() == plat.get(x).y() || ball.y() + dy == plat.get(x).y()) && ball.x() + dx == plat.get(x).x())
                                        {
                                            if(dz == 1 && ball.y() + 1 < width)
                                            {
                                                ball.setXY(ball.x(), ball.y() + 1);
                                            }
                                            if(dz == -1 && ball.y() - 1 > -1)
                                            {
                                                ball.setXY(ball.x(), ball.y() - 1);
                                            }
                                            dx = -1;
                                            if(cheat && changeDir)
                                            {
                                                if(dy == 1)
                                                {
                                                    dy = -1;
                                                }
                                                else if(dy == -1)
                                                {
                                                    dy = 1;
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if((ball.y() == plat.get(x).y() || ball.y() + dy == plat.get(x).y()) && ball.x() + dx == plat.get(x).x() && plat.get(x).pos().equals("left"))
                                        {
                                            if(dz == 1 && ball.y() + 1 < width)
                                            {
                                                ball.setXY(ball.x(), ball.y() + 1);
                                            }
                                            if(dz == -1 && ball.y() - 1 > -1)
                                            {
                                                ball.setXY(ball.x(), ball.y() - 1);
                                            }
                                            dx = -1;
                                            dy = -1;
                                            if(cheat && changeDir)
                                            {
                                                dy = 1;
                                            }
                                        }
                                        else if((ball.y() == plat.get(x).y() || ball.y() + dy == plat.get(x).y()) && ball.x() + dx == plat.get(x).x() && plat.get(x).pos().equals("right"))
                                        {
                                            if(dz == 1 && ball.y() + 1 < width)
                                            {
                                                ball.setXY(ball.x(), ball.y() + 1);
                                            }
                                            if(dz == -1 && ball.y() - 1 > -1)
                                            {
                                                ball.setXY(ball.x(), ball.y() - 1);
                                            }
                                            dx = -1;
                                            dy = 1;
                                            if(cheat && changeDir)
                                            {
                                                dy = -1;
                                            }
                                        }
                                    }
                                }

                                //detecting collision with blocks
                                boolean var = false;
                                if(ball.x() < height - 2 && ball.x() > 0 && !array[ball.x() + dx][ball.y()].getFill().equals(Color.WHITE))
                                {
                                    for(int x = ball.x(); x < height - 2; x++)
                                    {
                                        array[x + dx][ball.y()].setFill(Color.WHITE);
                                        brokenBlocks.add(new Ball(x + dx, ball.y()));
                                    }
                                    dx = -dx;
                                    breakBlock = true;
                                    var = true;
                                }
                                if(ball.y() < width - 1 && ball.y() > 0 && !array[ball.x()][ball.y() + dy].getFill().equals(Color.WHITE))
                                {
                                    for(int x = ball.x(); x < height - 2; x++)
                                    {
                                        array[x][ball.y() + dy].setFill(Color.WHITE);
                                        brokenBlocks.add(new Ball(x, ball.y() + dy));
                                    }
                                    dy = -dy;
                                    if(ball.x() < height - 2 && ball.x() > 0 && ball.y() < width - 1 && ball.y() > 0 && !array[ball.x() + dx][ball.y() + dy].getFill().equals(Color.WHITE) && array[ball.x() + dx][ball.y()].getFill().equals(Color.WHITE))
                                    {
                                        for(int x = ball.x(); x < height - 2; x++)
                                        {
                                            array[x + dx][ball.y() + dy].setFill(Color.WHITE);
                                            brokenBlocks.add(new Ball(x + dx, ball.y() + dy));
                                        }
                                        dx = -dx;
                                        dy = -dy;
                                        breakBlock = true;
                                    }
                                    breakBlock = true;
                                    var = true;
                                }
                                if(ball.x() < height - 2 && ball.x() > 0 && ball.y() < width - 1 && ball.y() > 0 && !array[ball.x() + dx][ball.y() + dy].getFill().equals(Color.WHITE) && var == false)
                                {
                                    for(int x = ball.x(); x < height - 2; x++)
                                    {
                                        array[x + dx][ball.y() + dy].setFill(Color.WHITE);
                                        brokenBlocks.add(new Ball(x + dx, ball.y() + dy));
                                    }
                                    dx = -dx;
                                    dy = -dy;
                                    breakBlock = true;
                                }



                                if(ball.x() == 0)
                                {
                                    dx = 1;
                                }
                                if(ball.x() == height - 1)
                                {
                                    dx = -1;
                                }
                                if(ball.y() == 0)
                                {
                                    dy = 1;
                                    if(ball.x() < height - 2 && ball.x() > 0 && ball.y() < width - 1 && (!array[ball.x() - 1][ball.y() + 1].getFill().equals(Color.WHITE)) && array[ball.x() - 1][ball.y()].getFill().equals(Color.WHITE) && var == false)
                                    {
                                        array[ball.x() - 1][ball.y() + 1].setFill(Color.WHITE);
                                        brokenBlocks.add(new Ball(ball.x() - 1, ball.y() + 1));
                                        dx = 1;
                                        breakBlock = true;
                                    }
                                    if(ball.x() < height - 2 && ball.x() > 0 && ball.y() < width - 1 && (!array[ball.x() + 1][ball.y() + 1].getFill().equals(Color.WHITE)) && array[ball.x() + 1][ball.y()].getFill().equals(Color.WHITE) && var == false)
                                    {
                                        for(int x = ball.x(); x < height - 2; x++)
                                        {
                                            array[x + 1][ball.y() + 1].setFill(Color.WHITE);
                                            brokenBlocks.add(new Ball(x + 1, ball.y() + 1));
                                        }
                                        dx = -1;
                                        breakBlock = true;
                                    }
                                }
                                if(ball.y() == width - 1)
                                {
                                    dy = -1;
                                    if(ball.x() < height - 2 && ball.x() > 0 && ball.y() > 0 && (!array[ball.x() - 1][ball.y() - 1].getFill().equals(Color.WHITE)) && array[ball.x() - 1][ball.y()].getFill().equals(Color.WHITE) && var == false)
                                    {
                                        array[ball.x() - 1][ball.y() - 1].setFill(Color.WHITE);
                                        brokenBlocks.add(new Ball(ball.x() - 1, ball.y() - 1));
                                        dx = 1;
                                        breakBlock = true;
                                    }
                                    if(ball.x() < height - 2 && ball.x() > 0 && ball.y() > 0 && (!array[ball.x() + 1][ball.y() - 1].getFill().equals(Color.WHITE)) && array[ball.x() + 1][ball.y()].getFill().equals(Color.WHITE) && var == false)
                                    {
                                        for(int x = ball.x(); x < height - 2; x++)
                                        {
                                            array[x + 1][ball.y() - 1].setFill(Color.WHITE);
                                            brokenBlocks.add(new Ball(x + 1, ball.y() - 1));
                                        }
                                        dx = -1;
                                        breakBlock = true;
                                    }
                                }
                                ball.setXY(ball.x() + dx, ball.y() + dy);
                                array[ball.x()][ball.y()].setFill(Color.ORANGE);
                                if(ball.x() == height - 1)
                                {
                                    dead = canDie;
                                }
                                lastUpdate = now;
                            }
                            else
                            {
                                Text youLose = new Text("You Lose");
                                youLose.setFont(Font.font(450));
                                GridPane layout = new GridPane();
                                layout.add(youLose, 0, 0);
                                Scene scene = new Scene(layout);
                                scene.setOnKeyPressed(e ->
                                {
                                    switch(e.getCode())
                                    {
                                        case ESCAPE: esc = true; break;
                                    }
                                });
                                window.setX(50);
                                window.setY(30);
                                window.setScene(scene);
                            }
                        }
                    }
                    //moving platform
                    if(dead == false)
                    {
                        if(now - lastUpdate2 >= platSpeed)
                        {
                            platSpeed = speed/2;
                            if(cheat && movePlat)
                            {
                                platSpeed = speed;
                                left = false;
                                right = false;
                                if(dy == 1)
                                {
                                    right = true;
                                }
                                if(dy == -1)
                                {
                                    left = true;
                                }
                            }
                            dz = 0;
                            if(left && right)
                            {
                                dz = 0;
                            }
                            else if(left)
                            {
                                if(plat.get(0).y() > 0)
                                {
                                    array[plat.get(plat.size() - 1).x()][plat.get(plat.size() - 1).y()].setFill(Color.WHITE);
                                    dz = -1;
                                }
                                else
                                {
                                    dz = 0;
                                }
                            }
                            else if(right)
                            {
                                if(plat.get(plat.size() - 1).y() < width - 1)
                                {
                                    array[plat.get(0).x()][plat.get(0).y()].setFill(Color.WHITE);
                                    dz = 1;
                                }
                                else
                                {
                                    dz = 0;
                                }
                            }
                            for(int x = 0; x < plat.size(); x++)
                            {
                                plat.get(x).setY(plat.get(x).y() + dz);
                            }
                            for(int x = 0; x < plat.size(); x++)
                            {
                                array[plat.get(x).x()][plat.get(x).y()].setFill(Color.BLUE);
                            }
                            if(cheat && !change)
                            {
                                dz = 0;
                            }
                            lastUpdate2 = now;
                        }
                    }
                }
            }
        };
        timer.start();
    }
}