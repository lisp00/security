package org.zerock.mapper;

import java.util.List;

import org.zerock.model.AuthVO;
import org.zerock.model.MemberVO;

public interface MemberMapper {
	public List<MemberVO> readList();
	
	public MemberVO read(String userid);
	
	public void insertMember(MemberVO member);
	
	public void insertAuth(AuthVO auth);
	
	public int updateMember(MemberVO member);
	
	public int updateAuth(AuthVO auth);
	
	public int deleteMember(String userid);
	
	public int deleteAuth(String userid);
}
