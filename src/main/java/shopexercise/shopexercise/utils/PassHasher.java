package shopexercise.shopexercise.utils;


import org.mindrot.jbcrypt.BCrypt;

public class PassHasher {
    public static String hashPassword(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public static boolean checkPass(String pass, String hashPass) {
        return BCrypt.checkpw(pass, hashPass);
    }

}
