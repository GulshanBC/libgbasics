package ml.gulshanbc.gbasics;

import java.util.ArrayList;
import java.util.ArrayList;

public class GString {

    String[] suffixesForVariants = {"s", "es", "'s", "s'", "ful", "er", "est", "ness", "d", "ed"};//dog==>dogs, do==>does
    String[] suffixesWithLastLetterRepeated = {"y"};//dog==>doggy
    String[][] suffixesWithReplacement = {{"y", "ies"}, {"ies", "y"}};//city==>cities, cities==>city

    private String baseString;
    
    public static boolean flagDebug = false;

    public GString(String string) {
        baseString = string;
    }
    
    public static String[] toStringArray(GString[] gstringArray){
        String[] stringArray = new String[gstringArray.length];
        for( int i = 0 ; i< gstringArray.length ; i++ )
            stringArray[i] = gstringArray[i].toString();
        return stringArray;
    }

    public String toString() {
        return baseString;
    }

    public void setString(String string) {
        baseString = string;
    }

    public GString toUpperCaseFirstOnly() {
        String result = "";
        if (baseString.length() <= 0) {
            return null;
        }
        if (!Character.isLetter(baseString.charAt(0))) {
            result += Character.toString(baseString.charAt(0));
        } else if (Character.isUpperCase(baseString.charAt(0))) {
            result += Character.toString(baseString.charAt(0));
        } else if (Character.isLetter(baseString.charAt(0))) {
            result += Character.toString((char) (baseString.charAt(0) - 32));
        } else {
            result += Character.toString((char) (baseString.charAt(0)));
        }
        for (int i = 1; i < baseString.length(); i++) {
            if (!Character.isLetter(baseString.charAt(i))) {
                result += Character.toString(baseString.charAt(i));
            } else if (Character.isLowerCase(baseString.charAt(i))) {
                result += Character.toString(baseString.charAt(i));
            } else {
                result += Character.toString((char) (baseString.charAt(i) + 32));
            }
        }

        return new GString(result);
    }

    public static String toUpperCaseFirstOnly(String word) {
        String result = "";
        if (word.length() <= 0) {
            return "";
        }
        if (!Character.isLetter(word.charAt(0))) {
            result += Character.toString(word.charAt(0));
        } else if (Character.isUpperCase(word.charAt(0))) {
            result += Character.toString(word.charAt(0));
        } else if (Character.isLetter(word.charAt(0))) {
            result += Character.toString((char) (word.charAt(0) - 32));
        } else {
            result += Character.toString((char) (word.charAt(0)));
        }
        for (int i = 1; i < word.length(); i++) {
            if (!Character.isLetter(word.charAt(i))) {
                result += Character.toString(word.charAt(i));
            } else if (Character.isLowerCase(word.charAt(i))) {
                result += Character.toString(word.charAt(i));
            } else {
                result += Character.toString((char) (word.charAt(i) + 32));
            }
        }

        return result;
    }

    public GString toUpperCaseFirstLetter()//only process first letter and leave remaining as it is
    {
        String result = "";
        if (baseString.length() <= 0) {
            return null;
        }
        result = Character.toUpperCase(baseString.charAt(0)) + baseString.substring(1, baseString.length());

        return new GString(result);
    }

    public static String toUpperCaseFirstLetter(String word)//only process first letter and leave remaining as it is
    {
        String result = "";
        if (word.length() <= 0) {
            return "";
        }
        result = Character.toUpperCase(word.charAt(0)) + word.substring(1, word.length());

        return result;
    }

    /**
     * Remove all other chars before occurrence of English alphabet
	 * *
     */
    public GString makeStringBeginWithLetter() {
        String result = "";
        int indexOfFirstLetter = 0;
        for (int i = 0; i < baseString.length(); i++) {
            if (Character.isLetter(baseString.charAt(i))) {
                indexOfFirstLetter = i;
                break;
            }
        }
        result = baseString.substring(indexOfFirstLetter, baseString.length());
        return new GString(result);
    }

    /**
     * Remove all other chars before occurrence of English alphabet
	 * *
     */
    public static String makeStringBeginWithLetter(String word) {
        String result = "";
        int indexOfFirstLetter = 0;
        for (int i = 0; i < word.length(); i++) {
            if (Character.isLetter(word.charAt(i))) {
                indexOfFirstLetter = i;
                break;
            }
        }
        result = word.substring(indexOfFirstLetter, word.length());
        return result;
    }

    /**
     * Remove all other chars before occurrence of English alphabet or one of
     * the supplied chars
	 * *
     */
    public GString makeStringBeginWithLetterPlus(String additionalChars) {
        String result = "";
        int indexOfFirstLetter = 0;
        for (int i = 0; i < baseString.length(); i++) {
            if (Character.isLetter(baseString.charAt(i)) || isOneOfThese(baseString.charAt(i), additionalChars)) {
                indexOfFirstLetter = i;
                break;
            }
        }
        result = baseString.substring(indexOfFirstLetter, baseString.length());
        return new GString(result);
    }

    /**
     * Remove all other chars before occurrence of English alphabet or one of
     * the supplied chars
	 * *
     */
    public static String makeStringBeginWithLetterPlus(String word, String additionalChars) {
        String result = "";
        int indexOfFirstLetter = 0;
        for (int i = 0; i < word.length(); i++) {
            if (Character.isLetter(word.charAt(i)) || isOneOfThese(word.charAt(i), additionalChars)) {
                indexOfFirstLetter = i;
                break;
            }
        }
        result = word.substring(indexOfFirstLetter, word.length());
        return result;
    }

    /**
     * Remove all other chars before occurrence of one of the supplied chars
	 * *
     */
    public GString makeStringBeginWithOneOfThese(String additionalChars) {
        String result = "";
        int indexOfFirstLetter = 0;
        for (int i = 0; i < baseString.length(); i++) {
            if (isOneOfThese(baseString.charAt(i), additionalChars)) {
                indexOfFirstLetter = i;
                break;
            }
        }
        result = baseString.substring(indexOfFirstLetter, baseString.length());
        return new GString(result);
    }

