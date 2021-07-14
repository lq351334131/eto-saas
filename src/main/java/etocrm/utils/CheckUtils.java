package etocrm.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author csd
 * @desc
 * @date 2020-09-11 15:50
 **/
public class CheckUtils {

  public static void copy(String field, Object value, Object commonImportDTO) throws IllegalAccessException {
    List<Field> fields = Arrays.asList(commonImportDTO.getClass().getDeclaredFields());
    for (Field f : fields) {
      if (f.getName().equals(field)) {
        f.setAccessible(true);
        f.set(commonImportDTO, value);
      }
    }
  }


  public static boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    Pattern pattern = compile("^-?\\d+(\\.\\d+)?$");
    return pattern.matcher(str).matches();
  }

  public static boolean isDate(Object date) {
    boolean flag = true;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      String str = format.format(date);
      System.out.println(str);
    } catch (Exception e) {
      flag = false;
    }
    return flag;
  }

  public static boolean isTelePhone(String str) {
    Pattern p1 = null, p2 = null;
    Matcher m = null;
    boolean isPhone = false;
    p1 = compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
    p2 = compile("^[1-9]{1}[0-9]{5,8}$");     // 验证没有区号的
    if (str.length() > 9) {
      m = p1.matcher(str);
      isPhone = m.matches();
    } else {
      m = p2.matcher(str);
      isPhone = m.matches();
    }
    return isPhone;
  }

  public static boolean isPhone(String str) {
    Pattern pattern = compile("^[1][3|4|5|6|7|8|9][0-9]{9}$");
    if (!pattern.matcher(str).matches()) {
      return false;
    } else {
      return true;
    }
  }

  public static boolean isNumber4(String str) {
    Pattern pattern = compile("^[1-9]\\d{0,7}(\\.\\d{1,4})?$|^0(\\.\\d{1,4})?$");
    Matcher match = pattern.matcher(str);
    if (match.matches() == false) {
      return false;
    } else {
      return true;
    }
  }


  public static boolean isNumber3(String str) {
    Pattern pattern = compile("^[1-9]\\d{0,7}(\\.\\d{1,3})?$|^0(\\.\\d{1,3})?$");
    Matcher match = pattern.matcher(str);
    if (match.matches() == false) {
      return false;
    } else {
      return true;
    }
  }

  public static boolean isNumber2(String str) {
    Pattern pattern = compile("^[1-9]\\d{0,7}(\\.\\d{1,2})?$|^0(\\.\\d{1,2})?$");
    Matcher match = pattern.matcher(str);
    if (match.matches() == false) {
      return false;
    } else {
      return true;
    }
  }

  public static boolean isEmail(String str) {
    String a =("^([a-zA-Z0-9_-])+\\.?([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
    return str.matches(a);
  }
}
