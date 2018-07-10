package com.penny.pwdedit;

import android.util.Log;
import java.util.regex.Pattern;

public class PwdStrengthGradeUtils {

  private static final int WEEK_GRADE = 60;
  private static final int MIDDLE_GRADE = 80;
  private static final int BASE_GRADE = 70;

  public static final int WEEK_NONE = 0;
  public static final int WEEK_LEVEL = 1;
  public static final int MIDDLE_LEVEL = 2;
  public static final int STRONG_LEVEL = 3;
  private static final String TAG = "PwdStrengthGradeUtils";

  public static int calculateGrade(String pwd) {
    int result = 0;
    if (!isNumberAlphabet(pwd)) {
      result = 0;
    } else if (!isBlank(pwd) && pwd.length() >= 8) {
      result = BASE_GRADE;
      int length = pwd.length();
      int upperSize = 0;
      int lowerSize = 0;
      int numberSize = 0;
      char lastchar = pwd.charAt(0);
      int ascSize = 1;
      int descSize = 1;
      int repeatSize = 1;
      int charNum = 0;
      int groupNum = 0;
      int repeatNum = 0;
      for (int i = 0; i < length; i++) {
        char c = pwd.charAt(i);
        if (c >= 'A' && c <= 'Z') {
          upperSize++;
        } else if (c >= 'a' && c <= 'z') {
          lowerSize++;
        } else if (c >= '0' && c <= '9') {
          numberSize++;
        }
        if (i > 0) {
          if (c - lastchar == 1) {
            ascSize++;
          } else if (lastchar - c == 1) {
            descSize++;
          } else {
            if (ascSize > 3) {
              charNum += (ascSize - 3);
            }
            if (ascSize > 2) {
              groupNum += 3;
            }

            if (descSize > 3) {
              charNum += (descSize - 3);
            }
            if (descSize > 2) {
              groupNum += 3;
            }
            ascSize = 1;
            descSize = 1;
          }

          if (c == lastchar) {
            repeatSize++;
          } else {
            repeatNum += (repeatSize > 1 ? repeatSize * 2 : 0);
            repeatSize = 1;
          }
        }
        lastchar = c;
      }

      repeatNum += (repeatSize > 1 ? repeatSize * 2 : 0);
      if (ascSize > 3) {
        charNum += (ascSize - 3);
      }
      if (ascSize > 2) {
        groupNum += 3;
      }

      if (descSize > 3) {
        charNum += (descSize - 3);
      }
      if (descSize > 2) {
        groupNum += 3;
      }

      result += (pwd.length() - 8) * 4;
      Log.d(TAG, "pwd length grade is " + ((pwd.length() - 8) * 4));
      if (upperSize > 0 && lowerSize > 0 && numberSize > 0) {
        result += upperSize * lowerSize * 2;
        Log.d(TAG, "pwd Aa grade is " + (upperSize * lowerSize * 2));
      } else if (upperSize > 0 && numberSize > 0) {
        result += upperSize * 2;
        Log.d(TAG, "pwd A grade is " + (upperSize * 2));
      } else if (lowerSize > 0 && numberSize > 0) {
        result += lowerSize * 2;
        Log.d(TAG, "pwd a grade is " + (lowerSize * 2));
      }
      result = result - repeatNum - charNum - groupNum;
      Log.d(TAG, "pwd repeatNum grade is " + repeatNum);
      Log.d(TAG, "pwd charNum grade is " + charNum);
      Log.d(TAG, "pwd groupNum grade is " + groupNum);
    }

    Log.d(TAG, "pwd result grade is " + result);
    if (result == 0) {
      return WEEK_NONE;
    } else if (result < WEEK_GRADE) {
      return WEEK_LEVEL;
    } else if (result < MIDDLE_GRADE) {
      return MIDDLE_LEVEL;
    } else {
      return STRONG_LEVEL;
    }
  }

  /** 数字字母 */
  private static final Pattern NUMBER_ALPHABET_PATTERN = Pattern.compile("^[0-9a-zA-Z]*$");

  private static boolean isNumberAlphabet(String content) {
    if (isBlank(content)) {
      return false;
    }
    return NUMBER_ALPHABET_PATTERN.matcher(content).matches();
  }

  private static boolean isBlank(String string) {
    return (string == null || string.toString().trim().length() == 0);
  }
}