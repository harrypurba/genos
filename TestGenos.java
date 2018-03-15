import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestGenos {
    public byte[] plainfile;
    public byte[] plainbiner = new byte[0];
    public byte[] kuncifile;
    public byte[] kuncibiner = new byte[0];
    public ArrayList<byte[]> plainblok = new ArrayList();
    public ArrayList<byte[]> cipherblok = new ArrayList();


    public static void main(String[] args) throws IOException {
        TestGenos tes = new TestGenos();

        // Choose one
        //tes.modeECB();
        tes.modeCBC();
    }

    public void modeECB() throws IOException {
        init();
        for (int i = 0; i < plainblok.size(); i++) {
            AlgoritmaGenos ag = new AlgoritmaGenos(plainblok.get(i), kuncibiner);
            ag.enkripsi();
            cipherblok.add(ag.getBlokciphertext());
            printblok(ag.getBlokciphertext());
        }
        //writefile();
    }

    public void modeCBC() throws IOException {
        init();
        byte[] temp = iv64bit();
        for (int i = 0; i < plainblok.size(); i++) {
            xor(temp,plainblok.get(i));
            AlgoritmaGenos ag = new AlgoritmaGenos(temp, kuncibiner);
            ag.enkripsi();
            cipherblok.add(ag.getBlokciphertext());
            printblok(ag.getBlokciphertext());
            temp = ag.getBlokciphertext().clone();
        }
        //writefile();
    }


    public void printblok(byte[] blok){
        for (int i = 0 ; i < blok.length ; i+=8){
            byte[] temp = Arrays.copyOfRange(blok, i, i + 8);
            byte temp2 = binertobyte(temp);
            printchar(temp2);
        }
    }

    public void init() throws IOException {
        // Baca PLAINTEXT

        plainfile = Files.readAllBytes(Paths.get("input.txt"));
        //System.out.println("Plainfile : ");
        //printfile(plainfile);
        plainfiletobiner();
        //printarray(plainbiner);
        setBlokPlain();
        //printarray(plainblok.get(4));
        System.out.println();

        // BACA KUNCI
        kuncifile = Files.readAllBytes(Paths.get("kuncieksternal.txt"));
        kuncifiletobiner();


    }

    public void printchar(byte b){
        System.out.print((char)b);
    }

    public void printarray(byte[] arr){
        int j = -1;
        for (int i = 0 ; i < arr.length ; i++){
            if(i % 8 == 0){
                System.out.print("  "+j);
                System.out.println();
                j++;
            }
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

    public void printfile(byte[] b){
        for (int i = 0; i < b.length; i++) {
            System.out.print((char)b[i]);
        }
        System.out.println();
    }

    public void setBlokPlain(){
        for (int i = 0 ; i < plainbiner.length ; i+=64){
            byte[] temp = Arrays.copyOfRange(plainbiner, i, i + 64);
            plainblok.add(temp);
        }
    }

    public void plainfiletobiner(){
        for (int i = 0; i < plainfile.length; i++) {
            plainbiner = concatarray(plainbiner,bytetobiner(plainfile[i]));
        }
    }

    public void kuncifiletobiner(){
        for (int i = 0; i < kuncifile.length; i++) {
            kuncibiner = concatarray(kuncibiner,bytetobiner(kuncifile[i]));
        }
    }

    public byte[] bytetobiner(byte a){
        byte[] res = new byte[8];

        for (int i = 0; i < 8; i++) {
            res[i] = 0;
        }
        int j = 7;
        byte temp = a;
        while(temp > 0){
            res[j] = (byte)(temp%2);
            j--;
            temp = (byte)(temp/2);
        }
        return res;
    }

    public byte binertobyte(byte[] b){
        byte res = 0;
        byte pengali = 1;
        for (int i = b.length-1; i >= 0; i--) {
            res += (byte) (b[i] * pengali);
            pengali *= 2;
        }
        return res;
    }

    public byte[] concatarray(byte[] a, byte[] b){
        byte[] res = new byte[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i];
        }
        int j = a.length;
        for (int i = 0; i < b.length; i++) {
            res[j] = b[i];
            j++;
        }
        return res;
    }

    public void writecipher() throws IOException {
        byte[] outcipher = new byte[cipherblok.size()*8];


        int index = 0;

        for(int i = 0 ; i < cipherblok.size(); i++) {
            for (int j = 0 ; j < cipherblok.get(i).length ; j+=8){
                byte[] temp = Arrays.copyOfRange(plainbiner, j, j + 8);
                byte temp2 = binertobyte(temp);
                outcipher[index] = temp2;
                index++;
            }
        }

        FileOutputStream stream = new FileOutputStream("output.txt");
        try {
            stream.write(outcipher);
        } finally {
            stream.close();
        }
    }

    public byte[] iv64bit(){
        byte[] res = new byte[64];
        for (byte b :res) {
            Random rand = new Random();
            b = (byte) rand.nextInt(2);
        }
        return res;
    }

    public void xor(byte[] blok1, byte[] blok2){
        for (int i = 0 ; i < blok1.length ; i++){
            blok1[i] = (byte) (blok1[i] ^ blok2[i]);
        }
    }
}