    /**
     * Remove all other chars before occurrence of one of the supplied chars
	 * *
     */
    public static String makeStringBeginWithOneOfThese(String word, String additionalChars) {
        String result = "";
        int indexOfFirstLetter = 0;
        for (int i = 0; i < word.length(); i++) {
            if (isOneOfThese(word.charAt(i), additionalChars)) {
                indexOfFirstLetter = i;
                break;
            }
        }
        result = word.substring(indexOfFirstLetter, word.length());
        return result;
    }

    public static String escapeRegexChars(String word) {
        String[] regexSymbols = {"|", "{", "}", "[", "]", "+", "(", ")", "^", "."};
        for (String regexSymbol : regexSymbols) {
            if (word.contains(regexSymbol)) {
                word = word.replaceAll("\\" + regexSymbol, "\\\\" + regexSymbol);//first argument is treated as regex , while second argument is simply string, thus, replacing regex symbols with escaped string, gives us complete escaped regex string			
            }
        }
        return word;
    }

    public GString removeAllNonEnglishAlphabets() {
        String result = "";
        for (int i = 0; i < baseString.length(); i++) {
            if (isEnglishAlphabet(baseString.charAt(i))) {
                result += baseString.charAt(i);
            }
        }

        return new GString(result);
    }

    public static String removeAllNonEnglishAlphabets(String baseString) {
        String result = "";
        for (int i = 0; i < baseString.length(); i++) {
            if (isEnglishAlphabet(baseString.charAt(i))) {
                result += baseString.charAt(i);
            }
        }

        return result;
    }

    /**
     * Allows non English alphabets
	 * *
     */
    public GString removeAllNonAlphabets() {
        String result = "";
        for (int i = 0; i < baseString.length(); i++) {
            if (Character.isAlphabetic(baseString.charAt(i))) {
                result += baseString.charAt(i);
            }
        }

        return new GString(result);
    }

    /**
     * Allows non English alphabets
	 * *
     */
    public static String removeAllNonAlphabets(String baseString) {
        String result = "";
        for (int i = 0; i < baseString.length(); i++) {
            if (Character.isAlphabetic(baseString.charAt(i))) {
                result += baseString.charAt(i);
            }
        }

        return result;
    }

