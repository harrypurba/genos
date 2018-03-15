import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
    public byte[] kuncieksternal;


    public byte[] blokplaintext;
    public byte[] blokciphertext = new byte[64];    // hasil enkripsi menjadi cipher
    public byte[] blokplain2 = new byte[64];        // hasil dekrispi menjadi plaintext
    public ArrayList<byte[]> kunciinternal = new ArrayList<>();
    public HashMap<String, byte[]> subbox= new HashMap<String, byte[]>();

    // Konstruktor
    public AlgoritmaGenos(byte[] temp,byte[] kunci){
        blokplaintext = temp;
        kuncieksternal = kunci;
    }

    // Getter
    public byte[] getBlokciphertext(){
        return blokciphertext;
    }

    public void enkripsi(){
        init();
        permutasiawal();
        putaranfeistel();
        permutasiakhir();
        blokciphertext = blokplaintext.clone();
    }
    public void dekripsi(){
        dekrippermutasiawal();
        dekripputaranfeistel();
        dekrippermutasiakhir();
        blokplain2 = blokciphertext.clone();
    }

    public void init(){

        // Pembagian Kunci Internal dari Kunci Eksternal

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

        // Mengisi SubBox

        subbox.put("0000",new byte[]{1,0,0,0});
        subbox.put("0001",new byte[]{1,0,1,0});
        subbox.put("0010",new byte[]{1,0,1,1});
        subbox.put("0011",new byte[]{0,0,0,0});

        subbox.put("0100",new byte[]{1,0,0,1});
        subbox.put("0101",new byte[]{0,0,0,0});
        subbox.put("0110",new byte[]{0,1,1,1});
        subbox.put("0111",new byte[]{1,1,0,1});

        subbox.put("1000",new byte[]{0,1,0,1});
        subbox.put("1001",new byte[]{0,1,1,0});
        subbox.put("1010",new byte[]{1,1,1,1});
        subbox.put("1011",new byte[]{0,0,1,0});

        subbox.put("1100",new byte[]{0,1,0,0});
        subbox.put("1101",new byte[]{1,1,0,0});
        subbox.put("1110",new byte[]{0,0,1,1});
        subbox.put("1111",new byte[]{1,1,1,0});

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
            xor(blokkanan,kunciinternal.get(putaran));
            subsdanrotasi(blokkanan);
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

    public String fourbytetosstring(byte[] arr){
        String res = "";
        for (int i = 0 ; i < arr.length ; i++){
            res += (arr[i]);
        }
        return res;
    }

    public void xor(byte[] blokkanan, byte[] blok2){
        for (int i = 0 ; i < blokkanan.length ; i++){
            blokkanan[i] = (byte) (blokkanan[i] ^ blok2[i]);
        }
    }

    public void subsdanrotasi(byte[] blokkanan){
        byte[] clone = blokkanan.clone();
        for (int i = 0 ; i < blokkanan.length ; i += 4){
            byte[] temp = Arrays.copyOfRange(blokkanan,i,i+4);
            String stemp = fourbytetosstring(temp);

            //substitusi
            byte[] subs = subbox.get(stemp);
            int k = 0;
            for (int j = i ; j < i+4 ; j++){
                blokkanan[j] = subs[k];
                k++;
            }

            //rotasi
            byte[] clone2 = temp.clone();
            blokkanan[i] = clone2[3];
            blokkanan[i+1] = clone2[2];
            blokkanan[i+2] = clone2[1];
            blokkanan[i+3] = clone2[0];
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

    public void printarray(byte[] arr){
        for (int i = 0 ; i < arr.length ; i++){
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

    public int compare64byte(byte[] arr1 , byte[] arr2){
        int count = 0 ;
        for (int i = 0 ; i < arr1.length ; i++){
            if (arr1[i] == arr2[i]){
                count++;
            }
        }
        return count;
    }



    // Dekripsi



    public void dekrippermutasiawal(){
        byte[] cipherclone = blokciphertext.clone();
        for (int i = 0 ; i < blokciphertext.length ; i++){
            blokciphertext[i] = cipherclone[tabelpermutasiawal[i]];
        }
    }

    public void dekrippermutasiakhir(){
        byte[] cipherclone = blokciphertext.clone();
        for (int i = 0 ; i < blokciphertext.length ; i++){
            blokciphertext[i] = cipherclone[tabelpermutasiakhir[i]];
        }
    }

    public void dekripputaranfeistel(){
        byte[] blokkiri = new byte[32];
        byte[] blokkanan = new byte[32];

        int j = 0 ;
        for (int i = 0 ; i < 32 ; i++){
            blokkiri[j] = blokciphertext[i];
            j++;
        }

        int k = 0 ;
        for (int i = 32 ; i < 64 ; i++){
            blokkanan[k] = blokciphertext[i];
            k++;
        }

        for (int putaran = 31 ; putaran >= 0 ; putaran--){
            byte[] clonekiri1 = blokkiri.clone();
            byte[] clonekiri2 = blokkiri.clone();

            permutasitengah(clonekiri1);
            xor(clonekiri1,kunciinternal.get(putaran));
            subsdanrotasi(clonekiri1);
            zigzag(clonekiri1);
            geser10bit(clonekiri1);
            permutasitengah(clonekiri1);
            xor(clonekiri1,blokkanan);

            blokkiri = clonekiri1;
            blokkanan = clonekiri2;

//            System.out.print("Kiri  ");
//            printarray(blokkiri);
//            System.out.print("Kanan ");
//            printarray(blokkanan);


        }
        j = 0 ;
        for (int i = 0 ; i < 32 ; i++){
            blokciphertext[i] = blokkiri[j];
            j++;
        }
        k = 0 ;
        for (int i = 32 ; i < 64 ; i++){
            blokciphertext[i] = blokkanan[k];
            k++;
        }
    }
}