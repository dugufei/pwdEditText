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
    int numSize = 0;
    if (!isNumberAlphabet(pwd)) {
      result = 0;
    } else if (!isBlank(pwd) && pwd.length() >= 8) {
      int length = pwd.length();
      int upperSize = 0;
      int lowerSize = 0;
      for (int i = 0; i < length; i++) {
        char c = pwd.charAt(i);
        if (c >= 'A' && c <= 'Z') {
          upperSize++;
        } else if (c >= 'a' && c <= 'z') {
          lowerSize++;
        } else if (c >= '0' && c <= '9') {
          numSize++;
        }
      }

      result +=
          BASE_GRADE
              + (length - 8) * 4 + calRepeatCharSize(pwd)
              + calSeriaAlphaSize(pwd)
              + calSeriaNumSize(pwd)
              + calContinuesAlpha(pwd,true)
              + calContinuesAlpha(pwd,false)
              + calContinuesNum(pwd,true)
              + calContinuesNum(pwd,false)
              + upperSize * lowerSize * 2;

      Log.d(TAG, "  ");
      Log.d(TAG, "pwd = "+pwd);
      Log.d(TAG, "result = "
          + BASE_GRADE
          + " + "
          + (length - 8) * 4
          + " + "
          + calRepeatCharSize(pwd)
          + " + "
          + calSeriaAlphaSize(pwd)
          + " + "
          + calSeriaNumSize(pwd)
          + " + "
          + calContinuesAlpha(pwd,true)
          + " + "
          + calContinuesAlpha(pwd,false)
          + " + "
          + calContinuesNum(pwd,true)
          + " + "
          + calContinuesNum(pwd,false)
          + " + "
          + upperSize * lowerSize * 2);
      Log.d(TAG,"result = "+result);
    }

    if(pwd.length()<1){
      return WEEK_NONE;
    } else if (result < WEEK_GRADE || numSize<2) {
      return WEEK_LEVEL;
    } else if (result < MIDDLE_GRADE) {
      return MIDDLE_LEVEL;
    } else {
      return STRONG_LEVEL;
    }
  }

  // 接连重复次数
  private static int calRepeatCharSize(String pwd) {
    int result = 0;
    for (int i = 0; i < pwd.length(); i++) {
      int repeatCharSize = 1;
      for (int j = i + 1; j < pwd.length(); j++) {
        if (pwd.charAt(i) == pwd.charAt(j)) {
          repeatCharSize++;
          if (j == pwd.length() - 1) {
            result -= repeatCharSize == 1 ? 0 : repeatCharSize * 2;
            i = j;
          }
        } else {
          result -= repeatCharSize == 1 ? 0 : repeatCharSize * 2;
          i = j - 1;
          break;
        }
      }
    }
    Log.d(TAG, "repeatChar result = " + result);
    return result;
  }

  // 接连数字长度
  private static int calSeriaNumSize(String pwd) {
    int result = 0;
    for (int i = 0; i < pwd.length() - 1; i++) {
      char c = pwd.charAt(i);
      int seriaNumSize = 1;
      for (int j = i + 1; j < pwd.length(); j++) {
        char c1 = pwd.charAt(j);
        if (isNum(c) && isNum(c1)) {
          seriaNumSize++;
          if (j == pwd.length() - 1) {
            result -= Math.max(0, seriaNumSize - 3);
            i = j;
          }
        } else {
          result -= Math.max(0, seriaNumSize - 3);
          i = j - 1;
          break;
        }
      }
    }
    Log.d(TAG, "seriaNum result = " + result);
    return result;
  }

  // 接连字母长度
  private static int calSeriaAlphaSize(String pwd) {
    int result = 0;
    for (int i = 0; i < pwd.length(); i++) {
      char c = pwd.charAt(i);
      int seriaCharSize = 1;

      for (int j = i + 1; j < pwd.length(); j++) {
        char c1 = pwd.charAt(j);
        if (isAlpha(c) && isAlpha(c1)) {
          seriaCharSize++;
          if (j == pwd.length() - 1) {
            result -= Math.max(0, seriaCharSize - 3);
            i = j;
          }
        } else {
          result -= Math.max(0, seriaCharSize - 3);
          i = j - 1;
          break;
        }
      }
    }

    Log.d(TAG, "seriaChar result = " + result);
    return result;
  }

  // 3个以上的连续字母
  private static int calContinuesAlpha(String pwd,boolean isAsc) {
    int result = 0;
    int count = 0;
    int abs = isAsc ? 1:-1;
    for (int i = 0; i < pwd.length() - 2; i++) {
      int offset = 0;
      for (int j = i + 1; j < pwd.length(); j++) {
        if (isAlpha(pwd.charAt(j - 1))
            && isAlpha(pwd.charAt(j + 1))
            && pwd.charAt(j) - pwd.charAt(j - 1) == abs
            && pwd.charAt(j + 1) - pwd.charAt(j) == abs) {
          offset++;
          if (j == pwd.length() - 2) {
            count++;
            i = j;
            break;
          }
        } else {
          if (offset > 0) {
            count++;
            i = j;
          }
          break;
        }
      }
    }
    result -= count * 3;
    Log.d(TAG, "ContinuesAlpha result = " + result);
    Log.d(TAG, "ContinuesAlpha count = " + count);
    return result;
  }

  // 3个以上的连续数字
  private static int calContinuesNum(String pwd,boolean isAsc) {
    int result = 0;
    int count = 0;
    int abs = isAsc ? 1:-1;
    for (int i = 0; i < pwd.length() - 2; i++) {
      int offset = 0;
      for (int j = i + 1; j < pwd.length(); j++) {
        if (isNum(pwd.charAt(j - 1))
            && isNum(pwd.charAt(j + 1))
            && pwd.charAt(j) - pwd.charAt(j - 1) == abs
            && pwd.charAt(j + 1) - pwd.charAt(j) == abs) {
          offset++;
          if (j == pwd.length() - 2) {
            count++;
            i = j;
            break;
          }
        } else {
          if (offset > 0) {
            count++;
            i = j;
          }
          break;
        }
      }
    }
    result -= count * 3;
    Log.d(TAG, "ContinuesNum result = " + result);
    Log.d(TAG, "ContinuesNum count = " + count);
    return result;
  }

  /** 密码（8-16位数字或字母,不包括特殊字符） */
  private static final Pattern NUMBER_ALPHABET_PATTERN =
      Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");

  private static boolean isNumberAlphabet(String content) {
    if (isBlank(content)) {
      return false;
    }
    return NUMBER_ALPHABET_PATTERN.matcher(content).matches();
  }

  private static boolean isAlpha(char c) {
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
  }

  private static boolean isNum(char c) {
    return c >= '0' && c <= '9';
  }

  private static boolean isBlank(String string) {
    return (string == null || string.toString().trim().length() == 0);
  }
}