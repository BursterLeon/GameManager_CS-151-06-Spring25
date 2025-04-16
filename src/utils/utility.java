package utils;

public class utility {
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
    }
}
