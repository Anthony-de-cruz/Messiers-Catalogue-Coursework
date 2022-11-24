public class MessierProgram {

    public static void main(String[] args) {

        String demo1 = "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000\"";
        String demo2 = "M2, \"NGC 7089\", \"-\", Globular cluster, 33.0, Aquarius, 6.5, 21h 33m 27.0200s, 0° 49' 23.7000\"";

        MessierObject bob = new MessierObject(demo1);
        MessierObject jim = new MessierObject(demo2);

        System.out.println(demo1);
        System.out.println(bob.toString());
        System.out.println(bob.getNgcicNumber());

        System.out.println(demo2);
        System.out.println(jim.toString());
        System.out.println(jim.getNgcicNumber());

        System.out.println(jim.compareTo(bob));

        System.out.println(jim.getRightAscension());
    }
}