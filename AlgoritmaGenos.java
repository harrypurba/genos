import java.util.ArrayList;
import java.util.Arrays;

public class AlgoritmaGenos {

    public byte[] tabelpermutasiawal = {
            58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,
            62,54,46,38,30,22,14,6,0,56,48,40,32,24,16,8,
            57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,
            61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7
    };

    public byte[] tabelpermutasiakhir = {
            24,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,
            30,37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,
            28,35,3,43,11,51,19,59,27,34,2,42,10,50,18,58,
            26,33,1,41,9,49,17,57,25,32,0,40,8,48,16,56
    };

    public byte[] tabelpermutasitengah = {
            26,18,10,2,28,20,12,4,30,22,14,6,0,24,16,8,
            25,17,9,1,27,19,11,3,29,21,13,5,31,23,15,7
    };

    public byte[] kuncieksternal = {
            0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1,
            1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0,
            0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1,
            1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1,
            1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0,
            0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0,
            0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0
    };


    public byte[] blokplaintext = {
            1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1,
            0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0,
            0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1
    };
    public ArrayList<byte[]> kunciinternal = new ArrayList<>();

    public static void main(String[] args){
        AlgoritmaGenos ag = new AlgoritmaGenos();

        System.out.println("Blok Plaintext Sebelum ");
        ag.printarray(ag.blokplaintext);
        System.out.println();
        ag.mainalgorithm();
        System.out.println("Blok Plaintext Sesudah");
        ag.printarray(ag.blokplaintext);

    }

    public void mainalgorithm(){
        init();
        permutasiawal();
        System.out.println("Sesudah Permutasi Awal");
        printarray(blokplaintext);
        System.out.println();

        putaranfeistel();
        System.out.println("Sesudah Feistel ");
        printarray(blokplaintext);
        System.out.println();
        permutasiakhir();
    }

    public void init(){

        int[] src = new int[] {1, 2, 3, 4, 5};
        int b1[] = Arrays.copyOfRange(src, 2, 5);

        byte[] kunciin0 =Arrays.copyOfRange(kuncieksternal,0,32);
        byte[] kunciin1 =Arrays.copyOfRange(kuncieksternal,32,64);
        byte[] kunciin2 =Arrays.copyOfRange(kuncieksternal,64,96);
        byte[] kunciin3 =Arrays.copyOfRange(kuncieksternal,96,128);
        byte[] kunciin4 =Arrays.copyOfRange(kuncieksternal,128,160);
        byte[] kunciin5 =Arrays.copyOfRange(kuncieksternal,160,192);
        byte[] kunciin6 =Arrays.copyOfRange(kuncieksternal,192,224);
        byte[] kunciin7 =Arrays.copyOfRange(kuncieksternal,224,256);

        for (int i = 0 ; i < 32 ; i++){
            int modulo = i % 8;
            switch (modulo){
                case 0 : kunciinternal.add(kunciin0); break;
                case 1 : kunciinternal.add(kunciin1); break;
                case 2 : kunciinternal.add(kunciin2); break;
                case 3 : kunciinternal.add(kunciin3); break;
                case 4 : kunciinternal.add(kunciin4); break;
                case 5 : kunciinternal.add(kunciin5); break;
                case 6 : kunciinternal.add(kunciin6); break;
                case 7 : kunciinternal.add(kunciin7); break;
            }
        }
    }

    public void putaranfeistel(){
        byte[] blokkiri = new byte[32];
        byte[] blokkanan = new byte[32];

        int j = 0 ;
        for (int i = 0 ; i < 32 ; i++){
            blokkiri[j] = blokplaintext[i];
            j++;
        }

        int k = 0 ;
        for (int i = 32 ; i < 64 ; i++){
            blokkanan[k] = blokplaintext[i];
            k++;
        }

        for (int putaran = 0 ; putaran < 32 ; putaran++){
            byte[] clonekanan = blokkanan.clone();
            permutasitengah(blokkanan);
            geser7bit(kunciinternal.get(putaran));
            xor(blokkanan,kunciinternal.get(putaran));
            //substitusi here
            zigzag(blokkanan);
            geser10bit(blokkanan);
            permutasitengah(blokkanan);
            xor(blokkanan,blokkiri);
            blokkiri = clonekanan;
        }

        j = 0 ;
        for (int i = 0 ; i < 32 ; i++){
            blokplaintext[i] = blokkiri[j];
            j++;
        }

        k = 0 ;
        for (int i = 32 ; i < 64 ; i++){
            blokplaintext[i] = blokkanan[k];
            k++;
        }

    }



    public byte twobinertodecimal(byte digit1, byte digit2){
        return (byte) (digit1*2 + digit2);
    }


    public void geser7bit(byte[] kunciinternal){
        byte[] clone = kunciinternal.clone();

        for (int i = 0 ; i < kunciinternal.length-7; i++){
            kunciinternal[i] = clone[i+7];
        }
        int j = 0;
        for (int i = kunciinternal.length-7 ; i < kunciinternal.length; i++){
            kunciinternal[i] = clone[j];
            j++;
        }
    }

    public void xor(byte[] blokkanan, byte[] blok2){
        for (int i = 0 ; i < blokkanan.length ; i++){
            blokkanan[i] = (byte) (blokkanan[i] ^ blok2[i]);
        }
    }

    public void printarray(byte[] arr){
        for (int i = 0 ; i < arr.length ; i++){
            System.out.print(arr[i] + ",");
        }
    }
    public void zigzag(byte[] blokkanan){
        byte[] clone = blokkanan.clone();

        //printarray(clone);

        int j = 0 ;
        for (int i = 0 ; i < blokkanan.length; i+=2){
            blokkanan[i] = clone[j];
            j++;
        }

        j = 16;

        for (int i = 1 ; i < blokkanan.length; i+=2){
            blokkanan[i] = clone[j];
            j++;
        }

    }

    void geser10bit(byte[] blokkanan){
        byte[] clone = blokkanan.clone();

        for (int i = 0 ; i < blokkanan.length-10; i++){
            blokkanan[i] = clone[i+10];
        }
        int j = 0;
        for (int i = blokkanan.length-10 ; i < blokkanan.length; i++){
            blokkanan[i] = clone[j];
            j++;
        }
    }

    public void rotasi(){

    }


    public void permutasiawal(){
        byte[] plainclone = blokplaintext.clone();
        for (int i = 0 ; i < blokplaintext.length ; i++){
            blokplaintext[i] = plainclone[tabelpermutasiawal[i]];
        }
    }

    public void permutasiakhir(){
        byte[] plainclone = blokplaintext.clone();
        for (int i = 0 ; i < blokplaintext.length ; i++){
            blokplaintext[i] = plainclone[tabelpermutasiakhir[i]];
        }
    }

    public void permutasitengah(byte[] blokkanan){
        byte[] plainclone = blokkanan.clone();
        for (int i = 0 ; i < blokkanan.length ; i++){
            blokkanan[i] = plainclone[tabelpermutasitengah[i]];
        }
    }

}