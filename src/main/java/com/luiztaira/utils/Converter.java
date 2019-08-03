package com.luiztaira.utils;

public interface Converter<T, R> {
	 R convert(T t);
}