    public static boolean areSubSet(String string1, String string2) {
        String str1lc = string1.toLowerCase();//there's no fuction like containsignorecase, so turning both to lowercase
        String str2lc = string2.toLowerCase();//subParts[1]
        if (str1lc.contains(str2lc) || str2lc.contains(str1lc))//[[w:Arrows Kipling|Arrows Kipling]], subParts[0]=w, subParts[1]=Arrows Kipling, values[l+1]=Arrows Kipling(here l=0, so l+1 is 1),means duplicate present in next section,so don't show this section 
        {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if they are subsets by removing any non English alphabets present
     * in them
	 * *
     */
    public static boolean areEglishAlphabeticSubSet(String string1, String string2) {
        String str1lc = string1.toLowerCase();//there's no fuction like containsignorecase, so turning both to lowercase
        String str2lc = string2.toLowerCase();//subParts[1]
        str1lc = GString.removeAllNonEnglishAlphabets(str1lc);
        str2lc = GString.removeAllNonEnglishAlphabets(str2lc);
        if (str1lc.contains(str2lc) || str2lc.contains(str1lc))//[[w:Arrows Kipling|Arrows Kipling]], subParts[0]=w, subParts[1]=Arrows Kipling, values[l+1]=Arrows Kipling(here l=0, so l+1 is 1),means duplicate present in next section,so don't show this section 
        {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if they are subsets by removing any non alphabetic present in them
	 * *
     */
    public static boolean areAlphabeticSubSet(String string1, String string2) {
        String str1lc = string1.toLowerCase();//there's no fuction like containsignorecase, so turning both to lowercase
        String str2lc = string2.toLowerCase();//subParts[1]
        str1lc = GString.removeAllNonAlphabets(str1lc);
        str2lc = GString.removeAllNonAlphabets(str2lc);
        if (str1lc.contains(str2lc) || str2lc.contains(str1lc))//[[w:Arrows Kipling|Arrows Kipling]], subParts[0]=w, subParts[1]=Arrows Kipling, values[l+1]=Arrows Kipling(here l=0, so l+1 is 1),means duplicate present in next section,so don't show this section 
        {
            return true;
        } else {
            return false;
        }
    }

    public GString capitalizeFirstCharOfEachWordInString(String separator) {
        try {
            String result = "";
            int index_of_left_separator;
            int index_of_right_separator;
            if (baseString.indexOf(separator) < 0) {
                return new GString(toUpperCaseFirstOnly(baseString));
            }
            index_of_left_separator = -1;//the code just below if statement adds 1 to the index_of_left_separator and to make it 0 for first char , we need to set its value to -1
            while (true) {
                index_of_right_separator = baseString.indexOf(separator, index_of_left_separator + 1);
                if (index_of_right_separator < 0) {
                    result += separator + toUpperCaseFirstOnly(baseString.substring(index_of_left_separator + 1, baseString.length()));
                    break;
                }
                result += separator + toUpperCaseFirstOnly(baseString.substring(index_of_left_separator + 1, index_of_right_separator));
                index_of_left_separator = index_of_right_separator;
            }
            return new GString(result.substring(1, result.length()));//because we add separator at begining of baseString in above code
        } catch (Exception e) {
            System.out.print(e);
            return null;
        }
    }

    public static String capitalizeFirstCharOfEachWordInString(String word, String separator) {
        try {
            String result = "";
            int index_of_left_separator;
            int index_of_right_separator;
            if (word.indexOf(separator) < 0) {
                return toUpperCaseFirstOnly(word);
            }
            index_of_left_separator = -1;//the code just below if statement adds 1 to the index_of_left_separator and to make it 0 for first char , we need to set its value to -1
            while (true) {
                index_of_right_separator = word.indexOf(separator, index_of_left_separator + 1);
                if (index_of_right_separator < 0) {
                    result += separator + toUpperCaseFirstOnly(word.substring(index_of_left_separator + 1, word.length()));
                    break;
                }
                result += separator + toUpperCaseFirstOnly(word.substring(index_of_left_separator + 1, index_of_right_separator));
                index_of_left_separator = index_of_right_separator;
            }
            return result.substring(1, result.length());//because we add separator at begining of word in above code
        } catch (Exception e) {
            System.out.print(e);
            return null;
        }
    }

    public static boolean isVowelLowerCase(char character) {
        if (character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u') {
            return true;
        }
        return false;
    }

    public static boolean isVowelUpperCase(char character) {
        if (character == 'A' || character == 'E' || character == 'I' || character == 'O' || character == 'U') {
            return true;
        }
        return false;
    }

    public GString toUpperCaseVowelsOnly() {
        String result = "";
        String word = baseString.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            if (isVowelLowerCase(word.charAt(i)) && Character.isLetter(word.charAt(i))) {
                result += (Character.toString((char) (word.charAt(i) - 32)));
            } else if (Character.isLowerCase(word.charAt(i)) && Character.isLetter(word.charAt(i))) {
                result += Character.toString(word.charAt(i));
            } //else result += Character.toString( (char)(word.charAt(i) + 32 ) ) ;
            else if (Character.isLetter(word.charAt(i))) {
                result += Character.toString((char) (word.charAt(i) + 32));
            } else {
                result += Character.toString((char) (word.charAt(i)));
            }
        }

        return new GString(result);
    }

    public static String toUpperCaseVowelsOnly(String word) {
        String result = "";
        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            if (isVowelLowerCase(word.charAt(i)) && Character.isLetter(word.charAt(i))) {
                result += (Character.toString((char) (word.charAt(i) - 32)));
            } else if (Character.isLowerCase(word.charAt(i)) && Character.isLetter(word.charAt(i))) {
                result += Character.toString(word.charAt(i));
            } //else result += Character.toString( (char)(word.charAt(i) + 32 ) ) ;
            else if (Character.isLetter(word.charAt(i))) {
                result += Character.toString((char) (word.charAt(i) + 32));
            } else {
                result += Character.toString((char) (word.charAt(i)));
            }
        }

        return result;
    }

    public GString toUpperCaseConsonantsOnly() {
        String result = "";
        String word = baseString.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            if (isVowelLowerCase(word.charAt(i)) && Character.isLetter(word.charAt(i))) {
                result += Character.toString(word.charAt(i));
            } else if (isVowelUpperCase(word.charAt(i)) && Character.isLetter(word.charAt(i))) {
                result += Character.toString((char) (word.charAt(i) + 32));
            } else if (Character.isLowerCase(word.charAt(i)) && Character.isLetter(word.charAt(i))) {
                result += (Character.toString((char) (word.charAt(i) - 32)));
            } else {
                result += Character.toString(word.charAt(i));
            }
        }

        return new GString(result);
    }

    public GString toUpperCaseAlternateFromFirst() {
        String result = "";

        for (int i = 0; i < baseString.length(); i++) {
            if (i % 2 == 0) {
                if (Character.isLowerCase(baseString.charAt(i)) && Character.isLetter(baseString.charAt(i))) {
                    result += Character.toString((char) (baseString.charAt(i) - 32));
                } else {
                    result += Character.toString(baseString.charAt(i));
                }
            } else {
                if (Character.isUpperCase(baseString.charAt(i)) && Character.isLetter(baseString.charAt(i))) {
                    result += Character.toString((char) (baseString.charAt(i) + 32));
                } else {
                    result += Character.toString(baseString.charAt(i));
                }
            }
        }

        return new GString(result);
    }

    public GString toUpperCaseAlternateFromSecond() {
        String result = "";

        for (int i = 0; i < baseString.length(); i++) {
            if (i % 2 == 0) {
                if (Character.isLowerCase(baseString.charAt(i)) && Character.isLetter(baseString.charAt(i))) {
                    result += Character.toString(baseString.charAt(i));
                } else if (Character.isLetter(baseString.charAt(i))) {
                    result += Character.toString((char) (baseString.charAt(i) + 32));
                } else {
                    result += Character.toString((char) (baseString.charAt(i)));
                }
            } else {
                if (Character.isUpperCase(baseString.charAt(i)) && Character.isLetter(baseString.charAt(i))) {
                    result += Character.toString(baseString.charAt(i));
                } else if (Character.isLetter(baseString.charAt(i))) {
                    result += Character.toString((char) (baseString.charAt(i) - 32));
                } else {
                    result += Character.toString((char) (baseString.charAt(i)));
                }
            }
        }

        return new GString(result);
    }

    public GString toUpperCaseFirstAndLastOnly() {
        String result = "";

        if (!Character.isLetter(baseString.charAt(0))) {
            result += Character.toString(baseString.charAt(0));
        } else if (Character.isUpperCase(baseString.charAt(0))) {
            result += Character.toString(baseString.charAt(0));
        } else {
            result += Character.toString((char) (baseString.charAt(0) - 32));
        }

        for (int i = 1; i < baseString.length() - 1; i++) {
            if (!Character.isLetter(baseString.charAt(i))) {
                result += Character.toString(baseString.charAt(i));
            } else if (Character.isLowerCase(baseString.charAt(i))) {
                result += Character.toString(baseString.charAt(i));
            } else {
                result += Character.toString((char) (baseString.charAt(i) + 32));
            }
        }

        if (!Character.isLetter(baseString.charAt(baseString.length() - 1))) {
            result += Character.toString(baseString.charAt(baseString.length() - 1));
        } else if (Character.isUpperCase(baseString.charAt(baseString.length() - 1))) {
            result += Character.toString(baseString.charAt(baseString.length() - 1));
        } else {
            result += Character.toString((char) (baseString.charAt(baseString.length() - 1) - 32));
        }

        return new GString(result);
    }

    public GString toLowerCaseFirstOnly() {
        String result = "";
        if (baseString.length() <= 0) {
            return new GString(result);
        }
        if (!Character.isLetter(baseString.charAt(0))) {
            result += Character.toString(baseString.charAt(0));
        } else if (Character.isLowerCase(baseString.charAt(0))) {
            result += Character.toString(baseString.charAt(0));
        } else {
            result += Character.toString((char) (baseString.charAt(0) + 32));
        }

        for (int i = 1; i < baseString.length(); i++) {
            if (!Character.isLetter(baseString.charAt(i))) {
                result += Character.toString(baseString.charAt(i));
            } else if (Character.isUpperCase(baseString.charAt(i))) {
                result += Character.toString(baseString.charAt(i));
            } else {
                result += Character.toString((char) (baseString.charAt(i) - 32));
            }
        }

        return new GString(result);
    }

    public GString toLowerCaseFirstAndLastOnly(String baseString) {
        String result = "";
        if (baseString.length() <= 0) {
            return new GString(result);
        }
        if (!Character.isLetter(baseString.charAt(0))) {
            result += Character.toString(baseString.charAt(0));
        } else if (Character.isLowerCase(baseString.charAt(0))) {
            result += Character.toString(baseString.charAt(0));
        } else {
            result += Character.toString((char) (baseString.charAt(0) + 32));
        }

        for (int i = 1; i < baseString.length() - 1; i++) {
            if (!Character.isLetter(baseString.charAt(i))) {
                result += Character.toString(baseString.charAt(i));
            } else if (Character.isUpperCase(baseString.charAt(i))) {
                result += Character.toString(baseString.charAt(i));
            } else {
                result += Character.toString((char) (baseString.charAt(i) - 32));
            }
        }

        if (!Character.isLetter(baseString.charAt(baseString.length() - 1))) {
            result += Character.toString(baseString.charAt(baseString.length() - 1));
        } else if (Character.isLowerCase(baseString.charAt(baseString.length() - 1))) {
            result += Character.toString(baseString.charAt(baseString.length() - 1));
        } else {
            result += Character.toString((char) (baseString.charAt(baseString.length() - 1) + 32));
        }

        return new GString(result);
    }

    public static boolean areAllCharsWhitespace(String line) {
        boolean result = true;

        for (int i = 0; i < line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                result = false;
                break;
            }
        }
        return result;
    }
    
