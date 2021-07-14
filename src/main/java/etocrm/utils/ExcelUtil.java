package etocrm.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.exception.UamException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Excel工具类
 *
 * @author xch
 * @data 2020/8/12 13:56
 */
@Slf4j
public class ExcelUtil {

    public static <T> List<T> excelToList(MultipartFile file, Class<T> entityClass, Map<String, String> fields) throws Exception {
        List<T> resultList = Lists.newArrayList();
       InputStream fileInputStream = file.getInputStream();

        Workbook workbook;
        try {

           workbook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception ex) {
            workbook = new HSSFWorkbook(fileInputStream);
        }


        // excel中字段的中英文名字数组
        String[] egTitles = new String[fields.size()];
        String[] cnTitles = new String[fields.size()];
        Iterator<String> it = fields.keySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            String cnTitle = it.next();
            String egTitle = fields.get(cnTitle);
            egTitles[count] = egTitle;
            cnTitles[count] = cnTitle;
            count++;
        }

        // 得到excel中sheet总数
        int sheetCount = workbook.getNumberOfSheets();

        if (sheetCount == 0) {
            workbook.close();
            throw  new UamException(ResponseEnum.FAILD.getCode(),"Excel文件中没有任何数据");
        }

        // 得到excel的第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            throw new UamException(ResponseEnum.FAILD.getCode(),"Sheet文件中没有任何数据");
        }

        // 每页中的第一行为标题行，对标题行的特殊处理
        // 正常情况下这里的入参应该是0,因为本次导入的excel有些特殊,第一行是注意事项,第二行才是表头,故此处0+1
        Row firstRow = sheet.getRow(0);


        String[] excelFieldNames = new String[fields.size()];
        LinkedHashMap<String, Integer> colMap = new LinkedHashMap<>();

        // 获取Excel中的列名
        for (int f = 0; f < fields.size(); f++) {
            Cell cell = firstRow.getCell(f);
            if (null != cell) {
                cell.setCellType(CellType.STRING);
                excelFieldNames[f] = cell.getStringCellValue().trim();
                // 将列名和列号放入Map中,这样通过列名就可以拿到列号
                for (int g = 0; g < excelFieldNames.length; g++) {
                    colMap.put(excelFieldNames[g], g);
                }
            }
        }
        // 由于数组是根据长度创建的，所以值是空值，这里对列名map做了去空键的处理
        colMap.remove(null);
        colMap.remove("");

        // 正确的表头顺序
        List<String> cnTitlesList = Arrays.asList(cnTitles);
        // 上传的表头顺序
        List<String> importTitlesList = Lists.newLinkedList();
        for (Map.Entry<String, Integer> entry : colMap.entrySet()) {
            String key = entry.getKey();
            importTitlesList.add(key);
        }
        boolean flag = ListUtils.eqList(cnTitlesList, importTitlesList);
        if (flag == false) {
            throw  new UamException(ResponseEnum.FAILD.getCode(),"模板不正确");
        }

        // 判断需要的字段在Excel中是否都存在
        // 需要注意的是这个方法中的map中：中文名为键，英文名为值
        boolean isExist = true;
        List<String> excelFieldList = Arrays.asList(excelFieldNames);
        for (String cnName : fields.keySet()) {
            if (!excelFieldList.contains(cnName)) {
                isExist = false;
                break;
            }
        }
        // 如果有列名不存在，则抛出异常，提示错误
        if (!isExist) {
            workbook.close();
            throw new Exception("Excel中缺少必要的字段，或字段名称有误");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        boolean numberFlag = false;

        // 将sheet转换为list
        for (int j = 1; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            if (null != row) {

                // 根据泛型创建实体类
                T entity = entityClass.newInstance();
                // 给对象中的字段赋值
                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    // 获取中文字段名
                    String cnNormalName = entry.getKey();
                    // 获取英文字段名
                    String enNormalName = entry.getValue();
                    // 根据中文字段名获取列号
                    int col = colMap.get(cnNormalName);
                    // 获取当前单元格中的内容
                    Cell cell = row.getCell(col);
                    if (null != cell) {

                        String content = "";
                        if (cnNormalName.contains("日期") || cnNormalName.contains("有效期")) {
                            boolean dateFlag = true;
                            if (!cell.toString().contains("月")) {
                                dateFlag = false;
                            }

                            if (dateFlag) {
                                Date value = cell.getDateCellValue();
                                if (null != value) {
                                    if (cell.toString().contains("-")) {
                                        content = sdf.format(value);
                                    } else if (cell.toString().contains("/")) {
                                        content = sdf2.format(value);
                                    }
                                }
                            } else {
                                cell.setCellType(CellType.STRING);
                                content = cell.toString().trim();
                            }
                        } else {
                            cell.setCellType(CellType.STRING);
                            content = cell.toString().trim();
                        }
                        // 给对象赋值
                        setFieldValueByName(enNormalName, content, entity);
                    }
                }
                resultList.add(entity);
            }
        }
        if (numberFlag) {
            throw new Exception("模板不正确");
        }
        workbook.close();
        return resultList;
    }

    private static void setFieldValueByName(String fieldName, Object fieldValue, Object o) throws Exception {
        Field field = getFieldByName(fieldName, o.getClass());
        if (field != null) {
            field.setAccessible(true);
            // 获取字段类型
            Class<?> fieldType = field.getType();

            // 根据字段类型给字段赋值
            if (String.class == fieldType) {
                field.set(o, String.valueOf(fieldValue));
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                field.set(o, Integer.parseInt(fieldValue.toString()));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                field.set(o, Long.valueOf(fieldValue.toString()));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                field.set(o, Float.valueOf(fieldValue.toString()));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                field.set(o, Short.valueOf(fieldValue.toString()));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                field.set(o, Double.valueOf(fieldValue.toString()));
            } else if (Character.TYPE == fieldType) {
                if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
                    field.set(o, Character.valueOf(fieldValue.toString().charAt(0)));
                }
            } else if (Date.class == fieldType) {
                field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
            } else {
                field.set(o, fieldValue);
            }
        } else {
            throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }
    }

    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 拿到本类的所有字段
        Field[] selfFields = clazz.getDeclaredFields();

        // 如果本类中存在该字段，则返回
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }

        // 如果本类和父类都没有，则返回空
        return null;
    }

    /**
     * 读取excel
     */
    public static List<String> readExcel(String templateUrl, String fileName) throws IOException {
        URL resourceUrl = new URL(templateUrl);
        HttpURLConnection con = (HttpURLConnection) resourceUrl.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5 * 1000);
        InputStream in = con.getInputStream();


        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(templateUrl));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null) {
                fileInputStream.close();
            }
        }
        Workbook book = null;
        try {
            book = WorkbookFactory.create(fileInputStream);
        } catch (InvalidFormatException e) {
           return null;
        }


        Sheet sheet = book.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        List<String> resultList = Lists.newLinkedList();
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (null != row) {
                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (null != cell) {
                        String s = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(s)) {
                            resultList.add(s);
                        }
                    }
                }
            }
        }
        resultList = resultList.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        return resultList;
    }




}
