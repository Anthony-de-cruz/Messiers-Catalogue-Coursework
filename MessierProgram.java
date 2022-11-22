public class MessierProgram {

    public static void main(String[] args) {

        String demo = "M1, \"NGC 1952\", \"Crab Nebula\", Supernova remnant, 4.9-8.1, Taurus, 8.4, 5h 34m 31.9400s, 22° 0' 52.2000";

        MessierObject bob = new MessierObject(demo);

        System.out.println(demo);
        System.out.println(bob.toString());
        System.out.println(bob.getNgcicNumber());
    }
}