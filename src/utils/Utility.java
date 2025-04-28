package utils;

//class that includes different static methods that are not directly connected to the game manager
public class Utility {
        private Utility() {}
        public static boolean isNullOrWhiteSpace(String str) {
            return str == null || str.trim().isEmpty();
        }

        public static boolean isValidInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //password hashing
    // 1. gets each char from the string 2. get a new int by using the hash function
    // 3. convert the int to a char again 4. append the new char to the hashedPassword String
    //visible character: 32 - 126
    public static String hashingPassword (String password) {
            StringBuilder hashedPassword = new StringBuilder();
            //String hashedPassword = "";
            for (int i = 0; i<password.length(); i++) {
                char ch = password.charAt(i);
                int hashedCharNumber = 32 + ((ch^97345)%95);
                hashedPassword.append((char)hashedCharNumber);

                System.out.println(hashedCharNumber);
                System.out.println((char)hashedCharNumber);
            }
            return hashedPassword.toString();
    }
}