    public static String removeAllWhitespaces(String line) {
        String result = "";

        for (int i = 0; i < line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                result += line.charAt(i);                
            }
        }
        return result;
    }
    
    public static boolean containsAtleastOneEnglishAlphabet(String line) {
        if (line == null || line.length() <= 0) {
            return false;
        }
        for (int i = 0; i < line.length(); i++) {
            if (isEnglishAlphabet(line.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsOnlyValidEnglishChars(String line) {
        if (line == null || line.length() <= 0) {
            return false;
        }
        for (int i = 0; i < line.length(); i++) {
            if (!isValidCharForEnglishWordOrExpression(line.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function checks if the char is Letter,Whitespace or one of ,.'-_ if
     * yes,returns true else returns false
     *
     * @param ch
     * @return boolean
     */
    public static boolean isValidCharForEnglishWordOrExpression(char ch) {
        if (isEnglishAlphabet(ch)) {
            return true;
        } else if (Character.isWhitespace(ch)) {
            return true;
        }

        String otherValidChars = ",.'-_";
        for (int i = 0; i < otherValidChars.length(); i++) {
            if (ch == otherValidChars.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function checks if the char is one of
     * "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" if yes,returns
     * true else returns false
     *
     * @param ch
     * @return boolean
     */
    public static boolean isOneOfThese(char ch, String validChars) {
        for (int i = 0; i < validChars.length(); i++) {
            if (ch == validChars.charAt(i)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This function checks if the char is one of
     * "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" if yes,returns
     * true else returns false
     *
     * @param ch
     * @return boolean
     */
    public static boolean isEnglishAlphabet(char ch) {
        /*String otherValidChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for( int i = 0 ; i < otherValidChars.length(); i++)
		{
			if( ch == otherValidChars.charAt(i) ) return true;
		}*/
        if (ch >= 65 && ch <= 90) {
            return true;
        } else if (ch >= 97 && ch <= 122) {
            return true;
        }
        return false;
    }

    public boolean hasAllWords(String text) {
        String[] words = text.split(" ");
        return hasAllWords(words);
    }

    public boolean hasAllWords(String[] words) {
        for (String word : words) {
            if (!hasWord(word)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Instead of boolean, it returns num of words from text that occurred in
     * the filename
	 * *
     */
    public int hasSomeWords(String text) {
        String[] words = text.split(" ");
        return hasSomeWords(words);
    }

    /**
     * Instead of boolean, it returns num of words from text that occurred in
     * the filename
	 * *
     */
    public int hasSomeWords(String[] words) {
        int countOfMatchedWords = 0;
        for (String word : words) {
            if (hasWord(word)) {
                countOfMatchedWords++;
            }
        }
        return countOfMatchedWords;
    }

    /**
     * If the supplied word occurs in one of the specified formats , it return
     * true else false say , word is cat , if filename has either "/cat." or
     * "/cat_" or "_cat_" or "_cat." or "/cat-" or "-cat-" or "-cat." or "/cat "
     * or " cat " or " cat." it returns true; if it is in any of the above form
     * , it means , its a single word, not part of other words like cathedral!!!
     *
     * @param filename
     * @param word
     * @return
     */
    public boolean hasWord(String word) {
        if (baseString.contains("/" + word + ".") || baseString.contains("/" + word + "_") || baseString.contains("_" + word + "_") || baseString.contains("_" + word + ".") || baseString.contains("/" + word + "-") || baseString.contains("-" + word + "-") || baseString.contains("-" + word + ".") || baseString.contains("/" + word + " ") || baseString.contains(" " + word + " ") || baseString.contains(" " + word + ".")) {
            return true;
        }
        return false;
    }

    /**
     * If the supplied word occurs as a whole, not part of someother word, then
     * it returns true else returns false
     *
     * @param filename
     * @param word
     * @return
     */
    public boolean hasWord(String word, String delimiter) {
        String[] words = baseString.split(delimiter);

        for (String word1 : words) {
            if (word1.equalsIgnoreCase(word)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This function checks if the filename contains one of the possible
     * variants of the word returns false when none of the variants(including
     * original word) is present returns true if at least one variant is present
	 * *
     */
    public boolean hasOneOfItsVarients(String word) {
        if (hasWord(word)) {
            return true;
        }
        String generatedWord = "";
        /*generating variant by adding suffix*/
        for (String suffix : suffixesForVariants) {
            //cause if baseString's length is greater than suffix , it could already contain it
            if (baseString.length() > suffix.length()) {
                if (!baseString.substring(baseString.length() - suffix.length()).equalsIgnoreCase(suffix)) {
                    if (hasWord(baseString + suffix)) {
                        return true;
                    }
                } else//cause if it already contains suffix, we generate probable word without suffix
                {
                    generatedWord = baseString.substring(0, baseString.length() - suffix.length());
                    if (hasWord(generatedWord)) {
                        return true;
                    }
                }
            }//cause if baseString's length is less than suffix , there's no way, it could already contain it
            else if (hasWord(baseString + suffix)) {
                return true;
            }

        }

        for (String suffix : suffixesWithLastLetterRepeated) {
            if (!baseString.substring(baseString.length() - suffix.length()).equalsIgnoreCase(suffix)) {
                generatedWord = baseString + baseString.charAt(baseString.length() - 1) + suffix;
                if (hasWord(generatedWord)) {
                    return true;
                }
            }
        }

        for (String[] suffixwithreplacement : suffixesWithReplacement) {
            if (baseString.length() > suffixwithreplacement[0].length())//cause, its only possible for a suffix to be present,if word is longer  than it
            {
                if (baseString.substring(baseString.length() - suffixwithreplacement[0].length()).equalsIgnoreCase(suffixwithreplacement[0])) {
                    generatedWord = baseString.substring(0, baseString.length() - suffixwithreplacement[0].length()) + suffixwithreplacement[1];
                    if (hasWord(generatedWord)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }    

    public GString[] getVarients() {
        ArrayList<String> generatedvariants = new ArrayList<String>();
        String generatedWord = "";
        /*generating variant by adding suffix*/
        for (String suffix : suffixesForVariants) {
            //cause if baseString's length is greater than suffix , it could already contain it
            if (baseString.length() > suffix.length()) {
                if (!baseString.substring(baseString.length() - suffix.length()).equalsIgnoreCase(suffix)) {
                    generatedvariants.add(baseString + suffix);
                } else//cause if it already contains suffix, we generate probable word without suffix
                {
                    generatedWord = baseString.substring(0, baseString.length() - suffix.length());
                    generatedvariants.add(generatedWord);
                }
            }//cause if baseString's length is less than suffix , there's no way, it could already contain it
            else {
                generatedvariants.add(baseString + suffix);
            }

        }

        for (String suffix : suffixesWithLastLetterRepeated) {
            if (!baseString.substring(baseString.length() - suffix.length()).equalsIgnoreCase(suffix)) {
                generatedWord = baseString + baseString.charAt(baseString.length() - 1) + suffix;
                generatedvariants.add(generatedWord);
            }
        }

        for (String[] suffixwithreplacement : suffixesWithReplacement) {
            if (baseString.length() > suffixwithreplacement[0].length())//cause, its only possible for a suffix to be present,if word is longer  than it
            {
                if (baseString.substring(baseString.length() - suffixwithreplacement[0].length()).equalsIgnoreCase(suffixwithreplacement[0])) {
                    generatedWord = baseString.substring(0, baseString.length() - suffixwithreplacement[0].length()) + suffixwithreplacement[1];
                    generatedvariants.add(generatedWord);
                }
            }
        }

        GString[] variants = new GString[generatedvariants.size()];
        for (int i = 0; i < generatedvariants.size(); i++) {
            variants[i] = new GString(generatedvariants.get(i));
        }
        return variants;
    }

    /**
     * This function returns words composed of english letters only ex: if line
     * is "The_quick_brown-fox jumps over (the) lazy dog's. 101 dalmations dateOfBirthCombinations
     * 42hero" it will return array of
     * The,quick,brown,fox,jumps,over,the,lazy,dog,s,damations,hero,date,of,birth,combinations
     * Note: transition from lowercase to uppercase is treated as word separation, ex: dateOfBirthCombinations -> date,of,birth,combinations
	 *
     */
    public static GString[] getWords(String line) {
        ArrayList<String> words = new ArrayList<String>();
        GString[] resultingWords;
        String currentWord = "";
        char prevChar='0';        
        for (int i = 0; i < line.length(); i++) {
            if( GString.isEnglishAlphabet(prevChar) && Character.isLowerCase(prevChar) && Character.isUpperCase(line.charAt(i)) )
            {
                if (currentWord.length() > 0) {
                    words.add(currentWord);
                }
                currentWord = "";
            }
            if (GString.isEnglishAlphabet(line.charAt(i))) {
                currentWord += String.valueOf(line.charAt(i));
            } else {
                if (currentWord.length() > 0) {
                    words.add(currentWord);
                }
                currentWord = "";
            }
            prevChar = line.charAt(i);
        }
        if (currentWord.length() > 0) {
            words.add(currentWord);
        }

        resultingWords = new GString[words.size()];
        for (int i = 0; i < words.size(); i++) {
            resultingWords[i] = new GString(words.get(i));
        }
        return resultingWords;
    }

    /**
     * This function returns words composed of english letters only ex: if line
     * is "The_quick_brown-fox jumps over (the) lazy dog's. 101 dalmations
     * 42hero" it will return array of
     * The,quick,brown,fox,jumps,over,the,lazy,dog,s,damations,hero
	 *
     */
    public static GString[] getNumbers(String line) {
        ArrayList<String> words = new ArrayList<String>();
        GString[] resultingWords;
        String currentWord = "";
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                currentWord += String.valueOf(line.charAt(i));
            } else {
                if (currentWord.length() > 0) {
                    words.add(currentWord);
                }
                currentWord = "";
            }
        }
        if (currentWord.length() > 0) {
            words.add(currentWord);
        }

        resultingWords = new GString[words.size()];
        for (int i = 0; i < words.size(); i++) {
            resultingWords[i] = new GString(words.get(i));
        }
        return resultingWords;
    }
    
    /**
     * This function returns every tags and contents between them as separate tokens
     * example: if htmlText supplied is <html > <head>  < title >This is title</title> </head>
     *                                  <body> <h1> This is header < / h1 ></body></html>
     * it ignores whitespaces between >< , however, whitespaces in other tokens remain intact
     * the tokens are <html >,<head>,< title >,This is title,</title>,</head>,<body>,<h1>,This is header,< / h1 >,</body>,</html>
     * 
     * Note: this ignores anything after last > symbol, usually this is after </htm> tag
     *         if you want everything to be included, just pass the htmText+"<" as argument!!
     *          this trick works!!
     * @param htmlText
     * @return 
     */
    public static ArrayList<String> getHtmlTokens(String htmlText, boolean flagIncludeWhitespaces){
        ArrayList<String> tokens = new ArrayList<>();
        
        char prevTagSymbol = '>'; 
        StringBuilder currentTokenSb = new StringBuilder();
        boolean tagStarted = false;        
        for( int i = 0 ; i < htmlText.length(); i++ ){            
            if(htmlText.charAt(i)=='<'){
                //System.out.println("< encountered at "+i);
                if(i==154) if(flagDebug) System.out.println("i="+i+"in if");
                if(!tagStarted){
                    //System.out.println("not tag started");
                    if(!currentTokenSb.toString().isEmpty() && !GString.areAllCharsWhitespace(currentTokenSb.toString()))
                    {                        
                        //System.out.println("if adding "+currentTokenSb.toString());
                        tokens.add(currentTokenSb.toString());
                    }
                    else if(!currentTokenSb.toString().isEmpty() && flagIncludeWhitespaces ){                       
                        //System.out.println("else if adding "+currentTokenSb.toString());
                        tokens.add(currentTokenSb.toString());
                    }
                    currentTokenSb = new StringBuilder();
                }
                currentTokenSb.append(htmlText.charAt(i));
                if(prevTagSymbol=='>') {
                    tagStarted = true;                    
                    //currentTokenSb.append("<");
                    prevTagSymbol = '<';
                }
            }
            else if(htmlText.charAt(i)=='>'){                
                if(prevTagSymbol=='<') {
                    tagStarted = false;                    
                    currentTokenSb.append(htmlText.charAt(i));
                    
                    if(!currentTokenSb.toString().isEmpty() && !GString.areAllCharsWhitespace(currentTokenSb.toString())){
                        tokens.add(currentTokenSb.toString());                        
                    }else if(!currentTokenSb.toString().isEmpty() && flagIncludeWhitespaces ) {
                        tokens.add(currentTokenSb.toString());                        
                    }
                    currentTokenSb = new StringBuilder();
                    prevTagSymbol = '>';
                }else{//this conditon occurs for > in --> when there is html Tag inside html comment like <!-- <h1>header</h1> -->                    
                    currentTokenSb.append(htmlText.charAt(i));
                    tokens.add(currentTokenSb.toString());
                    currentTokenSb = new StringBuilder();
                }
            }else{                
                currentTokenSb.append(htmlText.charAt(i));
            }
        }
        
        return tokens;
    }        
    
    public static boolean isHtmlTag(String line){        
        String tokenWithoutWhitespaces = GString.removeAllWhitespaces(line);
        if(tokenWithoutWhitespaces.isEmpty()) return false;
        if(tokenWithoutWhitespaces.charAt(0)=='<' && tokenWithoutWhitespaces.charAt(tokenWithoutWhitespaces.length()-1)=='>') return true;
        else return false;
            
    }
    
    /**
     * this function takes token(line) and returns the tagName of first tag, 
     * example: getTagName(< html >); returns html
     *          < / html > returns html
     *          <div class="classname" name="name"><span>any text</span></div> returns only div, not span!!
     * @param tokenWithSpacesIntact
     * @return 
     */
    public static String getTagName(String tokenWithSpacesIntact){
       String tagName="";
       boolean tagStarted=false;
       if(isHtmlTag(tokenWithSpacesIntact)) {
           for (int i = 0; i < tokenWithSpacesIntact.length(); i++) {
            if(tokenWithSpacesIntact.charAt(i)=='<'){
                tagStarted=true;
            }
            else if ( (Character.isWhitespace(tokenWithSpacesIntact.charAt(i)) && tagName.length()>0)||tokenWithSpacesIntact.charAt(i)=='>') {
                tagStarted=false;                                
            }else if(tokenWithSpacesIntact.charAt(i)=='/' || Character.isWhitespace(tokenWithSpacesIntact.charAt(i))){
             //skip as / is part of endtag, however tagName is same at start!!
            }else if(tagStarted){
                tagName += tokenWithSpacesIntact.charAt(i);
            }           
        }
       }       
       return tagName;
    }
    public static boolean isStartHtmlTag(String tag){
        if(GString.isHtmlTag(tag)){
            if(GString.removeAllWhitespaces(tag).indexOf("</")==0) return false;
            if(GString.removeAllWhitespaces(tag).indexOf("<")==0) return true;
        }
        return false;
    }
    public static boolean isEndHtmlTag(String tag){
        if(GString.isHtmlTag(tag)){
            if(GString.removeAllWhitespaces(tag).indexOf("</")==0) return true;
        }
        return false;
    }
    public static ArrayList<String> getTagTokens(String tagToken){
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add("<");
        if(GString.isHtmlTag(tagToken)) tagToken=tagToken.substring(tagToken.indexOf('<')+1,tagToken.indexOf('>'));
        String[] tokensSplitBySpace = tagToken.split(" ");
        
        for( String spaceToken : tokensSplitBySpace ){
            if(!GString.areAllCharsWhitespace(spaceToken) )
            {
                if(spaceToken.contains("="))
                {
                    String[] TokensSplitByEqual = spaceToken.split("=");
                    tokens.add(TokensSplitByEqual[0]);
                    for(int i = 1 ; i<TokensSplitByEqual.length; i++ )
                    {
                        tokens.add("=");
                        tokens.add(TokensSplitByEqual[i]);                        
                    }
                }else{
                    tokens.add(spaceToken.substring(0));
                }
            }
        }
        tokens.add(">");
        return tokens;
    }
    
    public static ArrayList<String> getTagTokensIncludingWhitespaces(String tagToken){
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add("<");
        if(GString.isHtmlTag(tagToken)) tagToken=tagToken.substring(tagToken.indexOf('<')+1,tagToken.indexOf('>'));
        String[] tokensSplitBySpace = tagToken.split(" ");
        String spaceToken;
        for(int l=0; l < tokensSplitBySpace.length-1; l++ ){
            spaceToken = tokensSplitBySpace[l];
            if(!GString.areAllCharsWhitespace(spaceToken) )
            {
                if(spaceToken.contains("="))
                {
                    String[] TokensSplitByEqual = spaceToken.split("=");
                    tokens.add(TokensSplitByEqual[0]);
                    for(int i = 1 ; i<TokensSplitByEqual.length; i++ )
                    {
                        tokens.add("=");
                        tokens.add(TokensSplitByEqual[i]);                        
                    }
                }else{
                    tokens.add(spaceToken.substring(0));
                }
            }
            //since its split by spaces, all spaces are lost in the split, to get them back, simply
            //add 1 space after each token!!
            tokens.add(" ");            
        }
        if(tokensSplitBySpace!=null && tokensSplitBySpace.length>0) tokens.add(tokensSplitBySpace[tokensSplitBySpace.length-1]);
        tokens.add(">");
        return tokens;
    }
    
    public static ArrayList<String> getTagTokensWithoutEqualsign(String tagToken){
        ArrayList<String> tokens = new ArrayList<>();
        if(GString.isHtmlTag(tagToken)) tagToken=tagToken.substring(tagToken.indexOf('<')+1,tagToken.indexOf('>'));
        String[] tokensSplitBySpace = tagToken.split(" ");
        for( String spaceToken : tokensSplitBySpace ){
            if(!GString.areAllCharsWhitespace(spaceToken) )
            {
                if(spaceToken.contains("="))
                {
                    String[] TokensSplitByEqual = spaceToken.split("=");
                    for(String tsbe : TokensSplitByEqual )
                    {
                        tokens.add(tsbe);
                    }
                }else{
                    tokens.add(spaceToken.substring(0));
                }
            }
        }
        
        return tokens;
    }
    /**
     * This returns the content of first occurence of tag whose name is specified
     * ex: if tag is <details><summary>summary</summary>this is details</details>
     *      TagContent:<summary>summary</summary>this is details
     *  if tag is <details><summary>summary</summary>this is details<details><summary>summary</summary>this is details</details></details>
     *      TagContent:<summary>summary</summary>this is details<details><summary>summary</summary>this is details</details>
     *  if tag is <details><summary>summary this is details</details> 
     *      TagContent:<summary>summary this is details
     *  when tag Name is details
     * @param tagLine
     * @param tagName
     * @return 
     */
    public static String getTagContent(String tagName,String tagLine){
        ArrayList<String> htmlTokens = GString.getHtmlTokens(tagLine, false);
        
        return getTagContent(tagName,htmlTokens,false);
    }
    public static String getTagContent(String tagName,String... htmlTokens){
        ArrayList<String> htmlTokensList = new ArrayList();
        for(String token: htmlTokens)
            htmlTokensList.add(token);
        return getTagContent(tagName,htmlTokensList,false);
    }
    public static String getTagContent(String tagName,ArrayList<String> htmlTokens){
        return getTagContent(tagName,htmlTokens,false);
    }
    public static String getTagHtml(String tagName,String tagLine){
        ArrayList<String> htmlTokens = GString.getHtmlTokens(tagLine, false);
        
        return getTagContent(tagName,htmlTokens,true);
    }
    public static String getTagHtml(String tagName,String... htmlTokens){
        ArrayList<String> htmlTokensList = new ArrayList();
        for(String token: htmlTokens)
            htmlTokensList.add(token);
        return getTagContent(tagName,htmlTokensList,true);
    }
    public static String getTagHtml(String tagName,ArrayList<String> htmlTokens){
        return getTagContent(tagName,htmlTokens,true);
    }
    public static String getTagContent(String tagName,ArrayList<String> htmlTokens,boolean includeTag){
        StringBuilder tagContent= new StringBuilder();
        int tagCount=0;
        
        String curToken;
        for( int i = 0 ; i < htmlTokens.size(); i++){
            curToken = htmlTokens.get(i);
            if(GString.getTagName(curToken).equalsIgnoreCase(tagName)){
                if(GString.isStartHtmlTag(curToken)){
                    if(tagCount > 0){
                        tagContent.append(curToken);
                    }else if(includeTag){
                        tagContent.append(curToken);
                    }
                    tagCount++;
                }else if(GString.isEndHtmlTag(curToken)){
                    tagCount--;
                    if(tagCount > 0){
                        tagContent.append(curToken);
                    }else {
                        if(includeTag) {
                            tagContent.append(curToken);
                        }
                        break;
                    }
                }                
            }else{
                if(tagCount > 0){
                    tagContent.append(curToken);
                }
            }
        }
        
        return tagContent.toString();
    }
    /**
     * if tagLine= <details class='details-tag'><summary class='summary-tag'>summary</summary>this is details</details>
     *      getNotTagContent(tagLine,"summary") returns "<details class='details-tag'>this is details</details>"
     * however, if tagLine= <summary class='summary-tag'>summary</summary>this is details
     *      getNotTagContent(tagLine,"summary") returns "this is details"
     * 
     * only first tag is removed, rest remain untouched
     * it preserves the nested behavior, i.e. something1<details><details>something2</details></details>something3<details>something4</details>
     *      returns something1something3<details>something4</details> it respects nesting
     *          not </details>something3<details>something4</details> as it doesn't respect nesting
     * @param tagLine
     * @param tagName
     * @return 
     */
    public static String getNotTagContent(String tagName,String tagLine){
        ArrayList<String> htmlTokens = GString.getHtmlTokens(tagLine+"<", true);//refer getHtmlTokens() to understand why < has been appended
        
        return getNotTagContent(tagName,htmlTokens);
    }
    public static String getNotTagContent(String tagName,String... htmlTokens){
        ArrayList<String> htmlTokensList = new ArrayList();
        for(String token: htmlTokens)
            htmlTokensList.add(token);
        return getNotTagContent(tagName,htmlTokensList);
    }
    public static String getNotTagContent(String tagName,ArrayList<String> htmlTokens){
        StringBuilder tagContent= new StringBuilder();
        int tagCount=0;

        String curToken;
        boolean flagStopChecking=false;
        for( int i = 0 ; i < htmlTokens.size(); i++){
            curToken = htmlTokens.get(i);        
            if(GString.getTagName(curToken).equalsIgnoreCase(tagName)){
                if(!flagStopChecking){
                    if(GString.isStartHtmlTag(curToken)){                
                        tagCount++;
                    }else if(GString.isEndHtmlTag(curToken)){
                        tagCount--;
                        if(tagCount==0) flagStopChecking=true;
                    }
                }
                else tagContent.append(curToken);
            }else{
                if(tagCount <= 0){
                    tagContent.append(curToken);
                }
            }
        }

        return tagContent.toString();
    }
    
    /**
     * for example, if cssText is html,body{ color: green; position: relative; display: table;width: 800px;} .classname{ color: red; }
     * returns "html,body","{","color: green;","position: relative;","display: table;","width: 800px;","}",".classname","color: red" and "}"
     * as tokens in ArrayList, entire single property is treated as single token!!
     * also, white spaces and newline characters remains intact!! i.e if you combine those tokens, you will end up with original document
     * @param cssText
     * @return 
     */
    public static ArrayList<String> getCssTokens(String cssText){
        return getCssTokens(cssText,false);
    }
    public static ArrayList<String> getCssTokens(String cssText, boolean skipComment){
        ArrayList<String> tokens = new ArrayList<>();
        
        if(!cssText.contains("{") || !cssText.contains("}")) return null;
        String firstToken=cssText.substring(0,cssText.indexOf("{"));
        tokens.add(firstToken);
        
        cssText = cssText.substring(cssText.indexOf("{"));
        
        char prevTagSymbol = '}'; 
        StringBuilder currentTokenSb = new StringBuilder();
        boolean blockStarted = true; 
        boolean commentStarted = false;
        for( int i = 0 ; i < cssText.length(); i++ ){
            if(skipComment){
                if(cssText.charAt(i)=='/'&&cssText.charAt(i+1)=='*'){
                    i++;
                    commentStarted = true;
                }else if(cssText.charAt(i)=='*'&&cssText.charAt(i+1)=='/'){
                    i+=2;
                    commentStarted = false;
                }
            }
            if(!commentStarted){
            if(cssText.charAt(i)=='{'){                
                if(!blockStarted){
                    if(!currentTokenSb.toString().isEmpty() && !GString.areAllCharsWhitespace(currentTokenSb.toString()))
                       tokens.add(currentTokenSb.toString());                   
                    currentTokenSb = new StringBuilder();                                        
                }
                //currentTokenSb.append(cssText.charAt(i));
                if(prevTagSymbol=='}') {
                    blockStarted = true; 
                    tokens.add("{");
                    //currentTokenSb.append("<");
                    prevTagSymbol = '{';
                }
            }
            else if(cssText.charAt(i)=='}'){
                
                if(prevTagSymbol=='{') {                    
                    blockStarted = false;                     
                    //currentTokenSb.append(cssText.charAt(i));                    
                    if(!currentTokenSb.toString().isEmpty() && !GString.areAllCharsWhitespace(currentTokenSb.toString()))
                    {
                        if(currentTokenSb.toString().contains(";")){
                            String[] propertyTokens = currentTokenSb.toString().split(";");
                            for(String propertyToken : propertyTokens){
                                if(!GString.areAllCharsWhitespace(propertyToken)) tokens.add(propertyToken+";");
                            }
                        }else{
                            tokens.add(currentTokenSb.toString());
                        }
                        tokens.add("}");
                    }
                    currentTokenSb = new StringBuilder();
                    prevTagSymbol = '}';
                }
            }else{
                currentTokenSb.append(cssText.charAt(i));
            }
            }
        }
        
        return tokens;
    }        
    
      
    /**
    * left and right are indices in array whereas
    * pivot is one of the values stored in array
    */
   public static int partition(int left, int right , String pivot, String[] fileWithPriority)
   {
           int leftIndex = left -1;
           int rightIndex = right;
           String temp = null;
           while(true)
           {
                   while( fileWithPriority[++leftIndex].compareTo(pivot) < 0 );
                   while( rightIndex>0 && fileWithPriority[--rightIndex].compareTo(pivot) > 0 );

                   if( leftIndex >= rightIndex ) break;
                   else//swap value at leftIndex and rightIndex
                   {
                           temp = fileWithPriority[leftIndex];
                           fileWithPriority[leftIndex]= fileWithPriority[rightIndex];
                           fileWithPriority[rightIndex] = temp;
                   }
           }

           //swap value at leftIndex and initial right index
           temp = fileWithPriority[leftIndex];
           fileWithPriority[leftIndex]= fileWithPriority[right];
           fileWithPriority[right] = temp;

           return leftIndex;
   }

   public static void quickSort( int leftIndex , int rightIndex, String[] fileWithPriority )
   {
           if( rightIndex-leftIndex <= 0 ) return;
           else
           {
                   String pivot = fileWithPriority[rightIndex];                   
                   int partitionIndex = partition(leftIndex, rightIndex , pivot, fileWithPriority);
                   quickSort(leftIndex,partitionIndex-1,fileWithPriority);
                   quickSort(partitionIndex+1,rightIndex,fileWithPriority);
           }
   }

   /**
    * left and right are indices in array whereas
    * pivot is one of the values stored in array
    */
   public static int partitionByDelimiterCount(int left, int right , String pivot, String[] fileWithPriority,String delimiter)
   {
           int leftIndex = left -1;
           int rightIndex = right;
           String temp = null;
           while(true)
           {
                   while( GString.countOf(fileWithPriority[++leftIndex],"/")< GString.countOf(pivot,"/") );
                   while( rightIndex>0 && GString.countOf(fileWithPriority[--rightIndex],"/") > GString.countOf(pivot,"/") );

                   if( leftIndex >= rightIndex ) break;
                   else//swap value at leftIndex and rightIndex
                   {
                           temp = fileWithPriority[leftIndex];
                           fileWithPriority[leftIndex]= fileWithPriority[rightIndex];
                           fileWithPriority[rightIndex] = temp;
                   }
           }

           //swap value at leftIndex and initial right index
           temp = fileWithPriority[leftIndex];
           fileWithPriority[leftIndex]= fileWithPriority[right];
           fileWithPriority[right] = temp;

           return leftIndex;
   }

   public static void quickSortByDelimiterCount( int leftIndex , int rightIndex, String[] fileWithPriority ,String delimiter)
   {
           if( rightIndex-leftIndex <= 0 ) return;
           else
           {
                   String pivot = fileWithPriority[rightIndex];                   
                   int partitionIndex = partitionByDelimiterCount(leftIndex, rightIndex , pivot, fileWithPriority,delimiter);
                   quickSortByDelimiterCount(leftIndex,partitionIndex-1,fileWithPriority,delimiter);
                   quickSortByDelimiterCount(partitionIndex+1,rightIndex,fileWithPriority,delimiter);
           }
   }
   
   public static int countOf(String line, String substring)
   {
       String[] parts = line.split(substring);
       if(parts!=null) return parts.length-1;
       return 0;
   }
}
