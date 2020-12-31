/**
 * Dinh Nguyen Hai Dang - B1704721
 */
package sources;

import static sources.RandomNumberGenerator.rng;
import java.applet.Applet;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoronaSimulator extends Applet {

    private People[] list_people;

    private final int list_size = 100;
    private final int font_size = 20;
    private final int width = 720;
    private final int height = width + font_size;

    private final int sleep_time = 1400;

    private final int size_min = 10;
    private final int size_max = 20;

    private final int is_susceptible_rate = 20; // Ti le khang benh bam sinh
    private final int recover_rate = 30; // Ti le khoi benh
    private final int infect_rate = 60; // Ti le lay benh (nguoi chet van co the lay benh)
    private final int infect_distance = 100; // Khoang cach co the lay benh

    @Override
    public void init() {
        this.setSize(width, height);

        list_people = new People[list_size];
        for (int i = 0; i < list_size; i++) {
            list_people[i] = new People(rng(width - 30), rng(width - 30), rng(size_min, size_max));

            /**
             * Moi nguoi co mot ti le khang benh bam sinh
             */
            boolean status = false;
            if (rng(100) <= is_susceptible_rate) {
                status = true;
            }
            list_people[i].is_susceptible = status;

        }
        list_people[0].is_susceptible = false;
        list_people[0].is_infected = true;

        new Thread() {
            @Override
            @SuppressWarnings("SleepWhileInLoop")
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(sleep_time);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CoronaSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    /**
                     * Lay benh theo chu ky sleep_time
                     */
                    spread_plague();

                    repaint();
                }
            }
        }.start();
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, font_size));
        g.drawString("Normal: green; Susceptible: cyan; Infected: red; Recovered: blue; Dead: gray.", 0, height - font_size);
        for (People people : list_people) {
            people.show(g);
        }
    }

    /**
     * Gia lap qua trinh lay benh va khoi benh
     */
    private void spread_plague() {
        /**
         * Lay danh sach nguoi nhiem benh
         */
        List<People> infected_list = new ArrayList<>();
        for (People people : list_people) {
            if (people.is_infected) {
                infected_list.add(people);
            }
        }

        /**
         * Nguoi bi benh co mot ti le khoi benh va co kha nang de khang
         */
        infected_list.stream().forEach((infected) -> {
            if (rng(100) <= recover_rate) {
                infected.awaken();
            } else {
                infected.die();
            }
        });

        /**
         * Nhung nguoi khong khang benh o gan nguoi bi benh co mot ti le nhiem
         * benh
         */
        infected_list.stream().forEach((infected) -> {
            for (People people : list_people) {
                if (people.distant(infected) <= infect_distance && !people.is_susceptible && rng(100) <= infect_rate) {
                    people.is_infected = true;
                }
            }
        });
    }
}
