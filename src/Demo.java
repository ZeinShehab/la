import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.la.Vector;


public class Demo {
    private static final int    WIDTH   = 64;
    private static final int    HEIGHT  = 32;
    private static final int    RADIUS  = HEIGHT/4;
    private static final int    FPS     = 30;
    private static final double GRAVITY = 200;
    private static final double DT      = 1.0/FPS;
    private static final double DAMPING = -0.75;
    private static final double PADDING = RADIUS * 1;

    private static char[] display = new char[WIDTH * HEIGHT];
    public static void main(String[] args) throws InterruptedException {
        Vector pos     = new Vector(-RADIUS + PADDING, -RADIUS + PADDING);
        Vector vel     = new Vector(50, 1);
        Vector gravity = new Vector(0, GRAVITY);
        
        boolean quit = false;
    
        hideCursor();
        while (!quit) {
            vel = vel.add(gravity.mul(DT));
            pos = pos.add(vel.mul(DT));
            
            if (pos.tail() > HEIGHT - RADIUS) {
                pos.set(1, HEIGHT - RADIUS); 
                vel.set(1, vel.get(1) * DAMPING);
            }

            if (pos.head() >= WIDTH + RADIUS + PADDING)
                quit = true;

            fill(' ');
            ball(pos, RADIUS);
            show();
            resetCursor();

            TimeUnit.MICROSECONDS.sleep(1000*1000 / FPS);
        }
        showCursor();
    }

    public static void hideCursor() {
        System.out.printf("\u001B[?25l");
    }

    public static void showCursor() {
        System.out.printf("\u001B[?25h");
    }
    
    public static void resetCursor() {
        System.out.printf("\033[%dD", WIDTH);
        System.out.printf("\033[%dA", HEIGHT/2);

    }

    public static void ball(Vector c, double r) {
        Vector start = c.sub(Math.floor(r));
        Vector end   = c.add(Math.ceil(r));

        for (int y = (int) start.tail(); y <= end.tail(); y++) {
            for (int x = (int) start.head(); x <= end.head(); x++) {
                Vector d = c.sub(new Vector(x+0.5, y+0.5));

                if (d.normSq() <= r*r) {
                    if (0 <= x && x < WIDTH && 0 <= y && y < HEIGHT) {
                        display[y*WIDTH + x] = '*';
                    }
                }
            }
        }
    }
    
    public static void show() {
        for (int y = 0; y < HEIGHT/2; y++) {
            for (int x = 0; x < WIDTH; x++) {
                char a = display[(y*2)*WIDTH + x];
                char b = display[(y*2+1)*WIDTH + x];

                char out;

                if (a == ' ' && b == ' ')
                    out = ' ';
                else if (a == ' ' && b == '*')
                    out = '_';
                else if (a == '*' && b == ' ')
                    out = '^';
                else
                    out = '@';

                System.out.print(out);
            }
            System.out.println();
        }
    }

    public static void fill(char c) {
        Arrays.fill(display, c);
    }
}