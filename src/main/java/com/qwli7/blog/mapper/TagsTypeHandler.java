package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Tag;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TagsTypeHandler extends BaseTypeHandler<Set<Tag>> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Set<Tag> tags, JdbcType jdbcType) throws SQLException {
        throw new RuntimeException("unsupport");
    }

    @Override
    public Set<Tag> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return get(resultSet.getString(s));
    }

    @Override
    public Set<Tag> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return get(resultSet.getString(i));
    }

    @Override
    public Set<Tag> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        throw new RuntimeException("unsupport");
    }
    public Set<Tag> get(String tagIds) {
        if(StringUtils.isEmpty(tagIds)) {
            return new HashSet<>();
        }
        return Arrays.stream(StringUtils.commaDelimitedListToStringArray(tagIds)).map(Integer::parseInt).map(Tag::new)
                .collect(Collectors.toSet());
    }
}
