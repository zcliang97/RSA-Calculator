package fliang97.ca.rsa_calculator;

/**
 * Created by Frank Liang on 2016-07-27.
 */

import android.widget.TextView;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Frank Liang on 2016-07-26.
 */
public class RSA_Calculator {

    private final int KEY_LENGTH = 996;      //A KEY_LENGTH of 996 will yield a prime with approximately 300 digits. (log(10^300)/log(2))

    public Random rand = new Random();
    public TextView tv;

    //Initialize variables:
    public BigInteger e = new BigInteger("0",10);
    public BigInteger d = new BigInteger("0",10);
    public BigInteger n = new BigInteger("0",10);
    public BigInteger message = new BigInteger("0",10);
    public BigInteger cipher = new BigInteger("0",10);

    public RSA_Calculator(TextView tv){
        this.tv = tv;
    }

    public void generateKeys(){
        BigInteger p;   //First prime P
        BigInteger q;   //Second prime Q

        //Generate P and Q:
        do {
            p = BigInteger.probablePrime(KEY_LENGTH, rand);     //Generate a prime with a length "KEY_LENGTH"
        }while(!p.isProbablePrime(100));                        //Check if the BigInteger is a prime, if not, generate another prime

        do{
            q = BigInteger.probablePrime(KEY_LENGTH,rand);      //Same as above
        }while(!q.isProbablePrime(100));


        //Calculate "n" and "fn":
        BigInteger n = p.multiply(q);                                                                   //n = p * q
        BigInteger fn = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));              //fn = (p-1)*(q-1)

        //Find the values for encryption and decryption keys:
        BigInteger en = new BigInteger("65537",10);     //Start at arbitrary odd integer and add 2 every time until gcd(en,fn)=1
        while(en.gcd(fn).intValue()!= 1){
            en.add(new BigInteger("2",10));
        }
        BigInteger de = en.modInverse(fn);          //"de" is just the inverse mod of en and fn.

        //Set these generated values into the public and private keys:
        setEncrytion(en);
        setDecryption(de);
        setN(n);
    }

    //Encrypts message:
    public BigInteger encryptMessage(){
        BigInteger output;
        output = message.modPow(e,n);
        cipher = output;
        return output;
    }

    public BigInteger encryptMessage(BigInteger inputMessage){
        BigInteger output;
        output = inputMessage.modPow(e,n);
        cipher = output;
        return output;
    }

    //Decrypts message:
    public BigInteger decryptMessage(){
        BigInteger output;
        output = cipher.modPow(d,n);
        return output;
    }

    public BigInteger decryptMessage(BigInteger inputCipher){
        BigInteger output;
        output = inputCipher.modPow(d,n);
        return output;
    }

    //Prints values into a TextView display
    public void printValues(){
        tv.setText("Message: " + message + " \nE: " + e + " \nD: " + d + " \nN: " + n + "\n");
    }

    //Returns a string with all the values
    public String getValues(){
        return "Message: " + message + " \nE: " + e + " \nD: " + d + " \nN: " + n + "\n";
    }

    //Sets the encryption key
    public void setEncrytion(BigInteger e){
        this.e = e;
    }

    //Sets the decryption key
    public void setDecryption(BigInteger d){
        this.d = d;
    }

    //Sets the mod value
    public void setN(BigInteger n){
        this.n = n;
    }

    //Sets the input message
    public void inputMessage(BigInteger m){
        this.message = m;
    }

}
