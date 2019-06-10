package com.luiztaira.utils;

public interface Converter<T, P> {
	 P convert(T t);
}
