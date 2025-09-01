package Exceptions1;

public class PasswordNotValidException extends Exception{
    public PasswordNotValidException(){
        super(e.getMessage());
    }
     // Method to check if a password is valid
     public static boolean isValidPassword(String password) {
        // Regular expression to check for at least one special character
        String specialCharacterPattern = ".*[!@#$%^&*(),.?\":{}|<>].*";
        
        // Check if the password matches the regular expression
        if (password.length() >= 8 && password.matches(specialCharacterPattern)) {
            return true; // Password is valid
        } else {
            return false; // Password is invalid
        }
    }
}
