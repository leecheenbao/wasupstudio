package com.wasupstudio.security;

import com.wasupstudio.model.dto.MemberDTO;
import lombok.Data;

/**
 * JwtUserDTO
 *
 * @author star
 */
@Data
public class JwtUser {

    private MemberDTO member;

    private String token;


}
