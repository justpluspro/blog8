package com.qwli7.blog.plugin.mybatis;

import com.qwli7.blog.entity.Tag;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author qwli7
 * @date 2023/4/6 15:53
 * 功能：blog8
 **/
public class TagsTypeHandler extends BaseTypeHandler<Set<Tag>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Set<Tag> tags, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Set<Tag> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return toTags(resultSet.getString(s));
    }

    @Override
    public Set<Tag> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return toTags(resultSet.getString(i));
    }

    @Override
    public Set<Tag> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }


    private Set<Tag> toTags(String str) {
        if (StringUtils.isEmpty(str)) {
            return new LinkedHashSet<>();
        }
        return StringUtils.commaDelimitedListToSet(str).stream().map(e -> {
            Tag tag = new Tag();
            tag.setId(Integer.parseInt(e));
            return tag;
        }).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
