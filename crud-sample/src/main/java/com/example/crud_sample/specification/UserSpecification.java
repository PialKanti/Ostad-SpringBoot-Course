package com.example.crud_sample.specification;

import com.example.crud_sample.model.entity.Profile;
import com.example.crud_sample.model.entity.User;
import com.example.crud_sample.model.enums.Gender;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    // Filter by first name
    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    // Filter by last name
    public static Specification<User> hasLastName(String lastName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    // Filter by username
    public static Specification<User> hasUsername(String username) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    // Filter by phone number (Profile)
    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, cb) -> {
            Join<User, Profile> profileJoin = root.join("profile", JoinType.LEFT);
            return cb.like(profileJoin.get("phoneNumber"), "%" + phoneNumber + "%");
        };
    }

    // Filter by gender (Profile)
    public static Specification<User> hasGender(Gender gender) {
        return (root, query, cb) -> {
            Join<User, Profile> profileJoin = root.join("profile", JoinType.LEFT);
            return cb.equal(profileJoin.get("gender"), gender);
        };
    }
}
