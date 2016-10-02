<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${table.mappingName?cap_first}DAO">
	
	<resultMap type="${beanPackage}.${table.mappingName?cap_first}" id="BaseResultMap">
		<#list columnList as column>
			<#if column.isPrimary=="YES">
				<id column="${column.columnName?lower_case}" property="${column.mappingName?uncap_first}" />
			<#else>
				<result column="${column.columnName?lower_case}" property="${column.mappingName?uncap_first}"/>
			</#if>
		</#list>
	</resultMap>
	
	<sql id="Base_Column_List">
		<#list columnList as column>${column.columnName?lower_case}<#if column_has_next>,</#if></#list>
	</sql>
	
	<sql id="Where_Column_List">
  		<#list columnList as column>
			<#if column.isPrimary!="YES">
		<if test="${column.mappingName?uncap_first}!=null and ${column.mappingName?uncap_first}!=''">
			AND ${column.columnName?lower_case} = ${r"#{"}${column.mappingName?uncap_first}${r"}"}
		</if>
			</#if>
		</#list>
  	</sql>
	
	<insert id="insert" parameterType="${beanPackage}.${table.mappingName?cap_first}" useGeneratedKeys = "true" keyProperty = "id">
		INSERT INTO ${table.tableName?lower_case} (<#list columnList as column><#if column.isPrimary!="YES">${column.columnName?lower_case}<#if column_has_next>,</#if></#if></#list>)
		VALUES (<#list columnList as column><#if column.isPrimary!="YES">${r"#{"}${column.mappingName?uncap_first}${r"}"}<#if column_has_next>,</#if></#if></#list>)
	</insert>
	
	<insert id="insertSelective" parameterType="${beanPackage}.${table.mappingName?cap_first}" useGeneratedKeys = "true" keyProperty = "id">
		INSERT INTO ${table.tableName?lower_case} 
		<trim prefix="(" suffix=")" suffixOverrides="," >
			<#list columnList as column>
				<#if column.isPrimary!="YES">
					<if test="${column.mappingName?uncap_first} != null" >
		        		${column.columnName?lower_case},
		      		</if>
		      	</#if>
			</#list>
	    </trim>
    	<trim prefix="values (" suffix=")" suffixOverrides="," >
			<#list columnList as column>
				<#if column.isPrimary!="YES">
                	<if test="${column.mappingName?uncap_first} != null">
					${r"#{"}${column.mappingName?uncap_first}${r"}"},
					</if>
				</#if>
			</#list>
		</trim>
	</insert>
	
	<update id="updateById" parameterType="${beanPackage}.${table.mappingName?cap_first}">
		UPDATE ${table.tableName?lower_case}
 		SET <#list columnList as column><#if column.isPrimary!="YES">${column.columnName?lower_case}=${r"#{"}${column.mappingName?uncap_first}${r"}"}<#if column_has_next>,</#if></#if></#list>
		WHERE ${databasePrimary?lower_case}=${r"#{"}${javaPrimary?uncap_first}${r"}"}
	</update>
	
	<update id="updateIdKeySelective" parameterType="${beanPackage}.${table.mappingName?cap_first}">
		UPDATE ${table.tableName?lower_case}
 		<set>
 		<#list columnList as column>
 			<#if column.isPrimary!="YES">
 			<if test="${column.mappingName?uncap_first} != null" >
 				${column.columnName?lower_case}=${r"#{"}${column.mappingName?uncap_first}${r"}"},
 			</if>
 			</#if>
 		</#list>
 		</set>
		WHERE ${databasePrimary?lower_case}=${r"#{"}${javaPrimary?uncap_first}${r"}"}
	</update>

	<delete id="deleteById" parameterType="java.lang.Long">
		DELETE FROM ${table.tableName?lower_case} WHERE ${databasePrimary?lower_case}=${r"#{value}"}
	</delete>

	<select id="selectById" resultMap="BaseResultMap" parameterType="java.lang.Long">
		SELECT <include refid="Base_Column_List"/> FROM ${table.tableName?lower_case} WHERE ${databasePrimary?lower_case}=${r"#{value}"} LIMIT 0,1
	</select>
	
	<select id="findAll" resultMap="BaseResultMap" parameterType="com.hundsun.sse.bond.project.dto.${table.mappingName?cap_first}Qry">
		SELECT <include refid="Base_Column_List"/> FROM ${table.tableName?lower_case} 
		 where 1 = 1
    	<include refid="Where_Column_List" />
    	order by id 
	</select>
	
</mapper>
