package com.byavs.frame.core.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.*;

/**
 * Created by zemo.wang on 2017/7/28.
 */
public class MapperXmlCachePlugin extends PluginAdapter {
    //属性name
    private String type = "type";
    //获取属性
    private Set<Map.Entry<Object, Object>> entrySet;

    private List<String> cacheList = new ArrayList<String>(Arrays.asList("eviction", "flushInterval", "readOnly", "size", "type"));

    public MapperXmlCachePlugin() {
    }

    @Override
    public void setProperties(Properties properties) {
        entrySet = properties.entrySet();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 添加缓存
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        boolean isType = false;
        XmlElement rootElement = document.getRootElement();
        XmlElement cacheElement = new XmlElement("cache");
        context.getCommentGenerator().addComment(cacheElement);
        Set<Map.Entry<Object, Object>> entrySet = new HashSet<Map.Entry<Object, Object>>();
        entrySet.addAll(this.entrySet);
        //添加cache属性
        for (String cacheAttribute : cacheList) {
            Iterator<Map.Entry<Object, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<Object, Object> entry = iterator.next();
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                if (cacheAttribute.equals(key)) {
                    cacheElement.addAttribute(new Attribute(key, value));
                    iterator.remove();
                    if (type.equals(key) && StringUtility.stringHasValue(value))
                        isType = true;
                }
            }
        }
        //引用其他缓存，不使用默认缓存配置，调整缓存参数
        if (isType) {
            for (Map.Entry<Object, Object> entry : entrySet) {
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                XmlElement entryElement = new XmlElement("property");
                entryElement.addAttribute(new Attribute("name", key));
                entryElement.addAttribute(new Attribute("value", value));
                cacheElement.addElement(entryElement);
            }
        }
        rootElement.addElement(0, cacheElement);
        return true;
    }
}
