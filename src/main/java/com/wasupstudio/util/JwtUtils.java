package com.wasupstudio.util;

import com.wasupstudio.constant.SecurityConstants;
import com.wasupstudio.constant.UserRoleConstants;
import com.wasupstudio.model.entity.MemberEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.DatatypeConverter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Jwt 工具類，用於生成、解析與驗證 token
 *
 * @author Paul
 **/
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

    private JwtUtils() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    /**
     * 根據用戶名和用戶角色生成 token
     *
     * @param member   用戶
     * @param isRemember 是否記住我
     * @return 返回生成的 token
     */
    public static String generateToken(MemberEntity member, boolean isRemember) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);
        // 過期時間
        long expiration = isRemember ? SecurityConstants.EXPIRATION_REMEMBER_TIME : SecurityConstants.EXPIRATION_TIME;
        // 生成 token
        String token = Jwts.builder()
                // 生成簽證信息
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey), SignatureAlgorithm.HS256)
                .setSubject(member.getEmail())
//                .claim(SecurityConstants.TOKEN_ROLE_CLAIM, member.getRole())
                .claim(SecurityConstants.TOKEN_MEMBER_INFO, member)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setIssuedAt(new Date())
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                // 設置有效時間
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
        return token;
    }

    /**
     * 驗證 token 是否有效
     *
     * <p>
     * 如果解析失敗，說明 token 是無效的
     *
     * @param token token 信息
     * @return 如果返回 true，說明 token 有效
     */
    public static boolean validateToken(String token) {
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
        }
        return false;
    }

    /**
     * 根據 token 獲取用戶認證信息
     *
     * @param token token 信息
     * @return 返回用戶認證信息
     */
    public static Authentication getAuthentication(String token) {
        Claims claims = getTokenBody(token);
        // 獲取用戶角色字符串
        Map<String,String> memberInfo = (Map<String, String>) claims.get(SecurityConstants.TOKEN_MEMBER_INFO);
        String role = memberInfo.get(SecurityConstants.TOKEN_ROLE_CLAIM);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (role == null) {
            authorities.add(new SimpleGrantedAuthority(UserRoleConstants.ROLE_USER));
        } else {
            authorities = Arrays.stream(role.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        // 獲取用戶名
        String userName = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }


    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getMemberAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public static String getMemberAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getAuthorities();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public static MemberEntity getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims claims = getTokenBody(authentication.getCredentials().toString());
        Map<String, Object> map = (Map<String, Object>) claims.get(SecurityConstants.TOKEN_MEMBER_INFO);

        if (map != null) {
            Long timestamp = (Long) map.get("birthday");
            Date birthday = new Date(timestamp);

            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setId((Integer) map.get("id"));
            memberEntity.setPhone((String) map.get("phone"));
            memberEntity.setEmail((String) map.get("email"));
            memberEntity.setStatus((Integer) map.get("status"));
            memberEntity.setGrade((Integer) map.get("grade"));
            memberEntity.setOrganization((String) map.get("organization"));
            memberEntity.setBirthday(birthday.toString());

            memberEntity.setRole(MemberEntity.Role.valueOf((String) map.get("role")));
            memberEntity.setName((String) map.get("name"));

            return memberEntity;
        }

        return null;
    }

}


