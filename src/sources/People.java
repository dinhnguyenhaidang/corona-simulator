/**
 * Dinh Nguyen Hai Dang - B1704721
 */
package sources;

import java.awt.Color;
import java.awt.Graphics;

public class People {

    private int location_x;
    private int location_y;
    protected int size;
    protected boolean is_susceptible;
    protected boolean is_infected;
    protected boolean is_recovered;
    protected boolean is_dead;

    /**
     * Ham xay dung co tham so.
     *
     * @param x toa do x
     * @param y toa do y
     * @param s kich thuoc
     */
    public People(int x, int y, int s) {
        location_x = x;
        location_y = y;
        size = s;
    }

    /**
     * Thu tuc the hien do hoa mot nguoi duoi dang cham tron.
     *
     * @param g
     */
    public void show(Graphics g) {
        g.setColor(Color.green);
        if (this.is_susceptible && !this.is_infected) {
            g.setColor(Color.cyan);
        }
        if (this.is_infected) {
            g.setColor(Color.red);
        }
        if (this.is_recovered) {
            g.setColor(Color.blue);
        }
        if (this.is_dead) {
            g.setColor(Color.gray);
        }
        g.fillOval(this.location_x, this.location_y, this.size, this.size);
    }

    /**
     * Ham tinh khoang cach giua 2 nguoi.
     *
     * @param p nguoi p
     * @return khoang cach giua nguoi "this" va nguoi p
     */
    public double distant(People p) {
        return Math.sqrt(Math.pow(this.location_x - p.location_x, 2) + Math.pow(this.location_y - p.location_y, 2));
    }

    /**
     * You died
     */
    public void die() {
        this.is_dead = true;
    }

    /**
     * Victory achieved
     */
    public void awaken() {
        this.is_recovered = true;
        this.is_infected = false;
        this.is_susceptible = true;
    }
}
