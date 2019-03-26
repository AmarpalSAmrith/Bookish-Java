package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Books;
import org.softwire.training.bookish.models.database.Members;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembersService extends DatabaseService {
    public List<Members> getAllMembers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM members WHERE applicable = 1")
                        .mapToBean(Members.class)
                        .list()
        );
    }
    public Optional<Members> getSingleMembers(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM members WHERE id = :id AND applicable = 1")
                        .bind("id", id)
                        .mapToBean(Members.class)
                        .findFirst()
        );
    }

    public void addMember(Members member) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO members (first_name, middle_name, surname, birth_date, gender, address_line1, address_line2, city, post_code, applicable) " +
                                         "VALUES (:firstName, :middleName, :surname, :birthDate, :gender, :addressLine1, :addressLine2, :city, :postCode, 1)")
                        .bind("firstName", member.getFirstName())
                        .bind("middleName", member.getMiddleName())
                        .bind("surname", member.getSurname())
                        .bind("birthDate", member.getBirthDate())
                        .bind("gender", member.getGender())
                        .bind("addressLine1", member.getAddressLine1())
                        .bind("addressLine2", member.getAddressLine2())
                        .bind("city", member.getCity())
                        .bind("postCode", member.getPostCode())
                        .execute()
        );
    }

    public void deleteMember(int memberId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE members SET applicable = 0 WHERE id=:id")
                        .bind("id", memberId)
                        .execute()
        );
    }
    public void updateMember(Members member) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE members SET first_name = :firstName, middle_name = :middleName, " +
                        "surname = :surname, birth_date = :birthDate, gender = :gender, address_line1 = :addressLine1, " +
                        "address_line2 = :addressLine2, city = :city, post_code = :postCode WHERE id = :id")
                        .bind("id", member.getId())
                        .bind("firstName", member.getFirstName())
                        .bind("middleName", member.getMiddleName())
                        .bind("surname", member.getSurname())
                        .bind("birthDate", member.getBirthDate())
                        .bind("gender", member.getGender())
                        .bind("addressLine1", member.getAddressLine1())
                        .bind("addressLine2", member.getAddressLine2())
                        .bind("city", member.getCity())
                        .bind("postCode", member.getPostCode())
                        .execute()
        );
    }
}