
import javax.swing.*;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random ;
import java.util.Scanner;
import java.math.BigInteger;
class Blowfish {
    byte[] skey = new byte[1000];
    String skeyString;
    static byte[] raw;
    public String Blowfish(String args[]) {
        String key;
        
    String inputMessage="";String encryptedData="";String decryptedMessage="";
        try {
            //  generateSymmetricKey();

            //  inputMessage=JOptionPane.showInputDialog(null,"Enter message to encrypt");
            //return args[0];
            inputMessage=args[0];
            key=args[1];
           //System.out.println(" key="+key+" message="+inputMessage);
            byte[] ibyte = inputMessage.getBytes();
            int num;
            BigInteger d,n,phi,u,a,e1,p,q,ua,b,c,v;
            int j;
            String encrypt="";
            BigInteger t=new BigInteger("1");
            p=new BigInteger("11");
            q=new BigInteger("7");
            n=p.multiply(q);
            phi=(p.subtract(t)).multiply(q.subtract(t));
            e1=new BigInteger("7");
            d=new BigInteger("43");
            u=new BigInteger("3");
            a=new BigInteger("31");
            ua=u.pow(31);
            BigInteger[]arr =new BigInteger[100];
            BigInteger[]dearr =new BigInteger[100];
            BigInteger[]ekey =new BigInteger[100];
            BigInteger[]enkey =new BigInteger[100];
            int i1,i2;String f_key="";
            for(j=0;j<key.length();j++)
            {
                ekey[j]=BigInteger.valueOf((int)key.charAt(j));
            }
            for(j=0;j<key.length();j++)
            {
                b=(ekey[j].multiply(ua)).pow(7);
                enkey[j]=b.mod(n);
            }
            for(j=0;j<key.length();j++)
            {
                f_key=f_key+Integer.toString(enkey[j].intValue());
            }
            raw=f_key.getBytes();
            byte[] ebyte=encrypt(raw, ibyte);
            encryptedData = new String(ebyte);
          System.out.println(encryptedData);
            //JOptionPane.showMessageDialog(null,"Encrypted Data "+"\n"+encryptedData);

            byte[] dbyte= decrypt(raw,ebyte);
             decryptedMessage = new String(dbyte);
           //System.out.println("Decrypted message "+decryptedMessage);
           }
        catch(Exception e) {
            System.out.println(e);
        }

            return "";
    }

    void generateSymmetricKey() {
        try {
            Random r = new Random();
            int num = r.nextInt(10000);
            String knum = String.valueOf(num);
            byte[] knumb = knum.getBytes();
            skey=getRawKey(knumb);
            skeyString = new String(skey);
            //System.out.println("Blowfish Symmetric key = "+skeyString);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 128, 256 and 448 bits may not be available
        SecretKey skey = kgen.generateKey();
        raw = skey.getEncoded();
        return raw;
    }
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static void main(String args[]) {
        Blowfish bf = new Blowfish();
        bf.Blowfish(args);
    }
}