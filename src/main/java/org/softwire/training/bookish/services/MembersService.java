package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Books;
import org.softwire.training.bookish.models.database.Members;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembersService extends DatabaseService {
    public List<Members> getAllMemebers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM members")
                        .mapToBean(Members.class)
                        .list()
        );
    }

    public void addMember(Members member) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO members (first_name, middle_name, surname, birth_date, gender, address_line1, address_line2, city, post_code) " +
                                         "VALUES (:firstName, :middleName, :surname, :birthDate, :gender, :addressLine1, :addressLine2, :city, :postCode)")
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
                handle.createUpdate("DELETE FROM members WHERE id = :id")
                        .bind("id", memberId)
                        .execute()
        );
    }
    public void updateMember(Members member, int memberId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE members SET first_name = :firstName, middle_name = :middleName, " +
                        "surname = :surname, birth_date = :birthDate, gender = :gender, address_line1 = :addressLine1, " +
                        "address_line2 = :addressLine2, city = :city, post_code = :postCode WHERE id = :id")
                        .bind("id", memberId)
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