package etocrm.utils;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @description:
 * @author: yubo
 * @createDate: 2020/5/15
 * @version: 1.0
 */
@Component
public class ListUtils<T> {

  /**
   * 获取两个List的不同元素 无序
   */
  public static List<String> getDiffrent(List<String> list1, List<String> list2) {
    List<String> diff = new ArrayList<>();
    List<String> maxList = list1;
    List<String> minList = list2;
    if (list2.size() > list1.size()) {
      maxList = list2;
      minList = list1;
    }
    Map<String, Integer> map = new HashMap<>(maxList.size());
    for (String string : maxList) {
      map.put(string, 1);
    }
    for (String string : minList) {
      if (map.get(string) != null) {
        map.put(string, 2);
        continue;
      }
      diff.add(string);
    }
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      if (entry.getValue() == 1) {
        diff.add(entry.getKey());
      }
    }
    return diff;
  }

  /**
   * 获取两个List的不同元素 有序
   */
  public static List<String> getDiffrent2(List<String> allList, List<String> subList) {
    List<String> diff = Lists.newLinkedList();
    for (String str : allList) {
      if (!subList.contains(str)) {
        diff.add(str);
      }
    }
    return diff;
  }

  /**
   * 复制list
   *
   * @param obj 源list
   * @param list2 目标list
   * @param classObj 目标list的类型
   */
  public void copyList(Object obj, List<T> list2, Class<T> classObj) {
    if ((!Objects.isNull(obj)) && (!Objects.isNull(list2))) {
      List list1 = (List) obj;
      list1.forEach(item -> {
        try {
          T data = classObj.newInstance();
          BeanUtils.copyProperties(item, data);
          list2.add(data);
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
      });
    }
  }

    /**
     * 比较两个List是否完全相当（元素及元素对应的顺序都要一致）
     */
    public static boolean eqList(List<String> list1, List<String> list2) {
        boolean res = true;
        if (list1.size() == list2.size()) {
            for (int i = 0; i < list1.size(); i++) {
                if ((list1.get(i)).equals(list2.get(i))) {
                    continue;
                } else {
                    res = false;
                    break;
                }
            }
        } else {
            res = false;
        }
        return res;
    }

}
