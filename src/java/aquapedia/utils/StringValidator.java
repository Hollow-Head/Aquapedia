package aquapedia.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator {

    private static final String EMAIL_REGEX = "^[\\w|ç]+@[\\w|ç]+\\..+";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    static final String LOGIN_ERROR_MESSAGE = "Email e/ou senha está incorreto.";

    /**
     * Checks if the email is valid using the following REGEX: {@code ^[\\w|ç]+@[\\w|ç]+\\..+}
     * @param email The email of the user.
     * @return {@code null} if the email is valid, {@code String} otherwise, containing the specific error.
     */
    public static String isEmailValid(String email){
        if (email == null || email.isEmpty()) {
            return "O email não pode estar vazio";
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (matcher.matches()){
            return null;
        }
        return "O email está inválido";
    }

    /**
     * For a valid password, the password needs to have at least 1 uppercase, 1 lowercase, 1 number, 1 simbol and can't be null.
     * and the length be between 8 and 60.
     * @param password The user password.
     * @return {@code null} if the password is valid, {@code String} otherwise, containing the specific error.
     */
    
    public static String isPasswordValid(String password){
        if (password == null || password.isEmpty()){
            return "A senha não pode estar vazia";
        } else if (password.length() < 8 && password.length() > 60){
            return "O tamanho da senha deve ser entre 8 e 60 caracteres";
        } else if (!password.matches(".*[A-Z].*")){
            return "A senha deve ter ao menos 1 letra maiúscula";
        } else if (!password.matches(".*[a-z].*")){
            return "A senha deve ter ao menos 1 letra minúscula";
        } else if(!password.matches(".*\\d.*")){
            return "A senha deve ter ao menos 1 número";
        } else if(!password.matches(".*[!@#$%^&*()_+=\\-{};:'\",.<>?/\\\\|`~].*")){
            return "A senha deve conter ao menos 1 símbolo";
        }
        return null; // Is valid.
    }

    /**
     * Validate if the nickname is valid, the length has to be between 3 and 30 characters, can't contains spaces and can't contain symbols.
     * @param nickname The nickname of the user.
     * @return {@code null} if the nickname is valid, {@code String} otherwise, containing the specific error.
     */
    public static String isNicknameValid(String nickname){
        if (nickname == null){
            return "A senha não pode estar vazia";
        } else if (nickname.length() < 3 && nickname.length() > 30){
            return "O apelido deve conter entre 3 e 30 caracteres";
        } else if(nickname.contains(" ")){
            return "O apelido não pode conter espaços";
        } else if(nickname.matches(".*[!@#$%^&*()_+=\\-{};:'\",.<>?/\\\\|`~].*")){
            return "O apelido não pode conter símbolos";
        }
        return null; // Is valid.
    }

    /**
     * Check if both passwords are equal.
     * @param password1 The first password;
     * @param password2 The second password;
     * @return {@code null} if the passwords are equal, {@code String} otherwise, containing the specific error.
     */
    public static String arePasswordsEqual(String password1, String password2){
        if (password1.equals(password2)){
            return null;
        }
        return "As senhas devem ser semelhantes";
    }

    /**
     * Check if the nicknames are equals or if the emails are equals.
     * @param nickname1 First nickname;
     * @param nickname2 Second nickname;
     * @param email1 First email;
     * @param email2 Second email;
     * @return {@code null} if is valid to register, {@code String} otherwise, containing the specific error.
     */
    public static String isValidToRegister(String nickname1, String nickname2, String email1, String email2){
        if (nickname1.equals(nickname2)){
            return "Já existe um usuário com este apelido.";
        }
        if (email1.equals(email2)){
            return "Já existe um usuário com este email.";
        }

        return null;
    }

    /**
     * Validate if both string used in the email or password of the login are equal.
     * @param str1 The first string;
     * @param str2 The second string;
     * @return {@code null} if is valid, {@code String} otherwise, containing the error message.
     */
    public static String isValidToLogin(String str1, String str2){
        return str1.equals(str2) ? null : LOGIN_ERROR_MESSAGE;
    }

}
