package org.zerock.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.zerock.model.CustomUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "webfirewood";

    // 토큰 유효시간 15일
    private long tokenValidTime = 15 * 24 * 60 * 60 * 1000L;
    
    @Setter(onMethod_= @Autowired)
    private CustomUserDetailsService customUserDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
    	log.info("JwtTokenProvider init()");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userId, List<? extends GrantedAuthority> authList) {
        Claims claims = Jwts.claims().setSubject(userId); // JWT payload 에 저장되는 정보단위
        claims.put("auth", authList); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        
        String jwtToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 
                                                                // signature 에 들어갈 secret값 세팅
                .compact();
        log.info("createToken() -> JWTTokenValue : " + jwtToken);
        return jwtToken;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
    	if(token == null) {
    		log.info("getAuthentication() -> getuserid() -> token value : " + token);
    		return null;
    	}
        CustomUser userDetails = (CustomUser) customUserDetailsService.loadUserByUsername(this.getUserId(token));
        log.info("getAutentication -> UserDetails : " + userDetails);
        UsernamePasswordAuthenticationToken authenticationToken = 
        		new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        log.info("getAuthentication -> authenticationToken : " + authenticationToken);
        return authenticationToken;
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
    	if(token == null) {
    		log.info("getUserId() -> token value : " + token);
    		return null;
    	}
    	log.info("getUserId() -> token value : " + token);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    
    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
    	String returnValue = request.getHeader("X-AUTH-TOKEN");
    	log.info("resolveToken() -> X-AUTH-TOKEN : " + returnValue);
        return returnValue;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            log.info("validateToken() -> Token Validation : " + claims);
            boolean validateReturn = claims.getBody().getExpiration().before(new Date());
            log.info("validateToken() -> expiration date : " + claims.getBody().getExpiration());
            log.info("validateToken() -> TokenValidate : " + !validateReturn);
            return !validateReturn;
        } catch (Exception e) {
            return false;
        }
    }
}