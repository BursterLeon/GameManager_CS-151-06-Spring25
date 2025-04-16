package utils;

public class utility {
    public class Utility {
        private Utility() {}
        public static boolean isNullOrWhiteSpace(String str) {
            return str == null || str.trim().isEmpty();
        }
    }
}
