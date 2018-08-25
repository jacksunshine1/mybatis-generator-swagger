package com.zzg.mybatis.generator.plugins;

/**
 * Created by zouzhigang on 2016/6/14.
 */

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class MySQPagePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    
    /**
     * 为每个Example类添加pageSize和pageNumber属性已经set、get方法
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

        Field pageSize = new Field();
        pageSize.setName("pageSize");
        pageSize.setVisibility(JavaVisibility.PRIVATE);
        pageSize.setType(integerWrapper);
        topLevelClass.addField(pageSize);

        Method setPageSize = new Method();
        setPageSize.setVisibility(JavaVisibility.PUBLIC);
        setPageSize.setName("setPageSize");
        setPageSize.addParameter(new Parameter(integerWrapper, "pageSize"));
        setPageSize.addBodyLine("this.pageSize = pageSize;");
        topLevelClass.addMethod(setPageSize);

        Method getPageSize = new Method();
        getPageSize.setVisibility(JavaVisibility.PUBLIC);
        getPageSize.setReturnType(integerWrapper);
        getPageSize.setName("getPageSize");
        getPageSize.addBodyLine("return pageSize;");
        topLevelClass.addMethod(getPageSize);

        Field pageNumber = new Field();
        pageNumber.setName("pageNumber");
        pageNumber.setVisibility(JavaVisibility.PRIVATE);
        pageNumber.setType(integerWrapper);
        topLevelClass.addField(pageNumber);

        Method setPageNumber = new Method();
        setPageNumber.setVisibility(JavaVisibility.PUBLIC);
        setPageNumber.setName("setPageNumber");
        setPageNumber.addParameter(new Parameter(integerWrapper, "pageNumber"));
        setPageNumber.addBodyLine("this.pageNumber = pageNumber;");
        topLevelClass.addMethod(setPageNumber);

        Method getPageNumber = new Method();
        getPageNumber.setVisibility(JavaVisibility.PUBLIC);
        getPageNumber.setReturnType(integerWrapper);
        getPageNumber.setName("getPageNumber");
        getPageNumber.addBodyLine("return pageNumber;");
        topLevelClass.addMethod(getPageNumber);

        return true;
    }

    /**
     * 为Mapper.xml的selectByExample添加pageSize
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {

        XmlElement ifPageSizeNotNullElement = new XmlElement("if");
        ifPageSizeNotNullElement.addAttribute(new Attribute("test", "pageSize != null"));

        XmlElement ifPageNumberNotNullElement = new XmlElement("if");
        ifPageNumberNotNullElement.addAttribute(new Attribute("test", "pageNumber != null"));
        ifPageNumberNotNullElement.addElement(new TextElement("limit (${pageNumber}-1)*${pageSize}, ${pageSize} "));
        
        ifPageSizeNotNullElement.addElement(ifPageNumberNotNullElement);

        XmlElement ifPageNumberNullElement = new XmlElement("if");
        ifPageNumberNullElement.addAttribute(new Attribute("test", "pageNumber == null"));
        ifPageNumberNullElement.addElement(new TextElement("limit ${pageSize} "));
        
        ifPageSizeNotNullElement.addElement(ifPageNumberNullElement);

        element.addElement(ifPageSizeNotNullElement);

        return true;
    }
}
