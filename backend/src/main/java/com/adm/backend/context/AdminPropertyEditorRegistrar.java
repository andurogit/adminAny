package com.adm.backend.context;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

/**
 * AdminPropertyEditorRegistrar
 * 프로퍼티 등록관
 * 스프링 프로퍼티 편집기
 */
public class AdminPropertyEditorRegistrar implements PropertyEditorRegistrar{
    private String datePattern = "yyyy/MM/dd hh:mm:ss";

	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Date.class, this.createCustomEditor());
	}

    // empty constractor
    public AdminPropertyEditorRegistrar() {}

    // datePattern constarctor
    public AdminPropertyEditorRegistrar(String datePattern) {
        this.datePattern = datePattern;
    }

    private PropertyEditor createCustomEditor() {
        return new CustomDateEditor((DateFormat) new SimpleDateFormat(this.datePattern), true);
    }
}