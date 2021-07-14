package etocrm.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExportUtil {

    /** CSV文件列分隔符 */
    private static final String CSV_COLUMN_SEPARATOR = ",";

    /** CSV文件行分隔符 */
//    private static final String CSV_RN = "\r\n";
    private static final String CSV_RN=System.lineSeparator();

    private static final String UTF = "UTF-8";

    private static final String GBK = "GB2312";

    public static final String CSV = ".csv";

    public static final String XLS = ".xls";

    public static final String XLSX = ".xlsx";

    /**
     *
     * @param dataList 集合数据
     * @param colNames 表头部数据
     * @param mapKey 查找的对应数据
     * @param os 返回结果
     */
    public static void doExport(List<Map<String, Object>> dataList, String colNames, String mapKey, OutputStream os) {
        try {
            StringBuffer buf = new StringBuffer();

            String[] colNamesArr = null;
            String[] mapKeyArr = null;

            colNamesArr = colNames.split(",");
            mapKeyArr = mapKey.split(",");

            // 完成数据csv文件的封装
            // 输出列头
            for (String aColNamesArr : colNamesArr) {
                buf.append(aColNamesArr).append(CSV_COLUMN_SEPARATOR);
            }
            buf.append(CSV_RN);

            if (null != dataList) { // 输出数据
                for (Map<String, Object> aDataList : dataList) {
                    for (String aMapKeyArr : mapKeyArr) {
                        if(aDataList.get(aMapKeyArr)==null){
                            buf.append(" ").append(CSV_COLUMN_SEPARATOR);
                        }else{
                            String text=aDataList.get(aMapKeyArr).toString();
                            text=text.replaceAll("\""," ").replaceAll(","," ").replaceAll("\r|\n"," ");
                            buf.append(text).append(CSV_COLUMN_SEPARATOR);
                        }

                    }
                    //删除行尾","
                    buf.delete(buf.lastIndexOf(CSV_COLUMN_SEPARATOR),buf.length());
                    buf.append(CSV_RN);
                }
            }
            //删除最后一空行
            buf.delete(buf.lastIndexOf(CSV_RN),buf.length());
            //微软的excel文件需要通过文件头的bom来识别编码，所以写文件时，需要先写入bom头
            byte[] uft8bom={(byte)0xef,(byte)0xbb,(byte)0xbf};
            os.write(uft8bom);
            // 写出响应
            os.write(buf.toString().getBytes(UTF));
            os.flush();

        } catch (Exception e) {
            log.error("doExport错误...", e);
        }

    }

    /**
     * setHeader
     */
    public static void responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置响应
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setCharacterEncoding(UTF);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, UTF));
    }

    //下载数据
    public static void exportExcel(List<Map<String,Object>>dataList,String fileName,String heads,String headsKey,OutputStream outputStream,HttpServletResponse response) throws Exception {
        ExportUtil.responseSetProperties(fileName,response);
        //写入数据
        ExportUtil.doExport(dataList,heads,headsKey,outputStream);
    }

    //获取response
    public static HttpServletResponse getResponse(){
        ServletRequestAttributes servletRequestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response=servletRequestAttributes.getResponse();
        return response;
    }

    public static String getFileName(String fileName,String fileType){
        String fn = fileName + LocalDateTimeUtil.format(LocalDateTimeUtil.now(),"yyyyMMddhhmmss") + fileType;
        return fn;
    }

}