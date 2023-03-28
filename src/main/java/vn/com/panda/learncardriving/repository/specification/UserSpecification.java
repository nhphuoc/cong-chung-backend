package vn.com.panda.learncardriving.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import vn.com.panda.learncardriving.entity.User;
import vn.com.panda.learncardriving.enums.UserRole;

public class UserSpecification {
    public static Specification<User> likeName(String value) {
        return (root, query, builder) -> getLikePredicate("name", value, root.get("person"), builder);
    }

    public static Specification<User> whereRoleIn(List<UserRole> values) {
        return (root, query, builder) -> {
            if (values.isEmpty()) {
                return null;
            }
            return root.get("role").in(values);
        };
    }

    public static Specification<User> likePhone(String value) {
        return (root, query, builder) -> getLikePredicate("username", value, root, builder);
    }

    private static Predicate getLikePredicate(String field, String value, Path<User> root, CriteriaBuilder builder) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return builder.like(builder.lower(root.get(field)), "%" + value + "%");
    }

    private static Predicate getEqualsPredicate(String field, Object value, Root<User> root, CriteriaBuilder builder) {
        if (value == null) {
            return null;
        }
        return builder.equal(root.get(field), value);
    }
}
