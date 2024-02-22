package com.test.users.dtos.response;

import com.test.users.models.Phone;
import com.test.users.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private Date created;
    private Date updated;
    private Date last_login;
    private String token;
    private Boolean isactive;
    private List<PhoneResponse> phones;

    public static UserResponse toResponse(User user, boolean allData){
        UserResponseBuilder builder =  UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .created(user.getCreated())
                .updated(user.getUpdated())
                .last_login(user.getLastlogin())
                .isactive(user.getIsactive());
        if (allData){
            builder.token(user.getToken())
                .phones(getPhoneList(user.getPhones()));
        }

        return builder.build();
    }

    private static List<PhoneResponse> getPhoneList(List<Phone> phoneList){
        List<PhoneResponse> responseList = new ArrayList<>();
        if (!phoneList.isEmpty())
            for (Phone phone : phoneList) {
                responseList.add(PhoneResponse.builder()
                        .id(phone.getId())
                        .number(phone.getNumber())
                        .citycode(phone.getCitycode())
                        .countrycode(phone.getCountrycode())
                        .build());
            }
        return responseList;
    }
}
