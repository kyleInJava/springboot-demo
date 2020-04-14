package com.kyle.demo.entity;

import javax.validation.groups.Default;

/**
 * 用于分组校验
 * 由于所有的校验默认是Default分组的，
 * 所以如果没有继承Default接口，在使用DUR分组的时候就指挥对标注了CUD分组的字段进行校验
 */
public interface DUR extends Default{

}
