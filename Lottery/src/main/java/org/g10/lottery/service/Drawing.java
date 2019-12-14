package org.g10.lottery.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import org.g10.lottery.models.Ticket;

public class Drawing {

    /*
    class that does the drawing of new tickets
    seems to be working properly so I don't need to touch it
    */
    
    private static Random rand = new Random();

    public static Ticket draw() {

        // guarantees uniqueness
        HashSet<Integer> set = new HashSet<>();
        while (set.size() < 5) {
            set.add(rand.nextInt(69) + 1);
        }

        Iterator<Integer> iterator = set.iterator();

        return new Ticket()
                .setPickOne(iterator.next())
                .setPickTwo(iterator.next())
                .setPickThree(iterator.next())
                .setPickFour(iterator.next())
                .setPickFive(iterator.next())
                .setPowerBall(rand.nextInt(26) + 1);
    }

}
