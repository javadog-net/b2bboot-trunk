package com.jhmis.modules.gen.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.config.Global;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.FileUtils;
import com.jhmis.common.utils.FreeMarkers;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.mapper.JaxbMapper;
import com.jhmis.modules.gen.entity.*;
import com.jhmis.modules.sys.entity.Area;
import com.jhmis.modules.sys.entity.Office;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class a {
    private static Logger logger = LoggerFactory.getLogger(a.class);
    public a() {
    }

    /**
     * 设置表相关数据
     * @param a1
     */
    public static void a4(GenTable a1) {
        Iterator<GenTableColumn> var2 = a1.getColumnList().iterator();

        while(true) {
            while(true) {
                GenTableColumn b1;
                do {
                    if (!var2.hasNext()) {
                        return;
                    }

                    b1 = (GenTableColumn)var2.next();
                } while(StringUtils.isNotBlank(b1.getId()));

                if (StringUtils.isBlank(b1.getComments())) {
                    b1.setComments(b1.getName());
                }

                if (!StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "CHAR") && !StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "VARCHAR") && !StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "NARCHAR")) {
                    if (!StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "DATETIME") && !StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "DATE") && !StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "TIMESTAMP")) {
                        if (StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "BIGINT") || StringUtils.startsWithIgnoreCase(b1.getJdbcType(), "NUMBER")) {
                            String[] ss = StringUtils.split(StringUtils.substringBetween(b1.getJdbcType(), "(", ")"), ",");
                            if (ss != null && ss.length == 2 && Integer.parseInt(ss[1]) > 0) {
                                b1.setJavaType("Double");
                            } else if (ss != null && ss.length == 1 && Integer.parseInt(ss[0]) <= 10) {
                                b1.setJavaType("Integer");
                            } else {
                                b1.setJavaType("Long");
                            }
                        }
                    } else {
                        b1.setJavaType("java.util.Date");
                        b1.setShowType("dateselect");
                    }
                } else {
                    b1.setJavaType("String");
                }

                b1.setJavaField(StringUtils.toCamelCase(b1.getName()));
                b1.setIsPk(a1.getPkList().contains(b1.getName()) ? "1" : "0");
                b1.setIsInsert("1");
                if (!StringUtils.equalsIgnoreCase(b1.getName(), "id") && !StringUtils.equalsIgnoreCase(b1.getName(), "create_by") && !StringUtils.equalsIgnoreCase(b1.getName(), "create_date") && !StringUtils.equalsIgnoreCase(b1.getName(), "del_flag")) {
                    b1.setIsEdit("1");
                } else {
                    b1.setIsEdit("0");
                }

                if (!StringUtils.equalsIgnoreCase(b1.getName(), "name") && !StringUtils.equalsIgnoreCase(b1.getName(), "title") && !StringUtils.equalsIgnoreCase(b1.getName(), "remarks") && !StringUtils.equalsIgnoreCase(b1.getName(), "update_date")) {
                    b1.setIsList("0");
                } else {
                    b1.setIsList("1");
                }

                if (!StringUtils.equalsIgnoreCase(b1.getName(), "name") && !StringUtils.equalsIgnoreCase(b1.getName(), "title")) {
                    b1.setIsQuery("0");
                } else {
                    b1.setIsQuery("1");
                }

                if (!StringUtils.equalsIgnoreCase(b1.getName(), "name") && !StringUtils.equalsIgnoreCase(b1.getName(), "title")) {
                    b1.setQueryType("=");
                } else {
                    b1.setQueryType("like");
                }

                if (StringUtils.startsWithIgnoreCase(b1.getName(), "user_id")) {
                    b1.setJavaType(User.class.getName());
                    b1.setJavaField(b1.getJavaField().replaceAll("Id", ".id|name"));
                    b1.setShowType("userselect");
                } else if (StringUtils.startsWithIgnoreCase(b1.getName(), "office_id")) {
                    b1.setJavaType(Office.class.getName());
                    b1.setJavaField(b1.getJavaField().replaceAll("Id", ".id|name"));
                    b1.setShowType("officeselect");
                } else if (StringUtils.startsWithIgnoreCase(b1.getName(), "area_id")) {
                    b1.setJavaType(Area.class.getName());
                    b1.setJavaField(b1.getJavaField().replaceAll("Id", ".id|name"));
                    b1.setShowType("areaselect");
                } else if (!StringUtils.startsWithIgnoreCase(b1.getName(), "create_by") && !StringUtils.startsWithIgnoreCase(b1.getName(), "update_by")) {
                    if (!StringUtils.startsWithIgnoreCase(b1.getName(), "create_date") && !StringUtils.startsWithIgnoreCase(b1.getName(), "update_date")) {
                        if (!StringUtils.equalsIgnoreCase(b1.getName(), "remarks") && !StringUtils.equalsIgnoreCase(b1.getName(), "content")) {
                            if (StringUtils.equalsIgnoreCase(b1.getName(), "parent_id")) {
                                b1.setJavaType("This");
                                b1.setJavaField("parent.id|name");
                                b1.setShowType("treeselect");
                            } else if (StringUtils.equalsIgnoreCase(b1.getName(), "parent_ids")) {
                                b1.setShowType("input");
                                b1.setQueryType("like");
                            } else if (StringUtils.equalsIgnoreCase(b1.getName(), "del_flag")) {
                                b1.setShowType("radiobox");
                                b1.setDictType("del_flag");
                            } else {
                                b1.setShowType("input");
                            }
                        } else {
                            b1.setShowType("textarea");
                        }
                    } else {
                        b1.setShowType("dateselect");
                    }
                } else {
                    b1.setJavaType(User.class.getName());
                    b1.setJavaField(b1.getJavaField() + ".id");
                    b1.setShowType("input");
                }
            }
        }
    }

    /**
     * 获取模板物理路径
     * @return
     */
    public static String a5() {
        try {
            File a1 = (new DefaultResourceLoader()).getResource("").getFile();
            if (a1 != null) {
                return a1.getAbsolutePath() + File.separator + StringUtils.replaceEach(a.class.getName(), new String[]{"util." + a.class.getSimpleName(), "."}, new String[]{"template", File.separator});
            }
        } catch (Exception var1) {
            logger.error("{}", var1);
        }

        return "";
    }

    /**
     * 根据对话框和窗口类型获取模板
     * @param a1
     * @param a2
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T a6(String a1, String a2, Class<?> clazz) {
        try {
            String a3 = "";
            if ("1".equals(a1)) {
                a3 = "com/jhmis/modules/gen/templates/form-style/modules/gen/" + a2;
            } else {
                a3 = "com/jhmis/modules/gen/templates/dialog-style/modules/gen/" + a2;
            }

            Resource a4 = new ClassPathResource(a3);
            InputStream a5 = a4.getInputStream();
            BufferedReader a8 = new BufferedReader(new InputStreamReader(a5, "UTF-8"));
            StringBuilder a6 = new StringBuilder();

            while(true) {
                String a7 = a8.readLine();
                if (a7 == null) {
                    if (a5 != null) {
                        a5.close();
                    }

                    if (a8 != null) {
                        a8.close();
                    }

                    return (T)JaxbMapper.fromXml(a6.toString(), clazz);
                }

                a6.append(a7).append("\r\n");
            }
        } catch (IOException var9) {
            logger.warn("Error file convert: {}", var9.getMessage());
            return null;
        }
    }

    /**
     * 读取表单配置文件
     * @return
     */
    public static GenConfig a7() {
        try {
            String a1 = "com/jhmis/modules/gen/templates/config.xml";
            Resource a2 = new ClassPathResource(a1);
            InputStream a3 = a2.getInputStream();
            BufferedReader a4 = new BufferedReader(new InputStreamReader(a3, "UTF-8"));
            StringBuilder a5 = new StringBuilder();

            while(true) {
                String a6 = a4.readLine();
                if (a6 == null) {
                    if (a3 != null) {
                        a3.close();
                    }

                    if (a4 != null) {
                        a4.close();
                    }

                    return (GenConfig)JaxbMapper.fromXml(a5.toString(), GenConfig.class);
                }

                a5.append(a6).append("\r\n");
            }
        } catch (IOException var6) {
            logger.warn("Error file convert: {}", var6.getMessage());
            return null;
        }
    }

    /**
     * 读取模板分类数据
     * @param a1
     * @param a2
     * @param a3
     * @param a4
     * @return
     */
    public static List<GenTemplate> a8(GenConfig a1, String a2, boolean a3, String a4) {
        List<GenTemplate> b1 = Lists.newArrayList();
        if (a1 != null && a1.getCategoryList() != null && a2 != null) {
            Iterator<GenCategory> var6 = a1.getCategoryList().iterator();

            while(var6.hasNext()) {
                GenCategory e = (GenCategory)var6.next();
                if (a2.equals(e.getValue())) {
                    List<String> c1 = null;
                    if (!a3) {
                        c1 = e.getTemplate();
                    } else {
                        c1 = e.getChildTableTemplate();
                    }

                    if (c1 != null) {
                        Iterator<String> var9 = c1.iterator();

                        while(var9.hasNext()) {
                            String s = (String)var9.next();
                            if (StringUtils.startsWith(s, GenCategory.CATEGORY_REF)) {
                                b1.addAll(a8(a1, StringUtils.replace(s, GenCategory.CATEGORY_REF, ""), false, a4));
                            } else {
                                GenTemplate a11 = (GenTemplate)a6(a4, s, GenTemplate.class);
                                if (a11 != null) {
                                    b1.add(a11);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }

        return b1;
    }

    /**
     * 获取模板需要的参数
     * @param a1
     * @return
     */
    public static Map<String, Object> a9(GenScheme a1) {
        Map<String, Object> a2 = Maps.newHashMap();
        a2.put("packageName", StringUtils.lowerCase(a1.getPackageName()));
        a2.put("lastPackageName", StringUtils.substringAfterLast((String)a2.get("packageName"), "."));
        a2.put("moduleName", StringUtils.lowerCase(a1.getModuleName()));
        a2.put("subModuleName", StringUtils.lowerCase(a1.getSubModuleName()));
        a2.put("className", StringUtils.uncapitalize(a1.getGenTable().getClassName()));
        a2.put("ClassName", StringUtils.capitalize(a1.getGenTable().getClassName()));
        a2.put("functionName", a1.getFunctionName());
        a2.put("functionNameSimple", a1.getFunctionNameSimple());
        a2.put("functionAuthor", StringUtils.isNotBlank(a1.getFunctionAuthor()) ? a1.getFunctionAuthor() : UserUtils.getUser().getName());
        a2.put("functionVersion", DateUtils.getDate());
        a2.put("urlPrefix", a2.get("moduleName") + (StringUtils.isNotBlank(a1.getSubModuleName()) ? "/" + StringUtils.lowerCase(a1.getSubModuleName()) : "") + "/" + a2.get("className"));
        a2.put("viewPrefix", a2.get("urlPrefix"));
        a2.put("permissionPrefix", a2.get("moduleName") + (StringUtils.isNotBlank(a1.getSubModuleName()) ? ":" + StringUtils.lowerCase(a1.getSubModuleName()) : "") + ":" + a2.get("className"));
        a2.put("dbType", Global.getConfig("jdbc.type"));
        a2.put("table", a1.getGenTable());
        return a2;
    }

    /**
     * 写入文件
     * @param a1
     * @param a2
     * @param a3
     * @param a4
     * @return
     * @throws Exception
     */
    public static String a10(GenTemplate a1, Map<String, Object> a2, boolean a3, String a4) throws Exception {
        String a5 = a4 + File.separator + StringUtils.replaceEach(FreeMarkers.renderString(a1.getFilePath() + "/", a2), new String[]{"//", "/", "."}, new String[]{File.separator, File.separator, File.separator}) + FreeMarkers.renderString(a1.getFileName(), a2);
        logger.debug(" fileName === " + a5);
        String a6 = FreeMarkers.renderString(StringUtils.trimToEmpty(a1.getContent()), a2);
        logger.debug(" content === \r\n" + a6);
        if (a3) {
            FileUtils.deleteFile(a5);
        }

        if (FileUtils.createFile(a5)) {
            FileUtils.writeToFile(a5, a6, true);
            logger.debug(" file create === " + a5);
            return "生成成功：" + a5 + "<br/>";
        } else {
            logger.debug(" file extents === " + a5);
            return "文件已存在：" + a5 + "<br/>";
        }
    }

    /**
     * 根据数据库类型获取不同的字段字典
     * @return
     */
    public static String a18() {
        String a1 = Global.getConfig("jdbc.type");
        GenConfig a2 = a7();
        StringBuffer a3 = new StringBuffer();
        List<GenDict> a4 = a2.getMysqlFieldType();
        if (a1.equals("mysql")) {
            a4 = a2.getMysqlFieldType();
        } else if (a1.equals("oracle")) {
            a4 = a2.getOracleFieldType();
        } else if (a1.equals("mssql")) {
            a4 = a2.getMssqlFieldType();
        }

        Iterator<GenDict> var5 = a4.iterator();

        while(var5.hasNext()) {
            GenDict a5 = (GenDict)var5.next();
            a3.append("'" + a5.getValue() + "',");
        }

        return a3.substring(0, a3.length() - 1).toString();
    }
}
